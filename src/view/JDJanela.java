package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.SwingConstants;

public class JDJanela extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private Timer tmrSumirCaixa;
	private float opacityForm = getOpacity();
	private URL urlImage;
	private BufferedImage image;

	
	public JDJanela(String usrImage, String usrNome, String attDesc, String attDate, String attTipo, int y) {
		setUndecorated(true);
		setBounds(100, 100, 400, 200);
		setIconImage(Toolkit.getDefaultToolkit().getImage("Imagens/logo.png"));
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(null);
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		try {
			try {
				urlImage = new URL("http://localhost/rss/pags_logon/img/"+usrImage);
			} catch (Exception e) {
				// TODO: handle exception
				urlImage = null;
			}
			image = ImageIO.read(urlImage);
			image.getScaledInstance(100, 100, Image.SCALE_DEFAULT);
			JLabel labelImage = new JLabel(new ImageIcon(image));
			labelImage.setBounds(10, 10, 100, 100);
			contentPanel.add(labelImage);
		} catch (Exception e) {
			// TODO: handle exception
			//JOptionPane.showMessageDialog(null, "Erro: "+e);
		}
		
		JLabel lblNome = new JLabel(usrNome);
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNome.setBounds(120, 10, 270, 25);
		contentPanel.add(lblNome);
		
		JLabel lblTexto = new JLabel("<html><meta http-equiv='Content-Type' content='text/html; charset=utf-8' />"+attDesc+"</html>");
		lblTexto.setVerticalAlignment(SwingConstants.TOP);
		lblTexto.setBounds(120, 46, 270, 119);
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
		JLabel lblData = new JLabel(attDate);
		lblData.setHorizontalAlignment(SwingConstants.CENTER);
		lblData.setFont(new Font("Tahoma", Font.PLAIN, 7));
		lblData.setBounds(260, 176, 130, 11);
		contentPanel.add(lblData);
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
		Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
		int x = ((int) rect.getMaxX() - getWidth())-10;
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
