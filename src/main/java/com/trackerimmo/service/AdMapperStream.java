package com.trackerimmo.service;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.springframework.kafka.config.KafkaStreamsInfrastructureCustomizer;
import org.springframework.kafka.support.serializer.JsonDeserializer;


//@Component
public class AdMapperStream implements KafkaStreamsInfrastructureCustomizer {

	private static final Serde<String> KEY_SERDE = Serdes.String();
	static final Deserializer<Object> VALUE_DE = new JsonDeserializer<>().ignoreTypeHeaders();
}
