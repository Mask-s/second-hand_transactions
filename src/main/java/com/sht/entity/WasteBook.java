package com.sht.entity;

import java.util.Date;

public class WasteBook {
	private Integer wasteId;
	private String exchangeHour;
	//正或负
	private Integer wasteStatus;
	//金额
	private Integer wastePrice;
	//描述
	private String wasteDes;
	private Person person;
	
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public Integer getWasteId() {
		return wasteId;
	}
	public void setWasteId(Integer wasteId) {
		this.wasteId = wasteId;
	}
	public String getExchangeHour() {
		return exchangeHour;
	}
	public void setExchangeHour(String exchangeHour) {
		this.exchangeHour = exchangeHour;
	}
	public Integer getWasteStatus() {
		return wasteStatus;
	}
	public void setWasteStatus(Integer wasteStatus) {
		this.wasteStatus = wasteStatus;
	}
	public Integer getWastePrice() {
		return wastePrice;
	}
	public void setWastePrice(Integer wastePrice) {
		this.wastePrice = wastePrice;
	}
	public String getWasteDes() {
		return wasteDes;
	}
	public void setWasteDes(String wasteDes) {
		this.wasteDes = wasteDes;
	}
	
	
}
