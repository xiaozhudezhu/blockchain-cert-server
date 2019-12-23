package com.swinginwind.iknowu.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;

public class Tablemaintenance {
	private String id;
	private String codename;
	private String tablename;
	private String abbreviation;
	private String classcode;
	private String labelsWidth;
	private String contentsWidth;
	private String order;
	private List<Map<String,Object>> tablemaintenances;
	private String viewCode;
	private String createUser;
	
	private List<String> menus;
	private Integer rowsPerPage;
	
	/**
	 * 动态表头内容
	 */
	private String dynamicTitle;
	
	/**
	 * 视图过滤条件
	 */
	private String filterRule;
	
	/**
	 * 是否启动workflow 1:启用
	 */
	private String workflowStatus;
	
	public Tablemaintenance() {
	}
	
	public Tablemaintenance(String id) {
		this.id = id;
	}
	
	public Tablemaintenance(String viewCode, String tablename) {
		this.viewCode = viewCode;
		this.tablename = tablename;
	}
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getViewCode() {
		return viewCode;
	}
	public void setViewCode(String viewCode) {
		this.viewCode = viewCode;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCodename() {
		return codename;
	}
	public void setCodename(String codename) {
		this.codename = codename;
	}
	public String getTablename() {
		return tablename;
	}
	public void setTablename(String tablename) {
		this.tablename = tablename;
	}
	public String getAbbreviation() {
		return abbreviation;
	}
	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}
	public String getClasscode() {
		return classcode;
	}
	public void setClasscode(String classcode) {
		this.classcode = classcode;
	}
	public List<Map<String,Object>> getTablemaintenances() {
		return tablemaintenances;
	}
	public void setTablemaintenances(List<Map<String,Object>> tablemaintenances) {
		this.tablemaintenances = tablemaintenances;
	}
    public String getFilterRule()
    {
        return filterRule;
    }
    public void setFilterRule(String filterRule)
    {
        this.filterRule = filterRule;
    }
	public String getDynamicTitle() {
		return dynamicTitle;
	}
	public void setDynamicTitle(String dynamicTitle) {
		this.dynamicTitle = dynamicTitle;
	}

	public String getWorkflowStatus() {
		return workflowStatus;
	}

	public void setWorkflowStatus(String workflowStatus) {
		this.workflowStatus = workflowStatus;
	}

	public List<String> getMenus() {
		return menus;
	}

	public void setMenus(List<String> menus) {
		this.menus = menus;
	}
	
	public void setRowsPerPage(Integer rowsPerPage) {
        this.rowsPerPage = rowsPerPage;
    }
    public Integer getRowsPerPage() {
        return rowsPerPage;
    }

	public String getLabelsWidth() {
		return labelsWidth;
	}

	public void setLabelsWidth(String labelsWidth) {
		this.labelsWidth = labelsWidth;
	}

	public String getContentsWidth() {
		return contentsWidth;
	}

	public void setContentsWidth(String contentsWidth) {
		this.contentsWidth = contentsWidth;
	}

}
