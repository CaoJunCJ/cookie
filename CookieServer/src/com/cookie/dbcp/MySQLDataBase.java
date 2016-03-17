package com.cookie.dbcp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.cookie.model.Ingredients;
import com.cookie.model.Recipe;
import com.cookie.model.Step;


public class MySQLDataBase {
	static String queryRecipeById = "SELECT * FROM cookiedb.recipe WHERE recipe_id=?";
	static String queryStepsByRecipeId = "SELECT * FROM cookiedb.steps WHERE recipe_id=? ORDER BY `index`";
	static String queryIngredientsByRecipeId = "SELECT * FROM cookiedb.ingredients WHERE recipe_id=? ORDER BY `index`";
	static String queryRandomPecipes = "SELECT recipe_id FROM cookiedb.recipe ORDER BY RAND() LIMIT ?";
	
	public static Recipe getRecipeById(int id) throws Exception  {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Recipe r = null;
		try {
			con = DBUtil.getInstance().getConnection();
			ps = con.prepareStatement(queryRecipeById);
			ps.setInt(1, id);
			rs = ps.executeQuery();
	        while(rs.next()){
	        	r = new Recipe();
	        	r.id = id;
	        	r.name = rs.getString("name");
	        	r.rating = rs.getFloat("rating");
	        	r.description = rs.getString("description");
	        	r.localTopImgPath = rs.getString("top_img_path");
	        	r.tip = rs.getString("tip");
	        	r.author = rs.getInt("author_id");
	        	String category = rs.getString("categorys_id_arrays");
	        	String temp[] = category.substring(1, category.length()-2).split(",");
	        	r.categoryType = Arrays.asList(temp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		} finally {
			closeResourse(ps, rs, con);
		}
		return r;
	}
	
	public static List<Step> getStepsByRecipeId(int id) throws Exception  {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Step> list = new ArrayList<Step>();
		try {
			con = DBUtil.getInstance().getConnection();
			ps = con.prepareStatement(queryStepsByRecipeId);
			ps.setInt(1, id);
			rs = ps.executeQuery();
	        while(rs.next()){
	        	Step s = new Step();
	        	s.id = rs.getInt("steps_id");
	        	s.description = rs.getString("step_context");
	        	s.hasImg = rs.getBoolean("has_img");
	        	s.localImgPath = rs.getString("step_img_path");
	        	s.index = rs.getInt("index");
	        	s.recipeId = id;
	        	list.add(s);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		} finally {
			closeResourse(ps, rs, con);
		}
		return list;
	}
	
	public static List<Ingredients> getIngredientsByRecipeId(int id) throws Exception  {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Ingredients> list = new ArrayList<Ingredients>();
		try {
			con = DBUtil.getInstance().getConnection();
			ps = con.prepareStatement(queryIngredientsByRecipeId);
			ps.setInt(1, id);
			rs = ps.executeQuery();
	        while(rs.next()){
	        	Ingredients s = new Ingredients();
	        	s.name = rs.getString("name");
	        	s.unit = rs.getString("value");
	        	s.index = rs.getInt("index");
	        	s.id = rs.getInt("ingredients_id");
	        	s.recipeId = id;
	        	list.add(s);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		} finally {
			closeResourse(ps, rs, con);
		}
		return list;
	}
	
	public static List<Integer> randomRecipes(int size) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Integer> list = new ArrayList<Integer>();
		try {
			con = DBUtil.getInstance().getConnection();
			ps = con.prepareStatement(queryRandomPecipes);
			ps.setInt(1, size);
			rs = ps.executeQuery();
	        while(rs.next()){
	        	list.add(rs.getInt("recipe_id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		} finally {
			closeResourse(ps, rs, con);
		}
		return list;
	}
	
	public static void closeResourse(Statement stmt, ResultSet rs, Connection con){
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException sqlEx) {
			} // ignore

			rs = null;
		}

		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException sqlEx) {
			} // ignore

			stmt = null;
		}
		
		if (con != null)
			try {
				con.close();
			} catch (Exception ignore) {
				ignore.printStackTrace();
			}
	}
}
