package com.swinginwind.iknowu.model;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

public class OrderDialog {
    /**
     * 
     * 表 : t_order_dialog
     * 对应字段 : ID
     */
    private Integer id;

    /**
     * 
     * 表 : t_order_dialog
     * 对应字段 : OID
     */
    private String oid;

    /**
     * 
     * 表 : t_order_dialog
     * 对应字段 : CREATE_USER
     */
    private Integer createUser;
    
    private String createUserName;

    /**
     * 
     * 表 : t_order_dialog
     * 对应字段 : CREATE_DATE
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")  
    private Date createDate;

    /**
     * 
     * 表 : t_order_dialog
     * 对应字段 : CONTENT
     */
    private String content;
    
    /**
     * 附件
     */
    private List<BaseFile> files;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid == null ? null : oid.trim();
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public List<BaseFile> getFiles() {
		return files;
	}

	public void setFiles(List<BaseFile> files) {
		this.files = files;
	}
}