package com.excilys.computerDatabase.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.exilys.computerDatabase.entity.Company;

public class CompanyMapper {
	
	private static CompanyMapper instence;
	
	private CompanyMapper() {
		
	}
	
	public static CompanyMapper getInstence() {
		if(instence == null) { instence = new CompanyMapper(); }
		return instence;
	}

	public Company ResultSetToCompany(ResultSet result) {
		Company company;
		
		try {
			company = new Company.CompanyBuilder().withId(result.getInt("id")).withName(result.getString("name")).build();
		} catch (SQLException e) {
			company = new Company();
		}
		
		return company;
	}
}
