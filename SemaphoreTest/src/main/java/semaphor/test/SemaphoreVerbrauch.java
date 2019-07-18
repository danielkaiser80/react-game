package semaphor.test;

import semaphor.Semaphor;
import semaphor.TestObjekt;

public class SemaphoreVerbrauch {
	private static final int n = 10;
	private static Semaphor Zugriff = new Semaphor(1); // binär
	private static Semaphor Frei = new Semaphor(n); // für n Betriebsmittel
	private static Semaphor Belegt = new Semaphor(0); // für n Betriebsmittel
	private ThreadOne mythread1;
	private ThreadTwo mythread2;

	public class ThreadOne extends Thread {

		@SuppressWarnings("unused")
		@Override
		public void run() {

			while (TestObjekt.Anzahl < 10000) {
				new TestObjekt();
				getFrei().down();
				getZugriff().down();
				System.out.println("Objekt " + TestObjekt.Anzahl + " ist in Puffer");
				getZugriff().up();
				getBelegt().up();
			}
		}
	}

	public class ThreadTwo extends Thread {

		@Override
		public void run() {

			while (TestObjekt.verbraucht > -10000) {
				getBelegt().down();
				getZugriff().down();
				TestObjekt.verbraucht--;
				getZugriff().up();
				getFrei().up();
				System.out.println("Objekt " + TestObjekt.verbraucht + " ist verbraucht");
			}
		}
	}

	private SemaphoreVerbrauch() {
		mythread1 = new ThreadOne();
		mythread2 = new ThreadTwo();
	}

	private void startTests() {
		mythread1.start();
		mythread2.start();
	}

	public static void main(String[] args) {
		TestObjekt.Anzahl = 0;
		TestObjekt.verbraucht = 0;
		SemaphoreVerbrauch mysem = new SemaphoreVerbrauch();
		mysem.startTests();
	}

	private static Semaphor getFrei() {
		return Frei;
	}

	public static void setFrei(Semaphor frei) {
		Frei = frei;
	}

	private static Semaphor getZugriff() {
		return Zugriff;
	}

	public static void setZugriff(Semaphor zugriff) {
		Zugriff = zugriff;
	}

	private static Semaphor getBelegt() {
		return Belegt;
	}

	public static void setBelegt(Semaphor belegt) {
		Belegt = belegt;
	}
}