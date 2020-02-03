package com.sht.service;

import java.util.List;

import com.sht.entity.Log;

public interface LogService {
	int insertLog(Log log);
	int deleteLog(int logId);
	List<Log> queryLog(Log log);
}
