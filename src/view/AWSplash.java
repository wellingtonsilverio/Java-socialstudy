package view;

import java.awt.EventQueue;
import java.awt.Toolkit;
import java.sql.SQLException;

import javax.swing.JFrame;

public class AWSplash {

	public JFrame frame;

	/**
	 * Create the application.
	 * @throws SQLException 
	 */
	public AWSplash() throws SQLException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws SQLException 
	 */
	private void initialize() throws SQLException {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagens/logo.png"));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//if(verificarMySQL()) new JFLogin().setVisible(true); else System.exit(0);
		
		this.frame.dispose();
		
	}
	private boolean verificarMySQL(){
		try {
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
