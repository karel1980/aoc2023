package info.vervaeke.euler

import com.google.common.math.LongMath.isPrime

private fun euler01() = (0 until 1000).filter { it % 3 == 0 || it % 5 == 0 }.sum()
private fun euler02(): Long {
    var a = 1L
    var b = 2L
    var result = 0L;
    while (b <= 4_000_000L) {
        if (b % 2L == 0L) {
            result += b
        }
        val b2 = a + b
        a = b
        b = b2
    }
    return result
}

private fun euler03(): Long {
    var n = 600851475143L
    var c = 2L
    var p = -1L
    while (c <= n) {
        if (n % c == 0L) {
            println(n)
            n /= c
            p = c
        } else {
            c++
        }
    }
    return p;
}

private fun euler04(): Int {
    return (100 until 1000).flatMap { a -> (100 until 1000).map { b -> a * b } }.filter { it.toString() == it.toString().reversed() }.max()
}

private fun euler05(): Long {
    return euler05(20)
}

private fun euler05(d: Long): Long {
    var r = 1L
    while (true) {
        if ((1 until d).all { r % it == 0L }) {
            return r
        }
        r++
    }
}

fun euler06(): Long {
    val n = 100L
    val a = (0..n).sumOf { it * it }
    val b = n * n * (n + 1) * (n + 1) / 4

    return b - a
}

fun euler07(): Long {
    val nr = 10001L

    var n = 2L

    var primeNr = 0L
    while (primeNr < nr) {
        if (isPrime(n)) {
            primeNr++
        }
        n++
    }
    return n - 1
}

fun euler08(): Long {
    val digits = """73167176531330624919225119674426574742355349194934
96983520312774506326239578318016984801869478851843
85861560789112949495459501737958331952853208805511
12540698747158523863050715693290963295227443043557
66896648950445244523161731856403098711121722383113
62229893423380308135336276614282806444486645238749
30358907296290491560440772390713810515859307960866
70172427121883998797908792274921901699720888093776
65727333001053367881220235421809751254540594752243
52584907711670556013604839586446706324415722155397
53697817977846174064955149290862569321978468622482
83972241375657056057490261407972968652414535100474
82166370484403199890008895243450658541227588666881
16427171479924442928230863465674813919123162824586
17866458359124566529476545682848912883142607690042
24219022671055626321111109370544217506941658960408
07198403850962455444362981230987879927244284909188
84580156166097919133875499200524063689912560717606
05886116467109405077541002256983155200055935729725
71636269561882670428252483600823257530420752963450""".trimIndent().replace("\n", "")

    val n = 13
    val result = (0 until digits.length - n).maxOf {
        digits.substring(it, it + n)
            .map { it.toString().toLong() }
            .foldRight(1L) { a, b -> a * b }
    }

    return result
}

fun euler09(): Int {
    (0 until 1000).forEach { a ->
        (a + 1 until 1000).forEach { b ->
            val c = 1000 - a - b

            if (c > b && a * a + b * b == c * c) {
                println("$a < $b < $c")
                return a * b * c
            }
        }
    }
    TODO("nope")
}

fun euler10(): Long {
    return (2L until 2_000_000).sumOf {
        if (isPrime(it)) {
            it
        } else {
            0
        }
    }
}

fun euler11(): Long {
    val numbers = """08 02 22 97 38 15 00 40 00 75 04 05 07 78 52 12 50 77 91 08
49 49 99 40 17 81 18 57 60 87 17 40 98 43 69 48 04 56 62 00
81 49 31 73 55 79 14 29 93 71 40 67 53 88 30 03 49 13 36 65
52 70 95 23 04 60 11 42 69 24 68 56 01 32 56 71 37 02 36 91
22 31 16 71 51 67 63 89 41 92 36 54 22 40 40 28 66 33 13 80
24 47 32 60 99 03 45 02 44 75 33 53 78 36 84 20 35 17 12 50
32 98 81 28 64 23 67 10 26 38 40 67 59 54 70 66 18 38 64 70
67 26 20 68 02 62 12 20 95 63 94 39 63 08 40 91 66 49 94 21
24 55 58 05 66 73 99 26 97 17 78 78 96 83 14 88 34 89 63 72
21 36 23 09 75 00 76 44 20 45 35 14 00 61 33 97 34 31 33 95
78 17 53 28 22 75 31 67 15 94 03 80 04 62 16 14 09 53 56 92
16 39 05 42 96 35 31 47 55 58 88 24 00 17 54 24 36 29 85 57
86 56 00 48 35 71 89 07 05 44 44 37 44 60 21 58 51 54 17 58
19 80 81 68 05 94 47 69 28 73 92 13 86 52 17 77 04 89 55 40
04 52 08 83 97 35 99 16 07 97 57 32 16 26 26 79 33 27 98 66
88 36 68 87 57 62 20 72 03 46 33 67 46 55 12 32 63 93 53 69
04 42 16 73 38 25 39 11 24 94 72 18 08 46 29 32 40 62 76 36
20 69 36 41 72 30 23 88 34 62 99 69 82 67 59 85 74 04 36 16
20 73 35 29 78 31 90 01 74 31 49 71 48 86 81 16 23 57 05 54
01 70 54 71 83 51 54 69 16 92 33 48 61 43 52 01 89 19 67 48""".trimIndent()

    val grid = numbers.lines().map { it.split(" ").map { it.toInt() } }

    val rows = grid.size
    val cols = grid[0].size

    val n = 4;
    var best = 0L;
    (0 until rows - n).forEach { row ->
        (0 until cols - n).forEach { col ->
            val h = product((0..n).map { grid[row][col + it] }.map { it.toLong() })
            val v = product((0..n).map { grid[row + it][col] }.map { it.toLong() })
            val d1 = product((0..n).map { grid[row + it][col + it] }.map { it.toLong() })
//            val d2 = product((0..n).map { grid[row + n - it][col + it] }.map { it.toLong() })

            val m = listOf(h, v, d1).max()
            if (m > best) {
                best = m
            }
        }
    }
    return best
}

private fun product(numbers: List<Long>): Long {
    return numbers.foldRight(1) { a, b -> a * b }
}

fun main() {
//    println("Euler 1: ${euler01()}")
//    println("Euler 2: ${euler02()}")
//    println("Euler 3: ${euler03()}")
//    println("Euler 4: ${euler04()}")
//    println("Euler 5: ${euler05()}")
//    println("Euler 6: ${euler06()}")
//    println("Euler 7: ${euler07()}")
//    println("Euler 8: ${euler08()}")
//    println("Euler 9: ${euler09()}")
//    println("Euler 10: ${euler10()}")
    println("Euler 11: ${euler11()}")
}
