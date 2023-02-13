package com.trackerimmo.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.text.similarity.LevenshteinDistance;
import org.apache.logging.log4j.util.Strings;

import com.trackerimmo.config.Context;
import com.trackerimmo.model.Ad;
import com.trackerimmo.model.Commune;
import com.trackerimmo.service.CityComputing;
import com.trackerimmo.service.SearchCommune;

public class ProcessCity implements Handler<Ad, Ad> {

	private final CityComputing cityComputing;
	private final LevenshteinDistance levenshteinAlgo;
	private final SearchCommune searchCommune;
	private List<Commune> communes;
	private List<Map<String, ?>> scoredCommunes;

	public ProcessCity() {
		cityComputing = Context.getBean(CityComputing.class);
		levenshteinAlgo = new LevenshteinDistance();
		searchCommune = Context.getBean(SearchCommune.class);
		scoredCommunes = new ArrayList<>(1);
	}

	@Override
	public Ad process(Ad input) {
		if (Strings.isEmpty(input.getCity())) {
			scoredCommunes.clear();
			List<String> locations = cityComputing.locationFinder(input.getDescription());
			if (!locations.isEmpty()) {
				for (String location : locations) {
					communes = searchCommune.getLocations(location);
					if (communes != null && !communes.isEmpty()) {
						double distance1 = levenshteinAlgo.apply(communes.get(0).getNom_commune(), location)
								/ communes.get(0).getNom_commune().length();
						double distance2 = levenshteinAlgo.apply(communes.get(0).getNom_commune_complet(), location)
								/ communes.get(0).getNom_commune_complet().length();

						scoredCommunes.add(Map.of("commune", communes.get(0).getNom_commune(), "score",
								Math.min(distance1, distance2)));
					}
				}

				scoredCommunes = scoredCommunes.stream().sorted((d1, d2) -> {
					if ((Double) d1.get("score") < (Double) d2.get("score"))
						return -1;
					if ((Double) d1.get("score") > (Double) d2.get("score"))
						return 1;
					return 0;
				}).collect(Collectors.toList());

				input.setCity((String) scoredCommunes.get(0).get("commune"));
			}
		}

		return input;
	}

}
