package com.trackerimmo.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.modelmapper.ModelMapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trackerimmo.model.Ad;
import com.trackerimmo.model.AdFluxImmo;
import com.trackerimmo.service.PatchObject;

//@Component
public class AdMapper implements Handler <String, Ad>, PatchObject{


	ObjectMapper objectMapper;

	ModelMapper modelMapper;
	
	public AdMapper(ObjectMapper objectMapper, ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
		this.objectMapper = objectMapper;
		
	}

	@Override
	public Ad process(String input) {
		TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>(){} ;
		Map<String, Object> map;
		try {
			map = objectMapper.readValue(input, typeRef);
			AdFluxImmo fluxImmoAd = new AdFluxImmo();
			patch(fluxImmoAd, map, objectMapper);
			Ad ad = modelMapper.map(fluxImmoAd, Ad.class);
			return ad;
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

}
