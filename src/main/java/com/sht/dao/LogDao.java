package com.sht.dao;

import java.util.List;

import com.sht.entity.Log;

public interface LogDao {
	int insertLog(Log log);
	int deleteLog(int logId);
	List<Log> queryLog(Log log);
}
