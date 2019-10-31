package com.excilys.computerDatabase.dao;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.computerDatabase.entity.Company;
import com.excilys.computerDatabase.entity.Computer;

import junit.framework.TestCase;

//import org.springframework.test.context.ContextConfiguration;

//@RunWith( SpringJUnit4ClassRunner.class )
//@ContextConfiguration
public class ComputerDaoTest extends TestCase {

	@Autowired
	ComputerDao dao;

	@Before
	public void setUp() {

	}

	@Test
	public void testFindAll() {
		
		ArrayList<Computer> list = new ArrayList<Computer>();
		Computer computer = new Computer.ComputerBuilder()
				.withId(1)
				.withName("MacBook Pro 15.4 inch")
				.build();
		list.add(computer);
		computer = new Computer.ComputerBuilder()
				.withId(2)
				.withName("CM-5")
				.withIntroduced(LocalDate.parse("1991-01-01"))
				.withCompany(new Company
						.CompanyBuilder()
						.withId(2)
						.withName("Thinking Machines")
						.build())
				.build();
		list.add(computer);
		computer = new Computer.ComputerBuilder()
				.withId(3)
				.withName("Apple III")
				.withIntroduced(LocalDate.parse("1980-05-01"))
				.withDiscontinued(LocalDate.parse("1984-04-01"))
				.withCompany(new Company
						.CompanyBuilder()
						.withId(1)
						.withName("Apple Inc.")
						.build())
				.build();
		list.add(computer);
		computer = new Computer.ComputerBuilder()
				.withId(6)
				.withName("MacBook Pro")
				.withDiscontinued(LocalDate.parse("2006-01-10"))
				.withCompany(new Company
						.CompanyBuilder()
						.withId(1)
						.withName("Apple Inc.")
						.build())
				.build();
		list.add(computer);

		assertEquals(list, dao.findAll());
	}

	@Test
	public void testFindPage() {
		ArrayList<Computer> list = new ArrayList<Computer>();
		Computer computer = new Computer.ComputerBuilder()
				.withId(2)
				.withName("CM-5")
				.withIntroduced(LocalDate.parse("1991-01-01"))
				.withCompany(new Company
						.CompanyBuilder()
						.withId(2)
						.withName("Thinking Machines")
						.build())
				.build();
		list.add(computer);
		computer = new Computer.ComputerBuilder()
				.withId(3)
				.withName("Apple III")
				.withIntroduced(LocalDate.parse("1980-05-01"))
				.withDiscontinued(LocalDate.parse("1984-04-01"))
				.withCompany(new Company
						.CompanyBuilder()
						.withId(1)
						.withName("Apple Inc.").build())
				.build();
		list.add(computer);

		assertEquals(list, dao.findPage(2, 1,"","id",true));
	}

	@Test
	public void testFindOneById() {
		Computer computer = new Computer.ComputerBuilder()
				.withId(2)
				.withName("CM-5")
				.withIntroduced(LocalDate.parse("1991-01-01"))
				.withCompany(new Company
						.CompanyBuilder()
						.withId(2)
						.withName("Thinking Machines")
						.build())
				.build();
		assertEquals(computer, dao.findOneById(2));
	}

	@Test
	public void testInserComputer() {
		Computer computer = new Computer.ComputerBuilder()
				.withName("CM-5")
				.withIntroduced(LocalDate.parse("1991-01-01"))
				.withCompany(new Company
						.CompanyBuilder()
						.withId(2)
						.build())
				.build();
		assertEquals(true,dao.inserComputer(computer));
	}

	@Test
	public void testUpdateComputer() {
		Computer computer = new Computer.ComputerBuilder()
				.withId(2)
				.withName("CM-5")
				.withIntroduced(LocalDate.parse("1991-01-01"))
				.withCompany(new Company
						.CompanyBuilder()
						.withId(2)
						.withName("Thinking Machines")
						.build())
				.build();
		assertEquals(true,dao.updateComputer(computer));
	}

	@Test
	public void testDelete() {
		assertEquals(true,dao.deleteComputer(2));
	}

	@Test
	public void testCountComputer() {
		assertEquals(4,dao.countComputer());
	}

}
