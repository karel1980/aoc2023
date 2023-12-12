package info.vervaeke.aoc2023.day12

import java.util.*
import java.util.regex.Pattern

data class Row(val spec: String, val brokenGroups: List<Int>) {
    val unknowns = spec.indices.filter { spec[it] == '?' }.mapIndexed { idx, pos -> pos to idx }.toMap()

    val pattern = Pattern.compile("\\.+")

    val options = ".#"

    fun countArrangements(): Int {
        if (unknowns.size == 0) {
            return 1
        }

        val numberOfCombinations = Math.pow(2.toDouble(), unknowns.size.toDouble()).toInt()
        return (0 until numberOfCombinations).count { n ->
            val bitset = createBitSet(n)
            val instance = spec.indices.map {
                spec[it].takeIf { it != '?' } ?: options[if (bitset[unknowns[it]!!]) {
                    0
                } else {
                    1
                }]
            }.joinToString("") { it.toString() }
            matchesInstance(instance)
        }
    }

    fun unfold(): Row {
        return Row(listOf(spec, spec, spec, spec).joinToString("?") { it }, (0 until 5).flatMap { brokenGroups })
    }

    fun getNthBit(i: Int, n: Int): Int {
        val mask = 1 shl n
        return (i and mask) shr n
    }

    fun matchesInstance(instance: String): Boolean {
        val parts = instance.split(pattern).filter { !it.isEmpty() }
        return parts.map { it.length }.equals(brokenGroups)
    }
}

data class Day12(val rows: List<Row>) {
    companion object {
        fun parseInput(path: String) = parseLines(readInput(path))
        fun readInput(path: String) = javaClass.getResource(path)!!.readText().lines()
        fun parseLines(lines: List<String>): Day12 {
            return Day12(lines.map { parseRow(it) })
        }

        fun parseRow(line: String) = line.split(" ").let {
            Row(it[0], it[1].split(",").map { it.toInt() })
        }
    }

    fun part1(): Int {
        return rows.sumOf { it.countArrangements() }
    }

    fun part2(): Int {
        return rows.map { it.unfold() }.sumOf { it.countArrangements() }
    }


}

fun createBitSet(n: Int): BitSet {
    return BitSet().apply {
        n.toBigInteger().toString(2).reversed().forEachIndexed { idx, c ->
            if (c == '1') {
                set(idx)
            }
        }
    }
}

fun main() {
    val day = Day12.parseInput("input")
    println("Part 1: ${day.part1()}")
    println("Part 2: ${day.part2()}")
}