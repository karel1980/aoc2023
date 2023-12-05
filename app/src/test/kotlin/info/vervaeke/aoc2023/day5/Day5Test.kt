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
        assertThat(Range(98L, 50L, 2L).map(98))
            .isEqualTo(50)
        assertThat(Range(98L, 50L, 2L).map(99))
            .isEqualTo(51)
    }

    @Test
    fun `applyValueRange valueRange completely in range`() {
        val range = Range(10, 30, 10)

        val actual = range.applyValueRange(12L..14L)
        assertThat(actual)
            .isEqualTo(listOf(32L..34))
    }

    @Test
    fun `applyValueRange valueRange starts at start of range`() {
        val range = Range(10, 30, 10)

        val actual = range.applyValueRange(10L..14L)
        assertThat(actual)
            .isEqualTo(listOf(30L..34))
    }


    @Test
    fun `valueRange start before range`() {
        val range = Range(10, 30, 10)

        val actual = range.applyValueRange(5L..14L)
        assertThat(actual)
            .isEqualTo(listOf(5..9, 30L..34))
    }

    @Test
    fun rangestuff() {
        val range = Range(50, 52, 48)

        assertThat(range.applyValueRange(0..200L))
            .isEqualTo(listOf(0..49L, 52..99L, 98..200L))
    }

    @Test
    fun rangestuff_inside() {
        val range = Range(50, 52, 48)

        assertThat(range.applyValueRange(60..70L))
            .isEqualTo(listOf(62..72L))
    }

    @Test
    fun part2() {
        assertThat(day5.part2())
            .isEqualTo(46)
    }

    @Test
    fun part2_troubleshoot() {
        val seedRanges = day5.almanac.seedRanges

        assertThat(seedRanges)
            .isEqualTo(
                listOf(
                    79L..92,
                    55L..67
                )
            )

        assertThat(day5.almanac.seedToSoil)
            //50 98 2
            //52 50 48
            .isEqualTo(
                RangeMapper(
                    "seed", "soil", listOf(
                        Range(98L, 50L, 2L),
                        Range(50L, 52L, 48L),
                    )
                )
            )

        assertThat(day5.almanac.seedToSoil.toDestinations(seedRanges))
            .isEqualTo(listOf(57..69L, 82..94L))
//
//        assertThat(day5.almanac.seedToSoil.toDestinations(seedRanges))
//            .isEqualTo()
    }

    @Test
    fun applyValueRange() {
        val valueRange = 79L..92
        val ranges = listOf(Range(98, 50, 2), Range(50, 52, 48))

        val result = ranges.flatMap { it.applyValueRange(valueRange) }
        assertThat(result)
            .isEqualTo(listOf(81L..94))
    }

    @Test
    fun applyValueRangeSingle() {
        val valueRange = 79L..92
        val range = Range(50, 52, 48)

        assertThat(range.applyValueRange(valueRange))
            .isEqualTo(listOf(81L..94))
    }

    @Test
    fun applyValueRangeSingle_doesNotOverlap() {
        val valueRange = 79L..92
        val range = Range(98, 50, 2)

        assertThat(range.applyValueRange(valueRange))
            .isEqualTo(listOf<LongRange>())
    }

    @Test
    fun day5_again() {
        val seed = 79L..92
        val range = Range(98, 50, 2)

        assertThat(listOf(seed).flatMap { mapRange(it, listOf(range)) })
            .isEqualTo(listOf(79..92L))

//        assertThat(listOf(day5.almanac.seedRanges))
//            .isEqualTo()

    }

}