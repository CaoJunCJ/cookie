package com.cookie.core.api;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.impl.LogFactoryImpl;
import org.json.JSONObject;

import com.cookie.util.StringUtil;
import com.google.gson.Gson;

/**
 * Servlet implementation class Recipe
 */
@WebServlet("/recipe")
public class Recipe extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Recipe() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		Log log = LogFactoryImpl.getLog(this.getClass());
		String recipeId = request.getParameter("recipe_id");
		JSONObject json = new JSONObject();
		if (StringUtil.isNull(recipeId)) {
			json.put("result", -3);
			out.print(json.toString(1));
			log.debug("agrs miss.");
			return;
		}
		
		
		com.cookie.model.Recipe r = com.cookie.model.Recipe.getRecipeById(Integer.parseInt(recipeId));
		
		json.put("result", "0");
		Gson gson = new Gson();

		JSONObject recipeJson = new JSONObject(gson.toJson(r));
		
		json.put("recipe", recipeJson);
		out.print(json.toString(1));
		log.debug(recipeJson);
		System.out.println(new JSONObject(recipeJson).toString(1));
		return;
	}

}
