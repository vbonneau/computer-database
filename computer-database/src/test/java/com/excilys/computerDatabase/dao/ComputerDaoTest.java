package test.java.com.excilys.computerDatabase.dao;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import main.java.com.excilys.computerDatabase.dao.ComputerDao;
import main.java.com.excilys.computerDatabase.dao.ConnectionMySQL;
import main.java.com.excilys.computerDatabase.entity.Company;
import main.java.com.excilys.computerDatabase.entity.Computer;

public class ComputerDaoTest extends TestCase {

	private ConnectionMySQL connectionMySQL;

	@Before
	public void setUp() {
		connectionMySQL = ConnectionMySQL.getInstence();
		connectionMySQL.useTestDatabase();
	}

	@Test
	public void testFindAll() {
		ComputerDao dao = ComputerDao.getComputerDao();

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
		ComputerDao dao = ComputerDao.getComputerDao();
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

		assertEquals(list, dao.findPage(2, 1));
	}

	@Test
	public void testFindOneById() {
		ComputerDao dao = ComputerDao.getComputerDao();
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
		ComputerDao dao = ComputerDao.getComputerDao();
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
		ComputerDao dao = ComputerDao.getComputerDao();
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
		ComputerDao dao = ComputerDao.getComputerDao();
		assertEquals(true,dao.deleteComputer(2));
	}

	@Test
	public void testCountComputer() {
		ComputerDao dao = ComputerDao.getComputerDao();
		assertEquals(4,dao.countComputer());
	}

}
