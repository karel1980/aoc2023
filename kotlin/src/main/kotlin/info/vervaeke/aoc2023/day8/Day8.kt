package info.vervaeke.aoc2023.day8

data class Node(val left: String, val right: String)

data class Day8(val instructions: String, val nodesById: Map<String, Node>) {

    companion object {
        fun parseInput(path: String) = parseLines(readInput(path))
        fun readInput(path: String) = javaClass.getResource(path)!!.readText().lines()
        fun parseLines(lines: List<String>): Day8 {
            val instructions = lines[0].trim()

            val nodesById = lines.drop(2).map {
                val parts = it.trim().split("=")
                val id = parts[0].trim()
                val connections = parts[1].trim().drop(1).dropLast(1).split(",")
                id to Node(connections[0].trim(), connections[1].trim())
            }.toMap()

            return Day8(instructions, nodesById)
        }
    }

    fun part1(): Int {
        return navigationDistance("AAA", "ZZZ")
    }

    private fun navigationDistance(start: String, end: String): Int {
        var distance = 0;
        var current = start;

        while (current != end) {
            current = nextLocation(current, distance%instructions.length)
            distance++
        }
        return distance
    }

    private fun nextLocation(current: String, offset: Int): String {
        val instruction = instructions[offset].toString()
        val node = nodesById[current]!!
        return if (instruction == "L") {
            node.left
        } else {
            node.right
        }
    }

    fun part2(): Long {
        var currentNodes = nodesById.keys.filter { it.endsWith("A") }
        var distance = 0L
        var offset = 0;

        while (!currentNodes.all { it.endsWith("Z") }) {
            if (distance < 10) {
                println(currentNodes)
                println(instructions[offset])
            }
            currentNodes = currentNodes.map {
                nextLocation(it, offset)
            }
            distance++
            offset++
            offset %= instructions.length
        }

        return distance
    }

    fun cycle(start: String): Int {
        var distance = 0;
        var current = start;

        while (!current.endsWith("Z")) {
            current = nextLocation(current, distance%instructions.length)
            distance++
        }
        return distance
    }

}


fun main() {
    val day = Day8.parseInput("input")
    println("Part 1: ${day.part1()}")
    // 31287194289918716640809369 is too high
    println("Part 2: ${day.part2()}")
}