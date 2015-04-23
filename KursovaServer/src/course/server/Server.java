package course.server;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import course.interf.Constant;
import course.model.*;


public class Server {
	
	public static void main(String[] args) throws RemoteException, AlreadyBoundException {
		
		Licence licence = new Licence();
		Producer producer = new Producer();
		Record record = new Record();
		Ref ref = new Ref();
		Software software = new Software();
		
		Registry registry = LocateRegistry.createRegistry(Constant.RMI_PORT);
		registry.bind(Constant.RMI_LICENCE_ID, licence);
		registry.bind(Constant.RMI_PRODUCER_ID, producer);
		registry.bind(Constant.RMI_RECORD_ID, record);
		registry.bind(Constant.RMI_REF_ID, ref);
		registry.bind(Constant.RMI_SOFTWARE_ID, software);
		
		System.out.println("Start server...");
	}

}
