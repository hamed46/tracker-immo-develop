package com.trackerimmo.geocoder;

import java.util.List;

import javax.json.JsonArray;

public class MeiliSearch extends SearchGeocoder {

	
	// http://54.36.191.72:7701/indexes/communes/search
	
	private static String formatUrl(String url, String key, String language) {
		if (url == null) {
			url = "http://54.36.191.72:7701/indexes/communes/search";
		} else {
			url += "/search";
		}
		url += "?q=%s&format=json&addressdetails=1";
		if (key != null) {
			url += "&key=" + key;
		}
		if (language != null) {
			url += "&accept-language=" + language;
		}
		return url;
	}
	
	public MeiliSearch(String url, AddressFormat addressFormat) {
		super(url, addressFormat);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Location> parseAddress(JsonArray jsonArray, AddressFormat addressFormat) {
		// TODO Auto-generated method stub
		return null;
	}

}
