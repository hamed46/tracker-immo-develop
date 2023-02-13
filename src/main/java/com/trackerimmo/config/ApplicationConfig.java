package com.trackerimmo.config;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.trackerimmo.geocoder.GeocoderException;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@Configuration
public class ApplicationConfig {

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper().setDefaultPropertyInclusion(Include.ALWAYS)
				.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
				.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
				.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING)
				.enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT)
				.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).findAndRegisterModules()
				.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper;
	}

	@Bean
	public HttpClient httpClient() {
		return HttpClient.create().option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
				.responseTimeout(Duration.ofMillis(5000))
				.doOnConnected(conn -> conn.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
						.addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS)));
	}
	
	
	ExchangeFilterFunction errorHandler() {
	    return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
	        if (clientResponse.statusCode().is5xxServerError()) {
	            return clientResponse.bodyToMono(String.class)
	                    .flatMap(errorBody -> Mono.error(new GeocoderException(errorBody)));
	        } else if (clientResponse.statusCode().is4xxClientError()) {
	            return clientResponse.bodyToMono(String.class)
	                    .flatMap(errorBody -> Mono.error(new GeocoderException(errorBody)));
	        } else {
	            return Mono.just(clientResponse);
	        }
	    });
	}
	
	@Bean
	public WebClient webClient() {
		return WebClient.builder().filter(errorHandler()).clientConnector(new ReactorClientHttpConnector(httpClient()))
				.build();
	}

}
