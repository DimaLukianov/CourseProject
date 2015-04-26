package course.model;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import course.dao.ProducerDao;
import course.interf.IItem;
import course.interf.model.IProducer;

public class Producer extends UnicastRemoteObject implements IProducer {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Producer() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	private static ProducerDao dao = new ProducerDao();
	protected Integer producerId;
	protected String name;
	protected String country;
	protected String city;
	protected String street;
	protected String email;
	protected String webSite;
	protected String telephone;
	
	public Integer getProducerId() {
		return producerId;
	}
	public void setProducerId(int producerId) {
		this.producerId = producerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getWebSite() {
		return webSite;
	}
	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	public int create(String name, String country, String city, String street, String email, String webSite, String telephone){
		this.name = name;
		this.country = country;
		this.city = city;
		this.street = street;
		this.email = email;
		this.telephone = telephone;
		return this.save();
	}
	@Override
	public int save(){
		try {
			this.producerId = dao.insertProducer(this);
			return this.producerId;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	@Override
	public boolean update(){
		try {
			dao.updateProducer(this);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	@Override
	public boolean delete(){
		try {
			dao.deleteProducer(this.producerId);
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
	
	public List<Producer> all(){
		List<Producer> all = null;
		try {
			all = dao.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return all;
	}
	@Override
	public IProducer newInstance() throws RemoteException {

		return new Producer();
	}

}
