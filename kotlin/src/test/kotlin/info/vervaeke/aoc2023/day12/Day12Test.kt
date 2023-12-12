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
    fun part1Real() {
        assertThat(Day12.parseInput("input").part1())
            .isEqualTo(7047)
    }

    @Test
    fun parse() {
        assertThat(day12.rows[0])
            .isEqualTo(Row("???.###", listOf(1, 1, 3)))
    }

    @Test
    fun atTest() {
        assertThat(day12.rows[0].groupCanBeAt(2, 4))
            .isTrue()
    }

    @Test
    fun row0_group1_cannotBeAt6() {
        assertThat(day12.rows[0].groupCanBeAt(1, 6))
            .isFalse()

    }

    @Test
    fun countArrangements_row0() {
        assertThat(day12.rows[0].countArrangements())
            .isEqualTo(1)
    }

    @Test
    fun countArrangements_row1() {
        assertThat(day12.rows[1].countArrangements())
            .isEqualTo(4)
    }

    @Test
    fun canBeAt_row1() {
//        assertThat(day12.rows[1].groupCanBeAt(0, 0))
//            .isFalse()
        assertThat(day12.rows[1].groupCanBeAt(0, 1))
            .isTrue()

    }

    @Test
    fun countArrangements_row2() {
        assertThat(day12.rows[2].countArrangements())
            .isEqualTo(1)
    }

    @Test
    fun countArrangements_row3() {
        assertThat(day12.rows[3].countArrangements())
            .isEqualTo(1)
    }

    @Test
    fun countArrangements_row4() {
        assertThat(day12.rows[4].countArrangements())
            .isEqualTo(4)
    }

    @Test
    fun countArrangements_row5() {
        assertThat(day12.rows[5].countArrangements())
            .isEqualTo(10)
    }

    @Test
    fun `row5_group2_cannotBeAtPosition2() {`() {
        assertThat(day12.rows[5].groupCanBeAt(2, 2))
            .isFalse()
    }

    @Test
    fun matchesInstance() {
        assertThat(day12.rows[0].matchesInstance("#.#.###"))
            .isTrue()
    }

    @Test
    fun createBitSet() {
        assertThat(createBitSet(5))
            .isEqualTo(BitSet().apply { set(0); set(2) })
        assertThat(createBitSet(4))
            .isEqualTo(BitSet().apply { set(2) })
    }

    @Test
    fun unfold() {
        assertThat(day12.rows[0].unfold())
            .isEqualTo(Row("???.###????.###????.###????.###????.###", listOf(1, 1, 3, 1, 1, 3, 1, 1, 3, 1, 1, 3, 1, 1, 3)))
    }

    @Test
    fun part2_countArrangements() {
        assertThat(day12.rows[0].unfold().countArrangements())
            .isEqualTo(1L)
        assertThat(day12.rows[1].unfold().countArrangements())
            .isEqualTo(16384L)
        assertThat(day12.rows[2].unfold().countArrangements())
            .isEqualTo(1L)
        assertThat(day12.rows[3].unfold().countArrangements())
            .isEqualTo(16L)
        assertThat(day12.rows[4].unfold().countArrangements())
            .isEqualTo(2500L)
        assertThat(day12.rows[5].unfold().countArrangements())
            .isEqualTo(506250L)
    }

    @Test
    fun part2_unfold2_canBeAt() {
        val row = day12.rows[2].unfold(2)
        assertThat((0 until row.spec.length).filter { row.groupCanBeAt(0, it) })
            .isEqualTo(listOf(1))
        assertThat((0 until row.spec.length).filter { row.groupCanBeAt(1, it) })
            .isEqualTo(listOf(1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27))
        assertThat((0 until row.spec.length).filter { row.groupCanBeAt(2, it) })
            .isEqualTo(listOf(1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29))
        assertThat((0 until row.spec.length).filter { row.groupCanBeAt(3, it) })
            .isEqualTo(listOf(9, 16))
        assertThat((0 until row.spec.length).filter { row.groupCanBeAt(4, it) })
            .isEqualTo(listOf(1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29))
        assertThat((0 until row.spec.length).filter { row.groupCanBeAt(5, it) })
            .isEqualTo(listOf(1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27))
        assertThat((0 until row.spec.length).filter { row.groupCanBeAt(6, it) })
            .isEqualTo(listOf(1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29))
        assertThat((0 until row.spec.length).filter { row.groupCanBeAt(7, it) })
            .isEqualTo(listOf(25))
    }

    @Test
    fun part2_unfold2() {
        val row = day12.rows[2].unfold(2)
        assertThat(row.countArrangements())
            .isEqualTo(1L)
    }

    @Test
    fun part2() {
        assertThat(day12.part2())
            .isEqualTo(525152L)
    }

    @Test
    fun part2_real() {
        assertThat(Day12.parseInput("input").part2())
            .isEqualTo(17391848518844L)
    }
}