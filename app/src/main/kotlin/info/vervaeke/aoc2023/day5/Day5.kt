package info.vervaeke.aoc2023.day5

data class Range(val sourceStart: Long, val destinationStart: Long, val size: Long) {
    fun contains(value: Long): Boolean {
        return value >= sourceStart && value < (sourceStart + size);
    }

    fun map(value: Long): Long {
        return destinationStart + (value - sourceStart)
    }
}

data class RangeMapper(val source: String, val destination: String, val ranges: List<Range>) {
    fun toDestination(value: Long): Long {
        return ranges.firstOrNull { it.contains(value) }?.map(value) ?: value
    }
}

class Almanac(
    val seeds: List<Long>,
    val seedToSoil: RangeMapper,
    val soilToFertilizer: RangeMapper,
    val fertilizerToWater: RangeMapper,
    val waterToLight: RangeMapper,
    val lightToTemperature: RangeMapper,
    val temperatureToHumidity: RangeMapper,
    val humidityToLocation: RangeMapper,
) {
    fun seedToLocation(seed: Long): Long {
        val soil = seedToSoil.toDestination(seed)
        val fert = soilToFertilizer.toDestination(soil)
        val wat = fertilizerToWater.toDestination(fert)
        val light = waterToLight.toDestination(wat)
        val temp = lightToTemperature.toDestination(light)
        val hum = temperatureToHumidity.toDestination(temp)
        val loc = humidityToLocation.toDestination(hum)

        println("seed $seed")
        println("soil $soil")
        println("fert $fert")
        println("water $wat")
        println("light $light")
        println("temp $temp")
        println("hum $hum")
        println("loc $loc")
        println()

        return loc
    }
}

fun createAlmanac(lines: List<String>): Almanac {
    val seeds = lines[0].split(":")[1].trim().split(" ").map { it.toLong() }

    val sts = createRangeMapper("seed", lines)
    val stf = createRangeMapper("soil", lines)
    val ftw = createRangeMapper("fertilizer", lines)
    val wtl = createRangeMapper("water", lines)
    val ltt = createRangeMapper("light", lines)
    val tth = createRangeMapper("temperature", lines)
    val htl = createRangeMapper("humidity", lines)

    return Almanac(seeds, sts, stf, ftw, wtl, ltt, tth, htl)
}

fun createRangeMapper(source: String, lines: List<String>): RangeMapper {
    val first = lines.indexOfFirst { it.startsWith("$source-") }
    val last = lines.subList(first, lines.size).indexOfFirst { it.isEmpty() }

    val header = lines[first]
    val maplines = lines.subList(
        first + 1, if (last == -1) {
            lines.size
        } else first + last
    )

//    println(header)
//    println(maplines)

    val destination = header.split("-")[1]
    return RangeMapper(destination, source, maplines.map { it.toRange() })
}

private fun String.toRange(): Range {
    val parts = this.split(" ").map { it.toLong() }
    return Range(parts[1], parts[0], parts[2])
}

class Day5(val lines: List<String>) {
    val data = createAlmanac(lines)

    companion object {
        fun readInput(path: String) = parseLines(javaClass.getResource(path)!!.readText().lines())
        fun parseLines(lines: List<String>) = lines.map(::parseLine)
        fun parseLine(line: String) = line.trim()
    }

    fun part1(): Long {
        return data.seeds.minOf {
            data.seedToLocation(it)
        }
    }

    fun part2(): Long {
        return 42
    }
}

fun main() {
    val sample = Day5.readInput("sample")
    val input = Day5.readInput("input")

    println("Part 1 sample: ${Day5(sample).part1()}")

    //9849901 too low
    println("Part 1 real: ${Day5(input).part1()}")

    println("Part 2 sample: ${Day5(sample).part2()}")

    println("Part 2 real: ${Day5(input).part2()}")
}

