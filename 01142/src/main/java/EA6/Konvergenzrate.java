package EA6;

public class Konvergenzrate {

	private static double a(int n) {
		return 1.0/(Math.pow(2.0, Math.pow(2, Math.pow(2, n))));
	}
	
	private static double a = 0.0; // Grenzwert


	public static void main(String[] args) {
		for (int i=0; i<10; i++) {
			for (int j=0; j<5; j++) {
				System.out.println((a(j+1)-a)/Math.pow((a(j)-a),i));
			}
		}
	}
}