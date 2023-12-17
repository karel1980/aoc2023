package info.vervaeke.aoc2023.day17

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

data class Node(val pos: Coord, val dir: Direction? = null)

data class Day17(val lines: List<String>) {
    val rows = lines.size
    val cols = lines[0].length

    companion object {
        fun parseInput(path: String) = parseLines(readInput(path))
        fun readInput(path: String) = javaClass.getResource(path)!!.readText().lines()
        fun parseLines(lines: List<String>) = Day17(lines)
    }

    fun part1() = aStarSolution(this::getNeighbours)
    fun part2() = aStarSolution(this::getNeighboursPart2)

    fun aStarSolution(getNeighboursFn: (Node) -> List<Node>): Int {
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
            if (current.pos == goal) {
                return gScore[current] ?: 1_000_000
            }

            queue.remove(current)

            val neighbours = getNeighboursFn(current)
            neighbours.forEach { neighbour ->
                val tentativeGScore = (gScore[current] ?: 1_000_000) + cost(current.pos, neighbour.pos)
                if (tentativeGScore < (gScore[neighbour] ?: 1_000_000)) {
                    cameFrom[neighbour] = current
                    gScore[neighbour] = tentativeGScore
                    fScore[neighbour] = tentativeGScore + heuristic(neighbour)

                    if (neighbour !in queue) {
                        queue.add(neighbour)
                    }
                }
            }
        }
        TODO("oops, could not reach the goal")
    }

    private fun cost(a: Coord, b: Coord): Int {
        val drow = sign(b.row - a.row)
        val dcol = sign(b.col - a.col)
        var n = a
        var cost = 0
        var c = 1
        while (n != b) {
            n = Coord(a.row + c * drow, a.col + c * dcol)
            cost += cost(n)
            c++
        }
        return cost
    }

    fun cost(c: Coord): Int {
        return lines[c.row][c.col].toString().toInt()
    }

    fun sign(n: Int): Int {
        if (n == 0) {
            return 0
        }
        if (n < 0) {
            return -1
        }
        return 1
    }

    fun getNeighbours(node: Node): List<Node> {
        return Direction.entries.filter {
            // no u turns
            it.opposite != node.dir
        }.filter {
            // can't go in the same direction 2 consecutive times
            it != node.dir
        }.flatMap { dir ->
            (1 until 4).map {
                Node(Coord(node.pos.row + dir.drow * it, node.pos.col + dir.dcol * it), dir)
            }.filter {
                it.pos.row in 0 until rows && it.pos.col in 0 until cols
            }
        }
    }

    fun getNeighboursPart2(node: Node): List<Node> {
        return Direction.entries.filter {
            // no u turns
            it.opposite != node.dir
        }.filter {
            // can't go in the same direction 2 consecutive times
            it != node.dir
        }.flatMap { dir ->
            (4..10).map {
                Node(Coord(node.pos.row + dir.drow * it, node.pos.col + dir.dcol * it), dir)
            }.filter {
                it.pos.row in 0 until rows && it.pos.col in 0 until cols
            }
        }
    }

    fun heuristic(node: Node): Int {
        // heuristic should never overestimate
        // use manhattan distance, as there are no 0s in the grid
        return (rows - node.pos.row) + (cols - node.pos.col)
    }
}

fun main() {
    val day = Day17.parseInput("input")
    println("Part 1: ${day.part1()}")
    println("Part 2: ${day.part2()}")
}