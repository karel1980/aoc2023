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
                    Node(Coord(0, 1), RIGHT, 1),
                    Node(Coord(1, 0), DOWN, 1)
                )
            )
    }

    @Test
    fun getNeighbours_rightOnce_noUTurns() {
        assertThat(sample.getNeighbours(Node(Coord(0, 1), RIGHT, 1)))
            .isEqualTo(
                listOf(
                    Node(Coord(0, 2), RIGHT, 2),
                    Node(Coord(1, 1), DOWN, 1)
                )
            )
    }

    @Test
    fun getNeighbours_rightTwice_noUTurns() {
        assertThat(sample.getNeighbours(Node(Coord(0, 2), RIGHT, 2)))
            .isEqualTo(
                listOf(
                    Node(Coord(0, 3), RIGHT, 3),
                    Node(Coord(1, 2), DOWN, 1)
                )
            )
    }

    @Test
    fun getNeighbours_rightThrice_noUTurns_andNoRight() {
        assertThat(sample.getNeighbours(Node(Coord(0, 3), RIGHT, 3)))
            .isEqualTo(
                listOf(
                    Node(Coord(1, 3), DOWN, 1)
                )
            )
    }


    @Test
    fun getNeighbours_rightThrice_noUTurns_andNoRight_centerBoard() {
        assertThat(sample.getNeighbours(Node(Coord(5, 3), RIGHT, 3)))
            .isEqualTo(
                listOf(
                    Node(Coord(6, 3), DOWN, 1),
                    Node(Coord(4, 3), UP, 1)
                )
            )
    }

    @Test
    fun turnWhatWay() {
        assertThat(sample.getNeighbours(Node(pos=Coord(row=11, col=7), dir=LEFT, count=3)))
            .isEqualTo(listOf(
                Node(Coord(12, 7), DOWN, 1),
                Node(Coord(10, 7), UP, 1),
            ))
    }

    @Test
    fun part1() {
        assertThat(sample.part1())
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