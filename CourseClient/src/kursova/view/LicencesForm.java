package kursova.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;

import kursova.interf.Constant;
import kursova.interf.model.ILicence;

public class LicencesForm extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String ALL_STUDENTS = "allStudent";
	
	private JTable licencesTable;
	
	private LicenceTableModel licencesTableModel;
	
	private JButton bCreate = new JButton("Create");
	
	private JButton bUpdate = new JButton("Update");
	
	private JButton bDelete = new JButton("Delete");
	
	private JButton bPrint = new JButton("Print");
	
	private JButton bClose = new JButton("Close");
	
	private JButton bReport = new JButton("Report");
	
	private NewLicenceForm newLicenceForm = new NewLicenceForm();


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	public LicencesForm(){
		getContentPane().setLayout(new BorderLayout());
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("File");
		JMenuItem menuItem = new JMenuItem("New");
		menuItem.setName(ALL_STUDENTS);
		menuItem.addActionListener(this);
		// Вставляємо пункт меню у випадаюче меню
		menu.add(menuItem);
		// Вставляємо випадаюче меню в рядок меню
		menuBar.add(menu);
		
		setJMenuBar(menuBar);
		
		// Створюємо нижню панель і задаємо їй layout
		JPanel bot = new JPanel();
		bot.setLayout(new BorderLayout());
		// Створюємо праву панель для виведення списку студентів
		JPanel right = new JPanel();
		// Задаємо layout і задаємо "бордюр" навколо панелі
		right.setLayout(new BorderLayout());
		right.setBorder(new BevelBorder(BevelBorder.RAISED));
		
		//таблиця компаній
		licencesTableModel = getTableModel();
		licencesTable = new JTable(licencesTableModel);
		licencesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		licencesTable.setPreferredScrollableViewportSize(new Dimension(880, 180));
		licencesTable.getColumnModel().getColumn(0).setMinWidth(25);
		licencesTable.getColumnModel().getColumn(1).setMinWidth(100);
		licencesTable.getColumnModel().getColumn(2).setMinWidth(150);
		licencesTable.getColumnModel().getColumn(3).setMinWidth(100);
		licencesTable.getColumnModel().getColumn(4).setMinWidth(150);
		licencesTable.setGridColor(Color.ORANGE);
		licencesTable.setRowHeight(20);
		Font FontGrid = new Font(Font.MONOSPACED, Font.PLAIN, 14);
		licencesTable.setFont(FontGrid);
		
		JScrollPane scrollPane = new JScrollPane(licencesTable);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
				
		JPanel nav = new JPanel();
		nav.setLayout(new FlowLayout());
		
		nav.add(bClose);
		nav.add(bDelete);
		nav.add(bUpdate);
		nav.add(bReport);
		nav.add(bPrint);
		nav.add(bCreate);
		
		right.add(scrollPane, BorderLayout.CENTER);
		right.add(nav, BorderLayout.SOUTH);
		
		//bot.add(left, BorderLayout.WEST);
		bot.add(right, BorderLayout.CENTER);

		// Вставляємо верхню і нижню панелі у форму
		//getContentPane().add(scrollPane2, BorderLayout.NORTH);
		getContentPane().add(bot, BorderLayout.CENTER);

		// Задаємо межі форми
		setBounds(100, 100, 1000, 400);
		
		bClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancel();
			}
		});

		bCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					createProducer();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NotBoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		bUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					updateProducer();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		bDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeProducer();
			}
		});
		bPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				printProducer();
			}
		});
		bReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					reportProducer();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}
	
	private void printProducer() {
		try {
			MessageFormat headerFormat = new MessageFormat("Page {0}");
			MessageFormat footerFormat = new MessageFormat("- {0} -");
			licencesTable.print(JTable.PrintMode.FIT_WIDTH, headerFormat,
					footerFormat);
		} catch (PrinterException pe) {
			System.err.println("You can not print the document because: "
					+ pe.getMessage());
		}
	}
	
	private void reportProducer() throws IOException {
		String fileName = JOptionPane.showInputDialog ("Enter file name...");
		if(fileName != null){
			if(!fileName.equals("")){
				Date d = new Date();
		        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy hh:mm");
				BufferedWriter bfw = new BufferedWriter(new FileWriter(fileName+".txt"));
				for(int i = 0 ; i < licencesTable.getColumnCount() ; i++)
				{
					bfw.write(String.format("%20s",licencesTable.getColumnName(i)));
					bfw.write("|");
				}
				bfw.newLine();
				for (int i = 0 ; i < licencesTable.getRowCount(); i++)
				{
					bfw.newLine();
				    for(int j = 0 ; j < licencesTable.getColumnCount();j++)
					{
				    	bfw.write((String)(String.format("%20s",licencesTable.getValueAt(i,j))));
				    	bfw.write("|");
					}
				    System.out.println("\r\n");
				}
				bfw.newLine();
				bfw.newLine();
				bfw.write("Date: "+format.format(d));
				JOptionPane.showMessageDialog(LicencesForm.this, "The report was successfully generated!", "Success", JOptionPane.DEFAULT_OPTION );
				bfw.close();
			}else JOptionPane.showMessageDialog(LicencesForm.this, "File name can't be blank!", "Error", JOptionPane.DEFAULT_OPTION );
		}
	}
	
	private void createProducer() throws RemoteException, NotBoundException {
		newLicenceForm.setLicence(getInstance());
		newLicenceForm.setVisible(true);
		if (newLicenceForm.getLicence().getLicenceId() != null) {
			licencesTableModel.addLicence(newLicenceForm.getLicence());
			JOptionPane.showMessageDialog(LicencesForm.this, "Record was successfully created!", "Success", JOptionPane.DEFAULT_OPTION );
		}
	}
	
	private void updateProducer() throws RemoteException {
		int index = licencesTable.getSelectedRow();
		if (index == -1){
			JOptionPane.showMessageDialog(LicencesForm.this, "Do not select any field, please select field!", "Error", JOptionPane.DEFAULT_OPTION );
			return;
		}
		ILicence licence = licencesTableModel.getRowLicence(index);
		if (licence != null) {
			newLicenceForm.setLicence(licence);
			newLicenceForm.setVisible(true);
			licencesTableModel.refreshUpdatedTable();
//			JOptionPane.showMessageDialog(LicencesForm.this, "Record was successfully updated!", "Success", JOptionPane.DEFAULT_OPTION );
		}
	}
	
	private void removeProducer() {
		int index = licencesTable.getSelectedRow();
		if (index == -1){
			JOptionPane.showMessageDialog(LicencesForm.this, "Do not select any field, please select field!", "Error", JOptionPane.DEFAULT_OPTION );
			return;
		}
		if (JOptionPane.showConfirmDialog(LicencesForm.this,
				"Are you sure you want to delete licence?",
				"Removing licence", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			try {
				ILicence l = licencesTableModel.getRowLicence(index);
				if (l != null) {
					if(l.delete()){
						licencesTableModel.removeRow(index);
						JOptionPane.showMessageDialog(LicencesForm.this, "Record was successfully deleted!", "Success", JOptionPane.DEFAULT_OPTION );
					}
					else 
						JOptionPane.showMessageDialog(LicencesForm.this, "You can not remove record!", "Error", JOptionPane.DEFAULT_OPTION );
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(LicencesForm.this, e.getMessage());
			}
		}
	}
	
	private LicenceTableModel getTableModel() {
		try {
			return new LicenceTableModel((List<ILicence>) getInstance().all());
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this,
					"Error: " + e.getMessage());
		}
		return new LicenceTableModel(new ArrayList<ILicence>(0));
	}
	
	private void cancel() {
		this.setVisible(false);
	}
	
	private ILicence getInstance() throws RemoteException, NotBoundException{
		Registry registry = LocateRegistry.getRegistry("localhost", Constant.RMI_PORT);
		ILicence licence = (ILicence) registry.lookup(Constant.RMI_LICENCE_ID);
		return licence.newInstance();
	}
	
	public static void main(String[] args) {
		
		LicencesForm lForm = new LicencesForm(); 

		lForm.setVisible(true);
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		lForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	

}
