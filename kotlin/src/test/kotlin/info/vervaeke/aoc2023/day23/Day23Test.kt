package info.vervaeke.aoc2023.day23

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day23Test {
    val sample = Day23.parseInput("sample")
    val real = Day23.parseInput("input")

    @Test
    fun part1() {
        assertThat(sample.part1())
            .isEqualTo(94)
    }

    @Test
    fun findAllUniquePaths() {
        assertThat(sample.findAllUniquePaths())
            .containsExactlyInAnyOrder(90, 86, 82, 82, 74, 94)
    }

    @Test
    fun part2() {
        assertThat(sample.part2())
            .isEqualTo(42)

    }
}