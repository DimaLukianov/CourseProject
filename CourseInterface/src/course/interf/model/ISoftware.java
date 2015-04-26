package course.interf.model;

import java.rmi.RemoteException;
import java.util.List;

import course.interf.IItem;

public interface ISoftware  extends IItem {

	public Integer getSoftwareId()throws RemoteException;
	public void setSoftwareId(Integer softwareId)throws RemoteException;
	public String getName()throws RemoteException;
	public void setName(String name)throws RemoteException;
	public String getIconPath()throws RemoteException;
	public void setIconPath(String iconPath)throws RemoteException;
	public String getVersion()throws RemoteException;
	public void setVersion(String version)throws RemoteException;
	public boolean isOsWindows()throws RemoteException;
	public void setOsWindows(boolean osWindows)throws RemoteException;
	public boolean isOsUnix()throws RemoteException;
	public void setOsUnix(boolean osUnix)throws RemoteException;
	public boolean isOsMac()throws RemoteException;
	public void setOsMac(boolean osMac)throws RemoteException;
	public String getReleaseDate()throws RemoteException;
	public void setReleaseDate(String releaseDate)throws RemoteException;
	public Integer getProducerId()throws RemoteException;
	public void setProducerId(Integer producerId)throws RemoteException;
//	public ImageIcon getImage()throws RemoteException;
//	public void setImage(ImageIcon image)throws RemoteException;
	public int create(String name, String iconPath, String version, boolean osWindows, boolean osUnix,
			boolean osMac, String releaseDate, Integer producerId)throws RemoteException;
	public ISoftware newInstance()throws RemoteException;
	public List<?> findAllByProducerId(Integer producerId)throws RemoteException;
	
}
