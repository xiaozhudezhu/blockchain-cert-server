package com.swinginwind.iknowu.pager;

import java.util.List;

import com.swinginwind.core.pager.Page;
import com.swinginwind.iknowu.model.BaseMaster;

public class BaseMasterPager extends Page<BaseMaster> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2594610780445156482L;


	private String mastername;
	
	/**
	 * 认证状态，1：基本身份认证，2：资质认证，3：认证完成，4：认证不通过
	 */
	private List<String> states;
	
	private String newTecher;
	
	private String popTecher;
	
	/**
     * 最低热门资源限制
     */
    private Integer popCountLimit;
    
    /**
     * 最低新进资源限制
     */
    private Integer newDayLimit;
	
	private String city;
	
	private List<String> types;
	
	
	public String getMastername() {
		return mastername;
	}

	public void setMastername(String mastername) {
		this.mastername = mastername;
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

	public List<String> getStates() {
		return states;
	}

	public void setStates(List<String> states) {
		this.states = states;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public List<String> getTypes() {
		return types;
	}

	public void setTypes(List<String> types) {
		this.types = types;
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
