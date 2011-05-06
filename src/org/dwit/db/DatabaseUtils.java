package org.dwit.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtils {
	 
	private Connection connection;
	private String jdbcDriver = "org.hsqldb.jdbcDriver";
	private String database_name = "...";
	private String database = "jdbc:hsqldb:file:"+database_name;
	private String user = "sa";
	private String password = "";
 
	
	public void connect() {
		
		try {
			Class.forName(jdbcDriver).newInstance();
		} catch (InstantiationException e) {
			System.out.println("ERROR: failed to load HSQLDB JDBC driver.");
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
 
		try {
			connection = DriverManager.getConnection(database, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
 
	public void disconnect() throws SQLException {
		
		Statement st = connection.createStatement();
 
		st.execute("SHUTDOWN");

		connection.close();
 
	}
	
	public void executeUpdate(String request) throws SQLException {
		Statement statement;
		statement = connection.createStatement();
		statement.executeUpdate(request);
	}
 
	public ResultSet executeRequest(String request) throws SQLException {
		Statement statement;
		statement = connection.createStatement();
		
		if (statement.execute(request)){
			ResultSet resultat = statement.executeQuery(request);
			return resultat;
		} else {
			return null;
		}
	}
}
