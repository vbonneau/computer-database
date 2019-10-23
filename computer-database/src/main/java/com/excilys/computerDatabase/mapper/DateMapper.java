package main.java.com.excilys.computerDatabase.mapper;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

	public LocalDate StringToDate(String dateString) {
		LocalDate date;
		try {
			date = LocalDate.parse(dateString);
			return date;
		} catch (DateTimeException e) {
			return null;
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
