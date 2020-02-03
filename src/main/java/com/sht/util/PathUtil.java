package com.sht.util;



public class PathUtil {
	//获取文件分隔符
	private static String seperator = System.getProperty("file.separator");
	//提供项目图片的根路径
	public static String getImgBasePath() {
		//判断操作系统
		String os = System.getProperty("os.name");
		String basepath = "";
		//startsWith（str前缀，int开始查找位置）指定前缀以win开始，如果是返回true否则返回false
		if(os.toLowerCase().startsWith("win")) {
			basepath = "E:/javaimgs/";
		}else {
			basepath = "/home/javaimgs/";
		}
		basepath = basepath.replace("/", seperator);
		return basepath;
	}
	//根据业务需求返回shop的子路径
	public static String getShopImagePath(long shopId) {
		String imagePath = "upload/item/shop/"+shopId+"/";
		return imagePath.replace("/", seperator);
	}
}
