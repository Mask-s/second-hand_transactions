package com.sht.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class ImageUtil {
	//从进程-》当前进程-》类处理器获得resources下的水印图片的路径
	private static	String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
	//时间格式
	private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	//产生随机数
	private static final Random r = new Random();
	
	private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);
	
	
	
	/**
	 * 将CommonsMultipartFile转换成File
	 * @param cFile
	 * @return
	 */
	public static File transferCommonsMultipartFileToFile(CommonsMultipartFile cFile) {
		File newFile = new File(cFile.getOriginalFilename());
		try {
			cFile.transferTo(newFile);
		} catch (IllegalStateException e) {
			logger.error(e.toString());
			e.printStackTrace();
		} catch (IOException e) {
			logger.error(e.toString());
			e.printStackTrace();
		}
		return newFile;
	}
	
	
	/**
	 * 处理缩略图返回文件相对路径
	 * @param thumbnail
	 * @param targetAddr
	 * @return
	 */
	public static String generateThumbnail(InputStream thumbnailInputStream,String fileName,String targetAddr) {
		String realFileName = getRandomFileName();
		String extension = getFileExtension(fileName);
		makeDirPath(targetAddr);
		String relativeAddr = targetAddr + realFileName + extension;
		logger.debug("current relativeAddr is :" + relativeAddr);
		File dest = new File(PathUtil.getImgBasePath()+relativeAddr);
		logger.debug("current complete addr is :" + PathUtil.getImgBasePath()+relativeAddr);
		try {
			//文件图片流
			Thumbnails.of(thumbnailInputStream).forceSize(200,200)
			.watermark(Positions.BOTTOM_CENTER,ImageIO.read(new File(basePath+"/watermark.jpg")),0.5f)
			.outputQuality(0.2f).toFile(dest);
		}catch(Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
		}
		return relativeAddr;
	}
	
	/**
	 * 创建目标路径所涉及到的目录，即自动创建路径上没有的文件夹
	 * @param targetAddr
	 */
	private static void makeDirPath(String targetAddr) {
		String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
		File dirPath = new File(realFileParentPath);
		if(!dirPath.exists()) {
			//创建路径上所有不存在的文件夹
			dirPath.mkdirs();
		}
		
	}


	/**
	 * 获取输入文件流的扩展名
	 * @param thumbnail
	 * @return
	 */
	private static String getFileExtension(String fileName) {
/*		//获取文件名
		String originalFileName = cFile.getName();*/
		//获取最后一个点号后面的字符
		return fileName.substring(fileName.lastIndexOf("."));//originalFileName.substring(originalFileName.lastIndexOf("."));
	}

	/**
	 * 随机生成文件名，年月日时分秒+五位随机数
	 * @return
	 */
	public static String getRandomFileName() {
		// 获取随机的五位数:r.nextInt(89999):范围0-89999
		int rannum = r.nextInt(89999)+10000;
		String nowTimeStr = sDateFormat.format(new Date());
		return nowTimeStr + rannum;
	}
	
	
	public static void main(String[] args) throws IOException {
		//Thumbnails.of(打开文件).size(x,y).watermark(水印位置，水印路径，透明度).outputQuality(压缩比例).toFile(输出位置)
		Thumbnails.of(new File("E:\\ok.jpeg")).size(200, 200)
		.watermark(Positions.BOTTOM_RIGHT,ImageIO.read(new File(basePath+"/watermark.jpg")),0.5f)
		.outputQuality(0.2f)
		.toFile("E:\\ok.jpeg");
	}
	
	/**
	 * storePath是文件路径还是目录路径
	 * 如果是文件路径则删除文件
	 * 如果是目录路径则删除该路径下的所有文件
	 */
	public static void deleteFileOrPath(String storePath) {
		File fileOrPath = new File(PathUtil.getImgBasePath()+storePath);
		if(fileOrPath.exists()) {
			if(fileOrPath.isDirectory()) {
				File files[] = fileOrPath.listFiles();
				for(int i = 0; i < files.length; i++) {
					files[i].delete();
				}
			}
			fileOrPath.delete();
		}
	}
}
