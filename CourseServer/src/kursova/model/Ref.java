package kursova.model;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import kursova.dao.RefDao;
import kursova.interf.IItem;
import kursova.interf.model.IRef;

public class Ref extends UnicastRemoteObject implements IRef {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Ref() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}
	private static RefDao dao = new RefDao();
	protected Integer refId;
	protected Integer softwareId;
	protected Integer licenceId;
	
	public Integer getRefId() {
		return refId;
	}
	public void setRefId(Integer refId) {
		this.refId = refId;
	}
	public Integer getSoftwareId() {
		return softwareId;
	}
	public void setSoftwareId(Integer softwareId) {
		this.softwareId = softwareId;
	}
	public Integer getLicenceId() {
		return licenceId;
	}
	public void setLicenceId(Integer licenceId) {
		this.licenceId = licenceId;
	}
	
	
	public int create(Integer softwareId, Integer licenceId){
		
		this.softwareId = softwareId;
		this.licenceId = licenceId;
		return this.save();
	}
	@Override
	public int save(){
		try {
			this.refId = dao.insertRef(this);
			return this.refId;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	@Override
	public boolean update(){
		try {
			dao.updateRef(this);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	@Override
	public boolean delete(){
		try {
			dao.deleteRef(this.refId);
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
	@Override
	public List<?> all() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public IRef newInstance() throws RemoteException {

		return new Ref();
	}

}
