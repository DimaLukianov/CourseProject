package course.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.HeadlessException;
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
import java.util.Date;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
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
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import course.interf.Constant;
import course.interf.model.IProducer;
import course.interf.model.ISoftware;

public class SoftwareForm extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String NEW = "newSoftware";
	
	private static final String UPDATE = "updateSoftware";
	
	private static final String DELETE= "deleteSoftware";
	
	private static final String PRINT = "printSoftware";
	
	private static final String REPORT = "reportSoftware";
	
	private static final String REFRESH = "refreshData";
	
	private static final String OPEN_PROD_WINDOW = "prodWindow";
	
	private static final String OPEN_LIC_WINDOW = "licWindow";
	
	private static final String CLOSE_WINDOW = "closeWindow";
	
	DefaultListModel listModel = new DefaultListModel();
	
	DefaultListModel listModel2 = new DefaultListModel();
	
	private JList prodList;
	
	private JPopupMenu popup = new JPopupMenu();
	
	private JTable softwareTable;
	
	private SoftwareTableModel softwareTableModel;
	
	private JButton bShowAllProd = new JButton("Show All");
	
	private JButton bShowAllLicence = new JButton("All licence");
	
	private JButton bRefreshData = new JButton("Refresh");
	
	private JButton bCreateSoft = new JButton("Create");
	
	private JButton bUpdateSoft = new JButton("Update");
	
	private JButton bDeleteSoft = new JButton("Delete");
	
	private JButton bPrint = new JButton("Print");
	
	private JButton bReport = new JButton("Repotr");
	
	private NewSoftwareForm newSoftwareForm = new NewSoftwareForm();
	
//	private ProducersForm producersForm = new ProducersForm();
	
