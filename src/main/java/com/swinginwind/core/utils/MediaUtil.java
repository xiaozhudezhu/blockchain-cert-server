package com.swinginwind.core.utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MediaUtil {

	private static final Logger log = LoggerFactory.getLogger(MediaUtil.class);

	private static Boolean DEFAULT_THUMBNAIL_FORCE = false;// 建议该值为false
	private static int DEFAULT_THUMBNAIL_WIDTH = 100;
	private static int DEFAULT_THUMBNAIL_HEIGHT = 100;


	/**
	 * 获取指定视频的帧并保存为图片至指定目录
	 * 
	 * @param videofile
	 *            源视频文件路径
	 * @param framefile
	 *            截取帧的图片存放路径
	 * @throws Exception
	 */
	public static void fetchFrame(String videofile, String framefile) throws Exception {
		long start = System.currentTimeMillis();
		File targetFile = new File(framefile);
		FFmpegFrameGrabber ff = new FFmpegFrameGrabber(videofile);
		ff.start();
		int lenght = ff.getLengthInFrames();
		int i = 0;
		Frame f = null;
		while (i < lenght) {
			// 过滤前5帧，避免出现全黑的图片，依自己情况而定
			f = ff.grabFrame();
			if ((i > 10) && (f.image != null)) {
				break;
			}
			i++;
		}
		IplImage img = f.image;
		int owidth = img.width();
		int oheight = img.height();
		// 对截取的帧进行等比例缩放
		int width = 600;
		int height = (int) (((double) width / owidth) * oheight);
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		bi.getGraphics().drawImage(f.image.getBufferedImage().getScaledInstance(width, height, Image.SCALE_SMOOTH), 0,
				0, null);
		ImageIO.write(bi, "jpg", targetFile);
		// ff.flush();
		ff.stop();
		System.out.println(System.currentTimeMillis() - start);
	}

	/**
	 * <p>
	 * Title: thumbnailImage
	 * </p>
	 * <p>
	 * Description: 依据图片路径生成缩略图
	 * </p>
	 * 
	 * @param imagePath
	 *            原图片路径
	 * @param w
	 *            缩略图宽
	 * @param h
	 *            缩略图高
	 * @param prevfix
	 *            生成缩略图的前缀
	 * @param force
	 *            是否强制依照宽高生成缩略图(假设为false，则生成最佳比例缩略图)
	 */
	public static void thumbnailImage(String imagePath, int w, int h, String dest, boolean force) {
		File imgFile = new File(imagePath);
		if (imgFile.exists()) {
			try {
				// ImageIO 支持的图片类型 : [BMP, bmp, jpg, JPG, wbmp, jpeg, png, PNG,
				// JPEG, WBMP, GIF, gif]
				String types = Arrays.toString(ImageIO.getReaderFormatNames());
				String suffix = null;
				// 获取图片后缀
				if (imgFile.getName().indexOf(".") > -1) {
					suffix = imgFile.getName().substring(imgFile.getName().lastIndexOf(".") + 1);
				} // 类型和图片后缀所有小写，然后推断后缀是否合法
				if (suffix == null || types.toLowerCase().indexOf(suffix.toLowerCase()) < 0) {
					log.error("Sorry, the image suffix is illegal. the standard image suffix is {}." + types);
					return;
				}
				log.debug("target image's size, width:{}, height:{}.", w, h);
				Image img = ImageIO.read(imgFile);
				if (!force) {
					// 依据原图与要求的缩略图比例，找到最合适的缩略图比例
					int width = img.getWidth(null);
					int height = img.getHeight(null);
					if ((width * 1.0) / w < (height * 1.0) / h) {
						if (width > w) {
							h = Integer.parseInt(new java.text.DecimalFormat("0").format(height * w / (width * 1.0)));
							log.debug("change image's height, width:{}, height:{}.", w, h);
						}
					} else {
						if (height > h) {
							w = Integer.parseInt(new java.text.DecimalFormat("0").format(width * h / (height * 1.0)));
							log.debug("change image's width, width:{}, height:{}.", w, h);
						}
					}
				}
				BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
				Graphics g = bi.getGraphics();
				g.drawImage(img, 0, 0, w, h, Color.LIGHT_GRAY, null);
				g.dispose();
				String p = imgFile.getPath();
				// 将图片保存在原文件夹并加上前缀
				ImageIO.write(bi, suffix, new File(dest));
				log.debug("缩略图在原路径下生成成功");
			} catch (IOException e) {
				log.error("generate thumbnail image failed.", e);
			}
		} else {
			log.warn("the image is not exist.");
		}
	}
	
	public static void thumbnailImage(String imagePath, String dest) {
		thumbnailImage(imagePath, MediaUtil.DEFAULT_THUMBNAIL_WIDTH, MediaUtil.DEFAULT_THUMBNAIL_HEIGHT, dest, MediaUtil.DEFAULT_THUMBNAIL_FORCE);
	}

	public static void main(String[] args) {
		try {
			//MediaUtil.fetchFrame("F:/Work/Warcraft III/Movies/NightElfEd.mpq", "D:/test4.jpg");
			MediaUtil.thumbnailImage("D:/faultCode.xlsx", "D:/test4.thumbnail.jpg");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
