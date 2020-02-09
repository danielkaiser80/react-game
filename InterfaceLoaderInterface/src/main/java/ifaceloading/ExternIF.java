package ifaceloading;


/**
 * Example Interface
 */
@SuppressWarnings("unused") // used by reflection
public interface ExternIF {
    void setCaller(InternIF caller);

    void callback();

    int example(int value1, int value2);

    enum CompatibleImplementations {
        MERCURY,
        VENUS,
        EARTH,
        MARS,
        JUPITER,
        SATURN,
        URANUS,
        NEPTUNE
    }
}
