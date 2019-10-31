package com.excilys.computerDatabase.dto;


public class ComputerDto {

	private int id;
	private String name;
	private String introduced;
	private String discontinued;
	private String companyName;
	private int companyId;

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
	public String getCompanyName() {
		return companyName;
	}
	public int getCompanyId() {
		return companyId;
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
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	@Override
	public String toString() {
		return "ComputerDto [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued="
				+ discontinued + ", companyName=" + companyName + ", companyId=" + companyId + "]";
	}
	
	public static class ComputerDtoBuilder {
		private int id = 0;
		private String name = "";
		private String introduced = "";
		private String discontinued = "";
		private String companyName = "";
		private int companyId = 0;

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

		public ComputerDtoBuilder withCompanyId(int companyId) {
			this.companyId = companyId;
			return this;
		}
		
		public ComputerDtoBuilder withCompanyName(String companyName) {
			this.companyName = companyName;
			return this;
		}

		public ComputerDto build() {
			ComputerDto computerDto = new ComputerDto();
			computerDto.setId(id);
			computerDto.setName(name);
			computerDto.setIntroduced(introduced);
			computerDto.setDiscontinued(discontinued);
			computerDto.setCompanyId(companyId);
			computerDto.setCompanyName(companyName);

			return computerDto;
		}
	}

}
