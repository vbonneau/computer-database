package com.excilys.computerDatabase.mapper;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import com.excilys.computerDatabase.exception.BadEntriException;

@Component
public class DateMapper {

	private DateMapper() {

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
			throw new BadEntriException("date format not good");
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
