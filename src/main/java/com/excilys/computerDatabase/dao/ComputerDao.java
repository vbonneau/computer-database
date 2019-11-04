package com.excilys.computerDatabase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.excilys.computerDatabase.entity.Computer;
import com.excilys.computerDatabase.mapper.ComputerMapper;

@Repository
public class ComputerDao {

	private final String SELECT_ID = "SELECT computer.id,computer.name,introduced,discontinued,company.id,company.name"
			+ " FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.id=:id";
	private final String INSERT = "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES (:name,:introduced,:discontinued,:company)";
	private final String UPDATE = "UPDATE computer SET name = :name,introduced = :introduced,discontinued = :discontinued, company_id = :company WHERE id = :id";
	private final String DELETE = "DELETE FROM computer WHERE id=:id";
	private final String SELECT_LIMT_OFFSET_SEARCH_ASC = "SELECT computer.id,computer.name,introduced,discontinued,company.id,company.name "
			+ "FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.name like :search OR company.name like :search "
			+ "ORDER BY CASE :order WHEN 'name' THEN computer.name WHEN 'introduced' THEN computer.introduced "
			+ "WHEN 'discontinued' THEN computer.discontinued WHEN 'company' THEN company.name END ASC LIMIT :limit OFFSET :offset";
	private final String SELECT_LIMT_OFFSET_SEARCH_DESC = "SELECT computer.id,computer.name,introduced,discontinued,company.id,company.name "
			+ "FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.name like :search OR company.name like :search "
			+ "ORDER BY CASE :order WHEN 'name' THEN computer.name WHEN 'introduced' THEN computer.introduced "
			+ "WHEN 'discontinued' THEN computer.discontinued WHEN 'company' THEN company.name END DESC LIMIT :limit OFFSET :offset";
//	private final String SELECT_LIMT_OFFSET_SEARCH = "SELECT computer.id,computer.name,introduced,discontinued,company.id,company.name "
//			+ "FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.name like :search OR company.name like :search "
//			+ "ORDER BY CASE :order WHEN 'name' THEN computer.name WHEN 'introduced' THEN computer.introduced "
//			+ "WHEN 'discontinued' THEN computer.discontinued WHEN 'company' THEN company.name END "
//			+ "CASE :asc WHEN 1 THEN ASC WHEN 0 THEN DESC END LIMIT :limit OFFSET :offset";
	
	private final String COUNT_SEARCH = "SELECT COUNT(computer.id) FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.name like :search OR company.name like :search";
	@Autowired
	private ComputerMapper computerMapper;
	
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Autowired
	public ComputerDao(DataSource dataSource) {
		jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	public ArrayList<Computer> findAll() {

		ArrayList<Computer> list = new ArrayList<Computer>();
		ResultSet results;

//		try {
//			Connection conn = connectionMySQL.getConnection();
//			PreparedStatement statement = conn.prepareStatement(SELECT_ALL);
//			results = statement.executeQuery();
//			while (results.next()) {
//				list.add(computerMapper.resultSetToComputer(results));
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			connectionMySQL.closeConnection();
//		}
		return list;
	}

	public ArrayList<Computer> findPage(int limit, int offset) {

		ArrayList<Computer> list = new ArrayList<Computer>();
		ResultSet results;
//
//		try {
//			Connection conn = connectionMySQL.getConnection();
//			PreparedStatement statement = conn.prepareStatement(SELECT_LIMT_OFFSET);
//			statement.setInt(1, limit);
//			statement.setInt(2, offset);
//			results = statement.executeQuery();
//
//			while (results.next()) {
//				list.add(computerMapper.resultSetToComputer(results));
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			connectionMySQL.closeConnection();
//		}
		
		
		return list;
	}

	public Computer findOneById(int id) {
//		Computer computer = new Computer();
//		ResultSet results;
//
//		try {
//				Connection conn = connectionMySQL.getConnection();
//				PreparedStatement statement = conn.prepareStatement(SELECT_ID);
//				statement.setInt(1, id);
//				results = statement.executeQuery();
//
//				results.next();
//				computer = computerMapper.resultSetToComputer(results);
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			connectionMySQL.closeConnection();
//		}
//		return computer;
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("id", id);

		return jdbcTemplate.queryForObject(SELECT_ID,parameters,computerMapper);
	}

	public boolean inserComputer(Computer computer) {
		return saveComputer(computer, INSERT);
		
	}

	public boolean updateComputer(Computer computer) {
		return saveComputer(computer, UPDATE);
	}

	private boolean saveComputer(Computer computer, String query) {
		int idCompany = computer.getCompany().getId();
				
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("id",computer.getId())
				.addValue("name",computer.getName())
				.addValue("introduced", computer.getIntroduced())
				.addValue("discontinued", computer.getDiscontinued())
				.addValue("company", idCompany == 0 ? null : idCompany);
		
		if(jdbcTemplate.update(query,parameters) == 1) {
			return true;
		}else {
			return false;
		}
	}

	public boolean deleteComputer(int id) {
		SqlParameterSource parameters = new MapSqlParameterSource().addValue("id", id);
		if(jdbcTemplate.update(DELETE,parameters) == 1) {
			return true;
		}else {
			return false;
		}
	}

	
	public List<Computer> findPage(int limit, int offset, String search, String order,boolean asc) {
		
		String query = asc ? SELECT_LIMT_OFFSET_SEARCH_ASC : SELECT_LIMT_OFFSET_SEARCH_DESC;
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("search", "%" + search + "%")
				.addValue("order", order)
				.addValue("asc", asc)
				.addValue("limit", limit)
				.addValue("offset", offset);

		//return jdbcTemplate.query(SELECT_LIMT_OFFSET_SEARCH,parameters,computerMapper);
		return jdbcTemplate.query(query,parameters,computerMapper);
//		ArrayList<Computer> list = new ArrayList<Computer>();
//		ResultSet results;
//
//		try {
//			Connection conn = connectionMySQL.getConnection();
//			String querry = asc ? SELECT_LIMT_OFFSET_SEARCH_ASC : SELECT_LIMT_OFFSET_SEARCH_DESC;
//			PreparedStatement statement = conn.prepareStatement(querry);
//			statement.setString(1, "%" + search + "%");
//			statement.setString(2, "%" + search + "%");
//			statement.setString(3, order);
//			statement.setInt(4, limit);
//			statement.setInt(5, offset);
//
//			results = statement.executeQuery();
//
//			while (results.next()) {
//				list.add(computerMapper.resultSetToComputer(results));
//			}
//		} catch (SQLException se) {
//			for(Throwable e: se) {
//				System.err.println(e);
//			}
//		} finally {
//			connectionMySQL.closeConnection();
//		}
//		return list;
	}

	public int countComputer(String search) {
		
		SqlParameterSource parameters = new MapSqlParameterSource().addValue("search", "%" + search + "%");
		return jdbcTemplate.queryForObject(COUNT_SEARCH,parameters,Integer.class);
	}
}
