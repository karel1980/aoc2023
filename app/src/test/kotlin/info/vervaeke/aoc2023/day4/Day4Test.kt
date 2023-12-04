package info.vervaeke.aoc2023.day4

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day4Test {

    val sample = Day4.readInput("sample")

    @Test
    fun part1Sample() {
        assertThat(Day4(sample).part1())
            .isEqualTo(13)
    }

    @Test
    fun part2Sample() {
        assertThat(Day4(sample).part2())
            .isEqualTo(42)
    }
}