package com.trackerimmo.service;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.node.ObjectNode;

public interface PatchObject {

	default Map<String, Object> updateHashMap(Map<String, Object> data, Map<String, Object> patch) {
		for (String key : patch.keySet()) {

			if (patch.get(key) == null || patch.get(key) == "") {
				if (data.containsKey(key))
					data.remove(key);
			} else {
				data.put(key, patch.get(key));
			}
		}
		return data;
	}

	default Map<String, Object> patchMap(Map<String, Object> existingMap, Object patch, ObjectMapper objectMapper)
			throws IOException {
		ObjectReader objectReader = objectMapper.readerForUpdating(existingMap);
		JsonNode patchNode = objectMapper.valueToTree(patch);
		return objectReader.readValue(patchNode);

	}

	default <Data, Patch> Data patch(Data data, Patch patch, ObjectMapper objectMapper) throws IOException {
		ObjectReader objectReader = objectMapper.readerForUpdating(data);
		JsonNode patchNode = objectMapper.valueToTree(patch);
		return objectReader.readValue(patchNode);

	}

}
