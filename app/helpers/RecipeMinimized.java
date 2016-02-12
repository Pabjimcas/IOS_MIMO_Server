package helpers;

import java.util.ArrayList;
import java.util.List;

import models.Recipe;

public class RecipeMinimized {
	
	public Long id;
	public String name;
	
	public RecipeMinimized(Recipe recipe) {
		this.id = recipe.id;
		this.name = recipe.name;
	}
	
	public static List<RecipeMinimized>getList(List<Recipe> recipes){
		List<RecipeMinimized> newList = new ArrayList<RecipeMinimized>(recipes.size());
		for (Recipe rp : recipes) { 
		  newList.add(new RecipeMinimized(rp)); 
		}
		return newList;
	}
	
	

}
