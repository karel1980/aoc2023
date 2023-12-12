package info.vervaeke.aoc2023.day12

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.*

class Day12Test {

    val day12 = Day12.parseInput("sample")

    @Test
    fun part1() {
        assertThat(day12.part1())
            .isEqualTo(21)
    }
    
    @Test
    fun parse() {
        assertThat(day12.rows[0])
            .isEqualTo(Row("???.###", listOf(1,1,3)))
    }

    @Test
    fun countArrangements() {
        assertThat(day12.rows[0].countArrangements())
            .isEqualTo(1)
        assertThat(day12.rows[1].countArrangements())
            .isEqualTo(4)
        assertThat(day12.rows[2].countArrangements())
            .isEqualTo(1)
        assertThat(day12.rows[3].countArrangements())
            .isEqualTo(1)
        assertThat(day12.rows[4].countArrangements())
            .isEqualTo(4)
        assertThat(day12.rows[5].countArrangements())
            .isEqualTo(10)
    }

    @Test
    fun matchesInstance() {
        assertThat(day12.rows[0].matchesInstance("#.#.###"))
            .isTrue()
    }

    @Test
    fun createBitSet() {
        assertThat(createBitSet(5))
            .isEqualTo(BitSet().apply { set(0); set (2) })
        assertThat(createBitSet(4))
            .isEqualTo(BitSet().apply { set (2) })
    }

    @Test
    fun part2() {
        assertThat(day12.part2())
            .isEqualTo(42)
    }
}