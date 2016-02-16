package controllers;

import helpers.ControllerHelper;

import java.util.List;

import models.Ingredient;
import models.MeasureIngredient;
import models.Recipe;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.fasterxml.jackson.databind.JsonNode;

public class IngredientController extends Controller {

	public Result newIngredient() {
		Form<Ingredient> form = Form.form(Ingredient.class).bindFromRequest();
		if (form.hasErrors()) {
			return badRequest(ControllerHelper.errorJson(2, "Datos incorrectos", form.errorsAsJson()));
		}
		Ingredient ingredient = form.get();
		if (Ingredient.existe(ingredient.name)) {
			return status(CONFLICT, "Ingrediente ya existente");
		}
		ingredient.save();
		if (request().accepts("application/json")) {
			return ok(Json.toJson(ingredient));
		}
		return badRequest("Unsupported format");
	}

	public Result newIngredients() {
		JsonNode json = request().body().asJson();

		for (JsonNode i : json.withArray("Ingredients")) {
			Ingredient ingredient = new Ingredient();
			ingredient.name = i.get("name").asText();
			ingredient.frozen = i.get("frozen").asBoolean();
			ingredient.category = i.get("category").asText();
			ingredient.baseType = i.get("baseType").asText();
			if (Ingredient.existe(ingredient.name)) {
				return status(CONFLICT, "Ingrediente ya existente " + ingredient.name);
			}
			ingredient.save();

		}
		List<Ingredient> ingredientList = Ingredient.find.all();

		if (ingredientList.size() == 0) {
			return badRequest("No se han encontrado resultados en la búsqueda");
		}
		if (request().accepts("application/json")) {
			return ok(Json.toJson(ingredientList));
		}
		return badRequest("Unsupported format");
	}

	public Result getIngredient(Long id) {
		Ingredient ingredient = Ingredient.findById(id);
		if (ingredient == null) {
			return badRequest("El ingrediente no existe");
		}

		if (request().accepts("application/json")) {
			return ok(Json.toJson(ingredient));
		}
		return badRequest("Unsupported format");
	}

	public Result getIngredients() {

		List<Ingredient> ingredientList = Ingredient.find.all();

		if (ingredientList.size() == 0) {
			return badRequest("No se han encontrado resultados en la búsqueda");
		}
		else {
			if (request().accepts("application/json")) {
				return ok(Json.toJson(ingredientList));
			}
			return badRequest("Unsupported format");
		}
	}

	public Result newIngredientRecipe(Long idIngredient, Long idRecipe) {
		Form<MeasureIngredient> form = Form.form(MeasureIngredient.class).bindFromRequest();
		if (form.hasErrors()) {
			return badRequest(ControllerHelper.errorJson(2, "Datos incorrectos", form.errorsAsJson()));
		}
		Ingredient ingredient = Ingredient.findById(idIngredient);
		if (ingredient == null) {
			return notFound("El ingrediente no existe");
		}
		Recipe recipe = Recipe.findById(idRecipe);
		if (recipe == null) {
			return notFound("La receta no existe");
		}
		if (MeasureIngredient.existeIngredienteReceta(idIngredient, idRecipe)) {
			return status(CONFLICT, "Relacion ya existente entre " + ingredient.name + " y " + recipe.name);
		}
		MeasureIngredient ingredientTask = form.get();
		ingredientTask.ingredient = ingredient;
		ingredientTask.recipe = recipe;
		ingredientTask.save();
		if (request().accepts("application/json")) {
			return ok("Ingredient assigned to task");
		}
		return badRequest("Unsupported format");
	}

	public Result newIngredientsRecipe(Long idRecipe) {

		Recipe recipe = Recipe.findById(idRecipe);
		if (recipe == null) {
			return notFound("La receta no existe");
		}
		JsonNode json = request().body().asJson();

		for (JsonNode i : json.withArray("Ingredients")) {

			Long id = i.get("id").asLong();
			Ingredient ingredient = Ingredient.findById(id);
			if (ingredient == null) {
				return notFound("El ingrediente no existe");
			}
			if (MeasureIngredient.existeIngredienteReceta(id, idRecipe)) {
				return status(CONFLICT, "Relacion ya existente entre " + ingredient.name + " y " + recipe.name);
			}
			MeasureIngredient ingredientTask = new MeasureIngredient();
			String measure = i.get("measure").asText();
			Float quantity = (float) i.get("quantity").asDouble();
			ingredientTask.measure = measure;
			ingredientTask.quantity = quantity;
			ingredientTask.ingredient = ingredient;
			ingredientTask.recipe = recipe;
			ingredientTask.save();

		}
		if (request().accepts("application/json")) {
			return ok("Ingredient assigned to recipe");
		}
		return badRequest("Unsupported format");
	}

	/*
	 * public Result addRecipe(Long idIngredient, Long idRecipe) {
	 * 
	 * Recipe recipe = Recipe.findById(idRecipe); if (recipe == null) { return
	 * notFound("La receta no existe"); }
	 * 
	 * Ingredient ingredient = Ingredient.findById(idIngredient); if (ingredient
	 * == null) { return notFound("El ingrediente no existe"); }
	 * 
	 * recipe.ingredients.add(ingredient); recipe.update(); if
	 * (request().accepts("application/json")) { return
	 * ok("Ingredient assigned to recipe"); } return
	 * badRequest("Unsupported format"); }
	 */

}
