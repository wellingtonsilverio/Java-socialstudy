package modal;

import java.sql.*;

import javax.swing.JOptionPane;

public class Conexao {
	
	public Connection conexao;
	
	public Conexao(){
		 String driver = "org.gjt.mm.mysql.Driver";
		 
		 String url = "jdbc:mysql://localhost/rss?useUnicode=true&characterEncoding=UTF-8";
		 String usuario = "root";
		 String senha = "";
		 
		 try {
			Class.forName(driver);
			this.conexao = DriverManager.getConnection(url,usuario,senha);
	         
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Você não está conectado a internet.","Erro na Conexão",JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		 
	}
	
	public ResultSet select(String sql){
		try {
			PreparedStatement stmt = null;
	        stmt = conexao.prepareStatement(sql);
	        return stmt.executeQuery();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro 01.");
			return null;
		}
	}
	
	public ResultSet select(String sql, int i){
		try {
			PreparedStatement stmt = null;
	        stmt = conexao.prepareStatement(sql);
	        stmt.setInt(1, i);
	        return stmt.executeQuery();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro 02.");
			return null;
		}
	}
	
	public ResultSet select(String sql, String arg0, String arg1){
		try {
			PreparedStatement stmt = null;
	        stmt = conexao.prepareStatement(sql);
	        stmt.setString(1, arg0);
	        stmt.setString(2, arg1);
	        return stmt.executeQuery();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro 03.");
			return null;
		}
	}
	 
}
