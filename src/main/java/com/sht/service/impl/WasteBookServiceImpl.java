package com.sht.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sht.dao.WasteBookDao;
import com.sht.entity.WasteBook;
import com.sht.service.WasteBookService;

@Service
public class WasteBookServiceImpl implements WasteBookService{

	@Autowired
	private WasteBookDao wasteBookDao;
	@Override
	public int insertWasteBook(WasteBook wasteBook) {
		return wasteBookDao.insertWasteBook(wasteBook);
	}

	@Override
	public List<WasteBook> queryWasteBook(WasteBook wasteBook) {
		return wasteBookDao.queryWasteBook(wasteBook);
	}

}
