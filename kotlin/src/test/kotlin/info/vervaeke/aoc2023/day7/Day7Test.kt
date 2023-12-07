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
        assertThat("AAAAA".toHand() > "KKKKK".toHand())
            .isEqualTo(true)
    }

    @Test
    fun `hand type`() {
        assertThat("AAAAA".toHand().handType())
            .isEqualTo(HandType.FIVE)
        assertThat("A3AAA".toHand().handType())
            .isEqualTo(HandType.FOUR)
        assertThat("A32AA".toHand().handType())
            .isEqualTo(HandType.THREE)
        assertThat("A33AA".toHand().handType())
            .isEqualTo(HandType.FULL)
        assertThat("AAKK3".toHand().handType())
            .isEqualTo(HandType.TWO_PAIR)
        assertThat("TT234".toHand().handType())
            .isEqualTo(HandType.PAIR)
        assertThat("34567".toHand().handType())
            .isEqualTo(HandType.HIGH)
    }

    @Test
    fun part2() {
        assertThat(day7.part2())
            .isEqualTo(5905)
    }

    @Test
    fun bestHandType() {
        assertThat("T55J5".toHand().bestHandType())
            .isEqualTo(HandType.FOUR)
        assertThat("KK677".toHand().bestHandType())
            .isEqualTo(HandType.TWO_PAIR)
        assertThat("KTJJT".toHand().bestHandType())
            .isEqualTo(HandType.FOUR)
        assertThat("QQQJA".toHand().bestHandType())
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
            .isEqualTo(listOf(
                "32T3K", "KK677", "T55J5", "KTJJT", "QQQJA"
            ))
    }

    @Test
    fun jokerIndividualIsLowest() {
        val handBids = listOf(HandBid("JKKK2".toHand(), 100), HandBid("QQQQ2".toHand(), 200))
        assertThat(handBids.sortedWith(Joker()).map { it.hand.toString() })
            .isEqualTo(listOf(
                "JKKK2", "QQQQ2"
            ))
    }

    @Test
    fun part2Sorting() {
        val hands = day7.handBids.sortedWith(Joker())
            .map { it.hand }

        hands.forEach {
            println("Best handtype for $it is ${it.bestHandType()}")
        }

        assertThat(hands)
            .isEqualTo(listOf(
                "32T3K".toHand(),
                "KK677".toHand(),
                "T55J5".toHand(),
                "QQQJA".toHand(),
                "KTJJT".toHand()
            ))

    }
}