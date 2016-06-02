package com.tweets.application;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SearchController {

	@Autowired
	private SearchTermDiscovery termDiscovery;
	
	@RequestMapping("/")
	public String show() {
		return "searchPage";
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String search(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		if (termDiscovery.constains("struts")) {
			redirectAttributes.addFlashAttribute("error", "Try to use Spring instead of struts");
			return "redirect:/";
		}
		redirectAttributes.addAttribute("forQuery", request.getParameter("forQuery"));
		
		return "redirect:search-tweet";
	}
	
}
