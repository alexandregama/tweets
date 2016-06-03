package com.twitter.profile;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
}
