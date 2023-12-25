package info.vervaeke.aoc2023.day25

typealias Edge = Pair<String, String>

data class Day25(val edges: List<Edge>) {
    val neighbours = createNeighbours(edges)

    companion object {
        fun parseInput(path: String) = parseLines(readInput(path))
        fun readInput(path: String) = javaClass.getResource(path)!!.readText().lines()
        fun parseLines(lines: List<String>): Day25 {
            return Day25(lines.map { it.split(":") }.flatMap { parts ->
                parts[1].trim().split(" ").map { parts[0] to it.trim() }
            })
        }
    }

    fun createNeighbours(edges: List<Edge>): Map<String, Set<String>> {
        val nodes = edges.flatMap { it.toList() }.toSet()

        return nodes.map { n ->
            n to (edges.filter { it.first == n }.map { it.second } + edges.filter { it.second == n }.map { it.first }).toSet()
        }.toMap()
    }

    fun part1(): Int {
        // select all combinations of 3 nodes
        // remove these and report if there are two partitions
        println("${edges.size}")
        val neighbours = createNeighbours(edges).mapValues { it.value.toMutableSet() }.toMutableMap()

        (0 until edges.size).forEach { a ->
            println("$a")
            (a + 1 until edges.size).forEach { b ->
                println("$b")
                (b + 1 until edges.size).forEach { c ->

                    // remove abc from neighbours
                    neighbours[edges[a].first]!!.remove(edges[a].second)
                    neighbours[edges[b].first]!!.remove(edges[b].second)
                    neighbours[edges[c].first]!!.remove(edges[c].second)
                    neighbours[edges[a].second]!!.remove(edges[a].first)
                    neighbours[edges[b].second]!!.remove(edges[b].first)
                    neighbours[edges[c].second]!!.remove(edges[c].first)

                    val n = findPartition(neighbours)

                    if (n < neighbours.size) {
                        val m = neighbours.size - n
                        println("found partitions $n $m")

                        return n * m
                    }

                    // add abc back
                    neighbours[edges[a].first]!!.add(edges[a].second)
                    neighbours[edges[b].first]!!.add(edges[b].second)
                    neighbours[edges[c].first]!!.add(edges[c].second)
                    neighbours[edges[a].second]!!.add(edges[a].first)
                    neighbours[edges[b].second]!!.add(edges[b].first)
                    neighbours[edges[c].second]!!.add(edges[c].first)

                }
            }
        }
        TODO("no partition found")
    }

    fun findPartition(neighbours: Map<String, Set<String>>): Int {
        val nodes = neighbours.keys

        // bfs to find all connected ones
        val queued = mutableSetOf(nodes.first())
        val visited = mutableSetOf<String>()
        while (queued.isNotEmpty()) {
            val current = queued.first()
            queued.remove(current)
            visited.add(current)

            neighbours[current]!!.forEach {
                if (it !in visited) {
                    queued.add(it)
                }
            }
        }

        return visited.size
    }

    fun part2(): Int {
        return 42
    }

}

data class VLimits(val vxLimits: LongRange, val vyLimits: LongRange, val vzLimits: LongRange)

fun main() {
    val day = Day25.parseInput("input")
    println("Part 1: ${day.part1()}")
    println("Part 2: ${day.part2()}")
}