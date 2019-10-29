package com.excilys.computerDatabase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.excilys.computerDatabase.entity.Company;
import com.excilys.computerDatabase.mapper.CompanyMapper;

public class CompanyDao {

	private final String SELECT_ALL = "SELECT id,name FROM company";
	private final String SELECT_LIMT_OFFSET = "SELECT id,name FROM company LIMIT ? OFFSET ?";
	private final String COUNT = "SELECT COUNT(id) FROM company";
	private final String SELECT_NAME = "SELECT id,name FROM company WHERE company.name LIKE ?";
	private final String DELETE_COMPUTER = "DELETE FROM computer WHERE company_id=?";
	private final String DELETE = "DELETE FROM company WHERE id=?";
	private static CompanyDao instence = new CompanyDao();

	private CompanyDao() {

	}

	public static CompanyDao getINSTENCE() {
		return instence;
	}



	public ArrayList<Company> findAll() {
		ArrayList<Company> list = new ArrayList<Company>();
		ResultSet results;
		CompanyMapper mapper = CompanyMapper.getInstence();
		ConnectionMySQL connectionMySQL = ConnectionMySQL.getInstence();

		try {
			Connection conn = connectionMySQL.getConnection();
			PreparedStatement statement = conn.prepareStatement(SELECT_ALL);
			results = statement.executeQuery();

			while (results.next()) {
				list.add(mapper.resultSetToCompany(results));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			connectionMySQL.closeConnection();
		}
		return list;
	}

	public ArrayList<Company> findPage(int limit, int offset) {

		ArrayList<Company> list = new ArrayList<Company>();
		ResultSet results;
		CompanyMapper mapper;
		ConnectionMySQL connectionMySQL = ConnectionMySQL.getInstence();

		try {
			Connection conn = connectionMySQL.getConnection();
			PreparedStatement statement = conn.prepareStatement(SELECT_LIMT_OFFSET);

			statement.setInt(1, limit);
			statement.setInt(2, offset);
			results = statement.executeQuery();

			while (results.next()) {
				mapper = CompanyMapper.getInstence();
				list.add(mapper.resultSetToCompany(results));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			connectionMySQL.closeConnection();
		}
		return list;
	}

	public int countCompany() {
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			connectionMySQL.closeConnection();
		}
		return 0;
	}

	public Company findOneByName(String name) {

		Company company = null;
		ResultSet results;
		CompanyMapper mapper;
		ConnectionMySQL connectionMySQL = ConnectionMySQL.getInstence();

		try {
			Connection conn = connectionMySQL.getConnection();
			PreparedStatement statement = conn.prepareStatement(SELECT_NAME);
			statement.setString(1, name);
			results = statement.executeQuery();
			results.next();
			mapper = CompanyMapper.getInstence();
			company = mapper.resultSetToCompany(results);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			connectionMySQL.closeConnection();
		}
		return company;
	}

	public boolean deleteCompany(int id) {
		ConnectionMySQL connectionMySQL = ConnectionMySQL.getInstence();
		try {
			Connection conn = connectionMySQL.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement computerStatement = conn.prepareStatement(DELETE_COMPUTER);
			computerStatement.setInt(1, id);
			computerStatement.executeUpdate();
			PreparedStatement companyStatement = conn.prepareStatement(DELETE);
			companyStatement.setInt(1, id);
			companyStatement.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			connectionMySQL.closeConnection();
		}
		return true;
		
	}
}
