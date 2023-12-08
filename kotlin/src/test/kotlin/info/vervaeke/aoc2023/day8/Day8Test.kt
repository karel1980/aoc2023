package info.vervaeke.aoc2023.day8

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.math.BigInteger

class Day8Test {

    val day8 = Day8.parseInput("sample")

    @Test
    fun part1() {
        Assertions.assertThat(day8.part1())
            .isEqualTo(2)
    }

    @Test
    fun part1_LLR() {
        Assertions.assertThat(Day8.parseInput("sample2").part1())
            .isEqualTo(6)
    }

    @Test
    fun part2() {
        Assertions.assertThat(Day8.parseInput("sample3").part2())
            .isEqualTo(6)
    }


}