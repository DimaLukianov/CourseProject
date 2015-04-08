package kursova.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.HeadlessException;
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
import java.util.Date;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import kursova.interf.Constant;
import kursova.interf.model.IProducer;
import kursova.interf.model.ISoftware;

public class SoftwareForm extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String ALL_STUDENTS = "allStudent";
	
	//private final static JFrame mainFrame = new JFrame();
	
	DefaultListModel listModel = new DefaultListModel();
	
	DefaultListModel listModel2 = new DefaultListModel();
	
	private JList prodList;
	
	//private JList softList;
	
//	private JTable producersTable;
	
	private JTable softwareTable;
	
//	private ProducerTableModel producersTableModel;
	
	private SoftwareTableModel softwareTableModel;
	
	private JButton bShowAllProd = new JButton("Show All");
	
	private JButton bShowAllLicence = new JButton("All licence");
	
	private JButton bAddLicence = new JButton("Add licence");
	
	private JButton bCreateSoft = new JButton("Create");
	
	private JButton bUpdateSoft = new JButton("Update");
	
	private JButton bDeleteSoft = new JButton("Delete");
	
	private JButton bPrint = new JButton("Print");
	
	private JButton bReport = new JButton("Repotr");
	
	private NewSoftwareForm newSoftwareForm = new NewSoftwareForm();
	
	private ProducersForm producersForm = new ProducersForm();
	
	private LicencesForm licencesForm = new LicencesForm();


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	public SoftwareForm() throws RemoteException, NotBoundException{
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

		// Створюємо ліву панель для виведення списку груп
		JPanel left = new JPanel();
		// Задаємо layout і задаємо "бордюр" навколо панелі
		left.setLayout(new BorderLayout());
		left.setBorder(new BevelBorder(BevelBorder.RAISED));

		// Отримуємо список виробників
		//List<Producer> producers = Producer.all();
		// Створюємо напис
		left.add(new JLabel("Producers:"), BorderLayout.NORTH);
		// Створюємо візуальний список і вставляємо його в скролінговану
		// панель, яку у свою чергу кладемо на панель left
		prodList = new JList(listModel);
		loadProducersList();
		
		left.add(new JScrollPane(prodList), BorderLayout.CENTER);
		left.add(bShowAllProd, BorderLayout.SOUTH);
				
		// Створюємо праву панель для виведення списку студентів
		JPanel right = new JPanel();
		// Задаємо layout і задаємо "бордюр" навколо панелі
		right.setLayout(new BorderLayout());
		right.setBorder(new BevelBorder(BevelBorder.RAISED));				
				
		softwareTableModel = new SoftwareTableModel((List<ISoftware>) getInstance().all());
		softwareTable = new JTable(softwareTableModel);
		softwareTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		softwareTable.setPreferredScrollableViewportSize(new Dimension(880, 180));
		softwareTable.getColumnModel().getColumn(0).setMinWidth(25);
		softwareTable.getColumnModel().getColumn(1).setMinWidth(100);
		softwareTable.getColumnModel().getColumn(2).setMinWidth(50);
		softwareTable.getColumnModel().getColumn(3).setMinWidth(100);
		softwareTable.getColumnModel().getColumn(4).setMinWidth(60);
		softwareTable.getColumnModel().getColumn(5).setMinWidth(50);
		softwareTable.getColumnModel().getColumn(6).setMinWidth(50);
		softwareTable.getColumnModel().getColumn(7).setMinWidth(100);
		softwareTable.setGridColor(Color.ORANGE);
		softwareTable.setRowHeight(40);
		Font FontGrid = new Font(Font.SANS_SERIF, Font.PLAIN, 14);
		softwareTable.setFont(FontGrid);
		JScrollPane scrollPane = new JScrollPane(softwareTable);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
				
		JPanel bottom = new JPanel();
		JPanel nav = new JPanel();
		JPanel navLicence = new JPanel();
			
		bottom.setLayout(new BorderLayout());
		nav.setLayout(new FlowLayout());
		navLicence.setLayout(new FlowLayout());
				
		nav.add(bDeleteSoft);
		nav.add(bUpdateSoft);
		nav.add(bReport);
		nav.add(bPrint);
		nav.add(bCreateSoft);
		
		navLicence.add(bShowAllLicence);
		navLicence.add(bAddLicence);
		
		bottom.add(nav, BorderLayout.CENTER);
		bottom.add(navLicence, BorderLayout.EAST);
				
		right.add(scrollPane, BorderLayout.CENTER);
		right.add(bottom, BorderLayout.SOUTH);
				
		bot.add(left, BorderLayout.WEST);
		bot.add(right, BorderLayout.CENTER);

		// Вставляємо верхню і нижню панелі у форму
		//getContentPane().add(scrollPane2, BorderLayout.NORTH);
		getContentPane().add(bot, BorderLayout.CENTER);
		// Задаємо межі форми
		setBounds(100, 100, 1000, 400);
				
		bShowAllProd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				producersForm.setVisible(true);
			}
		});
		bShowAllLicence.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				licencesForm.setVisible(true);
			}
		});
		bAddLicence.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					refreshData();
					
