package course.client;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

import course.view.*;

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
		
		RecordsForm recordForm = new RecordsForm(); 

		recordForm.setVisible(true);
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		recordForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
