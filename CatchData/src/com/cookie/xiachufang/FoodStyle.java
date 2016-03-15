package com.cookie.xiachufang;

public enum FoodStyle {
	JIACHANGCAI(40076), // 家常菜
	KUAISHOUCAI(40077), // 快手菜
	XIAFANCAI(40078), // 下饭菜
	SUCAI(51848), // 素菜
	DAYUDAROU(52354), // 大鱼大肉
	XIAJIUCAI(51743), // 下酒菜
	XIAOQINGXIN(52351), // 小清新
	CHUANGYICAI(51940);// 创意菜

	public int code;

	private FoodStyle(int code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return String.valueOf(code);
	}

	public int getMaxPage() {
		int maxPageNumber = 5;
		switch (this) {
		case JIACHANGCAI:
			maxPageNumber = 2;
			break;
			
		case KUAISHOUCAI:
			maxPageNumber = 20;
			break;
			
		case XIAFANCAI:
			maxPageNumber = 20;
			break;
			
		case SUCAI:
			maxPageNumber = 20;
			break;
			
		case DAYUDAROU:
			maxPageNumber = 20;
			break;
			
		case XIAJIUCAI:
			maxPageNumber = 20;
			break;
			
		case XIAOQINGXIN:
			maxPageNumber = 20;
			break;
			
		case CHUANGYICAI:
			maxPageNumber = 20;
			break;
			
		default:
			maxPageNumber = 5;
			break;
		}

		return maxPageNumber;
	}

}
