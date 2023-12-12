package info.vervaeke.aoc2023.day12

import java.util.*
import java.util.regex.Pattern

data class Row(val spec: String, val brokenGroups: List<Int>) {
    // indices of question marks in the spec
    val unknowns = spec.indices.filter { spec[it] == '?' }.mapIndexed { idx, pos -> pos to idx }.toMap()

    // number of '?' or '#' starting from the current location
    val numberOfBrokenCandidatesRightOfLocation = spec.indices.map {
        spec.substring(it).count { it == '?' || it == '#' }
    }

    // given number of remaining broken positions, what's the right most position that has that many positions available
    fun remainingBrokenStartingAt(pos: Int): Int {
        return spec.length - pos
    }

    val pattern = Pattern.compile("\\.+")

    fun countArrangements(): Long {
        if (unknowns.isEmpty()) {
            return 1
        }

        val arrangements = brokenGroups.map { spec.map { 0L }.toMutableList() }
        // arrangements[i][p] holds the number of arrangements that are possible for element i at position p
        // a 0 means it can't be at that position
        var i = brokenGroups.size - 1
        while (i >= 0) {
            // calculate arrangements for group i
            val groupSize = brokenGroups[i]
            (0 until spec.length).forEach { pos ->
                if (groupCanBeAt(i, pos)) {
                    if (i == brokenGroups.size - 1) {
                        arrangements[i][pos] = 1
                    } else {
                        if (pos + groupSize + 2 <= spec.length) {
                            // only count arrangements if there is no # between this group and the next
                            arrangements[i][pos] = (pos + groupSize + 1 until spec.length).filter {
                                !spec.substring(pos + groupSize+1, it).contains("#")
                            }.sumOf {
                                arrangements[i+1][it]
                            }
                        }
                    }
                }
            }
            i--
        }

        // return the sum of arrangements for group 0
        return arrangements[0].sum()
    }

    fun groupCanBeAt(groupNum: Int, pos: Int): Boolean {
        val groupSize = brokenGroups[groupNum]
        if (pos + groupSize > spec.length) {
            // not enough room
            return false
        }
        if (spec.substring(pos, pos + groupSize).contains(".")) {
            // spec part contains non broken part
            return false
        }
        if (groupNum == 0) {
            // extra check: there cannot be a broken item before the first group
            if (spec.substring(0, pos).contains("#")) {
                return false
            }
        } else {
            // there must be at least one blank before every group except the first
            if (pos == 0) {
                return false
            }
            if (spec[pos - 1] != '.' && spec[pos - 1] != '?') {
                return false
            }
        }
        if (groupNum == brokenGroups.size - 1) {
            // there should be no broken parts after this one
            if (pos + groupSize < spec.length && (spec.substring(pos + groupSize).contains("#"))) {
                return false
            } else {
                return true
            }
        } else {
            // there must be a dot after this one
            if (spec.substring(pos + groupSize).startsWith(".") || spec.substring(pos + groupSize).startsWith("?")) {
                return true
            } else {
                return false
            }
        }
    }

    fun unfold(n: Int = 5): Row {
        return Row((0 until n).map { spec }.joinToString("?") { it }, (0 until n).flatMap { brokenGroups })
    }

    fun specMatchesPositions(positions: List<Int>): Boolean {
        val instance = (0 until spec.length).map {
            '.'
        }.toMutableList()
        positions.forEachIndexed { idx, pos ->
            (pos until pos + brokenGroups[idx]).forEach {
                instance[it] = '#'
            }
        }

        return specMatchesInstance(instance)
    }

    private fun specMatchesInstance(instance: MutableList<Char>) = instance.zip(spec.toList()).all {
        (it.second == '?' || it.first == it.second)
    }

    fun matchesInstance(instance: String): Boolean {
        val parts = instance.split(pattern).filter { !it.isEmpty() }
        return parts.map { it.length }.equals(brokenGroups)
    }
}

data class Day12(val rows: List<Row>) {
    companion object {
        fun parseInput(path: String) = parseLines(readInput(path))
        fun readInput(path: String) = javaClass.getResource(path)!!.readText().lines()
        fun parseLines(lines: List<String>): Day12 {
            return Day12(lines.map { parseRow(it) })
        }

        fun parseRow(line: String) = line.split(" ").let {
            Row(it[0], it[1].split(",").map { it.toInt() })
        }
    }

    fun part1(): Long {
        return rows.sumOf { it.countArrangements() }
    }

    fun part2(): Long {
        return rows.map { it.unfold() }.sumOf {
            it.countArrangements()
        }
    }


}

fun createBitSet(n: Int): BitSet {
    return BitSet().apply {
        n.toBigInteger().toString(2).reversed().forEachIndexed { idx, c ->
            if (c == '1') {
                set(idx)
            }
        }
    }
}

fun main() {
    val day = Day12.parseInput("input")
    println("Part 1: ${day.part1()}")
    //499500 is too low
    //55672047646 is too low
    //433520951429150 is too high
    //17391848518844
    println("Part 2: ${day.part2()}")
}