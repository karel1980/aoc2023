package info.vervaeke.aoc2023.day17

import info.vervaeke.aoc2023.day17.Direction.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day17Test {

    val sample = Day17.parseInput("sample")

    @Test
    fun parse() {
        assertThat(sample.lines[0])
            .isEqualTo("2413432311323")
    }

    @Test
    fun getNeighbours() {
        assertThat(sample.getNeighbours(Node(Coord(0, 0))))
            .isEqualTo(
                listOf(
                    Node(Coord(0, 1), RIGHT),
                    Node(Coord(0, 2), RIGHT),
                    Node(Coord(0, 3), RIGHT),
                    Node(Coord(1, 0), DOWN),
                    Node(Coord(2, 0), DOWN),
                    Node(Coord(3, 0), DOWN),
                )
            )
    }

    @Test
    fun getNeighbours_noTwiceRight() {
        assertThat(sample.getNeighbours(Node(Coord(0, 0), dir=RIGHT)))
            .isEqualTo(
                listOf(
                    Node(Coord(1, 0), DOWN),
                    Node(Coord(2, 0), DOWN),
                    Node(Coord(3, 0), DOWN),
                )
            )
    }

    @Test
    fun getNeighbours_rightThrice_noUTurns_andNoRight_centerBoard() {
        assertThat(sample.getNeighbours(Node(Coord(5, 3), RIGHT)))
            .isEqualTo(
                listOf(
                    Node(Coord(6, 3), DOWN),
                    Node(Coord(7, 3), DOWN),
                    Node(Coord(8, 3), DOWN),
                    Node(Coord(4, 3), UP),
                    Node(Coord(3, 3), UP),
                    Node(Coord(2, 3), UP),
                )
            )
    }

    @Test
    fun turnWhatWay() {
        assertThat(sample.getNeighbours(Node(pos=Coord(row=11, col=7), dir=LEFT)))
            .isEqualTo(listOf(
                Node(Coord(12, 7), DOWN),
                Node(Coord(10, 7), UP),
                Node(Coord(9, 7), UP),
                Node(Coord(8, 7), UP),
            ))
    }

    @Test
    fun part1() {
        assertThat(sample.aStarSolution { n: Node -> sample.getNeighbours(n) })
            .isEqualTo(102)
    }

    @Test
    fun part2() {
        assertThat(sample.part2())
            .isEqualTo(94)
    }

    @Test
    fun part2_other() {
        assertThat(Day17.parseInput("othersample").part2())
            .isEqualTo(71)
    }
}