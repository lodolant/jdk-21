package com.github.lodolant.java21.kata;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UrlProcessorTest {
	private final UrlProcessor URL = new UrlProcessor();

	@Test
	void urlProcessor_OK() throws IllegalArgumentException {
		String operation = "/";
		int valeur1 = 5;
		int valeur2 = 10;

		String message = URL."http://maths/\{operation}/\{valeur1}/\{valeur2}";

		Assertions.assertEquals("http://maths/%2F/5/10", message);
	}

	@Test
	void urlProcessor_Divide0() throws IllegalArgumentException {
		String operation = "/";
		int valeur1 = 5;
		int valeur2 = 0;

		try {
			String message = URL."http://maths/\{operation}/\{valeur1}/\{valeur2}";
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
			String message = URL."http://maths/\{operation}/\{valeur1}/\{valeur2}";
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
			String message = URL."http://maths/\{operation}/\{valeur1}/";
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
			String message = URL."http://maths/\{operation}/\{valeur1}/\{valeur2}";
			Assertions.fail("Should not be here, exception is expected to be thrown previously");
		} catch (IllegalArgumentException e) {
			Assertions.assertEquals("Wrong type", e.getLocalizedMessage());
		}
	}
}
