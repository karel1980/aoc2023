package info.vervaeke.aoc2023.day18

import info.vervaeke.aoc2023.day18.Direction.*
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
        return calculateSurface1()
    }

    fun calculateSurface1(): Int {
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
        }.map { (point, count) ->
            //            println("$point -- $count")
            count
        }
            .sum() + allEdgePoints().size
    }

    fun getLeftEdgePoints(): List<Pair<Coord, Pair<Coord, Coord>>> {
        // using raycasting -> find each point that is left of an inside part and the first part the ray would hit
        val verticalPairs = getEdges(edgePoints)
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

    fun part2(): Int {
        return swap().calculateSurface2()
    }

    fun calculateSurface2(): Int {
        val cornerPoints = allCornerPoints()
        val edges = getEdges(cornerPoints)
        val verticalEdges = getVerticalEdges(edges)
        val consideredPoints = calculateConsideredPoints(cornerPoints)
        val pointsAndEdges = rayCastEdgeDetection(consideredPoints, edges, verticalEdges)
        val insidePoints = keepOnlyInsidePoints(pointsAndEdges)
        val pointsInside = pointsAndRowWidths(insidePoints)

        val rows = consideredPoints.map { it.row }.distinct().sorted()
        val rowToNext = rows.dropLast(1).zip(rows.drop(1)).toMap()
        return pointsInside.sumOf { (point, cols) ->
            val height = point.row - rowToNext[point.row]!! + 1
            val width = cols
            height * width
        }

    }

    fun pointsAndRowWidths(insidePoints: List<Pair<Coord, List<Pair<Coord, Coord>>>>) =
        insidePoints.map { (coord, edges) ->
            // for every coordinate, calculate how many squares are inside
            coord to edges.map { it.first.col }.min() - coord.col - 1
        }

    fun keepOnlyInsidePoints(pointsAndEdges: List<Pair<Coord, List<Pair<Coord, Coord>>>>) =
        pointsAndEdges.filter { (coord, edges) ->
            // only keep coordinates that are inside
            edges.size % 2 == 1
        }

    fun rayCastEdgeDetection(
        consideredPoints: List<Coord>,
        edges: List<Pair<Coord, Coord>>,
        verticalEdges: List<Pair<Coord, Coord>>,
    ) = getPointsNotInEdge(consideredPoints, edges).map {
        val edgesHitByRayCast = verticalEdges.filter {
            // only edges right of the current point considered
                edge ->
            it.col < edge.first.col
        }.filter {
            // count edges that our ray would hit. Note: ray goes to the right and very slightly up
                edge ->
            it.row > edge.first.row && it.row <= edge.second.row
        }
        it to edgesHitByRayCast
    }

    fun getPointsNotInEdge(
        consideredPoints: List<Coord>,
        edges: List<Pair<Coord, Coord>>,
    ) = consideredPoints.filter {
        // only keep points that are not part of the edge
        edges.none { edge -> edge.contains(it) }
    }

    fun calculateConsideredPoints(cornerPoints: List<Coord>) = cornerPoints.flatMap {
        // points to consider
        listOf(it + RIGHT)
    }.distinct().sortedBy { it.row }

    fun getVerticalEdges(edges: List<Pair<Coord, Coord>>) =
        edges.filter { it.first.col == it.second.col }
            .map {
                // vertical edges are oriented from low to high rows
                if (it.first.row < it.second.row) {
                    it
                } else {
                    it.second to it.first
                }
            }

    fun getEdges(cornerPoints: List<Coord>) = cornerPoints.indices.map { cornerPoints[it] to cornerPoints[(it + 1) % cornerPoints.size] }

    fun swap(): Day18 {
        return Day18(digs.map { it.swap() })
    }

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

    fun allCornerPoints(): List<Coord> {
        var current = Coord(0, 0)
        return buildList {
            digs.forEach { dig ->
                current = Coord(current.row + dig.dir.drow * dig.amount, current.col + dig.dir.dcol * dig.amount)
                add(current)
            }
        }
    }

    fun edgePoints(): Int {
        return digs.sumOf { it.amount }
    }


}

private fun Pair<Coord, Coord>.contains(coord: Coord): Boolean {
    if (this.first.row == this.second.row) {
        return coord.row == this.first.row && coord.col in this.first.col..this.second.col
    }
    if (this.first.col == this.second.col) {
        return coord.col == this.first.col && coord.row in this.first.row..this.second.row
    }
    return false
}

fun main() {
    val day = Day18.parseInput("input")
    //6160 is too low
    println("Part 1: ${day.part1()}")
    //1327053636 is too low
    println("Part 2: ${day.part2()}")
}