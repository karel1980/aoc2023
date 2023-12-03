package info.vervaeke.aoc2023.day3

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day3Test {

    val sample = Day3.readInput("sample")
    val day3 = Day3(sample)

    @Test
    fun part1Sample() {
        assertThat(day3.part1())
            .isEqualTo(4361)
    }

    @Test
    fun part2Sample() {
        assertThat(day3.part2())
            .isEqualTo(467835)
    }

    @Test
    fun getNumbers() {
        assertThat(day3.getNumbers())
            .hasSize(10)
    }

    @Test
    fun isPartNumber() {
        val nums = day3.getNumbers()
        assertThat(day3.isPartNumber(nums[7]))
            .isTrue()
        assertThat(day3.isPartNumber(nums[5]))
            .isFalse()
        assertThat(day3.isPartNumber(nums[0]))
            .isTrue()
        assertThat(day3.isPartNumber(nums[1]))
            .isFalse()
    }
}