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
                    Node(Coord(0, 1), listOf(RIGHT)),
                    Node(Coord(1, 0), listOf(DOWN))
                )
            )
    }

    @Test
    fun getNeighbours_rightOnce_noUTurns() {
        assertThat(sample.getNeighbours(Node(Coord(0, 1), listOf(RIGHT))))
            .isEqualTo(
                listOf(
                    Node(Coord(0, 2), listOf(RIGHT, RIGHT)),
                    Node(Coord(1, 1), listOf(RIGHT, DOWN))
                )
            )
    }

    @Test
    fun getNeighbours_rightTwice_noUTurns() {
        assertThat(sample.getNeighbours(Node(Coord(0, 2), listOf(RIGHT, RIGHT))))
            .isEqualTo(
                listOf(
                    Node(Coord(0, 3), listOf(RIGHT, RIGHT, RIGHT)),
                    Node(Coord(1, 2), listOf(RIGHT, RIGHT, DOWN))
                )
            )
    }

    @Test
    fun getNeighbours_rightThrice_noUTurns_andNoRight() {
        assertThat(sample.getNeighbours(Node(Coord(0, 3), listOf(RIGHT, RIGHT, RIGHT))))
            .isEqualTo(
                listOf(
                    Node(Coord(1, 3), listOf(RIGHT, RIGHT, DOWN))
                )
            )
    }


    @Test
    fun getNeighbours_rightThrice_noUTurns_andNoRight_centerBoard() {
        assertThat(sample.getNeighbours(Node(Coord(5, 3), listOf(RIGHT, RIGHT, RIGHT))))
            .isEqualTo(
                listOf(
                    Node(Coord(6, 3), listOf(RIGHT, RIGHT, DOWN)),
                    Node(Coord(4, 3), listOf(RIGHT, RIGHT, UP))
                )
            )
    }

    @Test
    fun part1() {
        assertThat(sample.part1())
            .isEqualTo(102)
    }
}