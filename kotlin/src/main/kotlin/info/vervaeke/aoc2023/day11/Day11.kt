package info.vervaeke.aoc2023.day11

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

data class Coord(val row: Int, val col: Int) {
}

data class Day11(val grid: List<String>) {
    val rows = 0 until grid.size
    val cols = 0 until grid[0].length
    val emptyRows = rows.filter {
        '#' !in grid[it]
    }
    val emptyCols = cols.filter { col ->
        rows.all { grid[it][col] != '#' }
    }

    companion object {
        fun parseInput(path: String) = parseLines(readInput(path))
        fun readInput(path: String) = javaClass.getResource(path)!!.readText().lines()
        fun parseLines(lines: List<String>): Day11 {
            return Day11(lines)
        }
    }

    fun part1(): Long {
        return calculateSpace(2)
    }

    fun part2(): Long {
        return calculateSpace(1000000L)
    }

    fun calculateSpace(emptySpace: Long = 2): Long {
        val galaxies = findGalaxies()

        return galaxies.sumOf { a ->
            galaxies.sumOf { b ->
                distance(a, b, emptySpace)
            }
        } / 2
    }

    fun distance(a: Coord, b: Coord, emptySpace: Long = 2): Long {
        val d = abs(b.col - a.col) + abs(b.row - a.row)

        val rplus = (min(a.row, b.row) until max(a.row, b.row)).count { it in emptyRows }
        val cplus = (min(a.col, b.col) until max(a.col, b.col)).count { it in emptyCols }

        return d.toLong() + rplus * (emptySpace - 1) + cplus * (emptySpace - 1)
    }

    fun emptyRows(): List<Int> {
        return rows.filter {
            '#' !in grid[it]
        }
    }

    fun emptyCols(): List<Int> {
        return cols.filter { col ->
            rows.all { grid[it][col] != '#' }
        }
    }

    fun findGalaxies(): List<Coord> {
        return buildList {
            rows.forEach { row ->
                cols.forEach { col ->
                    if (grid[row][col] == '#') {
                        add(Coord(row, col))
                    }
                }
            }
        }
    }


}

fun main() {
    val day = Day11.parseInput("input")
    println("Part 1: ${day.part1()}")
    println("Part 2: ${day.part2()}")
}