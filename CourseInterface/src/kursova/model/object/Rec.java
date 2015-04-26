package kursova.model.object;

public class Rec {
	
	private Soft software = new Soft();
	
	private Prod producer = new Prod();
	
	private Lic licence = new Lic();
	
	private Refr ref = new Refr();
	
	public Soft getSoftware() {
		return software;
	}

	public void setSoftware(Soft software) {
		this.software = software;
	}

	public Prod getProducer() {
		return producer;
	}

	public void setProducer(Prod producer) {
		this.producer = producer;
	}

	public Lic getLicence() {
		return licence;
	}

	public void setLicence(Lic licence) {
		this.licence = licence;
	}
	

	public Refr getRef() {
		return ref;
	}

	public void setRef(Refr ref) {
		this.ref = ref;
	}
	
}
