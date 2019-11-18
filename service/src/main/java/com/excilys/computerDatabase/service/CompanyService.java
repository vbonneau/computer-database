package com.excilys.computerDatabase.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.computerDatabase.dao.CompanyDao;
import com.excilys.computerDatabase.entity.Company;

@Service
public class CompanyService {

	@Autowired
	private CompanyDao dao;

	private CompanyService() {

	}

	public List<Company> getAll() {
		return dao.findAll();
	}

	public List<Company> getPage(int limit, int offcet) {
		return dao.findPage(limit, offcet);
	}

	public long count() {
		return dao.countCompany();
	}

	public Company getCompany(String companyName) {
		return dao.findOneByName(companyName);
	}

	public void deleteCompany(int id) {
		dao.deleteCompany(id);
	}
}
