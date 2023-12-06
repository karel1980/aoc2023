package info.vervaeke.aoc2023.day6

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day6Test {

    val day6 = Day6.parseInput("sample")

    @Test
    fun parse() {
        assertThat(day6.times)
            .isEqualTo(listOf(7L, 15L, 30L))
    }

    @Test
    fun part1() {
        assertThat(day6.part1())
            .isEqualTo(288)
    }

    @Test
    fun part2() {
        assertThat(day6.part2())
            .isEqualTo(71503L)
    }

    @Test
    fun part1_real() {
        assertThat(Day6.parseInput("input").part1())
            .isEqualTo(170000L)
    }

    @Test
    fun part2_real() {
        assertThat(Day6.parseInput("input").part2())
            .isEqualTo(20537782L)
    }

    @Test
    fun getDistance() {
        assertThat(day6.getDistance(7, 0))
            .isEqualTo(0)
        assertThat(day6.getDistance(7, 1))
            .isEqualTo(6)
        assertThat(day6.getDistance(7, 7))
            .isEqualTo(0)
    }
}