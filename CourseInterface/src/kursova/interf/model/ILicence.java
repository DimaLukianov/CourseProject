package kursova.interf.model;

import java.rmi.RemoteException;

import kursova.interf.IItem;


public interface ILicence  extends IItem {
	
	public Integer getLicenceId()throws RemoteException;
	public void setLicenceId(int licenceId)throws RemoteException;
	public String getName()throws RemoteException;
	public void setName(String name)throws RemoteException;
	public String getType()throws RemoteException;
	public void setType(String type)throws RemoteException;
	public int getPeriod()throws RemoteException;
	public void setPeriod(int period)throws RemoteException;
	public double getPrice()throws RemoteException;
	public void setPrice(double price)throws RemoteException;
	public int create(String name, String type, int period, double price)throws RemoteException;
	public ILicence newInstance()throws RemoteException;
	

}
