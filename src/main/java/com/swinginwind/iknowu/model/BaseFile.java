package com.swinginwind.iknowu.model;

import java.util.Date;

public class BaseFile {
    /**
     * 
     * 表 : t_base_file
     * 对应字段 : id
     */
    private Integer id;

    /**
     * 
     * 表 : t_base_file
     * 对应字段 : file_name
     */
    private String fileName;

    /**
     * 
     * 表 : t_base_file
     * 对应字段 : file_path
     */
    private String filePath;

    /**
     * 
     * 表 : t_base_file
     * 对应字段 : file_size
     */
    private Double fileSize;

    /**
     * 
     * 表 : t_base_file
     * 对应字段 : file_suffix
     */
    private String fileSuffix;

    /**
     * 
     * 表 : t_base_file
     * 对应字段 : file_type
     */
    private String fileType;

    /**
     * 
     * 表 : t_base_file
     * 对应字段 : file_md5
     */
    private String fileMd5;

    /**
     * 
     * 表 : t_base_file
     * 对应字段 : sort_code
     */
    private Integer sortCode;

    /**
     * 
     * 表 : t_base_file
     * 对应字段 : record_id
     */
    private String recordId;

    /**
     * 
     * 表 : t_base_file
     * 对应字段 : record_type
     */
    private String recordType;

    /**
     * 
     * 表 : t_base_file
     * 对应字段 : update_time
     */
    private Date updateTime;

    /**
     * 
     * 表 : t_base_file
     * 对应字段 : update_user
     */
    private Integer updateUser;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }

    public Double getFileSize() {
        return fileSize;
    }

    public void setFileSize(Double fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileSuffix() {
        return fileSuffix;
    }

    public void setFileSuffix(String fileSuffix) {
        this.fileSuffix = fileSuffix == null ? null : fileSuffix.trim();
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType == null ? null : fileType.trim();
    }

    public String getFileMd5() {
        return fileMd5;
    }

    public void setFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5 == null ? null : fileMd5.trim();
    }

    public Integer getSortCode() {
        return sortCode;
    }

    public void setSortCode(Integer sortCode) {
        this.sortCode = sortCode;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType == null ? null : recordType.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }
}