package com.sht.web.subController;

import java.io.IOException;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sht.entity.Orders;
import com.sht.entity.Person;
import com.sht.entity.Shop;
import com.sht.service.OrderService;
import com.sht.util.HttpServletRequestUtil;

@Controller
@RequestMapping(value="/order")
public class OrderController {
	@Autowired
	private OrderService orderService;
	@ResponseBody
	@PostMapping("/createorder")
	public Map<String, Object> createOrder(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		String orderStr = HttpServletRequestUtil.getString(request,"orderStr");
		int ownerId = HttpServletRequestUtil.getInt(request, "ownerId");
		int buyerId = HttpServletRequestUtil.getInt(request, "buyerId");
		int shopId = HttpServletRequestUtil.getInt(request, "shopId");
		if(orderStr == null || ownerId == -1 || buyerId == -1 || shopId == -1) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "参数不能为空!");
		}else {
			int count = -3;
			Orders orders = null;
			ObjectMapper mapper = new ObjectMapper();
			try {
				orders = mapper.readValue(orderStr, Orders.class);
			} catch (IOException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
			Person buyer = new Person();
			Person owner = new Person();
			Shop shop = new Shop();
			buyer.setUserId(buyerId);
			orders.setBuyer(buyer);
			owner.setUserId(ownerId);
			orders.setOwner(owner);
			shop.setShopId(shopId);
			orders.setShop(shop);
			count = orderService.createOrder(orders);
			if(count == -2) {
				modelMap.put("success", false);
				modelMap.put("errMsg", "内部错误!");
			}else if(count == -1) {
				modelMap.put("success", false);
				modelMap.put("errMsg", "参数不能空!");
			}else if(count == 0) {
				modelMap.put("success", false);
				modelMap.put("errMsg", "创建失败!");
			}else {
				modelMap.put("success", true);
				modelMap.put("orderId", count);
			}
		}
		return modelMap;
	}

	
	@ResponseBody
	@PostMapping("/modifyorder")
	public Map<String, Object> midifyOrder(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		String address = HttpServletRequestUtil.getString(request,"address");
		int orderId = HttpServletRequestUtil.getInt(request, "orderId");
		int orderStatus = HttpServletRequestUtil.getInt(request, "orderStatus");
		int shopStatus = HttpServletRequestUtil.getInt(request, "shopStatus");
		if(orderId == -1) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "orderId不能为空!");
		}else {
			int count = -3;
			Orders orders = new Orders();
			orders.setOrderId(orderId);
			if(address != null) {
				orders.setAddress(address);
			}else if(orderStatus != -1) {
				orders.setOrderStatus(orderStatus);
			}else if(shopStatus != -1) {
				orders.setShopStatus(shopStatus);
			}
			count = orderService.modifyOrder(orders);
			if(count == -2) {
				modelMap.put("success", false);
				modelMap.put("errMsg", "内部错误!");
			}else if(count == -1) {
				modelMap.put("success", false);
				modelMap.put("errMsg", "参数不能空!");
			}else if(count == 0) {
				modelMap.put("success", false);
				modelMap.put("errMsg", "修改失败!");
			}else {
				modelMap.put("success", true);
			}		
		}
		return modelMap;
	}
	
	
	@GetMapping("/queryorders")
	@ResponseBody
	public Map<String, Object> queryOrders(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String,Object>();
		int orderId = HttpServletRequestUtil.getInt(request, "orderId");
		int orderStatus = HttpServletRequestUtil.getInt(request, "orderStatus");
		int shopStatus = HttpServletRequestUtil.getInt(request, "shopStatus");
		String orderNumber = HttpServletRequestUtil.getString(request, "orderNumber");
		int ownerId = HttpServletRequestUtil.getInt(request, "ownerId");
		int buyerId = HttpServletRequestUtil.getInt(request, "buyerId");
		Orders orders = new Orders();
		if(orderId != -1) {
			orders.setOrderId(orderId);
		}
		if(orderStatus != -1) {
			orders.setOrderStatus(orderStatus);
		}
		if(shopStatus != -1) {
			orders.setShopStatus(shopStatus);
		}
		if(orderNumber != null) {
			orders.setOrderNumber(orderNumber);
		}
		if(ownerId != -1) {
			Person owner = new Person();
			owner.setUserId(ownerId);
			orders.setOwner(owner);
		}
		if(buyerId != -1) {
			Person buyer = new Person();
			buyer.setUserId(buyerId);
			orders.setBuyer(buyer);
		}
		List<Orders> ordersList = orderService.queryOrders(orders);
		if(ordersList.size() == 0) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "查无结果！");
		}else {
			modelMap.put("success", true);
			modelMap.put("rows", ordersList);
			modelMap.put("totals", ordersList.size());
		}
		return modelMap;
	}
	
	@GetMapping("/delorderbyid")
	@ResponseBody
	public Map<String, Object> delOrderById(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String,Object>();
		int orderId = HttpServletRequestUtil.getInt(request, "orderId");
		if(orderId == -1) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "orderId不能为空！");
		}else {
			int count = orderService.deleteOrder(orderId);
			if(count == -2) {
				modelMap.put("success", false);
				modelMap.put("errMsg", "内部错误!");
			}else if(count == -1) {
				modelMap.put("success", false);
				modelMap.put("errMsg", "参数不能空!");
			}else if(count == 0) {
				modelMap.put("success", false);
				modelMap.put("errMsg", "删除失败!");
			}else {
				modelMap.put("success", true);
			}
		}
		return modelMap;
	}
}
