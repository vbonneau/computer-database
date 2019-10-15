package main.resources.com.excilys.computerDatabase.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import main.resources.com.excilys.computerDatabase.entity.Company;
import main.resources.com.excilys.computerDatabase.entity.Computer;

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
			
			
			computer = new Computer.ComputerBuilder().withId(result.getInt("computer.id"))
					.withName(result.getString("computer.name")).withCompany(company).build();
			Timestamp introduced = result.getTimestamp("computer.introduced");
			
			if(introduced == null) {computer.setIntroduced(null);}
			else {computer.setIntroduced(introduced.toLocalDateTime());}
			Timestamp discontinued = result.getTimestamp("computer.discontinued");
			if(discontinued == null) {computer.setDiscontinued(null);}
			else {computer.setDiscontinued(discontinued.toLocalDateTime());}
			
		} catch (SQLException e) {
			if(e.getErrorCode() != 0){ System.out.println(e.getMessage()); };
		}
		return computer;
	}
	
	
	
}
