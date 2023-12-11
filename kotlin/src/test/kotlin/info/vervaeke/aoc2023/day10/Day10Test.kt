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
    fun start() {
        Assertions.assertThat(day10.start)
            .isEqualTo(Coord(2,0))
    }

    @Test
    fun part1() {
        Assertions.assertThat(day10.part1())
            .isEqualTo(8)
    }

    @Test
    fun part2_babysample() {
        Assertions.assertThat(Day10.parseInput("babysample").part2())
            .isEqualTo(1)
    }

    @Test
    fun part2() {
        Assertions.assertThat(Day10.parseInput("sample2").part2())
            .isEqualTo(4)

    }

    @Test
    fun part2b() {
        Assertions.assertThat(Day10.parseInput("sample2b").part2())
            .isEqualTo(4)

    }

    @Test
    fun part2_3() {
        Assertions.assertThat(Day10.parseInput("sample3").part2())
            .isEqualTo(8)

    }

    @Test
    fun part2_4() {
        Assertions.assertThat(Day10.parseInput("sample4").part2())
            .isEqualTo(10)

    }
}