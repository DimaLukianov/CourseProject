package course.client;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import course.interf.Constant;
import course.view.RecordsForm;


public class ClientUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
	final JPanel panelBorder = new JPanel(new FlowLayout(
			FlowLayout.CENTER, 10, 10));
	private final JLabel label1 = new JLabel("Host:");
	private final JLabel label2 = new JLabel("Port:");
	private final JButton bStart = new JButton("Start");
	private JTextField host = new JTextField("localhost");
	private JFormattedTextField port;
	
	
	public ClientUI(){
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		setTitle("Client");
		setSize(400, 80);
		setResizable(false);
		panel.setOpaque(false);
		panelBorder.setOpaque(false);
		panelBorder.add(panel);
		
		try {
			MaskFormatter mf = new MaskFormatter("####");
			mf.setPlaceholderCharacter('*');
			port = new JFormattedTextField(mf);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		panel.add(label1);
		panel.add(host);
		panel.add(label2);
		panel.add(port);
		panel.add(new JLabel(""));
		panel.add(bStart);
		
		getContentPane().add(panelBorder);
		
		bStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!port.getText().equals("****")){
					Constant.RMI_HOST = host.getText();
					Constant.RMI_PORT = Integer.parseInt(port.getText());
					setVisible(false);
					loadMainForm();
				}else JOptionPane.showMessageDialog(ClientUI.this, "Enter the port number please!", "Error", JOptionPane.DEFAULT_OPTION );
			}
		});
		
	}
	private void loadMainForm(){
		RecordsForm recordForm = new RecordsForm(); 

		recordForm.setVisible(true);
		recordForm.setLocationRelativeTo(null);
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		recordForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
}
