package info.vervaeke.aoc2023.day14

data class Day14(val lines: List<MutableList<String>>) {
    val rows = lines.size
    val cols = lines[0].size

    companion object {
        fun parseInput(path: String) = parseLines(readInput(path))
        fun readInput(path: String) = javaClass.getResource(path)!!.readText().lines()
        fun parseLines(lines: List<String>): Day14 {
            return Day14(lines.map { it.toCharArray().map { it.toString() }.toMutableList() })
        }
    }

    fun slideNorth(): Day14 {
        val newLines = lines.map { it.toMutableList() }
        (0 until cols).forEach {
            slideNorth(newLines, it)
        }
        return Day14(newLines)
    }

    private fun slideNorth(newLines: List<MutableList<String>>, col: Int) {
        val column = newLines.map { it[col] }
        val roundRockPositions = column.indices.filter { column[it] == "O" }
        roundRockPositions.forEach { pos ->
            var newPos = pos
            while (newPos > 0 && newLines[newPos - 1][col] == ".") {
                newPos--
            }
            if (pos != newPos) {
                newLines[newPos][col] = "O"
                newLines[pos][col] = "."
            }
        }
    }

    fun render(): String {
        return lines.joinToString("\n") { it.joinToString("") }
    }

    fun part1(): Int {
        return slideNorth().calculateWeight()
    }

    fun calculateWeight(): Int {
        return (0 until cols).sumOf {
            columnWeight(it)
        }
    }

    fun columnWeight(col: Int): Int {
        return (0 until rows).filter { lines[it][col] == "O" }
            .map { rows - it }
            .sum()
    }

    fun part2(): Int {
        val n = 1_000_000_000 // the cycle that we're interested in
        var result = this
        var cycles = 0
        val known = mutableMapOf<String, Int>()
        while (result.signature() !in known) {
            known[result.signature()] = cycles

            result = result.cycle()
            cycles++
        }
        val first = known[result.signature()]!!
        val second = cycles
        val cycleLength = second - first

        val numCycles = (n - first) / cycleLength

        val equivalentCycle = first + numCycles * cycleLength

        println("first: $first")
        println("second: $second")
        println("cycleLength: $cycleLength")
        println("result")
        println(result.render())
        println()

        println("cycles: $cycles")
        println("equivalent cycle: $equivalentCycle")

        (equivalentCycle until n).forEach {
            result = result.cycle()
        }

        return result.calculateWeight()
    }

    fun cycle(): Day14 {
        var result = this
        (0 until 4).forEach {
            result = result.slideNorth()
            result = result.rotateLeft()
        }
        return result
    }

    private fun signature() = render()

    private fun rotateLeft(): Day14 {
        val newLines = (0 until cols).map { col ->
            (0 until rows).map { row ->
                lines[rows - row - 1][col]
            }.toMutableList()
        }

        return Day14(newLines)
    }
}

fun main() {
    val day = Day14.parseInput("input")
    println("Part 1: ${day.part1()}")
    println("Part 2: ${day.part2()}")
}