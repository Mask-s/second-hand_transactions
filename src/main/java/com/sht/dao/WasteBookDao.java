package com.sht.dao;

import java.util.List;

import com.sht.entity.WasteBook;

public interface WasteBookDao {
	int insertWasteBook(WasteBook wasteBook);
	List<WasteBook> queryWasteBook(WasteBook wasteBook);
}
