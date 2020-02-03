package com.sht.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sht.dao.ShopTypeDao;
import com.sht.entity.ShopType;
import com.sht.service.ShopTypeService;

@Service
public class ShopTypeServiceImpl implements ShopTypeService{
	@Autowired
	private ShopTypeDao shopTypeDao;
	@Override
	public ShopType queryTypeById(int typeId) {
		return shopTypeDao.queryTypeById(typeId);
	}

	@Override
	public List<ShopType> queryallType() {
		return shopTypeDao.queryallType();
	}

}
