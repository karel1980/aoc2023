package info.vervaeke.aoc2023.day18

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day18Test {

    val sample = Day18.parseInput("sample")
    val real = Day18.parseInput("input")

    @Test
    fun parse() {
        assertThat(sample.digs[0])
            .isEqualTo(Dig(Direction.RIGHT, 6, "70c710"))
    }

    @Test
    fun swap() {
        assertThat(sample.swap().digs[0])
            .isEqualTo(Dig(Direction.RIGHT, 461937, "70c710"))
    }

    @Test
    fun part1() {
        assertThat(sample.part1())
            .isEqualTo(62)
    }

    @Test
    fun part1_square() {
        assertThat(Day18.parseInput("square").part1())
            .isEqualTo(30)
    }

    @Test
    fun isInside() {
        assertThat(sample.isInside(Coord(1,-1)))
            .isFalse()
    }

    @Test
    fun isInside_onEdge() {
        assertThat(sample.isInside(Coord(1,0)))
            .isFalse()
    }

    @Test
    fun part1Real() {
        assertThat(real.part1())
            .isEqualTo(70026)
    }

    @Test
    fun part2() {
        assertThat(sample.part2())
            .isEqualTo(952408144115)
    }

}