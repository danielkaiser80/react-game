package tests;

import java.net.URL;

public class ClassToLoadMultipleTimes
{
	static
	{
		System.out.println( "ClassToLoadMultipleTimes" );
	}
	
	static String getClassPath( Class<?> clazz )
	  {
	    ClassLoader loader = clazz.getClassLoader();
	    if ( loader == null )
	      return null;
	    URL url = loader.getResource( clazz.getName().replace('.', '/' )
	                                   + ".class" );
	    return ( url != null ) ? url.toString() : null;
	  }
	
	  public static void main( String... args ) throws Exception
	  {
		Class<?> c = Class.forName( "tests.ClassToLoadMultipleTimes" );
	    System.out.println( "Klasse: " + c.getName() );
	    System.out.println( "Dateiname: " + getClassPath(c) );
	  }
}
