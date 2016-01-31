package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.data.validation.Constraints.Required;

import com.avaje.ebean.Model;

@Entity
public class Ingredient extends Model{
	
	@Id
	public Long id;
	
	@Required
	public String name;
	
	@Required
	public String measure;
	
	@Required
	public Float quantity;
	
	@Required
	public Boolean frozen;
	
	@Required
	public String category;

}
