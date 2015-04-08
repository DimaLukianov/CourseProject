package kursova.view;

import java.rmi.RemoteException;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import kursova.interf.model.IProducer;

public class ProducerTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -2677658636846257452L;

	private String[] columns = new String[] { "ID", "Name",
			"Country", "City", "Street", "Email", "Web site", "Telephone" };

	private List<IProducer> producers;

	public ProducerTableModel(List<IProducer> producers) {
		this.producers = producers;
	}

	public void addProducer(IProducer producer) {
		producers.add(producer);
		fireTableRowsInserted(0, producers.size());
	}

	public IProducer getRowGroup(int rowIndex) {
		return producers.get(rowIndex);
	}

	public void removeRow(int rowIndex) {
		producers.remove(rowIndex);
		fireTableRowsDeleted(rowIndex, rowIndex);
	}

	public void refreshUpdatedTable() {
		fireTableRowsUpdated(0, producers.size());
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		IProducer p = producers.get(rowIndex);
		switch (columnIndex) {
		case 0:
			try {
				return Integer.toString(p.getProducerId());
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		case 1:
			try {
				return p.getName();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		case 2:
			try {
				return p.getCountry();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		case 3:
			try {
				return p.getCity();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		case 4:
			try {
				return p.getStreet();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		case 5:
			try {
				return p.getEmail();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		case 6:
			try {
				return p.getWebSite();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		case 7:
			try {
				return p.getTelephone();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "";
	}

	public int getRowCount() {
		return producers.size();
	}

	public String getColumnName(int columnIndex) {
		return columns[columnIndex];
	}

	public int getColumnCount() {
		return columns.length;
	}

	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}
}
