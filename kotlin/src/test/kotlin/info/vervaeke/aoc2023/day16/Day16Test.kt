package info.vervaeke.aoc2023.day16

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day16Test {
    
    val day16 = Day16.parseInput("sample")

    @Test
    fun parse() {
        assertThat(day16.lines[0])
            .isEqualTo(".|...\\....")
    }

    @Test
    fun part1() {
        assertThat(day16.part1())
            .isEqualTo(46)
    }
}