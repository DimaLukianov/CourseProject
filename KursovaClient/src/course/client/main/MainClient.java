package course.client.main;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

import course.client.ClientUI;
import course.interf.Constant;

public class MainClient {
	
	public static void main(String[] args) {
		try {
			//windows style
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			JFrame.setDefaultLookAndFeelDecorated(true);
			JDialog.setDefaultLookAndFeelDecorated(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		ClientUI client = new ClientUI(); 

		client.setVisible(true);
		client.pack();
		client.setLocationRelativeTo(null);
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		System.out.println("PortClient:"+Constant.RMI_PORT);
	}

}
