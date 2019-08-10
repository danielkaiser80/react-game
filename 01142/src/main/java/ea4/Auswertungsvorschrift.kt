package ea4;

public class Auswertungsvorschrift {

	/**
	 * @param args
	 */
	public static void main(String... args) {
		double x = 0.01;
		double a_1 = 1/(1+2*x)-(1-x)/(1+x);
		double a_2 = 2*x*x/(1+3*x+2*x*x);
		System.out.println(a_1);
		System.out.println(a_2);
		System.out.println(2.0/10302.0);
		System.out.println(0.009804*0.009901);
	}

}
