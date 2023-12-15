package info.vervaeke.aoc2023.day15

data class Lens(val label: String, val focus: Int)

data class Box(val id: Int, val lenses: MutableList<Lens> = mutableListOf()) {
    fun add(lens: Lens) {
        val idx = lenses.indexOfFirst { it.label == lens.label }
        if (idx == -1) {
            lenses.add(lens)
        } else {
            lenses[idx] = lens
        }
    }

    fun remove(label: String) {
        lenses.removeIf {
            it.label == label
        }
    }

    fun focusPower(): Int {
        return lenses.mapIndexed { idx, it ->
            (id+1) * (idx+1) * it.focus
        }.sum()
    }
}

interface Instruction {
    val label: String
    val box: Int
        get() = label.hash()
}

data class Add(val _label: String, val focus: Int) : Instruction {
    override val label: String
        get() = _label
}

data class Remove(val _label: String) : Instruction {
    override val label: String
        get() = _label
}

data class Machine(val boxes: List<Box> = (0..255).map { Box(it) }) {
    fun execute(instruction: Instruction) {
        when (instruction) {
            is Add -> boxes[instruction.box].add(Lens(instruction.label, instruction.focus))
            is Remove -> boxes[instruction.box].remove(instruction.label)
        }
    }

    fun focusingPower(): Int {
        return boxes.sumOf { it.focusPower() }
    }

}


data class Day15(val lines: List<String>, val instructions: List<Instruction>, val machine: Machine = Machine()) {

    companion object {
        fun parseInput(path: String) = parseLines(readInput(path))
        fun readInput(path: String) = javaClass.getResource(path)!!.readText().lines()
        fun parseLines(lines: List<String>): Day15 {
            val instructions = lines[0].split(",").map {
                if (it.contains("=")) {
                    val parts = it.split("=")
                    Add(parts[0], parts[1].toInt())
                } else {
                    Remove(it.substring(0, it.length - 1))
                }
            }
            return Day15(lines[0].split(","), instructions)
        }
    }


    fun part1(): Int {
        return lines.sumOf { it.hash() }
    }

    fun part2(): Int {
        instructions.forEach {
            machine.execute(it)
        }
        machine.boxes.forEach {
            println(it)
        }
        return machine.focusingPower()
    }

}

fun String.hash(): Int {
    var value = 0
    this.forEach {
        value += it.code
        value *= 17
        value %= 256
    }
    return value
}

fun main() {
    val day = Day15.parseInput("input")
    println("Part 1: ${day.part1()}")
    println("Part 2: ${day.part2()}")
}