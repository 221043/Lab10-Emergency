package it.polito.tdp.emergency.db;

import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DBConnection {

	private static final String jdbcURL = "jdbc:mysql://localhost/emergency?user=root";
	private static ComboPooledDataSource dataSource = null;
	
	public static Connection getConnection(){
		try{
			if(dataSource==null){ //creo ed attivo il pool
				dataSource = new ComboPooledDataSource();
				dataSource.setJdbcUrl(jdbcURL);
			}
			return dataSource.getConnection();
		} catch(SQLException e){
			e.printStackTrace();
			throw new RuntimeException("Errore nella connessione", e);
		}
	}
	
}