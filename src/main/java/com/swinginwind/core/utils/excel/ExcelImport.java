package com.swinginwind.core.utils.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;

@SuppressWarnings("unchecked")
public class ExcelImport {

    /**
     * Excel导入<br/>
     * 1、增加2003及以上的版本支持<br/>
     * 2、增加跳过指定的行和列功能<br/>
     * 3、增加获取非列表单元格的独立单元格数据，作为每一行的列。如表头经常有时间，单位等等
     * 4、增加同列夸行的合并单元格的数据获取。处理为下几行的合并格里的值为合并格的首格值
     * 
     * @param inputStream
     * @param config
     * @return
     * @throws Exception
     *
     * @author 创建人：郑阳文   2017年5月5日 上午10:51:22
     * @author 修改人：郑阳文   2017年5月5日 上午10:51:22
     */
    public static List<Map<String, Object>> importExcel(InputStream inputStream, ExcelConfig config) throws Exception {
        if (inputStream == null || config == null || config.getFieldList() == null) return null;
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        try {
            // 得到目标目标类的所有的字段列表
            List<ExcelField> fieldList = config.getFieldList();

            // 得到工作表
            Workbook book = WorkbookFactory.create(inputStream);
            Sheet sheet = null;
            // 根据名字获取指定sheet页
            if (StringUtils.isNotEmpty(config.getSheetName())) {
                sheet = book.getSheet(config.getSheetName());
            } 
            // 如果根据名字没有获取到，就得到第一页sheet
            if (sheet == null) {
                sheet = book.getSheetAt(0);
            }

            DataFormatter objDefaultFormat = new DataFormatter();
            //FormulaEvaluator objFormulaEvaluator = new HSSFFormulaEvaluator(book);
            FormulaEvaluator objFormulaEvaluator = book.getCreationHelper().createFormulaEvaluator();
            
            // 解析行上块状excel，块状excel就是一张张相同的表单，一块块整体出现在几行上。
            if (config.getAreaRowNum() > 0) {
                return importAreaExcel(sheet, config, objDefaultFormat, objFormulaEvaluator);
            }

            // 获取excel里固定单元格作为每一行的字段
            Map<String, Object> fixedFields = getFixedCellValue(sheet, config, 0, objDefaultFormat, objFormulaEvaluator);
            
            // 获取所有合并单元格
            List<CellRangeAddress> cellRanges = getAllMergedRegions(sheet);
            
            // 得到第一页的所有行
            Iterator<Row> rowIterator = sheet.rowIterator();
            // 跳过大标题行
            for (int i = 0; i < config.getTitleRowIndex(); i++) {
                rowIterator.next();
            }
            // 跳过列标题和数据中间所有行
            for (int i = 0, md = config.getDataRowIndex() - config.getTitleRowIndex(); i < md; i++) {
                rowIterator.next();
            }
            // 存储上一行的数据
            Map<String, Object> lastRowData = null;
            // 获取尾行
            int lastRowNum = sheet.getLastRowNum();
            // 遍历行
            row : while (rowIterator.hasNext()) {
                // 列标题下的第一行，也就是数据行
                Row dataRow = rowIterator.next();
                int rowIndex = dataRow.getRowNum();
                // 如果当前行超过了指定尾行则结束
                if (config.getDataEndRowIndex() > 0 && rowIndex >= config.getDataEndRowIndex())
                    break;
                // 如果当前行超过了指定尾行则结束,为负数是代表倒数第几行为尾行
                if (config.getDataEndRowIndex() < 0 && rowIndex > lastRowNum + config.getDataEndRowIndex())
                    break;
                // 如当前行是指定要忽略的行，则直接跳过进入下一行
                if (config.getSkipRowIndexes() != null && config.getSkipRowIndexes().contains(rowIndex)) {
                    continue;
                }
                // 定义行数据对象，用来存储行数据
                Map<String, Object> rowData = new HashMap<String, Object>();
                // 已跳过的列数
                int skipColumnCount = 0;
                // 跳过的字段个数
                int skipFieldCount = 0;
                // 获取尾列
                int lastCellNum = dataRow.getLastCellNum();
                // 遍历一行的列
                for (int k = 0; k < fieldList.size(); k++) {
                    // 这里得到此列的对应的标题
                    ExcelField field = fieldList.get(k);
                    if (field == null) continue;
                    String fieldCode = field.getFieldCode();
                    // 如果该字段是指定要忽略的字段，直接进入下一字段
                    if (config.getSkipFieldCodes() != null && config.getSkipFieldCodes().contains(fieldCode)) {
                        skipFieldCount++;
                        continue;
                    }
                    // 得到列下标
                    int columnIndex = config.getStartColumnIndex() + skipColumnCount + k - skipFieldCount;
                    // 如当前列是指定要忽略的列，则直接取下一列
                    while (config.getSkipColumnIndexes() != null && config.getSkipColumnIndexes().contains(columnIndex)) {
                        columnIndex++;
                        skipColumnCount++;
                    }
                    // 如果当前列超过了指定尾列则结束
                    if (config.getEndColumnIndex() > 0 && columnIndex >= config.getEndColumnIndex())
                        break;
                    // 如果当前列超过了指定尾列则结束,为负数是代表倒数第几列为尾列
                    if (config.getEndColumnIndex() < 0 && columnIndex >= lastCellNum + config.getEndColumnIndex())
                        break;
                    // 得到单元格
                    Cell cell = dataRow.getCell(columnIndex, Row.RETURN_BLANK_AS_NULL);
                    Object value = null;
                    if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK) {
                        // 得到单元格的值
                        value = getCellValue(cell, field, objDefaultFormat, objFormulaEvaluator);
                    } else if (isInMergedRegions(cellRanges, rowIndex, columnIndex)) {
                        // 如是同列的夸行合并单元格，则拿上一列作为当前值
                        // 这里不做同列判断，就认定该excel没有夸列的数据，因为这样的excel属于复杂情况，与数据库的行列没法对应。
                        // 要么该行数据是无用数据，作忽略行配置，要么合并后面的几列就是无用列，作忽略列配置
                        value = lastRowData.get(fieldCode);
                    }
                    // 验证需要验证的单元格值
                    int fieldIdx = -1;
                    if (config.getIncludeRowValidFieldList() != null && (fieldIdx = config.getIncludeRowValidFieldList().indexOf(field)) != -1) {
                        String regex = config.getIncludeRowValidFieldList().get(fieldIdx).getRegex();
                        String v = value == null ? "" : value.toString();
                        if (!v.matches(regex)) {
                            continue row;
                        }
                    }
                    if (value != null) rowData.put(fieldCode, value);
                }
                if (!rowData.isEmpty()) {
                    // 增加固定行数据值
                    rowData.putAll(fixedFields);
                    resultList.add(rowData);
                    lastRowData = rowData;
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return resultList;
    }
    /**
     * 导入块状表单excel
     * 
     * @param sheet
     * @param config
     * @param objDefaultFormat
     * @param objFormulaEvaluator
     * @return
     *
     * @author 创建人：郑阳文   2017年5月5日 下午5:50:02
     * @author 修改人：郑阳文   2017年5月5日 下午5:50:02
     */
    public static List<Map<String, Object>> importAreaExcel(Sheet sheet, ExcelConfig config, DataFormatter objDefaultFormat, FormulaEvaluator objFormulaEvaluator) {
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        // 获取尾行
        int lastRowNum = sheet.getLastRowNum();
        int rowNum = 0;
        while (rowNum < lastRowNum) {
            resultList.add(getFixedCellValue(sheet, config, rowNum, objDefaultFormat, objFormulaEvaluator));
            rowNum += config.getAreaRowNum() + config.getSpaceRowNum();
        }
        return resultList;
    }
    /**
     * 判断指定单元格是否是合并单元格
     * 
     * @param cellRanges
     * @param rowIdx
     * @param columnIdx
     * @return
     *
     * @author 创建人：郑阳文   2017年5月5日 上午9:59:45
     * @author 修改人：郑阳文   2017年5月5日 上午9:59:45
     */
    public static boolean isInMergedRegions(List<CellRangeAddress> cellRanges, int rowIdx, int columnIdx) {
        for (CellRangeAddress cellRange : cellRanges) {
            if (cellRange.isInRange(rowIdx, columnIdx)) {
                return true;
            }
        }
        return false;
    }
    /**
     * 获取sheet中所有的合并单元格
     * 
     * @param sheet
     * @return
     *
     * @author 创建人：郑阳文   2017年5月5日 上午9:56:37
     * @author 修改人：郑阳文   2017年5月5日 上午9:56:37
     */
    public static List<CellRangeAddress> getAllMergedRegions(Sheet sheet) {
        List<CellRangeAddress> cellRanges = new ArrayList<CellRangeAddress>();
        int num = sheet.getNumMergedRegions();
        for (int i = 0;i < num;i++) {
            cellRanges.add(sheet.getMergedRegion(i));
        }
        return cellRanges;
    }
    /**
     * 得到指定单元格的值
     * 
     * @param cell
     * @param field
     * @param objDefaultFormat
     * @param objFormulaEvaluator
     * @return
     *
     * @author 创建人：郑阳文   2017年5月5日 上午9:53:09
     * @author 修改人：郑阳文   2017年5月5日 上午9:53:09
     */
    public static Object getCellValue(Cell cell, ExcelField field, DataFormatter objDefaultFormat, FormulaEvaluator objFormulaEvaluator) {
        Object value = null;
        if (field.isDoImpConvert() && field.getConverter() != null) {
            value = field.getConverter().impConvert(cell.getStringCellValue());
        } else {
            if (field.getFieldType().equals(FieldTypeEnum.String)) {
                value = getStringCellValue(cell, field, objDefaultFormat, objFormulaEvaluator);
            } else if (field.getFieldType().equals(FieldTypeEnum.Number)) {
                Double num = getNumberCellValue(cell);
                if (num != null) value = new BigDecimal(num);
            } else if (field.getFieldType().equals(FieldTypeEnum.Date)) {
                if (field.getFormat() != null && !"".equals(field.getFormat())) {
                    SimpleDateFormat sf = new SimpleDateFormat(field.getFormat());
                    sf.format(cell.getDateCellValue());
                    value = sf.format(cell.getDateCellValue());
                } else value = cell.getDateCellValue();
            } else if (field.getFieldType().equals(FieldTypeEnum.Boolean)) {
                value = cell.getBooleanCellValue();
            }
        }
        if (value instanceof String) value = trim((String)value);
        return value;
    }
    /**
     * 获取所有固定单元格的值，用于不规则的excel，单独获取指定单元格的值
     * 
     * @param sheet
     * @param config
     * @param baseRowNum
     * @param objDefaultFormat
     * @param objFormulaEvaluator
     * @return
     *
     * @author 创建人：郑阳文   2017年5月5日 上午9:54:28
     * @author 修改人：郑阳文   2017年5月5日 上午9:54:28
     */
    public static Map<String, Object> getFixedCellValue(Sheet sheet, ExcelConfig config, int baseRowNum, DataFormatter objDefaultFormat, FormulaEvaluator objFormulaEvaluator) {
        Map<String, Object> fixedFields = new HashMap<String, Object>();
        List<ExcelField> fixedFieldList = config.getFixedFieldList();
        if (fixedFieldList != null) {
            for (ExcelField field : fixedFieldList) {
                int rowIndex = field.getRowIndex();
                int columnIndex = field.getColumnIndex();
                // 调整行
                rowIndex += baseRowNum;
                Row row = sheet.getRow(rowIndex);
                Cell cell = row.getCell(columnIndex, Row.RETURN_BLANK_AS_NULL);
                
                if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) continue;
                
                Object value = getCellValue(cell, field, objDefaultFormat, objFormulaEvaluator);
                // 如fieldName不为空，代表值中包含label，在此去掉
                String fieldName = field.getFieldName();
                if (StringUtils.isNotEmpty(fieldName) && value instanceof String) {
                    value = ((String)value).replaceFirst(fieldName, "");
                }
                fixedFields.put(field.getFieldCode(), value);
            }
        }
        return fixedFields;
    }
    /**
     * 去掉特殊字符
     * 
     * @param str
     * @return
     *
     * @author 创建人：邱千   2017年5月8日 下午2:58:50
     * @author 修改人：邱千   2017年5月8日 下午2:58:50
     */
    public static String trim(String str) {
    	if (str == null) return null;
    	return str.trim().replaceAll("(^ +)|( +$)", "");
    }

    public static Map<String, Object> readRowData(HSSFWorkbook book, Row dataRow, List<ExcelField> fieldList) throws Exception {
        Map<String, Object> obj = new HashMap<String, Object>();
        DataFormatter objDefaultFormat = new DataFormatter();
        FormulaEvaluator objFormulaEvaluator = new HSSFFormulaEvaluator(book);

        // 遍历一行的列
        for (int k = 0; k < fieldList.size(); k++) {
            Cell cell = dataRow.getCell(k, Row.RETURN_BLANK_AS_NULL);
            if (cell != null) {
                // 这里得到此列的对应的标题
                ExcelField field = fieldList.get(k);
                if (field == null) continue;

                if (field.isDoImpConvert() && field.getConverter() != null) {
                    obj.put(field.getFieldCode(), field.getConverter().impConvert(cell.getStringCellValue()));
                } else {
                    if (field.getFieldType().equals(FieldTypeEnum.String)) {
                        obj.put(field.getFieldCode(), getStringCellValue(cell, field, objDefaultFormat, objFormulaEvaluator));
                    } else if (field.getFieldType().equals(FieldTypeEnum.Number)) {
                        Double num = getNumberCellValue(cell);
                        if (num == null) obj.put(field.getFieldCode(), null);
                        else obj.put(field.getFieldCode(), new BigDecimal(num));
                    } else if (field.getFieldType().equals(FieldTypeEnum.Date)) {
                        if (field.getFormat() != null && !"".equals(field.getFormat())) {
                            SimpleDateFormat sf = new SimpleDateFormat(field.getFormat());
                            obj.put(field.getFieldCode(), sf.format(cell.getDateCellValue()));
                        } else obj.put(field.getFieldCode(), cell.getDateCellValue());
                    } else if (field.getFieldType().equals(FieldTypeEnum.Boolean)) {
                        obj.put(field.getFieldCode(), cell.getBooleanCellValue());
                    }
                }
            }
        }
        return obj;
    }

    /**
     * 
     * @param file
     * @param config
     * @return
     * @throws Exception
     */
    public static List<Map<String, Object>> importExcel(File file, ExcelConfig config) throws Exception {
        if (file == null || !file.exists() || config == null || config.getFieldList() == null) return null;
        try {
            // 将传入的File构造为FileInputStream;
            FileInputStream in = new FileInputStream(file);

            return ExcelImport.importExcel(in, config);
        } catch (Exception e) {
            // e.printStackTrace();
            throw e;
        }
    }

    private static String getStringCellValue(Cell cell, ExcelField field, DataFormatter objDefaultFormat, FormulaEvaluator objFormulaEvaluator) {// 获取单元格数据内容为字符串类型的数据
        String strCell = "";
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
        case Cell.CELL_TYPE_FORMULA:
            // cell.getCellFormula();
            try {
                /*
                 * 此处判断使用公式生成的字符串有问题，因为HSSFDateUtil.isCellDateFormatted(cell)判断过程中cell
                 * .getNumericCellValue();方法会抛出java.lang.NumberFormatException异常
                 */
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    strCell = getDateCellValue(cell, field.getFormat());
                    break;
                } else {
                    // 保证数字类型获取到正确的String值
                    objFormulaEvaluator.evaluate(cell);
                    strCell = objDefaultFormat.formatCellValue(cell, objFormulaEvaluator);
                    break;
                }
            } catch (IllegalStateException e) {
                strCell = String.valueOf(cell.getRichStringCellValue());
            }
            break;
        case Cell.CELL_TYPE_STRING:
            strCell = cell.getStringCellValue();
            break;
        case Cell.CELL_TYPE_NUMERIC:
            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                strCell = getDateCellValue(cell, field.getFormat());
                break;
            } else {
                // 保证数字类型获取到正确的String值
                objFormulaEvaluator.evaluate(cell);
                strCell = objDefaultFormat.formatCellValue(cell, objFormulaEvaluator);
                break;
            }
        case Cell.CELL_TYPE_BOOLEAN:
            strCell = String.valueOf(cell.getBooleanCellValue());
            break;
        case Cell.CELL_TYPE_BLANK:
            strCell = "";
            break;
        default:
            strCell = "";
            break;
        }
        return strCell;
    }

    /**
     * 获取单元格数据内容为日期类型的数据 描述
     * 
     * @param cell
     * @return
     */
    private static String getDateCellValue(Cell cell, String pattern) {
        String result = "";
        try {
            int cellType = cell.getCellType();
            if (cellType == Cell.CELL_TYPE_NUMERIC) {
                if (StringUtils.isEmpty(pattern)) {
                    pattern = "yyyy-MM-dd HH:mm:ss";
                }
                result = new SimpleDateFormat(pattern).format(cell.getDateCellValue());
            } else if (cellType == Cell.CELL_TYPE_STRING) {
                String date = cell.getStringCellValue();
                result = date.replaceAll("[年月]", "-").replace("日", "").trim();
            } else if (cellType == Cell.CELL_TYPE_BLANK) {
                result = "";
            }
        } catch (Exception e) {
            System.out.println("日期格式不正确!");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取单元格数据内容为数字类型的数据 描述
     * 
     * @param cell
     * @return
     */
    private static Double getNumberCellValue(Cell cell) {
        Double result = null;
        try {
            int cellType = cell.getCellType();
            if (cellType == Cell.CELL_TYPE_FORMULA) 
                cell.setCellType(Cell.CELL_TYPE_NUMERIC);
            if (cellType == Cell.CELL_TYPE_NUMERIC) {
                result = cell.getNumericCellValue();
            } else if (cellType == Cell.CELL_TYPE_STRING) {
                String str = cell.getStringCellValue();
                if (str == null || str.equals("")) result = null;
                else result = Double.parseDouble(str.trim());
            } else if (cellType == Cell.CELL_TYPE_BLANK) {

            }
        } catch (Exception e) {
            System.out.println("数字格式不正确!");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 兼容2003和2007
     * 
     * @param inputStream
     * @param config
     * @param suffixStr
     *            excel后缀名
     * @return
     * @throws Exception
     */
    public static List<Map<String, Object>> importExcelCompatibility(String excelFilePath, ExcelConfig config) throws Exception {
        if (excelFilePath == null || config == null || config.getFieldList() == null) return null;
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        // 得到目标目标类的所有的字段列表
        List<ExcelField> fieldList = config.getFieldList();

        Workbook book = WorkbookFactory.create(new File(excelFilePath));
        /*if (excelFilePath.endsWith(".xls")) {
            FileInputStream stream = new FileInputStream(excelFilePath);
            book = (Workbook) new HSSFWorkbook(stream);
        } else if (excelFilePath.endsWith(".xlsx")) {
            book = (Workbook) new XSSFWorkbook(excelFilePath);
        } else {
            return null;
        }*/

        // // 得到第一页
        // HSSFSheet sheet;
        Sheet sheet;

        if (StringUtils.isNotEmpty(config.getSheetName())) {
            sheet = book.getSheet(config.getSheetName());
        } else {
            sheet = book.getSheetAt(0);
        }

        DataFormatter objDefaultFormat = new DataFormatter();
        FormulaEvaluator objFormulaEvaluator = book.getCreationHelper().createFormulaEvaluator();

        // 得到第一面的所有行
        Iterator<Row> rowIterator = sheet.rowIterator();
        for (int i = 0; i < config.getTitleRowIndex(); i++) {
            rowIterator.next();
        }
        // 跳过标题行
        rowIterator.next();
        // 跳过标题和数据中间所有行
        for (int i = 0; i < config.getDataRowIndex() - config.getTitleRowIndex() - 1; i++) {
            rowIterator.next();
        }

        while (rowIterator.hasNext()) {
            // 标题下的第一行
            Row dataRow = rowIterator.next();

            Map<String, Object> obj = new HashMap<String, Object>();

            // 遍历一行的列
            for (int k = 0; k < fieldList.size(); k++) {
                Cell cell = dataRow.getCell(k, Row.RETURN_BLANK_AS_NULL);
                if (cell != null) {
                    // 这里得到此列的对应的标题
                    ExcelField field = fieldList.get(k);

                    if (field == null) continue;

                    if (field.isDoImpConvert() && field.getConverter() != null) {
                        obj.put(field.getFieldCode(), field.getConverter().impConvert(cell.getStringCellValue()));
                    } else {
                        if (field.getFieldType().equals(FieldTypeEnum.String)) {
                            obj.put(field.getFieldCode(), getStringCellValue(cell, field, objDefaultFormat, objFormulaEvaluator));
                        } else if (field.getFieldType().equals(FieldTypeEnum.Number)) {
                            Double num = getNumberCellValue(cell);
                            if (num == null) obj.put(field.getFieldCode(), null);
                            else obj.put(field.getFieldCode(), new BigDecimal(num));
                        } else if (field.getFieldType().equals(FieldTypeEnum.Date)) {
                            if (field.getFormat() != null && !"".equals(field.getFormat())) {
                                SimpleDateFormat sf = new SimpleDateFormat(field.getFormat());
                                sf.format(cell.getDateCellValue());
                                obj.put(field.getFieldCode(), sf.format(cell.getDateCellValue()));
                            } else obj.put(field.getFieldCode(), cell.getDateCellValue());
                        } else if (field.getFieldType().equals(FieldTypeEnum.Boolean)) {
                            obj.put(field.getFieldCode(), cell.getBooleanCellValue());
                        }
                    }
                }
            }
            resultList.add(obj);
        }
        return resultList;
    }

    public static void main(String[] args) throws Exception {

        // String filepath = "d://test//CRH380B动车组更换类作业指导书.xlsx";
        String filepath = "C:/Users/Administrator/Desktop/template.xls";
        // String filepath = "D://test//CRH380BL动车组更换类作业指导书.xlsx";

        ExcelConfig config = new ExcelConfig("", null);
        config.setDataRowIndex(1);
        ExcelField field0 = new ExcelField("id", "", FieldTypeEnum.String);
        ExcelField field1 = new ExcelField("certTypeName", "", FieldTypeEnum.String);
        ExcelField field2 = new ExcelField("certNo", "", FieldTypeEnum.String);
        ExcelField field3 = new ExcelField("certName", "", FieldTypeEnum.String);
        ExcelField field4 = new ExcelField("ownerName", "", FieldTypeEnum.String);
        ExcelField field5 = new ExcelField("ownerPhone", "", FieldTypeEnum.String);
        ExcelField field6 = new ExcelField("ownerId", "", FieldTypeEnum.String);
        ExcelField field7 = new ExcelField("ownerEmail", "", FieldTypeEnum.String);
        config.addField(field0);
        config.addField(field1);
        config.addField(field2);
        config.addField(field3);
        config.addField(field4);
        config.addField(field5);
        config.addField(field6);
        config.addField(field7);

        List<Map<String, Object>> resultList2 = ExcelImport.importExcelCompatibility(filepath, config);

        System.out.println(resultList2);
    }

}
