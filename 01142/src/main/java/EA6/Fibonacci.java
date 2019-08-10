package EA6;

public class Fibonacci {
    private final double[] F = {1.0, 1.0, 2.0, 3.0, 5.0, 8.0, 13.0, 21.0, 34.0, 55.0, 89.0, 144.0, 233.0, 377.0, 610.0, 987.0};
    private final int iter = 11;

    private double leftfak(final int i) {
        return this.F[this.iter + 1 - i] / this.F[this.iter + 3 - i];
    }

    private double rightfak(final int i) {
        return this.F[this.iter + 2 - i] / this.F[this.iter + 3 - i];
    }

    private static double f(final double x) {
        return x * x - x;
    }

    private double findmin(final double a, final double b) {
        double x, y, fx, fy;
        double na = a;
        double nb = b;
        for (int i = 0; i < this.iter + 1; i++) {
            x = na + leftfak(i) * (nb - na);
            y = na + rightfak(i) * (nb - na);
            fx = f(x);
            fy = f(y);
            System.out.println(i + "&" + na + "&" + nb + "&" + x + "&" + y + "&" + fx + "&" + fy + "\\\\");
            if (fx >= fy) {
                na = x;
                x = y;
                fx = fy;
            } else {
                nb = y;
            }
        }
        return (na + nb) / 2.0;
    }

    public static void main(String args[]) {
        Fibonacci suche = new Fibonacci();
        System.out.println(suche.findmin(-3.05, 3.05));
    }
}