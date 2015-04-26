package kursova.model.object;

import javax.swing.ImageIcon;

public class Soft {
	
	protected Integer softwareId;
	protected String name;
	protected String iconPath;
	protected String version;
	protected boolean osWindows;
	protected boolean osUnix;
	protected boolean osMac;
	protected String releaseDate;
	protected Integer producerId;
	protected ImageIcon image;
	
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
		this.image = new ImageIcon(((new ImageIcon(iconPath)).getImage()).getScaledInstance(30, 30, java.awt.Image.SCALE_DEFAULT));
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
	public ImageIcon getImage() {
		return image;
	}
	public void setImage(ImageIcon image) {
		this.image = image;
	}
}
