package com.exilys.computerDatabase.entity;

public class Company {
	
	private int id;
	private String name;
	
	public Company() {
		
	}
	
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

	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + "]";
	}
	
	public static class CompanyBuilder{
		private int id;
		private String name;
		
		public CompanyBuilder withId(int id) {
			this.id = id;
			return this;
		}
		
		public CompanyBuilder withName(String name) {
			this.name = name;
			return this;
		}
		
		public Company build() {
			Company company = new Company();
			company.setId(id);
			company.setName(name);
			return company;
		}
	}

}
