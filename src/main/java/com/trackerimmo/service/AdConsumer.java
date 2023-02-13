package com.trackerimmo.service;

import org.modelmapper.ModelMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trackerimmo.handler.AdMapper;
import com.trackerimmo.handler.MainAdHandler;
import com.trackerimmo.handler.Pipeline;
import com.trackerimmo.handler.ProcessCity;
import com.trackerimmo.handler.ProcessLimitrophe;
import com.trackerimmo.model.Ad;

//@Component
public class AdConsumer implements PatchObject {

	ObjectMapper objectMapper;

	ModelMapper modelMapper;

	Pipeline<String, Ad> pipeline;

	public AdConsumer(ObjectMapper objectMapper, ModelMapper modelMapper) {
		this.objectMapper = objectMapper;
		this.modelMapper = modelMapper;
		pipeline = new Pipeline<String, Ad>(new AdMapper(objectMapper, modelMapper)).addHandler(new ProcessCity())
				.addHandler(new ProcessLimitrophe()).addHandler(new MainAdHandler());

	}

	@KafkaListener(topics = "incomingAds", containerFactory = "kafkaListenerContainerFactory")
	public void consume(String record) {
		System.out.println(String.format("object : %s", record));
		pipeline.execute(record);

	}
}
