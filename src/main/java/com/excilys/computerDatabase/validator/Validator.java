package com.excilys.computerDatabase.validator;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.excilys.computerDatabase.entity.Computer;
import com.excilys.computerDatabase.exception.BadEntriException;

@Component
public class Validator {
	
	private void chekValidDate(LocalDate date) throws BadEntriException {
		if(date !=null && (date.compareTo(LocalDate.parse("1970-01-01"))<0 || date.compareTo(LocalDate.parse("2038-01-19"))>0)) {
			throw new BadEntriException("the date must be beetewn 1970-01-01 and 2038-01-19 ");
		}
	}
	
	private boolean checkName(String name) throws BadEntriException {
		if(name == null || name == "") {
			throw new BadEntriException("the name cannot be null");
		}
		return true;
	}
	
	private boolean checkTowDate(LocalDate introduced,LocalDate discontinued) throws BadEntriException {
		if (introduced == null || discontinued == null) {
			return true;
		}
		if (introduced.compareTo(discontinued) <= 0) {
			return true;
		} else {
			throw new BadEntriException("date not valid, the introduce date must be inferior to the discontinues date");
		}
	}

	public void checkComputer(Computer computer) throws BadEntriException {
		checkName(computer.getName());
		chekValidDate(computer.getIntroduced());
		chekValidDate(computer.getDiscontinued());
		checkTowDate(computer.getIntroduced(), computer.getDiscontinued());
		
	}
}
