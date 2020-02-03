package com.sht.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sht.BaseTest;
import com.sht.dao.ShopDao;
import com.sht.entity.Shop;
import com.sht.entity.ShopType;
import com.sht.util.PageCalculator;

public class ShopServiceTest extends BaseTest{
	@Autowired
	ShopService shopservice;
	@Autowired
	ShopDao shopdao;
	@Test
	public void query() {
		Shop shop = new Shop();
		String shoptype = "生鲜水果";
		ShopType shopType = new ShopType();
		shopType.setType(shoptype);
		shop.setShoptype(shopType);
		List<Shop> shopList = shopdao.queryConditionShopByShop(shop, 0, 10,1);
		assertEquals(1, shopList.size());
	}
}
