package course.interf.model;

import java.rmi.RemoteException;

import course.interf.IItem;

public interface IRef  extends IItem {

	public Integer getRefId()throws RemoteException;
	public void setRefId(Integer refId)throws RemoteException;
	public Integer getSoftwareId()throws RemoteException;
	public void setSoftwareId(Integer softwareId)throws RemoteException;
	public Integer getLicenceId()throws RemoteException;
	public void setLicenceId(Integer licenceId)throws RemoteException;
	public int create(Integer softwareId, Integer licenceId)throws RemoteException;
	public IRef newInstance()throws RemoteException;
}
