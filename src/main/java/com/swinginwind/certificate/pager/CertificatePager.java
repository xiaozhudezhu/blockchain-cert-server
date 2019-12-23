package com.swinginwind.certificate.pager;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.swinginwind.certificate.model.Certificate;
import com.swinginwind.core.pager.Page;

public class CertificatePager extends Page<Certificate> {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
     * 
     * 表 : t_certificate
     * 对应字段 : id
     */
    private Integer id;

    /**
     * 
     * 表 : t_certificate
     * 对应字段 : cert_type
     */
    private Integer certType;

    /**
     * 
     * 表 : t_certificate
     * 对应字段 : cert_name
     */
    private String certName;

    /**
     * 
     * 表 : t_certificate
     * 对应字段 : cert_no
     */
    private String certNo;

    /**
     * 
     * 表 : t_certificate
     * 对应字段 : owner_name
     */
    private String ownerName;

    /**
     * 
     * 表 : t_certificate
     * 对应字段 : owner_phone
     */
    private String ownerPhone;

    /**
     * 
     * 表 : t_certificate
     * 对应字段 : owner_id
     */
    private String ownerId;

    /**
     * 
     * 表 : t_certificate
     * 对应字段 : owner_email
     */
    private String ownerEmail;

    /**
     * 
     * 表 : t_certificate
     * 对应字段 : create_user
     */
    private Integer createUser;

    /**
     * 
     * 表 : t_certificate
     * 对应字段 : create_time
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 
     * 表 : t_certificate
     * 对应字段 : update_user
     */
    private Integer updateUser;

    /**
     * 
     * 表 : t_certificate
     * 对应字段 : update_time
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    
    /**
	 * 创建开始时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startTime;
	
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

	/**
	 * 创建结束时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCertType() {
        return certType;
    }

    public void setCertType(Integer certType) {
        this.certType = certType;
    }

    public String getCertName() {
        return certName;
    }

    public void setCertName(String certName) {
        this.certName = certName == null ? null : certName.trim();
    }

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo == null ? null : certNo.trim();
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName == null ? null : ownerName.trim();
    }

    public String getOwnerPhone() {
        return ownerPhone;
    }

    public void setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone == null ? null : ownerPhone.trim();
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId == null ? null : ownerId.trim();
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail == null ? null : ownerEmail.trim();
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
	
	

}
