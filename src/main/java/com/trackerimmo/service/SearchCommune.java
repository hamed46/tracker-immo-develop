package com.trackerimmo.service;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.json.JsonArray;
import javax.json.JsonObject;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestBodySpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trackerimmo.config.Config;
import com.trackerimmo.config.Context;
import com.trackerimmo.config.Keys;
import com.trackerimmo.geocoder.Location;
import com.trackerimmo.model.Commune;

import reactor.core.publisher.Mono;

@Service
public class SearchCommune implements PatchObject {

	private static final Logger LOGGER = LoggerFactory.getLogger(SearchCommune.class);

	// http://54.36.191.72:7701/indexes/communes/search

	String[] defaultAttributesToRetrieve = { "id", "code_commune_INSEE", "nom_commune_postal", "code_postal",
			"latitude", "longitude", "code_commune", "nom_commune", "nom_commune_complet", "code_departement",
			"nom_departement", "code_region", "nom_region" };

	private final WebClient webClient;
	private final Config config;
	private final ObjectMapper objectMapper;
	private final String token;

	private final String url;

	public SearchCommune() {
		webClient = Context.getBean(WebClient.class);
		config = Context.getBean(Config.class);
		objectMapper = Context.getBean(ObjectMapper.class);
		url = String.format("%sindexes/communes/search", config.getString(Keys.MEILI_SEARCh_URL));
		token = config.getString(Keys.MEILI_SEARCh_TOKEN);

	}

	private List<Commune> handleResponse(List<Map<String, Object>> jsonArray) {

		List<Commune> communes = new ArrayList<>(1);
		for (Map<String, Object> item : jsonArray) {
			try {
				communes.add(patch(new Commune(), item, objectMapper));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return communes;
	}

	public List<Commune> getLocations(String q) {
//		statisticsManager.registerSearchRequest();
		try {
			Map<String, Object> attributes = new HashMap<String, Object>();
			attributes.put("q", q);

			attributes.put("attributesToRetrieve", defaultAttributesToRetrieve);

			RequestBodySpec bodySpec = webClient.post().uri(URI.create(String.format(Locale.US, url)));

			RequestHeadersSpec<?> headersSpec = bodySpec.body(Mono.just(attributes), Map.class);

			ResponseSpec responseSpec = headersSpec.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
					.accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML).acceptCharset(StandardCharsets.UTF_8)
					.ifNoneMatch("*").ifModifiedSince(ZonedDateTime.now())
					.headers(h -> h.setBearerAuth(token)).retrieve();

			@SuppressWarnings("unchecked")
			Map<String, List<Map<String, Object>>> result = responseSpec.bodyToMono(Map.class).block();

			return handleResponse(result.get("hits"));

		} catch (Exception e) {
			LOGGER.warn("Geocoder network error", e);
		}

		return null;
	}
}
