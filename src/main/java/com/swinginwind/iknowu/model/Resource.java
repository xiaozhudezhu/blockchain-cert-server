package com.swinginwind.iknowu.model;

import java.util.Date;

public class Resource {
    /**
     * 
     * 表 : t_resource
     * 对应字段 : RID
     */
    private String rid;

    /**
     * 领域编号
     * 表 : t_resource
     * 对应字段 : FID
     */
    private String fid;

    /**
     * 问题名称
     * 表 : t_resource
     * 对应字段 : NAME
     */
    private String name;

    /**
     * 资源内容
     * 表 : t_resource
     * 对应字段 : RES_CONTENT
     */
    private String resContent;

    /**
     * 资源类别
     * 表 : t_resource
     * 对应字段 : TYPE
     */
    private String type;
    
    private String typeName;

    /**
     * 创建用户ID
     * 表 : t_resource
     * 对应字段 : CREATE_USER
     */
    private Integer createUser;
    
    private String createUserName;
    
    private String masterLevel;
    
    /**
     * 问题提出时间
     * 表 : t_resource
     * 对应字段 : DATE
     */
    private Date date;

    /**
     * 问题打赏价格
     * 表 : t_resource
     * 对应字段 : PRICE
     */
    private String price;
    
    /**
     * 
     * 表 : t_resource
     * 对应字段 : CITY
     */
    private String city;

    /**
     * 问题状态
     * 表 : t_resource
     * 对应字段 : STATE
     */
    private String state;
    
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

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid == null ? null : rid.trim();
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid == null ? null : fid.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getResContent() {
        return resContent;
    }

    public void setResContent(String resContent) {
        this.resContent = resContent == null ? null : resContent.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price == null ? null : price.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
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

	public String getMasterLevel() {
		return masterLevel;
	}

	public void setMasterLevel(String masterLevel) {
		this.masterLevel = masterLevel;
	}
}