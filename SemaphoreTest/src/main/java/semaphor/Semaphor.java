package semaphor;

public class Semaphor
{
	private int s;

	public Semaphor(int s)
	{
		assert (s > 0);
		this.s = s;
	}

	private synchronized void p()
	{
		while (this.s == 0)
		{
			try
			{
				wait();
			} //try
			catch (InterruptedException ie)
			{
				System.out.println("An InterruptedException caught\n"+ie.getMessage());
				ie.printStackTrace();
			} //catch
		} //while
		this.s--;
	} //p()
	

	private synchronized void v()
	{
		this.s++;
		notify();
	}

	public synchronized void down()
	{
		p();
	}
	

	public synchronized void up()
	{
		v();
	}

}
