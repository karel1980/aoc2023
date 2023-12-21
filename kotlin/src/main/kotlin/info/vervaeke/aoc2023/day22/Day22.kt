package info.vervaeke.aoc2023.day22

data class Day22(val lines: List<String>) {

    companion object {
        fun parseInput(path: String) = parseLines(readInput(path))
        fun readInput(path: String) = javaClass.getResource(path)!!.readText().lines()
        fun parseLines(lines: List<String>): Day22 {
            return Day22(lines)
        }
    }

    fun part1(): Long {
        return 42
    }

    fun part2(): Long {
        return 42
    }

}

fun main() {
    val day = Day22.parseInput("input")
    println("Part 1: ${day.part1()}")
    println("Part 2: ${day.part2()}")
}