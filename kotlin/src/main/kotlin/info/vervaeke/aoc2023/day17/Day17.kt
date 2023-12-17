package info.vervaeke.aoc2023.day17

import info.vervaeke.aoc2023.day17.Direction.RIGHT

data class Coord(val row: Int, val col: Int) {
    infix operator fun plus(dir: Direction): Coord {
        return Coord(row + dir.drow, col + dir.dcol)
    }
}

enum class Direction(val drow: Int, val dcol: Int) {
    // Putting right and down first because that will prioritize going in the right direction
    RIGHT(0, 1),
    DOWN(1, 0),
    UP(-1, 0),
    LEFT(0, -1);

    val opposite: Direction
        get() = entries.first { drow == -it.drow && dcol == -it.dcol }

}

data class Node(val pos: Coord, val dir: Direction = Direction.RIGHT, val count: Int = 0)

data class Day17(val lines: List<String>) {
    val rows = lines.size
    val cols = lines[0].length

    companion object {
        fun parseInput(path: String) = parseLines(readInput(path))
        fun readInput(path: String) = javaClass.getResource(path)!!.readText().lines()
        fun parseLines(lines: List<String>) = Day17(lines)
    }


    fun part1(): Int {
        // y u so slow?
        println("rows and cols: $rows $cols")

        val start = Node(Coord(0, 0))

        // current best score to get from start to n
        val gScore = mutableMapOf(start to 0)
        // current estimate to get from start to n
        val fScore = mutableMapOf(start to heuristic(start))
        val queue = mutableSetOf(start)
        val cameFrom = mutableMapOf<Node, Node>()

        val goal = Coord(rows - 1, cols - 1)

        var count = 0
        while (queue.isNotEmpty()) {
            val current = queue.minBy { fScore[it] ?: 1_000_000 }
//            println("current: $current.pos")
            if (current.pos == goal) {
                return gScore[current] ?: 1_000_000
            }

            queue.remove(current)

            val neighbours = getNeighbours(current)
//            println("neighbours: $neighbours")
            neighbours.forEach { neighbour ->
                val tentativeGScore = (gScore[current] ?: 1_000_000) + cost(neighbour.pos)
                if (tentativeGScore < (gScore[neighbour] ?: 1_000_000)) {
                    cameFrom[neighbour] = current
                    gScore[neighbour] = tentativeGScore
                    fScore[neighbour] = tentativeGScore + heuristic(neighbour)
                    if (neighbour.pos == goal) {
                        println("current cheapest path from $start to $neighbour: ${gScore[neighbour]}")
                    }
                    if (neighbour !in queue) {
                        queue.add(neighbour)
                    }
                }
            }
        }
        TODO("oops, could not reach the goal")
    }

    private fun cost(pos: Coord): Int {
        return lines[pos.row][pos.col].toString().toInt()
    }

    fun getNeighbours(node: Node): List<Node> {
        return Direction.entries.filter {
            // no u turns
            it != node.dir.opposite
        }.filter {
            // no going straight more than 3 times
            // == only go in same directory if count < 3
            it != node.dir || node.count < 3
        }.filter {
            // no going outside of the grid
            val newPos = node.pos + it
            newPos.row in 0 until rows && newPos.col in 0 until cols
        }.map {
            Node(
                node.pos + it, it, if (node.dir == it) {
                    node.count + 1
                } else {
                    1
                }
            )
        }
    }

    fun heuristic(node: Node): Int {
        // heuristic should never overestimate
        // use manhattan distance, as there are no 0s in the grid
        return (rows - node.pos.row) + (cols - node.pos.col)
    }

    fun part2(): Int {
        return 42
    }

}

fun main() {
    val day = Day17.parseInput("input")
    println("Part 1: ${day.part1()}")
//    println("Part 2: ${day.part2()}")
}