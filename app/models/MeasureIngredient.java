package models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class MeasureIngredient extends Model {

	@Id
	public Long id;

	public String measure;
	public Float quantity;
	@JsonIgnore
	@ManyToOne
	public Recipe recipe;

	@ManyToOne
	public Ingredient ingredient;

	public static final Find<Long, MeasureIngredient> find = new Find<Long, MeasureIngredient>() {
	};

	public static boolean existeIngredienteReceta(Long idIngrediente, Long idReceta) {
		Ingredient ingredient = Ingredient.findById(idIngrediente);
		Recipe recipe = Recipe.findById(idReceta);
		if (find.where().eq("ingredient", ingredient).eq("recipe", recipe).findList().size() == 0) {
			return false;
		}
		else {
			return true;
		}
	}
	public static Set<Recipe> recetasConIngredientes (List<Ingredient> listaIngredientes){
		List<Recipe> listaRecetas = new ArrayList<Recipe>();
		List<MeasureIngredient> listaMeasure = new ArrayList<MeasureIngredient>();
		for (Ingredient ingredient :listaIngredientes){
			listaMeasure= MeasureIngredient.find.where().eq("ingredient", ingredient).findList();
			Set<MeasureIngredient> setMeasure = new LinkedHashSet<MeasureIngredient>(listaMeasure);
			Iterator<MeasureIngredient> itr = setMeasure.iterator(); 
			while (itr.hasNext()){
				listaRecetas.add(itr.next().recipe);
			}
		}
		//Set<MeasureIngredient> setRecipe = new LinkedHashSet<MeasureIngredient>(listaMeasure);
		Set<Recipe> setRecipe = new LinkedHashSet<Recipe>(listaRecetas);
		
		return setRecipe;
		
	}

}
