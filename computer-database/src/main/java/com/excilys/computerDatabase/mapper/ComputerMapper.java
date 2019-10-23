package main.java.com.excilys.computerDatabase.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import main.java.com.excilys.computerDatabase.dto.ComputerDto;
import main.java.com.excilys.computerDatabase.entity.Company;
import main.java.com.excilys.computerDatabase.entity.Computer;

public class ComputerMapper {

	private static ComputerMapper instence;

	private ComputerMapper() {

	}

	public static ComputerMapper getInstence() {
		if (instence == null) {
			instence = new ComputerMapper();
		}
		return instence;
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
			if (e.getErrorCode() != 0) {
				System.out.println(e.getMessage());
			}
		}
		return computer;
	}
	
	public ComputerDto computerToDto(Computer computer) {
		DateMapper dateMapper = DateMapper.getInstence();
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
	
	public Computer dtoToComputer(ComputerDto dto) {
		DateMapper dateMapper = DateMapper.getInstence();
		Company company = new Company.CompanyBuilder()
				.withId(dto.getCompanyId())
				.withName(dto.getCompanyName())
				.build();
		Computer computer = new Computer.ComputerBuilder()
				.withId(dto.getId())
				.withName(dto.getName())
				.withIntroduced(dateMapper.StringToDate(dto.getIntroduced()))
				.withDiscontinued(dateMapper.StringToDate(dto.getDiscontinued()))
				.withCompany(company)
				.build();
		
		return computer;
	}
}
