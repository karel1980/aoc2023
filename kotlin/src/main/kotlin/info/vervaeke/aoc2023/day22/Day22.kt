package info.vervaeke.aoc2023.day22

import kotlin.math.max
import kotlin.math.min

data class Coord(val x: Int, val y: Int, val z: Int) {
    companion object {
        fun parse(spec: String) = spec.split(",")
            .map { it.toInt() }
            .let { Coord(it[0], it[1], it[2]) }
    }

    fun moveDown(dz: Int): Coord {
        return Coord(x, y, z - dz)
    }
}

data class Brick(val id: String, val start: Coord, val end: Coord) {
    val xs = start.x..end.x
    val ys = start.y..end.y

    init {
        if (start.x > end.x) TODO()
        if (start.y > end.y) TODO()
        if (start.z > end.z) TODO()
//        if (start.z == 0) TODO()
    }

    companion object {
        fun parse(id: String, spec: String): Brick {
            val parts = spec.split("~")
            return Brick(id, Coord.parse(parts[0]), Coord.parse(parts[1]))
        }
    }

    fun isOnGround() = start.x == 1

    fun isUnder(other: Brick): Boolean {
        return end.z < other.start.z && overlapsViewedFromTheTop(other)
    }

    fun overlapsViewedFromTheTop(other: Brick): Boolean {
        val xOverlap = max(start.x, other.start.x)..min(end.x, other.end.x)
        val yOverlap = max(start.y, other.start.y)..min(end.y, other.end.y)
        return !xOverlap.isEmpty() && !yOverlap.isEmpty()
    }

    fun moveDown(dz: Int): Brick {
        return Brick(id, start.moveDown(dz), end.moveDown(dz))
    }

    fun canBeDesintegrated(bricks: Set<Brick>): Boolean {
        return bricks.filter {
            // only keep bricks that this brick is supporting
            this.isDirectlySupporting(it)
        }.all { brickAbove ->
            // if all bricks above this one are supported more than once we can disintegrate this one
            bricks.count { it.isDirectlySupporting(brickAbove) } > 1
        }
    }

    fun isDirectlySupporting(brick: Brick): Boolean {
        if (brick.start.z != end.z + 1) {
            return false
        }

        return overlapsViewedFromTheTop(brick)
    }
}

data class Day22(val bricks: List<Brick>) {

    companion object {
        fun parseInput(path: String) = parseLines(readInput(path))
        fun readInput(path: String) = javaClass.getResource(path)!!.readText().lines()
        fun parseLines(lines: List<String>): Day22 {
            return Day22(lines.mapIndexed { idx, it -> Brick.parse((Char('A'.code + idx)).toString(), it) })
        }
    }

    fun part1(): Int {
        return countBricsThatCanBeDisintegrated()
    }

    fun countBricsThatCanBeDisintegrated(): Int {
        val bricks = dropThemAll()

        val found = findBricksThatCanBeDesintegrated(bricks)
        return found.size
    }

    fun findBricksThatCanBeDesintegrated(bricks: Set<Brick>): List<Brick> {
        return bricks.filter {
            it.canBeDesintegrated(bricks)
        }
    }

    fun dropThemAll(): MutableSet<Brick> {
        return dropThemAll(bricks)
    }

    private fun dropThemAll(bricksToDrop: List<Brick>): MutableSet<Brick> {
        val dropped = mutableSetOf<Brick>()
        val remaining = bricksToDrop.sortedBy { it.start.z }.toMutableSet()

        while (remaining.isNotEmpty()) {
            val next = remaining.sortedBy { it.start.z }.first()
            val bricksBelow = dropped.filter {
                it.isUnder(next)
            }

            dropped.add(canDrop(bricksBelow, next))
            remaining.remove(next)

        }

        return dropped
    }

    private fun canDrop(
        bricksBelow: List<Brick>,
        brick: Brick
    ): Brick {
        if (bricksBelow.isEmpty()) {
//            println("no bricks below $brick")
            val floored = brick.moveDown(brick.start.z - 1)
//            println("dropped to the floor: $floored")
            return floored
        } else {
            val highestUnder = bricksBelow.maxBy { it.end.z }
            val distanceBetween = brick.start.z - highestUnder.end.z
            val nextDown = brick.moveDown(distanceBetween - 1)
//            println("$brick falls on top of $highestUnder an becomes $nextDown")
            return nextDown
        }
    }

    fun part2(): Int {
        val bricks = dropThemAll()
        return bricks.map { brick -> howManyWouldFallAfterRemoving(brick, bricks) }
            .sum()
    }

    private fun howManyWouldFallAfterRemoving(brick: Brick, bricks: Set<Brick>): Int {
        val bricksWithoutFirst = bricks.filter { it != brick }

        val droppedBricks = dropThemAll(bricksWithoutFirst)

        // very brick that is not in the original list was dropped because bricks are value objects
        return droppedBricks.filter { it !in bricks }.count()
    }

}

fun main() {
    val day = Day22.parseInput("input")
    println("Part 1: ${day.part1()}")
    //1290 is too low
    println("Part 2: ${day.part2()}")
}