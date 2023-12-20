package info.vervaeke.aoc2023.day20

import com.google.common.math.IntMath
import com.google.common.math.LongMath
import java.math.BigInteger
import kotlin.math.max
import kotlin.time.times

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
    abstract fun isInitialState(): Boolean
    abstract fun reset()
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

    override fun reset() {
        state = FlipFlopState.OFF
    }

    override fun addInput(input: String) {
        this.inputs.add(input)
    }

    override fun isInitialState(): Boolean {
        return state == FlipFlopState.OFF
    }
}

data class Conjunction(private val _id: String, private val _targets: List<String>) : Module(_id, _targets) {
    private val inputMemory = mutableMapOf<String, Pulse?>()

    override fun receive(signal: Signal): List<Signal> {
        inputMemory[signal.from] = signal.pulse
        if (allHigh()) {
            return targets.map { Signal(id, Pulse.LOW, it) }
        } else {
            return targets.map { Signal(id, Pulse.HIGH, it) }
        }
    }

    fun allHigh(): Boolean {
        return inputMemory.values.all { it == Pulse.HIGH }
    }

    fun allLow(): Boolean {
        return inputMemory.values.all { it == Pulse.LOW }
    }

    override fun isInitialState(): Boolean {
        return allLow()
    }

    override fun reset() {
        inputMemory.keys.forEach { inputMemory[it] = Pulse.LOW }
    }

    override fun addInput(input: String) {
        this.inputs.add(input)
        inputMemory[input] = Pulse.LOW
    }
}

data class Broadcaster(private val _id: String, private val _targets: List<String>) : Module(_id, _targets) {
    override fun receive(signal: Signal): List<Signal> {
        return targets.map { Signal(id, signal.pulse, it) }
    }

    override fun addInput(input: String) {
        inputs.add(input)
    }

    override fun isInitialState(): Boolean {
        return true
    }

    override fun reset() {
        // nothing to do
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
            val lowHigh = pushButtonOnce("rx")
            low += lowHigh.first
            high += lowHigh.second
        }

        println("total low: $low")
        println("total high: $high")
        return low * high
    }

    private fun pushButtonOnce(moduleToWatch: String): Pair<Long, Long> {
        var lowPulses = 0L
        var highPulses = 0L

        val signals = mutableListOf(Signal("button", Pulse.LOW, "broadcaster"))
        while (signals.isNotEmpty()) {
            val signal = signals.removeAt(0)

            if (moduleToWatch == signal.from) {
                if (signal.pulse == Pulse.LOW) {
                    lowPulses += 1
                } else {
                    highPulses += 1
                }
            }

            val toId = signal.to
            val module = modulesById[toId] ?: TODO("unknown module")
            val newSignals = module.receive(signal)
            signals.addAll(newSignals)
        }

        return Pair(lowPulses, highPulses)
    }

    fun renderDot() {
        println("digraph {")
        modulesById.values.forEach {
            val color = when (modulesById[it.id]!!) {
                is FlipFlop -> "yellow"
                is Broadcaster -> "blue"
                is Conjunction -> "green"
                else -> "white"
            }
            println("${it.id} [color=\"$color\"];")
        }
        modulesById.values.flatMap { m ->
            m.targets.map { m.id to it }
        }.forEach {
            val color = when (modulesById[it.first]!!) {
                is FlipFlop -> "yellow"
                is Broadcaster -> "blue"
                is Conjunction -> "green"
                else -> "white"
            }
            println("${it.first} -> ${it.second}")
        }
        println("}")
    }

    fun analysis(): Long {
        reset()
        val groups = listOf(
            "jv,kt,mt,zp,rc,hj,vc,hf,nm,dh,mc,lv,tg",
            "qs,rg,vq,bs,sc,mv,gl,kf,dx,ts,ng,lh,dl",
            "jm,xv,nd,dg,tm,mh,mk,pb,tp,pf,mf,gv,km",
            "pr,pd,jp,mx,cl,hm,qb,bf,vx,ns,pn,cb,tk",
        ).map {it.split(",")}

        val cycles = groups.map { analyzeGroup(it) }
        println("cycles: ")
        println("product: " + cycles.foldRight(1L) { a,b -> a*b})
        println("lcd    : " + leastCommonDenominator(cycles))

        return -1
    }

    fun reset() {
        modulesById.values.forEach { it.reset() }
    }

    private fun analyzeGroup(watch: List<String>): Long {
        var pushes = 0
        var done = false
        var minSent = 999999L
        while (!done) {
            val sent = pushButtonOnce(watch.first())
            val totalSent = sent.first + sent.second
            if (totalSent < minSent) {
                minSent = totalSent
//                println("minimum at $pushes: $sent")
            }

            pushes++
            if (sent.first + sent.second == 1L) {
                println("exactly 1 sent $sent at $pushes")
            }
            if (watch.all { modulesById[it]!!.isInitialState() }) {
                println("loop size: $pushes")
                done = true
            }
        }

        return 1L * pushes
    }

    fun part2(): Long {
        var pushes = 0L

        TODO()
    }

}

// chatgpt
fun primeFactors(n: Long): Map<Long, Long> {
    var num = n
    val factors = mutableMapOf<Long, Long>()

    var i = 2L
    while (i * i <= num) {
        while (num % i == 0L) {
            factors[i] = (factors[i]?:0) + 1
            num /= i
        }
        i++
    }

    if (num > 1) {
        factors[num] = factors.getOrDefault(num, 0) + 1
    }

    return factors
}

fun leastCommonDenominator(numbers: List<Long>): Long {
    val primeFactorsMap = mutableMapOf<Long, Long>()

    for (num in numbers) {
        val factors = primeFactors(num)
        for ((factor, power) in factors) {
            primeFactorsMap[factor] = max(primeFactorsMap[factor]?: 0L, power)
        }
    }

    var lcd = 1L
    for ((factor, power) in primeFactorsMap) {
        lcd *= Math.pow(factor.toDouble(), power.toDouble()).toLong()
    }

    return lcd
}

fun main() {
    // 949764474
    println("Part 1: ${Day20.parseInput("input").part1()}")

    // 4820936720796 is too low
    // 242733240010887 is too low??? probably a multiple of this
    // 485466480021774 (double that is too high)
    println("Part 2: ${Day20.parseInput("input").part2()}")
}
