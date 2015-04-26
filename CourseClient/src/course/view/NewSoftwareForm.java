package course.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.ParseException;
import java.util.List;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.MaskFormatter;

import course.interf.Constant;
import course.interf.model.IProducer;
import course.interf.model.ISoftware;



public class NewSoftwareForm extends JDialog {
	
	private static final String VERSION_PATTERN = "([0-9.]+)";
	
	private static final String NAME_PATTERN = "([(A-Za-z|А-ЯІа-яі)-/_\\s]+)";

	private static final long serialVersionUID = -7265530307974489903L;
	

	private ISoftware software;
	private Vector<String> producerList = loadProducersList();
	private JFormattedTextField nameText;
	private JTextField iconPathText;
	private JFormattedTextField versionText;
	private JCheckBox osWindows;
	private JCheckBox osUnix;
	private JCheckBox osMac;
	private JFormattedTextField releaseDate;
	private JComboBox producerId;
	private JLabel JLabel_1 = new JLabel();
	private JLabel JLabel_2 = new JLabel();
	private JLabel JLabel_3 = new JLabel();
	private JLabel JLabel_4 = new JLabel();
	private JLabel JLabel_5 = new JLabel();
	private JLabel JLabel_6 = new JLabel();
	private JLabel JLabel_7 = new JLabel();
	private JLabel JLabel_8 = new JLabel();


	public NewSoftwareForm() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		setTitle("New producer");
		setSize(400, 400);
		setModal(true);
		setResizable(false);
		setLocationRelativeTo(null);

		final JButton cmdSave = new JButton("Save");
		final JButton cmdCancel = new JButton("Cancel");
		final JButton bOpen = new JButton("Open icon...");

