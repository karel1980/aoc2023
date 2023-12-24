package info.vervaeke.aoc2023.day24

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class Day24Test {

    val sample = Day24.parseInput("sample")
    val real = Day24.parseInput("input")

    @Test
    fun parse() {
        Assertions.assertThat(sample.stones[0])
            .isEqualTo(Stone(19, 13, 30, -2,  1, -2))
    }

    @Test
    fun crossPoint() {
        Assertions.assertThat(sample.stones[0].crosspoint(sample.stones[1]))
            .isEqualTo(Coord2d(x=14.333333333333334, y=15.333333333333334))
    }

    @Test
    fun countInside() {
        Assertions.assertThat(sample.countInside(7.0, 27.0))
            .isEqualTo(2)
    }

    @Test
    fun part2() {
        Assertions.assertThat(sample.part2())
            .isEqualTo(42)
    }
}