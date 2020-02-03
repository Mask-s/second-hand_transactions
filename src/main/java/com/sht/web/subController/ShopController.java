package com.sht.web.subController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sht.dto.ShopExecution;
import com.sht.entity.Person;
import com.sht.entity.Shop;
import com.sht.entity.ShopType;
import com.sht.enums.ShopStateEnum;
import com.sht.exceptions.MyRuntimeException;
import com.sht.service.ShopService;
import com.sht.service.ShopTypeService;
import com.sht.util.CodeUtil;
import com.sht.util.DateUtil;
import com.sht.util.HttpServletRequestUtil;
import com.sht.util.PageCalculator;

@Controller
@RequestMapping("/shop")
public class ShopController {
	@Autowired
	private ShopService shopService;
	@Autowired
	private ShopTypeService shopTypeService;
	
	
	@GetMapping("/deleteshopbyid")
	@ResponseBody
	public Map<String, Object> deleteShopById(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		int shopId = HttpServletRequestUtil.getInt(request, "shopId");
		int count = shopService.deleteShop(shopId);
		if(count <= 0) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "商品删除失败");
			return modelMap;
		}else {
			modelMap.put("success", true);
			modelMap.put("count", count);
			return modelMap;
		}
	}
	
	
	
	//通过页面修改
	@PostMapping("/updateshop")
	@ResponseBody
	public Map<String, Object> updateShop(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if(!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码错误！");
			return modelMap;
		}
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		ObjectMapper objectMapper = new ObjectMapper();
		Shop shop = null;
		try {
			shop = objectMapper.readValue(shopStr, Shop.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		CommonsMultipartFile shopImg = null;
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		if(commonsMultipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
			shopImg = (CommonsMultipartFile)multipartHttpServletRequest.getFile("shopImg");
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "上传图片不能为空");
			return modelMap;
		}
		if(shop != null && shop.getShopId() != null) {
			try {
				ShopExecution count = shopService.updateShop(shop, shopImg.getInputStream(), shopImg.getOriginalFilename());
				if(count.getState() == ShopStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				}
			} catch (IOException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", "商品修改失败");
				return modelMap;
			}catch(MyRuntimeException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}
			return modelMap;
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg","请输入商品信息");
			return modelMap;
		}
	}
	
	
	
	
	
	@PostMapping("/insertshop")
	@ResponseBody
	public Map<String, Object> insertShop(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if(!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码错误！");
			return modelMap;
		}
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		ObjectMapper oMapper = new ObjectMapper();
		Shop shop = null;
		try {
			shop = oMapper.readValue(shopStr, Shop.class);		
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		CommonsMultipartFile shopImg = null;
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		if(commonsMultipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
			shopImg = (CommonsMultipartFile)multipartHttpServletRequest.getFile("shopImg");
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "上传图片不能为空");
			return modelMap;
		}
		if(shop != null && shopImg != null) {
			Person person = (Person)request.getSession().getAttribute("user"); //= new Person();//
/*			if(person.getUserId() == null) {
				person.setUserId(1);
				person.setUserName("test");
			}*/
			shop.setPerson(person);
			try {
				ShopExecution se = shopService.insertShop(shop, shopImg.getInputStream(), shopImg.getOriginalFilename());
				if(se.getState() == ShopStateEnum.CHECK.getState()) {
					modelMap.put("success", true);
				}
			} catch (IOException e) {
 				modelMap.put("success", false);
 				modelMap.put("errMsg", "添加商品失败");
 				return modelMap;
			}catch (MyRuntimeException e) {
				modelMap.put("success", false);
 				modelMap.put("errMsg", e.getMessage());
 				return modelMap;
			}
			
			return modelMap;
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg","请输入商品信息");
			return modelMap;
		}
	}
	
	@GetMapping("/getshopandtypelist")
	@ResponseBody
	public Map<String, Object> getShopAndTypeList(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		int shopId = HttpServletRequestUtil.getInt(request, "shopId");
		if(shopId == -1) {
			modelMap.put("success", false);
			modelMap.put("errMsg", ShopStateEnum.NULL_SHOPID);
			return modelMap;
		}
		Shop shop = shopService.queryShopById(shopId);
		List<ShopType> typeList = new ArrayList<ShopType>();
		typeList = shopTypeService.queryallType();
		modelMap.put("success", true);
		modelMap.put("shop", shop);
		modelMap.put("typeList", typeList);
		return modelMap;
	}
	
	
	@GetMapping("/query")
	@ResponseBody
	public Map<String, Object> query(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		int userId = HttpServletRequestUtil.getInt(request, "userId");
		String shoptype = HttpServletRequestUtil.getString(request, "shoptype");
		String type = HttpServletRequestUtil.getString(request, "type");
		String condition = HttpServletRequestUtil.getString(request, "condition");
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		int shopStatus = HttpServletRequestUtil.getInt(request, "shopStatus");
		if(pageIndex == -1) {
			pageIndex = 1;
		}
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		if(pageSize == -1) {
			pageSize = 10;
		}
		int shopId = HttpServletRequestUtil.getInt(request, "shopId");
		int lowPrice = HttpServletRequestUtil.getInt(request, "lowPrice");
		int highPrice = HttpServletRequestUtil.getInt(request, "highPrice");
		if(shopId != -1) {
			Shop shop = shopService.queryShopById(shopId);
			modelMap.put("success", true);
			modelMap.put("shop", shop);
		}else if(userId != -1 || shopStatus != -1 || shoptype != null) {
			Shop shop = new Shop();
			if(shopStatus != -1) {
				shop.setShopStatus(shopStatus);
			}
			if(userId != -1) {
				Person person = new Person();
				person.setUserId(userId);
				shop.setPerson(person);
			}
			if(shoptype != null) {
				ShopType shopType = new ShopType();
				shopType.setType(shoptype);
				shop.setShoptype(shopType);
			}
			List<Shop> shopList = shopService.queryConditionShopByShop(shop, PageCalculator.calculatorRowIndex(pageIndex, pageSize), pageSize);
			modelMap.put("success", true);
			modelMap.put("rows", shopList);
		}else if(lowPrice != -1 || highPrice != -1) {
			if(lowPrice == -1) {
				lowPrice = 0;
			}
			if(highPrice == -1) {
				highPrice = 99999;
			}
			if(lowPrice > highPrice) {
				modelMap.put("success", false);
				modelMap.put("errMsg", "请输入正确的区间");
			}else{
				List<Shop> shopList = shopService.queryConditionShopByPrice(lowPrice, highPrice,type, PageCalculator.calculatorRowIndex(pageIndex, pageSize), pageSize);
				modelMap.put("success", true);
				modelMap.put("rows", shopList);
			}
		}else{
			List<Shop> shopList = shopService.queryConditionShop(condition, PageCalculator.calculatorRowIndex(pageIndex, pageSize), pageSize);
			modelMap.put("success", true);
			modelMap.put("rows", shopList);
		}
		return modelMap;
	}
	
	
	
	
	
	//不通过页面修改
	@GetMapping("/modifyshop")
	@ResponseBody
	public Map<String, Object> modifyShop(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		int shopId = HttpServletRequestUtil.getInt(request, "shopId");
		int shopStatus = HttpServletRequestUtil.getInt(request, "shopStatus");
		int counts = HttpServletRequestUtil.getInt(request, "counts");
		int shopNumber = HttpServletRequestUtil.getInt(request, "shopNumber");
		Shop shop = new Shop();
		if(shopId != -1) {
			shop.setShopId(shopId);
		}
		if(shopStatus != -1) {
			if(shopStatus == 1) {
				shop.setSoldOutTime(DateUtil.dateToString());
				shop.setShopStatus(shopStatus);
			}else if(shopStatus == 2) {
				shop.setPutAwayTime(DateUtil.dateToString());
				shop.setShopStatus(shopStatus);
			}else{
				shop.setShopStatus(shopStatus);
			}
		}
		if(counts != -1) {
			shop.setCounts(counts);
		}
		if(shopNumber != -1) {
			shop.setShopNumber(shopNumber);
		}
		int count = shopService.modifyShop(shop);
		if(count == -1) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "商品参数为空！");
		}else if(count == 0) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "商品修改失败！");
		}else if(count == 1) {
			modelMap.put("success", true);
		}
		return modelMap;
	}
}
