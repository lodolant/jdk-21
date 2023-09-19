package com.github.lodolant.java21.kitchen;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope.ShutdownOnFailure;
import java.util.concurrent.StructuredTaskScope.ShutdownOnSuccess;
import java.util.concurrent.StructuredTaskScope.Subtask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Kitchen {
	private final static Logger LOGGER = LoggerFactory.getLogger(Kitchen.class);

	public static Map<Ingredient, Integer> commandIngredients() {
		Instant begin = Instant.now();
		try (ShutdownOnFailure scope = new ShutdownOnFailure()) {
			List<Subtask<List<RecipeIngredient>>> allCommands = new ArrayList<>();
			allCommands.add(scope.fork(commandToButcher()));
			allCommands.add(scope.fork(commandToLocalProducer1()));
			allCommands.add(scope.fork(commandToLocalProducer2()));
			allCommands.add(scope.fork(commandCheese()));
			allCommands.add(scope.fork(getBakery()));

			scope.join();
			scope.throwIfFailed((Throwable t) -> {
				LOGGER.info("Error encountered : {}", t.getLocalizedMessage());
				throw new ServiceException("Could not receive commands");
			});

			Map<Ingredient, Integer> ingredientToQuantity = new HashMap<>();
			allCommands.forEach(oneCommand -> receiveCommand(oneCommand, ingredientToQuantity));
			return ingredientToQuantity;
		} catch (InterruptedException e) {
			LOGGER.info("Error encountered : {}", e.getLocalizedMessage());
			throw new ServiceException("Could not receive commands");
		} finally {
			Instant end = Instant.now();
			LOGGER.info("Loading duration = {} ms", Duration.between(begin, end).toMillis());
		}
	}
	
	public static Map<Ingredient, Integer> commandMore()  {
		try(ShutdownOnSuccess<List<RecipeIngredient>> scope = new ShutdownOnSuccess<>()) {
			List<Subtask<List<RecipeIngredient>>> allCommands = new ArrayList<>();
			allCommands.add(scope.fork(commandToButcher()));
			allCommands.add(scope.fork(commandToLocalProducer1()));
			allCommands.add(scope.fork(commandToLocalProducer2()));
			allCommands.add(scope.fork(commandCheese()));
			allCommands.add(scope.fork(getBakery()));

			scope.join();
			
			List<RecipeIngredient> result = scope.result();
			Map<Ingredient, Integer> ingredientToQuantity = new HashMap<>();
			receiveCommand(result, ingredientToQuantity);
			return ingredientToQuantity;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private static void receiveCommand(Subtask<List<RecipeIngredient>> ingredients,
			Map<Ingredient, Integer> ingredientToQuantity) {
		receiveCommand(ingredients.get(), ingredientToQuantity);
	}

	private static void receiveCommand(List<RecipeIngredient> ingredients,
			Map<Ingredient, Integer> ingredientToQuantity) {
		ingredients.forEach((recipeIngredient) -> {
			Ingredient ingredient = recipeIngredient.ingredient();
			ingredientToQuantity.putIfAbsent(ingredient, Integer.valueOf(0));
			int newQuantity = ingredientToQuantity.get(ingredient) + recipeIngredient.quantity();
			ingredientToQuantity.put(ingredient, newQuantity);
		});
	}


	private static Callable<List<RecipeIngredient>> commandToButcher() {
		return () -> {
			LOGGER.info("Command from caterer producers...");
			List<RecipeIngredient> ingredients = new ArrayList<>();
			ingredients.add(new RecipeIngredient(Ingredient.HAM, 20_000));
			return ingredients;
		};
	}

	private static Callable<List<RecipeIngredient>> commandCheese() {
		return () -> {
			LOGGER.info("Command from caterer producers...");
			List<RecipeIngredient> ingredients = new ArrayList<>();
			ingredients.add(new RecipeIngredient(Ingredient.CHEESE, 20_000));
			return ingredients;
		};
	}

	private static Callable<List<RecipeIngredient>> getBakery() {
		return () -> {
			LOGGER.info("Command from industrial producers...");
			List<RecipeIngredient> ingredients = new ArrayList<>();
			ingredients.add(new RecipeIngredient(Ingredient.BREAD, 20_000));
			return ingredients;
		};
	}

	private static Callable<List<RecipeIngredient>> commandToLocalProducer1() {
		return () -> {
			LOGGER.info("Command from local producers...");
			List<RecipeIngredient> ingredients = new ArrayList<>();
			ingredients.add(new RecipeIngredient(Ingredient.OIGNON, 20_000));
			ingredients.add(new RecipeIngredient(Ingredient.POTATO, 10_000));
			ingredients.add(new RecipeIngredient(Ingredient.EGG, 500));
			return ingredients;
		};
	}

	private static Callable<List<RecipeIngredient>> commandToLocalProducer2() {
		return () -> {
			LOGGER.info("Command from local producers...");
			List<RecipeIngredient> ingredients = new ArrayList<>();
			ingredients.add(new RecipeIngredient(Ingredient.OIGNON, 50_000));
			ingredients.add(new RecipeIngredient(Ingredient.POTATO, 10_000));
			return ingredients;
		};
	}

	public static Map<String, List<RecipeIngredient>> readRecipes() {
		Map<String, List<RecipeIngredient>> recipes = new HashMap<>();
		recipes.put("Croque-Monsieur",
				List.of(new RecipeIngredient(Ingredient.CHEESE, 100), new RecipeIngredient(Ingredient.BREAD, 100),
						new RecipeIngredient(Ingredient.EGG, 100), new RecipeIngredient(Ingredient.HAM, 100),
						new RecipeIngredient(Ingredient.POTATO, 100)));
		return recipes;
	}
}
