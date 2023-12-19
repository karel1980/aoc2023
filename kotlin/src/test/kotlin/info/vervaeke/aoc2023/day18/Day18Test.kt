package info.vervaeke.aoc2023.day18

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day18Test {

    val sample = Day18.parseInput("sample")
    val real = Day18.parseInput("input")

    @Test
    fun parse() {
        assertThat(sample.digs[0])
            .isEqualTo(Dig(Direction.RIGHT, 6, "70c710"))
    }

    @Test
    fun swap() {
        assertThat(sample.swap().digs[0])
            .isEqualTo(Dig(Direction.RIGHT, 461937, "70c710"))
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
                Coord(1, 0) to (Coord(0, 6) to Coord(1, 6)),
                Coord(2, 0) to (Coord(1, 6) to Coord(2, 6)),
                Coord(2, 1) to (Coord(1, 6) to Coord(2, 6)),
                Coord(2, 2) to (Coord(1, 6) to Coord(2, 6)),
            )
    }

    @Test
    fun part1_newWay() {
        val cornerPoints = sample.allCornerPoints()
        assertThat(cornerPoints)
            .isEqualTo(
                listOf(
                    Coord(0, 6),
                    Coord(5, 6),
                    Coord(5, 4),
                    Coord(7, 4),
                    Coord(7, 6),
                    Coord(9, 6),
                    Coord(9, 1),
                    Coord(7, 1),
                    Coord(7, 0),
                    Coord(5, 0),
                    Coord(5, 2),
                    Coord(2, 2),
                    Coord(2, 0),
                    Coord(0, 0),
                )
            )

        val edges = sample.getEdges(cornerPoints)
        assertThat(edges)
            .isEqualTo(
                listOf(
                    Coord(0, 6) to Coord(5, 6),
                    Coord(5, 6) to Coord(5, 4),
                    Coord(5, 4) to Coord(7, 4),
                    Coord(7, 4) to Coord(7, 6),
                    Coord(7, 6) to Coord(9, 6),
                    Coord(9, 6) to Coord(9, 1),
                    Coord(9, 1) to Coord(7, 1),
                    Coord(7, 1) to Coord(7, 0),
                    Coord(7, 0) to Coord(5, 0),
                    Coord(5, 0) to Coord(5, 2),
                    Coord(5, 2) to Coord(2, 2),
                    Coord(2, 2) to Coord(2, 0),
                    Coord(2, 0) to Coord(0, 0),
                    Coord(0, 0) to Coord(0, 6),

                    )
            )
        val verticalEdges = sample.getVerticalEdges(edges)
        assertThat(verticalEdges)
            .isEqualTo(
                listOf(
                    Coord(0, 6) to Coord(5, 6),
                    Coord(5, 4) to Coord(7, 4),
                    Coord(7, 6) to Coord(9, 6),
                    Coord(7, 1) to Coord(9, 1),
                    Coord(5, 0) to Coord(7, 0),
                    Coord(2, 2) to Coord(5, 2),
                    Coord(0, 0) to Coord(2, 0),
                )
            )
        val consideredPoints = sample.calculateConsideredPoints(cornerPoints)

        val pointsAndEdges = sample.rayCastEdgeDetection(consideredPoints, edges, verticalEdges)
        assertThat(pointsAndEdges)
            .containsExactlyInAnyOrder(
                Coord(-1, 6 + 1) to listOf(),
                Coord(0, 6 + 1)  to listOf(),
                Coord(1, 6 + 1)  to listOf(),
                Coord(5 - 1, 6 + 1)  to listOf(),
                Coord(5, 6 + 1)  to listOf(),
                Coord(5 + 1, 6 + 1)  to listOf(),
                Coord(5 - 1, 4 + 1)  to listOf(),
                Coord(5, 4 + 1)  to listOf(),
                Coord(5 + 1, 4 + 1)  to listOf(),
                Coord(7 - 1, 4 + 1)  to listOf(),
                Coord(7, 4 + 1)  to listOf(),
                Coord(7 + 1, 4 + 1)  to listOf(),
                Coord(7 - 1, 6 + 1)  to listOf(),
                Coord(7, 6 + 1)  to listOf(),
                Coord(7 + 1, 6 + 1)  to listOf(),
                Coord(9 - 1, 6 + 1)  to listOf(),
                Coord(9, 6 + 1)  to listOf(),
                Coord(9 + 1, 6 + 1)  to listOf(),
                Coord(9 - 1, 1 + 1)  to listOf(),
                Coord(9, 1 + 1)  to listOf(),
                Coord(9 + 1, 1 + 1)  to listOf(),
                Coord(7 - 1, 1 + 1)  to listOf(),
                Coord(7, 1 + 1)  to listOf(),
                Coord(7 + 1, 1 + 1)  to listOf(),
                Coord(7 - 1, 1)  to listOf(),
                Coord(7, 1)  to listOf(),
                Coord(7 + 1, 1)  to listOf(),
                Coord(5 - 1, 1)  to listOf(),
                Coord(5, 1)  to listOf(),
                Coord(5 + 1, 1)  to listOf(),
                Coord(5 - 1, 2 + 1)  to listOf(),
                Coord(5, 2 + 1)  to listOf(),
                Coord(5 + 1, 2 + 1)  to listOf(),
                Coord(2 - 1, 2 + 1)  to listOf(),
                Coord(2, 2 + 1)  to listOf(),
                Coord(2 + 1, 2 + 1)  to listOf(),
                Coord(2 - 1, 1)  to listOf(),
                Coord(2, 1)  to listOf(),
                Coord(2 + 1, 1)  to listOf(),
                Coord(-1, 1)  to listOf(),
                Coord(0, 1)  to listOf(),
                Coord(1, 1) to listOf())
        val insidePoints = sample.keepOnlyInsidePoints(pointsAndEdges)
        val pointsInside = sample.pointsAndRowWidths(insidePoints)

//        assertThat(sample.calculateSurface2())
//            .isEqualTo(62)
    }

    @Test
    fun part1() {
        assertThat(sample.part1())
            .isEqualTo(62)
    }

    @Test
    fun part1Real() {
        assertThat(real.part1())
            .isEqualTo(70026)
    }

//    @Test
//    fun part2() {
//        assertThat(sample.part2())
//            .isEqualTo(952408144115)
//    }
}