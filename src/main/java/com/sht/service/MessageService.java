package com.sht.service;

import java.util.List;

import com.sht.entity.Message;

public interface MessageService {
	int insertMessage(Message message);
	List<Message> queryMessage(Message message);
}
