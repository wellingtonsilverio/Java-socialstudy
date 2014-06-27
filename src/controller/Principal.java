package controller;

import java.awt.EventQueue;
import java.sql.Connection;

import modal.Conexao;
import modal.Opcoes;
import view.AWSplash;
import view.JDJanela;
import view.JFLogin;

public class Principal {
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Conexao objCon = new Conexao();
					Connection conn = objCon.conexao;
					Opcoes objOpc = new Opcoes();
					
					if(objOpc.isBlnLogAuto()){
						JFLogin window = new JFLogin(conn, true);
						window.setVisible(false);
					}else{
						JFLogin window = new JFLogin(conn, false);
						window.setVisible(true);
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
