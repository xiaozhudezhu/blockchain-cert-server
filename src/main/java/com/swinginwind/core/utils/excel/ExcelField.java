package com.swinginwind.core.utils.excel;

/**
 * Excel导入导出用到的列配置
 * 
 * @author Jimmy
 * 
 */
public class ExcelField {

    /**
     * 列编码
     */
    private String              fieldCode;

    /**
     * 列名称（标题）
     */
    private String              fieldName;

    /**
     * 列显示宽度
     */
    private int                 fieldWidth;

    /**
     * 类型
     */
    private FieldTypeEnum       fieldType;

    /**
     * 行位置
     */
    private int                 rowIndex;

    /**
     * 列位置
     */
    private int                 columnIndex;

    /**
     * Excel显示格式<br/>
     * 导出时自定义数据的显示格式 如：@、#.##等等。具体参见Excel
     */
    private String              dataFormat;

    /**
     * 日期格式，如yyyy-MM-dd <br/>
     * 日期字符串互转时用
     */
    private String              format;

    /**
     * 导入时是否转换
     */
    private boolean             doImpConvert;

    /**
     * 导出时是否转换
     */
    private boolean             doExpConvert;

    /**
     * 值转换器
     */
    private ExcelValueConverter converter;
    
    /**
     * 验证单元格值的正则表达式
     */
    private String              regex;

    public ExcelField() {
    }

    /**
     * 构造函数
     * 
     * @param fieldName
     *            列名称
     * @param fieldType
     *            列类型
     */
    public ExcelField(String fieldCode, String fieldName, FieldTypeEnum fieldType) {
        this.setFieldCode(fieldCode);
        this.setFieldName(fieldName);
        this.setFieldType(fieldType);
    }

    public ExcelField(String fieldCode, String fieldName, FieldTypeEnum fieldType, int rowIndexm, int columnIndex) {
        this.setFieldCode(fieldCode);
        this.setFieldName(fieldName);
        this.setFieldType(fieldType);
        this.setRowIndex(rowIndexm);
        this.setColumnIndex(columnIndex);
    }

    public ExcelField(String fieldCode, String fieldName, FieldTypeEnum fieldType, String dataFormat) {
        this.setFieldCode(fieldCode);
        this.setFieldName(fieldName);
        this.setFieldType(fieldType);
        this.setDataFormat(dataFormat);
    }

    /**
     * 构造函数
     * 
     * @param fieldName
     *            列名称
     * @param fieldWidth
     *            列宽度
     * @param fieldType
     *            列类型
     */
    public ExcelField(String fieldCode, String fieldName, int fieldWidth, FieldTypeEnum fieldType) {
        this.setFieldCode(fieldCode);
        this.setFieldName(fieldName);
        this.setFieldWidth(fieldWidth);
        this.setFieldType(fieldType);
        this.setDoImpConvert(false);
        this.setDoExpConvert(false);
        this.setConverter(null);
    }

    /**
     * 构造函数
     * 
     * @param fieldName
     *            列名称
     * @param fieldWidth
     *            列宽度
     * @param fieldType
     *            列类型
     */
    public ExcelField(String fieldCode, String fieldName, int fieldWidth, FieldTypeEnum fieldType, String format) {
        this.setFieldCode(fieldCode);
        this.setFieldName(fieldName);
        this.setFieldWidth(fieldWidth);
        this.setFieldType(fieldType);
        this.setFormat(format);
        this.setDoImpConvert(false);
        this.setDoExpConvert(false);
        this.setConverter(null);
    }

    public ExcelField(String fieldCode, String fieldName, int fieldWidth, FieldTypeEnum fieldType, boolean doImpConvert,
            boolean doExpConvert, ExcelValueConverter converter) {
        this.setFieldCode(fieldCode);
        this.setFieldName(fieldName);
        this.setFieldWidth(fieldWidth);
        this.setDoImpConvert(doImpConvert);
        this.setDoExpConvert(doExpConvert);
        this.setConverter(converter);
        this.setFieldType(fieldType);
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public int getFieldWidth() {
        return fieldWidth;
    }

    public void setFieldWidth(int fieldWidth) {
        this.fieldWidth = fieldWidth;
    }

    public boolean isDoImpConvert() {
        return doImpConvert;
    }

    public void setDoImpConvert(boolean doImpConvert) {
        this.doImpConvert = doImpConvert;
    }

    public boolean isDoExpConvert() {
        return doExpConvert;
    }

    public void setDoExpConvert(boolean doExpConvert) {
        this.doExpConvert = doExpConvert;
    }

    public ExcelValueConverter getConverter() {
        return converter;
    }

    public void setConverter(ExcelValueConverter converter) {
        this.converter = converter;
    }

    public String getFieldCode() {
        return fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public FieldTypeEnum getFieldType() {
        return fieldType;
    }

    public void setFieldType(FieldTypeEnum fieldType) {
        this.fieldType = fieldType;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getDataFormat() {
        return dataFormat;
    }

    public void setDataFormat(String dataFormat) {
        this.dataFormat = dataFormat;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    @Override
    public boolean equals(Object anObject) {
        if (this == anObject) {
            return true;
        }
        if (anObject instanceof ExcelField) {
            ExcelField anotherExcelField = (ExcelField) anObject;
            if (this.fieldCode == anotherExcelField.fieldCode
                    || this.fieldCode != null
                    && this.fieldCode.equals(anotherExcelField.fieldCode)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return this.fieldName + "-->" + this.fieldCode + "-->" + this.fieldType;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

}
