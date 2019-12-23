package com.swinginwind.iknowu.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class BaseMaster implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 
     * 表 : t_base_master
     * 对应字段 : TID
     */
    private String tid;

    /**
     * 导师姓名
     * 表 : t_base_master
     * 对应字段 : MASTERNAME
     */
    private String mastername;

    /**
     * 导师性别
     * 表 : t_base_master
     * 对应字段 : GENDER
     */
    private String gender;

    /**
     * 导师年龄
     * 表 : t_base_master
     * 对应字段 : AGE
     */
    private BigDecimal age;

    /**
     * 
     * 表 : t_base_master
     * 对应字段 : ID_NUMBER
     */
    private String idNumber;

    /**
     * 导师手机号码
     * 表 : t_base_master
     * 对应字段 : PHONECALL
     */
    private String phonecall;

    /**
     * 导师领域
     * 表 : t_base_master
     * 对应字段 : FIELDS
     */
    private String fields;

    /**
     * 导师最高级别
     * 表 : t_base_master
     * 对应字段 : LEVEL
     */
    private String level;

    /**
     * 导师来源
     * 表 : t_base_master
     * 对应字段 : SOURCE
     */
    private String source;

    /**
     * 导师微信号
     * 表 : t_base_master
     * 对应字段 : WEIXIN
     */
    private String weixin;

    /**
     * 导师支付宝账号
     * 表 : t_base_master
     * 对应字段 : ALIPAY
     */
    private String alipay;

    /**
     * 导师服务费支付方式
     * 表 : t_base_master
     * 对应字段 : PAYTYPE
     */
    private String paytype;

    /**
     * 导师服务费用合计
     * 表 : t_base_master
     * 对应字段 : TOTALFEE
     */
    private BigDecimal totalfee;

    /**
     * 导师所在城市
     * 表 : t_base_master
     * 对应字段 : CITY
     */
    private String city;

    /**
     * 导师所在单位
     * 表 : t_base_master
     * 对应字段 : FIRM
     */
    private String firm;

    /**
     * 导师职称
     * 表 : t_base_master
     * 对应字段 : RANK
     */
    private String rank;

    /**
     * 导师职位
     * 表 : t_base_master
     * 对应字段 : POSITION
     */
    private String position;

    /**
     * 
     * 表 : t_base_master
     * 对应字段 : REG_TIME
     */
    private Date regTime;

    /**
     * 注册时间一周(可调)以内认为是新进导师
     * 表 : t_base_master
     * 对应字段 : NEW_TECHER
     */
    private String newTecher;

    /**
     * 任何一天获得超过50个提问(可调)就认为是人气导师
     * 表 : t_base_master
     * 对应字段 : POP_TECHER
     */
    private String popTecher;

    /**
     * 关联用户表ID
     * 表 : t_base_master
     * 对应字段 : USER_ID
     */
    private Integer userId;
    
    /**
     * 本月接单数
     */
    private int monthCount = 50;

    /**
     * 认证状态，1：基本身份认证，2：资质认证，3：认证完成，4：认证不通过
     * 表 : t_base_master
     * 对应字段 : STATE
     */
    private String state;

    /**
     * 认证备注
     * 表 : t_base_master
     * 对应字段 : AUDIT_REMARK
     */
    private String auditRemark;

    /**
     * 审核结论
     * 表 : t_base_master
     * 对应字段 : AUDIT_RESULT
     */
    private String auditResult;
    
    /**
     * 类别
     */
    private List<DicType> typeList;
    
    /**
     * 注册附件
     */
    private List<BaseFile> regFiles;

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid == null ? null : tid.trim();
    }

    public String getMastername() {
        return mastername;
    }

    public void setMastername(String mastername) {
        this.mastername = mastername == null ? null : mastername.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public BigDecimal getAge() {
        return age;
    }

    public void setAge(BigDecimal age) {
        this.age = age;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber == null ? null : idNumber.trim();
    }

    public String getPhonecall() {
        return phonecall;
    }

    public void setPhonecall(String phonecall) {
        this.phonecall = phonecall == null ? null : phonecall.trim();
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields == null ? null : fields.trim();
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level == null ? null : level.trim();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin == null ? null : weixin.trim();
    }

    public String getAlipay() {
        return alipay;
    }

    public void setAlipay(String alipay) {
        this.alipay = alipay == null ? null : alipay.trim();
    }

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype == null ? null : paytype.trim();
    }

    public BigDecimal getTotalfee() {
        return totalfee;
    }

    public void setTotalfee(BigDecimal totalfee) {
        this.totalfee = totalfee;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getFirm() {
        return firm;
    }

    public void setFirm(String firm) {
        this.firm = firm == null ? null : firm.trim();
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank == null ? null : rank.trim();
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }

    public Date getRegTime() {
        return regTime;
    }

    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    public String getNewTecher() {
        return newTecher;
    }

    public void setNewTecher(String newTecher) {
        this.newTecher = newTecher == null ? null : newTecher.trim();
    }

    public String getPopTecher() {
        return popTecher;
    }

    public void setPopTecher(String popTecher) {
        this.popTecher = popTecher == null ? null : popTecher.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getAuditRemark() {
        return auditRemark;
    }

    public void setAuditRemark(String auditRemark) {
        this.auditRemark = auditRemark == null ? null : auditRemark.trim();
    }

    public String getAuditResult() {
        return auditResult;
    }

    public void setAuditResult(String auditResult) {
        this.auditResult = auditResult == null ? null : auditResult.trim();
    }

	public int getMonthCount() {
		return monthCount;
	}

	public void setMonthCount(int monthCount) {
		this.monthCount = monthCount;
	}

	public List<DicType> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<DicType> typeList) {
		this.typeList = typeList;
	}

	public List<BaseFile> getRegFiles() {
		return regFiles;
	}

	public void setRegFiles(List<BaseFile> regFiles) {
		this.regFiles = regFiles;
	}
}