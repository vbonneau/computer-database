package com.exilys.computerDatabase.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import com.exilys.computerDatabase.dao.ComputerDao;
import com.exilys.computerDatabase.entity.Company;
import com.exilys.computerDatabase.entity.Computer;

public class ComputerController {

	public static void displayAll() throws ClassNotFoundException {
		
		ArrayList<Computer> listComputer = ComputerDao.findAll();
		
		for(Computer computer : listComputer) {
			//System.out.println(computer.getName());
				System.out.println(computer.toString());
		}
	}
	
	public static void displayPage() throws ClassNotFoundException {
		int limit = 10;
		int offset = 0;
		int nbComputer;
		int nbPage;
		int actualPage = 1;
		int newPage = 1;
		String command;
		Scanner sc = new Scanner(System.in);
		nbComputer = ComputerDao.countComputer();
		nbPage = (nbComputer+limit-1)/limit;
		
		do {
			offset = (actualPage-1)*limit;
			ArrayList<Computer> listComputer = ComputerDao.findAll(limit,offset);
			for(Computer computer : listComputer) {
				System.out.println(computer.toString());
			}
			System.out.println("page "+actualPage+"/"+nbPage);
			System.out.println("veuiller entrer le numero de la page,+ ou - pour changer de page,-1 poir quitter");
			
			if(sc.hasNextInt()) {
				newPage = sc.nextInt();
				
			}else {
				command = sc.nextLine();
				System.out.println(command);
				switch(command) {
					case "+" :
						newPage = actualPage+1;
						break;
					case "-":
						newPage = actualPage-1;
						break;
					default :
						System.out.println("commande invalid");
				}
			}
			
			if(newPage >= 1 && newPage <= nbPage) {
				actualPage = newPage;
			}else {
				System.out.println("cette page n'existe pas");
			}
		
		}while(newPage != -1);
		
	}
	
	public static void displayOne() {
		Scanner sc = new Scanner(System.in);
		int id;
		System.out.println("veillez entre l'id de l'ordinateur");
		if(sc.hasNextInt()) {
			id = sc.nextInt();
			Computer computer = ComputerDao.findOneById(id);
			System.out.println(computer.toString());
		}
	}
	
	public static void creatComputer() {
		Computer computer = new Computer();
		Company company = new Company();
		Date introduced = null;
		Date discontinued = null;
		boolean dateIncoerent = false;
		String dateString;
		String name;
		String nameCompany;
		Scanner sc = new Scanner(System.in);
		
		do {
		System.out.println("veillez entrer le nom de l'ordinateur");
		name = sc.nextLine();
		}while(name == "");
		computer.setName(name);
		System.out.println("name = "+name);
		
		do {
			do {
				System.out.println("veillez entrer la date à laquelle l'ordinaiteur à été acheté (format jour/moi/année)");
				dateString = sc.nextLine();
				introduced = crateDate(dateString);
			
			}while(!dateString.equals("") && introduced == null);
			System.out.println("introduced : "+introduced);
			
			do {
				System.out.println("veillez entrer la date à laquelle l'ordinaiteur à été retirer (format jour/moi/année)");
				dateString = sc.nextLine();
				discontinued = crateDate(dateString);
			
			}while(!dateString.equals("") && discontinued == null);
			System.out.println("discontibued : "+discontinued);
			
			if(discontinued != null && introduced != null) {
				if(introduced.compareTo(discontinued) > 0) {
					dateIncoerent = true;
				}else {
					dateIncoerent = false;
				}
			}
		}while(dateIncoerent);
		computer.setIntroduced(introduced);
		computer.setDiscontinued(discontinued);
		
		System.out.println("veillez entrer le nom de la compagni qui a crée l'orsinateur");
		nameCompany = sc.nextLine();
		System.out.println("name : "+nameCompany);
		if(nameCompany != "") {
			company.setName(nameCompany);
		}
		
		computer.setCompany(company);
		System.out.println(computer.toString());
		
		ComputerDao.inserComputer(computer);
	}
	
	public static void updateComputer() {
		Scanner sc = new Scanner(System.in);
		int id;
		Date introduced = null;
		Date discontinued = null;
		boolean dateIncoerent = false;
		String dateEntri;
		String name;
		String nameCompany;
		Computer computer;
		Company company;
		
		System.out.println("veillez entre l'id de l'ordinateur");
		if(sc.hasNextInt()) {
			id = sc.nextInt();
			computer = ComputerDao.findOneById(id);
			company = computer.getCompany();
			System.out.println(computer.toString());
		}else { return; }
		sc.nextLine();
		
		System.out.println("veillez entrer le nom de l'ordinateur");
		name = sc.nextLine();
		if(name == "") {
			name = computer.getName();
		}
		System.out.println(name);
		
		do {
			do {
				System.out.println("veillez entrer la date à laquelle l'ordinaiteur à été acheté (format jour/moi/année)");
				dateEntri = sc.nextLine();
				introduced = crateDate(dateEntri);
			
			}while(!dateEntri.equals("") && introduced == null);
			System.out.println("introduced : "+introduced);
			
			do {
				System.out.println("veillez entrer la date à laquelle l'ordinaiteur à été retirer (format jour/moi/année)");
				dateEntri = sc.nextLine();
				discontinued = crateDate(dateEntri);
			
			}while(!dateEntri.equals("") && discontinued == null);
			System.out.println("discontibued : "+discontinued);
			
			if(discontinued != null && introduced != null) {
				if(introduced.compareTo(discontinued) > 0) {
					dateIncoerent = true;
				}else {
					dateIncoerent = false;
				}
			}
		}while(dateIncoerent);
		computer.setIntroduced(introduced);
		computer.setDiscontinued(discontinued);
		
		System.out.println("veillez entrer le nom de la compagni qui a crée l'orsinateur");
		nameCompany = sc.nextLine();
		System.out.println("name : "+nameCompany);
		if(name != "") {
			company.setName(nameCompany);
		}
		computer.setCompany(company);
		
		ComputerDao.updateComputer(computer);
	}
	
	public static void deleteComputer() {
		Scanner sc = new Scanner(System.in);
		int id;
		System.out.println("veillez entre l'id de l'ordinateur");
		if(sc.hasNextInt()) {
			id = sc.nextInt();
			ComputerDao.delete(id);
		}
	}
	
	private static Date crateDate(String dateString) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			Date date;
			try {
				date = format.parse(dateString);
				System.out.println(format.format(date));
				if(!format.format(date).equals(dateString)) {
					return null;
				}
				return date;
			} catch (ParseException e) {
				return null;
			}
	}
}
