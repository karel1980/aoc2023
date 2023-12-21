package info.vervaeke.aoc2023.day21

data class Coord(val row: Int, val col: Int) {
    infix operator fun plus(dir: Direction): Coord {
        return Coord(row + dir.drow, col + dir.dcol)
    }
}

enum class Direction(val drow: Int, val dcol: Int) {
    NORTH(-1, 0),
    SOUTH(1, 0),
    EAST(0, 1),
    WEST(0, -1)
}

data class Day21(val lines: List<String>) {
    val padding = 0
    val grid = (0..2 * padding).flatMap { lines.map { it.replace("S", ".").repeat(2 * padding + 1) } }

    val numRows = grid.size
    val rows = 0 until numRows
    val numCols = grid[0].length
    val cols = 0 until numCols

    val startPos = lines.indexOfFirst { it.contains("S") }.let { Coord(lines.size * padding + it, lines[0].length * padding + lines[it].indexOf("S")) }

    companion object {
        fun parseInput(path: String) = parseLines(readInput(path))
        fun readInput(path: String) = javaClass.getResource(path)!!.readText().lines()
        fun parseLines(lines: List<String>): Day21 {
            return Day21(lines)
        }
    }

    fun part1(): Int {
        return countPlots(64)
    }

    fun countPlots(steps: Int): Int {
        var plots = setOf(startPos)

        repeat(steps) {
            plots = takeStep(plots)
        }

        return plots.size
    }

    fun takeStep(plots: Set<Coord>): Set<Coord> {
        return plots.flatMap { validNeighboursOf(it) }
            .toSet()
    }

    private fun validNeighboursOf(pos: Coord): Set<Coord> {
        return Direction.entries.map {
            pos + it
//        }.filter {
//            isInsideGrid(it)
        }.filter {
            isNotAWall(it)
        }.toSet()
    }

    private fun isInsideGrid(pos: Coord): Boolean {
        return pos.row in rows && pos.col in cols
    }

    private fun isNotAWall(pos: Coord): Boolean {
        return grid[(131 * 1_000_000 + pos.row) % (rows.last + 1)][(131 * 1_000_000 + pos.col) % (cols.last + 1)] != '#'
    }

    fun part2(): Long {
        // at 65 + 131 we have a full center square + 4 'cones of plots' -> determine the 4 cones
        // at 65 + 2*131 we have 1 + 4*1 full squares + 4 'cones of plots'
        // at 65 + n*131 have 1+ 4*1 + 4*2 + ... + 4*n full grids = 1 + 4*n*(n-1)/2

        // at 65 + 1*131 the number is xxxx + 4 (4 adjacent cells are hit)
        // at 66 + 131 the number is 5 * xxxx + 4
        // at 66 + 2*131 the number is
        return countPlotsNew(26501365)
    }

    fun compute(n: Int): Long {
        var n = n - 1
        val a = 14655L
        val b = 44085L
        val c = 33150L

        return a * n * n + b * n + c
    }

    private fun countPlotsNew(steps: Int): Long {
        return compute(steps / 131)
//
//        val n = steps / 131
//
//        val maxPlots = 7335 // maxPlots
//        val cones = listOf(
//            determineCone(setOf(Coord(rows.first, 65))),
//            determineCone(setOf(Coord(rows.last, 65))),
//            determineCone(setOf(Coord(65, cols.first))),
//            determineCone(setOf(Coord(65, cols.last))),
//        )
//
//        return maxPlots * 2L * n * (n - 1) + cones.sum()
        // observations:
        // as soon as a grid is 'full' it will start to repeat
        // the presense of other grids does not make the time to first repeating cycle of a grid longer
        // we need to know how long until a grid repeats itself given a certain starting situation
        // the number of starting situations is limited
    }

    fun analyze(): Int {
//        analyze1()
//        analyze2()

        analyze3()
        return -1
    }

    private fun analyze3() {
        var plots = setOf(startPos)
        repeat(131 * 4) {
            println("$it: ${plots.size}")
            plots = takeStep(plots)
        }
    }

