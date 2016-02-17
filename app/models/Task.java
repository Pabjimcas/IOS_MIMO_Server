package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.data.validation.Constraints.Required;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Task extends Model {

	@Id
	public Long id;
	@Required
	public String name;

	@Required
	public String description;

	public Integer seconds;

	@JsonIgnore
	@ManyToOne
	public Recipe recipe;
	public String photo;
	public static final Find<Long, Task> find = new Find<Long, Task>() {
	};

	public static Task findByName(String nombre) {
		if (find.where().eq("name", nombre).findList().size() == 0) {
			return null;
		}
		else {
			return find.where().eq("name", nombre).findList().get(0);
		}

	}

	public static boolean existeTareaReceta(String task, Long idReceta) {
		Recipe recipe = Recipe.findById(idReceta);
		if (find.where().eq("name", task).eq("recipe", recipe).findList().size() == 0) {
			return false;
		}
		else {
			return true;
		}
	}

	public static boolean existe(String nombre) {
		return Task.findByName(nombre) != null;
	}

	public static Task findById(Long id) {
		return find.byId(id);
	}
}
