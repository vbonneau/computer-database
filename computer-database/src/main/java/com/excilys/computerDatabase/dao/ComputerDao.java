package com.excilys.computerDatabase.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.excilys.computerDatabase.entity.Computer;
import com.excilys.computerDatabase.mapper.ComputerMapper;

@Repository
@Scope("singleton")
public class ComputerDao {

	private final String SELECT_ALL = "SELECT computer.id,computer.name,introduced,discontinued,company.id,company.name"
			+ " FROM computer LEFT JOIN company ON computer.company_id = company.id";
	private final String SELECT_LIMT_OFFSET = "SELECT computer.id,computer.name,introduced,discontinued,company.id,company.name"
			+ " FROM computer LEFT JOIN company ON computer.company_id = company.id LIMIT ? OFFSET ?";
	private final String SELECT_ID = "SELECT computer.id,computer.name,introduced,discontinued,company.id,company.name"
			+ " FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.id=?";
	private final String INSERT = "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES (?,?,?,?)";
	private final String UPDATE = "UPDATE computer SET name = ?,introduced = ?,discontinued = ?, company_id = ? WHERE id = ?";
	private final String DELETE = "DELETE FROM computer WHERE id=?";
	private final String COUNT = "SELECT COUNT(id) FROM computer";
	private final String SELECT_LIMT_OFFSET_SEARCH_ASC = "SELECT computer.id,computer.name,introduced,discontinued,company.id,company.name "
			+ "FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.name like ? OR company.name like ? "
			+ "ORDER BY CASE ? WHEN 'name' THEN computer.name WHEN 'introduced' THEN computer.introduced "
			+ "WHEN 'discontinued' THEN computer.discontinued WHEN 'company' THEN company.name END ASC LIMIT ? OFFSET ?";
	private final String SELECT_LIMT_OFFSET_SEARCH_DESC = "SELECT computer.id,computer.name,introduced,discontinued,company.id,company.name "
			+ "FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.name like ? OR company.name like ? "
			+ "ORDER BY CASE ? WHEN 'name' THEN computer.name WHEN 'introduced' THEN computer.introduced "
			+ "WHEN 'discontinued' THEN computer.discontinued WHEN 'company' THEN company.name END DESC LIMIT ? OFFSET ?";
	private final String COUNT_SEARCH = "SELECT COUNT(computer.id) FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.name like ? OR company.name like ?";
	@Autowired
	private ConnectionMySQL connectionMySQL;
	@Autowired
	private ComputerMapper computerMapper;

	private ComputerDao() {

	}

	public ArrayList<Computer> findAll() {

		ArrayList<Computer> list = new ArrayList<Computer>();
		ResultSet results;

		try {
			Connection conn = connectionMySQL.getConnection();
			PreparedStatement statement = conn.prepareStatement(SELECT_ALL);
			results = statement.executeQuery();
			while (results.next()) {
				list.add(computerMapper.resultSetToComputer(results));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectionMySQL.closeConnection();
		}
		return list;
	}

	public ArrayList<Computer> findPage(int limit, int offset, String order) {

		ArrayList<Computer> list = new ArrayList<Computer>();
		ResultSet results;

		try {
			Connection conn = connectionMySQL.getConnection();
			PreparedStatement statement = conn.prepareStatement(SELECT_LIMT_OFFSET);
			statement.setInt(1, limit);
			statement.setInt(2, offset);
			results = statement.executeQuery();

			while (results.next()) {
				list.add(computerMapper.resultSetToComputer(results));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectionMySQL.closeConnection();
		}
		return list;
	}

	public Computer findOneById(int id) {
		Computer computer = new Computer();
		ResultSet results;

		try {
				Connection conn = connectionMySQL.getConnection();
				PreparedStatement statement = conn.prepareStatement(SELECT_ID);
				statement.setInt(1, id);
				results = statement.executeQuery();

				results.next();
				computer = computerMapper.resultSetToComputer(results);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectionMySQL.closeConnection();
		}
		return computer;
	}

	public boolean inserComputer(Computer computer) {
		return saveComputer(computer, INSERT);
		
	}

	public boolean updateComputer(Computer computer) {
		return saveComputer(computer, UPDATE);
	}

	private boolean saveComputer(Computer computer, String query) {
		LocalDate introduced = computer.getIntroduced();
		LocalDate discontinued = computer.getDiscontinued();
		int idCompany = computer.getCompany().getId();

		try {
			Connection conn = connectionMySQL.getConnection();
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1, computer.getName());
			statement.setDate(2, introduced == null?null:Date.valueOf(introduced));
			statement.setDate(3, discontinued == null?null:Date.valueOf(discontinued));
			if (idCompany == 0) {
				statement.setObject(4, null);
			} else {
				statement.setInt(4, computer.getCompany().getId());
			}
			if (query == UPDATE) {
				statement.setInt(5, computer.getId());
			}
			statement.executeUpdate();
			connectionMySQL.closeConnection();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		} finally {
			connectionMySQL.closeConnection();
		}
		return true;
	}

	public boolean deleteComputer(int id) {

		try {
			Connection conn = connectionMySQL.getConnection();
			PreparedStatement statement = conn.prepareStatement(DELETE);
			statement.setInt(1, id);
			statement.executeUpdate();
			connectionMySQL.closeConnection();
		} catch (SQLException e) {
			return false;
		} finally {
			connectionMySQL.closeConnection();
		}
		return true;
	}

	public int countComputer() {
		Connection conn;
		ResultSet results;

		try {
			conn = connectionMySQL.getConnection();
			PreparedStatement statement = conn.prepareStatement(COUNT);
			results = statement.executeQuery();
			results.next();
			return results.getInt(1);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectionMySQL.closeConnection();
		}
		return 0;
	}

	public ArrayList<Computer> findPage(int limit, int offset, String search, String order,boolean asc) {
		ArrayList<Computer> list = new ArrayList<Computer>();
		ResultSet results;

		try {
			Connection conn = connectionMySQL.getConnection();
			String querry = asc?SELECT_LIMT_OFFSET_SEARCH_ASC:SELECT_LIMT_OFFSET_SEARCH_DESC;
			PreparedStatement statement = conn.prepareStatement(querry);
			statement.setString(1, "%" + search + "%");
			statement.setString(2, "%" + search + "%");
			statement.setString(3, order);
			statement.setInt(4, limit);
			statement.setInt(5, offset);
			
			
			results = statement.executeQuery();

			while (results.next()) {
				list.add(computerMapper.resultSetToComputer(results));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectionMySQL.closeConnection();
		}
		return list;
	}

	public int countComputer(String search) {
		Connection conn;
		ResultSet results;

		try {
			conn = connectionMySQL.getConnection();
			PreparedStatement statement = conn.prepareStatement(COUNT_SEARCH);
			statement.setString(1, "%" + search + "%");
			statement.setString(2, "%" + search + "%");
			results = statement.executeQuery();
			results.next();
			return results.getInt(1);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectionMySQL.closeConnection();
		}
		return 0;
	}
}
