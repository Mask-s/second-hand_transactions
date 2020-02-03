package com.sht.enums;

public enum ShopStateEnum {
	CHECK(0,"审核中"),OFFLINE(-1,"已下架"),SUCCESS(1,"上架成功")
	,PASS(2,"认证通过"),INNER_ERROR(-1001,"内部系统错误"),NULL_SHOPID(-1002,"ShopId为空")
	,NULL_SHOP(-1003,"shop信息为空");
	private int state;
	private String stateInfo;
	
	private ShopStateEnum(int state,String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}
	
	/**
	 * 依据传入的state返回相应的enum值
	 */
	public static ShopStateEnum stateof(int state) {
		for(ShopStateEnum stateEnum:values()) {
			if(stateEnum.getState() == state) {
				return stateEnum;
			}
		}
		return null;
	}

	public int getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

}
