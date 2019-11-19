package com.excilys.computerDatabase.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.excilys.computerDatabase.entity.QUser;
import com.excilys.computerDatabase.entity.User;
import com.querydsl.jpa.impl.JPAQuery;

@Repository
public class UserDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	public User findOneByLogin(String login) {
		QUser user = QUser.user;
		JPAQuery<User> query = new JPAQuery<User>(entityManager);
		return query.from(user).where(user.login.like(login)).fetchOne();
	}
}
