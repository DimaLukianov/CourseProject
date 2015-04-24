package course.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;

import course.interf.Constant;
import course.interf.model.ILicence;

public class LicencesForm extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String NEW = "newLicence";
	
	private static final String UPDATE = "updateLicence";
	
	private static final String DELETE= "deleteLicence";
	
	private static final String PRINT = "printLicence";
	
	private static final String REPORT = "reportLicence";
	
	private static final String CLOSE_WINDOW = "closeWindow";
	
	private JPopupMenu popup = new JPopupMenu();
	
	private JTable licencesTable;
	
	private LicenceTableModel licencesTableModel;
	
	private JButton bCreate = new JButton("Create");
	
	private JButton bUpdate = new JButton("Update");
	
	private JButton bDelete = new JButton("Delete");
	
	private JButton bPrint = new JButton("Print");
	
	private JButton bClose = new JButton("Close");
	
	private JButton bReport = new JButton("Report");
	
	private NewLicenceForm newLicenceForm = new NewLicenceForm();
	
	private JMenuItem menuItemNew, menuItemUpdate, menuItemDelete, menuItemPrint, menuItemReport,
	menuItemClose;

	private JMenuItem popupItemNew, popupItemUpdate, popupItemDelete, popupItemPrint, popupItemReport;


	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == menuItemNew || e.getSource() == popupItemNew) {

			createProducer();

		} else if (e.getSource() == menuItemUpdate || e.getSource() == popupItemUpdate) {

			updateProducer();

		} else if (e.getSource() == menuItemDelete || e.getSource() == popupItemDelete) {
			
			removeProducer();
			
		} else if (e.getSource() == menuItemPrint || e.getSource() == popupItemPrint) {
			
			printProducer();
			
		} else if (e.getSource() == menuItemReport || e.getSource() == popupItemReport) {

			reportLicence();

		} else if (e.getSource() == menuItemClose) {
			this.setVisible(false);
		}
		
	}
	
	private void createMenu() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menuFile = new JMenu("File");
		menuItemNew = new JMenuItem("New");
		menuItemNew.setName(NEW);
		menuItemNew.setIcon(new ImageIcon("img/min_new.png"));
		menuItemNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Event.CTRL_MASK));
		menuItemNew.addActionListener(this);
		// Вставляємо пункт меню у випадаюче меню
		menuFile.add(menuItemNew);
		
		
		menuItemUpdate = new JMenuItem("Update");
		menuItemUpdate.setName(UPDATE);
		menuItemUpdate.setIcon(new ImageIcon("img/min_update.png"));
		menuItemUpdate.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, Event.CTRL_MASK));
		menuItemUpdate.addActionListener(this);
		menuFile.add(menuItemUpdate);
		
		menuItemDelete = new JMenuItem("Delete");
		menuItemDelete.setName(DELETE);
		menuItemDelete.setIcon(new ImageIcon("img/min_delete.png"));
		menuItemDelete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, Event.CTRL_MASK));
		menuItemDelete.addActionListener(this);
		menuFile.add(menuItemDelete);
		
		menuFile.addSeparator();
		
		menuItemPrint = new JMenuItem("Print");
		menuItemPrint.setName(PRINT);
		menuItemPrint.setIcon(new ImageIcon("img/min_print.png"));
		menuItemPrint.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, Event.CTRL_MASK));
		menuItemPrint.addActionListener(this);
		menuFile.add(menuItemPrint);
		
		menuItemReport = new JMenuItem("Generate report");
		menuItemReport.setName(REPORT);
		menuItemReport.setIcon(new ImageIcon("img/min_report.png"));
		menuItemReport.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, Event.CTRL_MASK));
		menuItemReport.addActionListener(this);
		menuFile.add(menuItemReport);
		
		menuFile.addSeparator();
		
		// Вставляємо випадаюче меню в рядок меню
		menuBar.add(menuFile);
		
		JMenu menuWindow = new JMenu("Window");
		
		menuItemClose= new JMenuItem("Close");
		menuItemClose.setName(CLOSE_WINDOW);
		menuItemClose.addActionListener(this);
		menuWindow.add(menuItemClose);
		
		menuBar.add(menuWindow);
		
		setJMenuBar(menuBar);
	}
	
	private void createPopupMenu() {
		popupItemNew = new JMenuItem("New");
		popupItemNew.setName(NEW);
		popupItemNew.addActionListener(this);
		popup.add(popupItemNew);
		
		popupItemUpdate = new JMenuItem("Update");
		popupItemUpdate.setName(UPDATE);
		popupItemUpdate.addActionListener(this);
		popup.add(popupItemUpdate);
		
		popupItemDelete = new JMenuItem("Delete");
		popupItemDelete.setName(DELETE);
		popupItemDelete.addActionListener(this);
		popup.add(popupItemDelete);
		
		popup.addSeparator();
		
		popupItemPrint = new JMenuItem("Print");
		popupItemPrint.setName(PRINT);
		popupItemPrint.addActionListener(this);
		popup.add(popupItemPrint);
		
		popupItemReport = new JMenuItem("Generate report");
		popupItemReport.setName(REPORT);
		popupItemReport.addActionListener(this);
		popup.add(popupItemReport);
	}
	