		nameText = new JFormattedTextField(new RegexFormatter(NAME_PATTERN));
		versionText = new JFormattedTextField(new RegexFormatter(VERSION_PATTERN));
		osWindows = new JCheckBox();
		osUnix = new JCheckBox();
		osMac = new JCheckBox();
		iconPathText = new JTextField(15);
		iconPathText.setEditable(false);
		MaskFormatter mf1;
		try {
			mf1 = new MaskFormatter("####-##-##");
			mf1.setPlaceholderCharacter('_');
			releaseDate = new JFormattedTextField(mf1);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		producerId = new JComboBox(producerList);
		final JPanel fieldsPanel = new JPanel(new GridLayout(9, 2, 10, 10));
		final JPanel fieldsPanelBorder = new JPanel(new FlowLayout(
				FlowLayout.CENTER, 10, 10));
		fieldsPanel.setOpaque(false);
		fieldsPanelBorder.setOpaque(false);
		fieldsPanelBorder.add(fieldsPanel);
		JLabel_1.setText("Name");
		JLabel_2.setText("Icon");
		JLabel_3.setText("Version");
		JLabel_4.setText("Windows");
		JLabel_5.setText("Unix");
		JLabel_6.setText("Mac");
		JLabel_7.setText("Release date");
		JLabel_8.setText("Producer");
		JLabel_8.setForeground(Color.ORANGE);
		JLabel_7.setForeground(Color.ORANGE);
		JLabel_6.setForeground(Color.ORANGE);
		JLabel_5.setForeground(Color.ORANGE);
		JLabel_4.setForeground(Color.ORANGE);
		JLabel_3.setForeground(Color.ORANGE);
		JLabel_2.setForeground(Color.ORANGE);
		JLabel_1.setForeground(Color.ORANGE);
		fieldsPanel.add(JLabel_1);
		fieldsPanel.add(nameText);
		fieldsPanel.add(JLabel_3);
		fieldsPanel.add(versionText);
		fieldsPanel.add(JLabel_4);
		fieldsPanel.add(osWindows);
		fieldsPanel.add(JLabel_5);
		fieldsPanel.add(osUnix);
		fieldsPanel.add(JLabel_6);
		fieldsPanel.add(osMac);
		fieldsPanel.add(JLabel_7);
		fieldsPanel.add(releaseDate);
		fieldsPanel.add(JLabel_8);
		fieldsPanel.add(producerId);
		fieldsPanel.add(JLabel_2);
		fieldsPanel.add(iconPathText);
		fieldsPanel.add(new JLabel());
		fieldsPanel.add(bOpen);
		

		final JPanel commandsPanel = new JPanel(new FlowLayout());
		final JPanel commandsPanelBorder = new JPanel(new FlowLayout(
				FlowLayout.CENTER, 0, 0));
		commandsPanelBorder.add(commandsPanel);
		commandsPanel.setOpaque(false);
		commandsPanel.add(cmdCancel);
		commandsPanel.add(cmdSave);
		commandsPanelBorder.setOpaque(false);
		
		getContentPane().add(fieldsPanelBorder, BorderLayout.NORTH);
		getContentPane().add(commandsPanelBorder, BorderLayout.SOUTH);
		
		cmdSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveSoftware();
			}
		});

		cmdCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelSave();
			}
		});
		bOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files(jpg | jpeg | png)", "jpg", "jpeg", "png");
				JFileChooser fileopen = new JFileChooser();
				if(!iconPathText.equals(""))fileopen.setCurrentDirectory(new File(iconPathText.getText()));
				fileopen.addChoosableFileFilter(filter);
                int ret = fileopen.showDialog(null, "Open file");                
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = fileopen.getSelectedFile();
                    iconPathText.setText(file.getPath());
                }
			}
		});
	}

	public ISoftware getSoftware() {
		return software;
	}

	public void setSoftware(ISoftware software) throws RemoteException {
		this.software = software;
		nameText.setText(software.getName());
		iconPathText.setText(software.getIconPath());
		versionText.setText(software.getVersion());
		osWindows.setSelected(software.isOsWindows());
		osUnix.setSelected(software.isOsUnix());
		osMac.setSelected(software.isOsMac());
		releaseDate.setText(software.getReleaseDate());
		if(software.getProducerId() != null)
			producerId.setSelectedItem(findProducerById(software.getProducerId()));
	}

	private void saveSoftware() {
		if(nameText.getText().equals(""))
			JOptionPane.showMessageDialog(NewSoftwareForm.this, "The name is entered incorrectly!", "Error", JOptionPane.DEFAULT_OPTION );
		else if(versionText.getText().equals(""))
			JOptionPane.showMessageDialog(NewSoftwareForm.this, "The version is entered incorrectly!", "Error", JOptionPane.DEFAULT_OPTION );
		else if(releaseDate.getText().equals(""))
			JOptionPane.showMessageDialog(NewSoftwareForm.this, "The release date is entered incorrectly!", "Error", JOptionPane.DEFAULT_OPTION );
		else{
			try {
				
				 String newIconPath = createNewIcon();
				
				software.setName(nameText.getText());
				software.setIconPath(newIconPath);
				software.setVersion(versionText.getText());
				software.setOsWindows(osWindows.isSelected());
				software.setOsUnix(osUnix.isSelected());
				software.setOsMac(osMac.isSelected());
				software.setReleaseDate(releaseDate.getText());
				//витягуємо id з назви
				String[] arr = ((String) producerId.getSelectedItem()).split("#");
				software.setProducerId(Integer.parseInt(arr[0]));
				
				if (software.getSoftwareId() == null) {
					software.save();
				} else {
					software.update();
				}
				this.setVisible(false);
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(this,
						"Error saving producer: " + e.getMessage());
			}
		}
	}

	private String createNewIcon() throws IOException {
		BufferedImage originalImage = ImageIO.read(new File(iconPathText.getText()));//change path to where file is located
		 int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
		 String array[] = iconPathText.getText().split("\\\\");
		 String newIconPath = "C:\\softwareImage\\"+array[(array.length-1)];
		 System.out.println(newIconPath);
		 BufferedImage resizeImageJpg = resizeImage(originalImage, type, 30, 30);
		 ImageIO.write(resizeImageJpg, "png", new File(newIconPath)); //change path where you want it saved
		return newIconPath;
	}

	private void cancelSave() {
		this.setVisible(false);
	}
	
	@SuppressWarnings("unchecked")
	private Vector<String> loadProducersList(){
		Vector<String> producersList = new Vector<String>();
		try {
			for (IProducer p : (List<IProducer>)getProducerInstance().all()) {
				producersList.add(p.getProducerId()+"# "+p.getName());
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		
		return producersList;
	}
	
	private String findProducerById(int id){
		String producer = null;
		for (String p : producerList) {
			String[] arr = p.split("#");
			if(Integer.parseInt(arr[0])==id)producer = p;
		}
		return producer;
	}
	
	private IProducer getProducerInstance() throws RemoteException, NotBoundException{
		Registry registry = LocateRegistry.getRegistry(Constant.RMI_HOST, Constant.RMI_PORT);
		IProducer producer = (IProducer) registry.lookup(Constant.RMI_PRODUCER_ID);
		return producer;
	}
	
	private static BufferedImage resizeImage(BufferedImage originalImage, int type, int IMG_WIDTH, int IMG_HEIGHT) {
	    BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
	    Graphics2D g = resizedImage.createGraphics();
	    g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
	    g.dispose();

	    return resizedImage;
	}

}

