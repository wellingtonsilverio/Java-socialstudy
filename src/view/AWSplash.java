package view;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class AWSplash {

	public JFrame frame;

	/**
	 * Create the application.
	 */
	public AWSplash() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		if(verificarMySQL()) new JFLogin().setVisible(true); else System.exit(0);
	}
	private boolean verificarMySQL(){
		try {
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
