package ifaceloading

/***
 * Main Method for simple example.
 */
fun main() {
    val testLoad = loadClassFromExternalPath("InterfaceLoaderInterface/target/classes", "ifaceloading.ExternMethode")
    println("Calling method in main: " + testLoad?.example(2, 4))
}
