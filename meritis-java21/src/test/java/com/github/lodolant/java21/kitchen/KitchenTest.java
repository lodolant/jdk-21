package com.github.lodolant.java21.kitchen;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class KitchenTest {
	private static Map<Ingredient, Integer> ingredients;
	private static Map<String, List<RecipeIngredient>> recipes;

	@BeforeAll
	static void loadData() {
		ingredients = Kitchen.commandIngredients();
		recipes = Kitchen.readRecipes();
	}

	@Test
	public void canCommandIngredients() {
		Assertions.assertFalse(ingredients.isEmpty());
	}

	@Test
	public void canReadRecipes() {
		Assertions.assertFalse(recipes.isEmpty());
	}
}
