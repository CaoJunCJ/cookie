package com.cookie.xiachufang;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.cookie.database.MySQL;
import com.cookie.util.LocalUrl;

public class Main {
	
	public static void main(String[] args) {
		XiaChuFangParse x1 = new XiaChuFangParse(FoodStyle.JIACHANGCAI);
		XiaChuFangParse x2 = new XiaChuFangParse(FoodStyle.KUAISHOUCAI);
		try {
			x1.parse();
			x2.parse();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		List<XiaChuFang> list = x1.list;
		list.addAll(x2.list);
		
		Map<Integer, String> categoryTypeMap = XiaChuFang.categoryTypeMap;
		for (int key : categoryTypeMap.keySet()) {
			String value = categoryTypeMap.get(key);
			System.out.println(String.format("%d : %s", key, value));
		}
		
		MySQL mysql = new MySQL();
		for (XiaChuFang x : list) {
			mysql.addRecipe(x.model);
			mysql.addIngredients(x.model.ingredients, x.model.id);
			mysql.addSteps(x.model.steps, x.model.id);
		}
		
		mysql.addAllCategory(categoryTypeMap);
		
		LocalUrl.waitDownloadTaskFinish();
	}

}
