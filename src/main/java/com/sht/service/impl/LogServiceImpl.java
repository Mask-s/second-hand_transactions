package com.sht.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sht.dao.LogDao;
import com.sht.entity.Log;
import com.sht.service.LogService;

@Service
public class LogServiceImpl implements LogService{
	@Autowired
	private LogDao logDao;
	@Override
	public int insertLog(Log log) {
		return logDao.insertLog(log);
	}

	@Override
	public int deleteLog(int logId) {
		return logDao.deleteLog(logId);
	}

	@Override
	public List<Log> queryLog(Log log) {
		return logDao.queryLog(log);
	}

}
