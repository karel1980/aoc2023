package info.vervaeke.aoc2023.day24

data class Day24(val lines: List<String>) {
    val rows = lines.size
    val cols = lines[0].length

    companion object {
        fun parseInput(path: String) = parseLines(readInput(path))
        fun readInput(path: String) = javaClass.getResource(path)!!.readText().lines()
        fun parseLines(lines: List<String>): Day24 {
            return Day24(lines)
        }
    }

    fun part1(): Int {
        return 42
    }

    fun part2(): Int {
        return 42
    }


}

fun main() {
    val day = Day24.parseInput("input")
    println("Part 1: ${day.part1()}")
    println("Part 2: ${day.part2()}")
}