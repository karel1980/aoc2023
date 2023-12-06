package info.vervaeke.aoc2023.day6

import java.util.regex.Pattern

data class Day6(val times: List<Long>, val records: List<Long>) {

    companion object {
        fun parseInput(path: String) = parse(readInput(path))
        fun readInput(path: String) = javaClass.getResource(path)!!.readText().lines()
        fun parse(lines: List<String>) = Day6(
            lines[0].split(":")[1].trim().split(Pattern.compile(" +"))
                .map { it.toLong() },
            lines[1].split(":")[1].trim().split(Pattern.compile(" +"))
                .map { it.toLong() },
        )
    }

    fun getDistance(totalTime: Long, buttonTime: Long):Long {
        if (buttonTime == 0L) {
            return 0
        }
        val speed = buttonTime;
        val duration = totalTime - buttonTime
        return speed * duration
    }

    fun part1(): Long {
        return times.indices.map {
            timesToImprove(times[it], records[it])
        }.foldRight(1) {a,b -> a*b}
    }

    private fun timesToImprove(time: Long, best: Long): Long {
        return (0L until time)
            .filter { getDistance(time, it) > best }
            .count().toLong()
    }

//    private fun timesToImprove(raceTime: Long, best: Long): Long {
//        // v = speed (= button time)
//        // distance = (raceTime - v) * v
//        // we need to find out the minimum and maximum for the equation
//        // distance = r
//
//        a = 1
//        b = raceTime
//        c = -best
//    }
}


fun main() {
    println("Part1: ${Day6.parseInput("input").part1()}")
}