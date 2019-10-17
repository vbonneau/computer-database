package main.java.com.excilys.computerDatabase.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import main.java.com.excilys.computerDatabase.entity.Company;
import main.java.com.excilys.computerDatabase.entity.Computer;

public class ComputerMapper {
	
	private static ComputerMapper instence;
	
	private ComputerMapper() {
		
	}
	
	public static ComputerMapper getInstence() {
		if(instence == null) { instence = new ComputerMapper(); }
		return instence;
	}

	public Computer ResultSetToComputer(ResultSet result) {
		Company company = new Company();
		Computer computer = new Computer();
		try {
			company = new Company.CompanyBuilder().withId(result.getInt("company.id")).withName(result.getString("company.name")).build();
			
			Timestamp introduced = result.getTimestamp("computer.introduced");
			LocalDateTime introducedLocal;
			if(introduced == null) {introducedLocal = null;}
			else {introducedLocal = introduced.toLocalDateTime();}
			
			Timestamp discontinued = result.getTimestamp("computer.discontinued");
			LocalDateTime discontinuedLocal;
			if(discontinued == null) {discontinuedLocal = null;}
			else {discontinuedLocal = discontinued.toLocalDateTime();}
			
			computer = new Computer.ComputerBuilder().withId(result.getInt("computer.id"))
					.withName(result.getString("computer.name")).withCompany(company)
					.withIntroduced(introducedLocal).withDiscontinued(discontinuedLocal).build();
			
			
		} catch (SQLException e) {
			if(e.getErrorCode() != 0){ System.out.println(e.getMessage()); };
		}
		return computer;
	}
	
	
	
}
