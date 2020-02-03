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

import com.sht.entity.Person;
import com.sht.entity.WasteBook;
import com.sht.service.WasteBookService;
import com.sht.util.DateUtil;
import com.sht.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/wastebook")
public class WasteBookController {
	@Autowired
	private WasteBookService service;
	
	@GetMapping("/insertwastebook")
	@ResponseBody
	public Map<String, Object> insertWasteBook(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		WasteBook wasteBook = new WasteBook();
		int wasteStatus = HttpServletRequestUtil.getInt(request, "wasteStatus");
		int wastePrice = HttpServletRequestUtil.getInt(request, "wastePrice");
		String wasteDes = HttpServletRequestUtil.getString(request, "wasteDes");
		int userId = HttpServletRequestUtil.getInt(request, "userId");
		if(wasteStatus == -1 || wastePrice == -1 || wasteDes == null || userId == -1) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "缺少必要的参数！");
		}else {
			wasteBook.setExchangeHour(DateUtil.dateToString());
			wasteBook.setWasteDes(wasteDes);
			wasteBook.setWastePrice(wastePrice);
			wasteBook.setWasteStatus(wasteStatus);
			Person person = new Person();
			person.setUserId(userId);
			wasteBook.setPerson(person);
			int count = service.insertWasteBook(wasteBook);
			if(count <= 0) {
				modelMap.put("success", false);
				modelMap.put("errMsg", "添加流水记录失败！");
			}else {
				modelMap.put("success", true);
			}
		}
		return modelMap;
	}
	
	
	@GetMapping("/querywastebook")
	@ResponseBody
	public Map<String, Object> queryWasteBook(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		WasteBook wasteBook = new WasteBook();
		int userId = HttpServletRequestUtil.getInt(request, "userId");
		if(userId == -1) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "缺少必要userId！");
		}else {
			Person person = new Person();
			person.setUserId(userId);
			wasteBook.setPerson(person);
			List<WasteBook> wasteBookList = service.queryWasteBook(wasteBook);
			modelMap.put("success", true);
			modelMap.put("rows", wasteBookList);
		}
		return modelMap;
	}
}
