package kursova.view;

import java.rmi.RemoteException;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import kursova.interf.model.ILicence;

public class LicenceTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -2677658636846257452L;

	private String[] columns = new String[] { "ID", "Name",
			"Type", "Period(day)", "Price($)" };

	private List<ILicence> licence;

	public LicenceTableModel(List<ILicence> licence) {
		this.licence = licence;
	}

	public void addLicence(ILicence licence) {
		this.licence.add(licence);
		fireTableRowsInserted(0, this.licence.size());
	}

	public ILicence getRowLicence(int rowIndex) {
		return licence.get(rowIndex);
	}

	public void removeRow(int rowIndex) {
		licence.remove(rowIndex);
		fireTableRowsDeleted(rowIndex, rowIndex);
	}

	public void refreshUpdatedTable() {
		fireTableRowsUpdated(0, licence.size());
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		ILicence l = licence.get(rowIndex);
		switch (columnIndex) {
		case 0:
			try {
				return Integer.toString(l.getLicenceId());
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		case 1:
			try {
				return l.getName();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		case 2:
			try {
				return l.getType();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		case 3:
			try {
				return l.getPeriod();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		case 4:
			try {
				return l.getPrice();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "";
	}

	public int getRowCount() {
		return licence.size();
	}

	public String getColumnName(int columnIndex) {
		return columns[columnIndex];
	}

	public int getColumnCount() {
		return columns.length;
	}

	public Class<?> getColumnClass(int columnIndex) {
		return getValueAt(0, columnIndex).getClass();
	}
}
