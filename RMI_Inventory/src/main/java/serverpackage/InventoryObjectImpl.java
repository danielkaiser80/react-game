package serverpackage;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class InventoryObjectImpl extends UnicastRemoteObject implements
		InventoryInterface {

	protected InventoryObjectImpl() throws RemoteException {
		super();
	}

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	double m_dStockAmount = 0;

	@Override
	public double StockExtend(double dStockChange) throws RemoteException {
		return m_dStockAmount += dStockChange;
	}

	@Override
	public double StockReduce(double dStockChange) throws RemoteException {
		return m_dStockAmount -= dStockChange;
	}
}