//					softwareTableModel.refreshUpdatedTable();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NotBoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
//				licencesForm.setVisible(true);
			}

			private void refreshData() throws RemoteException,
					NotBoundException {
				
				newSoftwareForm = new NewSoftwareForm();
				
				producersForm = new ProducersForm();
				
				licencesForm = new LicencesForm();
				loadProducersList();
				softwareTableModel.setSoftwareList((List<ISoftware>) getInstance().all());
				softwareTableModel.refreshUpdatedTable();
				softwareTable.repaint();
				System.out.println("Refresh");
			}
		});
		bCreateSoft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					createSoftware();
				} catch (HeadlessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		bUpdateSoft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					updateSoftware();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		bDeleteSoft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeSoftware();
			}
		});
		bPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				printSoftware();
			}
		});
		bReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					reportSoftware();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		prodList.addListSelectionListener(new ListSelectionListener() {
		      public void valueChanged(ListSelectionEvent evt) {
		        if (evt.getValueIsAdjusting() || prodList.getSelectedValue()==null)
		          return;
		        try {
			        String value = prodList.getSelectedValue().toString();
			        String[] arr = value.split("#");
			        Integer prodId = Integer.parseInt(arr[0]);
			        System.out.println(prodId);
			        if(prodId==0)
			        	softwareTableModel.setSoftwareList((List<ISoftware>) getInstance().all());
			        else
			        	softwareTableModel.setSoftwareList((List<ISoftware>) getInstance().findAllByProducerId(prodId));
					softwareTableModel.refreshUpdatedTable();
					softwareTable.repaint();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NotBoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		        
		      }
		    });
	}
	
	private void printSoftware() {
		try {
			MessageFormat headerFormat = new MessageFormat("Page {0}");
			MessageFormat footerFormat = new MessageFormat("- {0} -");
			softwareTable.print(JTable.PrintMode.FIT_WIDTH, headerFormat,
					footerFormat);
		} catch (PrinterException pe) {
			System.err.println("You can not print the document because: "
					+ pe.getMessage());
		}
	}
	
	private void reportSoftware() throws IOException {
		String fileName = JOptionPane.showInputDialog ("Enter file name...");
		if(fileName != null){
			if(!fileName.equals("")){
				Date d = new Date();
		        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy hh:mm");
				BufferedWriter bfw = new BufferedWriter(new FileWriter(fileName+".txt"));
				for(int i = 0 ; i < softwareTable.getColumnCount() ; i++)
				{
					bfw.write(String.format("%30s",softwareTable.getColumnName(i)));
					bfw.write("|");
				}
				bfw.newLine();
				for (int i = 0 ; i < softwareTable.getRowCount(); i++)
				{
					bfw.newLine();
				    for(int j = 0 ; j < softwareTable.getColumnCount();j++)
					{
				    	bfw.write((String)(String.format("%30s",softwareTable.getValueAt(i,j))));
				    	bfw.write("|");
					}
				    System.out.println("\r\n");
				}
				bfw.newLine();
				bfw.newLine();
				bfw.write("Date: "+format.format(d));
				JOptionPane.showMessageDialog(SoftwareForm.this, "The report was successfully generated!", "Success", JOptionPane.DEFAULT_OPTION );
				bfw.close();
			}else JOptionPane.showMessageDialog(SoftwareForm.this, "File name can't be blank!", "Error", JOptionPane.DEFAULT_OPTION );
		}
			
	}
	
	private void createSoftware() throws HeadlessException, RemoteException {
		try {
			newSoftwareForm.setSoftware(getInstance());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		newSoftwareForm.setVisible(true);
		if (newSoftwareForm.getSoftware().getSoftwareId() != null) {
			softwareTableModel.addSoftware(newSoftwareForm.getSoftware());
			JOptionPane.showMessageDialog(SoftwareForm.this, "Record was successfully created!", "Success", JOptionPane.DEFAULT_OPTION );
		}
	}
	
	private void updateSoftware() throws RemoteException {
		int index = softwareTable.getSelectedRow();
		if (index == -1){
			JOptionPane.showMessageDialog(SoftwareForm.this, "Do not select any field, please select field!", "Error", JOptionPane.DEFAULT_OPTION );
			return;
		}
		ISoftware software = softwareTableModel.getRowSoftware(index);
		if (software != null) {
			newSoftwareForm.setSoftware(software);
			newSoftwareForm.setVisible(true);
			softwareTableModel.refreshUpdatedTable();
//			JOptionPane.showMessageDialog(MainForm.this, "Record was successfully updated!", "Success", JOptionPane.DEFAULT_OPTION );
		}
	}
	
	private void removeSoftware() {
		int index = softwareTable.getSelectedRow();
		if (index == -1){
			JOptionPane.showMessageDialog(SoftwareForm.this, "Do not select any field, please select field!", "Error", JOptionPane.DEFAULT_OPTION );
			return;
		}
		if (JOptionPane.showConfirmDialog(SoftwareForm.this,
				"Are you sure you want to delete this software?",
				"Removing sowtware", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			try {
				ISoftware s = softwareTableModel.getRowSoftware(index);
				if (s != null) {
					if(s.delete()){
						softwareTableModel.removeRow(index);
						JOptionPane.showMessageDialog(SoftwareForm.this, "Record was successfully deleted!", "Success", JOptionPane.DEFAULT_OPTION );
					}
					else 
						JOptionPane.showMessageDialog(SoftwareForm.this, "You can not remove record!", "Error", JOptionPane.DEFAULT_OPTION );
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(SoftwareForm.this, e.getMessage());
			}
		}
	}
	
	private void loadProducersList() throws RemoteException, NotBoundException{
		listModel.clear();
		listModel.addElement("0# All");
		for (IProducer p : (List<IProducer>)getProducerInstance().all()) {
			listModel.addElement(p.getProducerId()+"# "+p.getName());
		}
		
		prodList.repaint();// = new JList(listModel);
	}
	
	private IProducer getProducerInstance() throws RemoteException, NotBoundException{
		Registry registry = LocateRegistry.getRegistry("localhost", Constant.RMI_PORT);
		IProducer producer = (IProducer) registry.lookup(Constant.RMI_PRODUCER_ID);
		return producer;
	}
	
	private ISoftware getInstance() throws RemoteException, NotBoundException{
		Registry registry = LocateRegistry.getRegistry("localhost", Constant.RMI_PORT);
		ISoftware software = (ISoftware) registry.lookup(Constant.RMI_SOFTWARE_ID);
		
		return software.newInstance();
	}
	
	
	
//	private ProducerTableModel getTableModel() {
//		try {
//			return new ProducerTableModel(Producer.all());
//		} catch (Exception e) {
//			e.printStackTrace();
//			JOptionPane.showMessageDialog(this,
//					"Помилка при заповненні таблиці груп: " + e.getMessage());
//		}
//		return new ProducerTableModel(new ArrayList<Producer>(0));
//	}
	
	public static void main(String[] args) {
		
		SoftwareForm mForm;
		try {
			mForm = new SoftwareForm();
			mForm.setVisible(true);
			JFrame.setDefaultLookAndFeelDecorated(true);
			JDialog.setDefaultLookAndFeelDecorated(true);
			mForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		

	}

}
