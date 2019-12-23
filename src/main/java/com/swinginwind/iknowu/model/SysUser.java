package com.swinginwind.iknowu.model;

import java.io.Serializable;
import java.util.Date;

public class SysUser implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 
     * 表 : t_sys_user
     * 对应字段 : id
     */
    private Integer id;

    /**
     * 
     * 表 : t_sys_user
     * 对应字段 : user_name
     */
    private String userName;

    /**
     * 
     * 表 : t_sys_user
     * 对应字段 : nick_name
     */
    private String nickName;

    /**
     * 
     * 表 : t_sys_user
     * 对应字段 : pwd
     */
    private String pwd;

    /**
     * 
     * 表 : t_sys_user
     * 对应字段 : email
     */
    private String email;

    /**
     * 
     * 表 : t_sys_user
     * 对应字段 : phonecall
     */
    private String phonecall;

    /**
     * 
     * 表 : t_sys_user
     * 对应字段 : gender
     */
    private String gender;
    
    /**
     * 
     * 表 : t_sys_user
     * 对应字段 : wechat_id
     */
    private String wechatId;
    
    /**
     * 
     * 表 : t_sys_user
     * 对应字段 : head_img_url
     */
    private String headImgUrl;

    /**
     * 
     * 表 : t_sys_user
     * 对应字段 : city
     */
    private String city;

    /**
     * 
     * 表 : t_sys_user
     * 对应字段 : qr_code_url
     */
    private String qrCodeUrl;

    /**
     * 钱包密码
     * 表 : t_sys_user
     * 对应字段 : wallet_pwd
     */
    private String walletPwd;

    /**
     * 用户uid，关联区块链用户账号
     * 表 : t_sys_user
     * 对应字段 : wallet_account
     */
    private String walletAccount;

    /**
     * 用户状态，1：已注册，2：已激活; 0：已禁用
     * 表 : t_sys_user
     * 对应字段 : status
     */
    private String status;

    /**
     * 
     * 表 : t_sys_user
     * 对应字段 : create_time
     */
    private Date createTime;
    
    private boolean isAdmin;
    
    private BaseMaster masterInfo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd == null ? null : pwd.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getPhonecall() {
        return phonecall;
    }

    public void setPhonecall(String phonecall) {
        this.phonecall = phonecall == null ? null : phonecall.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public String getWalletPwd() {
        return walletPwd;
    }

    public void setWalletPwd(String walletPwd) {
        this.walletPwd = walletPwd == null ? null : walletPwd.trim();
    }

    public String getWalletAccount() {
        return walletAccount;
    }

    public void setWalletAccount(String walletAccount) {
        this.walletAccount = walletAccount == null ? null : walletAccount.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public BaseMaster getMasterInfo() {
		return masterInfo;
	}

	public void setMasterInfo(BaseMaster masterInfo) {
		this.masterInfo = masterInfo;
	}

	public String getWechatId() {
		return wechatId;
	}

	public void setWechatId(String wechatId) {
		this.wechatId = wechatId;
	}

	public String getHeadImgUrl() {
		return headImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getQrCodeUrl() {
		return qrCodeUrl;
	}

	public void setQrCodeUrl(String qrCodeUrl) {
		this.qrCodeUrl = qrCodeUrl;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
}