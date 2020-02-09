package ea6

import kotlin.math.ln


fun main() {
    val b = 2000.0
    val c = 1700
    println(ln(ln(1001.0) / (ln(b) * (c + 1))) / ln(c.toDouble()))
}
