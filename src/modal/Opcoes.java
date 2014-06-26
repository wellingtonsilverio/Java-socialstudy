package modal;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JToggleButton;

public class Opcoes {
	private int intTempoVeri;
	private boolean blnNovaResp, blnNovaPerg, blnSeguidor, blnAbrirWin, blnLogAuto;
	
	public int getIntTempoVeri() {
		return intTempoVeri;
	}

	public void setIntTempoVeri(int intTempoVeri) {
		this.intTempoVeri = intTempoVeri;
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

	public Opcoes(){
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
		}
	}
	
	public void salvarAlteracies(ActionListener actionListener, ActionEvent arg0, JComboBox comboBox, JSpinner spinner, JToggleButton tglbtnMinhasRespostas, JToggleButton tglbtnMeusSeguidores, JToggleButton tglbtnAbrirJuntoDo, JToggleButton tglbtnMinhasPerguntas, JToggleButton tglbtnLogarAutomaticamente){
		try {
			FileReader arq = new FileReader("conf/.info.ss");
			BufferedReader lerArq = new BufferedReader(arq);
			String linha;
			while((linha = lerArq.readLine()) != null){
				String linhaEscrever = "";
				try {
					
					if(linha.charAt(0) == 'c'){
						linhaEscrever += linha+"\r\n";
						linha += lerArq.readLine();
						if(comboBox.getSelectedIndex() == 0) linhaEscrever += String.valueOf(spinner.getValue())+"\r\n";
						else if(comboBox.getSelectedIndex() == 1) linhaEscrever += String.valueOf(Integer.parseInt((String) spinner.getValue())*60)+"\r\n";
						linha = lerArq.readLine();
						String novaLinha = "";
						if(tglbtnMinhasRespostas.isSelected()) novaLinha += "t"; else novaLinha += "f";
						if(tglbtnMinhasPerguntas.isSelected()) novaLinha += "t"; else novaLinha += "f";
						if(tglbtnMeusSeguidores.isSelected()) novaLinha += "t"; else novaLinha += "f";
						if(tglbtnAbrirJuntoDo.isSelected()) novaLinha += "t"; else novaLinha += "f";
						if(tglbtnLogarAutomaticamente.isSelected()) novaLinha += "t"; else novaLinha += "f";
						linhaEscrever += novaLinha+"\r\n";
					}else{
						linhaEscrever += linha+"\r\n";
					}
					FileWriter arqW = new FileWriter("conf/.info.ss");
					arqW.write(linhaEscrever);
					arqW.close();
				} catch (Exception e) {
					// TODO: handle exception
					try {
						File diretorio = new File("conf");
						diretorio.mkdir();
						File arqF = new File(diretorio, ".info.ss");
						arqF.createNewFile();
						
						actionListener.actionPerformed(arg0);
						
					} catch (Exception e2) {
						// TODO: handle exception
					}
				}
			}
			
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro: "+e);
		}
	}
	public void resetarAlteracoes(ActionListener actionListener, ActionEvent arg0){
		try {
			FileWriter arqW;
			arqW = new FileWriter("conf/.info.ss");
	   		arqW.write("c"+"\r\n");
	   		arqW.write("120"+"\r\n");
	   		arqW.write("tttttt"+"\r\n");
	   		arqW.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			try {
				File diretorio = new File("conf");
				diretorio.mkdir();
				File arqF = new File(diretorio, ".info.ss");
				arqF.createNewFile();
				
				actionListener.actionPerformed(arg0);
				
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}
	public void fechar(JFrame form){
		
	}

}
