package EA4;
public class ZahlBasis7 {

	private final static int Basis = 7;
	
	public static void main(String[] args) {
		double a=1.0; double b=0.0; double g=0.6789;
		double c; int d;
		
		for (int i=0; i<25; i++) {
			c=(g*a-b)*Basis;
			d=(int) c;
			b=b*Basis+d;
			a=a*Basis;
			System.out.print(d + "   ");
		}
	}
}
