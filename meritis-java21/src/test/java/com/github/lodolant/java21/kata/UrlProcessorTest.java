package com.github.lodolant.java21.kata;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UrlProcessorTest {
	@Test
	void urlProcessor_OK() throws IllegalArgumentException {
		String operation = "/";
		int valeur1 = 5;
		int valeur2 = 10;

		String message = "http://maths/%s/%d/%d".formatted(escape(operation), valeur1, valeur2);
		check(message);

		Assertions.assertEquals("http://maths/%2F/5/10", message);
	}

	@Test
	void urlProcessor_Divide0() throws IllegalArgumentException {
		String operation = "/";
		int valeur1 = 5;
		int valeur2 = 0;

		try {
			String message = "http://maths/%s/%d/%d".formatted(escape(operation), valeur1, valeur2);
			check(message);
			Assertions.fail("Should not be here, exception is expected to be thrown previously");
		} catch (IllegalArgumentException e) {
			Assertions.assertEquals("Divide by 0", e.getLocalizedMessage());
		}
	}

	@Test
	void urlProcessor_MissingValue() throws IllegalArgumentException {
		String operation = "/";
		int valeur1 = 5;
		Integer valeur2 = null;

		try {
			String message = "http://maths/%s/%d/%d".formatted(escape(operation), valeur1, valeur2);
			check(message);
			Assertions.fail("Should not be here, exception is expected to be thrown previously");
		} catch (IllegalArgumentException e) {
			Assertions.assertEquals("Missing value", e.getLocalizedMessage());
		}
	}

	@Test
	void urlProcessor_MissingParameter() throws IllegalArgumentException {
		String operation = "/";
		int valeur1 = 5;

		try {
			String message = "http://maths/%s/%d".formatted(escape(operation), valeur1);
			check(message);
			Assertions.fail("Should not be here, exception is expected to be thrown previously");
		} catch (IllegalArgumentException e) {
			Assertions.assertEquals("Missing Parameter", e.getLocalizedMessage());
		}
	}

	@Test
	void urlProcessor_WrongType() throws IllegalArgumentException {
		String operation = "/";
		int valeur1 = 5;
		String valeur2 = "cinq";

		try {
			String message = "http://maths/%s/%d/%d".formatted(escape(operation), valeur1, valeur2);
			check(message);
			Assertions.fail("Should not be here, exception is expected to be thrown previously");
		} catch (IllegalArgumentException e) {
			Assertions.assertEquals("d != java.lang.String", e.getLocalizedMessage());
		}
	}

	private void check(String value) {
		if (value.contains("null")) {
			throw new IllegalArgumentException("Missing value");
		}
		String[] split = value.split("/");
		List<String> parts = Arrays.asList(split);
		for (int i = 0; i < parts.size(); i++) {
			String partValue = parts.get(i);
			try {
				Integer.parseInt(partValue);
			} catch (Exception e) { // NOT A NUMBER => An operation !
				if (i + 2 >= parts.size()) {
					throw new IllegalArgumentException("Missing Parameter");
				}
				if (partValue.equals("%2F")) {
					String divisionValue = parts.get(i + 2);
					if (Integer.valueOf(divisionValue).intValue() == 0) {
						throw new IllegalArgumentException("Divide by 0");
					}
				}
			}
		}
	}

	private String escape(String value) {
		return value.replace(" ", "+").replace("<", "%3C").replace(">", "%3E").replace("/", "%2F");
	}
}
