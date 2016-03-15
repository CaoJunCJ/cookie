package com.cookie.model;

import java.util.List;
import java.util.Map;

public class Model {
	public String name;
	public String topImgUrl;
	public String description;
	public Map<String, String> ingredients;
	public List<StepModel> steps;
	
	public void printIngredients(){
		if (ingredients == null) 
			return;
		
		System.out.println("------------------");
		
		for (String key : ingredients.keySet()) {
			String value = ingredients.get(key);
			System.out.println(String.format("%s : %s", key, value));
		}
		System.out.println("------------------\r\n");
	}
	
	public void printSteps(){
		if (steps == null) 
			return;
		System.out.println("------------------");
		int index = 1;
		for (StepModel s : steps) {
			System.out.println(String.format("%d %s\n[%s]", index, s.description, s.imgUrl));
			index++;
		}
		System.out.println("------------------\r\n");
	}
}
