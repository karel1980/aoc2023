package info.vervaeke.aoc2023.day21

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class Day21Test {
    val sample = Day21.parseInput("sample")

    @Test
    fun part1() {
        Assertions.assertThat(sample.part1())
            .isEqualTo(1L)
    }

    @Test
    fun part2() {
        Assertions.assertThat(sample.part2())
            .isEqualTo(1L)
    }
}