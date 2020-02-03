package com.sht.entity;

import java.util.Date;

public class Log {
	private String logTime;
	private Integer logId;
	private String logDes;
	private Person person;
	public String getLogTime() {
		return logTime;
	}
	public void setLogTime(String logTime) {
		this.logTime = logTime;
	}
	public Integer getLogId() {
		return logId;
	}
	public void setLogId(Integer logId) {
		this.logId = logId;
	}
	public String getLogDes() {
		return logDes;
	}
	public void setLogDes(String logDes) {
		this.logDes = logDes;
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	
}
