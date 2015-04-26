package kursova.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import kursova.interf.Constant;
import kursova.interf.model.ILicence;
import kursova.interf.model.IRecord;
import kursova.interf.model.ISoftware;



public class NewRecordForm extends JDialog {
	
	private static final long serialVersionUID = -7265530307974489903L;
	
	private IRecord record;

	private Vector<String> softwareList = loadSoftwareList();
	
	private Vector<String> licenceList = loadLicenceeList();

	private JComboBox softwareId;
	private JComboBox licenceId;


	private JLabel JLabel_1 = new JLabel();
	private JLabel JLabel_2 = new JLabel();



	public NewRecordForm(){
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		setTitle("Record");
		setSize(400, 200);
		setModal(true);
		setResizable(false);		
		
		final JButton cmdSave = new JButton("Save");
		final JButton cmdCancel = new JButton("Cancel");

		softwareId = new JComboBox(softwareList);
		licenceId = new JComboBox(licenceList);

		final JPanel fieldsPanel = new JPanel(new GridLayout(2, 2, 10, 10));
		final JPanel fieldsPanelBorder = new JPanel(new FlowLayout(
				FlowLayout.CENTER, 10, 10));
		fieldsPanel.setOpaque(false);
		fieldsPanelBorder.setOpaque(false);
		fieldsPanelBorder.add(fieldsPanel);
		JLabel_1.setText("Software");
		JLabel_2.setText("Licence");
		JLabel_2.setForeground(Color.ORANGE);
		JLabel_1.setForeground(Color.ORANGE);
		fieldsPanel.add(JLabel_1);
		fieldsPanel.add(JLabel_2);
		fieldsPanel.add(softwareId);
		fieldsPanel.add(licenceId);


		final JPanel commandsPanel = new JPanel(new FlowLayout());
		final JPanel commandsPanelBorder = new JPanel(new FlowLayout(
				FlowLayout.CENTER, 0, 0));
		commandsPanelBorder.add(commandsPanel);
		commandsPanel.setOpaque(false);
		commandsPanel.add(cmdSave);
		commandsPanel.add(cmdCancel);
		commandsPanelBorder.setOpaque(false);
		
		getContentPane().add(fieldsPanelBorder, BorderLayout.NORTH);
		getContentPane().add(commandsPanelBorder, BorderLayout.SOUTH);
		
		cmdSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveRecord();
			}
		});

		cmdCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelSave();
			}
		});
	}

	public IRecord getRecord() {
		return record;
	}

	public void setRecord(IRecord record) throws RemoteException {
		this.record = record;
		if(record.getRefer().getSoftwareId() != null)
			softwareId.setSelectedItem(findSoftwareById(record.getRefer().getSoftwareId()));
			
		if(record.getRefer().getLicenceId() != null)
			licenceId.setSelectedItem(findLicenceById(record.getRefer().getLicenceId()));
	}

	private void saveRecord() {
		try {
			String[] arr = ((String) softwareId.getSelectedItem()).split("#");
			record.getRefer().setSoftwareId(Integer.parseInt(arr[0]));
			
			arr =  ((String) licenceId.getSelectedItem()).split("#");
			record.getRefer().setLicenceId(Integer.parseInt(arr[0]));
			
			if (record.getRefer().getRefId() == null) {
				record.getRefer().save();
			} else {
				record.getRefer().update();
			}
			record.refresh();
			this.setVisible(false);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this,
					"Error saving record: " + e.getMessage());
		}
	}

	private void cancelSave() {
		this.setVisible(false);
	}
	
	private Vector<String> loadSoftwareList() {
		Vector<String> softwareList = new Vector<String>();
		try {
			for (ISoftware s : (List<ISoftware>)getSoftwareInstance().all()) {
				softwareList.add(s.getSoftwareId()+"# "+s.getName());
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return softwareList;
	}

	private Vector<String> loadLicenceeList(){
		Vector<String> licenceList = new Vector<String>();
		try {
			for (ILicence l : (List<ILicence>)getLicenceInstance().all()) {
				licenceList.add(l.getLicenceId()+"# "+l.getName());
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return licenceList;
	}
	
	private String findSoftwareById(int id){
		String software = null;
		for (String s : softwareList) {
			String[] arr = s.split("#");
			if(Integer.parseInt(arr[0])==id)software = s;
		}
		return software;
	}
	private String findLicenceById(int id){
		String licence = null;
		for (String l : licenceList) {
			String[] arr = l.split("#");
			if(Integer.parseInt(arr[0])==id)licence = l;
		}
		return licence;
	}
	
	private ILicence getLicenceInstance() throws RemoteException, NotBoundException{
		Registry registry = LocateRegistry.getRegistry("localhost", Constant.RMI_PORT);
		ILicence licence = (ILicence) registry.lookup(Constant.RMI_LICENCE_ID);
		return licence.newInstance();
	}
	
	private ISoftware getSoftwareInstance() throws RemoteException, NotBoundException{
		Registry registry = LocateRegistry.getRegistry("localhost", Constant.RMI_PORT);
		ISoftware software = (ISoftware) registry.lookup(Constant.RMI_SOFTWARE_ID);
		return software.newInstance();
	}

}
