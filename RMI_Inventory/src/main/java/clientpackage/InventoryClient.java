package clientpackage;

import serverpackage.InventoryInterface;

import java.rmi.Naming;

public class InventoryClient {

	/**
	 * @param args
	 */
	public static void main(String... args) {
		try {
			String host = "localhost";
			String port = "1099";
			String srv = "Inventory";
			String url = "rmi://" + host + ":" + port + "/" + srv;
			InventoryInterface inventory = (InventoryInterface) Naming.lookup(url);
			inventory.StockExtend(7000);
			double newStock = inventory.StockReduce(150);
			System.out.println(newStock);
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}

	}

}
