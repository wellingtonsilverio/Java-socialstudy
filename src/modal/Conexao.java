package modal;

import java.sql.*;

import javax.swing.JOptionPane;

public class Conexao {
	
	public Connection conexao;
	public Statement statement = null;
	public ResultSet resultSet = null;
	
	public void Conexao(){
		 String driver = "org.gjt.mm.mysql.Driver";
		 
		 String url = "jdbc:mysql://localhost/rss";
		 String usuario = "root";
		 String senha = "";
		 
		 try {
			Class.forName(driver);
			conexao = DriverManager.getConnection(url,usuario,senha);
		} catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "Erro: "+e);
		}
		 
	}

	
	 
}
