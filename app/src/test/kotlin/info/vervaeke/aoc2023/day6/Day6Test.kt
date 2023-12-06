package info.vervaeke.aoc2023.day6

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class Day6Test {

    val day6 = Day6.parseInput("sample")

    @Test
    fun parse() {
        Assertions.assertThat(day6.times)
            .isEqualTo(listOf(7, 15, 30))
    }

    @Test
    fun part1() {
        Assertions.assertThat(day6.part1())
            .isEqualTo(288)
    }

    @Test
    fun getDistance() {
        Assertions.assertThat(day6.getDistance(7, 0))
            .isEqualTo(0)
        Assertions.assertThat(day6.getDistance(7, 1))
            .isEqualTo(6)
        Assertions.assertThat(day6.getDistance(7, 7))
            .isEqualTo(0)
    }
}