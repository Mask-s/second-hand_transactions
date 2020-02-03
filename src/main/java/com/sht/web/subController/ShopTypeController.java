package com.sht.web.subController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sht.entity.ShopType;
import com.sht.service.ShopTypeService;
import com.sht.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/shoptype")
public class ShopTypeController {
	@Autowired
	private ShopTypeService shopTypeService;
	
	@GetMapping("/queryallshoptype")
	@ResponseBody
	public Map<String, Object> queryAllShopType(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<ShopType> rows = shopTypeService.queryallType();
		if(rows.size()==0 || rows == null) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "没有内容！");
		}else {
			modelMap.put("success", true);
			modelMap.put("rows", rows);
		}
		return modelMap;
	}
	
	@GetMapping("/queryshoptypebyid")
	@ResponseBody
	public Map<String, Object> queryShopTypeById(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		int typeId = HttpServletRequestUtil.getInt(request, "typeId");
		ShopType shopType = shopTypeService.queryTypeById(typeId);
		if(shopType == null) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "根据Id查无结果！");
		}else {
			modelMap.put("success", true);
			modelMap.put("shopType", shopType);
		}
		return modelMap;
	}
}
