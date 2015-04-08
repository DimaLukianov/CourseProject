package kursova.view;

import java.rmi.RemoteException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;

import kursova.interf.model.IRecord;

public class RecordsTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -2677658636846257452L;

	private String[] columns = new String[] { "ID", "Software", "Icon",
			"Producer", "License", "Price($)" };

	private List<IRecord> record;

	public RecordsTableModel(List<IRecord> record) {
		this.record = record;
	}

	public void addRecord(IRecord record) {
		this.record.add(record);
		fireTableRowsInserted(0, this.record.size());
	}

	public IRecord getRowRecord(int rowIndex) {
		return record.get(rowIndex);
	}

	public void removeRow(int rowIndex) {
		record.remove(rowIndex);
		fireTableRowsDeleted(rowIndex, rowIndex);
	}

	public void refreshUpdatedTable() {
		fireTableRowsUpdated(0, record.size());
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		IRecord r = record.get(rowIndex);
		switch (columnIndex) {
		case 0:
			try {
				return r.getRefer().getRefId();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		case 1:
			try {
				return "  "+r.getSoftware().getSoftwareId()+"# "+r.getSoftware().getName();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		case 2:
			try {
				return new ImageIcon(r.getSoftware().getIconPath());
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		case 3:
			try {
				return "  "+r.getProducer().getProducerId()+"# "+r.getProducer().getName();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		case 4:
			try {
				return  "  "+r.getLicence().getLicenceId()+"# "+r.getLicence().getName();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		case 5:
			try {
				return  r.getLicence().getPrice();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "";
	}

	public int getRowCount() {
		return record.size();
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

