package info.vervaeke.aoc2023.day25

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day25Test {
    val sample = Day25.parseInput("sample")
    val real = Day25.parseInput("input")


    @Test
    fun parse() {
        assertThat(sample.edges)
            .contains("jqt" to "rhn")

        assertThat(sample.neighbours["jqt"])
            .containsExactly("rhn", "xhk", "nvd", "ntq")
    }

    @Test
    fun analyze() {
        println("graph {")
        real.edges.forEach {
            println("${it.first} -- ${it.second};")
        }
        println("}")
    }

    @Test
    fun findPartition() {
        assertThat(sample.edges.size)
            .isEqualTo(33)
        val selectedEdges = sample.edges.filter {
            it.toList().toSet() != setOf("hfx", "pzl") &&
                    it.toList().toSet() != setOf("bvb", "cmg") &&
                    it.toList().toSet() != setOf("nvd", "jqt")
        }

        assertThat(selectedEdges.size)
            .isEqualTo(30)

        val neighbours = sample.createNeighbours(selectedEdges)
        assertThat(neighbours.size)
            .isEqualTo(15)

        val sizeOfPartition = sample.findPartition(neighbours)

        assertThat(sizeOfPartition)
            .isEqualTo(9)
    }

    @Test
    fun part1() {
        assertThat(sample.part1())
            .isEqualTo(9 * 6)
    }

    @Test
    fun part1_cheatmode_activated() {
        // looking at the 'neato' formatted dot file you can immediately spot the 3 conections:
        // "gbc", "hxr"
        // "mvv", "xkz"
        // "tmt", "pnz"

        val filteredEdges = real.edges.filter {
            it.toList().toSet() != setOf("gbc", "hxr") &&
                    it.toList().toSet() != setOf("mvv", "xkz") &&
                    it.toList().toSet() != setOf("tmt", "pnz")
        }
        val neighbours = real.createNeighbours(filteredEdges)
        val partition = real.findPartition(neighbours)
        println(partition * (neighbours.size - partition))
    }

    @Test
    fun part2() {
        assertThat(sample.part2())
            .isEqualTo(42)
    }
}