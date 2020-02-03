package com.sht.web.htmlController;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sht.entity.Person;

@Controller
@RequestMapping(value = "/main" , method= {RequestMethod.GET})
public class MainController {
	@GetMapping("/test")
	public String getMain() {
		return "test";
	}
	@GetMapping("/getperson")
	public String getPerson() {
		return "superAdminOperation/personManage";
	}
	@GetMapping("/operationperson")
	public String operationPerson() {
		return "superAdminOperation/personoperation";
	}
	@GetMapping("/operationshop")
	public String operationShop() {
		return "shop/shopoperation";
	}
	@GetMapping("/shopmainlist")
	public String shopMainList() {
		return "shop/shopmainlist";
	}
	@GetMapping("/shoplist")
	public String shopList() {
		return "shop/shoplist";
	}
	@GetMapping("/detail")
	public String detail() {
		return "shop/detail";
	}
	@GetMapping("/login")
	public String login(HttpServletRequest request) {
		Person person = (Person)request.getSession().getAttribute("user");
		if(person != null) {
			request.getSession().removeAttribute("user");
		}
		return "login";
	}
	@GetMapping("/user")
	public String user() {
		return "user/user";
	}
	@GetMapping("/buyerorder")
	public String buyerOrder() {
		return "user/buyerOrder";
	}
	@GetMapping("/ownerorder")
	public String ownerOrder() {
		return "user/ownerOrder";
	}
	@GetMapping("/shopmanage")
	public String shopManage() {
		return "user/shopManage";
	}
	@GetMapping("/shopstatusmanage")
	public String shopStatusManage() {
		return "superAdminOperation/shopStatusManage";
	}
	@GetMapping("/logmanage")
	public String logManage() {
		return "superAdminOperation/logManage";
	}
	@GetMapping("/wastebookmanage")
	public String wasteBookManage() {
		return "user/wasteBookManage";
	}
	@GetMapping("/pay")
	public String pay() {
		return "pay";
	}
}
