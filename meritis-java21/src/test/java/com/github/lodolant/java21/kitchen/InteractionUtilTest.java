package com.github.lodolant.java21.kitchen;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.github.lodolant.java21.interactions.InteractionClient;
import com.github.lodolant.java21.interactions.InteractionClientCheck;
import com.github.lodolant.java21.interactions.InteractionClientCheckCalories;
import com.github.lodolant.java21.interactions.InteractionClientCheckConsumption;
import com.github.lodolant.java21.interactions.InteractionClientTakeOrder;
import com.github.lodolant.java21.interactions.InteractionKitchen;
import com.github.lodolant.java21.interactions.InteractionUtil;

class InteractionUtilTest {

	@Test
	public void interactWithCustomer_null() {
		// WHEN
		InteractionClient interactionResult = InteractionUtil.interactWithCustomer(null);

		// THEN
		Assertions.assertTrue(interactionResult instanceof InteractionClientCheck);
	}

	@Test
	public void interactWithCustomer_checkConsumption() {
		// WHEN
		InteractionClient interactionResult = InteractionUtil.interactWithCustomer(Ingredient.HAM);

		// THEN
		Assertions.assertTrue(interactionResult instanceof InteractionClientCheckConsumption);
	}

	@Test
	public void interactWithCustomer_checkCalories() {
		// WHEN
		InteractionClient interactionResult = InteractionUtil
				.interactWithCustomer(new RecipeIngredient(Ingredient.BREAD, 500));

		// THEN
		Assertions.assertTrue(interactionResult instanceof InteractionClientCheckCalories);
	}

	@Test
	public void interactWithCustomer_takeOrder() {
		// WHEN
		InteractionClient interactionResult = InteractionUtil.interactWithCustomer(Ingredient.OIGNON);

		// THEN
		Assertions.assertTrue(interactionResult instanceof InteractionClientTakeOrder);
	}

	@Test
	public void interactWithCustomer_Unknown() {
		// WHEN
		Assertions.assertThrows(ServiceException.class, () -> InteractionUtil.interactWithCustomer("tada"));
	}

	@Test
	public void interactWithKitchen_null() {
		// WHEN / THEN
		Assertions.assertThrows(NullPointerException.class, () -> InteractionUtil.interactWithKitchen(null));
	}

	@Test
	public void interactWithKitchen_giveCommand() {
		// WHEN / THEN
		Assertions.assertEquals(InteractionKitchen.GIVE_COMMAND,
				InteractionUtil.interactWithKitchen(new InteractionClientTakeOrder(Ingredient.CHEESE)));
	}

	@Test
	public void interactWithKitchen_allergen() {
		// WHEN / THEN
		Assertions.assertEquals(InteractionKitchen.CHECK_ALLERGENES,
				InteractionUtil.interactWithKitchen(new InteractionClientCheckConsumption(Ingredient.HAM)));
	}

	@Test
	public void interactWithKitchen_command() {
		// WHEN / THEN
		Assertions.assertEquals(InteractionKitchen.COMMAND_MORE,
				InteractionUtil.interactWithKitchen(new InteractionClientCheckConsumption(Ingredient.OIGNON)));
	}

	@Test
	public void interactWithKitchen_client() {
		// WHEN
		Assertions.assertThrows(ServiceException.class,
				() -> InteractionUtil.interactWithKitchen(new InteractionClientCheck()));
	}
}
