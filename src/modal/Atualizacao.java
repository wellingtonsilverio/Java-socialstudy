package modal;

import java.io.BufferedReader;
import java.io.FileReader;
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
	private Timer tmrAtualizacao, tmrSumirCaixa;
	long delay = (5)*1000;
	long interval = (2*60*60)*1000;
	
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
		
		delay = (5)*1000;
		interval = (objOpc.getIntTempoVeri()*60)*1000;
		
		blnBarulho = objOpc.isBlnFazerBarulho();
		
		
		tmrAtualizacao = new Timer();
		tmrAtualizacao.scheduleAtFixedRate(new TimerTask() {
			
			public void run() {
				//select nos refesh, se tiver uma com tipo e id maior que o ultAtt vai abrir uma caixa lateral
				try {
					PreparedStatement stmt = null;
			        String sql;
			        sql = "SELECT * FROM refesh where usr_id = ?";
			        stmt = objOpc.getConn().prepareStatement(sql);
			        stmt.setInt(1, objOpc.getUsrID());
			        ResultSet rs = stmt.executeQuery();
			        while(rs.next()){
				        //usrNome = rs.getString("usr_nome");
			        	if(rs.getInt("att_tipo") == 3 && rs.getInt("att_id") > intIdUltAttPerg){
			        		new JDJanela();
			        	}
			        	if(rs.getInt("att_tipo") == 4 && rs.getInt("att_id") > intIdUltAttResp){
			        		new JDJanela();
			        	}
			        	if(rs.getInt("att_tipo") == 1 && rs.getInt("att_id") > intIdUltAttSeg){
			        		new JDJanela();
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

}
