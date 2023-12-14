package info.vervaeke.aoc2023.day14

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day14Test {
    val sample = Day14.parseInput("sample")

    @Test
    fun slideNorth_render() {
        assertThat(sample.slideNorth().render())
            .isEqualTo("""
OOOO.#.O..
OO..#....#
OO..O##..O
O..#.OO...
........#.
..#....#.#
..O..#.O.O
..O.......
#....###..
#....#....""".trimIndent())
    }

    @Test
    fun cycle() {
        assertThat(sample.cycle().render())
            .isEqualTo("""
.....#....
....#...O#
...OO##...
.OO#......
.....OOO#.
.O#...O#.#
....O#....
......OOOO
#...O###..
#..OO#....
""".trimIndent())
    }

    @Test
    fun cycle2() {
        assertThat(sample.cycle().cycle().render())
            .isEqualTo("""
.....#....
....#...O#
.....##...
..O#......
.....OOO#.
.O#...O#.#
....O#...O
.......OOO
#..OO###..
#.OOO#...O
""".trimIndent())
    }

    @Test
    fun cycle3() {
        assertThat(sample.cycle().cycle().cycle().render())
            .isEqualTo("""
.....#....
....#...O#
.....##...
..O#......
.....OOO#.
.O#...O#.#
....O#...O
.......OOO
#...O###.O
#.OOO#...O
""".trimIndent())
    }

    @Test
    fun columnWeight() {
        val north = sample.slideNorth()
        assertThat(north.columnWeight(0))
            .isEqualTo(10 + 9 + 8 + 7)
        assertThat(north.columnWeight(1))
            .isEqualTo(10 + 9 + 8)
        assertThat(north.columnWeight(2))
            .isEqualTo(10 + 4 + 3)
        assertThat(north.columnWeight(3))
            .isEqualTo(10)
        assertThat(north.columnWeight(4))
            .isEqualTo(8)
        assertThat(north.columnWeight(5))
            .isEqualTo(7)
        assertThat(north.columnWeight(6))
            .isEqualTo(7)
        assertThat(north.columnWeight(7))
            .isEqualTo(10 + 4)
        assertThat(north.columnWeight(8))
            .isEqualTo(0)
        assertThat(north.columnWeight(9))
            .isEqualTo(8 + 4)


        assertThat(10 + 9 + 8 + 7 + 10 + 9 + 8 + 10 + 4 + 3 + 10 + 8 + 7 + 7 + 10 + 4 + 0 + 8 + 4)
            .isEqualTo(136)
    }

    @Test
    fun part1() {
        assertThat(sample.part1())
            .isEqualTo(136)
    }

    @Test
    fun part2() {
        assertThat(sample.part2())
            .isEqualTo(64)
    }
}