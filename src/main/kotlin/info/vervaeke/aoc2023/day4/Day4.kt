package info.vervaeke.aoc2023.day4

class Day4(val lines: List<String>) {
    companion object {
        fun readInput(path: String) = parseLines(Day4::class.java.getResource(path)!!.readText().lines())
        fun parseLines(lines: List<String>) = lines.map(::parseLine)
        fun parseLine(line: String) = line
    }

    fun part1(): Int {
        return 42
    }

    fun part2(): Int {
        return 42
    }

}


fun main() {
    val sample = Day4.readInput("sample")
    val input = Day4.readInput("input")

    println("Part 1 sample: ${Day4(sample).part1()}")
    println("Part 1 real: ${Day4(input).part1()}")
    println("Part 2 sample: ${Day4(sample).part2()}")
    println("Part 2 real: ${Day4(sample).part2()}")
}

