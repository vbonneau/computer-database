package main.resources.com.excilys.computerDatabase.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Computer {
	
	private int id;
	private String name;
	private LocalDateTime introduced;
	private LocalDateTime discontinued;
	private Company company=null;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDateTime getIntroduced() {
		return introduced;
	}

	public void setIntroduced(LocalDateTime introduced) {
		this.introduced = introduced;
	}

	public LocalDateTime getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(LocalDateTime discontinued) {
		this.discontinued = discontinued;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@Override
	public String toString() {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String introducedString=introduced==null ? null: introduced.format(format);
		String discontinuedString=discontinued==null ? null: discontinued.format(format);
		String companyName= company==null ? null: company.getName();
		
		return "Computer [id=" + id + ", name=" + name + ", introduced=" + introducedString +
				", discontinued=" + discontinuedString + ", Company=" + companyName + "]";
	}
	
	public static class ComputerBuilder{
		private int id;
		private String name;
		private LocalDateTime introduced;
		private LocalDateTime discontinued;
		private Company company=null;
		
		public ComputerBuilder withId(int id) {
			this.id = id;
			return this;
		}
		
		public ComputerBuilder withName(String name) {
			this.name = name;
			return this;
		}
		
		public ComputerBuilder withIntroduced(LocalDateTime introduced) {
			this.introduced = introduced;
			return this;
		}
		
		public ComputerBuilder withDiscontinued(LocalDateTime discontinued) {
			this.discontinued = discontinued;
			return this;
		}
		
		public ComputerBuilder withCompany(Company company) {
			this.company = company;
			return this;
		}
		
		public Computer build() {
			Computer computer = new Computer();
			computer.setId(id);
			computer.setName(name);
			computer.setIntroduced(introduced);
			computer.setDiscontinued(discontinued);
			computer.setCompany(company);
			
			return computer;
		}
	}
	
}
