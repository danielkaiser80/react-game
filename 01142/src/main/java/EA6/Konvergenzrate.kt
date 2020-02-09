package ea6

import kotlin.math.pow

private fun a(n: Int): Double = 1.0 / 2.0.pow(2.0.pow(2.0.pow(n.toDouble())))

private const val a = 0.0 // Grenzwert

fun main() {
    (0..9).forEach { i ->
        (0..4).forEach { j ->
            println((a(j + 1) - a) / (a(j) - a).pow(i.toDouble()))
        }
    }
}
