package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Timer;
import java.util.TimerTask;

public class JDJanela extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private Timer tmrSumirCaixa;
	private float opacityForm = getOpacity();

	/**
	 * Create the dialog.
	 */
	public JDJanela() {
		setUndecorated(true);
		setBounds(100, 100, 400, 200);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(null);
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JPanel panelImage = new JPanel();
		panelImage.setBounds(10, 10, 100, 100);
		contentPanel.add(panelImage);
		
		JLabel lblNome = new JLabel("");
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNome.setBounds(120, 10, 270, 25);
		contentPanel.add(lblNome);
		
		JLabel lblTexto = new JLabel("");
		lblTexto.setBounds(120, 46, 270, 143);
		contentPanel.add(lblTexto);
		
		JButton btnOlhar = new JButton("Olhar");
		btnOlhar.setBounds(10, 121, 100, 30);
		contentPanel.add(btnOlhar);
		
		JButton btnFechar = new JButton("Fechar");
		btnFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnFechar.setBounds(10, 157, 100, 30);
		contentPanel.add(btnFechar);
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
		Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
		int x = ((int) rect.getMaxX() - getWidth())-10;
		int y = 10;
		setLocation(x, y);
		setVisible(true);
		
		tmrSumirCaixa = new Timer();
		tmrSumirCaixa.scheduleAtFixedRate(new TimerTask() {
			
			public void run() {
				if(opacityForm <= 0.1F){
					dispose();
					tmrSumirCaixa.cancel();
				}else{
					opacityForm -= 0.1F;
					setOpacity(opacityForm);
				}
			}
		}, 10*1000, 70);
		
	}

}
