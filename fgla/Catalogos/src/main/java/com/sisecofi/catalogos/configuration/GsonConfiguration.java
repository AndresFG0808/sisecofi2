package com.sisecofi.catalogos.configuration;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Configuration
public class GsonConfiguration {

	@Bean
	public GsonBuilder builder() {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(LocalDateTime.class,
				(JsonDeserializer<LocalDateTime>) (json, typeOfT, context) -> LocalDateTime.ofInstant(
						Instant.ofEpochMilli(json.getAsJsonPrimitive().getAsLong()), ZoneId.systemDefault()));
		return builder;
	}

	@Bean
	public Gson gson(@Autowired GsonBuilder builder) {
		return builder.setDateFormat("yyyy-MM-dd").create();
	}

}
