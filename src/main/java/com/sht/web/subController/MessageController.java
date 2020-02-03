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

import com.sht.entity.Message;
import com.sht.entity.Person;
import com.sht.entity.Shop;
import com.sht.service.MessageService;
import com.sht.util.DateUtil;
import com.sht.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/message")
public class MessageController {
	@Autowired
	private MessageService messageService;
	
	@GetMapping("/insertmessage")
	@ResponseBody
	public Map<String, Object> insertMessage(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		int shopId = HttpServletRequestUtil.getInt(request, "shopId");
		String details = HttpServletRequestUtil.getString(request, "details");
		Person person = (Person)request.getSession().getAttribute("user");
		if(shopId == -1) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "shopId为空！");
			return modelMap;
		}
		if(details == null) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "内容为空！");
			return modelMap;
		}
		if(person == null) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请先登入！");
			return modelMap;
		}
		Shop shop = new Shop();
		shop.setShopId(shopId);
		Message message = new Message();
		message.setDetails(details);
		message.setMessageCreateTime(DateUtil.dateToString());
		message.setPerson(person);
		message.setShop(shop);
		int count = messageService.insertMessage(message);
		if(count <= 0) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "提交失败！");
			return modelMap;
		}else {
			modelMap.put("success", true);
			return modelMap;
		}
	}
	
	
	@GetMapping("/querymessage")
	@ResponseBody
	public Map<String, Object> queryMessage(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		int shopId = HttpServletRequestUtil.getInt(request, "shopId");
		if(shopId == -1) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "shopId为空！");
			return modelMap;
		}
		Shop shop = new Shop();
		shop.setShopId(shopId);
		Message message = new Message();
		message.setShop(shop);
		List<Message> messageList = messageService.queryMessage(message);
		modelMap.put("rows", messageList);
		modelMap.put("totals", messageList.size());
		modelMap.put("success", true);
		return modelMap;
	}
}
