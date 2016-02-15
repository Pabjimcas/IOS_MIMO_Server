package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import play.data.validation.Constraints.Required;

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

	public static Ingredient findById(Long id) {
		return find.byId(id);
	}

}
