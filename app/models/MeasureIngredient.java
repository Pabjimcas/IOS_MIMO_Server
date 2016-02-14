package models;

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

}
