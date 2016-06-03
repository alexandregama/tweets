package com.tweets.application;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SearchTermDiscovery {

	@Autowired
	private HttpServletRequest request;

	public boolean constains(String term) {
		String searchTerm = request.getParameter("forQuery");
		if (searchTerm != null && searchTerm.contains(term)) {
			return true;
		}
		return false;
	}
	
}
