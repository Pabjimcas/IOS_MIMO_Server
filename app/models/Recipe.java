package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import play.data.validation.Constraints.Required;

import com.avaje.ebean.Model;

@Entity
public class Recipe extends Model {

	@Id
	public Long id;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
	public List<Task> tasks;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
	public List<MeasureIngredient> measureIngredients;
	@Required
	public String name;

	public Integer score;

	public String author;

	public Integer difficulty;

	@Required
	public Integer portions;

	public static final Find<Long, Recipe> find = new Find<Long, Recipe>() {
	};

	public static Recipe findById(Long id) {
		return find.byId(id);
	}
}
