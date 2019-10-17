package main.java.com.excilys.computerDatabase.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionMySQL {
	
	private Connection conn = null;
	private static ConnectionMySQL instence;
	private String url = "jdbc:mysql://localhost:3306/computer-database-db?useSSL=false";
	
	private ConnectionMySQL(){
		
	}
	
	public static ConnectionMySQL getInstence() {
		if(instence == null) { instence = new ConnectionMySQL(); }
		return instence;
	}
	
	public Connection getConnection(){
		try {
			if(conn == null || conn.isClosed()) {
				try {
					 conn = DriverManager.getConnection(url,"admincdb","qwerty1234");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return conn;
	}
	
	public void closeConnection() {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void useTestDatabase() {
		//url = "jdbc:h2:~/Documents/script-sql/SCHEMA H2DB.sql";
		url = "jdbc:h2:mem:test;INIT=runscript from '~/Documents/script-sql/SCHEMA H2DB.sql'";
	}
		
}
