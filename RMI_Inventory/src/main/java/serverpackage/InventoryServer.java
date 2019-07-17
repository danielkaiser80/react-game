package serverpackage;

import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.*;

public class InventoryServer {

	/**
	 * @param args
	 */
	public static void main(String... args) {

		try {
			LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
		}

		catch (RemoteException ex) {
			System.out.println(ex.getMessage());
		}
		try {
			InventoryObjectImpl inventory = new InventoryObjectImpl();
			Naming.rebind("Inventory",inventory);
			System.out.println("Server was launched.");
		}
		catch (MalformedURLException ex) {
			System.out.println(ex.getMessage());
		}
		catch (RemoteException ex) {
			System.out.println(ex.getMessage());
		}
	}
}
