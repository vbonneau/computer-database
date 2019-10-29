package com.excilys.computerDatabase.service;

import java.util.ArrayList;

import com.excilys.computerDatabase.dao.CompanyDao;
import com.excilys.computerDatabase.entity.Company;

public class CompanyService {

	private CompanyDao dao = CompanyDao.getINSTENCE();
	private static CompanyService instence;

	private CompanyService() {

	}

	public static CompanyService getInstence() {
		if (instence == null) {
			instence = new CompanyService();
		}
		return instence;
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
