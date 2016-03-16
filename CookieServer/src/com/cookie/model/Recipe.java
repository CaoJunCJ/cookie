package com.cookie.model;

import java.util.List;

import com.cookie.dbcp.MySQLDataBase;

public class Recipe {
	public int id;
	public String name;
	public String localTopImgPath;
	public String description;
	public List<String> categoryType;
	public List<Ingredients> ingredients;
	public List<Step> steps;
	public float rating;
	public int author;
	public String tip;
	
	public static Recipe getRecipeById(int id){
		try {
			Recipe r = MySQLDataBase.getRecipeById(id);
			r.ingredients = MySQLDataBase.getIngredientsByRecipeId(id);
			r.steps = MySQLDataBase.getStepsByRecipeId(id);
			return r;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
