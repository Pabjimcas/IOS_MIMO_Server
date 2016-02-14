package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import play.data.validation.Constraints.Required;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Ingredient extends Model{
	
	@Id
	public Long id;
	
	@JsonIgnore
	@ManyToMany
	public List<Recipe> recipes;
	
	@JsonIgnore
	@OneToMany(cascade=CascadeType.ALL, mappedBy="ingredient")
	public List<IngredientTask> ingredientTasks;
	
	@Required
	public String name;
	
	@Required
	public Boolean frozen;
	
	@Required
	public String category;
	
	@Required
	public String baseType;
	
	public static final Find<Long,Ingredient> find = new Find<Long,Ingredient>(){};
	
	public static Ingredient findById (Long id){
		return find.byId(id);
	}

}
