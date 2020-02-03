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

import com.sht.entity.Log;
import com.sht.service.LogService;

@Controller
@RequestMapping("/log")
public class LogController {
	
	@Autowired
	private LogService logService;
	
	@GetMapping("/querylog")
	@ResponseBody
	public Map<String, Object> queryLog(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Log log = new Log();
		List<Log> logList = logService.queryLog(log);
		modelMap.put("success", true);
		modelMap.put("rows", logList);
		return modelMap;
	}
}
