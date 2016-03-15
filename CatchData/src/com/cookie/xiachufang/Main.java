package com.cookie.xiachufang;

import java.io.IOException;

import com.cookie.util.LocalUrl;

public class Main {
	
	public static void main(String[] args) {
		XiaChuFangParse x = new XiaChuFangParse(FoodStyle.JIACHANGCAI);
		try {
			x.parse();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		LocalUrl.waitDownloadTaskFinish();
	}

}
