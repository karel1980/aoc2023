package info.vervaeke.aoc2023.day24

import org.apache.commons.math3.linear.Array2DRowRealMatrix
import org.apache.commons.math3.linear.MatrixUtils
import org.apache.commons.math3.linear.RealMatrix
import kotlin.math.max
import kotlin.math.min

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
        return point.x > px && vx > 0 || point.x < px && vx < 0
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
        /*
        *  We'll need a system of equations
        * line P is the unknown line
        * lines A B C are (three of) the known lines
        *
        * line A equations:
        * x(t) = xa + t*vax
        * y(t) = ya + t*vay
        * z(t) = za + t*vaz
        *
        * line X crosses with line A at ta, with line B at tb and with line C at tc
        * As 9 equations:
        *
        *  xa + ta*vax = xp + ta*vpx
        *  ya + ta*vay = yp + ta*vpy
        *  za + ta*vaz = zp + ta*vpz
        *
        *  xb + tb*vbx = xp + tb*vpx
        *  yb + tb*vby = yp + tb*vpy
        *  zb + tb*vbz = zp + tb*vpz
        *
        *  xc + tc*vcx = xp + tc*vpx
        *  yc + tc*vcy = yp + tc*vpy
        *  zc + tc*vcz = zp + tc*vpz
        *
        * Rather then solve these we'll assume we know vpx, vpy and vpz,
        * assuming that these will be in a reasonable range (let's say -500..500)
        *
        * Now we only have 5 unknowns (ta,tb,xp,yp,zp) in the first 6 equations.
        * Let's solve using the 5 equations and see if the results are the same for all lines
        *
        * ta*(vax-vpx) - xp = -xa
        * ta*(vay-vpy) - yp = -ya
        * ta*(vaz-vpz) - zp = -za
        * tb*(vbx-vpx) - xp = -xb
        * tb*(vby-vpy) - yp = -yb
        *
        * Matrix form: (columns are ta,tb,xp,yp,zp)
        *
        * A = [vax-vpx         0    -1  0   0 ]
        *     [vay-vpy         0     0 -1   0 ]
        *     [vaz-vpz         0     0  0  -1 ]
        *     [      0   vbx-vpx    -1  0   0 ]
        *     [      0   vby-vpy     0  -1  0 ]
        *
        * B = [ ta   tb   tc    vx    vy ]'
        * C = [ -xa  -ya  -za  -xb   -yb ]'
        *
        * A*B = C -> A'*A*B = A'*C
        *
        * We could also write it in augmented matrix format but let's see where we get with this.

        *
         */



        return 42
    }

    fun countInside(low: Double, high: Double): Int {
        return stones.indices.sumOf { i ->
            (i + 1 until stones.size).count { j ->
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

    fun analyze(): VLimits {
        var minVx = 0L
        var maxVx = 0L
        var minVy = 0L
        var maxVy = 0L
        var minVz = 0L
        var maxVz = 0L
        stones.forEach { a ->
            stones.filter { it != a}.forEach { b ->
                if (a.px < b.px && a.vx < 0 && b.vx > 0) {
                    minVx = max(b.vx, minVx)
                    maxVx = min(a.vx, maxVx)
                }
                if (a.py < b.py && a.vy < 0 && b.vy > 0) {
                    minVy = max(b.vx, minVy)
                    maxVy = min(a.vx, maxVy)
                }
                if (a.pz < b.pz && a.vz < 0 && b.vz > 0) {
                    minVz = max(b.vx, minVz)
                    maxVz = min(a.vx, maxVz)
                }
            }
        }

        println("vx must be <= $maxVx OR >= $minVx")
        println("vy must be <= $maxVy OR >= $minVx")
        println("vz must be <= $maxVz OR >= $minVz")

        return VLimits(maxVx .. minVx, maxVy .. minVy, maxVz .. minVz)
    }

    fun solveUnknowns(pvx: Double, pvy: Double, pvz: Double): RealMatrix {
        val a = stones[0]
        val b = stones[1]

        val matrixA = createMatrixA(a, b, pvx, pvy, pvz)
        val matrixC = createMatrixC(a, b, pvx, pvy, pvz)

        return MatrixUtils.inverse(matrixA).multiply(matrixC)
    }

    private fun createMatrixC(a: Stone, b: Stone, pvx: Double, pvy: Double, pvz: Double): RealMatrix {
        return Array2DRowRealMatrix(
            arrayOf(
                createDoubleArray(
                    arrayOf(
                        -a.px.toDouble(),
                        -a.py.toDouble(),
                        -a.pz.toDouble(),
                        -b.px.toDouble(),
                        -b.py.toDouble()
                    )
                )
            )
        )
    }

    private fun createDoubleArray(data: Array<Double>): DoubleArray {
        return data.let { row -> DoubleArray(row.size) { row[it] } }
    }

    private fun createMatrixA(
        a: Stone, b: Stone, pvx: Double, pvy: Double, pvz: Double,
    ): RealMatrix {

        val avx = a.vx
        val avy = a.vy
        val avz = a.vz
        val bvx = b.vx
        val bvy = b.vy
        val bvz = b.vz

        val params: Array<DoubleArray> = arrayOf(
            DoubleArray(5) { 0.0 },
            DoubleArray(5) { 0.0 },
            DoubleArray(5) { 0.0 },
            DoubleArray(5) { 0.0 },
            DoubleArray(5) { 0.0 },
        )
        val matrixA = Array2DRowRealMatrix(params)
        // fill the matrix
        matrixA.data[0][0] = avx - pvx
        matrixA.data[0][2] = -1.0

        matrixA.data[1][0] = avy - pvy
        matrixA.data[1][3] = -1.0

        matrixA.data[2][0] = avz - pvz
        matrixA.data[2][4] = -1.0

        matrixA.data[3][1] = bvx - pvx
        matrixA.data[3][2] = -1.0

        matrixA.data[3][1] = bvy - pvy
        matrixA.data[3][3] = -1.0

        return matrixA
    }


}

data class VLimits(val vxLimits: LongRange, val vyLimits: LongRange, val vzLimits: LongRange)

fun main() {
    val day = Day24.parseInput("input")
    // 12431 is too low
    // 16512 is too low
    // 17445 is too low
    // 25433
    println("Part 1: ${day.part1()}")
    println("Part 2: ${day.part2()}")
}