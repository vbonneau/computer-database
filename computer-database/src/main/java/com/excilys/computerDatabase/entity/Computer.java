package com.excilys.computerDatabase.entity;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.computerDatabase.mapper.DateMapper;

public class Computer {

	private int id;
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;
	@Autowired
	private Company company = new Company();
	@Autowired
	DateMapper dateMapper;

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

	public LocalDate getIntroduced() {
		return introduced;
	}

	public void setIntroduced(LocalDate introduced) {
		this.introduced = introduced;
	}

	public LocalDate getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(LocalDate discontinued) {
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

		return "Computer [id=" + id + ", name=" + name + ", introduced=" + dateMapper.dateToString(introduced) +
				", discontinued=" + dateMapper.dateToString(discontinued) + ", Company=" + company.toString() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
		result = prime * result + id;
		result = prime * result + ((introduced == null) ? 0 : introduced.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Computer other = (Computer) obj;
		if (company == null) {
			if (other.company != null) {
				return false;
			}
		} else if (!company.equals(other.company)) {
			return false;
		}
		if (discontinued == null) {
			if (other.discontinued != null) {
				return false;
			}
		} else if (!discontinued.equals(other.discontinued)) {
			return false;
		}
		if (id != other.id) {
			return false;
		}
		if (introduced == null) {
			if (other.introduced != null) {
				return false;
			}
		} else if (!introduced.equals(other.introduced)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

	public static class ComputerBuilder {
		private int id;
		private String name;
		private LocalDate introduced;
		private LocalDate discontinued;
		private Company company = new Company();

		public ComputerBuilder withId(int id) {
			this.id = id;
			return this;
		}

		public ComputerBuilder withName(String name) {
			this.name = name;
			return this;
		}

		public ComputerBuilder withIntroduced(LocalDate introduced) {
			this.introduced = introduced;
			return this;
		}

		public ComputerBuilder withDiscontinued(LocalDate discontinued) {
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
