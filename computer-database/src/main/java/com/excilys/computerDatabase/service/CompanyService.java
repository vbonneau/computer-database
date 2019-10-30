package com.excilys.computerDatabase.service;

import java.util.ArrayList;

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

	public ArrayList<Company> getAll() {
		return dao.findAll();
	}

	public ArrayList<Company> getPage(int limit, int offcet) {
		return dao.findPage(limit, offcet);
	}

	public int count() {
		return dao.countCompany();
	}

	public Company getCompany(String companyName) {
		return dao.findOneByName(companyName);
	}

	public void deleteCompany(int id) {
		dao.deleteCompany(id);
	}
}
