package course.interf.model;

import java.rmi.RemoteException;

import course.interf.IItem;

public interface IRecord  extends IItem {
	
	public ISoftware getSoftware()throws RemoteException;
	public void setSoftware(ISoftware software)throws RemoteException;
	public IProducer getProducer()throws RemoteException;
	public void setProducer(IProducer producer)throws RemoteException;
	public ILicence getLicence()throws RemoteException;
	public void setLicence(ILicence licence)throws RemoteException;
	public IRef getRefer()throws RemoteException;
	public void setRef(IRef ref)throws RemoteException;
	public void refresh()throws RemoteException;
	public IRecord newInstance()throws RemoteException;

}
