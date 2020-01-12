package com.swinginwind.core.utils.excel;

import java.util.ArrayList;
import java.util.List;

/**
 * Excel配置
 * 
 * @author Jimmy
 * 
 */
public class ExcelConfig {

    /**
     * 配置编码
     */
    private String           configCode;

    /**
     * Excel版本
     */
    private String           excelVersion;

    /**
     * 大标题
     */
    private String           head;

    /**
     * 存储的文件名
     */
    private String           fileName;

    /**
     * 标签页名称
     */
    private String           sheetName;

    /**
     * 开始列序号，默认为0
     */
    private int              startColumnIndex;

    /**
     * 结束列序号。默认为0，代表以配置字段的个数决定尾列在哪<br/>
     * 可为负值，代表去掉从尾往前数的列数
     */
    private int              endColumnIndex;

    /**
     * 标题行序号
     */
    private int              titleRowIndex;

    /**
     * 数据行开始序号
     */
    private int              dataRowIndex;

    /**
     * 数据行结束序号。默认为0，代表以excel实际尾行为结束<br/>
     * 可为负值，代表去掉从尾往前数的行数
     */
    private int              dataEndRowIndex;

    /**
     * 列表字段配置
     */
    private List<ExcelField> fieldList;

    /**
     * 编码
     */
    private String           charset;

    /**
     * 忽略excel的列
     */
    private List<Integer>    skipColumnIndexes;

    /**
     * 忽略excel的行
     */
    private List<Integer>    skipRowIndexes;

    /**
     * 固定字段配置
     */
    private List<ExcelField> fixedFieldList;

    /**
     * 忽略的字段编码配置
     */
    private List<String>     skipFieldCodes;

    /**
     * 验证指定字段值，排除验证不通过的字段所在行的数据
     */
    private List<ExcelField> includeRowValidFieldList;
    
    /**
     * 块状表单行数
     */
    private int              areaRowNum;

    /**
     * 块状表单间隔行数
     */
    private int              spaceRowNum;

    public ExcelConfig() {
        this.head = "";
        this.sheetName = "";
        this.fileName = "";
        this.startColumnIndex = 0;
        this.endColumnIndex = 0;
        this.titleRowIndex = 0;
        this.dataRowIndex = 2;
        this.dataEndRowIndex = 0;
        this.areaRowNum = 0;
        this.spaceRowNum = 0;
    }

    public ExcelConfig(String name, List<ExcelField> fieldList) {
        this.head = name;
        this.sheetName = name;
        this.fileName = name;
        this.fieldList = fieldList;
        this.startColumnIndex = 0;
        this.endColumnIndex = 0;
        this.titleRowIndex = 0;
        this.dataRowIndex = 2;
        this.dataEndRowIndex = 0;
        this.areaRowNum = 0;
        this.spaceRowNum = 0;
    }

    /**
     * 添加列表Field
     * 
     * @param field
     */
    public void addField(ExcelField field) {
        if (fieldList == null) fieldList = new ArrayList<ExcelField>();
        fieldList.add(field);
    }

    /**
     * 添加固定Field
     * 
     * @param field
     */
    public void addFixedField(ExcelField field) {
        if (fixedFieldList == null) fixedFieldList = new ArrayList<ExcelField>();
        fixedFieldList.add(field);
    }

    public String getConfigCode() {
        return configCode;
    }

    public void setConfigCode(String configCode) {
        this.configCode = configCode;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public int getTitleRowIndex() {
        return titleRowIndex;
    }

    public void setTitleRowIndex(int titleRowIndex) {
        this.titleRowIndex = titleRowIndex;
    }

    public int getDataRowIndex() {
        return dataRowIndex;
    }

    public void setDataRowIndex(int dataRowIndex) {
        this.dataRowIndex = dataRowIndex;
    }

    public List<ExcelField> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<ExcelField> fieldList) {
        this.fieldList = fieldList;
    }

    public int getStartColumnIndex() {
        return startColumnIndex;
    }

    public void setStartColumnIndex(int startColumnIndex) {
        this.startColumnIndex = startColumnIndex;
    }

    public String getExcelVersion() {
        return excelVersion;
    }

    public void setExcelVersion(String excelVersion) {
        this.excelVersion = excelVersion;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public List<Integer> getSkipColumnIndexes() {
        return skipColumnIndexes;
    }

    public void setSkipColumnIndexes(List<Integer> skipColumnIndexes) {
        this.skipColumnIndexes = skipColumnIndexes;
    }

    public List<Integer> getSkipRowIndexes() {
        return skipRowIndexes;
    }

    public void setSkipRowIndexes(List<Integer> skipRowIndexes) {
        this.skipRowIndexes = skipRowIndexes;
    }

    public List<ExcelField> getFixedFieldList() {
        return fixedFieldList;
    }

    public void setFixedFieldList(List<ExcelField> fixedFieldList) {
        this.fixedFieldList = fixedFieldList;
    }

    public int getEndColumnIndex() {
        return endColumnIndex;
    }

    public void setEndColumnIndex(int endColumnIndex) {
        this.endColumnIndex = endColumnIndex;
    }

    public int getDataEndRowIndex() {
        return dataEndRowIndex;
    }

    public void setDataEndRowIndex(int dataEndRowIndex) {
        this.dataEndRowIndex = dataEndRowIndex;
    }

    public List<String> getSkipFieldCodes() {
        return skipFieldCodes;
    }

    public void setSkipFieldCodes(List<String> skipFieldCodes) {
        this.skipFieldCodes = skipFieldCodes;
    }

    public List<ExcelField> getIncludeRowValidFieldList() {
        return includeRowValidFieldList;
    }

    public void setIncludeRowValidFieldList(List<ExcelField> includeRowValidFieldList) {
        this.includeRowValidFieldList = includeRowValidFieldList;
    }

    public int getSpaceRowNum() {
        return spaceRowNum;
    }

    public void setSpaceRowNum(int spaceRowNum) {
        this.spaceRowNum = spaceRowNum;
    }

    public int getAreaRowNum() {
        return areaRowNum;
    }

    public void setAreaRowNum(int areaRowNum) {
        this.areaRowNum = areaRowNum;
    }

}
