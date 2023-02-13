package com.trackerimmo.geocoder;

import java.net.URI;
import java.util.List;
import java.util.Locale;

import javax.json.JsonArray;
import javax.json.JsonObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;

import com.trackerimmo.config.Context;

import reactor.core.publisher.Mono;

public abstract class SearchGeocoder implements Search {

//	private final StatisticsManager statisticsManager = Context.getBean(StatisticsManager.class);

	private static final Logger LOGGER = LoggerFactory.getLogger(SearchGeocoder.class);

	private final String url;
	private final AddressFormat addressFormat;
	private final WebClient webClient;

	public SearchGeocoder(String url, AddressFormat addressFormat) {
		this.url = url;
		this.addressFormat = addressFormat;
		webClient = Context.getBean(WebClient.class);

	}

	protected String readValue(JsonObject object, String key) {
		if (object.containsKey(key) && !object.isNull(key)) {
			return object.getString(key);
		}
		return null;
	}

	private List<Location> handleResponse(JsonArray jsonArray) {
		List<Location> places = parseAddress(jsonArray, addressFormat);
		if (!places.isEmpty()) {
			return places;
		} else {
			String msg = "Empty address. Error: " + parseError(jsonArray);
			LOGGER.warn(msg);
		}
		return null;
	}

	@Override
	public List<Location> getLocations(String q, SearchCallback callback) {
//		statisticsManager.registerSearchRequest();
		try {
			WebClient.ResponseSpec responseSpec = webClient.get().uri(URI.create(String.format(Locale.US, url, q)))
					.retrieve();	
			
			return handleResponse(responseSpec.bodyToMono(JsonArray.class).block());
			
		} catch (Exception e) {
			LOGGER.warn("Geocoder network error", e);
		}

		return null;
	}

	public abstract List<Location> parseAddress(JsonArray jsonArray, AddressFormat addressFormat);

	protected String parseError(JsonArray jsonArray) {
		return null;
	}

}
