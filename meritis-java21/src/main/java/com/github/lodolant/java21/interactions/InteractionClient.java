package com.github.lodolant.java21.interactions;

public sealed interface InteractionClient permits InteractionClientCheck, InteractionClientTakeOrder,
		InteractionClientCheckConsumption, InteractionClientCheckCalories {
}
