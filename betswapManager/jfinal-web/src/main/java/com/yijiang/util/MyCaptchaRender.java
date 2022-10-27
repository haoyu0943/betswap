package com.yijiang.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;

import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.render.Render;

public class MyCaptchaRender extends Render {

	private static final String[] strArr = { "0", "1", "2", "3", "4", "5", "6",
			"7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
			"K", "L", "M", "N", "0", "P", "Q", "R", "S", "T", "U", "V", "W",
			"X", "Y", "Z" };

	private static String randomCodeKey = "IMG_CODE";
	private static boolean caseInsensitive = true;

	private int img_width = 85;
	private int img_height = 20;
	private int img_randNumber = 6;
	public String curimgcode="";

	public MyCaptchaRender() {
	}

	public MyCaptchaRender(String randomKey) {
		if (StrKit.isBlank(randomKey))
			throw new IllegalArgumentException("randomKey can not be blank");
		randomCodeKey = randomKey;
	}

	public MyCaptchaRender(int width, int height, int count,boolean isCaseInsensitive) {
		if (width <= 0 || height <= 0 || count <= 0) {
			throw new IllegalArgumentException(
					"Image width or height or count must be > 0");
		}
		this.img_width = width;
		this.img_height = height;
		this.img_randNumber = count;
		caseInsensitive = isCaseInsensitive;
	}

	public MyCaptchaRender(String randomKey, int width, int height, int count,
			boolean isCaseInsensitive) {
		if (StrKit.isBlank(randomKey))
			throw new IllegalArgumentException("randomKey can not be blank");
		randomCodeKey = randomKey;

		if (width <= 0 || height <= 0 || count <= 0) {
			throw new IllegalArgumentException(
					"Image width or height or count must be > 0");
		}
		this.img_width = width;
		this.img_height = height;
		this.img_randNumber = count;
		caseInsensitive = isCaseInsensitive;
	}
	
	public void CrtImgcode(){
		curimgcode="";
		Random random = new Random();
		for (int i = 0; i < img_randNumber; i++) {
			String rand = String.valueOf(strArr[random.nextInt(strArr.length)]);
			curimgcode += rand;
		}
	}
	   
		

	@Override
	public void render() {
		BufferedImage image = new BufferedImage(img_width, img_height,
				BufferedImage.TYPE_INT_RGB);
		String vCode = drawGraphic(image);
	    //vCode = encrypt(vCode);
		//Cookie cookie = new Cookie(randomCodeKey, vCode);
		//cookie.setMaxAge(-1);
		//cookie.setPath("/");
		//response.addCookie(cookie);
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");

		ServletOutputStream sos = null;
		try {
			sos = response.getOutputStream();
			ImageIO.write(image, "jpeg", sos);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (sos != null)
				try {
					sos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	private String drawGraphic(BufferedImage image) {
		// 获取图形上下文
		Graphics g = image.createGraphics();
		// 生成随机类
		Random random = new Random();
		// 设定背景色
		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, img_width, img_height);
		// 设定字体
		g.setFont(new Font("Times New Roman", Font.PLAIN, 26));
		// 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
		g.setColor(getRandColor(160, 200));
		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(img_width);
			int y = random.nextInt(img_height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}

		// 取随机产生的认证码(img_randNumber位数字)
		//String sRand = "";
		/*
		for (int i = 0; i < img_randNumber; i++) {
			String rand = String.valueOf(strArr[random.nextInt(strArr.length)]);
			sRand += rand;
			g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
			g.drawString(rand,(i+1)*((img_width-20)/img_randNumber), 25);
		}
		*/
		for (int i = 0; i <curimgcode.length(); i++) {
			String  rand = curimgcode.substring(i, i+1);
			g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
			g.drawString(rand,(i+1)*((img_width-20)/img_randNumber), 25);
		}

		// 图象生效
		g.dispose();

		//return sRand;
		return curimgcode;
	}

	/*
	 * 给定范围获得随机颜色
	 */
	private Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	private static final String encrypt(String srcStr) {
		try {
			String result = "";
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] bytes = md.digest(srcStr.getBytes("utf-8"));
			for (byte b : bytes) {
				String hex = Integer.toHexString(b & 0xFF).toUpperCase();
				result += ((hex.length() == 1) ? "0" : "") + hex;
			}
			return result;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static boolean validate(Controller controller, String inputRandomCode) {
		if (StrKit.isBlank(inputRandomCode))
			return false;
		try {
			if (caseInsensitive){
				inputRandomCode = inputRandomCode.toUpperCase();
			}
			String vinputRandomCode = encrypt(inputRandomCode);
			//System.out.println(inputRandomCode);
			//System.out.println(vinputRandomCode);
			//System.out.println(controller.getCookie(randomCodeKey));
			return vinputRandomCode.equals(controller.getCookie(randomCodeKey));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean validate(String sessioncode, String inputRandomCode) {
		if (StrKit.isBlank(inputRandomCode))
			return false;
		try {
			if (caseInsensitive){
				inputRandomCode = inputRandomCode.toUpperCase();
				sessioncode=sessioncode.toUpperCase();
			}
			return inputRandomCode.equals(sessioncode);
		} 
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
