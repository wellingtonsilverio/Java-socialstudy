package modal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class Opcoes {
	private int intTempoVeri;
	private boolean blnNovaResp, blnNovaPerg, blnSeguidor, blnAbrirWin, blnLogAuto;
	
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
	
	public void salvarAlteracies(){
		
	}
	public void resetarAlteracoes(){
		
	}
	public void fechar(){
		
	}

}
