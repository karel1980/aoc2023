package info.vervaeke.aoc2023.day23

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day23Test {
    val sample = Day23.parseInput("sample")
    val real = Day23.parseInput("input")

    @Test
    fun part1() {
        assertThat(sample.part1())
            .isEqualTo(94)
    }

    @Test
    fun findAllUniquePaths() {
        assertThat(sample.findAllUniquePaths())
            .containsExactlyInAnyOrder(90, 86, 82, 82, 74, 94)
    }

    @Test
    fun part2() {
        assertThat(sample.part2())
            .isEqualTo(154)
    }

    @Test
    fun createPosToNeighbours() {
        sample.createPosToNeighbours().forEach { k, v ->
            println(k)
            v.forEach {
                println("    $it")
            }
        }
    }

    @Test
    fun analysis() {
        val day = real.replaceSlopes()
        (1 until day.rows - 1).forEach { r ->
            (1 until day.cols - 1).forEach { c ->
                val l = day.lines
                val cs = listOf(l[r - 1][c], l[r + 1][c], l[r][c - 1], l[r][c + 1], l[r][c])
                    .count { it == '.' }

                if (cs > 3) {
                    println(r to c)
                }
            }
        }
    }

    @Test
    fun createGraph() {
        val sample = sample.createGraph()
        assertThat(sample.find { it.from == Coord(0, 1) })
            .isEqualTo(Edge(from = Coord(row = 0, col = 1), to = Coord(row = 1, col = 1), 1))
    }

    @Test
    fun simplifyGraph() {
        assertThat(sample.replaceSlopes().createGraph().any { it.from == Coord(4, 3) || it.to == Coord(4, 3) })
            .isTrue

        val edges = sample.simplifyGraph(sample.createGraph())

        edges.forEach { println(it) }
        assertThat(edges.filter { it.from == Coord(0, 1) || it.to == Coord(0, 1) })
            .containsExactly(Edge(Coord(0, 1), Coord(5, 3), 15))
    }
}