package com.trackerimmo.service;

import java.io.IOException;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.trackerimmo.model.AdFluxImmo;

public class AdFluxImmoDeSerializer extends StdDeserializer<AdFluxImmo> {

	private static final long serialVersionUID = 1L;

	protected AdFluxImmoDeSerializer(Class<?> vc) {
		super(vc);
		// TODO Auto-generated constructor stub
	}

	@Override
	public AdFluxImmo deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JacksonException {
		JsonNode node = jp.getCodec().readTree(jp);
	    
	    if(node.isNull() || node.asText().isEmpty()|| node.size()==0)
	        return null;
	    
	    AdFluxImmo city = new AdFluxImmo();
	
	    return city;
	}
}
