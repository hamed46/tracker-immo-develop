package com.trackerimmo.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.trackerimmo.config.Context;
import com.trackerimmo.model.Ad;
import com.trackerimmo.repository.AdRepository;

public class MainAdHandler implements Handler<Ad, Ad> {

	private static final Logger LOGGER = LoggerFactory.getLogger(MainAdHandler.class);

	AdRepository repository;
	
	public MainAdHandler() {
		repository = Context.getBean(AdRepository.class);
	}

	@Override
	public Ad process(Ad input) {
		try {
			repository.save(input);
			return input;
		} catch (Exception ex) {
			LOGGER.warn("Failed to save position ", ex);
		}
		return null;
	}

}
