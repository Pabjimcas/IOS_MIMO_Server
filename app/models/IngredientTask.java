package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class IngredientTask extends Model {

	@Id
	public Long id;

	@JsonIgnore
	@ManyToOne
	public Task task;

	@JsonIgnore
	@ManyToOne
	public Ingredient ingredient;
	public String measure;
	public Float quantity;

	public static final Find<Long, IngredientTask> find = new Find<Long, IngredientTask>() {
	};

}
