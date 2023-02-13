package com.trackerimmo.geocoder;

import java.util.ArrayList;
import java.util.List;

import javax.json.JsonArray;
import javax.json.JsonObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trackerimmo.config.Context;

public class NominatimSearch extends SearchGeocoder {

	// q=135+pilkington+avenue,+birmingham&format=xml&polygon_geojson=1&addressdetails=1

	private static String formatUrl(String url, String key, String language) {
		if (url == null) {
			url = "https://nominatim.openstreetmap.org/search";
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

	public NominatimSearch(String url, String key, String language, AddressFormat addressFormat) {
		super(formatUrl(url, key, language), addressFormat);
	}

	@Override
	public List<Location> parseAddress(JsonArray jsonArray, AddressFormat addressFormat) {
		List<Location> result = new ArrayList<Location>();
		ObjectMapper mapper = Context.getBean(ObjectMapper.class);
		JsonObject jsonObject;
		for (Object o : jsonArray) {
			jsonObject = mapper.convertValue(o, JsonObject.class);
			Address address = getAddress(jsonObject);
			if (address == null)
				continue;
			Location p = new Location();
			p.setAddress(address);
			result.add(p);
		}
		return result;
	}

	private Address getAddress(JsonObject json) {
		JsonObject result = json.getJsonObject("address");
		if (result != null) {
			Address address = new Address();

			if (result.containsKey("house_number")) {
				address.setHouse(result.getString("house_number"));
			}
			if (result.containsKey("road")) {
				address.setStreet(result.getString("road"));
			}
			if (result.containsKey("suburb")) {
				address.setSuburb(result.getString("suburb"));
			}

			if (result.containsKey("village")) {
				address.setSettlement(result.getString("village"));
			} else if (result.containsKey("town")) {
				address.setSettlement(result.getString("town"));
			} else if (result.containsKey("city")) {
				address.setSettlement(result.getString("city"));
			}

			if (result.containsKey("state_district")) {
				address.setDistrict(result.getString("state_district"));
			} else if (result.containsKey("region")) {
				address.setDistrict(result.getString("region"));
			}

			if (result.containsKey("state")) {
				address.setState(result.getString("state"));
			}
			if (result.containsKey("country_code")) {
				address.setCountry(result.getString("country_code").toUpperCase());
			}
			if (result.containsKey("postcode")) {
				address.setPostcode(result.getString("postcode"));
			}

			return address;
		}
		return null;
	}

}
