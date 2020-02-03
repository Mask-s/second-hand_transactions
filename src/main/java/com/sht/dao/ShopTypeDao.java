package com.sht.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.sht.entity.ShopType;

public interface ShopTypeDao {
	@Select("Select * from tb_shop_type")
	List<ShopType> queryallType();
	
	@Select("select * from tb_shop_type where type_id=#{typeId}")
	ShopType queryTypeById(int typeId);
}
