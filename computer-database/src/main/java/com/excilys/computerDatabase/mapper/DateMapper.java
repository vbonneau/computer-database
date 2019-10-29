package com.excilys.computerDatabase.mapper;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.excilys.computerDatabase.exception.BadEntriException;

public class DateMapper {

	private static DateMapper instence; 

	private DateMapper() {

	}

	public static DateMapper getInstence() {
		if (instence == null) {
			instence = new DateMapper();
		}
		return instence;
	}

	public LocalDate StringToDate(String dateString) throws BadEntriException {
		LocalDate date = null;
		if(dateString.equals("")) {
			return date;
		}
		try {
			date = LocalDate.parse(dateString);
			return date;
		} catch (DateTimeException e) {
			throw new BadEntriException("date fprmat not good");
		}
	}

	public String dateToString(LocalDate introduced) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		if (introduced == null) {
			return "";
		}
		String dateString = introduced.format(format);
		return dateString;
	}
}
