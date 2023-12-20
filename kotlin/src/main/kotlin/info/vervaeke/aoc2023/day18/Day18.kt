package info.vervaeke.aoc2023.day18

import info.vervaeke.aoc2023.day18.Direction.*

enum class Direction(val drow: Int, val dcol: Int, val letter: String) {
    UP(-1, 0, "U"),
    DOWN(1, 0, "D"),
    LEFT(0, -1, "L"),
    RIGHT(0, 1, "R");

    companion object {
        fun byLetter(l: String): Direction {
            return entries.first { it.letter == l }
        }
    }
}

data class Dig(val dir: Direction, val amount: Int, val color: String) {
    fun swap(): Dig {
        val amount = color.substring(0, 5).toInt(16)
        val dir = listOf(RIGHT, DOWN, LEFT, UP)[color[color.length - 1].toString().toInt(16)]

        return Dig(dir, amount, color)
    }
}

data class Coord(val row: Int, val col: Int) {
    infix operator fun plus(dir: Direction): Coord {
        return Coord(row + dir.drow, col + dir.dcol)
    }
}

data class Day18(val digs: List<Dig>) {
    val corners = determineCorners()
    val edges = corners.indices.map { corners[it] to corners[(it + 1) % corners.size] }
    val outlineArea = digs.sumOf { 0L + it.amount }
    val verticalEdges = edges.filter { it.first.row != it.second.row }
    val horizontalEdges = edges.filter { it.first.col != it.second.col }

    companion object {
        fun parseInput(path: String) = parseLines(readInput(path))
        fun readInput(path: String) = javaClass.getResource(path)!!.readText().lines()
        fun parseLines(lines: List<String>) = Day18(lines.map {
            it.split(" ")
        }.map {
            Dig(Direction.byLetter(it[0]), it[1].toInt(), it[2].substring(2, it[2].length - 1))
        })

    }

    fun part1(): Long {
        val rows = corners.map { it.row }
            .flatMap { listOf(it - 1, it, it + 1) }
            .toSet()
            .sorted()

        val cols = corners.map { it.col }
            .flatMap { listOf(it - 1, it, it + 1) }
            .toSet()
            .sorted()

        val rowSegments = rows.dropLast(1).zip(rows.drop(1)).map { it.first until it.second }
        val colSegments = cols.dropLast(1).zip(cols.drop(1)).map { it.first until it.second }

        var insideSurfaceArea = 0L
        rowSegments.forEach { rowSegment ->
            colSegments.forEach { colSegment ->
                val a = Coord(rowSegment.first, colSegment.first)
                val b = Coord(rowSegment.last, colSegment.last)
                val height = 0L + rowSegment.last - rowSegment.first + 1
                val width = 0L + colSegment.last - colSegment.first + 1
                val surf = height * width
                if (isInside(a)) {
//                    println("IN: $a to $b:   adding $height x $width = $surf")
                    insideSurfaceArea += surf
                } else {
//                    println("OUT: $a to $b: not adding $height x $width = $surf")
                }
            }
        }

//        println(outlineArea)
        return insideSurfaceArea + outlineArea;
    }

    fun isInside(coord: Coord): Boolean {
        if (verticalEdges.any { it.contains(coord) }) {
            return false;
        }
        if (horizontalEdges.any { it.contains(coord) }) {
            return false;
        }

        //ray casting
        val hits = verticalEdges.filter { it.first.col > coord.col }
            .filter {
                (it.first.row < coord.row && it.second.row >= coord.row) ||
                        it.second.row < coord.row && it.first.row >= coord.row
            }
            .count()

        return hits % 2 == 1
    }

    fun determineCorners(): List<Coord> {
        return buildList {
            var current = Coord(0, 0)
            digs.forEach {
                add(current)
                current = Coord(current.row + it.dir.drow * it.amount, current.col + it.dir.dcol * it.amount)
            }
        }
    }

    fun part2() = swap().part1()

    fun swap() = Day18(digs.map { it.swap() })

}

private fun Pair<Coord, Coord>.contains(coord: Coord): Boolean {
    if (first.row == second.row) {
        // horizontal edge
        if (coord.row != first.row) {
            return false
        }
        if (first.col < second.col) {
            return coord.col in first.col..second.col
        } else {
            return coord.col in second.col .. first.col
        }
    } else {
        // vertical
        if (coord.col != first.col) {
            return false
        }
        if (first.row < second.row) {
            return coord.row in first.row .. second.row
        } else {
            return coord.row in second.row .. first.row
        }

    }
}

fun main() {
    val day = Day18.parseInput("input")
    //6160 is too low
    println("Part 1: ${day.part1()}")

    //83812607470235 is too high
    println("Part 2: ${day.part2()}")
}