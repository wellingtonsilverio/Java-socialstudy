package view;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JToggleButton;

import modal.Conexao;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class JFLogin extends JFrame {

	private JPanel contentPane;
	private JTextField tfEmail;
	private JPasswordField tfSenha;
	private Conexao conn;
	private String emailSalvo = null;
	private JToggleButton tglbtnLembrarEmail;
	private int intTentativas = 0;
	private String usrSenha = "", usrEmail = "";

	/**
	 * Create the frame.
	 */
	public JFLogin(Conexao objCon, boolean autoLog){
		
		this.conn = objCon;	
		
		
		if(autoLog){
			//Verificar se loga automaticamente.
			try {
				this.setVisible(false);
				FileReader arq = new FileReader("conf/.alces.ss");
				BufferedReader lerArq = new BufferedReader(arq);
				String linha;
				if((linha = lerArq.readLine()) != null){
					usrEmail = linha;
					linha = lerArq.readLine();
					usrSenha = linha;
					
					if(verificarLogin(usrEmail, usrSenha) == true) this.dispose();
					
				}
			} catch (Exception e) {
				// TODO: handle exception
				new JFLogin(conn, false).setVisible(true);
				this.dispose();
			}
		}
		
		
		setUndecorated(true);
		setTitle("Login SocialStudy");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setIconImage(Toolkit.getDefaultToolkit().getImage("Imagens/logo.png"));
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
		
		tfEmail = new JTextField("");
		tfEmail.setBounds(64, 90, 307, 20);
		contentPane.add(tfEmail);
		tfEmail.setColumns(10);
		
		JLabel lblSenha = new JLabel("senha:");
		lblSenha.setBounds(79, 121, 46, 14);
		contentPane.add(lblSenha);
		
		tfSenha = new JPasswordField();
		tfSenha.setBounds(64, 143, 307, 20);
		contentPane.add(tfSenha);
		
		tglbtnLembrarEmail = new JToggleButton("Lembrar E-mail");
		tglbtnLembrarEmail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!(tglbtnLembrarEmail.isSelected())){
					tfEmail.setText(null);
					salvarArquivo();
				}
			}
		});
		tglbtnLembrarEmail.setBounds(61, 169, 300, 23);
		contentPane.add(tglbtnLembrarEmail);
		
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
				        stmt = conn.conexao.prepareStatement(sql);
				        stmt.setString(1, tfEmail.getText());
				        stmt.setString(2, new String(tfSenha.getPassword()));
				        ResultSet rs = stmt.executeQuery();
				        //STEP 5: Extract data from result set
					    if(rs.next()){
					       //Retrieve by column name
						   int usrID  = rs.getInt("usr_id");
						   new JFPainel(usrID, conn).setVisible(true);
						   if(tglbtnLembrarEmail.isSelected()){
							   salvarArquivo();
						   }
						   rs.close();
						   stmt.close();
						   //conn.close();
						   dispose();
					    }else{
					    	JOptionPane.showMessageDialog(null, "Login ou Senha está incorreto.");
							rs.close();
							stmt.close();
							intTentativas++;
							if(intTentativas > 3){
								new JFLogin(conn, false).setVisible(true);
								dispose();
							}
					    }
					} catch (Exception e) {
						// TODO: handle exception
						JOptionPane.showMessageDialog(null, "Erro: "+e);
					}
			        
				}
			}
		});
		btnLogar.setBounds(105, 203, 214, 50);
		contentPane.add(btnLogar);
		
		//Ler se existe um e-mail salvo
		try {			
			FileReader arq = new FileReader("conf/.email.ss");
			BufferedReader lerArq = new BufferedReader(arq);
			String linha;
			if((linha = lerArq.readLine()) != null){
				if(linha != null || linha != ""){
					tfEmail.setText(linha);
					tglbtnLembrarEmail.setSelected(true);
				}
				tfSenha.requestFocus();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	
	public boolean verificarLogin(String email, String senhaCodif) {
		
		try {
			PreparedStatement stmt = null;
	        String sql;
	        sql = "SELECT * FROM users where usr_email = ?";
	        stmt = conn.conexao.prepareStatement(sql);
	        stmt.setString(1, email);
	        ResultSet rs = stmt.executeQuery();
		    if(rs.next()){
		    	if(makeSHA1Hash(rs.getString("usr_senha")).equals(senhaCodif)){
			    	int usrID  = rs.getInt("usr_id");
				    new JFPainel(usrID, conn);
				    rs.close();
				    stmt.close();
				    return true;
		    	}else{
				    rs.close();
				    stmt.close();
				    deletarArquivoALCES();
				    return false;
		    	}
		    }else{
				rs.close();
				stmt.close();
			    return false;
		    }
		} catch (Exception e) {
			// TODO: handle exception
		    return false;
		}
	}
	public void salvarArquivo(){
		try {
	   		FileWriter arq = new FileWriter("conf/.email.ss");
			   
	   		arq.write(tfEmail.getText());
			   
			arq.close();
		} catch (Exception e) {
			// TODO: handle exception
			try {
				File diretorio = new File("conf");
				diretorio.mkdir();
				File arqF = new File(diretorio, ".email.ss");
				arqF.createNewFile();
				
				salvarArquivo();
				
			} catch (Exception e2) {
				// TODO: handle exception
			}
			
		}
	}
	public void deletarArquivoALCES(){
		try {
			File diretorio = new File("conf");
			diretorio.mkdir();
			File arqF = new File(diretorio, ".alces.ss");
			arqF.delete();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static String makeSHA1Hash(String input)
            throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA1");
        md.reset();
        byte[] buffer = input.getBytes();
        md.update(buffer);
        byte[] digest = md.digest();
 
        String hexStr = "";
        for (int i = 0; i < digest.length; i++) {
            hexStr += Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1);
        }
        return hexStr;
    }
}
