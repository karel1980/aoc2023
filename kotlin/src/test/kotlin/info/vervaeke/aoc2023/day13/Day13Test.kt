package info.vervaeke.aoc2023.day13

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day13Test {
    val day13 = Day13.parseInput("sample")

    @Test
    fun parse() {
        assertThat(day13.grids.size)
            .isEqualTo(2)
        assertThat(day13.grids[0].lines[0])
            .isEqualTo("#.##..##.")
    }

    @Test
    fun horizontalReflection() {
        assertThat(day13.grids[1].horizontalReflection())
            .isEqualTo(4)
    }

    @Test
    fun verticalReflection() {
        assertThat(day13.grids[0].verticalReflection())
            .isEqualTo(5)
    }

    @Test
    fun moreEdges() {
        val edgecases = Day13.parseInput("edgecases")
        assertThat(edgecases.grids[1].horizontalReflection())
            .isEqualTo(3)
    }

    @Test
    fun edges() {
        val edgecases = Day13.parseInput("edgecases")
        assertThat(edgecases.grids[0].horizontalReflection())
            .isEqualTo(1)
        assertThat(edgecases.grids[1].horizontalReflection())
            .isEqualTo(3)
        assertThat(edgecases.grids[2].horizontalReflection())
            .isEqualTo(1)
        assertThat(edgecases.grids[3].horizontalReflection())
            .isEqualTo(4)
    }

    @Test
    fun `part2FailToFindUpdatedReflectionLine() {`() {
        val grid = Day13.parseInput("edgecases").grids[4]
        assertThat(grid.horizontalReflection())
            .isEqualTo(2)
        assertThat(grid.flip(8, 11).horizontalReflection(2))
            .isEqualTo(9)

    }

    @Test
    fun sampleFlip() {
        assertThat(day13.grids[0].flip(0, 0).horizontalReflection())
            .isEqualTo(3)
        assertThat(day13.grids[1].flip(1, 4).horizontalReflection())
            .isEqualTo(1)
    }

    @Test
    fun part1() {
        assertThat(day13.part1())
            .isEqualTo(405)
    }

    @Test
    fun part1Real() {
        assertThat(Day13.parseInput("input").part1())
            .isEqualTo(31956)
    }

    @Test
    fun part2() {
        assertThat(day13.part2())
            .isEqualTo(400L)
    }

    @Test
    fun `part2Real() {`() {
        assertThat(Day13.parseInput("input").part2())
            .isEqualTo(37617)
    }
}