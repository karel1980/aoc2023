package info.vervaeke.aoc2023.day5

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class Day5Test {
    val day5 = Day5(Day5.readInput("sample"))

    @Test
    fun part1() {
        Assertions.assertThat(day5.part1())
            .isEqualTo(42)
    }

    @Test
    fun part2() {
        Assertions.assertThat(day5.part2())
            .isEqualTo(42)
    }
}