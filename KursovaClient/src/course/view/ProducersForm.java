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
import course.interf.model.IProducer;

public class ProducersForm extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String NEW = "newProducer";
	
	private static final String UPDATE = "updateProducer";
	
	private static final String DELETE= "deleteProducer";
	
	private static final String PRINT = "printProducer";
	
	private static final String REPORT = "reportProducer";
	
	private static final String CLOSE_WINDOW = "closeWindow";
	
	private JTable producersTable;
	
	private JPopupMenu popup = new JPopupMenu();
	
	private ProducerTableModel producersTableModel;
	
	private JButton bCreate = new JButton("Create");
	
	private JButton bUpdate = new JButton("Update");
	
	private JButton bDelete = new JButton("Delete");
	
	private JButton bPrint = new JButton("Print");
	
	private JButton bClose = new JButton("Close");
	
	private JButton bReport = new JButton("Report");
	
	private NewProducerForm newProducerForm = new NewProducerForm();
	
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

			reportProducer();

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
                reportProducer();
            }
        });
   

        add(toolbar, BorderLayout.NORTH);        
    }
	
	
	public ProducersForm(){
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		getContentPane().setLayout(new BorderLayout());
		
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
		producersTableModel = getTableModel();
		producersTable = new JTable(producersTableModel);
		producersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		producersTable.setPreferredScrollableViewportSize(new Dimension(880, 180));
		producersTable.getColumnModel().getColumn(0).setMinWidth(25);
		producersTable.getColumnModel().getColumn(1).setMinWidth(100);
		producersTable.getColumnModel().getColumn(2).setMinWidth(100);
		producersTable.getColumnModel().getColumn(3).setMinWidth(100);
		producersTable.getColumnModel().getColumn(4).setMinWidth(150);
		producersTable.getColumnModel().getColumn(5).setMinWidth(150);
		producersTable.getColumnModel().getColumn(6).setMinWidth(150);
		producersTable.getColumnModel().getColumn(7).setMinWidth(140);
		producersTable.setGridColor(Color.ORANGE);
		producersTable.setRowHeight(20);
		Font FontGrid = new Font(Font.MONOSPACED, Font.PLAIN, 14);
		producersTable.setFont(FontGrid);
		
		JScrollPane scrollPane = new JScrollPane(producersTable);
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

					reportProducer();

			}
		});
		producersTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            	if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2)
						updateProducer();
            	if (SwingUtilities.isRightMouseButton(e))
            		popup.show(producersTable, e.getX(), e.getY());
            }
        });
	}
	
	private void printProducer() {
		try {
			MessageFormat headerFormat = new MessageFormat("Page {0}");
			MessageFormat footerFormat = new MessageFormat("- {0} -");
			producersTable.print(JTable.PrintMode.FIT_WIDTH, headerFormat,
					footerFormat);
		} catch (PrinterException pe) {
			System.err.println("You can not print the document because: "
					+ pe.getMessage());
		}
	}
	
	private void reportProducer() {
		String fileName = JOptionPane.showInputDialog ("Enter file name...");
		if(fileName != null){
			if(!fileName.equals("")){
				try {
					Date d = new Date();
			        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy hh:mm");
					BufferedWriter bfw;
					
						bfw = new BufferedWriter(new FileWriter(fileName+".txt"));
					
					for(int i = 0 ; i < producersTableModel.getColumnCount() ; i++)
					{
						bfw.write(String.format("%20s",producersTableModel.getColumnName(i)));
						bfw.write("|");
					}
					bfw.newLine();
					for (int i = 0 ; i < producersTableModel.getRowCount(); i++)
					{
						bfw.newLine();
					    for(int j = 0 ; j < producersTableModel.getColumnCount();j++)
						{
					    	bfw.write((String)(String.format("%20s",producersTableModel.getValueAt(i,j))));
					    	bfw.write("|");
						}
					    System.out.println("\r\n");
					}
					bfw.newLine();
					bfw.newLine();
					bfw.write("Date: "+format.format(d));
					JOptionPane.showMessageDialog(ProducersForm.this, "The report was successfully generated!", "Success", JOptionPane.DEFAULT_OPTION );
					bfw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else JOptionPane.showMessageDialog(ProducersForm.this, "File name can't be blank!", "Error", JOptionPane.DEFAULT_OPTION );
		}
	}
	
	private void createProducer() {
		try {
			newProducerForm.setProducer(getInstance());
		
			newProducerForm.setVisible(true);
			if (newProducerForm.getProducer().getProducerId() != null) {
				producersTableModel.addProducer(newProducerForm.getProducer());
				JOptionPane.showMessageDialog(ProducersForm.this, "Record was successfully created!", "Success", JOptionPane.DEFAULT_OPTION );
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}
	
	private void updateProducer() {
		try {
		int index = producersTable.getSelectedRow();
		if (index == -1){
			JOptionPane.showMessageDialog(ProducersForm.this, "Do not select any field, please select field!", "Error", JOptionPane.DEFAULT_OPTION );
			return;
		}
		IProducer producer = producersTableModel.getRowGroup(index);
		if (producer != null) {
			
				newProducerForm.setProducer(producer);
			
			newProducerForm.setVisible(true);
			producersTableModel.refreshUpdatedTable();
		}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	private void removeProducer() {
		int index = producersTable.getSelectedRow();
		if (index == -1){
			JOptionPane.showMessageDialog(ProducersForm.this, "Do not select any field, please select field!", "Error", JOptionPane.DEFAULT_OPTION );
			return;
		}
		if (JOptionPane.showConfirmDialog(ProducersForm.this,
				"Are you sure you want to delete producer?",
				"Removing producers", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			try {
				IProducer p = producersTableModel.getRowGroup(index);
				if (p != null) {
					if(p.delete()){
						producersTableModel.removeRow(index);
						JOptionPane.showMessageDialog(ProducersForm.this, "Record was successfully deleted!", "Success", JOptionPane.DEFAULT_OPTION );
					}
					else 
						JOptionPane.showMessageDialog(ProducersForm.this, "You can not remove record!", "Error", JOptionPane.DEFAULT_OPTION );
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(ProducersForm.this, e.getMessage());
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private ProducerTableModel getTableModel() {
		try {
			List<IProducer> all = (List<IProducer>)getInstance().all();
			return new ProducerTableModel(all);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this,
					"Помилка при заповненні таблиці груп: " + e.getMessage());
		}
		return new ProducerTableModel(new ArrayList<IProducer>(0));
	}
	
	private void cancel() {
		this.setVisible(false);
	}
	
	private IProducer getInstance() throws RemoteException, NotBoundException{
		Registry registry = LocateRegistry.getRegistry("localhost", Constant.RMI_PORT);
		IProducer producer = (IProducer) registry.lookup(Constant.RMI_PRODUCER_ID);
		return producer.newInstance();
	}
	
	public static void main(String[] args) {
		
		ProducersForm pForm = new ProducersForm(); 

		pForm.setVisible(true);
		pForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	

}
