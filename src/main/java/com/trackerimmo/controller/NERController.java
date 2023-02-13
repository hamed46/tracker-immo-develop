package com.trackerimmo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trackerimmo.model.Commune;
import com.trackerimmo.service.CityComputing;
import com.trackerimmo.service.DL4JProcessing;
import com.trackerimmo.service.ImageProcessor;
import com.trackerimmo.service.SearchCommune;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path = "api/v1", produces = "application/json")
public class NERController {

	@Autowired
	CityComputing cityComputing;
	
	@Autowired
	SearchCommune searchCommune;
	
	@Autowired
	DL4JProcessing imageProcessor;
	
	@PostMapping(path = "/ner")
	public ResponseEntity<List<String>> store(@RequestBody String text, HttpServletRequest request) {
//		String[] sentence = text.split(",");
		 List<String> result = cityComputing.locationFinder(text);		
		return ResponseEntity.ok(result);
	}
	
	@PostMapping(path = "/train")
	public ResponseEntity<String> train() {
		cityComputing.trainModel();
		return ResponseEntity.ok("Success");
	}
	
	@PostMapping(path = "/commune")
	public ResponseEntity<List<Commune>> getCommune(@RequestParam String q) {
		List<Commune> locations = searchCommune.getLocations(q);
		return ResponseEntity.ok(locations);
	}
	
	@PostMapping(path = "/vectoring")
	public ResponseEntity<String> vectoring(@RequestParam String name) {
		 imageProcessor.processIamge(name);
		return ResponseEntity.ok("Ok");
	}

	
}
