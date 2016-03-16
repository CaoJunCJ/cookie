package com.cookie.dbcp;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBUtil {
	/**
	 * connection pool
	 */
	private static DataSource ds;

	/**
	 * the instance of this util class self
	 */
	private static DBUtil instance;

	/**
	 * constructor
	 * 
	 * @throws Exception
	 */
	private DBUtil() {
		try {
			// get the data source from the jndi context
			ds = (DataSource) ((Context) new InitialContext()
					.lookup("java:comp/env")).lookup("jdbc/CookieDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public synchronized static DBUtil getInstance() {
		if (instance == null) {
			instance = new DBUtil();
		}
		return instance;
	}

	/**
	 * get a database connection
	 * 
	 * @return a database connection
	 * @throws Exception database exception, if a database option error happens
	 */
	public Connection getConnection() {
		try {
			// get a database connection from the connection pool
			Connection conn = ds.getConnection();
			return conn;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
