package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Window.Type;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JToggleButton;

import modal.Conexao;

public class JFLogin extends JFrame {

	private JPanel contentPane;
	private JTextField tfEmail;
	private JPasswordField tfSenha;
	private Connection conn;

	/**
	 * Create the frame.
	 */
	public JFLogin(Connection con){
		this.conn = con;
		setUndecorated(true);
		setTitle("Login SocialStudy");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnX = new JButton("X");
		btnX.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnX.setBounds(400, 0, 50, 50);
		contentPane.add(btnX);
		
		JLabel lblNewLabel = new JLabel("E-mail:");
		lblNewLabel.setBounds(79, 69, 46, 14);
		contentPane.add(lblNewLabel);
		
		tfEmail = new JTextField();
		tfEmail.setBounds(64, 90, 307, 20);
		contentPane.add(tfEmail);
		tfEmail.setColumns(10);
		
		JLabel lblSenha = new JLabel("senha:");
		lblSenha.setBounds(79, 121, 46, 14);
		contentPane.add(lblSenha);
		
		tfSenha = new JPasswordField();
		tfSenha.setBounds(64, 143, 307, 20);
		contentPane.add(tfSenha);
		
		JButton btnLogar = new JButton("Logar");
		btnLogar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(tfEmail.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Digite seu e-mail.");
					tfEmail.requestFocus();
				}else if(new String(tfSenha.getPassword()).equals("")){
					JOptionPane.showMessageDialog(null, "Digite sua senha.");
					tfSenha.requestFocus();
				}else{
					try {
						PreparedStatement stmt = null;
				        String sql;
				        sql = "SELECT * FROM users where usr_email = ? AND usr_senha = ?";
				        stmt = conn.prepareStatement(sql);
				        stmt.setString(1, tfEmail.getText());
				        stmt.setString(2, new String(tfSenha.getPassword()));
				        ResultSet rs = stmt.executeQuery();
				        //STEP 5: Extract data from result set
					    while(rs.next()){
					       //Retrieve by column name
						   int usrID  = rs.getInt("usr_id");
						   new JFPainel(usrID, conn).setVisible(true);
					    }
					    rs.close();
					    stmt.close();
					    conn.close();
					    dispose();
					} catch (Exception e) {
						// TODO: handle exception
						JOptionPane.showMessageDialog(null, "Erro: "+e);
					}
			        
				}
			}
		});
		btnLogar.setBounds(105, 203, 214, 50);
		contentPane.add(btnLogar);
		
		JToggleButton tglbtnLembrarEmail = new JToggleButton("Lembrar E-mail");
		tglbtnLembrarEmail.setSelected(true);
		tglbtnLembrarEmail.setBounds(61, 169, 300, 23);
		contentPane.add(tglbtnLembrarEmail);
	}
}
