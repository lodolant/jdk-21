package com.github.lodolant.java21.interactions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.lodolant.java21.kitchen.Ingredient;
import com.github.lodolant.java21.kitchen.RecipeIngredient;
import com.github.lodolant.java21.kitchen.ServiceException;

public class InteractionUtil {

	private final static Logger LOGGER = LoggerFactory.getLogger(InteractionUtil.class);

	public static final String HELP = "Hmmmm.... I will ask about %s";

	public static InteractionClient interactWithCustomer(Object interactionTarget) {
		LOGGER.info("Interacting with customer: {}", interactionTarget);
		return switch (interactionTarget) {
		case null -> new InteractionClientCheck();
		case Ingredient ingredient when ingredient.isFlagged() -> new InteractionClientCheckConsumption(ingredient);
		case Ingredient ingredient -> new InteractionClientTakeOrder(ingredient);
		case RecipeIngredient(Ingredient ingredient, int quantity) -> new InteractionClientCheckCalories(quantity);
		default -> throw new ServiceException(HELP.formatted(interactionTarget.getClass().getSimpleName()));
		};
	}

	public static InteractionKitchen interactWithKitchen(InteractionClient interactionTarget) {
		LOGGER.info("Interacting with kitchen: {}", interactionTarget);
		return switch (interactionTarget) {
		case InteractionClientTakeOrder takeOrder -> InteractionKitchen.GIVE_COMMAND;
		case InteractionClientCheckConsumption checkConsumtion when checkConsumtion.getIngredient().isFlagged() -> InteractionKitchen.CHECK_ALLERGENES;
		case InteractionClientCheckConsumption checkConsumtion -> InteractionKitchen.COMMAND_MORE;
		case InteractionClientCheck checkClient -> throw new ServiceException(HELP.formatted(interactionTarget.getClass().getSimpleName()));
		case InteractionClientCheckCalories checkClient -> throw new ServiceException(HELP.formatted(interactionTarget.getClass().getSimpleName()));
		};
	}
}
