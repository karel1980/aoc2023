package info.vervaeke.aoc2023.day18

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day18Test {

    val sample = Day18.parseInput("sample")

    @Test
    fun parse() {
        assertThat(sample.digs[0])
            .isEqualTo(Dig(Direction.RIGHT, 6, "70c710"))
    }

    @Test
    fun points() {
        assertThat(sample.edgePoints())
            .isEqualTo(38)
    }

    @Test
    fun getLeftEdgePoints() {
        assertThat(sample.getLeftEdgePoints().filter { it.first.row < 3 })
            .containsExactlyInAnyOrder(
                Coord(1, 0) to  (Coord(0, 6) to Coord(1, 6)),
                Coord(2, 0) to  (Coord(1, 6) to Coord(2, 6)),
                Coord(2, 1) to  (Coord(1, 6) to Coord(2, 6)),
                Coord(2, 2) to  (Coord(1, 6) to Coord(2, 6)),
            )
    }

    @Test
    fun part1() {
        assertThat(sample.part1())
            .isEqualTo(62)
    }

    @Test
    fun part2() {
        assertThat(sample.part2())
            .isEqualTo(42)
    }
}