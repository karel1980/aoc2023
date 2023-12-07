package info.vervaeke.aoc2023.day7

enum class Face(val symbol: String) {
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9"),
    TEN("T"),
    JACK("J"),
    QUEEN("Q"),
    KING("K"),
    ACE("A");

    companion object {
        fun ofSymbol(symbol: String): Face {
            return values().first { it.symbol == symbol }
        }
    }
}

enum class HandType {
    FIVE,
    FOUR,
    FULL,
    THREE,
    TWO_PAIR,
    PAIR,
    HIGH
}

data class Hand(val faces: List<Face>) : Comparable<Hand> {

    fun handType(): HandType {
        val counts = faces.groupBy { it }.mapValues { it.value.size }
        if (counts.values.max() == 5) {
            return HandType.FIVE
        }
        if (counts.values.max() == 4) {
            return HandType.FOUR
        }
        if (counts.values.max() == 3) {
            return if (2 in counts.values) {
                HandType.FULL
            } else {
                HandType.THREE
            }
        }
        if (counts.values.max() == 2) {
            return if (counts.values.filter { it == 2 }.size == 2) {
                HandType.TWO_PAIR
            } else {
                HandType.PAIR
            }
        }
        return HandType.HIGH
    }



    override fun compareTo(other: Hand): Int {
        // compare comobinations
        if (this.handType() != other.handType()) {
            return this.handType().compareTo(other.handType())
        }

        // compare faces
        faces.zip(other.faces).forEachIndexed { idx, it ->
            if (it.first != it.second) {
                return -it.first.compareTo(it.second)
            }
        }
        return 0
    }

    override fun toString(): String {
        return faces.joinToString("") { it.symbol}
    }
}

data class HandBid(val hand: Hand, val bidValue: Int)

class Day7(val handBids: List<HandBid>) {

    companion object {
        fun parseInput(path: String) = parseLines(readInput(path))
        fun readInput(path: String) = javaClass.getResource(path)!!.readText().lines()
        fun parseLines(lines: List<String>) = Day7(lines.map { it.toHandBid() })
    }

    fun part1(): Int {
        return totalWinnings()
    }

    fun totalWinnings(): Int {
        val sortedHands = handBids.sortedBy { it.hand }
            .reversed()
        sortedHands.forEach {
            println(it)
        }
        return sortedHands
            .mapIndexed { idx, it ->
                (idx + 1) * it.bidValue
            }
            .sum()

    }

    fun part2(): Long {
        return 42
    }

}

private fun String.toHandBid(): HandBid {
    val parts = this.split(" ")
    val bidValue = parts[1].toInt()

    return HandBid(parts[0].toHand(), bidValue)
}

fun String.toHand() = Hand(map {
    Face.ofSymbol(it.toString())
}.toList())

fun main() {
    val day7 = Day7.parseInput("input")
    println("Part 1: ${day7.part1()}")
    println("Part 2: ${day7.part2()}")
}