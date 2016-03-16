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

import com.cookie.util.JSONTool;
import com.cookie.util.ResultType;
import com.cookie.util.StringUtil;

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
		String strRecipeId = request.getParameter("recipe_id");
		if (StringUtil.isNull(strRecipeId)) {
			log.debug(String.format("[%s] %s", "recipe_id", ResultType.ARGUMENT_INVALID_OR_MISS.toString()));
			out.print(JSONTool.getErrorResult(ResultType.ARGUMENT_INVALID_OR_MISS));
			return;
		}
		
		int recipeId;
		
		try{
			recipeId = Integer.parseInt(strRecipeId);
		}catch(Exception e){
			log.debug(String.format("[%s] %s", "recipe_id", ResultType.ARGUMENT_INVALID_OR_MISS.toString()));
			out.print(JSONTool.getErrorResult(ResultType.ARGUMENT_INVALID_OR_MISS));
			return;
		}
		
		com.cookie.model.Recipe r = com.cookie.model.Recipe.getRecipeById(recipeId);
		
		if (r == null) {
			log.debug("com.cookie.model.Recipe is null");
			out.print(JSONTool.getErrorResult(ResultType.INTERNAL_ERROR));
			return;
		}
		
		String result = JSONTool.getSuccessResult(r);
		out.print(result);
		return;
	}

}
