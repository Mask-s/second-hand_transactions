package com.sht.web.subController;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sht.config.AlipayConfig;
import com.sht.entity.Log;
import com.sht.entity.Person;
import com.sht.entity.WasteBook;
import com.sht.service.LogService;
import com.sht.service.PersonService;
import com.sht.service.WasteBookService;
import com.sht.util.DateUtil;
import com.sht.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/pay")
public class PayController {
	@Autowired
	private PersonService personService;
	@Autowired
	private LogService logService;
	@Autowired
	private WasteBookService wasteBookService;
	
	@RequestMapping(value="/alipay",produces="text/html; charset=utf-8",method=RequestMethod.POST)
	@ResponseBody
	private String Goalipay(HttpServletRequest request,HttpServletResponse response) throws Exception{
	  request.setCharacterEncoding("UTF-8");
	  //获得初始化的AlipayClient
	  AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
	  //设置请求参数
	  AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
	  alipayRequest.setReturnUrl(AlipayConfig.return_url);
	  alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
	  
	  //商户订单号，商户网站订单系统中唯一订单号，必填
	  String out_trade_no = HttpServletRequestUtil.getString(request, "WIDout_trade_no");
	  //付款金额，必填
	  String total_amount =String.valueOf(HttpServletRequestUtil.getInt(request, "WIDtotal_amount"));
	  //订单名称，必填
	  String subject = HttpServletRequestUtil.getString(request, "WIDsubject");
	  //商品描述，可空
	  String body = HttpServletRequestUtil.getString(request, "WIDbody");
	  System.out.println(out_trade_no+":"+total_amount+":"+subject+":"+body);
	  /*该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，
	  无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m*/
	  alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\"," 
	    + "\"total_amount\":\""+ total_amount +"\"," 
	    + "\"subject\":\""+ subject +"\"," 
	    + "\"body\":\""+ body +"\"}");
		/*该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，
		  无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m*/
		  alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\"," 
		    + "\"total_amount\":\""+ total_amount +"\"," 
		    + "\"subject\":\""+ subject +"\"," 
		    + "\"body\":\""+ body +"\"," 
		    + "\"timeout_express\":\"5m\"," 
		    + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
		  
		  
		  //请求
		  
		  String result = alipayClient.pageExecute(alipayRequest).getBody();
		  
		  int account = HttpServletRequestUtil.getInt(request, "WIDtotal_amount");
		  if(account != -1) {
			  Person person = (Person)request.getSession().getAttribute("user");
			  int userId = person.getUserId();
			  int nowAccount = person.getMoney();
			  int totalMoney = account+nowAccount;
			  Person person2 = new Person();
			  person2.setMoney(totalMoney);
			  person2.setUserId(userId);
			  personService.updatePerson(person2);
			  Log log = new Log();
			  log.setLogDes(person.getUserName()+"充值了"+account+"元");
			  log.setLogTime(DateUtil.dateToString());
			  log.setPerson(person2);
			  logService.insertLog(log);
			  WasteBook wasteBook = new WasteBook();
			  wasteBook.setExchangeHour(DateUtil.dateToString());
			  wasteBook.setPerson(person2);
			  wasteBook.setWasteDes("充值了"+account+"元");
			  wasteBook.setWastePrice(account);
			  wasteBook.setWasteStatus(1);
			  wasteBookService.insertWasteBook(wasteBook);
			  person.setMoney(totalMoney);
			  request.getSession().setAttribute("user", person);
		  }

		  return result;
		  
		  //输出
	}
}
