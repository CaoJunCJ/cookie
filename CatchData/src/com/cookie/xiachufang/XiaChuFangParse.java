package com.cookie.xiachufang;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class XiaChuFangParse {
	public static final String url = "http://www.xiachufang.com";
	public static final int nThreadCount = 10;
	public static final ExecutorService taskExecutor = Executors.newFixedThreadPool(nThreadCount);
	
	public void simpleParse() throws IOException{
		Document doc = Jsoup.connect(String.format("%s/category/%s/", url, FoodStyle.XIAOQINGXIN)).get();
		Element ul = doc.select("div[class=normal-recipe-list]").first().child(0);
		List<XiaChuFang> list = new ArrayList<XiaChuFang>();
		for (int i=0; i<ul.children().size(); i++) {
			Element li = ul.child(i);
			Element infoDiv = li.select("div[class=info pure-u]").first();
			Element href = infoDiv.select("a").first();
			String infoUrl = String.format("%s%s", url, href.attr("href"));
			XiaChuFang xiaChuFang = new XiaChuFang(infoUrl);
			taskExecutor.execute(xiaChuFang);
			list.add(xiaChuFang);
		}
		
		taskExecutor.shutdown();
		
		try {
			  taskExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
			} catch (InterruptedException e) {
			  e.printStackTrace();
			}
		
		for (XiaChuFang model : list) {
			XiaChuFangModel x = model.model;
			System.out.println(String.format("name:%s", x.name));
			System.out.println(String.format("url:%s", x.url));
			System.out.println(String.format("rating:%s", x.rating));
			System.out.println(String.format("img:%s", x.topImgUrl));
			System.out.println(String.format("desc:%s", x.description));
			x.printIngredients();
			x.printSteps();
			System.out.println(String.format("tip:%s\r\n\n", x.tip));
		}
		
	}
	
	public static void main(String[] args) throws Exception{
		XiaChuFangParse x = new XiaChuFangParse();
		x.simpleParse();
	}
	
}
