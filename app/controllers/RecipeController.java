package controllers;

import helpers.ControllerHelper;
import helpers.RecipeMinimized;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import models.Ingredient;
import models.MeasureIngredient;
import models.Recipe;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class RecipeController extends Controller {

	public Result newRecipe() {
		Form<Recipe> form = Form.form(Recipe.class).bindFromRequest();
		if (form.hasErrors()) {
			return badRequest(ControllerHelper.errorJson(2, "Datos incorrectos", form.errorsAsJson()));
		}
		Recipe recipe = form.get();
		if (Recipe.existe(recipe.name)) {
			return status(CONFLICT, "Receta ya existente");
		}
		recipe.save();
		if (request().accepts("application/json")) {
			return ok(Json.toJson(recipe));
		}
		return badRequest("Unsupported format");
	}

	public Result getRecipe(Long id) {
		Recipe recipe = Recipe.findById(id);
		if (recipe == null) {
			return badRequest("El ingrediente no existe");
		}
		if (request().accepts("application/json")) {
			return ok(Json.toJson(recipe));
		}
		return badRequest("Unsupported format");
	}

	public Result getRecipes() {

		List<Recipe> listRecipes = Recipe.find.all();

		if (listRecipes.size() == 0) {
			return badRequest("No se han encontrado resultados en la búsqueda");
		}
		else {
			if (request().accepts("application/json")) {
				List<RecipeMinimized> list = RecipeMinimized.getList(listRecipes);
				return ok(Json.toJson(list));
			}
			return badRequest("Unsupported format");
		}
	}
	public Result getRecetasIngredients(){
		
		Set<Recipe> listaRecetas = new LinkedHashSet<Recipe>();
		String listaIngredientes=request().getQueryString("ingredientes");
		List<Ingredient> lista = Ingredient.listaIngredientesExistentes(listaIngredientes);
		listaRecetas=MeasureIngredient.recetasConIngredientes(lista);
		
		return ok(Json.toJson(listaRecetas));
	}

}
