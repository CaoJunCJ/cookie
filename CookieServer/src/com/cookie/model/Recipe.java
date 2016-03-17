package com.cookie.model;

import java.util.List;

import com.cookie.dbcp.MySQLDataBase;

public class Recipe {
	// server ip
	public static final String SERVER_URL = "http://10.0.1.102:8080/CookieServer";
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
			r.localTopImgPath = String.format("%s%s", SERVER_URL, r.localTopImgPath.substring(2, r.localTopImgPath.length()));
			r.ingredients = MySQLDataBase.getIngredientsByRecipeId(id);
			r.steps = MySQLDataBase.getStepsByRecipeId(id);
			for (Step s : r.steps) {
				if (!s.hasImg)
					continue;
				s.localImgPath = String.format("%s%s", SERVER_URL, s.localImgPath.substring(2, s.localImgPath.length()));
			}
			return r;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