//	private LicencesForm licencesForm = new LicencesForm();
	
	private JMenuItem menuItemNew, menuItemUpdate, menuItemDelete, menuItemPrint, menuItemReport,
	menuItemRefresh, menuItemProducers, menuItemLicenses, menuItemClose;

	private JMenuItem popupItemNew, popupItemUpdate, popupItemDelete, popupItemPrint, popupItemReport;

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == menuItemNew || e.getSource() == popupItemNew) {

			createSoftware();

		} else if (e.getSource() == menuItemUpdate || e.getSource() == popupItemUpdate) {

			updateSoftware();

		} else if (e.getSource() == menuItemDelete || e.getSource() == popupItemDelete) {
			
			removeSoftware();
			
		} else if (e.getSource() == menuItemPrint || e.getSource() == popupItemPrint) {
			
			printSoftware();
			
		} else if (e.getSource() == menuItemReport || e.getSource() == popupItemReport) {

			reportSoftware();

		} else if (e.getSource() == menuItemRefresh) {
			
			refreshData();

		} else if (e.getSource() == menuItemProducers) {
			ProducersForm producersForm = new ProducersForm();
			producersForm.setVisible(true);
		} else if (e.getSource() == menuItemLicenses) {
			LicencesForm licencesForm = new LicencesForm();
			licencesForm.setVisible(true);
		} else if (e.getSource() == menuItemClose) {
			this.setVisible(false);
		}
		
		
	}
	
	private void createMenu() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menuFile = new JMenu("File");
		menuItemNew = new JMenuItem("New software");
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
		
		menuItemRefresh = new JMenuItem("Refresh data");
		menuItemRefresh.setName(REFRESH);
		menuItemRefresh.setIcon(new ImageIcon("img/min_refresh.png"));
		menuItemRefresh.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, Event.ALT_MASK));
		menuItemRefresh.addActionListener(this);
		menuFile.add(menuItemRefresh);
		
		// Вставляємо випадаюче меню в рядок меню
		menuBar.add(menuFile);
		
		JMenu menuWindow = new JMenu("Window");
		menuItemProducers = new JMenuItem("Producers");
		menuItemProducers.setName(OPEN_PROD_WINDOW);
		menuItemProducers.setIcon(new ImageIcon("img/min_producer.png"));
		menuItemProducers.addActionListener(this);
		menuWindow.add(menuItemProducers);
		
		menuItemLicenses= new JMenuItem("Licenses");
		menuItemLicenses.setName(OPEN_LIC_WINDOW);
		menuItemLicenses.setIcon(new ImageIcon("img/min_licence.png"));
		menuItemLicenses.addActionListener(this);
		menuWindow.add(menuItemLicenses);
		
		menuItemClose= new JMenuItem("Close");
		menuItemClose.setName(CLOSE_WINDOW);
		menuItemClose.addActionListener(this);
		menuWindow.add(menuItemClose);
		
		menuBar.add(menuWindow);
		
		setJMenuBar(menuBar);
	}
	
	private void createPopupMenu() {
		popupItemNew = new JMenuItem("New software");
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
        
        ImageIcon refreshIcon = new ImageIcon("img/refresh.png");
        JButton refreshButton = new JButton(refreshIcon);
        refreshButton.setToolTipText("Refresh data");
        toolbar.add(refreshButton);
        
        toolbar.addSeparator();
        
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
        
        
        ImageIcon licenceIcon = new ImageIcon("img/licence.png");
        JButton licenceButton = new JButton(licenceIcon);
        licenceButton.setToolTipText("Open license window");
        toolbar.add(licenceButton);
        
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
            	refreshData();
            }
        });
        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                createSoftware();
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                updateSoftware();;
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
            	removeSoftware();
            }
        });
        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                printSoftware();
            }
        });
        reportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                reportSoftware();
            }
        });
        producerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
            	ProducersForm producersForm = new ProducersForm();
            	producersForm.setVisible(true);
            }
        });

        licenceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
            	LicencesForm licencesForm = new LicencesForm();
            	licencesForm.setVisible(true);
            }
        });

        add(toolbar, BorderLayout.NORTH);        
    }
	
	
	
	@SuppressWarnings("unchecked")
	public SoftwareForm(){
		getContentPane().setLayout(new BorderLayout());
		pack();
		setLocationRelativeTo(null);
		
		createToolBar();
		
		createMenu();
		
		createPopupMenu();
		
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
		try {
			loadProducersList();
		} catch (RemoteException e1) {
			e1.printStackTrace();
		} catch (NotBoundException e1) {
			e1.printStackTrace();
		}
		
		left.add(new JScrollPane(prodList), BorderLayout.CENTER);
		left.add(bShowAllProd, BorderLayout.SOUTH);
				
		// Створюємо праву панель для виведення списку студентів
		JPanel right = new JPanel();
		// Задаємо layout і задаємо "бордюр" навколо панелі
		right.setLayout(new BorderLayout());
		right.setBorder(new BevelBorder(BevelBorder.RAISED));				
				
		try {
			softwareTableModel = new SoftwareTableModel((List<ISoftware>) getInstance().all());
		} catch (RemoteException e1) {
			e1.printStackTrace();
		} catch (NotBoundException e1) {
			e1.printStackTrace();
		}
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
		
		nav.add(bRefreshData);
		nav.add(new JSeparator(SwingConstants.VERTICAL));
		nav.add(bDeleteSoft);
		nav.add(bUpdateSoft);
		nav.add(bReport);
		nav.add(bPrint);
		nav.add(bCreateSoft);
		
		navLicence.add(bShowAllLicence);
		
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
				ProducersForm producersForm = new ProducersForm();
				producersForm.setVisible(true);
			}
		});
		bShowAllLicence.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LicencesForm licencesForm = new LicencesForm();
				licencesForm.setVisible(true);
			}
		});
		bRefreshData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					refreshData();
			}

			
		});
		bCreateSoft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				createSoftware();
			
			}
		});
		bUpdateSoft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				updateSoftware();
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

				reportSoftware();
					
			}
		});
		prodList.addListSelectionListener(new ListSelectionListener() {
		      public void valueChanged(ListSelectionEvent evt) {
		    	  
		        findSoftByProdId(evt);
		        
		   }
		});
		softwareTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            	if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2)
						updateSoftware();
            	if (SwingUtilities.isRightMouseButton(e))
            		popup.show(softwareTable, e.getX(), e.getY());
            }
        });
	}

	
	
	@SuppressWarnings("unchecked")
	private void refreshData() {
		
		newSoftwareForm = new NewSoftwareForm();
		
//		producersForm = new ProducersForm();
		
//		licencesForm = new LicencesForm();
		try {
			loadProducersList();
			softwareTableModel.setSoftwareList((List<ISoftware>) getInstance().all());
			softwareTableModel.refreshUpdatedTable();
			softwareTable.repaint();
			System.out.println("Refresh");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		
	}
	
	@SuppressWarnings("unchecked")
	private void findSoftByProdId(ListSelectionEvent evt) {
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
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
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
	
	private void reportSoftware() {
		String fileName = JOptionPane.showInputDialog ("Enter file name...");
		if(fileName != null){
			if(!fileName.equals("")){
				try {
					Date d = new Date();
			        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy hh:mm");
					BufferedWriter bfw;
					
						bfw = new BufferedWriter(new FileWriter(fileName+".txt"));
					
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
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else JOptionPane.showMessageDialog(SoftwareForm.this, "File name can't be blank!", "Error", JOptionPane.DEFAULT_OPTION );
		}
			
	}
	
	private void createSoftware() {
		try {
			newSoftwareForm.setSoftware(getInstance());
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		newSoftwareForm.setVisible(true);
		try {
			if (newSoftwareForm.getSoftware().getSoftwareId() != null) {
				softwareTableModel.addSoftware(newSoftwareForm.getSoftware());
				JOptionPane.showMessageDialog(SoftwareForm.this, "Record was successfully created!", "Success", JOptionPane.DEFAULT_OPTION );
			}
		} catch (HeadlessException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	private void updateSoftware() {
		try {
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
			}
		} catch (RemoteException e) {
			e.printStackTrace();
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
	
	@SuppressWarnings("unchecked")
	private void loadProducersList() throws RemoteException, NotBoundException{
		listModel.clear();
		listModel.addElement("0# All");
		for (IProducer p : (List<IProducer>)getProducerInstance().all()) {
			listModel.addElement(p.getProducerId()+"# "+p.getName());
		}
		
		prodList.repaint();// = new JList(listModel);
	}
	
	private IProducer getProducerInstance() throws RemoteException, NotBoundException{
		Registry registry = LocateRegistry.getRegistry(Constant.RMI_HOST, Constant.RMI_PORT);
		IProducer producer = (IProducer) registry.lookup(Constant.RMI_PRODUCER_ID);
		return producer;
	}
	
	private ISoftware getInstance() throws RemoteException, NotBoundException{
		Registry registry = LocateRegistry.getRegistry(Constant.RMI_HOST, Constant.RMI_PORT);
		ISoftware software = (ISoftware) registry.lookup(Constant.RMI_SOFTWARE_ID);
		
		return software.newInstance();
	}
	
	
	public static void main(String[] args) {
		try {
			//windows style
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			JFrame.setDefaultLookAndFeelDecorated(true);
			JDialog.setDefaultLookAndFeelDecorated(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		SoftwareForm mForm;
		mForm = new SoftwareForm();
		mForm.setVisible(true);
		mForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 

		

	}

}
