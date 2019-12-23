package com.swinginwind.iknowu.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.swinginwind.iknowu.model.BaseFile;

public interface BaseFileMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(BaseFile record);

	int insertSelective(BaseFile record);

	BaseFile selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(BaseFile record);

	int updateByPrimaryKey(BaseFile record);

	List<BaseFile> selectByRecord(@Param("recordId") String recordId, @Param("recordType") String recordType);

	int deleteUnusedFilesWhenUpdate(@Param("recordId") String recordId, @Param("recordType") String recordType,
			@Param("fileList") List<BaseFile> fileList);

}