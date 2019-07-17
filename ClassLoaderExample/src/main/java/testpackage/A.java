package testpackage;

class A 
{ 
	static String s = new java.util.Date().toString(); 

	public static void main( String... args ) 
	{ 
		B.start();
	} 
} 

class B 
{ 
	A a;

	public static void start()
	{
		System.out.println(  "A.s is " + A.s);
	}
}