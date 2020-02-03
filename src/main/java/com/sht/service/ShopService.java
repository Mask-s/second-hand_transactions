package com.sht.service;

import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sht.dto.ShopExecution;
import com.sht.entity.Shop;
import com.sht.exceptions.MyRuntimeException;

public interface ShopService {
	ShopExecution insertShop(Shop shop,InputStream shopInputStream,String fileName);
	
	int deleteShop(int shopId);
	
	ShopExecution updateShop(Shop shop,InputStream shopInputStream,String fileName);
	
	//根据条件模糊查询
	List<Shop> queryConditionShop(String condition,int rowIndex,int pageSize);
	
	Shop queryShopById(int shopId);
	
	List<Shop> queryConditionShopByShop(Shop conditionShop,int rowIndex,int pageSize);

	List<Shop> queryConditionShopByPrice(int lowPrice,int highPrice,String type,int rowIndex,int pageSize);

	int modifyShop(Shop shop);

	
}
