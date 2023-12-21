package info.vervaeke.aoc2023.day21

data class Coord(val row: Int, val col: Int) {
    infix operator fun plus(dir: Direction): Coord {
        return Coord(row + dir.drow, col + dir.dcol)
    }
}

enum class Direction(val drow: Int, val dcol: Int) {
    NORTH(-1, 0),
    SOUTH(1, 0),
    EAST(0, 1),
    WEST(0, -1)
}

data class Day21(val lines: List<String>) {
    val grid = lines.map { it.replace("S", ".") }
    val rows = 0 until lines.size
    val cols = 0 until lines[0].length
    val startPos = lines.indexOfFirst { it.contains("S") }.let { Coord(it, lines[it].indexOf("S")) }

    companion object {
        fun parseInput(path: String) = parseLines(readInput(path))
        fun readInput(path: String) = javaClass.getResource(path)!!.readText().lines()
        fun parseLines(lines: List<String>): Day21 {
            return Day21(lines)
        }
    }

    fun part1(): Int {
        return countPlots(64)
    }

    fun countPlots(steps: Int): Int {
        var plots = setOf(startPos)

        repeat(steps) {
            plots = takeStep(plots)
            println("$it -> $plots")
        }

        return plots.size
    }

    fun takeStep(plots: Set<Coord>): Set<Coord> {
        return plots.flatMap { validNeighboursOf(it) }
            .toSet()
    }

    private fun validNeighboursOf(pos: Coord): Set<Coord> {
        return Direction.entries.map {
            pos + it
        }.filter {
            // inside grid
            isInsideGrid(it)
        }.filter {
            isNotAWall(it)
        }.toSet()
    }

    private fun isNotAWall(pos: Coord): Boolean {
        return grid[pos.row][pos.col] != '#'
    }

    private fun isInsideGrid(pos: Coord): Boolean {
        return pos.row in rows && pos.col in cols
    }

    fun part2(): Long {
        return lines.size.toLong()
    }


}


fun main() {
    val day = Day21.parseInput("input")
    println("Part 1: ${day.part1()}")
    println("Part 2: ${day.part2()}")
}