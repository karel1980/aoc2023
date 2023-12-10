package info.vervaeke.aoc2023.day10

import info.vervaeke.aoc2023.day10.Direction.Companion.EAST
import info.vervaeke.aoc2023.day10.Direction.Companion.NESW
import info.vervaeke.aoc2023.day10.Direction.Companion.NORTH
import info.vervaeke.aoc2023.day10.Direction.Companion.SOUTH
import info.vervaeke.aoc2023.day10.Direction.Companion.WEST

data class Coord(val row: Int, val col: Int) {
    infix operator fun plus(direction: Direction): Coord {
        return Coord(row + direction.drow, col + direction.dcol)
    }
}

data class Direction(val drow: Int, val dcol: Int) {
    companion object {
        val EAST = Direction(0, 1);
        val WEST = Direction(0, -1);
        val NORTH = Direction(-1, 0);
        val SOUTH = Direction(1, 0);

        val NESW = listOf(NORTH, EAST, SOUTH, WEST)
    }

    fun opposite(): Direction {
        return Direction(-drow, -dcol)
    }
}

data class Day10(val grid: List<String>) {
    val rows = grid.size
    val cols = grid[0].length

    companion object {
        fun parseInput(path: String) = parseLines(readInput(path))
        fun readInput(path: String) = javaClass.getResource(path)!!.readText().lines()
        fun parseLines(lines: List<String>): Day10 {
            return Day10(lines)
        }
    }

    fun part1(): Int {
        return (findLoop().size + 1)/ 2
    }

    fun findLoop(): List<Coord> {
        var current: Coord = findSCoord()
        val loop = mutableListOf<Coord>()

        var done = false
        while (!done) {
//            println("now at $current")
            val dir = NESW.firstOrNull { inGrid(current + it) && connected(current, it) && current + it !in loop }
            if (dir == null) {
                done = true
            } else {
                val next = current + dir
                loop.add(current)
                current = next
            }
        }

        return loop.toList()
    }

    fun connected(coord: Coord, dir: Direction): Boolean {
        val neighbor = coord + dir

        return conn(coord, dir) && conn(neighbor, dir.opposite())
    }

    fun conn(coord: Coord, dir: Direction): Boolean {
        val sym = symbol(coord)
        return when (dir) {
            NORTH -> sym in listOf("S", "|", "J", "L")
            EAST -> sym in listOf("S", "-", "L", "F")
            SOUTH -> sym in listOf("S", "|", "F", "7")
            WEST -> sym in listOf("S", "-", "J", "7")
            else -> TODO("UH-OH")
        }
    }

    fun symbol(coord: Coord): String {
        return grid[coord.row][coord.col].toString()
    }

    private fun inGrid(coord: Coord): Boolean {
        return coord.row in 0 until rows && coord.col in 0 until cols
    }


    fun findSCoord(): Coord {
        val row = grid.indexOfFirst { it.contains("S") }
        val col = grid[row].indexOf("S")

        return Coord(row, col)
    }

    fun part2(): Int {
        return 42
    }

}


fun main() {
    val day = Day10.parseInput("input")
    println("Part 1: ${day.part1()}")
    println("Part 2: ${day.part2()}")
}