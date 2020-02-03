package com.sht.dao;

import java.util.List;

import com.sht.entity.Message;

public interface MessageDao {
	int insertMessage(Message message);
	List<Message> queryMessage(Message message);
}
