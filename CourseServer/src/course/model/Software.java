package course.model;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import course.dao.SoftwareDao;
import course.interf.IItem;
import course.interf.model.ISoftware;

public class Software extends UnicastRemoteObject implements ISoftware {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Software() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	private static SoftwareDao dao = new SoftwareDao();
	protected Integer softwareId;
	protected String name;
	protected String iconPath;
	protected String version;
	protected boolean osWindows;
	protected boolean osUnix;
	protected boolean osMac;
	protected String releaseDate;
	protected Integer producerId;
//	protected ImageIcon image;
	
	public Integer getSoftwareId() {
		return softwareId;
	}
	public void setSoftwareId(Integer softwareId) {
		this.softwareId = softwareId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIconPath() {
		return iconPath;
	}
	public void setIconPath(String iconPath) {
//		this.image = new ImageIcon(((new ImageIcon(iconPath)).getImage()).getScaledInstance(30, 30, java.awt.Image.SCALE_DEFAULT));
		this.iconPath = iconPath;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public boolean isOsWindows() {
		return osWindows;
	}
	public void setOsWindows(boolean osWindows) {
		this.osWindows = osWindows;
	}
	public boolean isOsUnix() {
		return osUnix;
	}
	public void setOsUnix(boolean osUnix) {
		this.osUnix = osUnix;
	}
	public boolean isOsMac() {
		return osMac;
	}
	public void setOsMac(boolean osMac) {
		this.osMac = osMac;
	}
	public String getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	public Integer getProducerId() {
		return producerId;
	}
	public void setProducerId(Integer producerId) {
		this.producerId = producerId;
	}
//	public ImageIcon getImage() {
//		return image;
//	}
//	public void setImage(ImageIcon image) {
//		this.image = image;
//	}
	
	
	public int create(String name, String iconPath, String version, boolean osWindows, boolean osUnix, boolean osMac, String releaseDate, Integer producerId){
		this.name = name;
		this.iconPath = iconPath;
		this.version = version;
		this.osWindows = osWindows;
		this.osUnix = osUnix;
		this.osMac = osMac;
		this.releaseDate = releaseDate;
		this.producerId = producerId;
		return this.save();
	}
	@Override
	public int save(){
		try {
			this.softwareId = dao.insertSoftware(this);
			//для швидкої роботи з таблицею!
//			this.image = new ImageIcon(((new ImageIcon(this.iconPath)).getImage()).getScaledInstance(30, 30, java.awt.Image.SCALE_DEFAULT));
			return this.softwareId;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	@Override
	public boolean update(){
		try {
			dao.updateSoftware(this);
			//для швидкої роботи з таблицею!
//			this.image = new ImageIcon(((new ImageIcon(this.iconPath)).getImage()).getScaledInstance(30, 30, java.awt.Image.SCALE_DEFAULT));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	@Override
	public boolean delete(){
		try {
			dao.deleteSoftware(this.softwareId);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}	
	}
	public IItem findById(int id) {
		try {
			return dao.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public int addLicence(Licence l) throws RemoteException{
		Ref ref = new Ref();
		return ref.create(this.softwareId, l.getLicenceId());
	}
	
	public List<Software> all(){
		List<Software> all = null;
		try {
			all = dao.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
//		//для швидкої роботи з таблицею!
//		for (Software s : all) {
//		    s.setImage(new ImageIcon(((new ImageIcon(s.getIconPath())).getImage()).getScaledInstance(30, 30, java.awt.Image.SCALE_DEFAULT)));
//		}
		return all;
	}
	

//	@Override
//	public ISoftware newInstance() throws RemoteException {
////		this.softwareId = 0;
////		this.name = null;
//		return new Software();
//		
//	}
	@Override
	public ISoftware newInstance() throws RemoteException {
		// TODO Auto-generated method stub
		return new Software();
	}
	
	@Override
	public List<Software> findAllByProducerId(Integer producerId) throws RemoteException {
		List<Software> all = null;
		try {
			all = dao.findAllByProducerId(producerId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return all;
	}

}
