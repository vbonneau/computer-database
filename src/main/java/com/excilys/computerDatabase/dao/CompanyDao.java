package com.excilys.computerDatabase.dao;


import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.excilys.computerDatabase.entity.Company;
import com.excilys.computerDatabase.mapper.CompanyMapper;


@Repository
public class CompanyDao {

	private final String SELECT_ALL = "SELECT id,name FROM company";
	private final String SELECT_LIMT_OFFSET = "SELECT id,name FROM company LIMIT :limit OFFSET :offset";
	private final String COUNT = "SELECT COUNT(id) FROM company";
	private final String SELECT_NAME = "SELECT id,name FROM company WHERE company.name LIKE :name";
	private final String DELETE_COMPUTER = "DELETE FROM computer WHERE company_id=:id";
	private final String DELETE = "DELETE FROM company WHERE id=:id";
	@Autowired
	private CompanyMapper companyMapper;
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	private CompanyDao(DataSource dataSource) {
		jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	public List<Company> findAll() {

		SqlParameterSource parameters = new MapSqlParameterSource();
		try {
			return jdbcTemplate.query(SELECT_ALL,parameters,companyMapper);
		} catch (BadSqlGrammarException e) {
			return new ArrayList<Company>();
		}
	}

	public List<Company> findPage(int limit, int offset) {

		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("limit", limit)
				.addValue("offset", offset);
		try {
			return jdbcTemplate.query(SELECT_LIMT_OFFSET,parameters,companyMapper);
		} catch (BadSqlGrammarException e) {
			return new ArrayList<Company>();
		}
	}

	public int countCompany() {
		SqlParameterSource parameters = new MapSqlParameterSource();
		return jdbcTemplate.queryForObject(COUNT,parameters,Integer.class);
	}

	public Company findOneByName(String name) {
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("name", name);

		return jdbcTemplate.queryForObject(SELECT_NAME,parameters,companyMapper);
		
	}

	public boolean deleteCompany(int id) {
		SqlParameterSource parameters = new MapSqlParameterSource().addValue("id", id);
		jdbcTemplate.update(DELETE_COMPUTER,parameters);
		if(jdbcTemplate.update(DELETE,parameters) == 1) {
			return true;
		}else {
			return false;
		}
	}
}
