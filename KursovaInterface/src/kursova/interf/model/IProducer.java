package kursova.interf.model;

import java.rmi.RemoteException;

import kursova.interf.IItem;

public interface IProducer extends IItem {
	
	public Integer getProducerId()throws RemoteException;
	public void setProducerId(int producerId)throws RemoteException;
	public String getName()throws RemoteException;
	public void setName(String name)throws RemoteException;
	public String getCountry()throws RemoteException;
	public void setCountry(String country)throws RemoteException;
	public String getCity()throws RemoteException;
	public void setCity(String city)throws RemoteException;
	public String getStreet()throws RemoteException;
	public void setStreet(String street)throws RemoteException;
	public String getEmail()throws RemoteException;
	public void setEmail(String email)throws RemoteException;
	public String getWebSite()throws RemoteException;
	public void setWebSite(String webSite)throws RemoteException;
	public String getTelephone()throws RemoteException;
	public void setTelephone(String telephone)throws RemoteException;
	public int create(String name, String country, String city, 
			String street, String email, String webSite, String telephone)throws RemoteException;
	public IProducer newInstance()throws RemoteException;

}
