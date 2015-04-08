package kursova.model;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import kursova.dao.LicenceDao;
import kursova.interf.IItem;
import kursova.interf.model.ILicence;

public class Licence extends UnicastRemoteObject implements ILicence {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Licence() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}
	protected Integer licenceId;
	protected String name;
	protected String type;
	protected int period;
	protected double price;
	private static LicenceDao dao = new LicenceDao();
	
	public Integer getLicenceId() {
		return licenceId;
	}
	public void setLicenceId(int licenceId) {
		this.licenceId = licenceId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	public int create(String name, String type, int period, double price){
		this.name = name;
		this.type = type;
		this.period = period;
		this.price = price;
		return this.save();
	}
	@Override
	public int save(){
		try {
			this.licenceId = dao.insertLicence(this);;
			return this.licenceId;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	@Override
	public boolean update(){
		try {
			dao.updateLicence(this);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	@Override
	public boolean delete(){
		try {
			dao.deleteLicence(this.licenceId);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}	
	}
	@Override
	public IItem findById(int id) {
		try {
			return dao.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public int addSoftware(Software s) throws RemoteException{
		Ref ref = new Ref();
		return ref.create(s.getSoftwareId(), this.licenceId);
	}
	@Override
	public List<?> all(){
		List<Licence> all = null;
		try {
			all = dao.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return all;
	}
	@Override
	public ILicence newInstance() throws RemoteException {

		return new Licence();
	}

}
