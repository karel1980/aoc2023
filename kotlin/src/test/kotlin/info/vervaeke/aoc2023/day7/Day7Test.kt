package info.vervaeke.aoc2023.day7

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day7Test {

    private val day7 = Day7.parseInput("sample")

    @Test
    fun part1() {
        assertThat(day7.part1())
            .isEqualTo(42)
    }

    @Test
    fun part2() {
        assertThat(day7.part2())
            .isEqualTo(42)
    }
}