package info.vervaeke.aoc2023.day10

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class Day10Test {

    val day10 = Day10.parseInput("sample")

    @Test
    fun parse() {
        Assertions.assertThat(day10.grid[0][0].toString())
            .isEqualTo(".")
    }

    @Test
    fun findSCoord() {
        Assertions.assertThat(day10.findSCoord())
            .isEqualTo(Coord(2,0))
    }

    @Test
    fun part1() {
        Assertions.assertThat(day10.part1())
            .isEqualTo(8)
    }

    @Test
    fun part2() {
        Assertions.assertThat(day10.part2())
            .isEqualTo(42)
    }
}