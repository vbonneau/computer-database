package main.resources.com.excilys.computerDatabase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import main.resources.com.excilys.computerDatabase.entity.Computer;
import main.resources.com.excilys.computerDatabase.mapper.ComputerMapper;

public class ComputerDao {
	
	private final String SELECT_ALL = "SELECT computer.id,computer.name,introduced,discontinued,company.id,company.name FROM computer LEFT JOIN company ON computer.company_id = company.id";
	private final String SELECT_LIMT_OFFSET = "SELECT computer.id,computer.name,introduced,discontinued,company.id,company.name FROM computer LEFT JOIN company ON computer.company_id = company.id LIMIT ? OFFSET ?";
	private final String SELECT_ID = "SELECT computer.id,computer.name,introduced,discontinued,company.id,company.name FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.id=?";
	private final String INSERT = "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES (?,?,?,?)";
	private final String UPDATE = "UPDATE computer SET name = ?,introduced = ?,discontinued = ?, company_id = (SELECT id FROM company WHERE company.name LIKE ?) WHERE id = ?";
	private final String DELETE = "DELETE FROM computer WHERE id=?";
	private final String COUNT = "SELECT COUNT(id) FROM computer";
	private final static ComputerDao INSTENCE = new ComputerDao();
	
	private ComputerDao(){
		
	}
	
	public static ComputerDao getComputerDao() {
		return INSTENCE;
	}
	
	public ArrayList<Computer> findAll(){
		
		ArrayList<Computer> list = new ArrayList<Computer>();
		ResultSet results;
		ComputerMapper mapper;
		
		try {
			Connection conn = ConnectionMySQL.getConnection();
			PreparedStatement statement = conn.prepareStatement(SELECT_ALL);
			results = statement.executeQuery();
			
			while(results.next()) {
				
				mapper = ComputerMapper.getInstence();
				list.add(mapper.ResultSetToComputer(results));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionMySQL.closeConnection();
		}
		
		return list;
	}
	
	public ArrayList<Computer> findPage(int limit,int offset){
		
		ArrayList<Computer> list = new ArrayList<Computer>();
		ResultSet results;
		ComputerMapper mapper = ComputerMapper.getInstence();
		
		try {
			Connection conn = ConnectionMySQL.getConnection();
			PreparedStatement statement = conn.prepareStatement(SELECT_LIMT_OFFSET);
			statement.setInt(1, limit);
			statement.setInt(2, offset);
			results = statement.executeQuery();
			
			while(results.next()) {
				
				list.add(mapper.ResultSetToComputer(results));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionMySQL.closeConnection();
		}
		
		return list;
	}
	
	public Computer findOneById(int id) {
		Computer computer = new Computer();
		ResultSet results;
		ComputerMapper mapper;
		
		try {
				Connection conn = ConnectionMySQL.getConnection();
				PreparedStatement statement = conn.prepareStatement(SELECT_ID);
				statement.setInt(1,id);
				results = statement.executeQuery();
				
				results.next();
				mapper =  ComputerMapper.getInstence();
				computer = mapper.ResultSetToComputer(results);
							
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionMySQL.closeConnection();
		}
		
		return computer;
	}
	
	public void inserComputer(Computer computer) {
		saveComputer(computer,INSERT);
	}
	
	public void updateComputer(Computer computer) {
		saveComputer(computer,UPDATE);
	}
	
	private void saveComputer(Computer computer,String query) {
		Timestamp introduced = null;
		Timestamp discontinued;
		LocalDateTime introducedDate = computer.getIntroduced();
		LocalDateTime discontinuedDate = computer.getDiscontinued();
		int idCompany = computer.getCompany().getId();
		

		introduced = introducedDate==null ? null: Timestamp.valueOf(introducedDate);
		discontinued = discontinuedDate==null ? null: Timestamp.valueOf(discontinuedDate);

		try {
			Connection conn = ConnectionMySQL.getConnection();
			PreparedStatement statement = conn.prepareStatement(query); 
			statement.setString(1,computer.getName());
			statement.setTimestamp(2,introduced);
			statement.setTimestamp(3,discontinued);
			if(idCompany == 0) { statement.setObject(4, null); }
			else { statement.setInt(4,computer.getCompany().getId()); }
			if(query == UPDATE) { statement.setInt(5,computer.getId()); }
			statement.executeUpdate();
			ConnectionMySQL.closeConnection();
		} catch (SQLException e){
			if(e.getErrorCode() == 1292) { System.out.println("date invalide l'enregistemant a échoué"); }
		}finally {
			ConnectionMySQL.closeConnection();
		}
	}
	
	public void delete(int id) {
		try {
			Connection conn = ConnectionMySQL.getConnection();
			PreparedStatement statement = conn.prepareStatement(DELETE);
			statement.setInt(1,id);
			statement.executeUpdate();
			ConnectionMySQL.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
			
	}
	
	public int countComputer() {
		Connection conn;
		ResultSet results;
		
		try {
			conn = ConnectionMySQL.getConnection();
			PreparedStatement statement = conn.prepareStatement(COUNT);
			results = statement.executeQuery();
			results.next();
			return results.getInt(1);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
}
