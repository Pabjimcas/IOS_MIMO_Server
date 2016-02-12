package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import play.data.validation.Constraints.Required;

import com.avaje.ebean.Model;

@Entity
public class Task extends Model{

	@Id
	public Long id;
	
	@Required
	public String description;
	
	public Integer seconds;
	
	@JsonIgnore
	@ManyToOne
	public Recipe recipe;
	
	@Required
	public String measure;
	
	@Required
	public Float quantity;
	
	public static final Find<Long,Task> find = new Find<Long,Task>(){};
	
	//PHOTO?
	
	public static Task findById (Long id){
		return find.byId(id);
	}
}
