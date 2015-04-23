package course.view;

import java.rmi.RemoteException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;

import course.interf.model.ISoftware;

public class SoftwareTableModel extends AbstractTableModel {

	
	private static final long serialVersionUID = 1L;

	private String[] columns = new String[] { "ID", "Name",
			"Icon", "Version", "Windows", "Unix", "Mac", "Release Date" };

	private List<ISoftware> software;

	public SoftwareTableModel(List<ISoftware> software) {
		this.software = software;
	}
	
	public void setSoftwareList(List<ISoftware> software){
		this.software = software;
	}

	public void addSoftware(ISoftware soft) {
		software.add(soft);
		fireTableRowsInserted(0, software.size());
	}

	public ISoftware getRowSoftware(int rowIndex) {
		return software.get(rowIndex);
	}

	public void removeRow(int rowIndex) {
		software.remove(rowIndex);
		fireTableRowsDeleted(rowIndex, rowIndex);
	}

	public void refreshUpdatedTable() {
		fireTableRowsUpdated(0, software.size());
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		ISoftware s = software.get(rowIndex);
		switch (columnIndex) {
		case 0:
			try {
				return Integer.toString(s.getSoftwareId());
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		case 1:
			try {
				return s.getName();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		case 2:
			try {
				return new ImageIcon(s.getIconPath());
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		case 3:
			try {
				return s.getVersion();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		case 4:
			try {
				return s.isOsWindows();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		case 5:
			try {
				return s.isOsUnix();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		case 6:
			try {
				return s.isOsMac();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		case 7:
			try {
				return s.getReleaseDate();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "";
	}

	public int getRowCount() {
		return software.size();
	}

	public String getColumnName(int columnIndex) {
		return columns[columnIndex];
	}

	public int getColumnCount() {
		return columns.length;
	}

	public Class<?> getColumnClass(int columnIndex) {
		//return String.class;
		return getValueAt(0, columnIndex).getClass();
	}

}

