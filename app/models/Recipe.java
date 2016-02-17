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

	public String photo;

	@Required
	public Integer portions;

	public static final Find<Long, Recipe> find = new Find<Long, Recipe>() {
	};

	public static Recipe findByName(String nombre) {
		if (find.where().eq("name", nombre).findList().size() == 0) {
			return null;
		}
		else {
			return find.where().eq("name", nombre).findList().get(0);
		}

	}

	public static boolean existe(String nombre) {
		return Recipe.findByName(nombre) != null;
	}

	public static Recipe findById(Long id) {
		return find.byId(id);
	}
}
