package info.vervaeke.aoc2023.day22

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day22Test {
    val sample = Day22.parseInput("sample")
    val real = Day22.parseInput("input")

    @Test
    fun part1() {
        assertThat(sample.part1())
            .isEqualTo(5)
    }

    @Test
    fun parse() {
        assertThat(sample.bricks[0])
            .isEqualTo(Brick("A", Coord(1, 0, 1), Coord(1, 2, 1)))
        assertThat(sample.bricks[1])
            .isEqualTo(Brick("B", Coord(0,0,2), Coord(2,0,2)))
    }

    @Test
    fun canBeDisintegrated() {
        val dropped = sample.dropThemAll()
        val droppedById = dropped.associateBy { it.id }

        assertThat(droppedById["A"]!!.canBeDesintegrated(dropped))
            .isFalse()

    }

    @Test
    fun directlySupporting() {
        val dropped = sample.dropThemAll()
        val droppedById = dropped.associateBy { it.id }
        val a = droppedById["A"]!!
        val g = droppedById["G"]!!

        assertThat(a.isDirectlySupporting(g))
            .isFalse
    }

    @Test
    fun isUnder() {
        val a = sample.bricks[0]
        val c = sample.bricks[2]
        assertThat(a.isUnder(c))
            .isTrue
    }

    @Test
    fun overlapsViewedFromTheTop() {
        val a = Brick("A", Coord(1,0, 0), Coord(1,2,0))
        val b = Brick("B", Coord(0,1, 0), Coord(2,1,0))

        assertThat(a.overlapsViewedFromTheTop(b))
            .isTrue

        assertThat(b.overlapsViewedFromTheTop(a))
            .isTrue

    }

    @Test
    fun dropAll() {
        val dropped = sample.dropThemAll().sortedBy { it.start.z }

        assertThat(dropped.first { it.id == "B" })
            .isEqualTo(Brick("B", Coord(0, 0, 2), Coord(2, 0, 2)))
        assertThat(dropped.map { it.start.z }.sorted())
            .containsExactly(
                1,2,2,3,3,4,5
            )
    }

    @Test
    fun overlapsViewedFromTopBrick() {
        val brick1 = Brick("X", Coord(0, 0, 0), Coord(0, 0, 0))
        assertThat(brick1.overlapsViewedFromTheTop(Brick("X", Coord(-1,-1,0), Coord(-1,-1,0))))
            .isFalse
        assertThat(brick1.overlapsViewedFromTheTop(Brick("X", Coord(-1,-1,0), Coord(-1,1,0))))
            .isFalse
        assertThat(brick1.overlapsViewedFromTheTop(Brick("X", Coord(-1,-1,0), Coord(1,1,0))))
            .isTrue
    }

    @Test
    fun isUnder2() {
        val brick1 = Brick("X", Coord(0, 0, 0), Coord(0, 0, 0))
        assertThat(brick1.isUnder(Brick("X", Coord(0,0,5), Coord(0,0,5))))
            .isTrue
        assertThat(brick1.isUnder(Brick("X", Coord(0,0,-5), Coord(0,0,-5))))
            .isFalse
        assertThat(brick1.isUnder(Brick("X", Coord(1,0,5), Coord(1,0,5))))
            .isFalse
        assertThat(brick1.isUnder(Brick("X", Coord(1,0,5), Coord(1,0,5))))
            .isFalse
        assertThat(brick1.isUnder(Brick("X", Coord(0,0,5), Coord(1,1,5))))
            .isTrue
    }

    @Test
    fun part2() {
        assertThat(sample.part2())
            .isEqualTo(7)
    }


}