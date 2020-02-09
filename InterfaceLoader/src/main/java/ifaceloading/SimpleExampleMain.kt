package ifaceloading

/***
 * Main Method for simple example.
 */
fun main() {
    val testLoad = ExtLoader.getMethode("InterfaceLoaderInterface/target/classes", "ifaceloading.ExternMethode")
    println("aus main: " + testLoad.example(2, 4))
}
