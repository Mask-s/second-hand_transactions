package com.sht.dto;

import java.util.List;

import com.sht.entity.Shop;
import com.sht.enums.ShopStateEnum;



public class ShopExecution {
	//结果状态
	private int state;
	
	//状态标识
	private String stateinfo;
	
	//商品数量
	private int count;
	
	//操作的shop（增删改的时候用）
	private Shop shop;
	
	//shop列表（查询商品列表时用）
	private List<Shop> shopList;
	
	public ShopExecution() {
		
	}
	//操作失败的时候使用的构造器
	public ShopExecution(ShopStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateinfo = stateEnum.getStateInfo();
	}
	//操作成功的时候使用的构造器
	public ShopExecution(ShopStateEnum stateEnum,Shop shop) {
		this.state = stateEnum.getState();
		this.stateinfo = stateEnum.getStateInfo();
		this.shop = shop;
	}
	//操作成功的时候使用的构造器
	public ShopExecution(ShopStateEnum stateEnum,List<Shop> shopList) {
		this.state = stateEnum.getState();
		this.stateinfo = stateEnum.getStateInfo();
		this.shopList = shopList;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getStateinfo() {
		return stateinfo;
	}
	public void setStateinfo(String stateinfo) {
		this.stateinfo = stateinfo;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Shop getShop() {
		return shop;
	}
	public void setShop(Shop shop) {
		this.shop = shop;
	}
	public List<Shop> getShopList() {
		return shopList;
	}
	public void setShopList(List<Shop> shopList) {
		this.shopList = shopList;
	}
	
	
}
