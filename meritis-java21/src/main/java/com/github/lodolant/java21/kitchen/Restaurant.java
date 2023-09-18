package com.github.lodolant.java21.kitchen;

import java.util.List;
import java.util.Map;

public class Restaurant {
	private static Map<Ingredient, Integer> ingredientToQuantity;
	private static Map<String, List<RecipeIngredient>> nameToRecipes;

	public static void main(String[] args) {
		ingredientToQuantity = Kitchen.commandIngredients();
		nameToRecipes = Kitchen.readRecipes();
	}

	public void command(String recipe) {
		List<RecipeIngredient> recipeIngredients = nameToRecipes.get(recipe);
		if (recipeIngredients == null) {
			throw new ServiceException("%s is not on the menu...".formatted(recipe));
		}
		for (RecipeIngredient recipeIngredient : recipeIngredients) {
			Ingredient ingredient = recipeIngredient.ingredient();
			int availableQuantity = ingredientToQuantity.get(ingredient).intValue();
			int necessaryQuantity = recipeIngredient.quantity();
			if (necessaryQuantity > availableQuantity) {
				throw new ServiceException("We don't have enough %s...".formatted(ingredient));
			}
		}
		for (RecipeIngredient recipeIngredient : recipeIngredients) {
			Ingredient ingredient = recipeIngredient.ingredient();
			Integer currentQuantity = ingredientToQuantity.get(ingredient);
			int necessaryQuantity = recipeIngredient.quantity();
			ingredientToQuantity.put(ingredient, currentQuantity - necessaryQuantity);
		}
	}
}