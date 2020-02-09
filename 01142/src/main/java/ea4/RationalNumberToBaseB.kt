package ea4

import kotlin.system.exitProcess

private fun rationalBasisB(p: Int, q: Int, basis: Int) {
    if (p <= q) {
        doCalculation(p, q, basis)
    } else {
        println("Der Zähler muss kleiner als der Nenner sein.")
        exitProcess(-1)
    }
}

private fun doCalculation(p: Int, q: Int, basis: Int) {
    var erg = p % q * basis
    print("0,")
    repeat(20) {
        print(erg / q)
        erg = erg % q * basis
    }
}

fun main() {
    rationalBasisB(1, 3, 2)
    println()
    rationalBasisB(1, 7, 3)
    println()
    rationalBasisB(1, 11, 5)
    println()
    rationalBasisB(1, 13, 7)
    println()
    rationalBasisB(2, 13, 7)
    println()
}
