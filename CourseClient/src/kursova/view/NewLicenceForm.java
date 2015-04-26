package kursova.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import kursova.interf.model.ILicence;



public class NewLicenceForm extends JDialog {
	
	private static final String TEXT_PATTERN = "([(A-Za-z|À-ß²à-ÿ³)\\s]+)";
	
	private static final String PERIOD_PATTERN = "([0-9]+)";
	
	private static final String PRICE_PATTERN = "([0-9.]+)";
	
	private static final long serialVersionUID = -7265530307974489903L;

	private ILicence licence;

	private JTextField nameText;
	private JFormattedTextField typeText;
	private JFormattedTextField periodText;
	private JFormattedTextField priceText;

	private JLabel JLabel_1 = new JLabel();
	private JLabel JLabel_2 = new JLabel();
	private JLabel JLabel_3 = new JLabel();
	private JLabel JLabel_4 = new JLabel();


	public NewLicenceForm(){
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		setTitle("Licence");
		setSize(400, 200);
		setModal(true);
		setResizable(false);		
		
		final JButton cmdSave = new JButton("Save");
		final JButton cmdCancel = new JButton("Cancel");

		nameText = new JTextField(15);
		typeText = new JFormattedTextField(new RegexFormatter(TEXT_PATTERN));
		periodText = new JFormattedTextField(new RegexFormatter(PERIOD_PATTERN));
		priceText = new JFormattedTextField(new RegexFormatter(PRICE_PATTERN));

		final JPanel fieldsPanel = new JPanel(new GridLayout(4, 2, 10, 10));
		final JPanel fieldsPanelBorder = new JPanel(new FlowLayout(
				FlowLayout.CENTER, 10, 10));
		fieldsPanel.setOpaque(false);
		fieldsPanelBorder.setOpaque(false);
		fieldsPanelBorder.add(fieldsPanel);
		JLabel_1.setText("Name");
		JLabel_2.setText("Type");
		JLabel_3.setText("Period");
		JLabel_4.setText("Price");
		JLabel_4.setForeground(Color.ORANGE);
		JLabel_3.setForeground(Color.ORANGE);
		JLabel_2.setForeground(Color.ORANGE);
		JLabel_1.setForeground(Color.ORANGE);
		fieldsPanel.add(JLabel_1);
		fieldsPanel.add(nameText);
		fieldsPanel.add(JLabel_2);
		fieldsPanel.add(typeText);
		fieldsPanel.add(JLabel_3);
		fieldsPanel.add(periodText);
		fieldsPanel.add(JLabel_4);
		fieldsPanel.add(priceText);


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
				saveLicence();
			}
		});

		cmdCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelSave();
			}
		});
	}

	public ILicence getLicence() {
		return licence;
	}

	public void setLicence(ILicence licence) throws RemoteException {
		this.licence = licence;
		nameText.setText(licence.getName());
		typeText.setText(licence.getType());
		periodText.setText(Integer.toString(licence.getPeriod()));
		priceText.setText(Double.toString(licence.getPrice()));
	}

	private void saveLicence() {
		if(nameText.getText().equals(""))
			JOptionPane.showMessageDialog(NewLicenceForm.this, "The field 'name' can't be blank!", "Error", JOptionPane.DEFAULT_OPTION );
		else if(typeText.getText().equals(""))
			JOptionPane.showMessageDialog(NewLicenceForm.this, "The type is entered incorrectly!", "Error", JOptionPane.DEFAULT_OPTION );
		else if(periodText.getText().equals(""))
			JOptionPane.showMessageDialog(NewLicenceForm.this, "The period is entered incorrectly!", "Error", JOptionPane.DEFAULT_OPTION );
		else if(priceText.getText().equals(""))
			JOptionPane.showMessageDialog(NewLicenceForm.this, "The price is entered incorrectly!", "Error", JOptionPane.DEFAULT_OPTION );
		else{
			try {
				licence.setName(nameText.getText());
				licence.setType(typeText.getText());
				licence.setPeriod(Integer.parseInt(periodText.getText()));
				licence.setPrice(Double.parseDouble(priceText.getText()));
	
	
				if (licence.getLicenceId() == null) {
					licence.save();
				} else {
					licence.update();
				}
				this.setVisible(false);
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(this,
						"Error saving license: " + e.getMessage());
			}
		}
	}

	private void cancelSave() {
		this.setVisible(false);
	}

}
