package com.exilys.computerDatabase.entity;

import java.text.SimpleDateFormat;
//import java.sql.Timestamp;
import java.util.Date;

public class Computer {
	
	private int id;
	private String name;
	private Date introduced;
	private Date discontinued;
	private Company company;
	
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

	public Date getIntroduced() {
		return introduced;
	}

	public void setIntroduced(Date introduced) {
		this.introduced = introduced;
	}

	public Date getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(Date discontinued) {
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
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		String introducedFormat = null;
		String discontinuedFormat = null;
		if(introduced != null) {
			introducedFormat = format.format(introduced);
		}
		if(discontinued != null) {
			discontinuedFormat = format.format(discontinued);
		}
		return "Computer [id=" + id + ", name=" + name + ", introduced=" + introducedFormat +
				", discontinued=" + discontinuedFormat + ", Company=" + company.getName() + "]";
	}
	
}
