package controllers;

import java.util.List;

import helpers.ControllerHelper;
import models.Recipe;
import play.mvc.*;
import play.data.Form;
import play.libs.Json;

public class RecipeController extends Controller{
	
	public Result newRecipe() {
		Form<Recipe> form = Form.form(Recipe.class).bindFromRequest();
		if (form.hasErrors()) {
			return badRequest(ControllerHelper.errorJson(2, "Datos incorrectos", form.errorsAsJson()));
		}
		Recipe recipe = form.get();
		recipe.save();
		if(request().accepts("application/json")){
    		return ok(Json.toJson(recipe));
    	}
    	return badRequest("Unsupported format");
    }
	
	public Result getRecipe(Long id) {
		Recipe recipe = Recipe.findById(id);
		if(recipe == null){
			return badRequest("El ingrediente no existe");
		}
		if(request().accepts("application/json")){
    		return ok(Json.toJson(recipe));
    	}
    	return badRequest("Unsupported format");
    }
	
	public Result getRecipes(){
		
		List<Recipe> listRecipes = Recipe.find.all();
		
		if(listRecipes.size() == 0){
			return badRequest("No se han encontrado resultados en la búsqueda");
		}else{
			if(request().accepts("application/json")){
	    		return ok(Json.toJson(listRecipes));
	    	}
	    	return badRequest("Unsupported format");
		}
    }

}
