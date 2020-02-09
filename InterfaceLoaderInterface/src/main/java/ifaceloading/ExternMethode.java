package ifaceloading;


/**
 * Example class to be loaded by reflection.
 * implements ExternIF
 */
@SuppressWarnings("unused") // used by reflection
public class ExternMethode implements ExternIF {
	
	private InternIF caller;
	
	@Override
	public int example( int value1, int value2 )
	{
		return value1 * value2;
	}
	
	@Override
	public void callback()
	{
		this.caller.test();
	}

	@Override
	public void setCaller(InternIF caller) {
		this.caller = caller;
	}
	
	public enum CompatibleImplementations {
		    MERCURY (3.303e+23, 2.4397e6),
		    VENUS   (4.869e+24, 6.0518e6),
		    EARTH   (5.976e+24, 6.37814e6),
		    MARS    (6.421e+23, 3.3972e6),
		    JUPITER (1.9e+27,   7.1492e7),
		    SATURN  (5.688e+26, 6.0268e7),
		    URANUS  (8.686e+25, 2.5559e7),
		    NEPTUNE (1.024e+26, 2.4746e7);

		    private final double mass;   // in kilograms
		    private final double radius;
		    
		    CompatibleImplementations(double mass, double radius) {
		        this.mass = mass;
		        this.radius = radius;
		    }
		    
		    public double getMass() {
		    	return this.mass;
		    }
		    
		    public double getRadius() {
		    	return this.radius;
		    }
		}
	
}
