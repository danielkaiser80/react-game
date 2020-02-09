package ea4


private const val BASIS = 7

fun main() {
    var a = 1.0
    var b = 0.0
    val g = 0.6789
    var c: Double
    var d: Int

    repeat(25) {
        c = (g * a - b) * BASIS
        d = c.toInt()
        b = b * BASIS + d
        a *= BASIS
        print("$d   ")
    }
}
