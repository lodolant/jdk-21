package com.github.lodolant.java21.kitchen;

public enum Ingredient {
	POTATO(IngredientFlags.NONE), OIGNON(IngredientFlags.NONE), BREAD(IngredientFlags.GLUTEN),
	HAM(IngredientFlags.ANIMAL_EXPLOITED | IngredientFlags.MEAT), CHEESE(IngredientFlags.ANIMAL_EXPLOITED),
	EGG(IngredientFlags.ANIMAL_EXPLOITED);

	private final int flags;

	private Ingredient(int flags) {
		this.flags = flags;
	}

	public boolean flagCheckedAtLeastOne(int flag) {
		return (flags & flag) > 0;
	}

	public boolean flagCheckedAll(int flag) {
		return (flags & flag) == flag;
	}

	public boolean isFlagged() {
		return flags > 0;
	}
}
