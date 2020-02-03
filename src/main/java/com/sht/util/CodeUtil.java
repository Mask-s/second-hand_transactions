package com.sht.util;

import javax.servlet.http.HttpServletRequest;

public class CodeUtil {
	public static boolean checkVerifyCode(HttpServletRequest request) {
		//实际的验证码
		String verifyCodeExpected = (String) request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		String verifyCodeActal = HttpServletRequestUtil.getString(request, "verifyCodeActual");
		if(verifyCodeActal == null || !verifyCodeActal.equals(verifyCodeExpected)) {
			return false;
		}
		return true;
	}
}
