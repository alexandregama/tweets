package com.tweets.application;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SearchController {

	@RequestMapping("/")
	public String show() {
		return "searchPage";
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String search(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		String searchTerm = request.getParameter("forQuery");
		redirectAttributes.addAttribute("forQuery", searchTerm);
		
		return "redirect:search-tweet";
	}
	
}
