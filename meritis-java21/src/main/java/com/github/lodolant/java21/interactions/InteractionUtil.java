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
		if (interactionTarget == null) {
			return new InteractionClientCheck();
		} else if (interactionTarget instanceof Ingredient ingredient) {
			if (ingredient.isFlagged()) {
				return new InteractionClientCheckConsumption(ingredient);
			}
			return new InteractionClientTakeOrder(ingredient);
		} else if (interactionTarget instanceof RecipeIngredient recipe) {
			return new InteractionClientCheckCalories(recipe.quantity());
		}
		throw new ServiceException(HELP.formatted(interactionTarget.getClass().getSimpleName()));
	}

	public static InteractionKitchen interactWithKitchen(InteractionClient interactionTarget) {
		LOGGER.info("Interacting with kitchen: {}", interactionTarget);
		if (interactionTarget == null) {
			throw new NullPointerException();
		}
		if (interactionTarget instanceof InteractionClientTakeOrder takeOrder) {
			return InteractionKitchen.GIVE_COMMAND;
		} else if (interactionTarget instanceof InteractionClientCheckConsumption checkConsumtion) {
			if (checkConsumtion.getIngredient().isFlagged()) {
				return InteractionKitchen.CHECK_ALLERGENES;
			}
			return InteractionKitchen.COMMAND_MORE;
		}
		throw new ServiceException(HELP.formatted(interactionTarget.getClass().getSimpleName()));
	}
}
