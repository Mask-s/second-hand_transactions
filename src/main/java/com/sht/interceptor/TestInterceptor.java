package com.sht.interceptor;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sht.entity.Person;

@Component
public class TestInterceptor implements HandlerInterceptor{

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			String user_cookie = "";
			for (int i = 0; i < cookies.length; i++) {
				if ((cookies[i].getName()).equals("user")) {
					user_cookie = cookies[i].getValue();
				}
			}
			try {
				user_cookie = URLDecoder.decode(user_cookie, "utf-8");  //将cookie中获取的字符串进行解码，防止有中文
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			if (user_cookie != null && !user_cookie.equals("")) {
				Person user = null;
				ObjectMapper objectMapper = new ObjectMapper();
				user = objectMapper.readValue(user_cookie, Person.class);
				System.out.println(user.getUserName());
				return true;
			}else {
				response.sendRedirect("/sht/main/login");
			}
 
		}
		return false;
	}

}
