package com.twitter.formatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.format.Formatter;

public class PersonalLocalDateFormatter implements Formatter<LocalDate> {
	
	private static final String NORMAL_DATE_FORMAT = "dd/MM/yyyy";
	private static final String US_DATE_FORMAT = "MM/dd/yyyy";

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
	
	private static DateTimeFormatter unitedStatesDateFormatter() {
		return DateTimeFormatter.ofPattern(US_DATE_FORMAT);
	}
	
	private static DateTimeFormatter normalDateFormatter() {
		return DateTimeFormatter.ofPattern(NORMAL_DATE_FORMAT);
	}

	private static boolean isUnitedStatesFrom(Locale locale) {
		return Locale.US.getCountry().equals(locale.getCountry());
	}

	public static String patternFrom(Locale locale) {
		if (isUnitedStatesFrom(locale)) {
			return US_DATE_FORMAT;
		}
		return NORMAL_DATE_FORMAT;
	}

}
