package com.twitter;

import java.time.LocalDate;

import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;

import com.twitter.formatter.PersonalLocalDateFormatter;

@Configuration
public class WebConfiguration extends WebMvcAutoConfigurationAdapter {

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addFormatterForFieldType(LocalDate.class, new PersonalLocalDateFormatter());
	}
	
}
