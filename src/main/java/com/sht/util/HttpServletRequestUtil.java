package com.sht.util;

import javax.servlet.http.HttpServletRequest;

public class HttpServletRequestUtil {
	//根据key获取request的参数并转换成Integer,如果转换失败返回-1
	public static int getInt(HttpServletRequest request,String key) {
		try {
			return Integer.decode(request.getParameter(key));
		}catch(Exception e) {
			return -1;
		}
	}
	
	public static long getLong(HttpServletRequest request,String key) {
		try {
			return Long.valueOf(request.getParameter(key));
		}catch(Exception e) {
			return -1;
		}
	}
	
	public static Double getDouble(HttpServletRequest request,String key) {
		try {
			return Double.valueOf(request.getParameter(key));
		}catch(Exception e) {
			return -1d;
		}
	}
	
	public static Boolean getBoolean(HttpServletRequest request,String key) {
		try {
			return Boolean.valueOf(request.getParameter(key));
		}catch(Exception e) {
			return false;
		}
	}
	
	public static String getString(HttpServletRequest request,String key) {
		try {
			String result = request.getParameter(key);
			if(result != null) {
				result = result.trim();
			}
			if("".equals(result)) {
				result = null;
			}
			return result;
		}catch(Exception e) {
			return null;
		}
	}
}
