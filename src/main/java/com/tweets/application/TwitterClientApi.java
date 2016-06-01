package com.tweets.application;

import static java.util.stream.Collectors.toList;

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

	public List<Tweet> searchTweetsFor(String query) {
		SearchResults search = twitter.searchOperations().search(query);
		List<Tweet> tweets = search.getTweets();
		
		return tweets;
	}
	
	public List<String> searchMessagesFor(String query) {
		SearchResults searchResults = twitter.searchOperations().search(query);
		
		List<String> messages = searchResults
			.getTweets()
			.stream()
			.map(Tweet::getText)
			.collect(toList());
		
		return messages;
	}
	
}
