package com.github.lodolant.java21.interactions;

import com.github.lodolant.java21.kitchen.Ingredient;

public final class InteractionClientCheckConsumption implements InteractionClient {
	private final Ingredient ingredient;

	public InteractionClientCheckConsumption(Ingredient ingredient) {
		this.ingredient = ingredient;
	}

	public Ingredient getIngredient() {
		return ingredient;
	}
}
