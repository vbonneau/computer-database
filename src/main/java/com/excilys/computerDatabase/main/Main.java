package com.excilys.computerDatabase.main;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.excilys.computerDatabase.configuration.SpringConfigurationCli;
import com.excilys.computerDatabase.entity.Company;
import com.excilys.computerDatabase.entity.Computer;
import com.excilys.computerDatabase.exception.BadEntriException;
import com.excilys.computerDatabase.mapper.DateMapper;
import com.excilys.computerDatabase.page.Page;
import com.excilys.computerDatabase.service.CompanyService;
import com.excilys.computerDatabase.service.ComputerService;

@Component
public class Main {

	private static Scanner sc = new Scanner(System.in);
	private static int limit = 10;
	private static int offset = 0;
	private static long nbPage;
	private static int actualPage = 1;
	private static String command;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private ComputerService computerService;
	@Autowired
	private DateMapper dateMapper;

	public static void main(String[] args) throws ClassNotFoundException {
		boolean testContinue = true;
		System.out.println("main");
		ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfigurationCli.class);

		Main main = ctx.getBean(Main.class);

		while (testContinue) {

			System.out.println("1 - afficher la liste des ordinateur");
			System.out.println("2 - afficher la liste des compagni");
			System.out.println("3 - afficher un ordinateur (en utilisant sont id)");
			System.out.println("4 - ajouter un ordinateur a la base de donnée");
			System.out.println("5 - modifié les donnée d'un ordinateur");
			System.out.println("6 - suprimer un ordinateur de la base de donnée");
			System.out.println("7 - suprimer une compagnie de la base de donnée");
			System.out.println("0 - quitter");

			command = sc.nextLine();
			switch (command) {
			case "1":
			case "List computers":
				main.listComputer();
				break;
			case "2":
			case "List companies":
				main.listCompany();
				break;
			case "3":
			case "Show computer details":
				main.displayOneComputer();
				break;
			case "4":
			case "Create a computer":
				main.creatComputer();
				break;
			case "5":
			case "Update a computer":
				main.updateComputer();
				break;
			case "6":
			case "Delete a computer":
				main.deleteComputer();
				break;
			case "7":
			case "Delete a company":
				main.deleteCompany();
				break;
			case "0":
			case "quit":
				testContinue = false;
				break;
			default:

			}
		}
		sc.close();
		System.out.println("good bye");

	}

	private void deleteCompany() {
		int id = 0;
		System.out.println("veuillez entrer l'id de la compagnie");
		if (sc.hasNextInt()) {
			id = sc.nextInt();
			sc.nextLine();
		}
		if (id == 0) {
			return;
		}
		companyService.deleteCompany(id);
	}

	private void listComputer() {
		long nbComputer = computerService.count();
		nbPage = (nbComputer + limit - 1) / limit;
		do {
			offset = (actualPage - 1) * limit;
			List<Computer> listComputer = computerService.getPage(limit, offset);
			displaylistComputer(listComputer);
		} while (navigatePage());
	}

	private void listCompany() {
		long nbCompany = companyService.count();
		nbPage = (nbCompany + limit - 1) / limit;
		do {
			offset = (actualPage - 1) * limit;
			List<Company> listCompany = companyService.getPage(limit, offset);
			displaylistCompany(listCompany);
		} while (navigatePage());

	}

	private boolean navigatePage() {
		System.out.println("page " + actualPage + "/" + nbPage);
		System.out.println("veuiller entrer le numero de la page,+ ou - pour changer de page,-1 poir quitter");
		int newPage = actualPage;

		if (sc.hasNextInt()) {
			newPage = sc.nextInt();
		} else {
			command = sc.nextLine();
			System.out.println(command);
			switch (command) {
			case "+":
				newPage = actualPage + 1;
				break;
			case "-":
				newPage = actualPage - 1;
				break;
			default:
				System.out.println("commande invalid");
			}
		}

		if (newPage >= 1 && newPage <= nbPage) {
			actualPage = newPage;
		} else {
			System.out.println("cette page n'existe pas");
		}

		if (newPage == -1) {
			actualPage = 1;
			sc.nextLine();
			return false;
		}
		return true;
	}

	private void displaylistCompany(List<Company> listCompany) {
		for (Company company : listCompany) {
			System.out.println(company.toString());
		}
	}

	private void displaylistComputer(List<Computer> listComputer) {
		for (Computer computer : listComputer) {
			System.out.println(computer.toString());
		}
	}

	private int displayOneComputer() {
		int id = 0;
		Computer computer;
		System.out.println("veuillez entrer l'id de l'ordinateur");
		if (sc.hasNextInt()) {
			id = sc.nextInt();
			sc.nextLine();
		}
		computer = computerService.getOne(id);
		id = computer.getId();
		if (id == 0) {
			System.out.println("l'ordinateur n'as pas été trouvé");
		} else {
			System.out.println(computer.toString());
		}

		return id;
	}

	private void creatComputer() {
		Computer computer = askInfoComputer();
		System.out.println(computer.toString());
		computerService.addCompeuter(computer);
	}

	private void updateComputer() {
		int id = displayOneComputer();
		if (id == 0) {
			return;
		}
		Computer computer = askInfoComputer();
		computer.setId(id);
		System.out.println(computer.toString());
		computerService.updateComputer(computer);
	}

	private void deleteComputer() {
		int id = displayOneComputer();
		if (id == 0) {
			return;
		}
		computerService.deleteComputer(id);
	}

	private Computer askInfoComputer() {
		Computer computer = new Computer();
		LocalDate introduced;
		LocalDate discontinued;
		computer.setName(askComputerName());

		do {
			introduced = askDate("introduit");
			discontinued = askDate("retirer");
		} while (!checkDate(introduced, discontinued));
		computer.setIntroduced(introduced);
		computer.setDiscontinued(discontinued);
		computer.setCompany(askCompany());

		return computer;
	}

	private String askComputerName() {
		String name;
		do {
			System.out.println("veuillez entrer le nom de l'ordinateur");
			name = sc.nextLine();
		} while (name.equals(""));

		return name;
	}

	private LocalDate askDate(String sentence) {
		LocalDate date = null;
		System.out.println(
				"veuillez entrer la date à laquelle l'ordinateur à été " + sentence + " ( format jour/mois/année )");
		String dateString = sc.nextLine();
		try {
			date = dateMapper.StringToDate(dateString);
		} catch (BadEntriException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (date == null) {
			System.out.println("date invalide, aucune date n'a été enregistré");
		}
		return date;
	}

	private static boolean checkDate(LocalDate introduced, LocalDate discontinued) {
		if (introduced == null || discontinued == null) {
			return true;
		}
		if (introduced.compareTo(discontinued) < 0) {
			return true;
		} else {
			System.out.println("les date sont incohérente veuillez les entre a nouveau");
			return false;
		}
	}

	private Company askCompany() {
		Company company = new Company();
		String companyName;
		System.out.println("veuillez entrer le nom de la compagni qui fabriqué l'ordinateur");
		companyName = sc.nextLine();
		company = companyService.getCompany(companyName);
		if (company.getId() == 0) {
			System.out.println(
					"la compagni n'a pas été trouvé dans la base de donnée aucune compagnie n'a été assosié à l'ordinateur");
		}
		return company;
	}
}
