package it.polito.tdp.emergency.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import it.polito.tdp.emergency.model.Paziente;

public class PazienteDAO {

	public Set<Paziente> readPazienti(){
		Set<Paziente> temp = new HashSet<>();
		try{			
			Connection conn = DBConnection.getConnection();
			String sql = "select id, triage from view_arrivals";
			Statement st = conn.createStatement();
			ResultSet res = st.executeQuery(sql);
			while(res.next()){
				Paziente p = null;
				if(res.getString("triage").compareTo("Green")==0){
					p = new Paziente(res.getInt("id"), Paziente.StatoPaziente.VERDE);
				}
				else if(res.getString("triage").compareTo("White")==0){
					p = new Paziente(res.getInt("id"), Paziente.StatoPaziente.BIANCO);
				}
				else if(res.getString("triage").compareTo("Red")==0){
					p = new Paziente(res.getInt("id"), Paziente.StatoPaziente.ROSSO);
				} 
				else if(res.getString("triage").compareTo("Yellow")==0){
					p = new Paziente(res.getInt("id"), Paziente.StatoPaziente.GIALLO);
				}
				temp.add(p);
			}
			res.close();
			conn.close();
		} catch(SQLException e){
			e.printStackTrace();
			System.out.println("Errore 2");
		}		
		return temp;
	}
	
}
