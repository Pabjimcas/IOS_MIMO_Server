package models;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import play.data.validation.Constraints.Required;

import com.avaje.ebean.Model;

@Entity
public class Recipe extends Model{
	
	@Id
	public Long id;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="task")
	public List<Task> tasks;
	
	@ManyToMany(mappedBy = "recipes")
	public Set<Ingredient> ingredients;
	
	@Required
	public String name;
	
	public Integer score;
	
	public String author;
	
	@Required
	public Integer portions;
}
