package com.cookie.xiachufang;

import java.io.IOException;

public class Main {
	
	public static void main(String[] args) {
		XiaChuFangParse x = new XiaChuFangParse();
		try {
			x.simpleParse();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
