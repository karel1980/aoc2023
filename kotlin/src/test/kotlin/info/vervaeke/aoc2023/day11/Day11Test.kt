package info.vervaeke.aoc2023.day11

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day11Test {

    val day11 = Day11.parseInput("sample")

    @Test
    fun part1() {
        assertThat(day11.part1())
            .isEqualTo(374)
    }

    @Test
    fun zero() {
        assertThat(day11.findGalaxies().all { day11.distance(it, it, day11.emptyRows(), day11.emptyCols()) == 0L })
            .isTrue()
    }

    @Test
    fun distance() {
        val gal = day11.findGalaxies()
        assertThat(day11.distance(gal[4], gal[8], day11.emptyRows(), day11.emptyCols()))
            .isEqualTo(9)
    }

    @Test
    fun emptyRows() {
        assertThat(day11.emptyRows())
            .isEqualTo(listOf(3, 7))
    }

    @Test
    fun findGalaxies() {
        assertThat(day11.findGalaxies())
            .contains(Coord(0, 3))
    }

    @Test
    fun part2() {
        assertThat(day11.part2())
            .isEqualTo(42)
    }
    @Test
    fun calculateEmptySpace10() {
        assertThat(day11.calculateSpace(10))
            .isEqualTo(1030)
    }
    @Test
    fun calculateEmptySpace1000() {
        assertThat(day11.calculateSpace(100))
            .isEqualTo(8410)
    }
}