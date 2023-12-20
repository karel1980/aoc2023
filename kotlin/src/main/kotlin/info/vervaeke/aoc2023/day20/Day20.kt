package info.vervaeke.aoc2023.day20

enum class Pulse {
    LOW,
    HIGH
}

data class Signal(val from: String, val pulse: Pulse, val to: String)

abstract class Module(val id: String, val targets: List<String>, val inputs: MutableList<String> = mutableListOf()) {
    companion object {
        fun create(spec: String): Module {
            val parts = spec.split(" ->")
            val targets = parts[1].trim().takeIf { it.isNotBlank() }?.split(", ") ?: emptyList()
            return when (parts[0][0]) {
                '%' -> FlipFlop(parts[0].substring(1), targets)
                '&' -> Conjunction(parts[0].substring(1), targets)
                else -> Broadcaster(parts[0], targets)
            }
        }
    }

    abstract fun receive(signal: Signal): List<Signal>

    abstract fun addInput(input: String)
}

enum class FlipFlopState {
    OFF,
    ON
}

data class FlipFlop(private val _id: String, private val _targets: List<String>, var state: FlipFlopState = FlipFlopState.OFF) : Module(_id, _targets) {
    override fun receive(signal: Signal): List<Signal> {
        if (signal.pulse == Pulse.HIGH) {
            return emptyList()
        }
        if (state == FlipFlopState.OFF) {
            state = FlipFlopState.ON
            return targets.map { Signal(id, Pulse.HIGH, it) }
        } else {
            state = FlipFlopState.OFF
            return targets.map { Signal(id, Pulse.LOW, it) }
        }
    }

    override fun addInput(input: String) {
        println("$input inputs to $id")
        this.inputs.add(input)
    }
}

data class Conjunction(private val _id: String, private val _targets: List<String>) : Module(_id, _targets) {
    private val inputMemory = mutableMapOf<String, Pulse?>()

    override fun receive(signal: Signal): List<Signal> {
//        println("I am $id")
//        println("received $signal")
//        println("initial state $inputMemory")
        inputMemory[signal.from] = signal.pulse
//        println("values in memory are now ${inputMemory.values}")
        if (inputMemory.values.all { it == Pulse.HIGH }) {
            val result = targets.map { Signal(id, Pulse.LOW, it) }
//            println("conjunction emitting $result")
            return result
        } else {
            val result = targets.map { Signal(id, Pulse.HIGH, it) }
//            println("conjunction emitting $result")
            return result
        }
    }

    override fun addInput(input: String) {
        println("$input inputs to $id")
        this.inputs.add(input)
        inputMemory[input] = Pulse.LOW
    }
}

data class Broadcaster(private val _id: String, private val _targets: List<String>) : Module(_id, _targets) {
    override fun receive(signal: Signal): List<Signal> {
        return targets.map { Signal(id, signal.pulse, it) }
    }

    override fun addInput(input: String) {
        println("$input inputs to $id")
        inputs.add(input)
    }
}

data class Day20(val modulesById: Map<String, Module>) {

    companion object {
        fun parseInput(path: String) = parseLines(readInput(path))
        fun readInput(path: String) = javaClass.getResource(path)!!.readText().lines()
        fun parseLines(lines: List<String>): Day20 {
            val modulesById = lines.map {
                Module.create(it)
            }.associateBy { it.id }
                .toMutableMap()

            modulesById.values.flatMap { module -> module.targets }
                .toSet()
                .filter { it !in modulesById }
                .forEach { modulesById[it] = Broadcaster(it, emptyList()) }

            modulesById.values.forEach { module ->
                module.targets.forEach { target ->
                    val targetModule = modulesById[target] ?: TODO("kapot")
                    targetModule.addInput(module.id)
                }
            }
            return Day20(modulesById)
        }
    }

    fun part1(): Long {
        var low = 0L
        var high = 0L

        (0 until 1000).forEach {
            val lowHigh = pushButton()
            low += lowHigh.first
            high += lowHigh.second
        }

        println("total low: $low")
        println("total high: $high")
        return low * high
    }

    fun cycleLength(): Long {
        val (lowPulses, highPulses) = pushButton()

        println("total low: $lowPulses, total high: $highPulses");

        return lowPulses * highPulses
    }

    private fun pushButton(): Pair<Long, Long> {
        var lowPulses = 0L
        var highPulses = 0L

        val signals = mutableListOf(Signal("button", Pulse.LOW, "broadcaster"))
        while (!signals.isEmpty()) {
            val signal = signals.removeAt(0)

//            println("processing $signal")
            if (signal.pulse == Pulse.LOW) {
                lowPulses += 1
            } else {
                highPulses += 1
            }

            val toId = signal.to
            val module = modulesById[toId] ?: TODO("unknown module")
            val newSignals = module.receive(signal)
            signals.addAll(newSignals)

        }
        return Pair(lowPulses, highPulses)
    }

    fun part2(): Long {
        return 42
    }

}

fun main() {
    val day = Day20.parseInput("input")
    //6160 is too low
    println("Part 1: ${day.part1()}")
    //1327053636 is too low
    println("Part 2: ${day.part2()}")
}