package info.vervaeke.aoc2023.day15

data class Day15(val lines: List<String>) {

    companion object {
        fun parseInput(path: String) = parseLines(readInput(path))
        fun readInput(path: String) = javaClass.getResource(path)!!.readText().lines()
        fun parseLines(lines: List<String>): Day15 {
            return Day15(lines)
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
    val day = Day15.parseInput("input")
    println("Part 1: ${day.part1()}")
    println("Part 2: ${day.part2()}")
}