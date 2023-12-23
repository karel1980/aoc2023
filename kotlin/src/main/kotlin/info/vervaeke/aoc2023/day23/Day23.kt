package info.vervaeke.aoc2023.day23

import info.vervaeke.aoc2023.day23.Direction.*

data class Coord(val row: Int, val col: Int) {
    infix operator fun plus(dir: Direction) =
        Coord(this.row + dir.drow, this.col + dir.dcol)
}

enum class Direction(val drow: Int, val dcol: Int) {
    UP(-1, 0),
    DOWN(1, 0),
    LEFT(0, -1),
    RIGHT(0, 1)
}

data class Node(val pos: Coord, val neighbours: List<Coord>)
data class Edge(val from: Coord, val to: Coord, val length: Int = 1)

data class BestOf(var best: Int)

data class Day23(val lines: List<String>) {
    val rows = lines.size
    val cols = lines[0].length
    val start = Coord(0, lines[0].indexOf('.'))
    val goal = Coord(lines.size - 1, lines.last().indexOf('.'))

    companion object {
        fun parseInput(path: String) = parseLines(readInput(path))
        fun readInput(path: String) = javaClass.getResource(path)!!.readText().lines()
        fun parseLines(lines: List<String>): Day23 {
            return Day23(lines)
        }
    }

    fun part1(): Int {
        return findAllUniquePaths().max()
    }

    fun part2(): Int {
        return findMaximumPathSize()
    }

    fun replaceSlopes(): Day23 {
        return Day23(lines.map {
            it
                .replace(">", ".")
                .replace("<", ".")
                .replace("^", ".")
                .replace("v", ".")
        })
    }

    fun findAllUniquePaths(): List<Int> {
        val results = mutableListOf<List<Coord>>()
        findPathsRecursive(results = results)

        return results.map { it.size - 1 }
    }

    fun findMaximumPathSize(): Int {
        val posToNeighbours = createPosToNeighbours()

        val best = BestOf(0)
        findMaximumPathSizeRecursive(mutableListOf(start), posToNeighbours, best = best)

        return best.best
    }

    fun createPosToNeighbours(): MutableMap<Coord, MutableList<Pair<Coord, Int>>> {
        val graph = simplifyGraph(createGraph())

        val posToNeighbours = mutableMapOf<Coord, MutableList<Pair<Coord, Int>>>()
        graph.forEach {
            if (it.from !in posToNeighbours) {
                posToNeighbours[it.from] = mutableListOf()
            }
            posToNeighbours[it.from]!!.add(it.to to it.length)
            if (it.to !in posToNeighbours) {
                posToNeighbours[it.to] = mutableListOf()
            }
            posToNeighbours[it.to]!!.add(it.from to it.length)
        }
        return posToNeighbours
    }

    private fun findMaximumPathSizeRecursive(
        currentPath: MutableList<Coord>,
        posToNeighbours: MutableMap<Coord, MutableList<Pair<Coord, Int>>>,
        currentPathLength: Int = 0,
        best: BestOf = BestOf(0),
    ) {
        if (currentPath.last() == goal) {
            if (currentPathLength > best.best) {
                best.best = currentPathLength
            }
            return
        }

        val neighbours = posToNeighbours[currentPath.last()]!!
            .filter { it.first !in currentPath }

        neighbours.forEach {
            currentPath.add(it.first)
            findMaximumPathSizeRecursive(currentPath, posToNeighbours, currentPathLength + it.second, best = best)
            currentPath.removeLast()
        }
    }

    private fun findPathsRecursive(
        currentPath: MutableList<Coord> = mutableListOf(start),
        results: MutableList<List<Coord>> = mutableListOf(),
        bestOf: BestOf = BestOf(0),
    ) {
        if (currentPath.last() == goal) {
            if (currentPath.size - 1 > bestOf.best) {
                bestOf.best = currentPath.size - 1
                println("Found a better one:" + bestOf.best)
            }
            results.add(currentPath.toList())
            return
        }

        getValidNeighbours(currentPath.last()).filter {
            it !in currentPath
        }.forEach {
            currentPath.add(it)
            findPathsRecursive(currentPath, results, bestOf)
            currentPath.removeLast()
        }
    }

    private fun getValidNeighbours(pos: Coord): List<Coord> {
        return when (symbol(pos)) {
            '.' -> listOf(pos + DOWN, pos + RIGHT, pos + LEFT, pos + UP)
            '>' -> listOf(pos + RIGHT)
            '<' -> listOf(pos + LEFT)
            '^' -> listOf(pos + UP)
            'v' -> listOf(pos + DOWN)
            '#' -> listOf()
            else -> TODO()
        }
            .filter { isInsideGrid(it) }
    }

    private fun symbol(pos: Coord): Char {
        return lines[pos.row][pos.col]
    }

    private fun isInsideGrid(it: Coord): Boolean {
        return it.row in 0 until rows && it.col in 0 until cols

    }

    fun createGraph(): List<Edge> {
        if (lines.any { '>' in it }) {
            return replaceSlopes().createGraph()
        }
        val result = mutableListOf<Edge>()
        repeat(rows) { row ->
            repeat(cols) { col ->
                val pos = Coord(row, col)
                if (symbol(pos) == '.') {
                    listOf(RIGHT, DOWN).map {
                        pos + it
                    }.filter { isInsideGrid(it) }
                        .filter { symbol(it) == '.' }
                        .forEach { result.add(Edge(pos, it, 1)) }


                }
            }
        }
        // create nodes out of every '.'
        // for every node, add its neighbours

        return result
    }

    fun simplifyGraph(edges: List<Edge>): List<Edge> {
        val result = edges.toMutableList()

        var done = false
        while (!done) {
            // find the first edge that can be replaced
            // and edge can be replaced if its start is only used as an end once
            // in that case, remove the edge and increase the size of the 2nd edge

            val pos = result.flatMap {
                listOf(it.from, it.to)
            }.find {
                matchesExactlyTwoEdges(it, result)
            }

            if (pos == null) {
                done = true
                continue
            }

            val matchingEdges = findMatchingEdges(pos, result)
            if (matchingEdges.size != 2) TODO()

            val newEdgeNodes = matchingEdges
                .flatMap { listOf(it.from, it.to) }
                .filter { it != pos }
                .toList()
            if (newEdgeNodes.size != 2) TODO()

            val newEdge = Edge(newEdgeNodes[0], newEdgeNodes[1], matchingEdges[0].length + matchingEdges[1].length)
            result.removeAll(matchingEdges)
            result.add(newEdge)
        }

        return result
    }

    private fun matchesExactlyTwoEdges(pos: Coord, result: MutableList<Edge>): Boolean {
        return findMatchingEdges(pos, result).size == 2
    }

    private fun findMatchingEdges(
        pos: Coord,
        result: MutableList<Edge>,
    ) = result.filter { it.from == pos || it.to == pos }

}

fun main() {
    val day = Day23.parseInput("input")
    println("Part 1: ${day.part1()}")
    println("Part 2: ${day.part2()}")
}