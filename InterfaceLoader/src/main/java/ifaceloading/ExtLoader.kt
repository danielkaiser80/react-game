package ifaceloading;


import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;


/**
 * Example loader Method
 */
public class ExtLoader {
    private static URLClassLoader ucl;

    public static ExternIF getMethode(String extFilePath, String classname) throws ClassNotFoundException, AbstractMethodError {
        System.out.println("Starting to load external class...");
        //für Rückgabe
        ExternIF extIF = null;

        try {
            final URL url = new File(extFilePath).toURI().toURL();

            System.out.println("URL to load from: " + url);

            ucl = new URLClassLoader(new URL[]{url});
            //Klasse laden
            final Class<?> tempClass = ucl.loadClass(classname);

            System.out.println("Classes:");

            //test der enumeration vor Instanziierung
            Class<?>[] classes = tempClass.getClasses();

            for (Class<?> c : classes) {
                System.out.println(c.toString());
            }


            tempClass.getDeclaredClasses();

            System.out.println("Fields:");

            //test der enumeration vor Instanziierung
            Field[] fields = tempClass.getFields();

            for (Field f : fields) {
                System.out.println(f.toString());
            }

            System.out.println("Methods:");


            //für unbekannte Klassen: Ausgabe alles Methodensignaturen
            Method m[] = tempClass.getDeclaredMethods();
            for (Method mm : m)
                System.out.println(mm.toString());

            //Test, ob Klasse das Interface implementiert
            if (ExternIF.class.isAssignableFrom(tempClass)) {

                //Cast und direkte Benutzung des Interfaces
                extIF = (ExternIF) tempClass.newInstance();
                System.out.println("aus ExtLoader (Direktaufruf): " + extIF.example(2, 5));

                //indirekte Benutzung des Interfaces / der Methoden bei unbekannter Klasse
                //Übergabe der Parameter als Klassentypen
                Class<?> partypes[] = new Class[2];
                partypes[0] = Integer.TYPE;
                partypes[1] = Integer.TYPE;
                final String methodName = "example";
                Method method = tempClass.getDeclaredMethod(methodName, partypes); //new Class<?>[]{} );

                //Übergabe der Parameter als Objekte
                Object arglist[] = new Object[2];
                arglist[0] = 37;
                arglist[1] = 2;
                System.out.println("aus ExtLoader (indirekt): " + method.invoke(extIF, arglist)); //new Object[]{}
            }
        } catch (MalformedURLException ex) {
            // dieser Fehler tritt offensichtlich nie auf, da grundsätzlich eine File URL gebaut wird - und dann eine ClassNotFoundException auftritt, wenn die Klasse nicht gefunden wird
            System.out.println("MalformedURLException: " + ex.getMessage());
        } catch (ClassCastException ex) {
            System.out.println("ClassCastException: " + ex.getMessage());
        } catch (InstantiationException ex) {
            System.out.println("InstantiationException: " + ex.getMessage());
        } catch (IllegalAccessException ex) {
            System.out.println("IllegalAccessException: " + ex.getMessage());
        } catch (SecurityException ex) {
            System.out.println("SecurityException: " + ex.getMessage());
        } catch (NoSuchMethodException ex) {
            System.out.println("NoSuchMethodException: Es existiert keine derartige Methode: " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            System.out.println("IllegalArgumentException: Die Argument-Typen sind ungültig." + ex.getMessage());
        } catch (InvocationTargetException ex) {
            System.out.println("InvocationTargetException: " + ex.getMessage());
        }
        return extIF;
    }
}