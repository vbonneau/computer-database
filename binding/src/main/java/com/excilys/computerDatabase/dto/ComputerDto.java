package com.excilys.computerDatabase.dto;

public class ComputerDto {

	private int id;
	private String name;
	private String introduced;
	private String discontinued;
	private CompanyDto company;

	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getIntroduced() {
		return introduced;
	}
	public String getDiscontinued() {
		return discontinued;
	}
	public CompanyDto getCompany() {
		return company;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setIntroduced(String introduced) {
		this.introduced = introduced;
	}
	public void setDiscontinued(String discontinued) {
		this.discontinued = discontinued;
	}
	public void setCompany(CompanyDto company) {
		this.company = company;
	}
	
	@Override
	public String toString() {
		return "ComputerDto [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued="
				+ discontinued + ", company=" + company + "]";
	}

	public static class ComputerDtoBuilder {
		private int id;
		private String name;
		private String introduced;
		private String discontinued;
		private CompanyDto company;

		public ComputerDtoBuilder withId(int id) {
			this.id = id;
			return this;
		}

		public ComputerDtoBuilder withName(String name) {
			this.name = name;
			return this;
		}

		public ComputerDtoBuilder withIntroduced(String introduced) {
			this.introduced = introduced;
			return this;
		}

		public ComputerDtoBuilder withDiscontinued(String discontinued) {
			this.discontinued = discontinued;
			return this;
		}

		public ComputerDtoBuilder withCompanyId(CompanyDto company) {
			this.company = company;
			return this;
		}
		

		public ComputerDto build() {
			ComputerDto computerDto = new ComputerDto();
			computerDto.setId(id);
			computerDto.setName(name);
			computerDto.setIntroduced(introduced);
			computerDto.setDiscontinued(discontinued);
			computerDto.setCompany(company);

			return computerDto;
		}
	}

}
