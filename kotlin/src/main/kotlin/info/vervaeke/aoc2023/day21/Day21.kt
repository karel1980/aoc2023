package info.vervaeke.aoc2023.day21

data class Day21(val lines: List<String>) {
    companion object {
        fun parseInput(path: String) = parseLines(readInput(path))
        fun readInput(path: String) = javaClass.getResource(path)!!.readText().lines()
        fun parseLines(lines: List<String>): Day21 {
            return Day21(lines)
        }
    }

    fun part1(): Long {
        return lines.size.toLong()
    }

    fun part2(): Long {
        return lines.size.toLong()
    }


}


fun main() {
    val day = Day21.parseInput("input")
    println("Part 1: ${day.part1()}")
    println("Part 2: ${day.part2()}")
}