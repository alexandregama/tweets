package com.hello.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@SpringBootApplication
@Controller
public class HelloController {

	@Autowired
	private Twitter twitter;
	
	@RequestMapping("/search-tweet")
	public String search(@RequestParam(name = "searchFor") String query, Model model) {
		SearchResults search = twitter.searchOperations().search(query);
		String textOnTweet = search.getTweets().get(0).getText();
		model.addAttribute("textOnTweet", textOnTweet);
		return "resultPage";
	}

	public static void main(String[] args) {
		SpringApplication.run(HelloController.class, args);
	}

}
