package modal;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
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

import com.sun.javafx.font.Disposer;

import view.JDJanela;
import view.JFLogin;

public class Atualizacao {
	private int intIdUltAttResp, intIdUltAttPerg, intIdUltAttSeg;
	private boolean blnBarulho;
	private Timer tmrAtualizacao;
	long delay;
	long interval;

	String urlSite = "http://localhost/rss";
	
	Rectangle rect;
	
	private Opcoes objOpc;
	
	int y = 10;
	
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
		
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
		rect = defaultScreen.getDefaultConfiguration().getBounds();
		
		tmrAtualizacao = new Timer();
		tmrAtualizacao.scheduleAtFixedRate(new TimerTask() {
			
			public void run() {
				y = 10;
				//select nos refesh, se tiver uma com tipo e id maior que o ultAtt vai abrir uma caixa lateral
				try {
					if(objOpc.isBlnSeguidor() && (y+210) < rect.getMaxY()){
						ResultSet rs = objOpc.getConn().select("select * FROM usr_usr INNER JOIN refesh ON usr_usr.flw_quem = refesh.user_id WHERE usr_usr.flw_de = ?", objOpc.getUsrID());
						while(rs.next()){
							if(rs.getInt("att_id") > intIdUltAttSeg){
								ResultSet selectUsers = objOpc.getConn().select("select * from users where usr_id = ?", rs.getInt("user_id"));
								selectUsers.next();
								new JDJanela(selectUsers.getString("usr_image"), selectUsers.getString("usr_nome"), rs.getString("att_desc"), rs.getString("att_date"), rs.getString("att_tipo"), y);
								y += 210;
								intIdUltAttSeg = rs.getInt("att_id");
								salvar(intIdUltAttPerg, intIdUltAttResp, intIdUltAttSeg);
								if((y+210) >= rect.getMaxY()) break;
							}
						}
					}
					if(objOpc.isBlnNovaPerg() && (y+210) < rect.getMaxY()){
						//pegar o id grupo que participa e pegar as perguntas que foi feito la
						//qual ele participa
						ResultSet rs = objOpc.getConn().select("select * from grp_usr where gu_usr = ?", objOpc.getUsrID());
						while(rs.next()){
							//pergunta do id do grupo que ele participa
							ResultSet selectPergGrups = objOpc.getConn().select("select * from pergunta where pgt_grp = ?", rs.getInt("gu_grp"));
							while(selectPergGrups.next()){
								if(selectPergGrups.getInt("pgt_id") > intIdUltAttPerg && selectPergGrups.getInt("pgt_usr") != objOpc.getUsrID()){
									//usuario que fez a pergunta
									ResultSet selectUsers = objOpc.getConn().select("select * from users where usr_id = ?", selectPergGrups.getInt("pgt_usr"));
									selectUsers.next();
									new JDJanela(selectUsers.getString("usr_image"), selectUsers.getString("usr_nome"), selectPergGrups.getString("pgt_perg"), selectPergGrups.getString("pgt_date"), "11", y);
									y += 210;
									intIdUltAttPerg = selectPergGrups.getInt("pgt_id");
									salvar(intIdUltAttPerg, intIdUltAttResp, intIdUltAttSeg);
									if((y+210) >= rect.getMaxY()) break;
								}
							}
						}
					}
					if(objOpc.isBlnNovaResp() && (y+210) < rect.getMaxY()){
						ResultSet rs = objOpc.getConn().select("select * from grp_usr where gu_usr = ?", objOpc.getUsrID());
						while(rs.next()){
							//pergunta do id do grupo que ele participa
							ResultSet selectPergGrups = objOpc.getConn().select("select * from pergunta where pgt_grp = ?", rs.getInt("gu_grp"));
							while(selectPergGrups.next()){
								ResultSet selectResp = objOpc.getConn().select("select * from responder where res_perg = ?", selectPergGrups.getInt("pgt_id"));
								while(selectResp.next()){
									if(selectResp.getInt("res_id") > intIdUltAttResp && selectResp.getInt("res_usr") != objOpc.getUsrID()){
										//usuario que fez a pergunta
										ResultSet selectUsers = objOpc.getConn().select("select * from users where usr_id = ?", selectResp.getInt("res_usr"));
										selectUsers.next();
										new JDJanela(selectUsers.getString("usr_image"), selectUsers.getString("usr_nome"), selectPergGrups.getString("pgt_perg")+"<br ><br ><font size='+1'>Resposta:</font><br >"+selectResp.getString("res_resp"), selectResp.getString("res_data"), "12", y);
										y += 210;
										intIdUltAttResp = selectResp.getInt("res_id");
										salvar(intIdUltAttPerg, intIdUltAttResp, intIdUltAttSeg);
										if((y+210) >= rect.getMaxY()) break;
									}
								}
							}
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(null, "Erro att: "+e);
				}
			}
		}, delay, interval);
		
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

	public void fechar() {
		tmrAtualizacao.cancel();
	}

}
