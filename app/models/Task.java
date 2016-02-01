package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.data.validation.Constraints.Required;

import com.avaje.ebean.Model;

@Entity
public class Task extends Model{

	@Id
	public Long id;
	
	@Required
	public String description;
	
	public Integer seconds;
	
	public static final Find<Long,Task> find = new Find<Long,Task>(){};
	
	//PHOTO?
}
