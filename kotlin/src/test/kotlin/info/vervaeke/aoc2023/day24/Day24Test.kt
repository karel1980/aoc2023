package info.vervaeke.aoc2023.day24

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class Day24Test {

    val sample = Day24.parseInput("sample")
    val real = Day24.parseInput("input")

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