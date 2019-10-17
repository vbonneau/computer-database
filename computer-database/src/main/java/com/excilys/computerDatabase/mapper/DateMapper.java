package main.java.com.excilys.computerDatabase.mapper;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateMapper {

	private static DateMapper instence;
	
	private DateMapper() {
		
	}
	
	public static DateMapper getInstence() {
		if(instence == null) {instence = new DateMapper();}
		return instence;
	}
	
	public LocalDateTime createDate(String dateString) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		LocalDateTime date;
		try {
			System.out.println("date");
			date = LocalDateTime.parse(dateString+" 00:00",format);
			return date;
		} catch (DateTimeException e) {
			return null;
		}
	}
}
