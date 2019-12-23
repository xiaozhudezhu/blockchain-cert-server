package com.swinginwind.iknowu.model;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

public class BaseOrder {
    /**
     * 
     * 表 : t_base_order
     * 对应字段 : OID
     */
    private String oid;

    /**
     * 领域编号
     * 表 : t_base_order
     * 对应字段 : FID
     */
    private String fid;

    /**
     * 
     * 表 : t_base_order
     * 对应字段 : TID
     */
    private String tid;

    /**
     * 问题名称
     * 表 : t_base_order
     * 对应字段 : NAME
     */
    private String name;

    /**
     * 问题内容
     * 表 : t_base_order
     * 对应字段 : ISSUE_CONTENT
     */
    private String issueContent;

    /**
     * 问题类别
     * 表 : t_base_order
     * 对应字段 : TYPE
     */
    private String type;

    /**
     * 创建用户ID
     * 表 : t_base_order
     * 对应字段 : CREATE_USER
     */
    private Integer createUser;
    
    private String createUserName;
    
    /**
     * 答题用户ID
     * 表 : t_base_order
     * 对应字段 : ANSWER_USER
     */
    private Integer answerUser;
    
    private String answerUserName;

    /**
     * 问题提出人
     * 表 : t_base_order
     * 对应字段 : PARENT
     */
    private String parent;

    /**
     * 问题提出时间
     * 表 : t_base_order
     * 对应字段 : DATE
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")  
    private Date date;

    /**
     * 问题重要程度
     * 表 : t_base_order
     * 对应字段 : IMPLEVEL
     */
    private String implevel;

    /**
     * 问题紧急程度
     * 表 : t_base_order
     * 对应字段 : URGENT
     */
    private String urgent;

    /**
     * 问题打赏价格
     * 表 : t_base_order
     * 对应字段 : PRICE
     */
    private String price;

    /**
     * 问题分配导师
     * 表 : t_base_order
     * 对应字段 : MASTER
     */
    private String master;
    
    /**
     * 指定的资源
     * 表 : t_base_order
     * 对应字段 : RESOURCE
     */
    private String resource;
    
    /**
     * 截止日期
     * 表 : t_base_order
     * 对应字段 : DUEDATE
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")  
    private Date duedate;

	/**
     * 问题处理时间
     * 表 : t_base_order
     * 对应字段 : DELDATE
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")  
    private Date deldate;

    /**
     * 问题处理说明
     * 表 : t_base_order
     * 对应字段 : DELPROCESS
     */
    private String delprocess;

    /**
     * 问题状态，0：可抢单（未指派）；1：已指派未处理；2：处理中；3：已评价
     * 表 : t_base_order
     * 对应字段 : STATE
     */
    private String state;

    /**
     * 客户评价内容
     * 表 : t_base_order
     * 对应字段 : CUS_CONTENT
     */
    private String cusContent;

    /**
     * 客户评价结果
     * 表 : t_base_order
     * 对应字段 : CUS_RESULT
     */
    private String cusResult;

    /**
     * 客服回访内容
     * 表 : t_base_order
     * 对应字段 : CUSS_CONTENT
     */
    private String cussContent;

    /**
     * 客服回访结果
     * 表 : t_base_order
     * 对应字段 : CUSS_RESULT
     */
    private String cussResult;
    
    /**
     * 提问附件
     */
    private List<BaseFile> filesQuestion;
    
    /**
     * 回答问题附件
     */
    private List<BaseFile> filesAnswer;
    
    /**
     * 对话内容
     */
    private List<OrderDialog> dialogs;
    
    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid == null ? null : oid.trim();
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid == null ? null : fid.trim();
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid == null ? null : tid.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getIssueContent() {
        return issueContent;
    }

    public void setIssueContent(String issueContent) {
        this.issueContent = issueContent == null ? null : issueContent.trim();
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

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent == null ? null : parent.trim();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getImplevel() {
        return implevel;
    }

    public void setImplevel(String implevel) {
        this.implevel = implevel == null ? null : implevel.trim();
    }

    public String getUrgent() {
        return urgent;
    }

    public void setUrgent(String urgent) {
        this.urgent = urgent == null ? null : urgent.trim();
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price == null ? null : price.trim();
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master == null ? null : master.trim();
    }
    
    public Date getDuedate() {
		return duedate;
	}

	public void setDuedate(Date duedate) {
		this.duedate = duedate;
	}

    public Date getDeldate() {
        return deldate;
    }

    public void setDeldate(Date deldate) {
        this.deldate = deldate;
    }

    public String getDelprocess() {
        return delprocess;
    }

    public void setDelprocess(String delprocess) {
        this.delprocess = delprocess == null ? null : delprocess.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getCusContent() {
        return cusContent;
    }

    public void setCusContent(String cusContent) {
        this.cusContent = cusContent == null ? null : cusContent.trim();
    }

    public String getCusResult() {
        return cusResult;
    }

    public void setCusResult(String cusResult) {
        this.cusResult = cusResult == null ? null : cusResult.trim();
    }

    public String getCussContent() {
        return cussContent;
    }

    public void setCussContent(String cussContent) {
        this.cussContent = cussContent == null ? null : cussContent.trim();
    }

    public String getCussResult() {
        return cussResult;
    }

    public void setCussResult(String cussResult) {
        this.cussResult = cussResult == null ? null : cussResult.trim();
    }

	public List<BaseFile> getFilesQuestion() {
		return filesQuestion;
	}

	public void setFilesQuestion(List<BaseFile> filesQuestion) {
		this.filesQuestion = filesQuestion;
	}

	public List<BaseFile> getFilesAnswer() {
		return filesAnswer;
	}

	public void setFilesAnswer(List<BaseFile> filesAnswer) {
		this.filesAnswer = filesAnswer;
	}

	public Integer getAnswerUser() {
		return answerUser;
	}

	public void setAnswerUser(Integer answerUser) {
		this.answerUser = answerUser;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getAnswerUserName() {
		return answerUserName;
	}

	public void setAnswerUserName(String answerUserName) {
		this.answerUserName = answerUserName;
	}

	public List<OrderDialog> getDialogs() {
		return dialogs;
	}

	public void setDialogs(List<OrderDialog> dialogs) {
		this.dialogs = dialogs;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}
}