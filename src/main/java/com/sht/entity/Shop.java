package com.sht.entity;

import java.util.Date;

public class Shop {
	private Integer shopId;
	private String shopName;
	private String shopImg;
	private String shopDes;
	//0:下架 1：上架中
	private Integer shopStatus;
	//浏览人数
	private Integer counts;
	private Integer price;
	//商品个数
	private Integer shopNumber;
	//上架时间
	private String putAwayTime;
	//下架时间
	private String soldOutTime;
	//上传者
	private Person person;
	//商品类型
	private ShopType shoptype;
	public Integer getShopId() {
		return shopId;
	}
	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getShopImg() {
		return shopImg;
	}
	public void setShopImg(String shopImg) {
		this.shopImg = shopImg;
	}
	public String getShopDes() {
		return shopDes;
	}
	public void setShopDes(String shopDes) {
		this.shopDes = shopDes;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public String getPutAwayTime() {
		return putAwayTime;
	}
	public void setPutAwayTime(String putAwayTime) {
		this.putAwayTime = putAwayTime;
	}
	public String getSoldOutTime() {
		return soldOutTime;
	}
	public void setSoldOutTime(String soldOutTime) {
		this.soldOutTime = soldOutTime;
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public ShopType getShoptype() {
		return shoptype;
	}
	public void setShoptype(ShopType shoptype) {
		this.shoptype = shoptype;
	}
	public Integer getShopStatus() {
		return shopStatus;
	}
	public void setShopStatus(Integer shopStatus) {
		this.shopStatus = shopStatus;
	}
	public Integer getCounts() {
		return counts;
	}
	public void setCounts(Integer counts) {
		this.counts = counts;
	}
	public Integer getShopNumber() {
		return shopNumber;
	}
	public void setShopNumber(Integer shopNumber) {
		this.shopNumber = shopNumber;
	}
	
}
