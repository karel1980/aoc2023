package info.vervaeke.aoc2023.day23

data class Day23(val lines: List<String>) {

    companion object {
        fun parseInput(path: String) = parseLines(readInput(path))
        fun readInput(path: String) = javaClass.getResource(path)!!.readText().lines()
        fun parseLines(lines: List<String>): Day23 {
            return Day23(lines)
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
    val day = Day23.parseInput("input")
    println("Part 1: ${day.part1()}")
    //1290 is too low
    println("Part 2: ${day.part2()}")
}