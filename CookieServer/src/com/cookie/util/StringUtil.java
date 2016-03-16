package com.cookie.util;

import java.util.Set;

import org.json.JSONObject;

public class StringUtil {
	
	public static void main(String args[]){
		
	}

	
	public static boolean isNull(String s){
		boolean res = false;
		if (s == null || s.trim().length() < 1)
			res = true;
		return res;
	}
	
	public static boolean jsonArgsNullCheck(JSONObject json){
		
		if (json == null)
			return false;
		System.out.println(json.toString(1));
		Set<?> set = json.keySet();
		for (Object s : set){
			String key = (String)s;
			Object value = json.opt(key);
			if (value instanceof String){
				if (isNull((String)value))
					return false;
			}
		}
		
		return true;
	}
}
