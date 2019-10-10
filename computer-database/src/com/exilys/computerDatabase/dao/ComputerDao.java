package com.exilys.computerDatabase.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.exilys.computerDatabase.entity.Company;
import com.exilys.computerDatabase.entity.Computer;

public class ComputerDao {
	

	public static ArrayList<Computer> findAll() throws ClassNotFoundException {
		String query = "SELECT * FROM computer LEFT JOIN company ON computer.company_id = company.id";
		ArrayList<Computer> list = new ArrayList<Computer>();
		ResultSet results;
		
		try {
			Connection conn = ConnectionMySQL.getConnection();
			PreparedStatement statement = conn.prepareStatement(query);
			results = statement.executeQuery();
			
			while(results.next()) {
				Company company = new Company();
				company.setId(results.getInt("company.id"));
				company.setName(results.getString("company.name"));
				
				Computer computer = new Computer();
				computer.setId(results.getInt("computer.id"));
				computer.setName(results.getString("computer.name"));
				computer.setIntroduced(results.getDate("computer.introduced"));
				computer.setDiscontinued(results.getDate("computer.discontinued"));
				computer.setCompany(company);
				
				list.add(computer);
			}
			
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnectionMySQL.closeConnection();
		}
		
		return list;
	}
	
	public static ArrayList<Computer> findAll(int limit,int offset) throws ClassNotFoundException {
		String query = "SELECT * FROM computer LEFT JOIN company ON computer.company_id = company.id LIMIT ? OFFSET ?";
		ArrayList<Computer> list = new ArrayList<Computer>();
		ResultSet results;
		
		try {
			Connection conn = ConnectionMySQL.getConnection();
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setInt(1, limit);
			statement.setInt(2, offset);
			results = statement.executeQuery();
			
			while(results.next()) {
				Company company = new Company();
				company.setId(results.getInt("company.id"));
				company.setName(results.getString("company.name"));
				
				Computer computer = new Computer();
				computer.setId(results.getInt("computer.id"));
				computer.setName(results.getString("computer.name"));
				computer.setIntroduced(results.getDate("computer.introduced"));
				computer.setDiscontinued(results.getDate("computer.discontinued"));
				computer.setCompany(company);
				computer.toString();
				list.add(computer);
			}
			
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnectionMySQL.closeConnection();
		}
		
		return list;
	}
	
	public static Computer findOneById(int id) {
		String query = "SELECT * FROM computer"  
				+ " LEFT JOIN company ON computer.company_id = company.id"
				+ " WHERE computer.id=?";
		Company company = new Company();
		Computer computer = new Computer();
		ResultSet results;
		
		try {
				Connection conn = ConnectionMySQL.getConnection();
				PreparedStatement statement = conn.prepareStatement(query);
				statement.setInt(1,id);
				results = statement.executeQuery();
				
				results.next();
				company.setId(results.getInt("company.id"));
				company.setName(results.getString("company.name"));
							
				computer.setId(results.getInt("computer.id"));
				computer.setName(results.getString("computer.name"));
				computer.setIntroduced(results.getDate("computer.introduced"));
				computer.setDiscontinued(results.getDate("computer.discontinued"));
				computer.setCompany(company);
							
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnectionMySQL.closeConnection();
		}
		
		return computer;
	}
	
	public static void inserComputer(Computer computer) {
		String query = "INSERT INTO computer (name,introduced,discontinued,company_id)"
				+"VALUES (?,?,?,(SELECT id FROM company WHERE company.name LIKE ?))";
		Date sqlIntroduced = null;
		if(computer.getIntroduced() != null) {
			sqlIntroduced = new Date(computer.getIntroduced().getTime());
		}
		Date sqlDiscontinued = null;
		if(computer.getDiscontinued() != null) {
			sqlDiscontinued = new Date(computer.getDiscontinued().getTime());
		}

		try {
			Connection conn = ConnectionMySQL.getConnection();
			PreparedStatement statement = conn.prepareStatement(query); 
			statement.setString(1,computer.getName());
			statement.setDate(2,sqlIntroduced);
			statement.setDate(3,sqlDiscontinued);
			statement.setString(4,computer.getCompany().getName());
			statement.executeUpdate();
			ConnectionMySQL.closeConnection();
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void updateComputer(Computer computer) {
		String query = "UPDATE computer SET name = ?,introduced = ?,discontinued = ?,"
				+ " company_id = (SELECT id FROM company WHERE company.name LIKE ?) WHERE id = ?";
		Date sqlIntroduced = new Date(computer.getIntroduced().getTime());
		Date sqlDiscontinued = new Date(computer.getDiscontinued().getTime());

		try {
			Connection conn = ConnectionMySQL.getConnection();
			PreparedStatement statement = conn.prepareStatement(query); 
			statement.setString(1,computer.getName());
			statement.setDate(2,sqlIntroduced);
			statement.setDate(3,sqlDiscontinued);
			statement.setString(4,computer.getCompany().getName());
			statement.setInt(5, computer.getId());
			statement.executeUpdate();
			ConnectionMySQL.closeConnection();			
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void delete(int id) {
		String query ="DELETE FROM computer WHERE id=?";
		try {
			Connection conn = ConnectionMySQL.getConnection();
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setInt(1,id);
			statement.executeUpdate();
			ConnectionMySQL.closeConnection();
		} catch (SQLException |ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	public static int countComputer() {
		String query = "SELECT COUNT(*) FROM computer";
		Connection conn;
		ResultSet results;
		
		try {
			conn = ConnectionMySQL.getConnection();
			PreparedStatement statement = conn.prepareStatement(query);
			results = statement.executeQuery();
			results.next();
			return results.getInt(1);
			
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
}
