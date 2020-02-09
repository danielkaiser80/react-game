package ea6

private val fibonacciDoubles = doubleArrayOf(1.0, 1.0, 2.0, 3.0, 5.0, 8.0, 13.0, 21.0, 34.0, 55.0, 89.0, 144.0, 233.0, 377.0, 610.0, 987.0)
private const val iter = 11


fun main() = println(findMin(-3.05, 3.05))


internal fun findMin(a: Double, b: Double): Double {
    var x: Double
    var y: Double
    var fx: Double
    var fy: Double
    var na = a
    var nb = b

    (0 until iter + 1).forEach { i ->
        x = na + leftFak(i) * (nb - na)
        y = na + rightFak(i) * (nb - na)
        fx = f(x)
        fy = f(y)
        println("$i&$na&$nb&$x&$y&$fx&$fy\\\\")
        if (fx >= fy) {
            na = x
            x = y
            fx = fy
        } else {
            nb = y
        }
    }
    return (na + nb) / 2.0
}


private fun leftFak(i: Int): Double = fibonacciDoubles[iter + 1 - i] / fibonacciDoubles[iter + 3 - i]

private fun rightFak(i: Int): Double = fibonacciDoubles[iter + 2 - i] / fibonacciDoubles[iter + 3 - i]

private fun f(x: Double): Double = x * x - x
