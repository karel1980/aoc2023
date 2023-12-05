package info.vervaeke.aoc2023.day5

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day5Test {
    val day5 = Day5(Day5.readInput("sample"))

    @Test
    fun part1() {
        assertThat(day5.part1())
            .isEqualTo(35L)
    }

    @Test
    fun range() {
        assertThat(RangeMap(98L, 50L, 2L).map(98))
            .isEqualTo(50)
        assertThat(RangeMap(98L, 50L, 2L).map(99))
            .isEqualTo(51)
    }

    @Test
    fun part2() {
        assertThat(day5.part2())
            .isEqualTo(46)
    }

    @Test
    fun part2_real() {
        assertThat(Day5(Day5.readInput("input")).part2())
            .isEqualTo(219529182L)
    }

    @Test
    fun day5_again() {
        val seed = 79L..92
        val rangeMapper = RangeMapper("seed", "soil", listOf(RangeMap(98, 50, 2)))

        assertThat(rangeMapper.mapSingleRange(seed))
            .isEqualTo(listOf(79..92L))
    }

    @Test
    fun day5_numberOfSeeds() {
        val numberOfSeeds = day5.almanac.seedRanges.sumOf {
            it.endInclusive - it.start + 1
        }

        assertThat(numberOfSeeds)
            .isEqualTo(27)
    }
}