package com.excilys.computerDatabase.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computerDatabase.entity.Computer;
import com.excilys.computerDatabase.entity.QCompany;
import com.excilys.computerDatabase.entity.QComputer;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAUpdateClause;

@Repository
public class ComputerDao {
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	public ComputerDao() {
	}

	public List<Computer> findPage(int limit, int offset) {

		JPAQuery<Computer> query = new JPAQuery<Computer>(entityManager);
		QComputer computer = QComputer.computer;
		QCompany company = QCompany.company;
		return query.from(computer)
				.leftJoin(computer.company,company)
				.orderBy(computer.name.asc()).limit(limit).offset(offset).fetch();
	}

	public Computer findOneById(int id) {
		QComputer computer = QComputer.computer;
		JPAQuery<Computer> query = new JPAQuery<Computer>(entityManager);
		return query.from(computer).where(computer.id.eq(id)).fetchOne();
	}

	@Transactional
	public boolean inserComputer(Computer computer) {
		entityManager.persist(computer);
		return true;

	}

	@Transactional
	public boolean updateComputer(Computer computer) {
		QComputer qComputer = QComputer.computer;
		new JPAUpdateClause(entityManager, qComputer).where(qComputer.id.eq(computer.getId()))
		.set(qComputer.name, computer.getName())
		.set(qComputer.introduced, computer.getIntroduced())
		.set(qComputer.discontinued, computer.getDiscontinued())
		.set(qComputer.company.id, computer.getCompany()==null? null:computer.getCompany().getId())
		.execute();
		return true;
		
	}

	@Transactional
	public boolean deleteComputer(int id) {
		QComputer computer = QComputer.computer;
		new JPADeleteClause(entityManager, computer).where(computer.id.eq(id)).execute();
		return true;
	}

	public long countComputer() {
		JPAQuery<?> query = new JPAQuery<Void>(entityManager);
		QComputer computer = QComputer.computer;
		return query.from(computer)
				.fetchCount();
	}

	public List<Computer> findPage(int limit, int offset, String search, String order, boolean asc) {
		JPAQuery<Computer> query = new JPAQuery<Computer>(entityManager);
		QComputer computer = QComputer.computer;
		QCompany company = QCompany.company;
		return query.from(computer)
				.leftJoin(computer.company,company)
				.where(computer.name.like("%" + search + "%").or(computer.company.name.like("%" + search + "%")))
				.orderBy(computer.name.asc()).limit(limit).offset(offset).fetch();

	}

	public long countComputer(String search) {
		JPAQuery<?> query = new JPAQuery<Void>(entityManager);
		QComputer computer = QComputer.computer;
		QCompany company = QCompany.company;
		return query.from(computer)
				.leftJoin(computer.company,company)
				.where(computer.name.like("%" + search + "%").or(company.name.like("%" + search + "%")))
				.fetchCount();
	}
}
