package com.sht.web.subController;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sht.entity.Person;

@Controller
@RequestMapping("/session")
public class GetSessionController {
	@GetMapping("/getsession")
	@ResponseBody
	public Map<String, Object> getSession(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Person user = (Person)request.getSession().getAttribute("user");
		if(user == null) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请先登入");
		}else {
			modelMap.put("success", true);
			modelMap.put("session", user);
		}
		return modelMap;
	}
}