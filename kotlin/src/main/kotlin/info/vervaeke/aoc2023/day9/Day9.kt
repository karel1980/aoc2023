package info.vervaeke.aoc2023.day9

data class Node(val left: String, val right: String)

data class Day9(val input: List<List<Int>>) {

    companion object {
        fun parseInput(path: String) = parseLines(readInput(path))
        fun readInput(path: String) = javaClass.getResource(path)!!.readText().lines()
        fun parseLines(lines: List<String>): Day9 {
            return Day9(lines.map { it.split(" ").map { it.toInt() } })
        }
    }

    fun part1(): Int {
        return input.sumOf { findNextValue(it) }
    }

    fun part2(): Int {
        return 42
    }

    fun findNextValue(line: List<Int>): Int {
        var current = line
        val lines = buildList {
            add(current)
            while (!current.all { it == 0 }) {
                current = (0 until current.size - 1).map { current[it + 1] - current[it] }
                add(current)
            }
        }
//        println(lines)

        return lines.sumOf { it.last() }
    }


}


fun main() {
    val day = Day9.parseInput("input")
    println("Part 1: ${day.part1()}")
    println("Part 2: ${day.part2()}")
}