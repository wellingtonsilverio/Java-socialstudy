package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import modal.Atualizacao;
import modal.Conexao;
import modal.Opcoes;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.Font;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.JToggleButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JFPainel extends JFrame {

	private JPanel contentPane;
	
	Opcoes objOpc = new Opcoes();
	
	String usrNome = "", usrSobrenome = "", usrGenero = "", usrImage = "";
	private String usrSenha = "", usrEmail = "";
	int usrNivel = 0;
	private JComboBox comboBox;
	private JSpinner spinner;
	private JToggleButton tglbtnMinhasRespostas, tglbtnMeusSeguidores, tglbtnAbrirJuntoDo, tglbtnMinhasPerguntas, tglbtnLogarAutomaticamente, btnAbrirMinimizado, tglbtnFazerBarunhoEm;
	private JFrame form = this; 
	
	public Atualizacao att;
	
	private URL urlImage;
	
	Conexao con;
	
	//Imagem no Painel
	private BufferedImage image;
	
	protected void paintComponent(Graphics g){
		super.paintComponents(g);
		g.drawImage(image, 0, 0, null);
	}


	/**
	 * Create the frame.
	 */
	public JFPainel(int usrID, Conexao conn) {
		objOpc.setConn(conn);
		objOpc.setUsrID(usrID);
		this.con = conn;
		//select do log
		try {
			PreparedStatement stmt = null;
	        String sql;
	        sql = "SELECT * FROM users where usr_id = ?";
	        stmt = con.conexao.prepareStatement(sql);
	        stmt.setInt(1, usrID);
	        ResultSet rs = stmt.executeQuery();
	        if(rs.next()){
		        usrNome = rs.getString("usr_nome");
		        usrSobrenome = rs.getString("usr_sobrenome");
		        usrNivel = rs.getInt("usr_level");
		        usrGenero = rs.getString("usr_genero");
		        usrImage = rs.getString("usr_image");
		        usrSenha = rs.getString("usr_senha");
		        usrEmail = rs.getString("usr_email");
	        }
			//this.con.close();
		} catch (Exception e) {
			// TODO: handle exception
			//JOptionPane.showMessageDialog(null, "Erro: "+e);
			new JFLogin(con, false).setVisible(true);
			this.dispose();
			this.form.setVisible(false);
			this.form.dispose();
		}
		
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 550);
		setIconImage(Toolkit.getDefaultToolkit().getImage("Imagens/logo.png"));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(102, 204, 204));
		panel.setBounds(0, 0, 400, 200);
		contentPane.add(panel);
		panel.setLayout(null);
		
		try {
			try {
				urlImage = new URL("http://localhost/rss/pags_logon/img/"+usrImage);
			} catch (Exception e) {
				// TODO: handle exception
				urlImage = null;
			}
			image = ImageIO.read(urlImage);
			JLabel lblImagem = new JLabel(new ImageIcon(image));
			lblImagem.setBounds(10, 11, 174, 174);
			panel.add(lblImagem);
		} catch (Exception e) {
			// TODO: handle exception
			//JOptionPane.showMessageDialog(null, "Erro: "+e);
		}
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(216, 11, 119, 14);
		panel.add(lblNome);
		
		JLabel tlNome = new JLabel(usrNome);
		tlNome.setFont(new Font("Tahoma", Font.BOLD, 14));
		tlNome.setBounds(198, 29, 192, 30);
		panel.add(tlNome);
		
		JLabel sobrenome = new JLabel(usrSobrenome);
		sobrenome.setFont(new Font("Tahoma", Font.BOLD, 14));
		sobrenome.setBounds(194, 88, 192, 30);
		panel.add(sobrenome);
		
		JLabel Sobrenome = new JLabel("Sobrenome:");
		Sobrenome.setBounds(212, 70, 119, 14);
		panel.add(Sobrenome);
		
		JLabel Nivel = new JLabel("Nivel: "+usrNivel);
		Nivel.setBounds(198, 129, 64, 14);
		panel.add(Nivel);
		
		JLabel genero = new JLabel(usrGenero);
		genero.setBounds(271, 129, 119, 14);
		panel.add(genero);
		
		JButton btnSair = new JButton("Sair");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int dLogoof = JOptionPane.showConfirmDialog(null, "Deseja fazer logoff?");
				if(dLogoof == JOptionPane.YES_OPTION){
					new JFLogin(con, false).setVisible(true);
					att.fechar();
					dispose();
				}else if(dLogoof == JOptionPane.NO_OPTION){
					System.exit(0);
				}
			}
		});
		btnSair.setBounds(198, 166, 89, 23);
		panel.add(btnSair);
		
		JButton btnFechar = new JButton("Fechar");
		btnFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				objOpc.fechar(form);
			}
		});
		btnFechar.setBounds(297, 166, 89, 23);
		panel.add(btnFechar);
		
		JLabel lblTempoParaAtualizao = new JLabel("Tempo para atualiza\u00E7\u00E3o:");
		lblTempoParaAtualizao.setBounds(32, 211, 148, 14);
		contentPane.add(lblTempoParaAtualizao);
		
		spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(objOpc.getIntTempoVeri(), 1, 999999, 1));
		spinner.setBounds(10, 236, 60, 20);
		contentPane.add(spinner);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Minutos", "Horas"}));
		comboBox.setSelectedIndex(0);
		comboBox.setBounds(80, 236, 76, 20);
		contentPane.add(comboBox);
		
		JLabel lblOpesDeApresentao = new JLabel("Op\u00E7\u00F5es de Apresenta\u00E7\u00E3o:");
		lblOpesDeApresentao.setBounds(32, 267, 148, 14);
		contentPane.add(lblOpesDeApresentao);
		
		tglbtnMinhasPerguntas = new JToggleButton("Novas Perguntas");
		tglbtnMinhasPerguntas.setSelected(objOpc.isBlnNovaPerg());
		tglbtnMinhasPerguntas.setBounds(10, 292, 170, 40);
		contentPane.add(tglbtnMinhasPerguntas);
		
		tglbtnMinhasRespostas = new JToggleButton("Novas respostas");
		tglbtnMinhasRespostas.setSelected(objOpc.isBlnNovaResp());
		tglbtnMinhasRespostas.setBounds(10, 343, 170, 40);
		contentPane.add(tglbtnMinhasRespostas);
		
		tglbtnMeusSeguidores = new JToggleButton("Meus Seguidores");
		tglbtnMeusSeguidores.setSelected(objOpc.isBlnSeguidor());
		tglbtnMeusSeguidores.setBounds(10, 394, 170, 40);
		contentPane.add(tglbtnMeusSeguidores);
		
		JLabel lblOpesDeDefinio = new JLabel("Op\u00E7\u00F5es de Defini\u00E7\u00E3o:");
		lblOpesDeDefinio.setBounds(210, 211, 170, 14);
		contentPane.add(lblOpesDeDefinio);
		
		tglbtnAbrirJuntoDo = new JToggleButton("Abrir Junto do Windows");
		tglbtnAbrirJuntoDo.setSelected(objOpc.isBlnAbrirWin());
		tglbtnAbrirJuntoDo.setBounds(188, 236, 192, 40);
		contentPane.add(tglbtnAbrirJuntoDo);
		
		tglbtnLogarAutomaticamente = new JToggleButton("Logar Automaticamente");
		tglbtnLogarAutomaticamente.setSelected(objOpc.isBlnLogAuto());
		tglbtnLogarAutomaticamente.setBounds(188, 287, 192, 40);
		contentPane.add(tglbtnLogarAutomaticamente);
		
		btnAbrirMinimizado = new JToggleButton("Abrir Minimizado");
		btnAbrirMinimizado.setSelected(objOpc.isBlnAbrirTray());
		btnAbrirMinimizado.setBounds(190, 338, 190, 40);
		contentPane.add(btnAbrirMinimizado);
		
		tglbtnFazerBarunhoEm = new JToggleButton("Fazer Barunho em novas Notifica\u00E7\u00F5es");
		tglbtnFazerBarunhoEm.setSelected(objOpc.isBlnFazerBarulho());
		tglbtnFazerBarunhoEm.setBounds(190, 389, 190, 40);
		contentPane.add(tglbtnFazerBarunhoEm);
		
		JButton btnSalvarConfiguraes = new JButton("Salvar Configura\u00E7\u00F5es");
		btnSalvarConfiguraes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				objOpc.salvarAlteracies(this, arg0, comboBox, spinner, tglbtnMinhasRespostas, tglbtnMeusSeguidores, tglbtnAbrirJuntoDo, tglbtnMinhasPerguntas, tglbtnLogarAutomaticamente, btnAbrirMinimizado, tglbtnFazerBarunhoEm);
			}
		});
		btnSalvarConfiguraes.setBounds(10, 485, 180, 54);
		contentPane.add(btnSalvarConfiguraes);
		
		JButton btnRedefinirPadro = new JButton("Redefinir Padr\u00E3o");
		btnRedefinirPadro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				objOpc.resetarAlteracoes(this, arg0);
			}
		});
		btnRedefinirPadro.setBounds(200, 485, 180, 54);
		contentPane.add(btnRedefinirPadro);
		
		if(tglbtnLogarAutomaticamente.isSelected()){
			salvarArquivoES();
		}
		if(objOpc.isBlnAbrirTray()) objOpc.fechar(form); else setVisible(true);
		
		att = new Atualizacao(objOpc);
		
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

	public void salvarArquivoES(){
		try {
	   		FileWriter arq = new FileWriter("conf/.alces.ss");
			
	   		usrSenha = makeSHA1Hash(usrSenha);
	   		
	   		arq.write(usrEmail+"\r\n"+usrSenha);
			   
			arq.close();
		} catch (Exception e) {
			// TODO: handle exception
			try {
				File diretorio = new File("conf");
				diretorio.mkdir();
				File arqF = new File(diretorio, ".alces.ss");
				arqF.createNewFile();
				
				salvarArquivoES();
				
			} catch (Exception e2) {
				// TODO: handle exception
			}
			
		}
	}
}
