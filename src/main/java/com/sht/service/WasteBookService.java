package com.sht.service;

import java.util.List;

import com.sht.entity.WasteBook;

public interface WasteBookService {
	int insertWasteBook(WasteBook wasteBook);
	List<WasteBook> queryWasteBook(WasteBook wasteBook);
}
