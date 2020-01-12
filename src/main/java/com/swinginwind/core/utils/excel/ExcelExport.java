package com.swinginwind.core.utils.excel;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Clob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;


public class ExcelExport {
    /**
     * Excel导出方法
     * 
     * @param config
     *            导出配置
     * @param dataList
     *            导出数据
     * @param out
     *            导出流
     * @throws Exception
     */
    public static Workbook exportExcel(ExcelConfig config, List<Map<String, Object>> dataList, OutputStream out,
            Workbook workbook, boolean isMultiTable) throws Exception {
        if (workbook == null) {
            // 声明一个工作薄
            workbook = new HSSFWorkbook();
        }
        try {
            if (config == null || config.getFieldList() == null) {
                throw new Exception("传入参数不能为空！");
            }
            // 生成一个表格
            Sheet sheet = workbook.createSheet(config.getSheetName());
            // sheet.setDisplayGridlines(false);// 不显示网格

            // 设置大标题
            if (config.getTitleRowIndex() >= 0) {
                Row row = sheet.createRow(0);
                Cell cell = row.createCell(config.getStartColumnIndex());

                int lastColumnIndex = config.getFieldList().size() - 1 + config.getStartColumnIndex();
                CellStyle style = createHeadCellStyle(workbook, 12, null);
                CellStyle style1 = workbook.createCellStyle(); // 样式对象
                Font font = workbook.createFont();
                font.setBoldweight(Font.BOLDWEIGHT_BOLD);
                style1.setFont(font);

                Cell lastCell = row.createCell(lastColumnIndex);
                if (!isMultiTable) {
                    lastCell.setCellStyle(style);
                    row.setHeightInPoints((short) 30.5);
                } else {
                    lastCell.setCellStyle(style1);
                }
                sheet.addMergedRegion(new CellRangeAddress(0, config.getTitleRowIndex(), config.getStartColumnIndex(),
                        lastColumnIndex));
                if (!isMultiTable) {
                    cell.setCellStyle(style);
                } else {
                    cell.setCellStyle(style1);
                }
                String headTitle = isMultiTable ? ("#0." + config.getHead()) : config.getHead();
                cell.setCellValue(headTitle);
            }

            // 单元格样式
            CellStyle formatStyle = createBodyCellStyle(workbook);
            DataFormat format = workbook.createDataFormat();

            // 产生表格标题行
            Row row = sheet.createRow(config.getDataRowIndex() - 1);

            // 设置每行的列宽和标题行
            for (int i = 0; i < config.getFieldList().size(); i++) {
                ExcelField field = config.getFieldList().get(i);
                if (field != null) {
                    int columnIndex = i + config.getStartColumnIndex();
                    if (field.getFieldWidth() > 0) sheet.setColumnWidth(columnIndex, 512 * field.getFieldWidth());
                    Cell cell = row.createCell(columnIndex);

                    if (!isMultiTable) {
                        cell.setCellStyle(createHeadCellStyle(workbook, 10, HSSFColor.AQUA.index));
                    }

                    RichTextString text = new HSSFRichTextString(field.getFieldName());
                    cell.setCellValue(text);
                }
            }
            if (dataList != null && dataList.size() > 0) {
                for (int i = 0; i < dataList.size(); i++) {
                    Map<String, Object> obj = dataList.get(i);
                    if (obj == null) continue;
                    row = sheet.createRow(i + config.getDataRowIndex());
                    for (int k = 0; k < config.getFieldList().size(); k++) {
                        ExcelField field = config.getFieldList().get(k);
                        if (field != null) {
                            Cell cell = row.createCell(k + config.getStartColumnIndex());
                            Object value = null;
                            if (field.isDoExpConvert() && field.getConverter() != null) {
                                value = field.getConverter().expConvert(obj.get(field.getFieldCode()));
                            } else {
                                value = obj.get(field.getFieldCode());
                            }
                            if (value == null) value = "";

                            CellStyle cellStyle = formatStyle;
                            if (value.toString().length() >= 240) cellStyle = createBodyCellStyle(workbook);
                            // 获取单元格格式
                            String dataFormat = field.getDataFormat();
                            // 未指定时，默认为@
                            if (StringUtils.isEmpty(dataFormat)) dataFormat = "@";
                            // 设置单元格格式
                            cellStyle.setDataFormat(format.getFormat(dataFormat));
                            // 设置单元格样式
                            cell.setCellStyle(cellStyle);

                            if (field.getFieldType().equals(FieldTypeEnum.Number) && value instanceof BigDecimal) {
                                cell.setCellValue(((BigDecimal) value).doubleValue());
                            } else if (field.getFieldType().equals(FieldTypeEnum.Date) && value instanceof Date) {
                                if (StringUtils.isNotEmpty(field.getFormat())) {
                                    SimpleDateFormat sf = new SimpleDateFormat(field.getFormat());
                                    cell.setCellValue(sf.format(cell.getDateCellValue()));
                                } else {
                                    cell.setCellValue((Date) value);
                                }
                            } else if (field.getFieldType().equals(FieldTypeEnum.Boolean) && value instanceof Boolean) {
                                cell.setCellValue((Boolean) value);
                            } else if (field.getFieldType().equals(FieldTypeEnum.String)) {
                                cell.setCellValue(value.toString());
                            } else if (field.getFieldType().equals(FieldTypeEnum.Clob)) {
                                Clob clob = (Clob) value;
                                cell.setCellValue(clob.getSubString(1, (int) clob.length()));
                            }
                        }
                    }
                }
            }
            if (out != null) {
                workbook.write(out);
            }
        } catch (Exception e) {
            throw e;
        }
        return workbook;
    }
    
