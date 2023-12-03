package info.vervaeke.aoc2023.day3

data class Coord(val row: Int, val col: Int)
data class Number(val row: Int, val cols: IntRange, val value: Int) {
    fun isAdjacent(symbol: Coord): Boolean {
        return row - 1 <= symbol.row && symbol.row <= row + 1 &&
                cols.start - 1 <= symbol.col && symbol.col <= cols.endInclusive + 1
    }
}

data class Gear(val pos: Coord, val numbers: List<Number>) {
    val ratio: Int
        get() = numbers[0].value * numbers[1].value
}

private fun Char.isSymbol(): Boolean {
    return this != '.' && this !in "0123456789"
}

class Day3(val lines: List<String>) {
    val rows: Int = lines.size
    val cols: Int = lines[0].length
    fun getNumbers(): List<Number> {
        return lines.flatMapIndexed { idx, line -> line.getNumbers(idx) }
    }

    fun getPartNumbers(): List<Number> {
        val parts = getNumbers().filter { isPartNumber(it) }
        parts.forEach { println(it) }
        return parts
    }

    fun getSymbols(): List<Coord> {
        return lines.flatMapIndexed { row, line ->
            lines.mapIndexed { col, ch -> Coord(row, col) }
                .filter { lines[it.row][it.col].isSymbol() }
        }
    }

    fun isPartNumber(number: Number): Boolean {
        if (number.row > 0) {
            val row = lines[number.row - 1]
            if (containsSymbol(row, number.cols.start - 1, number.cols.endInclusive + 2)) {
                return true
            }
        }
        if (containsSymbol(lines[number.row], number.cols.start - 1, number.cols.endInclusive + 2)) {
            return true
        }
        if (number.row < lines.size - 1) {
            val row = lines[number.row + 1]
            if (containsSymbol(row, number.cols.start - 1, number.cols.endInclusive + 2)) {
                return true
            }
        }

        return false
    }

    private fun containsSymbol(line: String, start: Int, end: Int): Boolean {
        val start = Math.max(0, start)
        val end = Math.min(line.length, end)
        val part = line.substring(start, end)
        return part.any {
            it.isSymbol()
        }
    }

    fun getGears(): List<Gear> {
        val numbers = getNumbers()
        return getSymbols().map { symbol ->
            Gear(symbol, numbers.filter { it.isAdjacent(symbol) })
        }.filter { it.numbers.size == 2 }
    }
}

private fun String.getNumbers(idx: Int): List<Number> {
    return Regex("[0-9]+").findAll(this)
        .map {
            Number(idx, it.groups[0]!!.range, it.groups[0]!!.value.toInt())
        }
        .toList()
}

fun read_input(path: String) = parse_lines(Day3::class.java.getResource(path)!!.readText().lines())
fun parse_lines(lines: List<String>) = lines.map(::parse_line)
fun parse_line(line: String) = line

fun solve_part1(lines: List<String>): Int {
    return Day3(lines).getPartNumbers().sumOf { it.value }
}

fun solve_part2(lines: List<String>): Int {
    return Day3(lines).getGears().sumOf { it.ratio }
}

fun solve_part1_sample() = solve_part1(read_input("sample"))
fun solve_part1_real() = solve_part1(read_input("input"))
fun solve_part2_sample() = solve_part2(read_input("sample"))
fun solve_part2_real() = solve_part2(read_input("input"))

fun main() {
    println("Part 1 sample: ${solve_part1_sample()}")
    println("Part 1 real: ${solve_part1_real()}")
    println("Part 2 sample: ${solve_part2_sample()}")
    println("Part 2 real: ${solve_part2_real()}")
}

