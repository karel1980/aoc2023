package info.vervaeke.aoc2023.day24

import org.apache.commons.math3.linear.SingularMatrixException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import javax.sound.midi.ShortMessage

class Day24Test {

    val sample = Day24.parseInput("sample")
    val real = Day24.parseInput("input")

    @Test
    fun parse() {
        assertThat(sample.stones[0]).isEqualTo(Stone(19, 13, 30, -2, 1, -2))
    }

    @Test
    fun crossPoint() {
        assertThat(sample.stones[0].crosspoint(sample.stones[1])).isEqualTo(Coord2d(x = 14.333333333333334, y = 15.333333333333334))
    }

    @Test
    fun countInside() {
        assertThat(sample.countInside(7.0, 27.0)).isEqualTo(2)
    }

    @Test
    fun analyze() {
        println(real.analyze())
    }

    @Test
    fun solveUnknowns() {
        val ranges = real.analyze()

        val vxRanges = listOf(
            ranges.vxLimits.start - 100 .. ranges.vxLimits.start,
            ranges.vxLimits.last..ranges.vxLimits.last + 100
        )
        val vyRanges = listOf(
            ranges.vyLimits.start - 100 .. ranges.vyLimits.start,
            ranges.vyLimits.last..ranges.vyLimits.last + 100
        )
        val vzRanges = listOf(
            ranges.vzLimits.start - 100 .. ranges.vzLimits.start,
            ranges.vzLimits.last..ranges.vzLimits.last + 100
        )

        var ok = 0L
        var bad = 0L
        vxRanges.forEach { xr ->
            xr.forEach { vx ->
                println("$vx  -- $bad -- $ok")
                vyRanges.forEach { yr ->
                    yr.forEach { vy ->
                        vzRanges.forEach { zr ->
                            zr.forEach { vz ->
                                try {
                                    println(real.solveUnknowns(vx.toDouble(), vy.toDouble(), vz.toDouble()))
                                    ok++
                                } catch (e: SingularMatrixException) {
                                    //
                                    bad++
                                }
                            }
                        }
                    }
                }
            }
        }

        println("done - $ok $bad")
    }

    @Test
    fun part2() {
        assertThat(sample.part2()).isEqualTo(42)
    }
}