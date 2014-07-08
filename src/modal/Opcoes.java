package modal;

import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.util.Scanner;

import javafx.stage.Popup;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JToggleButton;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.UIManager;

public class Opcoes {
	private int intTempoVeri, usrID;
	private boolean blnNovaResp, blnNovaPerg, blnSeguidor, blnAbrirWin, blnLogAuto, blnAbrirTray, blnFazerBarulho;
	private JFrame jForm;
	private Conexao conn;
	
	private SystemTray tray;
	private TrayIcon icoTray;
	
	
	public Conexao getConn() {
		return conn;
	}

	public void setConn(Conexao conn) {
		this.conn = conn;
	}

	public int getUsrID() {
		return usrID;
	}

	public void setUsrID(int usrID) {
		this.usrID = usrID;
	}

	public int getIntTempoVeri() {
		return intTempoVeri;
	}

	public void setIntTempoVeri(int intTempoVeri) {
		this.intTempoVeri = intTempoVeri;
	}

	public boolean isBlnAbrirTray() {
		return blnAbrirTray;
	}

	public void setBlnAbrirTray(boolean blnAbrirTray) {
		this.blnAbrirTray = blnAbrirTray;
	}

	public boolean isBlnNovaResp() {
		return blnNovaResp;
	}

	public void setBlnNovaResp(boolean blnNovaResp) {
		this.blnNovaResp = blnNovaResp;
	}

	public boolean isBlnNovaPerg() {
		return blnNovaPerg;
	}

	public void setBlnNovaPerg(boolean blnNovaPerg) {
		this.blnNovaPerg = blnNovaPerg;
	}

	public boolean isBlnSeguidor() {
		return blnSeguidor;
	}

	public void setBlnSeguidor(boolean blnSeguidor) {
		this.blnSeguidor = blnSeguidor;
	}

	public boolean isBlnAbrirWin() {
		return blnAbrirWin;
	}

	public void setBlnAbrirWin(boolean blnAbrirWin) {
		this.blnAbrirWin = blnAbrirWin;
	}

	public boolean isBlnLogAuto() {
		return blnLogAuto;
	}

	public void setBlnLogAuto(boolean blnLogAuto) {
		this.blnLogAuto = blnLogAuto;
	}
	
	public boolean isBlnFazerBarulho(){
		return blnFazerBarulho;
	}
	
	public void setBlnFazerBarulho(boolean blnBlnFazerBarulho){
		this.blnFazerBarulho = blnBlnFazerBarulho;
	}

