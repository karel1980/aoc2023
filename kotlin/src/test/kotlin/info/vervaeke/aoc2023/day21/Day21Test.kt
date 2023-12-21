package info.vervaeke.aoc2023.day21

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class Day21Test {
    val sample = Day21.parseInput("sample")
    val real = Day21.parseInput("input")

    @Test
    fun part1() {
        Assertions.assertThat(sample.countPlots(6))
            .isEqualTo(16)
    }

    @Test
    fun part2() {
        Assertions.assertThat(compute(26501365/131))
            .isEqualTo(1L)
    }

    @Test
    fun analyze() {
        real.analyze()
    }

    @Test
    fun tryPart2() {
        validateN(1)
        validateN(2)
        validateN(3)
        validateN(4)
    }

    private fun validateN(n: Int) {
        println("$n: simulated: ${simulate(n)}")
        println("$n: computed : ${compute(n)}")
//        Assertions.assertThat(computeNew(1))
//            .isEqualTo(real.takeNSteps(n * 131 + 65).size)
    }

    private fun simulate(n: Int) = real.takeNSteps(n * 131 + 65).size

    fun compute(n: Int): Long {
        var n = n-1
        val a = 14655L
        val b = 44085L
        val c = 33150L

        return a * n * n + b * n + c
    }
}