package serverpackage;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InventoryInterface extends Remote {
	public double StockExtend(double dStockChange)
			throws RemoteException;

	public double StockReduce(double dStockChange)
			throws RemoteException;

}