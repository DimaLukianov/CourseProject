package kursova.model;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import kursova.dao.RecordDao;
import kursova.interf.IItem;
import kursova.interf.model.ILicence;
import kursova.interf.model.IProducer;
import kursova.interf.model.IRecord;
import kursova.interf.model.IRef;
import kursova.interf.model.ISoftware;

public class Record extends UnicastRemoteObject implements IRecord {
	
	public Record() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Software software = new Software();
	
	private Producer producer = new Producer();
	
	private Licence licence = new Licence();
	
	private Ref ref = new Ref();
	
	private static RecordDao dao = new RecordDao();

	public ISoftware getSoftware() {
		return software;
	}

	public void setSoftware(ISoftware software) {
		this.software = (Software) software;
	}

	public IProducer getProducer() {
		return producer;
	}

	public void setProducer(IProducer producer) {
		this.producer = (Producer) producer;
	}

	public ILicence getLicence() {
		return licence;
	}

	public void setLicence(ILicence licence) {
		this.licence = (Licence) licence;
	}
	

	public IRef getRefer() {
		return ref;
	}

	public void setRef(IRef ref) {
		this.ref = (Ref) ref;
	}
	
	public int addRef(Integer softwareId, Integer licenceId){
		return this.ref.create(softwareId, licenceId);
	}

	
	public void refresh(){
		this.software = (Software)this.software.findById(this.ref.getSoftwareId());
		this.licence = (Licence) this.licence.findById(this.ref.getLicenceId());
		this.producer = (Producer) this.producer.findById(this.software.getProducerId());
	}
	

	
	public boolean delete(){
		return this.ref.delete();
	}
	
	
	public IItem findById(int id) {
		try {
			return dao.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Record> all(){
		List<Record> all = null;
		try {
			all = dao.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return all;
	}

	@Override
	public int save() throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean update() throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IRecord newInstance() throws RemoteException {

		return new Record();
	}



}
