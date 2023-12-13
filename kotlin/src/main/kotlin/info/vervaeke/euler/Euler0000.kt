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

fun main() {
    println("Euler 1: ${euler01()}")
    println("Euler 2: ${euler02()}")
    println("Euler 3: ${euler03()}")
    println("Euler 4: ${euler04()}")
    println("Euler 5: ${euler05()}")
    println("Euler 6: ${euler06()}")
    println("Euler 7: ${euler07()}")
    println("Euler 8: ${euler08()}")
    println("Euler 9: ${euler09()}")
}
