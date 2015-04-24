package course.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;

import course.interf.Constant;
import course.interf.model.IInfo;

public class InfoForm extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InfoForm() {

		getContentPane().setLayout(new BorderLayout());
		setTitle("Info");
		setLocationRelativeTo(null);
		setBounds(100, 100, 1000, 400);
		
		JButton bClose = new JButton("Close");

		JTabbedPane tabs = new JTabbedPane();

		JTextPane helpPane = new JTextPane();
		JTextPane aboutPane = new JTextPane();
		helpPane.setEditable(false);
		aboutPane.setEditable(false);

		try {
			helpPane.setText(getInfo().getHelpInfo());
			aboutPane.setText(getInfo().getAboutInfo());
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		tabs.addTab("Help", new JScrollPane(helpPane));
		tabs.addTab("About", new JScrollPane(aboutPane));

		add(tabs, BorderLayout.CENTER);
		add(bClose, BorderLayout.SOUTH);
		
		bClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancel();
			}
		});
	}

	private IInfo getInfo() {
		Registry registry;
		IInfo info = null;
		try {
			registry = LocateRegistry.getRegistry(Constant.RMI_HOST, Constant.RMI_PORT);

			info = (IInfo) registry.lookup(Constant.RMI_INFO_ID);

		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		return info;
	}
	
	private void cancel() {
		this.setVisible(false);
	}
	
	public static void main(String[] args) {
		InfoForm f = new InfoForm();
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
