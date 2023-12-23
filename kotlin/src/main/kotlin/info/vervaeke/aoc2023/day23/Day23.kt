package info.vervaeke.aoc2023.day23

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
        return 42
    }

    fun findAllUniquePaths(): List<Int> {
        val results = mutableListOf<List<Coord>>()
        findPathsRecursive(results = results)

        return results.map { it.size - 1 }
    }

    private fun findPathsRecursive(currentPath: MutableList<Coord> = mutableListOf(start), results: MutableList<List<Coord>> = mutableListOf()) {
        if (currentPath.last() == goal) {
            println("found path of length ${currentPath.size}")
            results.add(currentPath.toList())
            return
        }

        getValidNeighbours(currentPath.last()).filter {
            it !in currentPath
        }.forEach {
            currentPath.add(it)
            findPathsRecursive(currentPath, results)
            currentPath.removeLast()
        }
    }

    private fun getValidNeighbours(pos: Coord): List<Coord> {
        return when (symbol(pos)) {
            '.' -> listOf(pos + Direction.DOWN, pos + Direction.RIGHT, pos + Direction.LEFT, pos + Direction.UP)
            '>' -> listOf(pos + Direction.RIGHT)
            '<' -> listOf(pos + Direction.LEFT)
            '^' -> listOf(pos + Direction.UP)
            'v' -> listOf(pos + Direction.DOWN)
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

}

fun main() {
    val day = Day23.parseInput("input")
    println("Part 1: ${day.part1()}")
    //1290 is too low
    println("Part 2: ${day.part2()}")
}