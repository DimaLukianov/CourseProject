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
import course.interf.model.IRecord;

public class RecordsForm extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String NEW = "newRecord";
	
	private static final String UPDATE = "updateRecord";
	
	private static final String DELETE= "deleteRecord";
	
	private static final String PRINT = "printRecord";
	
	private static final String REPORT = "reportRecord";
	
	private static final String OPEN_PROD_WINDOW = "prodWindow";
	
	private static final String OPEN_SOFT_WINDOW = "softWindow";
	
	private static final String OPEN_LIC_WINDOW = "licWindow";
	
	private static final String CLOSE_WINDOW = "closeWindow";
	
	private static final String OPEN_HELP_WINDOW = "helpWindow";
	
	private static final String OPEN_ABOUT_WINDOW = "aboutWindow";
	
	private JPopupMenu popup = new JPopupMenu();
	
	private JTable recordsTable;
	
	private RecordsTableModel recordsTableModel;
	
	private JButton bCreate = new JButton("Create");
	
	private JButton bUpdate = new JButton("Update");
	
	private JButton bDelete = new JButton("Delete");
	
	private JButton bPrint = new JButton("Print");
	
	private JButton bReport = new JButton("Report");
	
	private NewRecordForm newRecordForm = new NewRecordForm();
	
//	private SoftwareForm softwareForm = new SoftwareForm();
	
//	private ProducersForm producersForm = new ProducersForm();
	
//	private LicencesForm licencesForm = new LicencesForm();
	
