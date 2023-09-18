package com.github.lodolant.java21.kitchen;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class IngredientTest {
	@Test
	void testFlag_NoFlag() {
		Assertions.assertFalse(Ingredient.OIGNON.isFlagged());
		Assertions.assertFalse(Ingredient.OIGNON.flagCheckedAll(IngredientFlags.ANIMAL_EXPLOITED | IngredientFlags.GLUTEN));
		Assertions.assertFalse(Ingredient.OIGNON.flagCheckedAtLeastOne(IngredientFlags.ANIMAL_EXPLOITED | IngredientFlags.GLUTEN));
	}

	@Test
	void testFlag_Flagged() {
		Assertions.assertTrue(Ingredient.HAM.isFlagged());
		
		Assertions.assertTrue(Ingredient.HAM.flagCheckedAll(IngredientFlags.ANIMAL_EXPLOITED | IngredientFlags.MEAT));
		Assertions.assertTrue(Ingredient.HAM.flagCheckedAtLeastOne(IngredientFlags.ANIMAL_EXPLOITED | IngredientFlags.MEAT));

		Assertions.assertFalse(Ingredient.HAM.flagCheckedAll(IngredientFlags.ANIMAL_EXPLOITED | IngredientFlags.GLUTEN));
		Assertions.assertTrue(Ingredient.HAM.flagCheckedAtLeastOne(IngredientFlags.ANIMAL_EXPLOITED | IngredientFlags.GLUTEN));
	}
}
