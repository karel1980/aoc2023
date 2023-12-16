package info.vervaeke.aoc2023.day16

data class Coord(val row: Int, val col: Int)

enum class Direction(val drow: Int, val dcol: Int) {
    UP(-1, 0),
    DOWN(1, 0),
    RIGHT(0, 1),
    LEFT(0, -1);
}

data class Particle(val pos: Coord, val dir: Direction)

data class Day16(val lines: List<String>) {
    val rows = lines.size
    val cols = lines[0].length

    companion object {
        fun parseInput(path: String) = parseLines(readInput(path))
        fun readInput(path: String) = javaClass.getResource(path)!!.readText().lines()
        fun parseLines(lines: List<String>) = Day16(lines)
    }


    fun part1(): Int {
        return countEnergized(Particle(Coord(0, -1), Direction.RIGHT))
    }

    private fun countEnergized(entryPoint: Particle): Int {
        val queue = nextParticles(entryPoint).toMutableList()
        val seen = mutableSetOf<Particle>()
        val energizedTiles = mutableSetOf<Coord>()

        var count = 0
        while (queue.isNotEmpty()) {
            val particle = queue.removeFirst()!!

            energizedTiles.add(particle.pos)
            count++
            if (particle in seen) {
                continue
            }
            seen.add(particle)
            nextParticles(particle).forEach { queue.add(it) }
        }

        return energizedTiles.size
    }

    private fun render(energizedTiles: Set<Coord>) {
        (0 until rows).map { row ->
            lines[row].indices.joinToString("") { col -> if (Coord(row, col) in energizedTiles) "#" else "." }
        }.forEach {
            println(it)
        }
    }

    private fun nextParticles(particle: Particle): List<Particle> {
        val nextPos = Coord(particle.pos.row + particle.dir.drow, particle.pos.col + particle.dir.dcol)
        if (nextPos.row < 0 || nextPos.row >= rows) {
            return emptyList()
        }
        if (nextPos.col < 0 || nextPos.col >= cols) {
            return emptyList()
        }

        return when (lines[nextPos.row][nextPos.col]) {
            '-' -> when (particle.dir) {
                Direction.LEFT -> listOf(Particle(nextPos, particle.dir))
                Direction.RIGHT -> listOf(Particle(nextPos, particle.dir))
                Direction.UP -> listOf(Particle(nextPos, Direction.LEFT), Particle(nextPos, Direction.RIGHT))
                Direction.DOWN -> listOf(Particle(nextPos, Direction.LEFT), Particle(nextPos, Direction.RIGHT))
            }

            '|' -> when (particle.dir) {
                Direction.LEFT -> listOf(Particle(nextPos, Direction.UP), Particle(nextPos, Direction.DOWN))
                Direction.RIGHT -> listOf(Particle(nextPos, Direction.UP), Particle(nextPos, Direction.DOWN))
                Direction.UP -> listOf(Particle(nextPos, particle.dir))
                Direction.DOWN -> listOf(Particle(nextPos, particle.dir))
            }

            '/' -> when (particle.dir) {
                Direction.LEFT -> listOf(Particle(nextPos, Direction.DOWN))
                Direction.RIGHT -> listOf(Particle(nextPos, Direction.UP))
                Direction.UP -> listOf(Particle(nextPos, Direction.RIGHT))
                Direction.DOWN -> listOf(Particle(nextPos, Direction.LEFT))
            }

            '\\' -> when (particle.dir) {
                Direction.LEFT -> listOf(Particle(nextPos, Direction.UP))
                Direction.RIGHT -> listOf(Particle(nextPos, Direction.DOWN))
                Direction.UP -> listOf(Particle(nextPos, Direction.LEFT))
                Direction.DOWN -> listOf(Particle(nextPos, Direction.RIGHT))
            }

            else -> listOf(Particle(nextPos, particle.dir))
        }
    }

    fun part2(): Int {
        return 42
    }

}

fun main() {
    val day = Day16.parseInput("input")
    //8620 is too high
    println("Part 1: ${day.part1()}")
    println("Part 2: ${day.part2()}")
}