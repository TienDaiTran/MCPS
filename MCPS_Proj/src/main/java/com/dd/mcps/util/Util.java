package com.dd.mcps.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.imageio.ImageIO;

import org.springframework.web.multipart.MultipartFile;

public class Util {
	
	public static boolean saveImage(MultipartFile imgFile, String bannerDesFolder, String bannerFileName) {
		BufferedImage src;
		File destination = new File(bannerDesFolder + "\\" + bannerFileName);
		try {
			src = ImageIO.read(new ByteArrayInputStream(imgFile.getBytes()));
			ImageIO.write(src, "jpg", destination);
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	public static String convertToUtf8(String text) throws UnsupportedEncodingException {
		byte ptext[] = text.getBytes("ISO-8859-1"); 
		String value = new String(ptext, "UTF-8"); 
		return value;
	}
	
}
