package info.vervaeke.aoc2023.day9

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day9Test {

    val day9 = Day9.parseInput("sample")

    @Test
    fun parse() {
        Assertions.assertThat(day9.input[0])
            .isEqualTo(listOf(0, 3, 6, 9, 12, 15))
    }

    @Test
    fun findNextValue() {
        Assertions.assertThat(day9.findNextValue(listOf(0, 3, 6, 9, 12, 15)))
            .isEqualTo(18)
    }

    @Test
    fun part1() {
        Assertions.assertThat(day9.part1())
            .isEqualTo(114)
    }

    @Test
    fun part2() {
        Assertions.assertThat(day9.part2())
            .isEqualTo(2)
    }
}