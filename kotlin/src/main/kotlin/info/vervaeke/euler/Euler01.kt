package info.vervaeke.euler

fun main() {
//    println("Euler 1 ${euler01()}")
//    println("Euler 2 ${euler02()}")
//    println("Euler 3 ${euler03()}")
    println("Euler 4 ${euler04()}")
}

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
    return (100 until 1000).flatMap { a ->
        (100 until 1000).map { b -> a * b }
    }.filter { it.toString() == it.toString().reversed() }
        .max()
}