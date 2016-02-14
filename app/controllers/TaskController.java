package controllers;

import helpers.ControllerHelper;

import java.util.List;

import models.Recipe;
import models.Task;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.fasterxml.jackson.databind.JsonNode;

public class TaskController extends Controller {

	public Result newTask(Long idRecipe) {
		Form<Task> form = Form.form(Task.class).bindFromRequest();
		if (form.hasErrors()) {
			return badRequest(ControllerHelper.errorJson(2, "Datos incorrectos", form.errorsAsJson()));
		}
		Recipe recipe = Recipe.findById(idRecipe);
		if (recipe == null) {
			return notFound("La receta no existe");
		}
		Task task = form.get();
		task.recipe = recipe;
		task.save();
		if (request().accepts("application/json")) {
			return ok(Json.toJson(task));
		}
		return badRequest("Unsupported format");
	}

	public Result newTasks(Long idRecipe) {
		Recipe recipe = Recipe.findById(idRecipe);
		if (recipe == null) {
			return notFound("La receta no existe");
		}
		JsonNode json = request().body().asJson();

		for (JsonNode t : json.withArray("Tasks")) {
			Task task = new Task();
			task.name = t.get("name").asText();
			task.description = t.get("description").asText();
			if (t.get("seconds") != null) {
				task.seconds = t.get("seconds").asInt();
			}
			task.recipe = recipe;
			task.save();

		}

		List<Task> taskList = recipe.tasks;

		if (taskList.size() == 0) {
			return badRequest("No se han encontrado resultados en la búsqueda");
		}
		else {
			if (request().accepts("application/json")) {
				return ok(Json.toJson(taskList));
			}
			return badRequest("Unsupported format");
		}
	}

	public Result getTask(Long idTask) {
		Task task = Task.findById(idTask);
		if (task == null) {
			return badRequest("La tarea no existe");
		}
		if (request().accepts("application/json")) {
			return ok(Json.toJson(task));
		}
		return badRequest("Unsupported format");
	}

	public Result getTasks(Long idRecipe) {

		Recipe recipe = Recipe.findById(idRecipe);

		if (recipe == null) {
			return badRequest("La receta no existe");
		}

		List<Task> taskList = recipe.tasks;

		if (taskList.size() == 0) {
			return badRequest("No se han encontrado resultados en la búsqueda");
		}
		else {
			if (request().accepts("application/json")) {
				return ok(Json.toJson(taskList));
			}
			return badRequest("Unsupported format");
		}
	}

}
