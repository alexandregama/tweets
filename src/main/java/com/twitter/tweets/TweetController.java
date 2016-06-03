package com.twitter.tweets;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.twitter.api.TwitterClientApi;

@Controller
public class TweetController {

	private static final String RESULT_TWEET_PAGE = "resultPage";
	
	@Autowired
	private TwitterClientApi twitterClient;
	
	@RequestMapping("/search-tweet")
	public String searchCompleteTweets(@RequestParam(name = "forQuery") String query, Model model) {
		List<Tweet> tweets = twitterClient.searchTweetsFor(query);
		
		model.addAttribute("tweets", tweets);
		model.addAttribute("searchedQuery", query);
		
		return RESULT_TWEET_PAGE;
	}
	
	@RequestMapping("/search-tweet-messages")
	public String searchJustTweetsMessages(@RequestParam(name = "forQuery") String query, Model model) {
		List<String> messages = twitterClient.searchMessagesFor(query);
		
		model.addAttribute("messages", messages);
		
		return RESULT_TWEET_PAGE;
	}

}
