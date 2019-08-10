package hunde

import java.io.File
import java.net.URLClassLoader


fun main() {
    repeat(4) {
        newInstance("ClassLoaderHelper/target/classes/", "tests.ClassToLoadMultipleTimes")
    }
}

internal fun newInstance(path: String, classname: String): Any {
    val url = File(path).toURI().toURL()

    URLClassLoader(arrayOf(url)).use { urlClassLoader ->
        return urlClassLoader.loadClass(classname).getDeclaredConstructor().newInstance()
    }
}
