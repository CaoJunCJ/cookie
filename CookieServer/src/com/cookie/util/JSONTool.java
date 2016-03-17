package com.cookie.util;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;

public class JSONTool {
	
	static final String RESULT_KEY = "result";
	static final String MESSAGE_KEY = "message";
	
	public static String getSimpleSuccessResult() {
		JSONObject json = new JSONObject();
		json.put(RESULT_KEY, 0);
		return json.toString(1);
	}
	
	public static String getSuccessResult(String key, JSONObject value) {
		JSONObject json = new JSONObject();
		json.put(RESULT_KEY, 0);
		json.put(key, value);
		return json.toString(1);
	}
	
	public static String getSuccessResult(String key, JSONArray value) {
		JSONObject json = new JSONObject();
		json.put(RESULT_KEY, 0);
		json.put(key, value);
		return json.toString(1);
	}
	
	public static String getSuccessResult(String key, Object o) {
		if ( o instanceof List) {
			return getSuccessResult(key, new JSONArray(new Gson().toJson(o)));
		} else {
			return getSuccessResult(key, new JSONObject(new Gson().toJson(o)));
		}
	}
	
	public static String getSuccessResult(Object o) {
		return getSuccessResult(o.getClass().getSimpleName(), o);
	}
	
	public static String getErrorResult(ResultType result) {
		JSONObject json = new JSONObject();
		
		String message = "Unknow Error.";
		switch(result) {
		
		case TIMEOUT:
			message = "Time out.";
			break;
			
		case ARGUMENT_INVALID_OR_MISS:
			message = "Invalid argument or miss.";
			break;
			
		case INTERNAL_ERROR:
			message = "Internal error.";
			break;
			
		default:
			break;
		}
		
		json.put(RESULT_KEY, result.errorCode);
		json.put(MESSAGE_KEY, message);
		
		return json.toString(1);
	}
}