    /**
     * 可配置预警故障导出excel
     * @author lxf
     */
    public static HSSFWorkbook createConfigExcel(Map<String,List<Map<String, Object>>> maps,
            Map<String,ExcelConfig> configMaps
        ) throws Exception{
        
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFCellStyle contentStyle = ExcelStyle.getContentStyle(workbook);
        HSSFCellStyle headerStyle0 = ExcelStyle.getHeaderStyleA(workbook);
        HSSFCellStyle headerStyle1 = ExcelStyle.getHeaderStyleA(workbook);
        headerStyle1.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        
        
        if(maps != null && maps.size() > 0 && configMaps != null && configMaps.size() > 0){
            for (Map.Entry<String, List<Map<String, Object>>> entry : maps.entrySet()) 
            {
                ExcelConfig config = configMaps.get(entry.getKey());
                List<Map<String, Object>> dataList = entry.getValue();
                try {
                    if (config == null || config.getFieldList() == null) {
                        throw new Exception("传入参数不能为空！");
                    }
                    // 生成一个表格
                    Sheet sheet = workbook.createSheet(config.getSheetName());
                    // sheet.setDisplayGridlines(false);// 不显示网格

                    // 设置大标题
                    if (config.getTitleRowIndex() > 0) {
                        Row row = sheet.createRow(0);
                        Cell cell = row.createCell(config.getStartColumnIndex());

                        int lastColumnIndex = config.getFieldList().size() - 1 + config.getStartColumnIndex();
                        CellStyle style = createHeadCellStyle(workbook, 12, null);
                        CellStyle style1 = workbook.createCellStyle(); // 样式对象
                        Font font = workbook.createFont();
                        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
                        style1.setFont(font);

                        Cell lastCell = row.createCell(lastColumnIndex);
                   
                            lastCell.setCellStyle(style);
                            row.setHeightInPoints((short) 30.5);
                      
                        sheet.addMergedRegion(new CellRangeAddress(0, config.getTitleRowIndex() - 1, config.getStartColumnIndex(),
                                lastColumnIndex));
                     
                            cell.setCellStyle(style);
                      
                        String headTitle = config.getHead();
                        cell.setCellValue(headTitle);
                    }

                    // 单元格样式
                    CellStyle formatStyle = createBodyCellStyle(workbook);
                    DataFormat format = workbook.createDataFormat();

                    // 产生表格标题行
                    Row row = sheet.createRow(config.getTitleRowIndex());

                    // 设置每行的列宽和标题行
                    for (int i = 0; i < config.getFieldList().size(); i++) {
                        ExcelField field = config.getFieldList().get(i);
                        if (field != null) {
                            int columnIndex = i + config.getStartColumnIndex();
                            if (field.getFieldWidth() > 0) sheet.setColumnWidth(columnIndex, 512 * field.getFieldWidth());
                            Cell cell = row.createCell(columnIndex);

         
                                cell.setCellStyle(createHeadCellStyle(workbook, 10, HSSFColor.AQUA.index));
                       

                            RichTextString text = new HSSFRichTextString(field.getFieldName());
                            cell.setCellValue(text);
                        }
                    }
                    if (dataList != null && dataList.size() > 0) {
                        for (int i = 0; i < dataList.size(); i++) {
                            Map<String, Object> obj = dataList.get(i);
                            if (obj == null) continue;
                            row = sheet.createRow(i + config.getDataRowIndex());
                            for (int k = 0; k < config.getFieldList().size(); k++) {
                                ExcelField field = config.getFieldList().get(k);
                                if (field != null) {
                                    Cell cell = row.createCell(k + config.getStartColumnIndex());
                                    Object value = null;
                                    if (field.isDoExpConvert() && field.getConverter() != null) {
                                        value = field.getConverter().expConvert(obj.get(field.getFieldCode()));
                                    } else {
                                        value = obj.get(field.getFieldCode());
                                    }
                                    if (value == null) value = "";

                                    CellStyle cellStyle = formatStyle;
                                    if (value.toString().length() >= 240) cellStyle = createBodyCellStyle(workbook);
                                    // 获取单元格格式
                                    String dataFormat = field.getDataFormat();
                                    // 未指定时，默认为@
                                    if (StringUtils.isEmpty(dataFormat)) dataFormat = "@";
                                    // 设置单元格格式
                                    cellStyle.setDataFormat(format.getFormat(dataFormat));
                                    // 设置单元格样式
                                    cell.setCellStyle(cellStyle);

                                    if (field.getFieldType().equals(FieldTypeEnum.Number) && value instanceof BigDecimal) {
                                        cell.setCellValue(((BigDecimal) value).doubleValue());
                                    } else if (field.getFieldType().equals(FieldTypeEnum.Date) && value instanceof Date) {
                                        if (StringUtils.isNotEmpty(field.getFormat())) {
                                            SimpleDateFormat sf = new SimpleDateFormat(field.getFormat());
                                            cell.setCellValue(sf.format(cell.getDateCellValue()));
                                        } else {
                                            cell.setCellValue((Date) value);
                                        }
                                    } else if (field.getFieldType().equals(FieldTypeEnum.Boolean) && value instanceof Boolean) {
                                        cell.setCellValue((Boolean) value);
                                    } else if (field.getFieldType().equals(FieldTypeEnum.String)) {
                                        cell.setCellValue(value.toString());
                                    } else if (field.getFieldType().equals(FieldTypeEnum.Clob)) {
                                        Clob clob = (Clob) value;
                                        cell.setCellValue(clob.getSubString(1, (int) clob.length()));
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    throw e;
                }
            }
        }
        
        if(workbook.getNumberOfSheets() == 0){
            workbook.createSheet();
        }
        
        return workbook;
    }
    
    


    public static CellStyle createBodyCellStyle(Workbook workbook) {
        CellStyle wrapBorderStyle = workbook.createCellStyle();
        wrapBorderStyle.setWrapText(true);// 自动换行
        wrapBorderStyle.setBorderBottom((short) 1);
        wrapBorderStyle.setBorderLeft((short) 1);
        wrapBorderStyle.setBorderRight((short) 1);
        wrapBorderStyle.setBorderTop((short) 1);
        wrapBorderStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        return wrapBorderStyle;
    }

    public static CellStyle createHeadCellStyle(Workbook workbook, int fontSize, Short backColor) {
        if (workbook == null) return null;
        CellStyle style = workbook.createCellStyle(); // 样式对象
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平
        style.setBorderBottom((short) 1);
        style.setBorderLeft((short) 1);
        style.setBorderRight((short) 1);
        style.setBorderTop((short) 1);
        if (backColor != null) {
            style.setFillForegroundColor(backColor);
            style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        }
        Font font = workbook.createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        font.setFontHeightInPoints((short) fontSize);
        style.setFont(font);
        return style;
    }

    public static void main(String[] args) throws Exception {
        /*
         * File file = new File("D://test.xls"); ExcelConfig config = new ExcelConfig("测试", null);
         * config.setDataRowIndex(2); ExcelField field1 = new ExcelField("E001", "E001", FieldTypeEnum.String);
         * ExcelField field2 = new ExcelField("E002", "E002", FieldTypeEnum.String); ExcelField field3 = new
         * ExcelField("E003", "E003", FieldTypeEnum.String); ExcelField field4 = new ExcelField("E004", "E004",
         * FieldTypeEnum.Number); ExcelField field5 = new ExcelField("E005", "E005", 0, FieldTypeEnum.Date,
         * "yyyy-MM-dd HH:mm"); config.addField(field1); config.addField(field2); config.addField(field3);
         * config.addField(field4); config.addField(field5); List<Map<String, Object>> resultList =
         * ExcelImport.importExcel(file, config); OutputStream out = new FileOutputStream("D://testOne.xls");
         * ExcelExport.exportExcel(config, resultList, out);
         */

        HSSFWorkbook wb = new HSSFWorkbook();
        CellStyle style = wb.createCellStyle();
        wb.getCustomPalette().setColorAtIndex(HSSFColor.LIGHT_GREEN.index, (byte) 158, (byte) 213, (byte) 97);
        style.setFillForegroundColor(HSSFColor.AQUA.index);
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        wb.createSheet().createRow(0).createCell(0).setCellStyle(style);
        wb.write(new FileOutputStream("D://sss.xls"));
    }

    public static void createTitleRow(Workbook workbook, Sheet sheet, ExcelConfig config, int rowIndex) {
        // 产生表格标题行
        Row row = sheet.createRow(rowIndex);

        // 设置每行的列宽和标题行
        for (int i = 0; i < config.getFieldList().size(); i++) {
            ExcelField field = config.getFieldList().get(i);
            if (field != null) {
                int columnIndex = i + config.getStartColumnIndex();
                /*
                 * if (field.getFieldWidth() > 0) sheet.setColumnWidth(columnIndex, 512 * field.getFieldWidth());
                 */
                Cell cell = row.createCell(columnIndex);

                // cell.setCellStyle(createHeadCellStyle(workbook, 10, HSSFColor.AQUA.index));

                RichTextString text = new HSSFRichTextString(field.getFieldName());
                cell.setCellValue(text);
            }
        }
    }

    public static void updateExcel(Workbook book, OutputStream out, List<ExcelConfig> configList, boolean isHiddenCol)
            throws Exception {
        try {
            if (configList == null || configList.isEmpty() || out == null || book == null) {
                throw new Exception("传入参数不能为空！");
            }
            // 得到工作表
            // Workbook book = new HSSFWorkbook(in);
            // // 目前只有多表的时候会调用此方法，目前仅用于作业指导书，作业指导书第一页是说明，所以得到第二个sheet页
            int sn = book.getNumberOfSheets();
            Sheet sheet = book.getSheetAt(sn < 1 ? 0 : sn - 1);
            int rowIndex = 3;
            int order = 1;
            for (ExcelConfig config : configList) {
                /*
                 * Row row = sheet.createRow(rowIndex); Cell cell = row.createCell(0); cell.setCellValue("#" + order +
                 * "." + config.getHead());
                 */
                // 设置大标题
                Row row = sheet.createRow(rowIndex);
                // row.setHeightInPoints((short) 20);
                Cell cell = row.createCell(config.getStartColumnIndex());

                CellStyle style = book.createCellStyle(); // 样式对象
                Font font = book.createFont();
                font.setBoldweight(Font.BOLDWEIGHT_BOLD);
                style.setFont(font);
                // 设置自动换行 @王鹏飞
                style.setWrapText(true);

                int lastColumnIndex = config.getFieldList().size() - 1 + config.getStartColumnIndex();
                // CellStyle style = createHeadCellStyle(book, 12, null);
                row.createCell(lastColumnIndex).setCellStyle(style);
                sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, config.getStartColumnIndex(), lastColumnIndex));
                cell.setCellStyle(style);
                cell.setCellValue("#" + order + "." + config.getHead());

                rowIndex++;
                createTitleRow(book, sheet, config, rowIndex);
                rowIndex += 2;
                order++;
            }
            if (isHiddenCol) {
                sheet.setColumnHidden(0, true);
            }
            book.write(out);
        } catch (Exception e) {
            throw e;
        }
    }

   

    public static String getSelectValue(String sourse) {
        String resultStr = null;
        // sourse="[{key:'1',value:'普通'},{key:'2',value:'秘密'}]";
        if (sourse.startsWith("[{")) {
            resultStr = sourse.replace("[", "").replace("]", "").replace("{", "").replace("}", "").replace("key:", "")
                    .replace("value:", "");
        }
        return resultStr;
    }
}