private void createToolBar() {
        
        JToolBar toolbar = new JToolBar();
        
        ImageIcon newIcon = new ImageIcon("img/new.png");
        JButton newButton = new JButton(newIcon);
        newButton.setToolTipText("New");
        toolbar.add(newButton);
        
        ImageIcon updateIcon = new ImageIcon("img/update.png");
        JButton updateButton = new JButton(updateIcon);
        updateButton.setToolTipText("Update");
        toolbar.add(updateButton);
        
        ImageIcon deleteIcon = new ImageIcon("img/delete.png");
        JButton deleteButton = new JButton(deleteIcon);
        deleteButton.setToolTipText("Delete");
        toolbar.add(deleteButton);
        
        toolbar.addSeparator();
        
        ImageIcon printIcon = new ImageIcon("img/print.png");
        JButton printButton = new JButton(printIcon);
        printButton.setToolTipText("Print");
        toolbar.add(printButton);
        
        ImageIcon reportIcon = new ImageIcon("img/report.png");
        JButton reportButton = new JButton(reportIcon);
        reportButton.setToolTipText("Generate report");
        toolbar.add(reportButton);
        
        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                createProducer();
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                updateProducer();
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
            	removeProducer();
            }
        });
        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                printProducer();
            }
        });
        reportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                reportLicence();
            }
        });
   

        add(toolbar, BorderLayout.NORTH);        
    }
	
	
	public LicencesForm(){
		getContentPane().setLayout(new BorderLayout());
		pack();
		setLocationRelativeTo(null);
		
		createMenu();
		
		createPopupMenu();
		
		createToolBar();
		
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
		
		bot.add(right, BorderLayout.CENTER);

		// Вставляємо верхню і нижню панелі у форму
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
					createProducer();
			}
		});
		bUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					updateProducer();
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
					reportLicence();
			}
		});
		licencesTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            	if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2)
						updateProducer();
            	if (SwingUtilities.isRightMouseButton(e))
            		popup.show(licencesTable, e.getX(), e.getY());
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
	
	private void reportLicence() {
		String fileName = JOptionPane.showInputDialog ("Enter file name...");
		if(fileName != null){
			if(!fileName.equals("")){
				try {
					Date d = new Date();
			        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy hh:mm");
					BufferedWriter bfw;
					
						bfw = new BufferedWriter(new FileWriter(fileName+".txt"));
					
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
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else JOptionPane.showMessageDialog(LicencesForm.this, "File name can't be blank!", "Error", JOptionPane.DEFAULT_OPTION );
		}
	}
	
	private void createProducer(){
		try {
			newLicenceForm.setLicence(getInstance());
		
			newLicenceForm.setVisible(true);
			if (newLicenceForm.getLicence().getLicenceId() != null) {
				licencesTableModel.addLicence(newLicenceForm.getLicence());
				JOptionPane.showMessageDialog(LicencesForm.this, "Record was successfully created!", "Success", JOptionPane.DEFAULT_OPTION );
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}
	
	private void updateProducer(){
		try {
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
			}
		} catch (RemoteException e) {
			e.printStackTrace();
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
	
	@SuppressWarnings("unchecked")
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
		Registry registry = LocateRegistry.getRegistry(Constant.RMI_HOST, Constant.RMI_PORT);
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
