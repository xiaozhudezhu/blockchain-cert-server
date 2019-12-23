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
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FaultKbTest {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		File file = new File("D:/faultCode1.xlsx");
		System.out.println(file.exists());
		XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));
		XSSFSheet sheet = workbook.getSheetAt(0);
		int count = 0;
		List<IsoRecord> isoList = new ArrayList<IsoRecord>();
		String dateString = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		for (int j = 2; j < sheet.getLastRowNum() + 1; j++) {// getLastRowNum，获取最后一行的行标
			XSSFRow row = sheet.getRow(j);
			if (row != null) {
				row.getCell(0).setCellType(HSSFCell.CELL_TYPE_STRING);
				String sysCode = row.getCell(0).getStringCellValue();
				row.getCell(2).setCellType(HSSFCell.CELL_TYPE_STRING);
				String faultCode = row.getCell(2).getStringCellValue();
				row.getCell(4).setCellType(HSSFCell.CELL_TYPE_STRING);
				String faultName = row.getCell(4).getStringCellValue();
				String isoContent = "";
				try {
					isoContent = row.getCell(17).getStringCellValue();
				} catch (Exception e) {
					isoContent = "0";
				}
				if ("0".equals(isoContent))
					continue;
				if (StringUtils.isEmpty(isoContent)) {
					if (isoList.size() > 0) {
						isoList.get(isoList.size() - 1).getFaultCodeList().add(faultCode);
					}
					continue;
				}
				System.out.println(sysCode + " " + faultCode + " " + faultName);
				System.out.println(isoContent);
				count++;
				IsoRecord record = new IsoRecord();
				record.setDesc(faultName);
				record.setName(faultName);
				record.setSysCode(sysCode);
				record.setCode("FI-" + dateString + "-" + StringUtils.leftPad(String.valueOf(count + 1), 3, "0"));
				record.setStepList(getIsoStepsFromString(isoContent));
				record.getFaultCodeList().add(faultCode);
				isoList.add(record);
			}
		}
		for (IsoRecord iso : isoList) {
			if (iso.getFaultCodeList().size() == 1) {
				iso.setName("[" + iso.getFaultCodeList().get(0) + "]" + iso.getName());
			} else
				iso.setName("[" + iso.getFaultCodeList().get(0) + "-"
						+ iso.getFaultCodeList().get(iso.getFaultCodeList().size() - 1) + "]" + iso.getName());
		}

		System.out.println(count);
		
		writeExcel(isoList);
	}
	
	private static void writeExcel(List<IsoRecord> isoList) throws FileNotFoundException, IOException {
		File file = new File("D:/iso.xls");
		System.out.println(file.exists());
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(file));
		HSSFSheet sheet = workbook.getSheetAt(0);
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框  
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框  
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框  
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框  
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		cellStyle.setWrapText(true);
		for(int i = 0; i < 17; i ++) {
			sheet.setDefaultColumnStyle(i, cellStyle);
		}
		for (int j = 1; j < sheet.getLastRowNum() + 1; j++) {
			sheet.removeRow(sheet.getRow(j));
		}
		int count = 0;
		for(IsoRecord iso : isoList) {
			int k = 0, isoCount = 0;
			for(IsoStep step : iso.getStepList()) {
				count ++;
				isoCount ++;
				Row row = sheet.createRow(count);
				Cell cell = null;
				if(k == 0) {
					cell = row.createCell(0); 
			        cell.setCellValue(iso.getSysCode());
			        cell = row.createCell(1);
			        cell.setCellValue(iso.getCode());
			        cell = row.createCell(2);
			        cell.setCellValue(iso.getName());
			        cell = row.createCell(3);
			        cell.setCellValue(iso.getDesc());
				}
				cell = row.createCell(4);
				cell.setCellValue(String.valueOf(step.getIndex()));
				cell = row.createCell(5);
				cell.setCellValue(step.getName());
				cell = row.createCell(6);
				cell.setCellValue(step.getActivity());
				cell = row.createCell(7);
				cell.setCellValue(step.getDesc());
				cell = row.createCell(8);
				cell.setCellValue(step.getQuestion());
				cell = row.createCell(9);
				cell.setCellValue(step.getType());
				cell = row.createCell(10);
				cell.setCellValue(step.getTask());
		        k ++;
		        if(step.getRelationList() != null && step.getRelationList().size() > 0) {
		        	IsoStepRelation relation = step.getRelationList().get(0);
		        	cell = row.createCell(11);
					cell.setCellValue(relation.getNext() == null ? "" : relation.getNext().toString());
					cell = row.createCell(12);
					cell.setCellValue(relation.getCondition());
					cell = row.createCell(13);
					cell.setCellValue(relation.getExpression());
					if(step.getRelationList().size() > 1) {
						int stepCount = 1;
						for(int i = 1; i < step.getRelationList().size(); i ++) {
							count ++;
							isoCount ++;
							stepCount ++;
							row = sheet.createRow(count);
							relation = step.getRelationList().get(i);
				        	cell = row.createCell(11);
							cell.setCellValue(relation.getNext() == null ? "" : relation.getNext().toString());
							cell = row.createCell(12);
							cell.setCellValue(relation.getCondition());
							cell = row.createCell(13);
							cell.setCellValue(relation.getExpression());
						}
						for(int i = 4; i < 11; i ++) {
							CellRangeAddress region = new CellRangeAddress(count - stepCount + 1, count, (short) i, (short) i);
							sheet.addMergedRegion(region);
						}
					}
		        }
			}
			for(int i = 0; i < 4; i ++) {
				CellRangeAddress region = new CellRangeAddress(count - isoCount + 1, count, (short) i, (short) i);
				sheet.addMergedRegion(region);
			}
			for(int i = 14; i < 16; i ++) {
				CellRangeAddress region = new CellRangeAddress(count - isoCount + 1, count, (short) i, (short) i);
				sheet.addMergedRegion(region);
			}
	        
		}
		FileOutputStream fo = new FileOutputStream("D:/iso.xls"); // 输出到文件
        workbook.write(fo);
        
        File file1 = new File("D:/faultCode1.xlsx");
		System.out.println(file1.exists());
		XSSFWorkbook workbook1 = new XSSFWorkbook(new FileInputStream(file1));
		XSSFSheet sheet1 = workbook1.getSheetAt(0);
		Map<String, String> codeMap = new HashMap<String, String>();
		for(IsoRecord iso : isoList) {
			for(String faultCode : iso.getFaultCodeList())
				codeMap.put(faultCode, iso.getCode());
		}
		for (int j = 2; j < sheet1.getLastRowNum() + 1; j++) {// getLastRowNum，获取最后一行的行标
			XSSFRow row = sheet1.getRow(j);
			if (row != null) {
				row.getCell(2).setCellType(HSSFCell.CELL_TYPE_STRING);
				String faultCode = row.getCell(2).getStringCellValue();
				String code = codeMap.get(faultCode);
				row.createCell(18).setCellValue(code);
			}
		}
		FileOutputStream fo1 = new FileOutputStream(file1); // 输出到文件
        workbook1.write(fo1);
	}

	private static List<IsoStep> getIsoStepsFromString(String isoContent) {

		String[] strList = isoContent.split("\n");
		List<IsoStep> stepList = new ArrayList<IsoStep>();
		Map<Integer, Integer> indexMap = new HashMap<Integer, Integer>();
		int count = 0, count1 = 0;
		for (String str : strList) {
			count++;
			count1++;
			indexMap.put(count1, count);
			str = str.trim();
			int i1 = str.indexOf("/");
			int i2 = str.indexOf("#");
			boolean isDoSome = false;
			boolean isStop = false;
			boolean isLast = (count1 == strList.length);
			if (i1 >= 0 && i2 >= 0) {
				isDoSome = true;
			}
			if (str.endsWith("@")) {
				isStop = true;
				str = str.substring(0, str.length() - 1);
			}

			IsoStep step = new IsoStep();
			step.setIndex(count);
			step.setName(str.substring(str.indexOf(".") + 1, i1 >= 0 ? i1 : str.length()).trim());
			step.setActivity(step.getName());
			step.setType("布尔型");
			stepList.add(step);
			List<IsoStepRelation> relationList = new ArrayList<IsoStepRelation>();
			step.setRelationList(relationList);
			if (isDoSome) {
				step.setQuestion("是否异常");
				String doSome = str.substring(i1 + 1, i2);
				String[] nums = doSome.split("&");
				boolean isBranch = false;
				if (nums.length > 1) {
					isBranch = true;
					int k = 0;
					for (String numStr : nums) {
						try {
							int num = Integer.parseInt(numStr);
							IsoStepRelation relation = new IsoStepRelation();
							relation.setCondition(k == 0 ? "是" : "否");
							relation.setNext(num);
							relation.setNeedUpdateIndex(true);
							relationList.add(relation);
							k++;
						} catch (Exception e) {
							isBranch = false;
						}
					}
				}
				if (!isBranch) {
					count++;
					relationList.clear();
					IsoStepRelation relation = new IsoStepRelation();
					relation.setCondition("是");
					relation.setNext(count);
					relationList.add(relation);
					relation = new IsoStepRelation();
					relation.setCondition("否");
					if (!isLast && !isStop)
						relation.setNext(count + 1);
					relationList.add(relation);
					IsoStep step1 = new IsoStep();
					step1.setIndex(count);
					if (StringUtils.isEmpty(doSome)) {
						doSome = "xxx部件";
					}
					step1.setName("更换" + doSome);
					step1.setActivity("更换" + doSome);
					// step1.setQuestion("");
					step1.setType("布尔型");
					stepList.add(step1);
					if (!isLast && !isStop) {
						List<IsoStepRelation> relationList1 = new ArrayList<IsoStepRelation>();
						relation = new IsoStepRelation();
						// relation.setCondition("是");
						relation.setNext(count + 1);
						relationList1.add(relation);
						step1.setRelationList(relationList1);
					}

				}
			} else {
				if (!isLast && !isStop) {
					IsoStepRelation relation = new IsoStepRelation();
					relation.setNext(count + 1);
					relationList.add(relation);
				}
			}
		}
		for (IsoStep step : stepList) {
			if (step.getRelationList() != null && step.getRelationList().size() > 0) {
				for (IsoStepRelation relation : step.getRelationList()) {
					if (relation.isNeedUpdateIndex()) {
						relation.setNext(indexMap.get(relation.getNext()));
					}
				}
			}
		}
		return stepList;
	}

	public static class IsoRecord {
		private String sysCode;
		private String code;
		private String name;
		private String desc;

		private List<String> faultCodeList = new ArrayList<String>();

		private List<IsoStep> stepList;

		public String getSysCode() {
			return sysCode;
		}

		public void setSysCode(String sysCode) {
			this.sysCode = sysCode;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

		public List<IsoStep> getStepList() {
			return stepList;
		}

		public void setStepList(List<IsoStep> stepList) {
			this.stepList = stepList;
		}

		public List<String> getFaultCodeList() {
			return faultCodeList;
		}

		public void setFaultCodeList(List<String> faultCodeList) {
			this.faultCodeList = faultCodeList;
		}
	}

	public static class IsoStep {
		private int index;
		private String name;
		private String activity;
		private String desc;
		private String question;
		private String type;
		private String task;
		private List<IsoStepRelation> relationList;

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getActivity() {
			return activity;
		}

		public void setActivity(String activity) {
			this.activity = activity;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

		public String getQuestion() {
			return question;
		}

		public void setQuestion(String question) {
			this.question = question;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getTask() {
			return task;
		}

		public void setTask(String task) {
			this.task = task;
		}

		public List<IsoStepRelation> getRelationList() {
			return relationList;
		}

		public void setRelationList(List<IsoStepRelation> relationList) {
			this.relationList = relationList;
		}
	}

	public static class IsoStepRelation {
		private Integer next;
		private String condition;
		private String expression;
		private boolean needUpdateIndex;

		public Integer getNext() {
			return next;
		}

		public void setNext(Integer next) {
			this.next = next;
		}

		public String getCondition() {
			return condition;
		}

		public void setCondition(String condition) {
			this.condition = condition;
		}

		public String getExpression() {
			return expression;
		}

		public void setExpression(String expression) {
			this.expression = expression;
		}

		public boolean isNeedUpdateIndex() {
			return needUpdateIndex;
		}

		public void setNeedUpdateIndex(boolean needUpdateIndex) {
			this.needUpdateIndex = needUpdateIndex;
		}
	}

}
