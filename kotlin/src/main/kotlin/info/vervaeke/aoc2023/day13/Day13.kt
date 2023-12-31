package info.vervaeke.aoc2023.day13


data class Grid(val lines: List<String>) {
    val rows = 0 until lines.size
    val cols = 0 until lines[0].length

    fun flip(row: Int, col: Int): Grid {
        return Grid(lines.mapIndexed { idx, it ->
            if (idx == row) {
                it.substring(0, col) + flip(it[col]) + it.substring(col + 1)
            } else {
                it
            }
        })
    }

    fun flip(char: Char) = when (char) {
        '.' -> '#'
        '#' -> '.'
        else -> TODO("oops")
    }

    fun rotate(): Grid {
        val rows = lines.size
        val cols = lines[0].length

        return Grid((0 until cols).map { col ->
            (0 until rows).map { row ->
                lines[row][col]
            }.joinToString("")
        })
    }

    fun horizontalReflection(exclude: Int? = null): Int? {
        (1 until lines.size).map {
            if (it == exclude) {
                return@map
            }
            val top = lines.subList(0, it).reversed()
            val bottom = lines.subList(it, lines.size)

            if (top.isEmpty() || bottom.isEmpty()) {
                TODO("check boundaries")
            }
            if (top.size <= bottom.size) {
                if (top == bottom.subList(0, top.size)) {
                    return it
                }
            }
            if (top.size > bottom.size) {
                if (top.subList(0, bottom.size) == bottom) {
                    return it
                }
            }
        }
        return null
    }

    fun verticalReflection(exclude: Int? = null) = rotate().horizontalReflection(exclude)

    fun part1Score(): Int {
        val h = horizontalReflection() ?: 0
        val v = verticalReflection() ?: 0
        return h * 100 + v
    }

    fun part2Score(): Int {
        val origH = horizontalReflection()
        val origV = verticalReflection()
        rows.forEach { row ->
            cols.forEach { col ->
                val cleaned = flip(row, col)
                val h = cleaned.horizontalReflection(origH)
                val v = cleaned.verticalReflection(origV)
                if (h != null && h != origH) {
                    return h * 100
                }
                if (v != null && v != origV) {
                    return v
                }
            }
        }

        TODO("Did not find a different line of reflection")
    }
}

data class Day13(val grids: List<Grid>) {
    companion object {
        fun parseInput(path: String) = parseLines(readInput(path))
        fun readInput(path: String) = javaClass.getResource(path)!!.readText().lines()
        fun parseLines(lines: List<String>): Day13 {
            val stops = lines.indices.filter { lines[it] == "" }
            return Day13((listOf(-1) + stops).zip(stops + listOf(lines.size)).map {
                Grid(lines.subList(it.first + 1, it.second))
            })
        }
    }

    fun part1(): Int {
        return grids.sumOf { it.part1Score() }
    }

    fun part2(): Int {
        return grids.sumOf { it.part2Score() }
    }
}

fun main() {
    val day = Day13.parseInput("input")
    //11344 is too low
    //31956 OK
    println("Part 1: ${day.part1()}")
    //27950 is too low
    println("Part 2: ${day.part2()}")
}