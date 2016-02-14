package controllers;

import helpers.ControllerHelper;

import java.util.List;

import models.Ingredient;
import models.IngredientTask;
import models.Recipe;
import models.Task;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class IngredientController extends Controller{
	
	public Result newIngredient() {
		Form<Ingredient> form = Form.form(Ingredient.class).bindFromRequest();
		if (form.hasErrors()) {
			return badRequest(ControllerHelper.errorJson(2, "Datos incorrectos", form.errorsAsJson()));
		}
		Ingredient ingredient = form.get();
		ingredient.save();
		if(request().accepts("application/json")){
    		return ok(Json.toJson(ingredient));
    	}
    	return badRequest("Unsupported format");
    }
	
	public Result getIngredient(Long id) {
		Ingredient ingredient = Ingredient.findById(id);
		if(ingredient == null){
			return badRequest("El ingrediente no existe");
		}
		if(request().accepts("application/json")){
    		return ok(Json.toJson(ingredient));
    	}
    	return badRequest("Unsupported format");
    }
	
	public Result getIngredients(){
		
		List<Ingredient> ingredientList = Ingredient.find.all();
		
		if(ingredientList.size() == 0){
			return badRequest("No se han encontrado resultados en la búsqueda");
		}else{
			if(request().accepts("application/json")){
	    		return ok(Json.toJson(ingredientList));
	    	}
	    	return badRequest("Unsupported format");
		}
    }
	
	public Result newIngredientTask(Long idIngredient, Long idTask) {
		Form<IngredientTask> form = Form.form(IngredientTask.class).bindFromRequest();
		if (form.hasErrors()) {
			return badRequest(ControllerHelper.errorJson(2, "Datos incorrectos", form.errorsAsJson()));
		}
		Ingredient ingredient = Ingredient.findById(idIngredient);
		if(ingredient == null){
			return notFound("El ingrediente no existe");
		}
		Task task = Task.findById(idTask);
		if(task == null){
			return notFound("La tarea no existe");
		}
		IngredientTask ingredientTask = form.get();
		ingredientTask.ingredient = ingredient;
		ingredientTask.task = task;
		ingredientTask.save();
		if(request().accepts("application/json")){
    		return ok("Ingredient assigned to task");
    	}
    	return badRequest("Unsupported format");
    }
	
	public Result addRecipe(Long idIngredient,Long idRecipe) {
		
		Recipe recipe = Recipe.findById(idRecipe);
		if(recipe == null){
			return notFound("La receta no existe");
		}
		
		Ingredient ingredient = Ingredient.findById(idIngredient);
		if(ingredient == null){
			return notFound("El ingrediente no existe");
		}
		
		recipe.ingredients.add(ingredient);
		recipe.update();
		if(request().accepts("application/json")){
    		return ok("Ingredient assigned to recipe");
    	}
    	return badRequest("Unsupported format");
    }

}
