package models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import play.data.validation.Constraints.Required;

import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Ingredient extends Model {

	@Id
	public Long id;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "ingredient")
	public List<MeasureIngredient> measureIngredient;

	@Required
	public String name;

	@Required
	public Boolean frozen;

	@Required
	public String category;

	@Required
	public String baseType;
	public String photo;
	public static final Find<Long, Ingredient> find = new Find<Long, Ingredient>() {
	};

	public static Ingredient findByName(String nombre) {
		if (find.where().eq("name", nombre).findList().size() == 0) {
			return null;
		}
		else {
			return find.where().eq("name", nombre).findList().get(0);
		}

	}

	public static boolean existe(String nombre) {
		return Ingredient.findByName(nombre) != null;
	}

	public static Ingredient findById(Long id) {
		return find.byId(id);
	}

	public static List<Ingredient> filterIngredients(String category,List<String> listIds) {
		List<Long> longlist = new ArrayList<Long>();
		for(String id : listIds) longlist.add(Long.valueOf(id));
		ExpressionList<Ingredient> exp = find.where().eq("category", category);
		if(longlist.size()>0){
			exp.notIn("id", longlist);
		}
		return exp.findList();
	}
public static List<Ingredient> listaIngredientesExistentes(String ingredientes){
		
		List<Ingredient> listaIngredients=new ArrayList<Ingredient>();
		List<String> ingredientesIds=Arrays.asList(ingredientes.split("\\s*,\\s*"));
		List<Long> longlist = new ArrayList<Long>();
		for(String id : ingredientesIds) longlist.add(Long.valueOf(id));
		for (Long id2 : longlist){
			
			Ingredient i = Ingredient.findById(id2);
			if (i!=null){
				listaIngredients.add(i);
			}
		}
		return listaIngredients;
	}

}
