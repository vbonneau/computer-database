package com.excilys.computerDatabase.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ConnectionMySQL {

	private Connection conn = null;
//	String configFile = "/db.properties";
	
//	HikariConfig cfg = new HikariConfig(configFile);
//    HikariDataSource ds = new HikariDataSource(cfg);
	@Autowired
	private DataSource ds;

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
