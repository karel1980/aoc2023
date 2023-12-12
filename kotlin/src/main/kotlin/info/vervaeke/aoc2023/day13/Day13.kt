package info.vervaeke.aoc2023.day13

data class Day13(val grid: List<String>) {
    companion object {
        fun parseInput(path: String) = parseLines(readInput(path))
        fun readInput(path: String) = javaClass.getResource(path)!!.readText().lines()
        fun parseLines(lines: List<String>): Day13 {
            return Day13(lines)
        }
    }

    fun part1(): Long {
        return 42L
    }

    fun part2(): Long {
        return 42L
    }
}

fun main() {
    val day = Day13.parseInput("input")
    println("Part 1: ${day.part1()}")
    println("Part 2: ${day.part2()}")
}