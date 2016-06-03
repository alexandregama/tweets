package com.twitter.formatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.format.Formatter;

public class PersonalLocalDateFormatter implements Formatter<LocalDate> {
	
	@Override
	public LocalDate parse(String dateInText, Locale locale) throws ParseException {
		DateTimeFormatter dateFormatter = null;
		
		if (isUnitedStatesFrom(locale)) {
			dateFormatter = normalDateFormatter();
		} else {
			dateFormatter = unitedStatesDateFormatter();
		}
		LocalDate formattedDate = LocalDate.parse(dateInText, dateFormatter);
		
		return formattedDate;
	}
	
	@Override
	public String print(LocalDate desiredDate, Locale locale) {
		if (isUnitedStatesFrom(locale)) {
			return normalDateFormatter().format(desiredDate);
		}
		return unitedStatesDateFormatter().format(desiredDate);
	}
	
	private DateTimeFormatter unitedStatesDateFormatter() {
		return DateTimeFormatter.ofPattern("MM/dd/yyyy");
	}
	
	private DateTimeFormatter normalDateFormatter() {
		return DateTimeFormatter.ofPattern("dd/MM/yyyy");
	}

	private boolean isUnitedStatesFrom(Locale locale) {
		return Locale.US.getCountry().equals(locale.getCountry());
	}

}
