package com.exilys.computerDatabase.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class ConnectionMySQL {
	
	private static Connection conn = null;
	
	private ConnectionMySQL() throws ClassNotFoundException {
		//Class.forName("mysql-connector-java-5.1.48.jar");
		
		String url = "jdbc:mysql://localhost:3306/computer-database-db?useSSL=false";
				
		try {
			 conn = DriverManager.getConnection(url,"admincdb","qwerty1234");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static Connection getConnection() throws ClassNotFoundException {
		try {
			if(conn == null || conn.isClosed()) {
				new ConnectionMySQL();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return conn;
	}
	
	public static void closeConnection() {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
		
}
