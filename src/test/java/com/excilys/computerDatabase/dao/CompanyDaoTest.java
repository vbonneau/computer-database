package com.excilys.computerDatabase.dao;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import junit.framework.TestCase;
import com.excilys.computerDatabase.dao.CompanyDao;
import com.excilys.computerDatabase.entity.Company;



public class CompanyDaoTest extends TestCase {
	@Autowired
	CompanyDao dao;

	@Before
	public void setUp() {
	}

	@Test
	public void testFindAll() {

		ArrayList<Company> list = new ArrayList<Company>();
		Company company = new Company.CompanyBuilder().withId(1).withName("Apple Inc.").build();
		list.add(company);
		company = new Company.CompanyBuilder().withId(2).withName("Thinking Machines").build();
		list.add(company);
		company = new Company.CompanyBuilder().withId(3).withName("RCA").build();
		list.add(company);
		company = new Company.CompanyBuilder().withId(4).withName("Netronics").build();
		list.add(company);

		assertEquals(list, dao.findAll());
	}

	@Test
	public void testFindPage() {

		ArrayList<Company> list = new ArrayList<Company>();
		Company company = new Company.CompanyBuilder().withId(2).withName("Thinking Machines").build();
		list.add(company);
		company = new Company.CompanyBuilder().withId(3).withName("RCA").build();
		list.add(company);

		assertEquals(list, dao.findPage(2, 1));
	}

	@Test
	public void testCountCompany() {

		assertEquals(4, dao.countCompany());
	}

	@Test
	public void testFindOneByName() {
		Company company = new Company.CompanyBuilder().withId(2).withName("Thinking Machines").build();
		assertEquals(company, dao.findOneByName("Thinking Machines"));
	}

	@Test
	public void testFindOneByNameCompanyNotExist() {
		Company company = new Company();
		assertEquals(company, dao.findOneByName("enqgjkle"));
	}

}
