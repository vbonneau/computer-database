package com.exilys.computerDatabase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.exilys.computerDatabase.entity.Company;

public class CompanyDao {
	
	public static ArrayList<Company> findAll() throws ClassNotFoundException {
		String query = "SELECT * FROM company";
		ArrayList<Company> list = new ArrayList<Company>();
		ResultSet results;
		
		try {
			Connection conn = ConnectionMySQL.getConnection();
			PreparedStatement statement = conn.prepareStatement(query);
			results = statement.executeQuery(query);
			
			while(results.next()) {
				Company company = new Company();
				company.setId(results.getInt("id"));
				company.setName(results.getString("name"));
				list.add(company);
			}
			//ConnectionMySQL.closeConnection();
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnectionMySQL.closeConnection();
		}
		
		return list;
	}

}
