package info.vervaeke.aoc2023.day12

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day12Test {

    val day12 = Day12.parseInput("sample")

    @Test
    fun part1() {
        assertThat(day12.part1())
            .isEqualTo(42)
    }

    @Test
    fun part2() {
        assertThat(day12.part2())
            .isEqualTo(42)
    }
}