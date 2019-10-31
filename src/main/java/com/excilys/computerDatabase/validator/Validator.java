package com.excilys.computerDatabase.validator;

import java.time.LocalDate;

import com.excilys.computerDatabase.exception.BadEntriException;

public class Validator {
	private static  Validator instence;
	
	private Validator() {
		
	}
	
	public static Validator getInstence() {
		if(instence == null) {
			instence = new Validator();
		}
		
		return instence;
	}
	
	public boolean chekValidDate(LocalDate date) {
		if(date !=null && (date.compareTo(LocalDate.parse("1970-01-01"))<0 || date.compareTo(LocalDate.parse("2038-01-19"))>0)) {
			return false;
		}
		return true;
	}
	
	public boolean checkName(String name) throws BadEntriException {
		if(name == null || name == "") {
			throw new BadEntriException("the name cannot be null");
		}
		return true;
	}
	
	public boolean checkTowDate(LocalDate introduced,LocalDate discontinued) throws BadEntriException {
		if (introduced == null || discontinued == null) {
			return true;
		}
		if (introduced.compareTo(discontinued) <= 0) {
			return true;
		} else {
			throw new BadEntriException("date not valid, the introduce date must be inferior to the discontinues date");
		}
	}
}
