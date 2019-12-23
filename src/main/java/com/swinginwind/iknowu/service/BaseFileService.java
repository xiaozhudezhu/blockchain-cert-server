package com.swinginwind.iknowu.service;

import java.io.File;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.swinginwind.iknowu.model.BaseFile;

public interface BaseFileService {
	
	public int insertBatch(List<BaseFile> fileList);
	
	public BaseFile selectById(int id);

	int insert(BaseFile file);

	List<BaseFile> selectByRecord(String recordId, String recordType);

	String getFileUploadDir();

	BaseFile saveFile(MultipartFile file, String fileType);

	BaseFile saveFile(File file, String fileType);

}
