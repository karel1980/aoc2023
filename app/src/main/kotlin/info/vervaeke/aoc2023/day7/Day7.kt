package info.vervaeke.aoc2023.day7

class Day7(val lines: List<String>) {

    companion object {
        fun parseInput(path: String) = parseLines(readInput(path))
        fun readInput(path: String) = javaClass.getResource(path)!!.readText().lines()
        fun parseLines(lines: List<String>) = Day7(lines)
    }

    fun part1(): Long {
        return 42
    }

    fun part2(): Long {
        return 42
    }

}