package com.cookie.xiachufang;

import com.cookie.model.Model;

public class XiaChuFangModel extends Model{
	public static final String FROM = "xiachufang";
	
	public String url;
	public String coverImgUrl;
	public float rating;
	public String author;
	public String tip;
	public int id;
	
	public XiaChuFangModel(String url) {
		if (url == null)
			return;
		this.url = url;
		String []temp = url.split("/");
		id = Integer.parseInt(temp[temp.length-1]);
	}
 }
