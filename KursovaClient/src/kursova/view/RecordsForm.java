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
import kursova.interf.model.IRecord;

public class RecordsForm extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String ALL_STUDENTS = "allStudent";
	
	private JTable recordsTable;
	
	private RecordsTableModel recordsTableModel;
	
	private JButton bCreate = new JButton("Create");
	
	private JButton bUpdate = new JButton("Update");
	
	private JButton bDelete = new JButton("Delete");
	
	private JButton bPrint = new JButton("Print");
	
	private JButton bClose = new JButton("Close");
	
	private JButton bReport = new JButton("Report");
	
	private NewRecordForm newRecordForm = new NewRecordForm();


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	public RecordsForm(){
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
		recordsTableModel = getTableModel();
		recordsTable = new JTable(recordsTableModel);
		recordsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		recordsTable.setPreferredScrollableViewportSize(new Dimension(880, 180));
		recordsTable.getColumnModel().getColumn(0).setMinWidth(25);
		recordsTable.getColumnModel().getColumn(1).setMinWidth(150);
		recordsTable.getColumnModel().getColumn(2).setMinWidth(60);
		recordsTable.getColumnModel().getColumn(3).setMinWidth(100);
		recordsTable.getColumnModel().getColumn(4).setMinWidth(100);
		recordsTable.getColumnModel().getColumn(4).setMinWidth(100);
		recordsTable.setGridColor(Color.ORANGE);
		recordsTable.setRowHeight(40);
		Font FontGrid = new Font(Font.MONOSPACED, Font.PLAIN, 14);
		recordsTable.setFont(FontGrid);
		
		JScrollPane scrollPane = new JScrollPane(recordsTable);
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
					createRecord();
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
					updateRecord();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		bDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeRecord();
			}
		});
		bPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				printRecord();
			}
		});
		bReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					reportRecord();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}
	
	private void printRecord() {
		try {
			MessageFormat headerFormat = new MessageFormat("Page {0}");
			MessageFormat footerFormat = new MessageFormat("- {0} -");
			recordsTable.print(JTable.PrintMode.FIT_WIDTH, headerFormat,
					footerFormat);
		} catch (PrinterException pe) {
			System.err.println("You can not print the document because: "
					+ pe.getMessage());
		}
	}
	
	private void reportRecord() throws IOException {
		String fileName = JOptionPane.showInputDialog ("Enter file name...");
		if(fileName != null){
			if(!fileName.equals("")){
				Date d = new Date();
		        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy hh:mm");
				BufferedWriter bfw = new BufferedWriter(new FileWriter(fileName+".txt"));
				for(int i = 0 ; i < recordsTable.getColumnCount() ; i++)
				{
					bfw.write(String.format("%20s",recordsTable.getColumnName(i)));
					bfw.write("|");
				}
				bfw.newLine();
				for (int i = 0 ; i < recordsTable.getRowCount(); i++)
				{
					bfw.newLine();
				    for(int j = 0 ; j < recordsTable.getColumnCount();j++)
					{
				    	bfw.write((String)(String.format("%20s",recordsTable.getValueAt(i,j))));
				    	bfw.write("|");
					}
				    System.out.println("\r\n");
				}
				bfw.newLine();
				bfw.newLine();
				bfw.write("Date: "+format.format(d));
				JOptionPane.showMessageDialog(RecordsForm.this, "The report was successfully generated!", "Success", JOptionPane.DEFAULT_OPTION );
				bfw.close();
			}else JOptionPane.showMessageDialog(RecordsForm.this, "File name can't be blank!", "Error", JOptionPane.DEFAULT_OPTION );
		}
	}
	
	private void createRecord() throws RemoteException, NotBoundException {
		newRecordForm.setRecord(getInstance());
		newRecordForm.setVisible(true);
		if (newRecordForm.getRecord().getRefer().getRefId() != null) {
			recordsTableModel.addRecord(newRecordForm.getRecord());
			JOptionPane.showMessageDialog(RecordsForm.this, "Record was successfully created!", "Success", JOptionPane.DEFAULT_OPTION );
		}
	}
	
	private void updateRecord() throws RemoteException {
		int index = recordsTable.getSelectedRow();
		if (index == -1){
			JOptionPane.showMessageDialog(RecordsForm.this, "Do not select any field, please select field!", "Error", JOptionPane.DEFAULT_OPTION );
			return;
		}
		IRecord record = recordsTableModel.getRowRecord(index);
		if (record != null) {
			newRecordForm.setRecord(record);
			newRecordForm.setVisible(true);
			recordsTableModel.refreshUpdatedTable();
		}
	}
	
	private void removeRecord() {
		int index = recordsTable.getSelectedRow();
		if (index == -1){
			JOptionPane.showMessageDialog(RecordsForm.this, "Do not select any field, please select field!", "Error", JOptionPane.DEFAULT_OPTION );
			return;
		}
		if (JOptionPane.showConfirmDialog(RecordsForm.this,
				"Are you sure you want to delete record?",
				"Removing record", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			try {
				IRecord r = recordsTableModel.getRowRecord(index);
				if (r != null) {
					if(r.getRefer().delete()){
						recordsTableModel.removeRow(index);
						JOptionPane.showMessageDialog(RecordsForm.this, "Record was successfully deleted!", "Success", JOptionPane.DEFAULT_OPTION );
					}
					else 
						JOptionPane.showMessageDialog(RecordsForm.this, "You can not remove record!", "Error", JOptionPane.DEFAULT_OPTION );
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(RecordsForm.this, e.getMessage());
			}
		}
	}
	
	private RecordsTableModel getTableModel() {
		try {
			return new RecordsTableModel((List<IRecord>) getInstance().all());
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this,
					"Error: " + e.getMessage());
		}
		return new RecordsTableModel(new ArrayList<IRecord>(0));
	}
	
	private void cancel() {
		this.setVisible(false);
	}
	
	private IRecord getInstance() throws RemoteException, NotBoundException{
		Registry registry = LocateRegistry.getRegistry("localhost", Constant.RMI_PORT);
		IRecord record = (IRecord) registry.lookup(Constant.RMI_RECORD_ID);
		return record.newInstance();
	}
	
	public static void main(String[] args) {
		
		RecordsForm rForm = new RecordsForm(); 

		rForm.setVisible(true);
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		rForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	

}
