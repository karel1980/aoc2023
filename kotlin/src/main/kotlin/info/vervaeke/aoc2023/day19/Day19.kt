package info.vervaeke.aoc2023.day19

import java.math.BigInteger
import java.util.regex.Pattern

data class Part(val x: Int, val m: Int, val a: Int, val s: Int) {
    fun score(): Int {
        return x + m + a + s
    }

    companion object {
        fun parse(spec: String): Part {
            val pattern = Pattern.compile("\\{x=(.*),m=(.*),a=(.*),s=(.*)\\}")
            val m = pattern.matcher(spec)
            if (!m.matches()) {
                TODO("kapot")
            }
            return Part(m.group(1).toInt(), m.group(2).toInt(), m.group(3).toInt(), m.group(4).toInt())
        }
    }

}

sealed interface Rule {
    fun eval(part: Part): String?
}

data class Compare(val field: String, val op: String, val value: Int, val target: String) : Rule {
    override fun eval(part: Part): String? {
        val property = when (field) {
            "x" -> part.x
            "m" -> part.m
            "a" -> part.a
            "s" -> part.s
            else -> TODO()
        }

        val matches = when (op) {
            "<" -> property < value
            ">" -> property > value
            else -> TODO()
        }

        return if (matches) {
            target
        } else {
            null
        }
    }
}

data class GoTo(val target: String) : Rule {
    override fun eval(part: Part): String {
        return target
    }
}

data class Workflow(val id: String, val rules: List<Rule>) {
    companion object {
        fun parse(spec: String): Workflow {
            //px{a<2006:qkq,m>2090:A,rfg}
            val id = spec.substringBefore("{")
            return Workflow(id, spec.substringAfter("{").substringBefore("}")
                .split(",")
                .map {
                    if (it.contains(":")) {
                        val parts = it.split(":")
                        val target = parts[1]
                        val op = if ("<" in parts[0]) {
                            "<"
                        } else {
                            ">"
                        }
                        Compare(
                            parts[0].substringBefore(op),
                            op,
                            parts[0].substringAfter(op).toInt(),
                            target
                        )
                    } else {
                        GoTo(it)
                    }
                })
        }
    }
}

data class Day19(val workflows: Map<String, Workflow>, val parts: List<Part>) {

    companion object {
        fun parseInput(path: String) = parseLines(readInput(path))
        fun readInput(path: String) = javaClass.getResource(path)!!.readText().lines()
        fun parseLines(lines: List<String>) = Day19(
            lines.subList(0, lines.indexOf(""))
                .map { Workflow.parse(it) }
                .associateBy { it.id },
            lines.subList(lines.indexOf("") + 1, lines.size)
                .map { Part.parse(it) })
    }

    fun part1(): Int {
        return parts.filter {
            processPart(it) == "A"
        }.sumOf { it.score() }
    }

    fun part2(): BigInteger {
        val points = workflows.values.flatMap {
            it.rules
        }.map {
            when (it) {
                is GoTo -> "LOL" to 0
                is Compare -> it.field to if (it.op == "<") {
                    it.value
                } else (it.value + 1)
            }
        }.groupBy { it.first }

        val xVal = getChangePoints(points["x"]!!)
        val mVal = getChangePoints(points["m"]!!)
        val aVal = getChangePoints(points["a"]!!)
        val sVal = getChangePoints(points["s"]!!)

        var result = BigInteger.ZERO
        println(xVal)
        println(mVal)
        println(aVal)
        println(sVal)
        pairsOf(xVal) { xa, xb ->
            val xBlock = xb - xa
            println(xBlock)
            pairsOf(mVal) { ma, mb ->
                val mBlock = mb - ma.toLong()
                pairsOf(aVal) { aa, ab ->
                    val aBlock = ab - aa.toLong()
                    pairsOf(sVal) { sa, sb ->
                        val sBlock = sb - sa.toLong()
                        val combos = BigInteger.valueOf(0L + (xBlock) * (mBlock) * (aBlock) * (sBlock))
                        if (processPart(Part(xa, ma, aa, sa)) == "A") {
                            result += combos
                        }
                    }
                }
            }
        }

        return result
    }

//    fun part2Bis(): BigInteger {
//        return findAccepted(workflows["in"]!!, 0, 1..4000, 1..4000, 1..4000, 1..4000)
//    }
//
//    private fun findAccepted(workflow: Workflow, ruleIdx: Int, xRange: IntRange, mRange: IntRange, aRange: IntRange, sRange: IntRange): BigInteger {
//        val rule = workflow.rules[ruleIdx]
//        if (rule is GoTo) {
//            if (rule.target == "R") {
//                return BigInteger.ZERO
//            }
//            if (rule.target == "A") {
//                return listOf(xRange, mRange, aRange, sRange)
//                    .map { BigInteger.valueOf(1L + it.last - it.first) }
//                    .foldRight(BigInteger.ONE) { a, b -> a.times(b) }
//            }
//            return findAccepted(workflows[rule.target]!!, 0, xRange, mRange, aRange, sRange)
//        }
//        if (rule is Compare) {
//            val splitAt = if (rule.op == "<" ? rule.value+1)
//        }
//
//        TODO()
//    }

    private fun getChangePoints(points: List<Pair<String, Int>>) =
        (listOf(1) + points.map { it.second } + listOf(4001)).distinct().sorted()

    fun pairsOf(list: List<Int>, fn: (a: Int, b: Int) -> Unit) {
        list.indices.drop(1).forEach { fn(list[it - 1], list[it]) }
    }

    fun processPart(part: Part): String {
        var current: String? = "in"
        while (current != "A" && current != "R") {
            val wf = workflows[current]!!
            var ruleIdx = 0
            current = null
            while (current == null) {
                val rule = wf.rules[ruleIdx]
                current = rule.eval(part)
                ruleIdx++
            }
        }
        return current
    }
}

fun main() {
    val day = Day19.parseInput("input")
    //6160 is too low
    println("Part 1: ${day.part1()}")
    //1327053636 is too low
    println("Part 2: ${day.part2()}")
}