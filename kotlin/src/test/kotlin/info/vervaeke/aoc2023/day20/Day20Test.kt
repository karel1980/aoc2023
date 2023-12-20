package info.vervaeke.aoc2023.day20

import com.google.common.math.LongMath
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.math.BigInteger

class Day20Test {
    val sample = Day20.parseInput("sample")
    val sample2 = Day20.parseInput("sample2")
    val real = createReal()

    private fun createReal() = Day20.parseInput("input")

    @Test
    fun part1_sample() {
        Assertions.assertThat(sample.part1())
            .isEqualTo(32000000L)
    }

    @Test
    fun part1_sample2() {
        Assertions.assertThat(sample2.part1())
            .isEqualTo(11687500L)
    }

    @Test
    fun part2() {
        Assertions.assertThat(sample.part2())
            .isEqualTo(42)
    }

    @Test
    fun visualiseGraph() {
        real.renderDot()
    }

    @Test
    fun cycleTest() {
//        println(primeFactors(242733240010887L))
        createReal().analysis()


        Assertions.assertThat(false)
            .isEqualTo(true)
    }
}