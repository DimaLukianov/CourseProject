package course.interf.model;

import java.io.FileNotFoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;


public interface IInfo extends Remote {
	public String getHelpInfo()throws RemoteException, FileNotFoundException;
	public String getAboutInfo()throws RemoteException, FileNotFoundException;

}
