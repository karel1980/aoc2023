package info.vervaeke.aoc2023.day14

import info.vervaeke.aoc2023.day13.Day14
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day14Test {
    val sample = Day14.parseInput("input")

    @Test
    fun part1() {
        assertThat(sample.part1())
            .isEqualTo(42)
    }

    @Test
    fun part2() {
        assertThat(sample.part2())
            .isEqualTo(42)
    }
}