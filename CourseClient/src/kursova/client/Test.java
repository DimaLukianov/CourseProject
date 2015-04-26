package kursova.client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

import kursova.interf.Constant;
import kursova.interf.model.ILicence;
import kursova.interf.model.IProducer;


public class Test {
	
	public static void main(String[] args) throws RemoteException, NotBoundException {
		Registry registry = LocateRegistry.getRegistry("localhost", Constant.RMI_PORT);
		IProducer producer = (IProducer) registry.lookup(Constant.RMI_PRODUCER_ID);
		List<IProducer> pl = (List<IProducer>) producer.all();
		for(IProducer p: pl)
		System.out.println(p.getName());
		
		ILicence licence = (ILicence) registry.lookup(Constant.RMI_LICENCE_ID);
		List<ILicence> licL = (List<ILicence>) licence.all();
		for(ILicence l: licL)
		System.out.println(l.getName());
	}

}
