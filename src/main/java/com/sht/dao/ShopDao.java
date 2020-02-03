package com.sht.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sht.dto.ShopExecution;
import com.sht.entity.Shop;

public interface ShopDao {
	int insertShop(Shop shop);
	
	int deleteShop(int shopId);
	
	int updateShop(Shop shop);
	
	//根据条件模糊查询
	List<Shop> queryConditionShop(@Param("condition")String condition,@Param("rowIndex")int rowIndex,@Param("pageSize")int pageSize,@Param("userId")int userId);
	
	Shop queryShopById(int shopId);
	
	List<Shop> queryConditionShopByShop(@Param("conditionShop")Shop conditionShop,@Param("rowIndex")int rowIndex,@Param("pageSize")int pageSize,@Param("userId")int userId);

	List<Shop> queryConditionShopByPrice(@Param("lowPrice")int lowPrice,@Param("highPrice")int highPrice,@Param("type")String type,@Param("rowIndex")int rowIndex,@Param("pageSize")int pageSize,@Param("userId")int userId);

	
}
