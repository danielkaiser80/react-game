package tests

import java.net.URL

@Suppress("unused") // used by reflection
class ClassToLoadMultipleTimes {
    init {
        println("ClassToLoadMultipleTimes")
    }
}

internal fun getClassPath(clazz: Class<*>): URL? =
        clazz.classLoader.getResource(clazz.name.replace('.', '/') + ".class")

fun main() {
    val c = Class.forName("tests.ClassToLoadMultipleTimes")
    println("Klasse: ${c.name}")
    println("Dateiname: ${getClassPath(c)}")
}
