package ea4;

public class rationaleZahlBasisB {

    private static void rationalBasisB(int p, int q, int Basis) {
        if (p > q) {
            System.out.println("Der Zähler muss kleiner als der Nenner sein.");
            System.exit(-1);
        }
        int erg = (p % q) * Basis;
        System.out.print("0,");

        for (int i = 0; i < 20; i++) {
            System.out.print((erg / q));
            erg = (erg % q) * Basis;
        }
    }

    public static void main(String[] args) {
        rationaleZahlBasisB.rationalBasisB(1, 3, 2);
        System.out.println();
        rationaleZahlBasisB.rationalBasisB(1, 7, 3);
        System.out.println();
        rationaleZahlBasisB.rationalBasisB(1, 11, 5);
        System.out.println();
        rationaleZahlBasisB.rationalBasisB(1, 13, 7);
        System.out.println();
    }
}