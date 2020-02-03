package com.sht.aop;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.sht.entity.Log;
import com.sht.entity.Person;
import com.sht.service.LogService;
import com.sht.util.DateUtil;

@Component
@Aspect
public class MyAspects {
	@Autowired
	private LogService logService;
	
	@Pointcut(value = "execution(* com.sht.web.subController.PersonController.loginCheck(..))")
	public void loginCheckpointcut() {}
	
	@Pointcut(value="execution(* com.sht.web.subController.PersonController.modifyPerson(..))")
	public void modifyPersonpointcut() {}
	
	@Pointcut(value = "execution(* com.sht.web.subController.ShopController.updateShop(..))")
	public void modifyshoppointcut() {}
	
	@Pointcut(value = "execution(* com.sht.web.subController.ShopController.insertShop(..))")
	public void addshoppointcut() {}
	
	//添加用户登入的日志
	@After("loginCheckpointcut()")
	public void userLogin(JoinPoint point) {
		ServletRequestAttributes attr = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
    	HttpSession session=attr.getRequest().getSession(true);
    	Person person = (Person)session.getAttribute("user");
		if(person != null) {
			Log log = new Log();
			log.setLogDes(person.getUserName()+"登入了系统");
			log.setLogTime(DateUtil.dateToString());
			log.setPerson(person);
			logService.insertLog(log);
		}
	}
	
	//添加用户修改信息的日志
	@After("modifyPersonpointcut()")
	public void modifyUser(JoinPoint point) {
		ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
		HttpSession session = attributes.getRequest().getSession(true);
		Person person = (Person)session.getAttribute("user");
		if(person != null) {
			Log log = new Log();
			log.setLogDes(person.getUserName()+"修改了用户信息");
			log.setLogTime(DateUtil.dateToString());
			log.setPerson(person);
			logService.insertLog(log);
		}
	}
	
	@After("modifyshoppointcut()")
	public void modifyshop(JoinPoint point) {
		ServletRequestAttributes attr = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true);
		Person person = (Person)session.getAttribute("user");
		if(person != null) {
			Log log = new Log();
			log.setLogDes(person.getUserName()+"修改了商品信息");
			log.setLogTime(DateUtil.dateToString());
			log.setPerson(person);
			logService.insertLog(log);
		}
	}
	
	@After("addshoppointcut()")
	public void addshop(JoinPoint point) {
		ServletRequestAttributes attr = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true);
		Person person = (Person)session.getAttribute("user");
		if(person != null) {
			Log log = new Log();
			log.setLogDes(person.getUserName()+"上传了商品,请及时审批");
			log.setLogTime(DateUtil.dateToString());
			log.setPerson(person);
			logService.insertLog(log);
		}
	}
}
