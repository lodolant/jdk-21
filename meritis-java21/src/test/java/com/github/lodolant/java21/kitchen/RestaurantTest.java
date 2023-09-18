package com.github.lodolant.java21.kitchen;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RestaurantTest {
	private final Restaurant restaurant = new Restaurant();

	@BeforeAll
	static void init() {
		Restaurant.main(new String[0]);
	}

	@Test
	public void command_runOut() {
		for (int i = 0; i < 5; i++) {
			restaurant.command("Croque-Monsieur");
		}
		Assertions.assertThrows(ServiceException.class, () -> restaurant.command("Croque-Monsieur"));
	}

	@Test
	public void command_UnknownOut() {
		Assertions.assertThrows(ServiceException.class, () -> restaurant.command("Pizza Regina"));
	}
}
