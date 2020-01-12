package com.swinginwind.core.utils.excel;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;

/**
 * 
 * @ClassName: ExcelStyle
 * @Description: 设置Excel的样式
 * @author
 * @date 2014-4-17 上午10:38:32
 * 
 */
public class ExcelStyle {

	/**
	 * 
	 * @Title getTitleStyleA
	 * @author gaojc gaojunchao@gener-tech.com
	 * @Description 设置标题样式
	 * @param workbook
	 * @return
	 * @return HSSFCellStyle
	 * @throws
	 */
	public static HSSFCellStyle getTitleStyleA (HSSFWorkbook workbook) {
		 HSSFFont font0 = workbook.createFont(); 
	     font0.setFontName("黑体");
	     font0.setFontHeightInPoints((short) 14);
		 HSSFCellStyle headerStyle0 = workbook.createCellStyle(); 
	     headerStyle0.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
	     headerStyle0.setFillPattern(CellStyle.SOLID_FOREGROUND);
	     headerStyle0.setFont(font0);
	     headerStyle0.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
	     headerStyle0.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//上下居中   
		 return headerStyle0;		 
	}
	/**
	 * 
	 * @Title: getHeaderStyleA
	 * @Description: 设置标题行样式
	 * @param @param workbook
	 * @param @return
	 * @return HSSFCellStyle
	 * @throws
	 */
	public static HSSFCellStyle getHeaderStyleA(HSSFWorkbook workbook) {

		// 创建样式
		HSSFFont font1 = workbook.createFont();
		// 字体加粗
		font1.setFontName("黑体");
		font1.setFontHeightInPoints((short) 12);// 设置字体大小
		// font1.setColor(HSSFColor.LIGHT_BLUE.index); //绿字
		font1.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);

		HSSFCellStyle headerStyle = workbook.createCellStyle();
		// 设置垂直居中
		headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
		// 设置边框
		headerStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		headerStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		headerStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		headerStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		headerStyle.setFont(font1);
		headerStyle.setWrapText(true); 

		return headerStyle;
	}

	/**
	 * 
	 * @Title: getContentStyle
	 * @Description: 设置输出内容格式
	 * @param @param workbook
	 * @param @return
	 * @return HSSFCellStyle
	 * @throws
	 */
	public static HSSFCellStyle getContentStyle(HSSFWorkbook workbook) {

		// 设置内容行格式
		HSSFCellStyle contentStyle = workbook.createCellStyle();
		// 设置垂直居中
		contentStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		contentStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
		// 设置边框
		contentStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		contentStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		contentStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		contentStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);

		return contentStyle;
	}

}