package info.vervaeke.aoc2023.day5

data class RangeMap(val sourceStart: Long, val destinationStart: Long, val size: Long) {
    val offset = destinationStart - sourceStart
    val sourceRange = sourceStart until sourceStart + size

    fun contains(value: Long): Boolean {
        return value in sourceRange
    }

    fun map(value: Long): Long {
        return value + offset
    }
}

data class RangeMapper(val source: String, val destination: String, val rangeMaps: List<RangeMap>) {
    init {
        validate()
    }

    private fun validate() {
        rangeMaps.indices.forEach { i ->
            rangeMaps.indices.forEach { j ->
                if (i != j) {
                    if (rangeMaps[i].sourceRange.start in rangeMaps[j].sourceRange) {
                        TODO("overlapping source ranges - panic")
                    }
                    if (rangeMaps[i].sourceRange.endInclusive in rangeMaps[j].sourceRange) {
                        TODO("overlapping source ranges - panic")
                    }
                }
            }
        }
    }

    fun mapInputRanges(inputRanges: List<LongRange>) = inputRanges.flatMap { mapSingleRange(it, rangeMaps) }

    fun mapSingleRange(inputRange: LongRange) =
        split(inputRange, rangeMaps).map { it.first.start + it.second..it.first.endInclusive + it.second }

    fun toDestination(value: Long) = rangeMaps.firstOrNull { it.contains(value) }?.map(value) ?: value

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
    val mappers = listOf(
        seedToSoil,
        soilToFertilizer,
        fertilizerToWater,
        waterToLight,
        lightToTemperature,
        temperatureToHumidity,
        humidityToLocation
    )
    val seedRanges = (0 until seeds.size).step(2).map {
        seeds[it] until (seeds[it] + seeds[it + 1])
    }

    companion object {
        fun parseInput(path: String) = createAlmanac(readInput(path))
        fun readInput(path: String) = javaClass.getResource(path)!!.readText().lines()

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
    }

    fun part1(): Long {
        return seeds.minOf {
            seedToLocation(it)
        }
    }

    fun part2(): Long {
        return minimumLocationFromSeedRange(seedRanges)
    }


    fun seedToLocation(seed: Long): Long {
        val soil = seedToSoil.toDestination(seed)
        val fert = soilToFertilizer.toDestination(soil)
        val wat = fertilizerToWater.toDestination(fert)
        val light = waterToLight.toDestination(wat)
        val temp = lightToTemperature.toDestination(light)
        val hum = temperatureToHumidity.toDestination(temp)
        val loc = humidityToLocation.toDestination(hum)
        return loc
    }

    fun seedsToLocations(seeds: List<LongRange>): List<LongRange> {
        return mappers
            .fold(seeds) { ranges, rangeMapper -> rangeMapper.mapInputRanges(ranges) }
    }

    fun minimumLocationFromSeedRange(seeds: List<LongRange>) = seedsToLocations(seeds).minOf { it.start }
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

    val destination = header.split("-")[2].split(" ")[0]
    return RangeMapper(source, destination, maplines.map { it.toRange() })
}

private fun String.toRange(): RangeMap {
    val parts = this.split(" ").map { it.toLong() }
    return RangeMap(parts[1], parts[0], parts[2])
}

fun main() {
    val sample = Almanac.parseInput("sample")
    val input = Almanac.parseInput("input")

    println("Part 1 sample: ${sample.part1()}")

    //9849901 too low
    println("Part 1 real: ${input.part1()}")

    println("Part 2 sample: ${sample.part2()}")

    println("Part 2 real: ${input.part2()}")
}

fun mapSingleRange(inputRange: LongRange, rangeMaps: List<RangeMap>): List<LongRange> {
    val sections = split(inputRange, rangeMaps)
    return sections.map { it.first.start + it.second..it.first.endInclusive + it.second }
}

fun split(inputRange: LongRange, rangeMaps: List<RangeMap>): List<Pair<LongRange, Long>> {
    val changePoints = buildList {
        add(inputRange.start)
        add(inputRange.endInclusive + 1)
        rangeMaps.forEach {
            add(it.sourceRange.start)
            add(it.sourceRange.endInclusive + 1)
        }
    }.sorted()

    val sections = changePoints.dropLast(1).zip(changePoints.drop(1))
        .map { it.first until it.second }
        .filter { it.start in inputRange }
    return sections.map { section ->
        section to (rangeMaps.firstOrNull { section.start in it.sourceRange }?.offset ?: 0L)
    }
}
