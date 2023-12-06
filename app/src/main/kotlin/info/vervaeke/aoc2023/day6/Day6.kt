package info.vervaeke.aoc2023.day6

import java.util.regex.Pattern
import kotlin.math.sqrt

const val makeItFaster = true

data class Day6(val times: List<Long>, val records: List<Long>) {

    companion object {
        fun parseInput(path: String) = parseLines(readInput(path))
        fun readInput(path: String) = javaClass.getResource(path)!!.readText().lines()
        fun parseLines(lines: List<String>) = Day6(
            lines[0].split(":")[1].trim().split(Pattern.compile(" +"))
                .map { it.toLong() },
            lines[1].split(":")[1].trim().split(Pattern.compile(" +"))
                .map { it.toLong() },
        )
    }

    fun part1(): Long {
        val findWaysToImprove = times.zip(records).map { (raceTime, best) ->
            numberOfWaysToImprove(raceTime, best)
        }

        return calculateProduct(findWaysToImprove)
    }

    private fun calculateProduct(findWaysToImprove: List<Long>): Long =
        findWaysToImprove.foldRight(1) { a, b -> a * b }

    fun part2(): Long {
        val joinedTime = times.joinToString("").toLong()
        val joinedRecords = records.joinToString("").toLong()
        return numberOfWaysToImprove(joinedTime, joinedRecords)
    }

    fun getDistance(totalTime: Long, buttonTime: Long): Long {
        if (buttonTime == 0L) {
            return 0
        }
        val speed = buttonTime;
        val duration = totalTime - buttonTime
        return speed * duration
    }

    private fun numberOfWaysToImprove(time: Long, best: Long): Long {
        if (makeItFaster) {
            return calculateNumberOfWaysToImprove(time, best)
        } else {
            return countNumberOfWaysToImprove(time, best)
        }
    }

    fun countNumberOfWaysToImprove(time: Long, best: Long): Long {
        return (0L until time)
            .filter { getDistance(time, it) > best }
            .count().toLong()
    }

    fun calculateNumberOfWaysToImprove(raceTime: Long, best: Long): Long {
        // v = speed (= button time)
        // distance = (raceTime - v) * v

        // we need to find out the solutions for the inequality
        // distance > best

        // instead we'll find the min and max value by solving the equality
        // distance = best
        // <=> (raceTime - v) * v == best
        // <=> -v^2 + raceTime * v - best == 0
        // let's use standard notation
        // x = v (== speed, or the button time)
        // a = -1
        // b = raceTime
        // c = -best

        val a = -1
        val b = raceTime
        val c = -best

        val quadraticSolution = solveQuadraticEquation(a.toDouble(), b.toDouble(), c.toDouble())
        val minDouble = quadraticSolution.first!!

        val min = minDouble.toLong() + 1 // this is the first long value that improves the record
        val max = raceTime - min // it's symmetric, but we could also
        return max - min + 1
    }
}

// chatgpt
private fun solveQuadraticEquation(a: Double, b: Double, c: Double): Pair<Double?, Double?> {
    val discriminant = b * b - 4 * a * c

    if (discriminant < 0) {
        // No real roots
        return Pair(null, null)
    }

    val root1 = (-b + sqrt(discriminant)) / (2 * a)
    val root2 = (-b - sqrt(discriminant)) / (2 * a)

    return Pair(root1, root2)
}


fun main() {
    println("Part1: ${Day6.parseInput("input").part1()}")
    println("Part2: ${Day6.parseInput("input").part2()}")
}