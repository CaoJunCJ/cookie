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

import com.cookie.dbcp.MySQLDataBase;
import com.cookie.util.JSONTool;
import com.cookie.util.ResultType;

/**
 * Servlet implementation class RandomRecipe
 */
@WebServlet("/v1/RandomRecipe")
public class RandomRecipe extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RandomRecipe() {
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
		PrintWriter out = response.getWriter();
		Log log = LogFactoryImpl.getLog(this.getClass());
		try {
			out.print(JSONTool.getSuccessResult("data", MySQLDataBase.randomRecipes(10)));
		}catch(Exception e) {
			e.printStackTrace();
			log.debug("the result is error.");
			out.print(JSONTool.getErrorResult(ResultType.INTERNAL_ERROR));
		}
	}

}
