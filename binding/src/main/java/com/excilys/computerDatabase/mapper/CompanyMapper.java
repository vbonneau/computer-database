package com.excilys.computerDatabase.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.excilys.computerDatabase.dto.CompanyDto;
import com.excilys.computerDatabase.entity.Company;

@Component
public class CompanyMapper implements RowMapper<Company> {

	private CompanyMapper() {

	}


	public Company mapRow(ResultSet result, int rowNum) {
		Company company;

		try {
			company = new Company.CompanyBuilder().withId(result.getInt("id")).withName(result.getString("name")).build();
		} catch (SQLException e) {
			company = new Company();
		}
		return company;
	}
	
	public CompanyDto companyToDto(Company company) {
		return new CompanyDto.CompanyDtoBuilder().withId(company.getId()).withName(company.getName()).build();
	}
	
	public Company dtoToCompany(CompanyDto companyDto){
		return new Company.CompanyBuilder().withId(companyDto.getId()).withName(companyDto.getName()).build();
	}
}
