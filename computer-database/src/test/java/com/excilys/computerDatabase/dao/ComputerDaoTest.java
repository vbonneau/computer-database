package test.java.com.excilys.computerDatabase.dao;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import main.java.com.excilys.computerDatabase.dao.ComputerDao;
import main.java.com.excilys.computerDatabase.dao.ConnectionMySQL;
import main.java.com.excilys.computerDatabase.entity.Company;
import main.java.com.excilys.computerDatabase.entity.Computer;

public class ComputerDaoTest {
	
	ConnectionMySQL connectionMySQL;
	
	@Before
	public void setUp(){
		connectionMySQL = ConnectionMySQL.getInstence();
		connectionMySQL.useTestDatabase();
	}

	@Test
	public void testFindAll() {
		ComputerDao dao = ComputerDao.getComputerDao();
		
		ArrayList<Computer> list = new ArrayList<Computer>();
		Computer computer = new Computer.ComputerBuilder().withId(1).withName("MacBook Pro 15.4 inch").build();
		list.add(computer);
		computer = new Computer.ComputerBuilder().withId(2).withName("CM-5").withIntroduced(LocalDateTime.parse("1991-01-01T00:00"))
				.withCompany(new Company.CompanyBuilder().withId(2).withName("Thinking Machines").build()).build();
		list.add(computer);
		computer = new Computer.ComputerBuilder().withId(3).withName("Apple III")
				.withIntroduced(LocalDateTime.parse("1980-05-01T00:00")).withDiscontinued(LocalDateTime.parse("1984-04-01T00:00"))
				.withCompany(new Company.CompanyBuilder().withId(1).withName("Apple Inc.").build()).build();
		list.add(computer);
		computer = new Computer.ComputerBuilder().withId(6).withName("MacBook Pro").withDiscontinued(LocalDateTime.parse("2006-01-10T00:00"))
				.withCompany(new Company.CompanyBuilder().withId(1).withName("Apple Inc.").build()).build();
		list.add(computer);

		assertEquals(list,dao.findAll());
		
	}

	@Test
	public void testFindPage() {
		ComputerDao dao = ComputerDao.getComputerDao();
		ArrayList<Computer> list = new ArrayList<Computer>();
		Computer computer = new Computer.ComputerBuilder().withId(2).withName("CM-5").withIntroduced(LocalDateTime.parse("1991-01-01T00:00"))
				.withCompany(new Company.CompanyBuilder().withId(2).withName("Thinking Machines").build()).build();
		list.add(computer);
		computer = new Computer.ComputerBuilder().withId(3).withName("Apple III")
				.withIntroduced(LocalDateTime.parse("1980-05-01T00:00")).withDiscontinued(LocalDateTime.parse("1984-04-01T00:00"))
				.withCompany(new Company.CompanyBuilder().withId(1).withName("Apple Inc.").build()).build();
		list.add(computer);
		
		assertEquals(list,dao.findPage(2,1));
	}

	@Test
	public void testFindOneById() {
		ComputerDao dao = ComputerDao.getComputerDao();
		Computer computer = new Computer.ComputerBuilder().withId(2).withName("CM-5").withIntroduced(LocalDateTime.parse("1991-01-01T00:00"))
				.withCompany(new Company.CompanyBuilder().withId(2).withName("Thinking Machines").build()).build();
		assertEquals(computer,dao.findOneById(2));
	}

	@Test
	public void testInserComputer() {

	}

	@Test
	public void testUpdateComputer() {
		fail("Not yet implemented");
	}

	@Test
	public void testDelete() {
		fail("Not yet implemented");
	}

	@Test
	public void testCountComputer() {
		fail("Not yet implemented");
	}

}
