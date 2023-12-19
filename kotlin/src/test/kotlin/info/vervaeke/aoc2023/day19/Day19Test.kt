package info.vervaeke.aoc2023.day19

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.math.BigInteger

class Day19Test {

    val sample = Day19.parseInput("sample")

    @Test
    fun parseWorkflow() {
        println(sample.workflows)
        assertThat(sample.workflows["px"])
            .isEqualTo(
                Workflow(
                    "px",
                    listOf(
                        Compare("a", "<", 2006, "qkq"),
                        Compare("m", ">", 2090, "A"),
                        GoTo("rfg")
                    )
                )
            )
    }

    @Test
    fun parseParts() {
        assertThat(sample.parts[0])
            .isEqualTo(
                Part(787, 2655, 1222, 2876)
            )
    }

    @Test
    fun processPart() {
        assertThat(sample.processPart(sample.parts[0]))
            .isEqualTo("A")
        assertThat(sample.processPart(sample.parts[1]))
            .isEqualTo("R")
        assertThat(sample.processPart(sample.parts[2]))
            .isEqualTo("A")
        assertThat(sample.processPart(sample.parts[3]))
            .isEqualTo("R")
        assertThat(sample.processPart(sample.parts[4]))
            .isEqualTo("A")
    }

    @Test
    fun part1() {
        assertThat(sample.part1())
            .isEqualTo(19114)
    }

    @Test
    fun part2() {
        assertThat(sample.part2())
            .isEqualTo(BigInteger.valueOf(167409079868000L))
    }
}