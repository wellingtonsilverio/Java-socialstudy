package modal;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

import view.JDJanela;
import view.JFLogin;

public class Atualizacao {
	private int intIdUltAttResp, intIdUltAttPerg, intIdUltAttSeg;
	private boolean blnBarulho;
	private Timer tmrAtualizacao;
	long delay;
	long interval;
	
	private Opcoes objOpc;
	
	public Atualizacao(Opcoes Opc){
		this.objOpc = Opc;
		
		try {
			FileReader arq = new FileReader("conf/.ultAtt.ss");
			BufferedReader lerArq = new BufferedReader(arq);
			String linha;
			while((linha = lerArq.readLine()) != null){
				try {
					intIdUltAttResp = Integer.parseInt(linha);
					linha = lerArq.readLine();
					intIdUltAttPerg = Integer.parseInt(linha);
					linha = lerArq.readLine();
					intIdUltAttSeg = Integer.parseInt(linha);
				} catch (Exception e) {
					intIdUltAttResp = 0;
					intIdUltAttPerg = 0;
					intIdUltAttSeg = 0;
				}
			}
			
		} catch (Exception e) {
			intIdUltAttResp = 0;
			intIdUltAttPerg = 0;
			intIdUltAttSeg = 0;
		}
		
		delay = 1000;
		interval = (objOpc.getIntTempoVeri()*60)*1000;
		
		blnBarulho = objOpc.isBlnFazerBarulho();
		
		
		tmrAtualizacao = new Timer();
		tmrAtualizacao.scheduleAtFixedRate(new TimerTask() {
			
			public void run() {
				//select nos refesh, se tiver uma com tipo e id maior que o ultAtt vai abrir uma caixa lateral
				try {
					int y = 10;
					
					PreparedStatement stmt = null;
			        String sql;
			        sql = "SELECT * FROM refesh where user_id = ?";
			        stmt = objOpc.getConn().prepareStatement(sql);
			        stmt.setInt(1, objOpc.getUsrID());
			        ResultSet rs = stmt.executeQuery();
			        while(rs.next()){
			        	delay = 1;
			        	if(rs.getInt("att_tipo") == 3 && rs.getInt("att_id") > intIdUltAttPerg){
			        		PreparedStatement stmt2 = null;
					        String sql2;
					        sql2 = "SELECT * FROM users where usr_id = ?";
					        stmt2 = objOpc.getConn().prepareStatement(sql2);
					        stmt2.setInt(1, rs.getInt("user_id"));
					        ResultSet rs2 = stmt2.executeQuery();
					        if(rs2.next()){
					        	new JDJanela(
				        				rs2.getString("usr_image"),
				        				rs2.getString("usr_nome"),
				        				rs.getString("att_desc"),
				        				rs.getString("att_date"),
				        				rs.getString("att_tipo"), y);
					        	intIdUltAttPerg = rs.getInt("att_id");
					        	salvar(intIdUltAttPerg, intIdUltAttResp, intIdUltAttSeg);
					        	y += 210;
					        }
			        		
			        	}else
			        	if(rs.getInt("att_tipo") == 4 && rs.getInt("att_id") > intIdUltAttResp){
			        		PreparedStatement stmt2 = null;
					        String sql2;
					        sql2 = "SELECT * FROM users where usr_id = ?";
					        stmt2 = objOpc.getConn().prepareStatement(sql2);
					        stmt2.setInt(1, rs.getInt("user_id"));
					        ResultSet rs2 = stmt2.executeQuery();
					        if(rs2.next()){
					        	new JDJanela(
				        				rs2.getString("usr_image"),
				        				rs2.getString("usr_nome"),
				        				rs.getString("att_desc"),
				        				rs.getString("att_date"),
				        				rs.getString("att_tipo"), y);
					        	intIdUltAttResp = rs.getInt("att_id");
					        	salvar(intIdUltAttPerg, intIdUltAttResp, intIdUltAttSeg);
					        	y += 210;
					        }
			        	}else
			        	if(rs.getInt("att_tipo") == 1 && rs.getInt("att_id") > intIdUltAttSeg){
			        		PreparedStatement stmt2 = null;
					        String sql2;
					        sql2 = "SELECT * FROM users where usr_id = ?";
					        stmt2 = objOpc.getConn().prepareStatement(sql2);
					        stmt2.setInt(1, rs.getInt("user_id"));
					        ResultSet rs2 = stmt2.executeQuery();
					        if(rs2.next()){
					        	new JDJanela(
				        				rs2.getString("usr_image"),
				        				rs2.getString("usr_nome"),
				        				rs.getString("att_desc"),
				        				rs.getString("att_date"),
				        				rs.getString("att_tipo"), y);
					        	intIdUltAttSeg = rs.getInt("att_id");
					        	salvar(intIdUltAttPerg, intIdUltAttResp, intIdUltAttSeg);
					        	y += 210;
					        }
			        	}else{
			        		y = 10;
			        	}
			        }
					//this.con.close();
				} catch (Exception e) {
					// TODO: handle exception
					//JOptionPane.showMessageDialog(null, "Erro: "+e);
				}
			}
		}, delay, interval);
		
	}
	
	public void mostrarCaixa(int intOque, String strMensagem){
		
	}
	public void fechar(){
		
	}
	public void click(String strLink){
		
	}
	public boolean verificarAtt(boolean blnNovaResp, boolean blnNovaPerg, boolean blnSeguidor){
		return false;
	}
	public void salvar(int intIdUltAttPerg2, int intIdUltAttResp2, int intIdUltAttSeg2){
		try {
			FileWriter arqW;
			arqW = new FileWriter("conf/.ultAtt.ss");
	   		arqW.write(intIdUltAttPerg2+"\r\n");
	   		arqW.write(intIdUltAttResp2+"\r\n");
	   		arqW.write(intIdUltAttSeg2+"\r\n");
	   		arqW.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			try {
				File diretorio = new File("conf");
				diretorio.mkdir();
				File arqF = new File(diretorio, ".ultAtt.ss");
				arqF.createNewFile();
				salvar(intIdUltAttPerg2, intIdUltAttResp2, intIdUltAttSeg2);
				
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}

}
