package com.swinginwind.iknowu.model;

public class DicType {
    /**
     * 
     * 表 : t_dic_type
     * 对应字段 : TID
     */
    private String tid;

    /**
     * 领域名称
     * 表 : t_dic_type
     * 对应字段 : NAME
     */
    private String name;

    /**
     * 
     * 表 : t_dic_type
     * 对应字段 : SORT_NUM
     */
    private Integer sortNum;

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

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }
}