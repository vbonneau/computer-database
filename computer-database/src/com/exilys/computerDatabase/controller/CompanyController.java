package com.exilys.computerDatabase.controller;

import java.util.ArrayList;

import com.exilys.computerDatabase.dao.CompanyDao;
import com.exilys.computerDatabase.entity.Company;

public class CompanyController {

	public static void displayAll() throws ClassNotFoundException {
		
		ArrayList<Company> listCompany = CompanyDao.findAll();
		
		for(Company company : listCompany) {
			System.out.println(company.getName());
			
		}
	}
}
