package course.server.main;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

import course.server.ServerUI;

public class MainServer {

	public static void main(String[] args) {
		
		try {
			//windows style
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			JFrame.setDefaultLookAndFeelDecorated(true);
			JDialog.setDefaultLookAndFeelDecorated(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ServerUI s = new ServerUI();
		s.setVisible(true);
		s.pack();
		s.setLocationRelativeTo(null);
		s.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
