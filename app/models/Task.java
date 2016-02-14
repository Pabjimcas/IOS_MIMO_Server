package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "task")
	public List<IngredientTask> taskIngredients;

	@JsonIgnore
	@ManyToOne
	public Recipe recipe;

	public static final Find<Long, Task> find = new Find<Long, Task>() {
	};

	// PHOTO?

	public static Task findById(Long id) {
		return find.byId(id);
	}
}
