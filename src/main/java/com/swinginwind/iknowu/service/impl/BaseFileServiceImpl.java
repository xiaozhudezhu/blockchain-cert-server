package com.swinginwind.iknowu.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.swinginwind.core.utils.ApplicationPropsUtil;
import com.swinginwind.core.utils.FileUtil;
import com.swinginwind.core.utils.Identities;
import com.swinginwind.iknowu.dao.BaseFileMapper;
import com.swinginwind.iknowu.model.BaseFile;
import com.swinginwind.iknowu.model.SysUser;
import com.swinginwind.iknowu.service.BaseFileService;
import com.swinginwind.iknowu.service.SysUserService;

@Service
public class BaseFileServiceImpl implements BaseFileService {

	@Autowired
	private BaseFileMapper baseFileMapper;
	
	@Autowired
	private SysUserService userService;

	@Override
	public int insertBatch(List<BaseFile> fileList) {
		for (BaseFile file : fileList) {
			baseFileMapper.insert(file);
		}
		return fileList.size();
	}

	@Override
	public int insert(BaseFile file) {
		return baseFileMapper.insert(file);
	}

	@Override
	public BaseFile selectById(int id) {
		return baseFileMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<BaseFile> selectByRecord(String recordId, String recordType) {
		return baseFileMapper.selectByRecord(recordId, recordType);
	}
	
	@Override
	public String getFileUploadDir() {
		String uploadDir = ApplicationPropsUtil.getPropsValue(ApplicationPropsUtil.ATTACHMENT_UPLOADDIR);
		return uploadDir;
	}

	@Override
	public BaseFile saveFile(MultipartFile file, String fileType) {
		String uploadDir = this.getFileUploadDir();
		String newFileName = FileUtil.getFilenameWithoutExtension(file.getOriginalFilename()) + "-" + Identities.uuid()
				+ "." + FileUtil.getFileExtension(file.getOriginalFilename());
		File file1;
		try {
			file1 = FileUtil.saveFile(file, uploadDir + newFileName);
			return this.saveFile(file1, fileType);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public BaseFile saveFile(File file, String fileType) {
		String fileName = file.getName();
		BaseFile baseFile = new BaseFile();
		baseFile.setFileName(fileName);
		baseFile.setFileSize(new Double(file.length()));
		baseFile.setFileSuffix(FileUtil.getFileExtension(fileName));
		baseFile.setFileMd5(FileUtil.getFileMD5(file));
		String uploadDir = this.getFileUploadDir();
		baseFile.setFilePath(file.getAbsolutePath().substring(uploadDir.length()).replaceAll("\\\\", "/"));
		baseFile.setFileType(fileType);
		baseFile.setUpdateTime(new Date());
		SysUser user = userService.getCurrentUser();
		if (user != null)
			baseFile.setUpdateUser(user.getId());
		
		this.insert(baseFile);
		return baseFile;

	}

}
