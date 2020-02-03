package com.sht.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long t = System.currentTimeMillis();
		System.out.println(format.format(new Date(t)));
		
		//System.out.println(new Date());
		
		/*int hashCodeV = UUID.randomUUID().toString().hashCode();
        if(hashCodeV < 0) {//有可能是负数
            hashCodeV = - hashCodeV;
        }
        System.out.println(String.format("%015d", hashCodeV));*/
		
/*		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-DD HH:mm:ss");
		Date date = null;
		try {
			date = format.parse("2020-01-15 14:58:52");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println(date);*/
	}

}