//	private InfoForm infoForm = new InfoForm();
	
	private JMenuItem menuItemNew, menuItemUpdate, menuItemDelete, menuItemPrint, menuItemReport,
	menuItemProducers, menuItemSoftware, menuItemLicenses, menuItemClose, menuItemHelp, menuItemAbout;

	private JMenuItem popupItemNew, popupItemUpdate, popupItemDelete, popupItemPrint, popupItemReport;



	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == menuItemNew || e.getSource() == popupItemNew) {

			createRecord();

		} else if (e.getSource() == menuItemUpdate || e.getSource() == popupItemUpdate) {

			updateRecord();

		} else if (e.getSource() == menuItemDelete || e.getSource() == popupItemDelete) {
			
			removeRecord();
			
		} else if (e.getSource() == menuItemPrint || e.getSource() == popupItemPrint) {
			
			printRecord();
			
		} else if (e.getSource() == menuItemReport || e.getSource() == popupItemReport) {

			reportRecord();

		} else if (e.getSource() == menuItemProducers) {
			ProducersForm producersForm = new ProducersForm();
			producersForm.setVisible(true);
			
		} else if (e.getSource() == menuItemSoftware) {
			SoftwareForm softwareForm = new SoftwareForm();
			softwareForm.setVisible(true);
			
		} else if (e.getSource() == menuItemLicenses) {
			LicencesForm licencesForm = new LicencesForm();
			licencesForm.setVisible(true);
			
		} else if (e.getSource() == menuItemClose) {
			
			System.exit(0);
			
		} else if (e.getSource() == menuItemHelp) {
			InfoForm infoForm = new InfoForm();
			infoForm.setVisible(true);
		} else if (e.getSource() == menuItemAbout) {
			InfoForm infoForm = new InfoForm();
			infoForm.setVisible(true);
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
		
		// Вставляємо випадаюче меню в рядок меню
		menuBar.add(menuFile);
		
		JMenu menuWindow = new JMenu("Window");
		menuItemProducers = new JMenuItem("Producers");
		menuItemProducers.setName(OPEN_PROD_WINDOW);
		menuItemProducers.setIcon(new ImageIcon("img/min_producer.png"));
		menuItemProducers.addActionListener(this);
		menuWindow.add(menuItemProducers);
		
		menuItemSoftware= new JMenuItem("Software");
		menuItemSoftware.setName(OPEN_SOFT_WINDOW);
		menuItemSoftware.setIcon(new ImageIcon("img/min_software.png"));
		menuItemSoftware.addActionListener(this);
		menuWindow.add(menuItemSoftware);
		
		menuItemLicenses= new JMenuItem("Licenses");
		menuItemLicenses.setName(OPEN_LIC_WINDOW);
		menuItemLicenses.setIcon(new ImageIcon("img/min_licence.png"));
		menuItemLicenses.addActionListener(this);
		menuWindow.add(menuItemLicenses);
		
		menuItemClose= new JMenuItem("Exit");
		menuItemClose.setName(CLOSE_WINDOW);
		menuItemClose.setIcon(new ImageIcon("img/min_exit.png"));
		menuItemClose.addActionListener(this);
		menuWindow.add(menuItemClose);
		
		menuBar.add(menuWindow);
		
		JMenu menuInfo = new JMenu("Info");
		menuItemHelp = new JMenuItem("Help");
		menuItemHelp.setName(OPEN_HELP_WINDOW);
		menuItemHelp.setIcon(new ImageIcon("img/min_help.png"));
		menuItemHelp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, Event.CTRL_MASK));
		menuItemHelp.addActionListener(this);
		menuInfo.add(menuItemHelp);
		
		menuItemAbout= new JMenuItem("About");
		menuItemAbout.setName(OPEN_ABOUT_WINDOW);
		menuItemAbout.setIcon(new ImageIcon("img/min_about.png"));
		menuItemAbout.addActionListener(this);
		menuInfo.add(menuItemAbout);
		
		menuBar.add(menuInfo);
		
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
	        
	        toolbar.addSeparator();
	        
	        ImageIcon producerIcon = new ImageIcon("img/producer.png");
	        JButton producerButton = new JButton(producerIcon);
	        producerButton.setToolTipText("Open producer window");
	        toolbar.add(producerButton);
	        
	        ImageIcon softwareIcon = new ImageIcon("img/software.png");
	        JButton softwareButton = new JButton(softwareIcon);
	        softwareButton.setToolTipText("Open software window");
	        toolbar.add(softwareButton);
	        
	        ImageIcon licenceIcon = new ImageIcon("img/licence.png");
	        JButton licenceButton = new JButton(licenceIcon);
	        licenceButton.setToolTipText("Open license window");
	        toolbar.add(licenceButton);
	        
	        toolbar.addSeparator();

	        ImageIcon icon = new ImageIcon("img/exit.png");
	        JButton exitButton = new JButton(icon);
	        exitButton.setToolTipText("Exit");
	        toolbar.add(exitButton);
	        
	        newButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent event) {
	                createRecord();
	            }
	        });
	        updateButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent event) {
	                updateRecord();
	            }
	        });
	        deleteButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent event) {
	            	removeRecord();
	            }
	        });
	        printButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent event) {
	                printRecord();
	            }
	        });
	        reportButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent event) {
	                reportRecord();
	            }
	        });
	        producerButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent event) {
	            	ProducersForm producersForm = new ProducersForm();
	            	producersForm.setVisible(true);
	            }
	        });
	        softwareButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent event) {
	            	SoftwareForm softwareForm = new SoftwareForm();
	    			softwareForm.setVisible(true);
	            }
	        });
	        licenceButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent event) {
	            	LicencesForm licencesForm = new LicencesForm();
	            	licencesForm.setVisible(true);
	            }
	        });
	        exitButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent event) {
	            	System.exit(0);
	            }
	        });

	        add(toolbar, BorderLayout.NORTH);        
	    }
	
	public RecordsForm(){
		getContentPane().setLayout(new BorderLayout());
		pack();
		setLocationRelativeTo(null);
		
		
		createToolBar();
		
		createMenu();
		
		createPopupMenu();
		
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
		

		bCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
					createRecord();
				
			}
		});
		bUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

					updateRecord();
					
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
				
					reportRecord();
		
			}
		});
		recordsTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            	if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2)
						updateRecord();
            	if (SwingUtilities.isRightMouseButton(e))
            		popup.show(recordsTable, e.getX(), e.getY());
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
	
	private void reportRecord() {
		String fileName = JOptionPane.showInputDialog ("Enter file name...","RecordReport");
		if(fileName != null){
			if(!fileName.equals("")){
				try {
					Date d = new Date();
			        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy hh:mm");
					BufferedWriter bfw;
					
						bfw = new BufferedWriter(new FileWriter(fileName+".txt"));
					
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
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else JOptionPane.showMessageDialog(RecordsForm.this, "File name can't be blank!", "Error", JOptionPane.DEFAULT_OPTION );
		}
	}
	
	private void createRecord() {
		try {
			newRecordForm.setRecord(getInstance());
			newRecordForm.setVisible(true);
			if (newRecordForm.getRecord().getRefer().getRefId() != null) {
				recordsTableModel.addRecord(newRecordForm.getRecord());
				JOptionPane.showMessageDialog(RecordsForm.this, "Record was successfully created!", "Success", JOptionPane.DEFAULT_OPTION );
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		
	}
	
	private void updateRecord() {
		try {
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
		} catch (RemoteException e) {
			e.printStackTrace();
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
	
	@SuppressWarnings("unchecked")
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
	
	
	private IRecord getInstance() throws RemoteException, NotBoundException{
		Registry registry = LocateRegistry.getRegistry(Constant.RMI_HOST, Constant.RMI_PORT);
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
