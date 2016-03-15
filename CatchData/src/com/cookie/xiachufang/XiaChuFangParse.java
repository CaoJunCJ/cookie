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
	public static final int nThreadCount = 20;
	public static final ExecutorService taskExecutor = Executors.newFixedThreadPool(nThreadCount);
	
	public FoodStyle style;
	public int maxPageNumber;
	List<XiaChuFang> list;
	
	public XiaChuFangParse(){
		
	}
	
	public XiaChuFangParse(FoodStyle style){
		this.style = style;
		maxPageNumber = this.style.getMaxPage();
		list = new ArrayList<XiaChuFang>();
	}
	
	public void simpleParse(String reqUrl) throws IOException{
		Document doc = Jsoup.connect(reqUrl).get();
		Element ul = doc.select("div[class=normal-recipe-list]").first().child(0);
		for (int i=0; i<ul.children().size(); i++) {
			Element li = ul.child(i);
			Element infoDiv = li.select("div[class=info pure-u]").first();
			Element href = infoDiv.select("a").first();
			String infoUrl = String.format("%s%s", url, href.attr("href"));
			XiaChuFang xiaChuFang = new XiaChuFang(infoUrl, style);
			taskExecutor.execute(xiaChuFang);
			list.add(xiaChuFang);
		}
		
	}
	
	public void parse() throws IOException {
		
		for (int i=1; i<maxPageNumber; i++) {
			String reqUrl = String.format("%s/category/%s/?page=%d", url, style, i);
			simpleParse(reqUrl);
		}
		
		taskExecutor.shutdown();
		
		try {
			  taskExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
			} catch (InterruptedException e) {
			  e.printStackTrace();
			}
		
		print();
	}
	
	public void print() {
		System.out.println(String.format("size:%d", list.size()));
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
	
}
