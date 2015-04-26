package course.server;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.MaskFormatter;

import course.interf.Constant;
import course.model.Licence;
import course.model.Producer;
import course.model.Record;
import course.model.Ref;
import course.model.Software;

public class ServerUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final JPanel panel = new JPanel(new GridLayout(1, 3, 10, 10));
	final JPanel panelBorder = new JPanel(new FlowLayout(
			FlowLayout.CENTER, 10, 10));
	private final JLabel label = new JLabel("Port:");
	private final JButton bStart = new JButton("Start server");
	private JFormattedTextField port;
	private boolean isStarted = false;
	
	public ServerUI(){
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		setTitle("Server");
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
		
		panel.add(label);
		panel.add(port);
		panel.add(bStart);
		
		getContentPane().add(panelBorder);
		
		bStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!port.getText().equals("****")){
					if(!isStarted){
						Constant.RMI_PORT = Integer.parseInt(port.getText());
						port.setEditable(false);
						bStart.setText("Stop server");
						isStarted = true;
						try {
							new Server();
						} catch (RemoteException e1) {
							e1.printStackTrace();
						} catch (AlreadyBoundException e1) {
							e1.printStackTrace();
						}
						JOptionPane.showMessageDialog(ServerUI.this, "The server successfully launched!", "Success", JOptionPane.DEFAULT_OPTION );
					}else{
						System.exit(0);
					}
				}else JOptionPane.showMessageDialog(ServerUI.this, "Enter the port number please!", "Error", JOptionPane.DEFAULT_OPTION );
			}
		});
		
	}
	
}
