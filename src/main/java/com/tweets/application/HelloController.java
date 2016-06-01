package com.tweets.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@SpringBootApplication
@Controller
public class HelloController {

	private static final String RESULT_TWEET_PAGE = "resultPage";
	
	@Autowired
	private TwitterClientApi twitterClient;
	
	@RequestMapping("/search-tweet")
	public String searchCompleteTweets(@RequestParam(name = "forQuery") String query, Model model) {
		List<Tweet> tweets = twitterClient.searchTweetsFor(query);
		
		model.addAttribute("tweets", tweets);
		
		return RESULT_TWEET_PAGE;
	}
	
	@RequestMapping("/search-tweet-messages")
	public String searchJustTweetsMessages(@RequestParam(name = "forQuery") String query, Model model) {
		List<String> messages = twitterClient.searchMessagesFor(query);
		
		model.addAttribute("messages", messages);
		
		return RESULT_TWEET_PAGE;
	}

	public static void main(String[] args) {
		SpringApplication.run(HelloController.class, args);
	}

}
