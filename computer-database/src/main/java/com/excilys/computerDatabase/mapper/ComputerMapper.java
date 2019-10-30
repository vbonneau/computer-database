package com.excilys.computerDatabase.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.computerDatabase.dto.ComputerDto;
import com.excilys.computerDatabase.entity.Company;
import com.excilys.computerDatabase.entity.Computer;
import com.excilys.computerDatabase.exception.BadEntriException;
import com.excilys.computerDatabase.validator.Validator;

@Component
public class ComputerMapper {


	@Autowired
	DateMapper dateMapper;
	private ComputerMapper() {

	}

	public Computer resultSetToComputer(ResultSet result) {
		Company company = new Company();
		Computer computer = new Computer();
		try {
			company = new Company.CompanyBuilder().withId(result.getInt("company.id")).withName(result.getString("company.name")).build();

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
					.withName(result.getString("computer.name")).withCompany(company)
					.withIntroduced(introducedLocal).withDiscontinued(discontinuedLocal).build();

		} catch (SQLException e) {
				System.out.println(e.getMessage());
		}
		return computer;
	}
	
	public ComputerDto computerToDto(Computer computer) {
		ComputerDto dto = new ComputerDto.ComputerDtoBuilder()
				.withId(computer.getId())
				.withName(computer.getName())
				.withDiscontinued(dateMapper.dateToString(computer.getDiscontinued()))
				.withIntroduced(dateMapper.dateToString(computer.getIntroduced()))
				.withCompanyId(computer.getCompany().getId())
				.withCompanyName(computer.getCompany().getName())
				.build();
		return dto;
	}
	
	public Computer dtoToComputer(ComputerDto dto) throws BadEntriException {
		Validator validator = Validator.getInstence();
		LocalDate introduced = dateMapper.StringToDate(dto.getIntroduced());
		LocalDate discontinued = dateMapper.StringToDate(dto.getDiscontinued());
		
		validator.checkName(dto.getName());
		validator.chekValidDate(introduced);
		validator.chekValidDate(discontinued);
		validator.checkTowDate(introduced, discontinued);
		
		Company company = new Company.CompanyBuilder()
				.withId(dto.getCompanyId())
				.withName(dto.getCompanyName())
				.build();
		Computer computer = new Computer.ComputerBuilder()
				.withId(dto.getId())
				.withName(dto.getName())
				.withIntroduced(introduced)
				.withDiscontinued(discontinued)
				.withCompany(company)
				.build();
		
		return computer;
	}
}
