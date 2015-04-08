package kursova.interf;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;


public interface IItem extends Remote {
	
	int save()throws RemoteException;
	boolean update()throws RemoteException;
	boolean delete()throws RemoteException;
	IItem findById(int id)throws RemoteException;
	List<?> all()throws RemoteException;
}
