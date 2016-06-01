package com.tweets.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Component;

@Component
public class TwitterClientApi {

	@Autowired
	private Twitter twitter;

	public List<Tweet> searchFor(String query) {
		SearchResults search = twitter.searchOperations().search(query);
		List<Tweet> tweets = search.getTweets();
		
		return tweets;
	}
	
}
