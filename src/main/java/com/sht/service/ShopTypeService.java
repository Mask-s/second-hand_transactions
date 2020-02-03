package com.sht.service;

import java.util.List;

import com.sht.entity.ShopType;

public interface ShopTypeService {
	ShopType queryTypeById(int typeId);
	List<ShopType> queryallType();
}
