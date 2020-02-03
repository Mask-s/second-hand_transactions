package com.sht.web.subController;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sht.entity.Orders;
import com.sht.entity.Person;
import com.sht.entity.Shop;
import com.sht.service.OrderService;
import com.sht.service.PersonService;
import com.sht.service.ShopService;
import com.sht.service.impl.PersonServiceImpl;
import com.sht.util.CodeUtil;
import com.sht.util.HttpServletRequestUtil;

@Controller
@RequestMapping(value = "/person")
public class PersonController {
	@Autowired
	private PersonService personService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private ShopService shopService;
	
	
	/**
	 * 初始化页面
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@GetMapping(value="/getallperson")
	@ResponseBody
	public Map<String,Object> getAllPerson(HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		List<Person> personlist = new ArrayList<Person>();	
		personlist = personService.getAllPerson(0, 10);
		try {
			modelMap.put("rows", personlist);
			modelMap.put("totals", personlist.size());
			modelMap.put("pgcounts", personlist.size()/10+1);
			modelMap.put("success", true);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
		}
		
		return modelMap;
	}
	
	
	
	@GetMapping(value="/getconditionperson")
	@ResponseBody
	public Map<String,Object> getConditionPerson(HttpServletRequest request) {
		Map<String,Object> modelMap = new HashMap<String,Object>();
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		String condition = HttpServletRequestUtil.getString(request, "condition");
		Person user = new Person();
		user.setUserId(1);
		user.setUserName("管理员");
		request.getSession().setAttribute("user", user);
		List<Person> personlist = new ArrayList<Person>();
		int pacounts=1;
		if(pageIndex==-1 && condition==null) {
			personlist = personService.getAllPerson(1,10);
		}else {
			if(pageIndex==-1) {
				pageIndex=1;
			}
			personlist = personService.getConditionPerson(condition, pageIndex, 10);
		}
		try {
			modelMap.put("session", request.getSession().getAttribute("user"));
			modelMap.put("rows", personlist);
			modelMap.put("totals", personlist.size());
			modelMap.put("pgcounts", pacounts);
			modelMap.put("success", true);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
		}
		return modelMap;
	}
	
	@PostMapping(value="/addperson")
	@ResponseBody
	public Map<String, Object> addPerson(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String,Object>();
		if(!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码不正确");
			return modelMap;
		}
		String personStr = HttpServletRequestUtil.getString(request, "personStr");
		ObjectMapper mapper = new ObjectMapper();
		Person person = null;
		try {
			person = mapper.readValue(personStr, Person.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		int count = personService.addPerson(person);
		if(count==-1) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "用户已存在！");
			return modelMap;
		}else if (count==0) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "数据不能为空！");
			return modelMap;
		}else if (count==-2) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "系统错误！");
			return modelMap;
		}else {
			modelMap.put("success", true);
			return modelMap;
		}
	}
	
	
	
	@PostMapping(value="/modifyperson")
	@ResponseBody
	public Map<String, Object> modifyPerson(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String,Object>();
		if(!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码不正确");
			return modelMap;
		}
		String personStr = HttpServletRequestUtil.getString(request, "personStr");
		ObjectMapper mapper = new ObjectMapper();
		Person person = null;
		try {
			person = mapper.readValue(personStr, Person.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		int count = personService.updatePerson(person);
		if (count==0) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "数据不能为空！");
			return modelMap;
		}else {
			modelMap.put("success", true);
			return modelMap;
		}
	}
	
	@GetMapping(value="/getpersonbyid")
	@ResponseBody
	public Map<String, Object> getPersonById(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String,Object>();
		int id = HttpServletRequestUtil.getInt(request, "personId");
		if(id == -1) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "id为空！");
			return modelMap;
		}else {
			Person person = personService.getPersonById(id);
			if(person == null) {
				modelMap.put("success", false);
				modelMap.put("errMsg", "该用户不存在！");
				return modelMap;
			}else {
				modelMap.put("success", true);
				modelMap.put("person", person);
			}
		}
		return modelMap;
		
	}
	
	@GetMapping("delpersonbyid")
	@ResponseBody
	public Map<String, Object> delPersonById(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String,Object>();
		int userId = HttpServletRequestUtil.getInt(request, "personId");
		if(userId == -1) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "id不能为空！");
			return modelMap;
		}else {
			Person person = new Person();
			person.setUserId(userId);
			Orders orders = new Orders();
			orders.setBuyer(person);
			List<Orders> ordersBuyerList = orderService.queryOrders(orders);
			Orders orders2 = new Orders();
			orders2.setOwner(person);
			List<Orders> ordersOwnerList = orderService.queryOrders(orders);
			Shop shop = new Shop();
			shop.setPerson(person);
			List<Shop> shopList = shopService.queryConditionShopByShop(shop, 1, 999);
			if((ordersBuyerList == null || ordersBuyerList.size() == 0) && (ordersOwnerList == null || ordersOwnerList.size() == 0) && (shopList == null || shopList.size() == 0)) {
				int count = personService.deletePersonById(userId);
				if(count<=0) {
					modelMap.put("success", false);
					modelMap.put("errMsg", "内部错误！");
					return modelMap;
				}
				modelMap.put("success", true);
				modelMap.put("count", count);
				return modelMap;
			}else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "该用户正在交易！");
				return modelMap;
			}
		}		
	}
	
	@GetMapping("/logincheck")
	@ResponseBody
	public Map<String, Object> loginCheck(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String,Object>();
		String userName = HttpServletRequestUtil.getString(request, "userName");
		String passWord = HttpServletRequestUtil.getString(request, "passWord");
		if(userName != null && passWord != null) {
			List<Person> personList = personService.loginCheck(userName, passWord);
			if(personList.size() == 0) {
				modelMap.put("success", false);
				modelMap.put("errMsg", "用户名或密码不正确！");
			}else {
				for(Person p:personList) {
					request.getSession().setAttribute("user", p);
					request.getSession().setMaxInactiveInterval(15*60);
				}
				modelMap.put("success", true);
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "用户名或密码不能为空！");
		}
		return modelMap;
	}
	
	
	
	
	
	
	
	
	@PostMapping(value="/modifyaccount")
	@ResponseBody
	public Map<String, Object> modifyAccount(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String,Object>();
		String personStr = HttpServletRequestUtil.getString(request, "personStr");
		ObjectMapper mapper = new ObjectMapper();
		Person person = null;
		try {
			person = mapper.readValue(personStr, Person.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		int count = personService.updatePerson(person);
		if (count==0) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "数据不能为空！");
			return modelMap;
		}else {
			Person person3 = (Person)request.getSession().getAttribute("user");
			Person person2 = personService.getPersonById(person3.getUserId());
			request.getSession().setAttribute("user", person2);		
			modelMap.put("success", true);
			return modelMap;
		}
	}
}
