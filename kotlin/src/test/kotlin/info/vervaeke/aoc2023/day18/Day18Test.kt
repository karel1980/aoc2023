package info.vervaeke.aoc2023.day18

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class Day18Test {

    val sample = Day18.parseInput("sample")

    @Test
    fun part1() {
        Assertions.assertThat(sample.part1())
            .isEqualTo(42)
    }

    @Test
    fun part2() {
        Assertions.assertThat(sample.part2())
            .isEqualTo(42)
    }
}