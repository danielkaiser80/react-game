package ifaceloading

import java.io.File
import java.net.URLClassLoader

/**
 * Load classes from external project using reflection and URLClassLoader.
 */
fun loadClassFromExternalPath(extFilePath: String, classname: String): ExternIF? {
    println("Starting to load external class...")

    val url = File(extFilePath).toURI().toURL()
    println("URL to load from: $url")
    val ucl = URLClassLoader(arrayOf(url))

    // load the class
    val tempClass: Class<*> = ucl.loadClass(classname)

    println("Classes:")
    tempClass.classes.forEach { c ->
        println(c.toString())
    }
    tempClass.declaredClasses

    println("Fields:")
    tempClass.fields.map { it.toString() }.forEach { f ->
        println(f)
    }

    println("Methods:")
    // print all method signatures
    tempClass.declaredMethods.forEach { mm ->
        println(mm.toString())
    }

    // test whether the class implements our provided interface
    if (ExternIF::class.java.isAssignableFrom(tempClass)) { // Cast and use the interface directly
        return createAndHandleOurInterface(tempClass)
    }

    return null
}

private fun createAndHandleOurInterface(tempClass: Class<*>): ExternIF {
    val extIF = tempClass.getDeclaredConstructor().newInstance() as ExternIF
    println("got from ExtLoader (direct call): " + extIF.example(2, 5))

    // indirect use of the interface or the methods when the class would be unknown
    // --> provide the parameters as class types
    val partypes: Array<Class<Int>> = arrayOf(Integer.TYPE, Integer.TYPE)
    val methodName = "example"
    val method = tempClass.getDeclaredMethod(methodName, *partypes)

    // provide the parameters as objects or primitives in method call
    println("from ExtLoader (indirect with reflection): ${method?.invoke(extIF, 37, 2)}")
    return extIF
}
