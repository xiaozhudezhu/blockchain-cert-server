package org.apache.poi.hwpf.usermodel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FaultKbTest2 {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		File file = new File("D:/faultCode2.xlsx");
		System.out.println(file.exists());
		XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));
		XSSFSheet sheet = workbook.getSheetAt(0);
		int count = 0;
		List<FaultMode> fmList = new ArrayList<FaultMode>();
		Map<String, FaultMode> fmMap = new HashMap<String, FaultMode>();
		Map<String, String> fmCodeMap = new HashMap<String, String>();
		for (int j = 1; j < sheet.getLastRowNum() + 1; j++) {// getLastRowNum，获取最后一行的行标
			XSSFRow row = sheet.getRow(j);
			if (row != null) {
				row.getCell(0, Row.CREATE_NULL_AS_BLANK).setCellType(HSSFCell.CELL_TYPE_STRING);
				String faultCode = row.getCell(0).getStringCellValue();
				row.getCell(1, Row.CREATE_NULL_AS_BLANK).setCellType(HSSFCell.CELL_TYPE_STRING);
				String mtrCodes = row.getCell(1).getStringCellValue();
				row.getCell(2, Row.CREATE_NULL_AS_BLANK).setCellType(HSSFCell.CELL_TYPE_STRING);
				String supplierCodes = row.getCell(2).getStringCellValue();
				row.getCell(3, Row.CREATE_NULL_AS_BLANK).setCellType(HSSFCell.CELL_TYPE_STRING);
				String mtrNames = row.getCell(3).getStringCellValue();
				row.getCell(4, Row.CREATE_NULL_AS_BLANK).setCellType(HSSFCell.CELL_TYPE_STRING);
				String repairTaskCodes = row.getCell(4).getStringCellValue();
				row.getCell(6, Row.CREATE_NULL_AS_BLANK).setCellType(HSSFCell.CELL_TYPE_STRING);
				String fmNames = row.getCell(6).getStringCellValue();

				row.getCell(5, Row.CREATE_NULL_AS_BLANK).setCellType(HSSFCell.CELL_TYPE_STRING);
				String fmCodes = row.getCell(5).getStringCellValue();
				if (StringUtils.isEmpty(mtrCodes) || StringUtils.isEmpty(fmCodes)) {
					continue;
				}

				String[] mtrCodeArray = mtrCodes.split("\\|", -1);
				String[] supplierCodeArray = supplierCodes.split("\\|", -1);
				String[] mtrNameArray = mtrNames.replaceAll("\\\\", "|").split("\\|", -1);
				String[] repairTaskCodeArray = repairTaskCodes.split("\\|", -1);
				String[] fmNameArray = fmNames.split("\\|", -1);
				String[] fmCodeArray = fmCodes.split("\\|", -1);
				for (int k = 0; k < mtrCodeArray.length; k++) {
					String mtrCode = mtrCodeArray[k];
					if (!StringUtils.isEmpty(mtrCode)) {
						for (int p = 0; p < fmCodeArray.length; p++) {
							String fmCode = fmCodeArray[p];
							if (!StringUtils.isEmpty(fmCode)) {
								FaultMode fm = new FaultMode();
								fm.setFmCode(fmCode);
								fm.setFmId(mtrCode + (supplierCodeArray[k] == null ? "" : supplierCodeArray[k]) + fmCode);
								fm.setFmName(mtrNameArray[k] + fmNameArray[p]);
								fm.setMtrCode(mtrCode);
								try {
									fm.setRepairTask(repairTaskCodeArray[k]);
								} catch (Exception e) {

								}
								fm.setSupplierCode(supplierCodeArray[k]);
								if (!fmMap.containsKey(fm.getFmId())) {
									fmMap.put(fm.getFmId(), fm);
									fm.setFaultCodeList(new ArrayList<String>());
									fm.getFaultCodeList().add(faultCode);
									fmList.add(fm);
									count++;
								} else {
									fm = fmMap.get(fm.getFmId());
									fm.getFaultCodeList().add(fmCode);
								}

								if (fmCodeMap.containsKey(faultCode))
									fmCodeMap.put(faultCode, fmCodeMap.get(faultCode) + "/" + fm.getFmId());
								else
									fmCodeMap.put(faultCode, fm.getFmId());
							}
						}
					}

				}
			}
		}

		System.out.println(count);

		writeExcel(fmList, fmCodeMap);
	}

	private static void writeExcel(List<FaultMode> fmList, Map<String, String> fmCodeMap)
			throws FileNotFoundException, IOException {
		File file = new File("D:/faultMode.xls");
		System.out.println(file.exists());
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(file));
		HSSFSheet sheet = workbook.getSheetAt(0);
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		cellStyle.setWrapText(true);
		for (int i = 0; i < 7; i++) {
			sheet.setDefaultColumnStyle(i, cellStyle);
		}
		for (int j = 2; j < sheet.getLastRowNum() + 1; j++) {
			sheet.removeRow(sheet.getRow(j));
		}
		int count = 1;
		for (FaultMode fm : fmList) {
			count++;
			Row row = sheet.createRow(count);
			Cell cell = null;
			cell = row.createCell(0);
			cell.setCellValue(fm.getMtrCode());
			cell = row.createCell(1);
			cell.setCellValue(fm.getSupplierCode());
			cell = row.createCell(2);
			cell.setCellValue(fm.getFmId());
			cell = row.createCell(3);
			cell.setCellValue(fm.getFmCode());
			cell = row.createCell(4);
			cell.setCellValue(fm.getFmName());
			cell = row.createCell(5);
			cell.setCellValue(fm.getTestTask());
			cell = row.createCell(6);
			cell.setCellValue(fm.getRepairTask());
			
		}
		FileOutputStream fo = new FileOutputStream("D:/faultMode.xls"); // 输出到文件
		workbook.write(fo);

		File file1 = new File("D:/faultCode3.xlsx");
		System.out.println(file1.exists());
		XSSFWorkbook workbook1 = new XSSFWorkbook(new FileInputStream(file1));
		XSSFSheet sheet1 = workbook1.getSheetAt(0);

		for (int j = 1; j < sheet1.getLastRowNum() + 1; j++) {// getLastRowNum，获取最后一行的行标
			XSSFRow row = sheet1.getRow(j);
			if (row != null) {
				row.getCell(2).setCellType(HSSFCell.CELL_TYPE_STRING);
				String faultCode = row.getCell(2).getStringCellValue();
				String code = fmCodeMap.get(faultCode);
				Cell cell = row.getCell(18, Row.CREATE_NULL_AS_BLANK);
				if(StringUtils.isEmpty(code))
					continue;
				if(StringUtils.isEmpty(cell.getStringCellValue()))
					cell.setCellValue(code);
				else
					cell.setCellValue(cell.getStringCellValue() + "/" + code);
			}
		}
		FileOutputStream fo1 = new FileOutputStream(file1); // 输出到文件
		workbook1.write(fo1);
	}

	public static class FaultMode {
		private String mtrCode;
		private String supplierCode;
		private String fmId;
		private String fmCode;
		private String fmName;
		private String testTask;
		private String repairTask;
		private List<String> faultCodeList;

		public List<String> getFaultCodeList() {
			return faultCodeList;
		}

		public void setFaultCodeList(List<String> faultCodeList) {
			this.faultCodeList = faultCodeList;
		}

		public String getMtrCode() {
			return mtrCode;
		}

		public void setMtrCode(String mtrCode) {
			this.mtrCode = mtrCode;
		}

		public String getSupplierCode() {
			return supplierCode;
		}

		public void setSupplierCode(String supplierCode) {
			this.supplierCode = supplierCode;
		}

		public String getFmId() {
			return fmId;
		}

		public void setFmId(String fmId) {
			this.fmId = fmId;
		}

		public String getFmCode() {
			return fmCode;
		}

		public void setFmCode(String fmCode) {
			this.fmCode = fmCode;
		}

		public String getFmName() {
			return fmName;
		}

		public void setFmName(String fmName) {
			this.fmName = fmName;
		}

		public String getTestTask() {
			return testTask;
		}

		public void setTestTask(String testTask) {
			this.testTask = testTask;
		}

		public String getRepairTask() {
			return repairTask;
		}

		public void setRepairTask(String repairTask) {
			this.repairTask = repairTask;
		}

	}

}
