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

data class Node(val pos: Coord, val last3Moves: List<Direction> = listOf()) {
}

data class Day17(val lines: List<String>) {
    val rows = lines.size
    val cols = lines[0].length

    companion object {
        fun parseInput(path: String) = parseLines(readInput(path))
        fun readInput(path: String) = javaClass.getResource(path)!!.readText().lines()
        fun parseLines(lines: List<String>) = Day17(lines)
    }


    fun part1(): Int {
        val start = Node(Coord(0, 0))
        // map of best score from start to n
        val gScore = mutableMapOf<Node, Int>()
        gScore[start] = 0

        val queue = mutableSetOf(start)

        while (queue.isNotEmpty()) {
            val node = queue.first()
            queue.remove(node)

            val neighbours = getNeighbours(node)
            neighbours.forEach {
                queue.add(it)
            }

        }
        return 42
    }

    fun getNeighbours(node: Node): List<Node> {
        return Direction.entries.filter {
            // no u turns
            node.last3Moves.isEmpty() || node.last3Moves.last().opposite != it
        }.filter { move ->
            // no going straight more than 3 times
            node.last3Moves.size < 3 || !node.last3Moves.all { it == move }
        }.filter {
            // no going outside of the grid
            node.pos.row + it.drow in 0 until rows && node.pos.col + it.dcol in 0 until cols
        }.map {
            Node(node.pos + it, node.last3Moves.takeLast(2) + listOf(it))
        }
    }

    fun heuristic(): Int {
        return 100000 // I don't really have a good overestimating heuristic because I don't know how to deal with the 3 in a row constraint
    }

    fun part2(): Int {
        return 0
    }

}

fun main() {
    val day = Day17.parseInput("input")
    //8620 is too high
    println("Part 1: ${day.part1()}")
    println("Part 2: ${day.part2()}")
}