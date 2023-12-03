package info.vervaeke.aoc2023.day3

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day3KtTest {

    val day3 = Day3(read_input("sample"))

    @Test
    fun part1Sample() {
        assertThat(solve_part1_sample())
            .isEqualTo(4361)
    }

    @Test
    fun part2Sample() {
        assertThat(solve_part2_sample())
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