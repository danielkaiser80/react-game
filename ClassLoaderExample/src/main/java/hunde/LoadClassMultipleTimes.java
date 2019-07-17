package hunde;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

public class LoadClassMultipleTimes {

	static Object newInstance(String path, String classname) throws Exception {
		URL url = new File(path).toURI().toURL();
		System.out.println(url);
		try (URLClassLoader urlClassLoader = new URLClassLoader(new URL[] { url });) {
			Class<?> c = urlClassLoader.loadClass(classname);
			return c.getDeclaredConstructor().newInstance();
		}
	}

	public static void main(String[] args) throws Exception {
		newInstance("../ClassLoaderHelper/bin/", "tests.ClassToLoadMultipleTimes");
		newInstance("../ClassLoaderHelper/bin/", "tests.ClassToLoadMultipleTimes");
	}
}
