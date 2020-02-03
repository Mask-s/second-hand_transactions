package com.sht.service.impl;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.sht.dao.ShopDao;
import com.sht.dto.ShopExecution;
import com.sht.entity.Person;
import com.sht.entity.Shop;
import com.sht.enums.ShopStateEnum;
import com.sht.exceptions.MyRuntimeException;
import com.sht.service.ShopService;
import com.sht.util.ImageUtil;
import com.sht.util.PathUtil;

@Service
public class ShopServiceImpl implements ShopService{
	@Autowired
	private ShopDao shopDao;

	@Override
	@Transactional
	public ShopExecution insertShop(Shop shop,InputStream shopInputStream,String fileName) throws MyRuntimeException{
		try {
			if(shop == null) {
				return new ShopExecution(ShopStateEnum.NULL_SHOP);
			}
			shop.setShopStatus(ShopStateEnum.CHECK.getState());
			shop.setCounts(0);
			int count = shopDao.insertShop(shop);
			if(count <= 0) {
				throw new MyRuntimeException("创建商品信息失败");
			}else {
				if(shopInputStream != null) {
					try {
						addShopImg(shop, shopInputStream, fileName);
					} catch (Exception e) {
						throw new MyRuntimeException("addShopImg err"+e.getMessage());
					}
					count = shopDao.updateShop(shop);
					if(count <= 0) {
						throw new MyRuntimeException("图片更新失败");
					}
				}
			}
		} catch (Exception e) {
			throw new MyRuntimeException("addShop err"+e.getMessage());
		}
		return new ShopExecution(ShopStateEnum.CHECK,shop);
	}

	@Override
	public int deleteShop(int shopId) {
		// TODO Auto-generated method stub
		//查询订单或者消息是否被应用
		return shopDao.deleteShop(shopId);
	}

	@Override
	@Transactional
	public ShopExecution updateShop(Shop shop,InputStream shopInputStream,String fileName)throws MyRuntimeException {
		if(shop == null || shop.getShopId() == null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP);
		}else {
			try {
				if(shopInputStream != null && fileName != null && !"".equals(fileName)) {
					Shop shoptemp = shopDao.queryShopById(shop.getShopId());
					if(shoptemp.getShopImg() != null) {
						ImageUtil.deleteFileOrPath(shoptemp.getShopImg());
					}
					addShopImg(shop, shopInputStream, fileName);
				}
				shop.setShopStatus(0);
				int count = shopDao.updateShop(shop);
				if(count <= 0) {
					return new ShopExecution(ShopStateEnum.INNER_ERROR);
				}else {
					shop = shopDao.queryShopById(shop.getShopId());
					return new ShopExecution(ShopStateEnum.SUCCESS,shop);
				}
			} catch (Exception e) {
				throw new MyRuntimeException("updateShop err" + e.getMessage());
			}
		}
	}

	@Override
	public List<Shop> queryConditionShop(String condition, int rowIndex, int pageSize) {
		// TODO Auto-generated method stub
		return shopDao.queryConditionShop(condition, rowIndex, pageSize,returnUserId());
	}

	@Override
	public Shop queryShopById(int shopId) {
		return shopDao.queryShopById(shopId);
	}

	@Override
	public List<Shop> queryConditionShopByShop(Shop conditionShop, int rowIndex, int pageSize) {
		// TODO Auto-generated method stub
		return shopDao.queryConditionShopByShop(conditionShop, rowIndex, pageSize,returnUserId());
	}

	@Override
	public List<Shop> queryConditionShopByPrice(int lowPrice, int highPrice,String type, int rowIndex, int pageSize) {
		// TODO Auto-generated method stub
		return shopDao.queryConditionShopByPrice(lowPrice, highPrice,type, rowIndex, pageSize,returnUserId());
	}
	
	private void addShopImg(Shop shop,InputStream shopInputStream,String fileName) {
		String dest = PathUtil.getShopImagePath(shop.getShopId());
		String shopImgAddr = ImageUtil.generateThumbnail(shopInputStream, fileName, dest);
		shop.setShopImg(shopImgAddr);
	}
	
	
	@Override
	@Transactional
	public int modifyShop(Shop shop)throws MyRuntimeException {
		//1:修改成功 -1:数据为空 0：修改失败
		if(shop == null || shop.getShopId() == null) {
			return -1;
		}else {
			int count = shopDao.updateShop(shop);
			if(count <= 0) {
				return 0;
			}else {
				return 1;
			}
		}
	}
	
	private int returnUserId() {
		ServletRequestAttributes attr = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
		Person person = (Person)attr.getRequest().getSession(true).getAttribute("user");
		return person.getUserId();
	}
}
