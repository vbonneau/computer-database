package com.exilys.computerDatabase.main;
import java.util.Scanner;

import com.exilys.computerDatabase.controller.CompanyController;
import com.exilys.computerDatabase.controller.ComputerController;

public class Main {

	public static void main(String[] args) throws ClassNotFoundException {
		// TODO Auto-generated method stub
		String command = "";
		boolean testContinue = true;
		Scanner sc = new Scanner(System.in);
		
		while(testContinue) {
			
			System.out.println("1 - afficher la liste des ordinateur");
			System.out.println("2 - afficher la liste des compagni");
			System.out.println("3 - afficher un ordinateur (en utilisant sont id)");
			System.out.println("4 - ajouter un ordinateur a la base de donnée");
			System.out.println("5 - modifié les donnée d'un ordinateur");
			System.out.println("6 - suprimer un ordinateur de la base de donnée");
			System.out.println("0 - quitter");
			
			command = sc.nextLine();
			switch(command) {
			case "1":
			case "List computers":
				ComputerController.displayPage();
				break;
			case "2":
			case "List companies":
				CompanyController.displayAll();
				break;
			case "3":
			case "Show computer details":
				ComputerController.displayOne();
				break;
			case "4":
			case "Create a computer":
				ComputerController.creatComputer();
				break;
			case "5":
			case "Update a computer":
				ComputerController.updateComputer();
				break;
			case "6":
			case "Delete a computer":
				ComputerController.deleteComputer();
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

}
