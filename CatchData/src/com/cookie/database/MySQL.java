package com.cookie.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import com.cookie.model.StepModel;
import com.cookie.xiachufang.XiaChuFangModel;

public class MySQL {
	public static Connection conn = null;
	static {
		try {
			conn = DriverManager
					.getConnection("jdbc:mysql://localhost:3306/cookiedb?useUnicode=true&characterEncoding=utf-8",
							"root", "cerosoft");
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public void test() {
		Statement stmt = null;
		ResultSet rs = null;

		try {

			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT foo FROM bar");

			// or alternatively, if you don't know ahead of time that
			// the query will be a SELECT...

			if (stmt.execute("SELECT foo FROM bar")) {
				rs = stmt.getResultSet();
			}

			// Now do something with the ResultSet ....
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		} finally {
			// it is a good idea to release
			// resources in a finally{} block
			// in reverse-order of their creation
			// if they are no-longer needed

			closeResourse(stmt, rs);
		}
	}
	
	public void closeResourse(Statement stmt, ResultSet rs){
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
	}
	
	public void printSQLExcpetion(SQLException ex) {
		ex.printStackTrace();
		System.out.println("SQLException: " + ex.getMessage());
		System.out.println("SQLState: " + ex.getSQLState());
		System.out.println("VendorError: " + ex.getErrorCode());
		//System.exit(-1);
	}
	
	public boolean checkHasRecipe(int recipeId) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT recipe_id from  cookiedb.recipe WHERE recipe_id=?";
		try {
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, recipeId);
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				return true;
			}
			
			conn.commit();
		} catch (SQLException ex) {
			printSQLExcpetion(ex);
		} finally {
			closeResourse(stmt, rs);
		}
		return false;
	}
	
	public void addRecipe(XiaChuFangModel model) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "INSERT INTO cookiedb.recipe (recipe_id, name, rating, description, "
				+ "top_img_path, tip, data_from, categorys_id_arrays, author_id) VALUES(?,?,?,?,?,?,?,?,?)";
		try {
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, model.id);
			stmt.setString(2, model.name);
			stmt.setFloat(3, model.rating);
			stmt.setString(4, model.description);
			stmt.setString(5, model.localTopImgPath);
			stmt.setString(6, model.tip);
			stmt.setString(7, XiaChuFangModel.FROM);
			stmt.setString(8, model.categoryType.toString());
			stmt.setInt(9, 1);
			stmt.executeUpdate();
			conn.commit();
		} catch (SQLException ex) {
			printSQLExcpetion(ex);
		} finally {
			closeResourse(stmt, rs);
		}
	}
	
	public void addSteps(List<StepModel> list, int recipeId) {
		if (list == null || list.size() == 0)
			return;
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "INSERT INTO cookiedb.steps (`index`, step_context, step_img_path, has_img, recipe_id)"
				+ " VALUES(?,?,?,?,?)";
		try {
			conn.setAutoCommit(false);
			for (int i=0; i<list.size(); i++) {
				StepModel model = list.get(i);
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, i);
				stmt.setString(2, model.description);
				stmt.setString(3, model.localImgPath);
				stmt.setBoolean(4, model.hasImg);
				stmt.setInt(5, recipeId);
				stmt.executeUpdate();
			}
			conn.commit();
		} catch (SQLException ex) {
			printSQLExcpetion(ex);
		} finally {
			closeResourse(stmt, rs);
		}
	}
	
	public void addIngredients(Map<String, String> map, int recipeId) {
		if (map == null || map.size() == 0)
			return;
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "INSERT INTO cookiedb.ingredients (name, value, `index`, recipe_id)"
				+ " VALUES(?,?,?,?)";
		try {
			conn.setAutoCommit(false);
			int index = 0;
			for (String key : map.keySet()) {
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, key);
				stmt.setString(2, map.get(key));
				stmt.setInt(3, index);
				stmt.setInt(4, recipeId);
				stmt.executeUpdate();
				index++;
			}
			conn.commit();
		} catch (SQLException ex) {
			printSQLExcpetion(ex);
		} finally {
			closeResourse(stmt, rs);
		}
	}
	
	public void addAllCategory(Map<Integer, String> map) {
		if (map == null || map.size() == 0)
			return;
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "INSERT INTO cookiedb.category (category_id, name)"
				+ " VALUES(?,?)";
		try {
			conn.setAutoCommit(false);
			for (Integer key : map.keySet()) {
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, key);
				stmt.setString(2, map.get(key));
				stmt.executeUpdate();
			}
			conn.commit();
		} catch (SQLException ex) {
			printSQLExcpetion(ex);
		} finally {
			closeResourse(stmt, rs);
		}
	}
}
