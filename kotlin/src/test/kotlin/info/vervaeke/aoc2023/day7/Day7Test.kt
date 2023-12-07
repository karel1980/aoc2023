package info.vervaeke.aoc2023.day7

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day7Test {

    private val day7 = Day7.parseInput("sample")

    @Test
    fun part1() {
        assertThat(day7.part1())
            .isEqualTo(6440)
    }

    @Test
    fun `hand order`() {
        assertThat(Hand("AAAAA") > Hand("KKKKK"))
            .isEqualTo(true)
    }

    @Test
    fun part1_real() {
        assertThat(Day7.parseInput("input").part1())
            .isEqualTo(251287184)
    }

    @Test
    fun part2_real() {
        assertThat(Day7.parseInput("input").part2())
            .isEqualTo(250757288)
    }

    @Test
    fun `hand type`() {
        assertThat(Hand("AAAAA").handType())
            .isEqualTo(HandType.FIVE)
        assertThat(Hand("A3AAA").handType())
            .isEqualTo(HandType.FOUR)
        assertThat(Hand("A32AA").handType())
            .isEqualTo(HandType.THREE)
        assertThat(Hand("A33AA").handType())
            .isEqualTo(HandType.FULL)
        assertThat(Hand("AAKK3").handType())
            .isEqualTo(HandType.TWO_PAIR)
        assertThat(Hand("TT234").handType())
            .isEqualTo(HandType.PAIR)
        assertThat(Hand("34567").handType())
            .isEqualTo(HandType.HIGH)
    }

    @Test
    fun part2() {
        assertThat(day7.part2())
            .isEqualTo(5905)
    }

    @Test
    fun bestHandType() {
        assertThat(Hand("T55J5").bestHandType())
            .isEqualTo(HandType.FOUR)
        assertThat(Hand("KK677").bestHandType())
            .isEqualTo(HandType.TWO_PAIR)
        assertThat(Hand("KTJJT").bestHandType())
            .isEqualTo(HandType.FOUR)
        assertThat(Hand("QQQJA").bestHandType())
            .isEqualTo(HandType.FOUR)
    }

    @Test
    fun enumSorting() {
        assertThat(HandType.TWO_PAIR < HandType.FOUR)
            .isTrue()
    }

    @Test
    fun sortHandTypes() {
        assertThat(day7.handBids.sortedBy { it.hand.bestHandType() }.map { it.hand.toString() })
            .isEqualTo(
                listOf(
                    "32T3K", "KK677", "T55J5", "KTJJT", "QQQJA"
                )
            )
    }

    @Test
    fun jokerIndividualIsLowest() {
        val handBids = listOf(HandBid(Hand("JKKK2"), 100), HandBid(Hand("QQQQ2"), 200))
        assertThat(handBids.sortedWith(Joker()).map { it.hand.toString() })
            .isEqualTo(
                listOf(
                    "JKKK2", "QQQQ2"
                )
            )
    }

    @Test
    fun part2Sorting() {
        val hands = day7.handBids.sortedWith(Joker())
            .map { it.hand }

        hands.forEach {
            println("Best handtype for $it is ${it.bestHandType()}")
        }

        assertThat(hands)
            .isEqualTo(
                listOf(
                    Hand("32T3K"),
                    Hand("KK677"),
                    Hand("T55J5"),
                    Hand("QQQJA"),
                    Hand("KTJJT")
                )
            )

    }
}