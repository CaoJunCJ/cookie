package com.cookie.xiachufang;

import com.cookie.model.Model;

public class XiaChuFangModel extends Model{
	public String url;
	public String coverImgUrl;
	public float rating;
	public String author;
	public String tip;
	public int id;
	public XiaChuFangModel() {
		
	}
	
	public XiaChuFangModel(String url) {
		if (url == null)
			return;
		String []temp = url.split("/");
		id = Integer.parseInt(temp[temp.length-1]);
	}
 }
