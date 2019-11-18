package com.excilys.computerDatabase.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.excilys.computerDatabase.dto.ComputerDto;
import com.excilys.computerDatabase.entity.Company;
import com.excilys.computerDatabase.entity.Computer;
import com.excilys.computerDatabase.exception.BadEntriException;
import com.excilys.computerDatabase.validator.Validator;

@Component
public class ComputerMapper implements RowMapper<Computer> {

	@Autowired
	DateMapper dateMapper;
	@Autowired
	Validator validator;

	public ComputerMapper() {

	}

	public Computer mapRow(ResultSet result, int rowNum) {
		Company company = null;
		Computer computer = null;
		try {
			company = new Company.CompanyBuilder().withId(result.getInt("company.id"))
					.withName(result.getString("company.name")).build();

			Date introduced = result.getDate("computer.introduced");
			LocalDate introducedLocal;
			if (introduced == null) {
				introducedLocal = null;
			} else {
				introducedLocal = introduced.toLocalDate();
			}

			Date discontinued = result.getDate("computer.discontinued");
			LocalDate discontinuedLocal;
			if (discontinued == null) {
				discontinuedLocal = null;
			} else {
				discontinuedLocal = discontinued.toLocalDate();
			}

			computer = new Computer.ComputerBuilder().withId(result.getInt("computer.id"))
					.withName(result.getString("computer.name")).withCompany(company).withIntroduced(introducedLocal)
					.withDiscontinued(discontinuedLocal).build();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return computer;
	}

	public ComputerDto computerToDto(Computer computer) {
		ComputerDto dto = new ComputerDto.ComputerDtoBuilder().withId(computer.getId()).withName(computer.getName())
				.withDiscontinued(dateMapper.dateToString(computer.getDiscontinued()))
				.withIntroduced(dateMapper.dateToString(computer.getIntroduced()))
				.withCompanyId(computer.getCompany()==null? 0:computer.getCompany().getId())
				.build();
		return dto;
	}

	public Computer dtoToComputer(ComputerDto dto) throws BadEntriException {

		LocalDate introduced = dateMapper.StringToDate(dto.getIntroduced());
		LocalDate discontinued = dateMapper.StringToDate(dto.getDiscontinued());

		Computer computer = new Computer.ComputerBuilder()
				.withId(dto.getId())
				.withName(dto.getName())
				.withIntroduced(introduced)
				.withDiscontinued(discontinued)
				.withCompany(dto.getCompanyId() == 0 ? null: new Company.CompanyBuilder().withId(dto.getCompanyId()).build())
				.build();

		validator.checkComputer(computer);

		return computer;
	}
}
