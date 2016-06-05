package com.twitter.profile;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.twitter.formatter.PersonalLocalDateFormatter;

@Controller
public class ProfileController {

	@RequestMapping(value = "/profile")
	public String show(ProfileForm profileForm) {
		return "profilePage";
	}

	@RequestMapping(value = "/profile", method = RequestMethod.POST)
	public String saveProfile(ProfileForm profileForm) {
		System.out.println(profileForm);
		
		return "redirect:/profile";
	}

	@ModelAttribute(value = "dateFormat")
	public String localeFormat(Locale locale) {
		return PersonalLocalDateFormatter.patternFrom(locale);
	}
}
