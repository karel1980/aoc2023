package info.vervaeke.aoc2023.day20

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class Day20Test {
    val sample = Day20.parseInput("sample")
    val sample2 = Day20.parseInput("sample2")

    @Test
    fun part2_cycleLength() {
        Assertions.assertThat(sample2.cycleLength())
            .isEqualTo(4)
    }

    @Test
    fun part1_sample() {
        Assertions.assertThat(sample.part1())
            .isEqualTo(32000000L)
    }

    @Test
    fun part1_sample2() {
        Assertions.assertThat(sample2.part1())
            .isEqualTo(11687500L)
    }

    @Test
    fun part2() {
        Assertions.assertThat(sample.part2())
            .isEqualTo(42)
    }

}