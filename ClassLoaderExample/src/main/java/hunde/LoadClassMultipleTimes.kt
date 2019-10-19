package hunde

import java.io.File
import java.net.URLClassLoader


fun main() {
    repeat(4) {
        newInstance("ClassLoaderHelper/target/classes/", "tests.ClassToLoadMultipleTimes")
    }
}

internal fun newInstance(path: String, classname: String) =
        File(path).toURI().toURL().let { url ->
            URLClassLoader(arrayOf(url)).use {
                it.loadClass(classname).getDeclaredConstructor().newInstance()
            }
        }
