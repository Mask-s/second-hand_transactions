package com.sht.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sht.dao.MessageDao;
import com.sht.entity.Message;
import com.sht.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService{
	@Autowired
	private MessageDao messageDao;
	
	@Override
	public int insertMessage(Message message) {
		return messageDao.insertMessage(message);
	}

	@Override
	public List<Message> queryMessage(Message message) {
		return messageDao.queryMessage(message);
	}

}
