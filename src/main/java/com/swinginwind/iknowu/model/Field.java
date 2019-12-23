package com.swinginwind.iknowu.model;

import java.io.Serializable;

public class Field implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String id;
    //�ֶ���
    private String name;
    private String[] names;
    //������
    private String tableId;
    private String tableViewCode;
    //����
    private String type;
    //����
    private Integer length;
    //С��
    private Integer decimalCount;
    //������ʾ
    private String title;
    //��ʾ
    private Integer show;
    //���뷽ʽ
    private String inputType;
    private String[] inputTypeOrs;
    //��Դ
    private String source;
    //����
    private Integer fieldOrder;
    private Integer isEmpty;
    private String rules;
    private Integer isPrimary;
    
    private String fkTable;
    private String fkSaveField;
    private String fkShowField;
    private String[] fkShowFields;
    
    /**
     * 是否可编辑
     */
    private Integer isEditable;
    
    /**
     * 默认值
     */
    private String defaultValue;
    
    /**
     * 是否唯一
     */
    private Integer isUnique;
    
    /**
     * 是否作为部门查询字段
     */
    private Integer isDept;
    
    /**
     * 默认值
     */
    private String searchDefaultValue;
    /**
     * 样式
     */
    private String formate;
    
    /**
     * 过滤条件，目前用于外键的级联查询
     */
    private String filterRule_field;
    
    /**
     * 表单列位置
     */
    private String formColPos;
    
    /**
     * 表单行位置
     */
    private String formRowPos;
    
    /**
     * 表单显示宽度
     */
    private String formShow;
    /**
     * 明细表过滤条件
     */
    private String dtFilter;
    
    public Field() {
	}
    
    public Field(String name) {
		this.name = name;
	}
    
    public Field(String[] names,String tableViewCode) {
		this.names = names;
		this.tableViewCode = tableViewCode;
	}
    
    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    
    public void setTableId(String tableId) {
        this.tableId = tableId;
    }
    public String getTableId() {
        return tableId;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }
    
    public void setLength(Integer length) {
        this.length = length;
    }
    public Integer getLength() {
        return length;
    }
    
    public void setDecimalCount(Integer decimalCount) {
        this.decimalCount = decimalCount;
    }
    public Integer getDecimalCount() {
        return decimalCount;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }
    
    public void setShow(Integer show) {
        this.show = show;
    }
    public Integer getShow() {
        return show;
    }
    
    public void setInputType(String inputType) {
        this.inputType = inputType;
    }
    public String getInputType() {
        return inputType;
    }
    
    public void setSource(String source) {
        this.source = source;
    }
    public String getSource() {
        return source;
    }
    
    public void setFieldOrder(Integer fieldOrder) {
        this.fieldOrder = fieldOrder;
    }
    public Integer getFieldOrder() {
        return fieldOrder;
    }
    
    public void setIsEmpty(Integer isEmpty) {
        this.isEmpty = isEmpty;
    }
    public Integer getIsEmpty() {
        return isEmpty;
    }
    
    public void setRules(String rules) {
        this.rules = rules;
    }
    public String getRules() {
        return rules;
    }
    
    public void setIsPrimary(Integer isPrimary) {
        this.isPrimary = isPrimary;
    }
    public Integer getIsPrimary() {
        return isPrimary;
    }
    
    public void setFkSaveField(String saveField) {
        this.fkSaveField = saveField;
    }
    public String getFkSaveField() {
        return fkSaveField;
    }
    
    /**
     * @return the defaultValue
     */
    public String getDefaultValue()
    {
        return defaultValue;
    }
    /**
     * @param defaultValue the defaultValue to set
     */
    public void setDefaultValue(String defaultValue)
    {
        this.defaultValue = defaultValue;
    }

    /**
     * @return the fkTable
     */
    public String getFkTable()
    {
        return fkTable;
    }
    /**
     * @param fkTable the fkTable to set
     */
    public void setFkTable(String fkTable)
    {
        this.fkTable = fkTable;
    }
    /**
     * @return the fkShowField
     */
    public String getFkShowField()
    {
        return fkShowField;
    }
    /**
     * @param fkShowField the fkShowField to set
     */
    public void setFkShowField(String fkShowField)
    {
        this.fkShowField = fkShowField;
    }
    /**
     * @return the isEditable
     */
    public Integer getIsEditable()
    {
        return isEditable;
    }
    /**
     * @param isEditable the isEditable to set
     */
    public void setIsEditable(Integer isEditable)
    {
        this.isEditable = isEditable;
    }
    /**
     * @return the isUnique
     */
    public Integer getIsUnique()
    {
        return isUnique;
    }
    /**
     * @param isUnique the isUnique to set
     */
    public void setIsUnique(Integer isUnique)
    {
        this.isUnique = isUnique;
    }
    /**
     * @return the isDept
     */
    public Integer getIsDept()
    {
        return isDept;
    }
    /**
     * @param isUnique the isUnique to set
     */
    public void setIsDept(Integer isDept)
    {
        this.isDept = isDept;
    }
    /**
     * @return the filterRule_field
     */
    public String getFilterRule_field()
    {
        return filterRule_field;
    }
    /**
     * @param filterRule_field the filterRule_field to set
     */
    public void setFilterRule_field(String filterRule_field)
    {
        this.filterRule_field = filterRule_field;
    }
	public String getSearchDefaultValue() {
		return searchDefaultValue;
	}
	public void setSearchDefaultValue(String searchDefaultValue) {
		this.searchDefaultValue = searchDefaultValue;
	}
	public String[] getInputTypeOrs() {
		return inputTypeOrs;
	}
	public void setInputTypeOrs(String[] inputTypeOrs) {
		this.inputTypeOrs = inputTypeOrs;
	}
	public String getFormate() {
		return formate;
	}
	public void setFormate(String formate) {
		this.formate = formate;
	}

	public String[] getFkShowFields() {
		return fkShowFields;
	}

	public void setFkShowFields(String[] fkShowFields) {
		this.fkShowFields = fkShowFields;
	}

	public String getTableViewCode() {
		return tableViewCode;
	}

	public void setTableViewCode(String tableViewCode) {
		this.tableViewCode = tableViewCode;
	}
 
    @Override
    public String toString() {
    	String s = this.title +"-->"+ this.name +" "+ this.type;
    	if(this.length!=null&&this.length.intValue()!=0){
    		s += "(" + this.length;
    		if(this.decimalCount!=null&&this.decimalCount.intValue()!=0){
    		    s += ", " + this.decimalCount;
    		}
    		s += ")";
    	}
    	return s;
    }

	public String[] getNames() {
		return names;
	}

	public void setNames(String[] names) {
		this.names = names;
	}

	/**
	 * @return the formColPos
	 */
	public String getFormColPos() {
		return formColPos;
	}

	/**
	 * @param formColPos the formColPos to set
	 */
	public void setFormColPos(String formColPos) {
		this.formColPos = formColPos;
	}

	/**
	 * @return the formRowPos
	 */
	public String getFormRowPos() {
		return formRowPos;
	}

	/**
	 * @param formRowPos the formRowPos to set
	 */
	public void setFormRowPos(String formRowPos) {
		this.formRowPos = formRowPos;
	}

	/**
	 * @return the formShow
	 */
	public String getFormShow() {
		return formShow;
	}

	/**
	 * @param formShow the formShow to set
	 */
	public void setFormShow(String formShow) {
		this.formShow = formShow;
	}

	public String getDtFilter() {
		return dtFilter;
	}

	public void setDtFilter(String dtFilter) {
		this.dtFilter = dtFilter;
	}
}
