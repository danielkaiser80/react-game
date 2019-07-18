package semaphor.test;

import semaphor.Semaphor;

public class Philosophie
{
	private Philosoph[] Philo;

	public class Philosoph extends Thread {
		private Semaphor mutex;
		private int statusValue = 0;
		private int s = 1;
		private int nummer;
		private int denkenZähler = 0;
		private int essenZähler = 0;
		private Philosoph links;
		private Philosoph rechts;

		Philosoph(int i) {
			this.mutex = new Semaphor(1);
			this.nummer = i;
		}


		void setStatus(int i) {
			this.statusValue = i;
		}


		int getStatus() {
			return this.statusValue;
		}


		@Override
		public String toString() {
			return Integer.toString(this.nummer);
		}


		void teste() {

			if ((getStatus()==2)&&(this.getLinks().getStatus()!=1) && 
					(this.getRechts().getStatus()!=1)) {

				setStatus(1);
				down();
			}
		}



		synchronized void up()

		{
			while (this.s == 0)
			{
				try
				{
					System.out.println("Philosoph " + this.toString() + " schläft.");
					wait();

				} //try
				catch (InterruptedException ie)
				{
					System.out.println("An InterruptedException caught\n" + ie.getMessage());
				} //catch

			} //while

			this.s--;
		} //up()



		public synchronized void down()
		{
			this.s++;
			System.out.println("Philosoph " + this.toString() + " erwacht.");
			notify();
		} //down()

		private void denken() {
			System.out.println("Philosoph " + this.toString() + " denkt. (" + 
					(this.denkenZähler++) + ")");
		}

		private void essen() {
			System.out.println("Philosoph "+this.toString()+" isst. (" + 
					(this.essenZähler++) + ")");
		}

		private void stäbchen_nehmen() {
			this.mutex.down();
			this.statusValue = 2;
			teste(); // nimmt beide Stäbchen, wenn sie frei sind
			this.mutex.up();
			up(); //hier schläft der Philosoph ein, wenn er nicht beide Stäbchen
			//bekommen hat
		}



		private void stäbchen_weglegen() {
			mutex.down();
			statusValue = 0;
			getLinks().teste(); //evtl. Nachbarn aufwecken
			getRechts().teste(); //evtl. Nachbarn aufwecken
			mutex.up();
		}

		@Override
		public void run(){
			while (true){
				denken();
				stäbchen_nehmen();
				essen();
				stäbchen_weglegen();
			}
		}


		Philosoph getLinks() {
			return links;
		}


		void setLinks(Philosoph links) {
			this.links = links;
		}


		Philosoph getRechts() {
			return rechts;
		}


		void setRechts(Philosoph rechts) {
			this.rechts = rechts;
		}
	}



	private Philosophie()
	{
		this.Philo = new Philosoph[5];

		for (int i = 0; i < 5; i++) this.Philo[i] = new Philosoph(i);

		Philo[0].setLinks(Philo[4]);
		Philo[4].setRechts(Philo[0]);

		for (int i = 0; i < 5; i++) {
			if (i != 0) Philo[i].setLinks(Philo[i-1]);
			if (i != 4) Philo[i].setRechts(Philo[i+1]);
		}
	}



	private void startPhilosophenproblem()
	{
		for (int i = 0; i < 5; i++) Philo[i].start();
	}


	public static void main(String... args)
	{
		Philosophie philosophen = new Philosophie();
		philosophen.startPhilosophenproblem();		
	}

}

