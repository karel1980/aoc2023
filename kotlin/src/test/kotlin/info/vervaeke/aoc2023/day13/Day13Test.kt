package info.vervaeke.aoc2023.day13

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class Day13Test {
    val day13 = Day13.parseInput("sample")

    @Test
    fun parse() {
        Assertions.assertThat(day13)
            .isEqualTo(Day13(listOf("sample")))
    }

    @Test
    fun part1() {
        Assertions.assertThat(day13.part1())
            .isEqualTo(42L)
    }

    @Test
    fun part2() {
        Assertions.assertThat(day13.part2())
            .isEqualTo(42L)
    }
}