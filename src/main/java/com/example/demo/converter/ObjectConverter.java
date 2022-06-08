package com.example.demo.converter;

import java.io.IOException;

import javax.persistence.AttributeConverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectConverter implements AttributeConverter<Object, String> {

	private final ObjectMapper obmap = new ObjectMapper();

	@Override
	public String convertToDatabaseColumn(Object customerInfo) {

		String customerInfoJson = null;
		try {
			customerInfoJson = obmap.writeValueAsString(customerInfo);
		} catch (final JsonProcessingException e) {
		}
		return customerInfoJson;
	}

	@Override
	public Object convertToEntityAttribute(String customerInfoJSON) {
		Object customerInfo = null;
		try {
			customerInfo = obmap.readValue(customerInfoJSON, Object.class);
		} catch (final IOException e) {
		}
		return customerInfo;
	}

}
