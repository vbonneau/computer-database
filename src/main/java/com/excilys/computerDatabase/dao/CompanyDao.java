package com.excilys.computerDatabase.dao;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computerDatabase.entity.Company;
import com.excilys.computerDatabase.entity.QCompany;
import com.excilys.computerDatabase.entity.QComputer;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQuery;


@Repository
public class CompanyDao {
	
	@PersistenceContext
	EntityManager entityManager;


	@Autowired
	public CompanyDao(DataSource dataSource) {
	}

	public List<Company> findAll() {
		JPAQuery<Company> query = new JPAQuery<Company>(entityManager);
		QCompany company = QCompany.company;
		return query.from(company).fetch();
	}

	public List<Company> findPage(int limit, int offset) {

		JPAQuery<Company> query = new JPAQuery<Company>(entityManager);
		QCompany company = QCompany.company;
		return query.from(company)
				.limit(limit).offset(offset).fetch();
	}

	public long countCompany() {
		JPAQuery<?> query = new JPAQuery<Void>(entityManager);
		QCompany company = QCompany.company;
		return query.from(company)
				.fetchCount();
	}

	public Company findOneByName(String name) {
		JPAQuery<Company> query = new JPAQuery<Company>(entityManager);
		QCompany company = QCompany.company;
		return query.from(company).where(company.name.like(name)).fetchOne();
		
	}

	@Transactional
	public boolean deleteCompany(int id) {
		QComputer computer = QComputer.computer;
		QCompany company = QCompany.company;
		new JPADeleteClause(entityManager, computer).where(computer.company.id.eq(id)).execute();
		new JPADeleteClause(entityManager, company).where(company.id.eq(id)).execute();
		return true;
	}
}
