package info.vervaeke.aoc2023.day4

import java.util.regex.Pattern
import kotlin.math.pow

class Card(val id: Int, val winning: List<Int>, val yours: List<Int>, var count: Int = 1) {
    val matches = winning.toSet().intersect(yours.toSet())
    val score = if (matches.isEmpty()) {
        0
    } else {
        2.toDouble().pow(matches.size - 1).toInt()
    }

    override fun toString(): String {
        return "Card(id=$id, winning=$winning, yours=$yours, count=$count, matches=$matches, score=$score)"
    }


}

class Day4(val lines: List<String>) {
    companion object {
        fun readInput(path: String) = parseLines(javaClass.getResource(path)!!.readText().lines())
        fun parseLines(lines: List<String>) = lines.map(::parseLine)
        fun parseLine(line: String) = line
    }

    fun part1(): Int {
        return lines.map { it.toCard() }
            .map { it.score }
            .sum()
    }

    fun part2(): Int {
        val cards = lines.map { it.toCard() }
        val cardsById = cards.associateBy { it.id }

        cards.forEach { card ->
            val score = card.matches.size
            val count = card.count
            (card.id + 1..card.id + score).forEach {
                if (it in cardsById.keys) {
                    cardsById[it]!!.count += count
                }
            }
        }

        return cards.sumOf { it.count }
    }
}

private fun String.toCard(): Card {
    val parts = this.split(":")
    val nums = parts[1].trim().split("|")

    val id = parts[0].split(Pattern.compile(" +"))[1].toInt()
    val winning = nums[0].trim().split(Pattern.compile(" +")).map { it.toInt() }
    val yours = nums[1].trim().split(Pattern.compile(" +")).map { it.toInt() }
    return Card(id, winning, yours)
}


fun main() {
    val sample = Day4.readInput("sample")
    val input = Day4.readInput("input")

    println("Part 1 sample: ${Day4(sample).part1()}")
    println("Part 1 real: ${Day4(input).part1()}")
    println("Part 2 sample: ${Day4(sample).part2()}")
    println("Part 2 real: ${Day4(input).part2()}")
}

