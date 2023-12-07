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
            .isEqualTo(42)
    }
}