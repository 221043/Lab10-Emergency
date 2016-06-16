package it.polito.tdp.emergency.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.emergency.model.Evento;

public class EventoDAO {
	
	public List<Evento> readEventi(){
		List<Evento> temp = new LinkedList<>();
		try{			
			Connection conn = DBConnection.getConnection();
			String sql = "select id, timestamp from view_arrivals";
			Statement st = conn.createStatement();
			ResultSet res = st.executeQuery(sql);
			while(res.next()){
				temp.add(new Evento(res.getTimestamp("timestamp").getTime()/(60*1000), Evento.TipoEvento.PAZIENTE_ARRIVO, res.getInt("id")));
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
