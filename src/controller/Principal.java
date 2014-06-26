package controller;

import java.awt.EventQueue;
import java.sql.Connection;

import modal.Conexao;
import view.AWSplash;
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
					JFLogin window = new JFLogin(conn);
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