	public Opcoes(){
		Gerar();
	}
	public void Gerar(){
		try {
			FileReader arq = new FileReader("conf/.info.ss");
			BufferedReader lerArq = new BufferedReader(arq);
			String linha;
			while((linha = lerArq.readLine()) != null){
				if(linha.charAt(0) == 'c'){
					linha = lerArq.readLine();
					intTempoVeri = Integer.parseInt(linha);
					linha = lerArq.readLine();
					if(linha.charAt(0) == 't') blnNovaResp = true; else blnNovaResp = false;
					if(linha.charAt(1) == 't') blnNovaPerg = true; else blnNovaPerg = false;
					if(linha.charAt(2) == 't') blnSeguidor = true; else blnSeguidor = false;
					if(linha.charAt(3) == 't') blnAbrirWin = true; else blnAbrirWin = false;
					if(linha.charAt(4) == 't') blnLogAuto = true; else blnLogAuto = false;
					if(linha.charAt(5) == 't') blnAbrirTray = true; else blnAbrirTray = false;
					if(linha.charAt(6) == 't') blnFazerBarulho = true; else blnFazerBarulho = false;
				}
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			intTempoVeri = 1;
			blnNovaResp = true;
			blnNovaPerg = true;
			blnSeguidor = true;
			blnAbrirWin = true;
			blnLogAuto = true;
			blnAbrirTray = true;
			blnFazerBarulho = true;
		}
	}
	
	public void salvarAlteracies(ActionListener actionListener,
			ActionEvent arg0,
			JComboBox comboBox,
			JSpinner spinner,
			JToggleButton tglbtnMinhasRespostas,
			JToggleButton tglbtnMeusSeguidores,
			JToggleButton tglbtnAbrirJuntoDo,
			JToggleButton tglbtnMinhasPerguntas,
			JToggleButton tglbtnLogarAutomaticamente,
			JToggleButton btnAbrirMinimizado,
			JToggleButton tglbtnFazerBarunhoEm){
		try {
			FileReader arq = new FileReader("conf/.info.ss");
			BufferedReader lerArq = new BufferedReader(arq);
			String linha;
			while((linha = lerArq.readLine()) != null){
				String linhaEscrever = "";
				try {
					
					if(linha.charAt(0) == 'c'){
						linhaEscrever += linha+"\r\n";
						linha = lerArq.readLine();
						if(comboBox.getSelectedIndex() == 0) linhaEscrever += String.valueOf(spinner.getValue())+"\r\n";
						else if(comboBox.getSelectedIndex() == 1) linhaEscrever += String.valueOf(Integer.parseInt((String) spinner.getValue())*60)+"\r\n";
						linha = lerArq.readLine();
						String novaLinha = "";
						if(tglbtnMinhasRespostas.isSelected()) novaLinha += "t"; else novaLinha += "f";
						if(tglbtnMinhasPerguntas.isSelected()) novaLinha += "t"; else novaLinha += "f";
						if(tglbtnMeusSeguidores.isSelected()) novaLinha += "t"; else novaLinha += "f";
						if(tglbtnAbrirJuntoDo.isSelected()) novaLinha += "t"; else novaLinha += "f";
						if(tglbtnLogarAutomaticamente.isSelected()) novaLinha += "t"; else novaLinha += "f";
						if(btnAbrirMinimizado.isSelected()) novaLinha += "t"; else novaLinha += "f";
						if(tglbtnFazerBarunhoEm.isSelected()) novaLinha += "t"; else novaLinha += "f";
						linhaEscrever += novaLinha+"\r\n";
					}else{
						linhaEscrever += linha+"\r\n";
					}
					FileWriter arqW = new FileWriter("conf/.info.ss");
					arqW.write(linhaEscrever);
					arqW.close();
					Gerar();
				} catch (Exception e) {
					// TODO: handle exception
					try {
						File diretorio = new File("conf");
						diretorio.mkdir();
						File arqF = new File(diretorio, ".info.ss");
						arqF.createNewFile();
						
						salvarAlteracies(actionListener, arg0, comboBox, spinner, tglbtnMinhasRespostas, tglbtnMeusSeguidores, tglbtnAbrirJuntoDo, tglbtnMinhasPerguntas, tglbtnLogarAutomaticamente, btnAbrirMinimizado, tglbtnFazerBarunhoEm);
						
					} catch (Exception e2) {
						// TODO: handle exception
					}
				}
			}
			
			
		} catch (Exception e) {
			try {
				resetarAlteracoes(actionListener, arg0);
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "Erro 04.");
			}
			salvarAlteracies(actionListener, arg0, comboBox, spinner, tglbtnMinhasRespostas, tglbtnMeusSeguidores, tglbtnAbrirJuntoDo, tglbtnMinhasPerguntas, tglbtnLogarAutomaticamente, btnAbrirMinimizado, tglbtnFazerBarunhoEm);
			
		}
	}
	public void resetarAlteracoes(ActionListener actionListener, ActionEvent arg0){
		try {
			FileWriter arqW;
			arqW = new FileWriter("conf/.info.ss");
	   		arqW.write("config"+"\r\n");
	   		arqW.write("120"+"\r\n");
	   		arqW.write("tttttttt"+"\r\n");
	   		arqW.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			try {
				File diretorio = new File("conf");
				diretorio.mkdir();
				File arqF = new File(diretorio, ".info.ss");
				arqF.createNewFile();
				if(actionListener != null) actionListener.actionPerformed(arg0);
				
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}
	public void fechar(JFrame form){
		this.jForm = form;
		if(tray.isSupported()){
			tray = SystemTray.getSystemTray();
			
			Image img = Toolkit.getDefaultToolkit().getImage("Imagens/logo.png");
			
			PopupMenu popupMenu = new PopupMenu();
			MenuItem defaultItem = new MenuItem("Sair do SocialStudy");
			defaultItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					System.exit(0);
				}
			});
			popupMenu.add(defaultItem);
			defaultItem = new MenuItem("Abrir Opções");
			defaultItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					tray.remove(icoTray);
					jForm.setVisible(true);
				}
			});
			popupMenu.add(defaultItem);
			
			icoTray = new TrayIcon(img, "SocialStudy", popupMenu);
			icoTray.setImageAutoSize(true);
			icoTray.addMouseListener(new MouseListener() {

				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					if(e.getClickCount() >= 2 && e.getButton() == MouseEvent.BUTTON1){
						tray.remove(icoTray);
						jForm.setVisible(true);
					}
				}

				public void mouseEntered(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				public void mouseExited(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					
					
				}

				public void mouseReleased(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
			
			try {
				tray.add(icoTray);
				jForm.setVisible(false);
			} catch (Exception e) {
			}
			
		}
	}



}
