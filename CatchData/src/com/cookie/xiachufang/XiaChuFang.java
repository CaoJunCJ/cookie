package com.cookie.xiachufang;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.cookie.model.StepModel;
import com.cookie.util.LocalUrl;

public class XiaChuFang implements Runnable{
	public String url;
	public XiaChuFangModel model;
	public FoodStyle foodStyle;
	
	public XiaChuFang(String url, FoodStyle foodStyle) {
		this.url = url;
		this.foodStyle = foodStyle;
		this.model = new XiaChuFangModel(url);
	}
	
	public void parse() throws IOException{
		
		try{
			Document doc = Jsoup.connect(url).get();
			Element ele = doc.select("div[class=block block-has-padding white-bg recipe-show]").first();
			model.name = doc.select("h1[itemprop=name]").first().text();
			model.topImgUrl = ele.select("img").first().attr("src");
			Elements ratingEle = doc.select("span[itemprop=ratingValue]");
			
			model.rating = ratingEle.size() > 0 ? Float.parseFloat(ratingEle.first().text()) : 0.0f;
			
			Elements descEle = doc.select("div[class=desc]");
			model.description = descEle.size() > 0 ? descEle.first().text() : null;
			model.ingredients = parseIngredients(doc);
			model.steps = parseStep(doc);
			Elements tipEle = doc.select("div[class=tip]");
			model.tip = tipEle.size() > 0 ? tipEle.first().text() : null;
			
			LocalUrl.netUrlToLoaclUrl(model.topImgUrl, String.format("%d_top_img.jpg", model.id));
			
			}catch(Exception e){
				System.out.println(url);
				e.printStackTrace();
				System.exit(-1);
			}
	}
	
	public Map<String, String> parseIngredients(Document doc){
		Element div = doc.select("div[class=ings]").first();
		Element tbody = div.child(0).child(0);
		Map<String, String> ings = new HashMap<String, String>();
		for (Element e : tbody.children()) {
			if (!e.attr("itemprop").equals("ingredients"))
				continue;
			String key = e.select("td[class=name has-border]").first().text();
			String value = e.select("td[class=unit has-border]").first().text();
			value = value.trim().length() == 0 ? "" : value;
			ings.put(key, value);
		}
		return ings;
	}
	
	public List<StepModel> parseStep(Document doc) {
		Element ol = doc.select("ol[itemprop=recipeInstructions]").first();
		List<StepModel> list = new ArrayList<StepModel>();
		for (Element e : ol.children()) {
			String desc = e.select("p[class=text]").first().text();
			Elements img = e.select("img");
			String imgUrl = img.size() > 0 ? img.first().attr("src") : null;
			StepModel stepModel = new StepModel();
			stepModel.description = desc;
			stepModel.imgUrl = imgUrl;
			stepModel.hasImg = imgUrl == null ? false : true;
			list.add(stepModel);
		}
		return list;
	}
	
	
	public static void main(String[] args) {

	}

	@Override
	public void run() {
		try {
			parse();
		}catch (Exception e){
			e.printStackTrace();
		}
	}

}
