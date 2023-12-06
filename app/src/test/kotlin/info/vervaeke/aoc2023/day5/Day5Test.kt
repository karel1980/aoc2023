package info.vervaeke.aoc2023.day5

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day5Test {
    val almanac = Almanac.parseInput("sample")

    @Test
    fun part1() {
        assertThat(almanac.part1())
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
        assertThat(almanac.part2())
            .isEqualTo(46)
    }

    @Test
    fun part2_real() {
        assertThat(Almanac.parseInput("input").part2())
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
        val numberOfSeeds = almanac.seedRanges.sumOf {
            it.endInclusive - it.start + 1
        }

        assertThat(numberOfSeeds)
            .isEqualTo(27)
    }
}