    private fun analyze1() {
        println("NORTH DIRECTION")
        analyzeRow(rows.first, rows.last)

        println("SOUTH DIRECTION")
        analyzeRow(rows.last, rows.first)

        println("WEST DIRECTION")
        analyzeCol(cols.first, cols.last)

        println("EAST DIRECTION")
        analyzeCol(cols.last, cols.first)


        // TIL the start is on an empty row and column

        // there are 5 types of cell, for each of these, determine when they are full
        // type 1: original cell
        // type N: N (entered via bottom row at col 65)
        // type E: entered at col 0 row 65
        // type S: entered at col 0 row 65
        // type W: entered at col 0 row 65

        analyzeGrid(setOf(startPos))
        analyzeGrid(setOf(Coord(rows.last, 65)))
        analyzeGrid(setOf(Coord(rows.first, 65)))
        analyzeGrid(setOf(Coord(65, cols.first)))
        analyzeGrid(setOf(Coord(65, cols.last)))

        println("cones:")
        println(determineCone(setOf(Coord(rows.first, 65))))
        println(determineCone(setOf(Coord(rows.last, 65))))
        println(determineCone(setOf(Coord(65, cols.first))))
        println(determineCone(setOf(Coord(65, cols.last))))
    }

    fun analyze2(): Int {
        repeat(200) {
            println("$it: ${takeNSteps(it, setOf(startPos)).size}")
        }

        return -1
    }

    fun takeNSteps(n: Int, initialPlots: Set<Coord> = setOf(startPos)): Set<Coord> {
        var plots = initialPlots
        repeat(n) {
            plots = takeStep(plots)
        }

        return plots
    }

    fun determineCone(initial: Set<Coord>): Int {
        var plots = initial
        repeat(65) {
            plots = takeStep(plots)
        }

        return plots.size
    }

    private fun analyzeGrid(initialPlots: Set<Coord>) {
        var plots = initialPlots
        var steps = 0
        var mostPlots = 0
        var mostPlotsAt = 0
        repeat(200) {
            plots = takeStep(plots)
            steps += 1
            if (plots.size > mostPlots) {
                mostPlots = plots.size
                mostPlotsAt = steps
            }
        }

        println("$initialPlots reaches max plots $mostPlots at step $mostPlotsAt")
    }

    private fun analyzeRow(outRow: Int, inRow: Int): Set<Coord> {
        var plots = setOf(startPos)
        repeat(5) {
            val result = analyzeRow(plots, outRow)
            println(result)
            plots = result.second.map { Coord(inRow, it) }.toSet()
        }
        return plots
    }


    private fun analyzeCol(outCol: Int, inCol: Int): Set<Coord> {
        var plots = setOf(startPos)
        repeat(5) {
            val result = analyzeCol(plots, outCol)
            println(result)
            plots = result.second.map { Coord(it, inCol) }.toSet()
        }
        return plots
    }

    fun analyzeRow(coords: Set<Coord>, rowToWatch: Int): Pair<Int, List<Int>> {
        var plots = coords
        var steps = 0
        while (plots.none { it.row == rowToWatch }) {
            plots = takeStep(plots)
            steps++
        }

        val row = plots.filter { it.row == rowToWatch }.map { it.col }
        println(cols.joinToString("") { if (it in row) "o" else "." })

        return steps to row
    }

    fun analyzeCol(coords: Set<Coord>, colToWatch: Int): Pair<Int, List<Int>> {
        var plots = coords
        var steps = 0
        while (plots.none { it.col == colToWatch }) {
            plots = takeStep(plots)
            steps++
        }

        val col = plots.filter { it.col == colToWatch }.map { it.row }
        println(rows.joinToString("") { if (it in col) "o" else "." })

        return steps to col
    }

    fun render(plots: Set<Coord>): String {
        val rgrid = grid.map { it.toMutableList() }
//        println(plots)
        plots.forEach {
            rgrid[it.row][it.col] = 'o'
        }

        return rgrid.joinToString("\n") { it.joinToString("") }
    }
}

fun main() {
    val day = Day21.parseInput("input")
    println("Part 1: ${day.part1()}")

    //600371036566551 is too high
    //599763113936220
    println("Part 2: ${day.part2()}")
}