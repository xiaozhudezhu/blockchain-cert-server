package com.swinginwind.iknowu.pager;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.swinginwind.core.pager.Page;
import com.swinginwind.iknowu.model.Resource;

public class ResourcePager extends Page<Resource> {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 提问开始时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startTime;
	
	/**
	 * 提问结束时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endTime;
	
	/**
	 * 提问用户ID
	 */
	private Integer createUser;
	
	/**
	 * 资源状态，1：有效；2：暂停；3：失效
	 */
	private List<String> states;
	
	/**
	 * 资源类别
	 */
	private List<String> types;
	
	/**
	 * 城市
	 */
	private String city;
	
	/**
	 * 内容（模糊查询）
	 */
	private String resContent;
	
	/**
	 * 导师名称（模糊查询）
	 */
	private String masterName;
	
	/**
     * 创建时间一周(可调)以内认为是新进资源
     * 表 : t_resource
     * 对应字段 : NEW_TECHER
     */
    private String newTecher;

    /**
     * 超过10个需求（可调）认为是热门资源
     * 表 : t_resource
     * 对应字段 : POP_TECHER
     */
    private String popTecher;
    
    /**
     * 最低热门资源限制
     */
    private Integer popCountLimit;
    
    /**
     * 最低新进资源限制
     */
    private Integer newDayLimit;

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Integer createUser) {
		this.createUser = createUser;
	}

	public List<String> getStates() {
		return states;
	}

	public void setStates(List<String> states) {
		this.states = states;
	}

	public List<String> getTypes() {
		return types;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getResContent() {
		return resContent;
	}

	public void setResContent(String resContent) {
		this.resContent = resContent;
	}

	public String getMasterName() {
		return masterName;
	}

	public void setMasterName(String masterName) {
		this.masterName = masterName;
	}

	public String getNewTecher() {
		return newTecher;
	}

	public void setNewTecher(String newTecher) {
		this.newTecher = newTecher;
	}

	public String getPopTecher() {
		return popTecher;
	}

	public void setPopTecher(String popTecher) {
		this.popTecher = popTecher;
	}

	public Integer getPopCountLimit() {
		return popCountLimit;
	}

	public void setPopCountLimit(Integer popCountLimit) {
		this.popCountLimit = popCountLimit;
	}

	public Integer getNewDayLimit() {
		return newDayLimit;
	}

	public void setNewDayLimit(Integer newDayLimit) {
		this.newDayLimit = newDayLimit;
	}
	
	

}
