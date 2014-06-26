package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import modal.Conexao;
import modal.Opcoes;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.JToggleButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class JFPainel extends JFrame {

	private JPanel contentPane;
	
	Opcoes objOpc = new Opcoes();
	
	String usrNome = "", usrSobrenome = "", usrGenero = "";
	int usrNivel = 0;
	
	Connection con;


	/**
	 * Create the frame.
	 */
	public JFPainel(int usrID, Connection conn) {
		this.con = conn;
		//select do log
		try {
			PreparedStatement stmt = null;
	        String sql;
	        sql = "SELECT * FROM users where usr_id = ?";
	        stmt = conn.prepareStatement(sql);
	        stmt.setInt(1, usrID);
	        ResultSet rs = stmt.executeQuery();
	        if(rs.next()){
		        usrNome = rs.getString("usr_nome");
		        usrSobrenome = rs.getString("usr_sobrenome");
		        usrNivel = rs.getInt("usr_level");
		        usrGenero = rs.getString("usr_genero");
	        }
		} catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "Erro: "+e);
			new JFLogin(conn).setVisible(true);
			this.dispose();
		}
		
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 550);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(102, 204, 204));
		panel.setBounds(0, 0, 400, 200);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JPanel photo = new JPanel();
		photo.setBounds(10, 11, 178, 178);
		panel.add(photo);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(216, 11, 119, 14);
		panel.add(lblNome);
		
		JLabel tlNome = new JLabel(usrNome);
		tlNome.setFont(new Font("Tahoma", Font.BOLD, 14));
		tlNome.setBounds(198, 29, 192, 30);
		panel.add(tlNome);
		
		JLabel sobrenome = new JLabel(usrSobrenome);
		sobrenome.setFont(new Font("Tahoma", Font.BOLD, 14));
		sobrenome.setBounds(198, 88, 192, 30);
		panel.add(sobrenome);
		
		JLabel Sobrenome = new JLabel("Sobrenome:");
		Sobrenome.setBounds(216, 70, 119, 14);
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
				new JFLogin(con).setVisible(true);
				dispose();
			}
		});
		btnSair.setBounds(198, 166, 89, 23);
		panel.add(btnSair);
		
		JButton btnFechar = new JButton("Fechar");
		btnFechar.setBounds(297, 166, 89, 23);
		panel.add(btnFechar);
		
		JLabel lblTempoParaAtualizao = new JLabel("Tempo para atualiza\u00E7\u00E3o:");
		lblTempoParaAtualizao.setBounds(32, 211, 148, 14);
		contentPane.add(lblTempoParaAtualizao);
		
		JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(1, 1, 60, 1));
		spinner.setBounds(10, 236, 60, 20);
		contentPane.add(spinner);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Minutos", "Horas"}));
		comboBox.setSelectedIndex(1);
		comboBox.setBounds(80, 236, 76, 20);
		contentPane.add(comboBox);
		
		JLabel lblOpesDeApresentao = new JLabel("Op\u00E7\u00F5es de Apresenta\u00E7\u00E3o:");
		lblOpesDeApresentao.setBounds(32, 267, 148, 14);
		contentPane.add(lblOpesDeApresentao);
		
		JToggleButton tglbtnMinhasPerguntas = new JToggleButton("Novas Perguntas");
		tglbtnMinhasPerguntas.setBounds(10, 292, 170, 40);
		contentPane.add(tglbtnMinhasPerguntas);
		
		JToggleButton tglbtnMinhasRespostas = new JToggleButton("Novas respostas");
		tglbtnMinhasRespostas.setBounds(10, 343, 170, 40);
		contentPane.add(tglbtnMinhasRespostas);
		
		JToggleButton tglbtnMeusSeguidores = new JToggleButton("Meus Seguidores");
		tglbtnMeusSeguidores.setBounds(10, 394, 170, 40);
		contentPane.add(tglbtnMeusSeguidores);
		
		JLabel lblOpesDeDefinio = new JLabel("Op\u00E7\u00F5es de Defini\u00E7\u00E3o:");
		lblOpesDeDefinio.setBounds(210, 211, 170, 14);
		contentPane.add(lblOpesDeDefinio);
		
		JToggleButton tglbtnAbrirJuntoDo = new JToggleButton("Abrir Junto do Windows");
		tglbtnAbrirJuntoDo.setBounds(188, 236, 192, 40);
		contentPane.add(tglbtnAbrirJuntoDo);
		
		JToggleButton tglbtnLogarAutomaticamente = new JToggleButton("Logar Automaticamente");
		tglbtnLogarAutomaticamente.setBounds(188, 287, 192, 40);
		contentPane.add(tglbtnLogarAutomaticamente);
		
		JButton btnSalvarConfiguraes = new JButton("Salvar Configura\u00E7\u00F5es");
		btnSalvarConfiguraes.setBounds(10, 485, 180, 54);
		contentPane.add(btnSalvarConfiguraes);
		
		JButton btnRedefinirPadro = new JButton("Redefinir Padr\u00E3o");
		btnRedefinirPadro.setBounds(200, 485, 180, 54);
		contentPane.add(btnRedefinirPadro);
	}
}
