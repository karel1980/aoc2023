package info.vervaeke.aoc2023.day18

import kotlin.math.max
import kotlin.math.min

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

}

data class Coord(val row: Int, val col: Int) {
    infix operator fun plus(dir: Direction): Coord {
        return Coord(row + dir.drow, col + dir.dcol)
    }
}

data class Day18(val digs: List<Dig>) {
    val edgePoints = allEdgePoints()

    companion object {
        fun parseInput(path: String) = parseLines(readInput(path))
        fun readInput(path: String) = javaClass.getResource(path)!!.readText().lines()
        fun parseLines(lines: List<String>) = Day18(lines.map {
            it.split(" ")
        }.map {
            Dig(Direction.byLetter(it[0]), it[1].toInt(), it[2].substring(2, it[2].length - 1))
        })

    }

    fun part1(): Int {
        val points = getLeftEdgePoints()
        return points.map {
            // these are all points that are potentially inside
            // but we must exclude points that are already inside
            val point = it.first
            val startCol = point.col

            // this is the wall that we hit
            val endCol = min(it.second.first.col, edgePoints.filter { it.row == point.row && it.col > point.col }.minOf { it.col })

            point to endCol - startCol - 1
        }.sortedBy { (point, count) ->
            point.row
        }. map { (point, count) ->
            println("$point -- $count")
            count
        }
        .sum() + allEdgePoints().size
    }

    fun getLeftEdgePoints(): List<Pair<Coord, Pair<Coord, Coord>>> {
        // using raycasting -> find each point that is left of an inside part and the first part the ray would hit
        val verticalPairs = edgePoints.dropLast(1).zip(edgePoints.drop(1))
            .filter { it.first.col == it.second.col }

        return edgePoints.map { point ->
            // for every point, the list of walls that our ray (pointed to the right) would hit
            point to verticalPairs.filter {
                val top = min(it.first.row, it.second.row)
                val bottom = max(it.first.row, it.second.row)
                it.first.col > point.col && top < point.row && bottom >= point.row
            }
        }.filter {
            // odd number of walls -> interior
            it.second.size % 2 == 1
        }.map {
            // find the leftmost of these walls. That one determines how many (interior) cells to dig
            val point = it.first
            val wall = it.second.minBy { it.first.col }

            point to wall
        }
    }

    fun part2() = 42

    fun allEdgePoints(): List<Coord> {
        var current = Coord(0, 0)
        return buildList {
            digs.forEach { dig ->
                (0 until dig.amount).forEach {
                    current += dig.dir
                    add(current)
                }
            }
        }
    }

    fun edgePoints(): Int {
        return digs.sumOf { it.amount }
    }


}

fun main() {
    val day = Day18.parseInput("input")
    //6160 is too low
    println("Part 1: ${day.part1()}")
    println("Part 2: ${day.part2()}")
}