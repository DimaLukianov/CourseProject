package kursova.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.MaskFormatter;

import kursova.interf.model.IProducer;


public class NewProducerForm extends JDialog {
	
	private static final String STREET_PATTERN = "([A-Za-zÀ-ß²à-ÿ³0-9\\s-./]+)";

	private static final String NAME_PATTERN = "(([A-Z]|[À-ß²])[(A-Za-z|À-ß²à-ÿ³)-_\\s]+)";

	private static final String TEXT_PATTERN = "([(A-Za-z|À-ß²à-ÿ³)\\s-_]+)";

	private static final String URL_PATTERN = "(@)?(href=')?(HREF=')?(HREF=\")?(href=\")?(http://)?[a-zA-Z_0-9\\-]+(\\.\\w[a-zA-Z_0-9\\-]+)+(/[#&\\n\\-=?\\+\\%/\\.\\w]+)?";

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	private static final long serialVersionUID = -7265530307974489903L;

	private IProducer producer;

	private JFormattedTextField nameText;
	private JFormattedTextField countryText;
	private JFormattedTextField cityText;
	private JFormattedTextField streetText;
	private JFormattedTextField emailText;
	private JFormattedTextField webSiteText;
	private JFormattedTextField  telephoneText;
	private JLabel JLabel_1 = new JLabel();
	private JLabel JLabel_2 = new JLabel();
	private JLabel JLabel_3 = new JLabel();
	private JLabel JLabel_4 = new JLabel();
	private JLabel JLabel_5 = new JLabel();
	private JLabel JLabel_6 = new JLabel();
	private JLabel JLabel_7 = new JLabel();


	public NewProducerForm() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		setTitle("New producer");
		setSize(350, 300);
		setModal(true);
		setResizable(false);

		final JButton cmdSave = new JButton("Save");
		final JButton cmdCancel = new JButton("Cancel");

		nameText = new JFormattedTextField(new RegexFormatter(TEXT_PATTERN));
		countryText = new JFormattedTextField(new RegexFormatter(NAME_PATTERN));
		cityText = new JFormattedTextField(new RegexFormatter(NAME_PATTERN));
		streetText = new JFormattedTextField(new RegexFormatter(STREET_PATTERN));//validation street
		emailText =  new JFormattedTextField(new RegexFormatter(EMAIL_PATTERN));//validation email
		webSiteText =  new JFormattedTextField(new RegexFormatter(URL_PATTERN));//validation url
		MaskFormatter mf1;
		try {
			mf1 = new MaskFormatter("+###(##)## ## ###");
			mf1.setPlaceholderCharacter('_');
			telephoneText = new JFormattedTextField(mf1);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
	    
		final JPanel fieldsPanel = new JPanel(new GridLayout(7, 2, 10, 10));
		final JPanel fieldsPanelBorder = new JPanel(new FlowLayout(
				FlowLayout.CENTER, 10, 10));
		fieldsPanel.setOpaque(false);
		fieldsPanelBorder.setOpaque(false);
		fieldsPanelBorder.add(fieldsPanel);
		JLabel_1.setText("Name");
		JLabel_2.setText("Country");
		JLabel_3.setText("City");
		JLabel_4.setText("Street");
		JLabel_5.setText("Email");
		JLabel_6.setText("Web site");
		JLabel_7.setText("Telephone");
		JLabel_7.setForeground(Color.ORANGE);
		JLabel_6.setForeground(Color.ORANGE);
		JLabel_5.setForeground(Color.ORANGE);
		JLabel_4.setForeground(Color.ORANGE);
		JLabel_3.setForeground(Color.ORANGE);
		JLabel_2.setForeground(Color.ORANGE);
		JLabel_1.setForeground(Color.ORANGE);
		fieldsPanel.add(JLabel_1);
		fieldsPanel.add(nameText);
		fieldsPanel.add(JLabel_2);
		fieldsPanel.add(countryText);
		fieldsPanel.add(JLabel_3);
		fieldsPanel.add(cityText);
		fieldsPanel.add(JLabel_4);
		fieldsPanel.add(streetText);
		fieldsPanel.add(JLabel_5);
		fieldsPanel.add(emailText);
		fieldsPanel.add(JLabel_6);
		fieldsPanel.add(webSiteText);
		fieldsPanel.add(JLabel_7);
		fieldsPanel.add(telephoneText);

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
				saveProducer();
			}
		});

		cmdCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelSave();
			}
		});
	}

	public IProducer getProducer() {
		return producer;
	}

	public void setProducer(IProducer producer) throws RemoteException {
		this.producer = producer;
		nameText.setText(producer.getName());
		countryText.setText(producer.getCountry());
		cityText.setText(producer.getCity());
		streetText.setText(producer.getStreet());
		emailText.setText(producer.getEmail());
		webSiteText.setText(producer.getWebSite());
		telephoneText.setText(producer.getTelephone());
	}

	private void saveProducer() {
		if(nameText.getText().equals(""))
			JOptionPane.showMessageDialog(NewProducerForm.this, "The name is entered incorrectly!", "Error", JOptionPane.DEFAULT_OPTION );
		else if(countryText.getText().equals(""))
			JOptionPane.showMessageDialog(NewProducerForm.this, "The country is entered incorrectly!", "Error", JOptionPane.DEFAULT_OPTION );
		else if(cityText.getText().equals(""))
			JOptionPane.showMessageDialog(NewProducerForm.this, "The city is entered incorrectly!", "Error", JOptionPane.DEFAULT_OPTION );
		else if(streetText.getText().equals(""))
			JOptionPane.showMessageDialog(NewProducerForm.this, "The street is entered incorrectly!", "Error", JOptionPane.DEFAULT_OPTION );
		else if(emailText.getText().equals(""))
			JOptionPane.showMessageDialog(NewProducerForm.this, "The email is entered incorrectly!", "Error", JOptionPane.DEFAULT_OPTION );
		else if(webSiteText.getText().equals(""))
			JOptionPane.showMessageDialog(NewProducerForm.this, "The web site is entered incorrectly!", "Error", JOptionPane.DEFAULT_OPTION );
		else if(telephoneText.getText().equals("+___(__)__ __ ___"))
			JOptionPane.showMessageDialog(NewProducerForm.this, "The telephone is entered incorrectly!", "Error", JOptionPane.DEFAULT_OPTION );
		else{
			try {
				producer.setName(nameText.getText());
				producer.setCountry(countryText.getText());
				producer.setCity(cityText.getText());
				producer.setStreet(streetText.getText());
				producer.setEmail(emailText.getText());
				producer.setWebSite(webSiteText.getText());
				producer.setTelephone(telephoneText.getText());
	//			producer.update();
	//			producer.create(nameText.getText(), countryText.getText(), cityText.getText(), 
	//					streetText.getText(), emailText.getText(), webSiteText.getText(), telephoneText.getText());
				
				
				if (producer.getProducerId() == null) {
					producer.save();
				} else {
					producer.update();
				}
				this.setVisible(false);
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(this,
						"Error saving producer: " + e.getMessage());
			}
		}
	}

	private void cancelSave() {
		this.setVisible(false);
	}

}
