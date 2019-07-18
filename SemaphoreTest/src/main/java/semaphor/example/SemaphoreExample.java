package semaphor.example;

public class SemaphoreExample {
	private Semaphore myS;
	private static int Zaehler = 0;
	private static int Zahl = 0;
	private ThreadOne mythread1;
	private ThreadTwo mythread2;

	public class Semaphore {
		int s;

		Semaphore(int s) {
			assert (s > 0);
			this.s = s;
		} // constructor

		public synchronized void p() {
			while (this.s == 0) {
				try {
					wait();
				} // try
				catch (InterruptedException ie) {
					System.out.println(
							"An InterruptedException caught\n" + ie.getMessage());
				} // catch
			} // while
			this.s--;
		} // p()

		synchronized void v() {
			s++;
			notify();
		}
	}

	public class ThreadOne extends Thread {
		@Override
		public void run() {
			while (getZahl() <= 5000) {
				getMyS().p();
				setZaehler(getZaehler() + 1);
				System.out.println("1: " + getZaehler());
				getMyS().v();
				setZahl(getZahl() + 1);
			}
		}
	}

	public class ThreadTwo extends Thread {
		@Override
		public void run() {
			while (getZahl() <= 5000) {
				getMyS().p();
				System.out.println("2: " + getZaehler());
				setZaehler(getZaehler() - 1);
				getMyS().v();
			}
		}
	}

	public SemaphoreExample() {
		setMyS(new Semaphore(1));
		mythread1 = new ThreadOne();
		mythread2 = new ThreadTwo();
	}

	public void startThreads() {
		mythread1.start();
		mythread2.start();
	}

	public static void main(String[] args) {
		SemaphoreExample mysem = new SemaphoreExample();
		mysem.startThreads();
	}

	private static int getZahl() {
		return Zahl;
	}

	private static void setZahl(int zahl) {
		Zahl = zahl;
	}

	private Semaphore getMyS() {
		return myS;
	}

	private void setMyS(Semaphore myS) {
		this.myS = myS;
	}

	private static int getZaehler() {
		return Zaehler;
	}

	private static void setZaehler(int zaehler) {
		Zaehler = zaehler;
	}
}