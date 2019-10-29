package com.excilys.computerDatabase.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ConnectionMySQL {

	private Connection conn = null;
	private static ConnectionMySQL instence;
	String configFile = "/db.properties";
	
	HikariConfig cfg = new HikariConfig(configFile);
    HikariDataSource ds = new HikariDataSource(cfg);

	private ConnectionMySQL() {

	}

	public static ConnectionMySQL getInstence() {
		if (instence == null) {
			instence = new ConnectionMySQL();
		}
		return instence;
	}

	public Connection getConnection() throws SQLException {
		conn=ds.getConnection();
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

}
