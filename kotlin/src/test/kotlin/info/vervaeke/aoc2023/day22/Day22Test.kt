package info.vervaeke.aoc2023.day22

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class Day22Test {
    val sample = Day22.parseInput("sample")
    val real = Day22.parseInput("input")

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