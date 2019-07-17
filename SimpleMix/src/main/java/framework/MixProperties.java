package framework;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Provides methods to load properties from a file and to convert property keys
 * to different formats.
 *
 * @author Daniel Kaiser
 */
@SuppressWarnings("serial")
public class MixProperties extends Properties {

    /**
     * Create an empty <code>MixProperties</code>-object.
     */
    public MixProperties() {
        super();
    }

    /**
     * Create a <code>MixProperties</code>-object based on a standard Java
     * <code>Properties</code>-object.
     *
     * @param properties the properties object used
     */
    public MixProperties(Properties properties) {
        super(properties);
    }

    /**
     * Load a properties file from the file system.
     *
     * @param name name of the properties file to load
     */
    // FIXME the path here is wrong, maybe use a Loader / Helper framework for loading
    public void load(String name) {
        try (FileInputStream is = new FileInputStream(name + ".properties")) {
            load(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Searches and returns the property with the specified key in the
     * <code>property</code> object and converts it to an Integer.
     *
     * @param key The property key.
     * @return Property with the specified key in the <code>property
     * </code> object as Integer.
     */
    public int getPropertyAsInt(String key) {

        return Integer.parseInt(getProperty(key));

    }

    /**
     * Searches and returns the property with the specified key in the
     * <code>property</code> object and converts it to a Float.
     *
     * @param key The property key.
     * @return Property with the specified key in the <code>property
     * </code> object as Float.
     */
    public float getPropertyAsFloat(String key) {

        return Float.parseFloat(getProperty(key));

    }

    /**
     * Searches and returns the property with the specified key in the
     * <code>property</code> object and converts it to a Long.
     *
     * @param key The property key.
     * @return Property with the specified key in the <code>property
     * </code> object as Long.
     */
    public long getPropertyAsLong(String key) {

        return Long.parseLong(getProperty(key));

    }

    /**
     * Searches and returns the property with the specified key in the
     * <code>property</code> object and converts it to a Double.
     *
     * @param key The property key.
     * @return Property with the specified key in the <code>property
     * </code> object as Double.
     */
    public double getPropertyAsDouble(String key) {

        return Double.parseDouble(getProperty(key));

    }

    /**
     * Searches and returns the property with the specified key in the
     * <code>property</code> object and converts it to a Boolean.
     *
     * @param key The property key.
     * @return Property with the specified key in the <code>property
     * </code> object as Boolean.
     */
    public boolean getPropertyAsBoolean(String key) {

        String value = getProperty(key);

        return value.equals("TRUE") || value.equals("true") || value.equals("ON") || value.equals("on")
                || value.equals("1") || value.equals("YES") || value.equals("yes");

    }

}
