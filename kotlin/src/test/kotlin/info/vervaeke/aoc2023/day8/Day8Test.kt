package info.vervaeke.aoc2023.day8

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.math.BigInteger

class Day8Test {

    val day8 = Day8.parseInput("sample")

    @Test
    fun part1() {
        Assertions.assertThat(day8.part1())
            .isEqualTo(2)
    }

    @Test
    fun part1_LLR() {
        Assertions.assertThat(Day8.parseInput("sample2").part1())
            .isEqualTo(6)
    }

    @Test
    fun part2() {
        Assertions.assertThat(Day8.parseInput("sample3").part2())
            .isEqualTo(6)
    }

    @Test
    fun part2_cycletest() {
        val realDay8 = Day8.parseInput("input")

        println(realDay8.instructions.length)

        var cycles = buildList {
            realDay8.nodesById.keys.filter { it.endsWith("A") }.forEach {
                println(it)
                val firstZ = realDay8.cycle(it)
                println(firstZ)
                add(BigInteger.valueOf(firstZ.toLong()))
            }
        }

        println(findLCM(cycles))
    }

    // chatgpt

    fun findLCM(numbers: List<BigInteger>): BigInteger {
        if (numbers.isEmpty()) {
            throw IllegalArgumentException("List cannot be empty")
        }

        var lcm = numbers[0].abs()

        for (i in 1 until numbers.size) {
            lcm = lcm(lcm, numbers[i])
        }

        return lcm
    }

    fun lcm(a: BigInteger, b: BigInteger): BigInteger {
        return a.multiply(b).divide(a.gcd(b))
    }
}