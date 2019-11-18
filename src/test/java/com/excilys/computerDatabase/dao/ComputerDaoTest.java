package com.excilys.computerDatabase.dao;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.computerDatabase.entity.Company;
import com.excilys.computerDatabase.entity.Computer;
//import com.excilys.computerDatabase.configuration.SpringConfiguration;
import com.excilys.computerDatabase.dao.ComputerDao;

import junit.framework.TestCase;

@RunWith( SpringJUnit4ClassRunner.class )
//@ContextConfiguration(classes= {SpringConfiguration.class})
public class ComputerDaoTest extends TestCase {

	@Autowired
	ComputerDao dao;

	@Before
	public void setUp() {

	}

	// @Test
	public void testFindPage() {
		System.out.println(2);
		ArrayList<Computer> list = new ArrayList<Computer>();
		Computer computer = new Computer.ComputerBuilder().withId(2).withName("CM-5")
				.withIntroduced(LocalDate.parse("1991-01-01")).withDiscontinued(null)
				.withCompany(new Company.CompanyBuilder().withId(2).withName("Thinking Machines").build()).build();
		list.add(computer);
		computer = new Computer.ComputerBuilder().withId(3).withName("Apple III")
				.withIntroduced(LocalDate.parse("1980-05-01")).withDiscontinued(LocalDate.parse("1984-04-01"))
				.withCompany(new Company.CompanyBuilder().withId(1).withName("Apple Inc.").build()).build();
		list.add(computer);

		assertEquals(list, dao.findPage(2, 1,"","name",false));
	}

	// @Test
	public void testCountComputer() {
		System.out.println(3);
		// assertEquals(4,dao.countComputer(""));
	}

	// @Test
	public void testFindOneById() {
		System.out.println(4);
		Computer computer = new Computer.ComputerBuilder().withId(2).withName("CM-5")
				.withIntroduced(LocalDate.parse("1991-01-01"))
				.withCompany(new Company.CompanyBuilder().withId(2).withName("Thinking Machines").build()).build();
		assertEquals(computer, dao.findOneById(2));
	}

	// @Test
	public void testInserComputer() {
		System.out.println(5);
		Computer computer = new Computer.ComputerBuilder().withId(7).withName("CM-5")
				.withIntroduced(LocalDate.parse("1991-01-01"))
				.withCompany(new Company.CompanyBuilder().withId(2).withName("Thinking Machines").build()).build();
		assertEquals(true,dao.inserComputer(computer));
	}

	// @Test
	public void testUpdateComputer() {
		System.out.println(6);
		Computer computer = new Computer.ComputerBuilder().withId(7).withName("mac 25")
				.withIntroduced(LocalDate.parse("1996-02-27"))
				.withCompany(new Company.CompanyBuilder().withId(1).withName("Apple Inc.").build()).build();
		assertEquals(true,dao.updateComputer(computer));
	}

	@Test
	public void testDelete() {
		System.out.println(7);
		assertEquals(true,dao.deleteComputer(7));
		assert (true);
	}

}
