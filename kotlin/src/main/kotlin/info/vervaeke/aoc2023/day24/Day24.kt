package info.vervaeke.aoc2023.day24

data class Stone(val px: Long, val py: Long, val pz: Long, val vx: Long, val vy: Long, val vz: Long) {
    val a = 1.0 * vy
    val b = -1.0 * vx
    val c = 1.0 * py * vx - 1.0 * vy * px

    init {
        if (vx == 0L) {
            TODO()
        }
        if (vy == 0L) {
            TODO()
        }
    }

    companion object {
        fun parse(spec: String): Stone {
            val parts = spec.split("@")

            val pos = parts[0].split(", ").map { it.trim().toLong() }
            val vel = parts[1].split(", ").map { it.trim().toLong() }

            return Stone(pos[0], pos[1], pos[2], vel[0], vel[1], vel[2])
        }
    }

    fun crosspoint(other: Stone): Coord2d? {
        val d = 1.0 * a * other.b - b * other.a
        if (d == 0.0) {
            // parallel
            return null
        }

        val nx = 1.0 * b * other.c - 1.0 * c * other.b
        val ny = 1.0 * c * other.a - 1.0 * a * other.c

        return Coord2d(nx / d, ny / d)
    }

    fun isInTheFuture(point: Coord2d): Boolean {
        if (point.x > px && vx > 0 || point.x < px && vx < 0) {
            return true
        } else {
            return false
        }
    }

    fun format(): String {
        return "%d, %d, %d @ %d, %d, %d".format(px, py, pz, vx, vy, vz)
    }
}

data class Coord2d(val x: Double, val y: Double) {

}

data class Day24(val stones: List<Stone>) {
    val rows = stones.size

    companion object {
        fun parseInput(path: String) = parseLines(readInput(path))
        fun readInput(path: String) = javaClass.getResource(path)!!.readText().lines()
        fun parseLines(lines: List<String>): Day24 {
            return Day24(lines.map { Stone.parse(it) })
        }
    }

    fun part1(): Int {
        return countInside(200000000000000.0, 400000000000000.0)
    }

    fun part2(): Int {
        return 42
    }

    fun countInside(low: Double, high: Double): Int {
        return stones.indices.sumOf { i ->
            (i+1 until stones.size).count { j ->
                val a = stones[i]
                val b = stones[j]
                println("Hailstone A: ${a.format()}")
                println("Hailstone B: ${b.format()}")
                val crosspoint = a.crosspoint(b)
                if (crosspoint == null) {
                    println("Hailstones' paths are parallel; they never intersect.")
                    println()
                    false
                } else {
                    println("crosspoint: $crosspoint")
                    val isInside = low <= crosspoint.x && crosspoint.x <= high &&
                            low <= crosspoint.y && crosspoint.y <= high
                    val isInside2 = low - 0.01 <= crosspoint.x && crosspoint.x <= high + 0.01 &&
                            low - 0.01 <= crosspoint.y && crosspoint.y <= high + 0.01

                    if (isInside != isInside2) {
                        println("$a $b $crosspoint")
                        TODO("quick and dirty check for mean edge cases")
                    }

                    val futureA = a.isInTheFuture(crosspoint)
                    val futureB = b.isInTheFuture(crosspoint)
                    if (futureA && futureB) {
                        if (isInside) {
                            println("Hailstones' paths will cross inside the test area (at x=$crosspoint.x, y=${crosspoint.y}).")
                            println()
                            true
                        } else {
                            println("Hailstones' paths will cross outside the test area (at x=$crosspoint.x, y=${crosspoint.y}).")
                            println()
                            false
                        }
                    } else {
                        if (!futureA && !futureB) {
                            println("Hailstones' paths crossed in the past for both hailstones.")
                            println()
                        } else if (!futureA) {
                            println("Hailstones' paths crossed in the past for hailstone A.")
                            println()
                        } else {
                            println("Hailstones' paths crossed in the past for hailstone B.")
                            println()
                        }
                        false
                    }
                }
            }
        }
    }


}

fun main() {
    val day = Day24.parseInput("input")
    // 12431 is too low
    // 16512 is too low
    // 17445 is too low
    // 25433
    println("Part 1: ${day.part1()}")
    println("Part 2: ${day.part2()}")
}