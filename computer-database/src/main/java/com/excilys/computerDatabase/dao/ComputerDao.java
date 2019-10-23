package main.java.com.excilys.computerDatabase.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import main.java.com.excilys.computerDatabase.entity.Computer;
import main.java.com.excilys.computerDatabase.mapper.ComputerMapper;

public class ComputerDao {

	private final String SELECT_ALL = "SELECT computer.id,computer.name,introduced,discontinued,company.id,company.name FROM computer LEFT JOIN company ON computer.company_id = company.id";
	private final String SELECT_LIMT_OFFSET = "SELECT computer.id,computer.name,introduced,discontinued,company.id,company.name FROM computer LEFT JOIN company ON computer.company_id = company.id LIMIT ? OFFSET ?";
	private final String SELECT_ID = "SELECT computer.id,computer.name,introduced,discontinued,company.id,company.name FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.id=?";
	private final String INSERT = "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES (?,?,?,?)";
	private final String UPDATE = "UPDATE computer SET name = ?,introduced = ?,discontinued = ?, company_id = (SELECT id FROM company WHERE company.name LIKE ?) WHERE id = ?";
	private final String DELETE = "DELETE FROM computer WHERE id=?";
	private final String COUNT = "SELECT COUNT(id) FROM computer";
	private static final ComputerDao INSTENCE = new ComputerDao();

	private ComputerDao() {

	}

	public static ComputerDao getComputerDao() {
		return INSTENCE;
	}

	public ArrayList<Computer> findAll() {

		ArrayList<Computer> list = new ArrayList<Computer>();
		ResultSet results;
		ComputerMapper mapper;
		ConnectionMySQL connectionMySQL = ConnectionMySQL.getInstence();

		try {
			Connection conn = connectionMySQL.getConnection();
			PreparedStatement statement = conn.prepareStatement(SELECT_ALL);
			results = statement.executeQuery();
			while (results.next()) {
				mapper = ComputerMapper.getInstence();
				list.add(mapper.resultSetToComputer(results));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectionMySQL.closeConnection();
		}
		return list;
	}

	public ArrayList<Computer> findPage(int limit, int offset) {

		ArrayList<Computer> list = new ArrayList<Computer>();
		ResultSet results;
		ComputerMapper mapper = ComputerMapper.getInstence();
		ConnectionMySQL connectionMySQL = ConnectionMySQL.getInstence();

		try {
			Connection conn = connectionMySQL.getConnection();
			PreparedStatement statement = conn.prepareStatement(SELECT_LIMT_OFFSET);
			statement.setInt(1, limit);
			statement.setInt(2, offset);
			results = statement.executeQuery();

			while (results.next()) {
				list.add(mapper.resultSetToComputer(results));
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
		ComputerMapper mapper;
		ConnectionMySQL connectionMySQL = ConnectionMySQL.getInstence();

		try {
				Connection conn = connectionMySQL.getConnection();
				PreparedStatement statement = conn.prepareStatement(SELECT_ID);
				statement.setInt(1, id);
				results = statement.executeQuery();

				results.next();
				mapper =  ComputerMapper.getInstence();
				computer = mapper.resultSetToComputer(results);

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
		ConnectionMySQL connectionMySQL = ConnectionMySQL.getInstence();

		try {
			Connection conn = connectionMySQL.getConnection();
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1, computer.getName());
			statement.setDate(2, Date.valueOf(introduced));
			statement.setDate(3, Date.valueOf(discontinued));
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
			if (e.getErrorCode() == 1292) {
				System.out.println("date invalide l'enregistemant a échoué");
			}
			return false;
		} finally {
			connectionMySQL.closeConnection();
		}
		return true;
	}

	public boolean deleteComputer(int id) {
		ConnectionMySQL connectionMySQL = ConnectionMySQL.getInstence();

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
		ConnectionMySQL connectionMySQL = ConnectionMySQL.getInstence();

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
}
