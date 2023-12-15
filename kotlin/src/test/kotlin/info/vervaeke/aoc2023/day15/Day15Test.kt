package info.vervaeke.aoc2023.day15

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day15Test {
    val sample = Day15.parseInput("sample")

    @Test
    fun part1() {
        assertThat(sample.part1())
            .isEqualTo(1320)
    }

    @Test
    fun part2() {
        assertThat(sample.part2())
            .isEqualTo(145)
    }
}