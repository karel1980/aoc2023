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

    fun north(): Coord {
        return this + NORTH
    }

    fun south(): Coord {
        return this + SOUTH
    }

    fun east(): Coord {
        return this + EAST
    }

    fun west(): Coord {
        return this + WEST
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
    val fixedGrid = grid.map {
        it to it.map { it.toString() }.toMutableList()
    }
    val start = findSCoord()

    companion object {
        fun parseInput(path: String) = parseLines(readInput(path))
        fun readInput(path: String) = javaClass.getResource(path)!!.readText().lines()
        fun parseLines(lines: List<String>): Day10 {
            return Day10(lines)
        }
    }

    fun part1(): Int {
        return (findLoop().size + 1) / 2
    }

    fun findLoop(): List<Coord> {
        var current: Coord = start
        val loop = mutableSetOf<Coord>()

        var done = false
        while (!done) {
//            println("now at $current")
            val dir = NESW.firstOrNull { inGrid(current + it) && connected(current, it) && current + it !in loop }
            if (dir == null) {
                done = true
            } else {
                val next = current + dir
                loop.add(current)
                loop.add(next)
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
        if (coord.row !in 0 until rows || coord.col !in 0 until cols) {
            return "."
        } else {
            return grid[coord.row][coord.col].toString()
        }
    }

    fun fixedSymbol(coord: Coord, loop: List<Coord>): String {
        if (coord !in loop) {
            return "."
        }
        val idx = loop.indexOf(coord)
        val next = loop[(idx + 1) % loop.size]
        val prev = loop[(idx + loop.size - 1) % loop.size]
        val dirs = buildSet {
            NESW.forEach {
                if (coord + it == next) add(it)
                if (coord + it == prev) add(it)
            }
        }
        return when (dirs) {
            setOf(EAST, WEST) -> "-"
            setOf(NORTH, SOUTH) -> "|"
            setOf(EAST, NORTH) -> "L"
            setOf(EAST, SOUTH) -> "F"
            setOf(WEST, NORTH) -> "J"
            setOf(WEST, SOUTH) -> "7"
            else -> TODO()
        }

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
        return part2_floodfill_doublegrid()
//        return part2_raycast()
    }

    fun part2_floodfill_doublegrid(): Int {
        return enclosedInLoop(findLoop())
    }

    fun part2_raycast(): Int {
        val loop = findLoop()

        val result = (0 until rows).sumOf { row ->
            cellsInsideLoop(row, loop)
        }
        return result
    }

    /* grid that only contains the loop. everything else is set to '.' */
    private fun createCleanGrid(loop: Set<Coord>): List<MutableList<String>> {
        val cleanGrid = grid.map { ".".repeat(cols).map { it.toString() }.toMutableList() }
        loop.forEach {
            cleanGrid[it.row][it.col] = symbol(it)
        }
        return cleanGrid
    }

    private fun cellsInsideLoop(row: Int, loop: List<Coord>): Int {
        return (0 until cols)
            .sumOf { col ->
                oneIfInsideLoop(row, col, loop)
            }
    }

    private fun oneIfInsideLoop(row: Int, col: Int, loop: List<Coord>) =
        if (isInsideLoop(Coord(row, col), loop)) {
            1
        } else {
            0
        }

    private fun isInsideLoop(coord: Coord, loop: List<Coord>): Boolean {
        if (fixedSymbol(coord, loop) != ".") {
            return false
        }
        val countWallsRight = (coord.col + 1 until cols).map {
            Coord(coord.row, it)
        }.filter {
            it in loop
        }.count {
            val symbol = fixedSymbol(it, loop)
            symbol in "|7F"
        }
        val isInside = countWallsRight % 2 == 1
        return isInside
    }

    fun enclosedInLoop(loop: List<Coord>): Int {
        // blow up coordinates
        val doubleLoop = loop.map { Coord(it.row * 2, it.col * 2) }.toMutableSet()

        loop.forEach {
            val here = Coord(it.row * 2, it.col * 2)
            val symbol = fixedSymbol(it, loop)
            when (symbol) {
                "L" -> {
                    doubleLoop.add(here + EAST)
                    doubleLoop.add(here + NORTH)
                }

                "F" -> {
                    doubleLoop.add(here + EAST)
                    doubleLoop.add(here + SOUTH)
                }

                "J" -> {
                    doubleLoop.add(here + WEST)
                    doubleLoop.add(here + NORTH)
                }

                "7" -> {
                    doubleLoop.add(here + WEST)
                    doubleLoop.add(here + SOUTH)
                }

                "-" -> {
                    doubleLoop.add(here + EAST)
                    doubleLoop.add(here + WEST)
                }

                "|" -> {
                    doubleLoop.add(here + NORTH)
                    doubleLoop.add(here + SOUTH)
                }

                else -> {
                    println("found $symbol at $it")
                }
            }
        }

        val offsetLoop = doubleLoop.map { Coord(it.row + 2, it.col + 2) }
        val rows = offsetLoop.maxOf { it.row } + 2
        val cols = offsetLoop.maxOf { it.col } + 2
        val grid = (0..rows).map {
            (0..cols).map { "." }.toMutableList()
        }

        // now paint doubleLoop on a grid
        offsetLoop.forEach {
            grid[it.row][it.col] = "#"
        }
//        printGrid(grid)
        // flood fill it
        floodFill(grid)
//        printGrid(grid)

        // now count the dots that are on even positions
        return (0 until grid.size step 2).sumOf { row ->
            (0 until grid[0].size step 2).count { col -> grid[row][col] == "." }
        }
    }

    private fun floodFill(grid: List<MutableList<String>>) {

        val toVisit = mutableSetOf(Coord(0, 0))

        while (!toVisit.isEmpty()) {
            val current = toVisit.take(1)[0]
            grid[current.row][current.col] = "#"
            toVisit.remove(current)

            NESW.map { current + it }
                .filter { it.row in 0 until grid.size }
                .filter { it.col in 0 until grid[0].size }
                .filter { grid[it.row][it.col] != "#" }
                .forEach {
                    toVisit.add(it)
                }
        }

    }

    fun printGrid(grid: Iterable<Iterable<String>>) {
        grid.forEach {
            println(it.joinToString(""))
        }
    }

}

fun String.connections() = when (this) {
    "S" -> TODO()
    "J" -> setOf(EAST, NORTH)
    "F" -> setOf(EAST, SOUTH)
    "L" -> setOf(WEST, NORTH)
    "7" -> setOf(WEST, SOUTH)
    "-" -> setOf(EAST, WEST)
    "|" -> setOf(NORTH, SOUTH)
    else -> setOf()
}

fun main() {
    val day = Day10.parseInput("input")
    println("Part 1: ${day.part1()}")
    println("Part 2: ${day.part2()}")
}