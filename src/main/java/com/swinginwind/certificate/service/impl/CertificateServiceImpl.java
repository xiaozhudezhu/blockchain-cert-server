package com.swinginwind.certificate.service.impl;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.swinginwind.blockchain.util.Web3jUtil;
import com.swinginwind.certificate.dao.CertificateMapper;
import com.swinginwind.certificate.model.Certificate;
import com.swinginwind.certificate.pager.CertificatePager;
import com.swinginwind.certificate.service.CertificateService;
import com.swinginwind.core.utils.ApplicationPropsUtil;
import com.swinginwind.core.utils.FileUtil;
import com.swinginwind.core.utils.Identities;
import com.swinginwind.core.utils.excel.ExcelConfig;
import com.swinginwind.core.utils.excel.ExcelField;
import com.swinginwind.core.utils.excel.ExcelImport;
import com.swinginwind.core.utils.excel.FieldTypeEnum;
import com.swinginwind.iknowu.dao.BaseFileMapper;
import com.swinginwind.iknowu.dao.DicTypeMapper;
import com.swinginwind.iknowu.model.BaseFile;
import com.swinginwind.iknowu.model.DicType;
import com.swinginwind.iknowu.model.SysUser;
import com.swinginwind.iknowu.service.BaseFileService;
import com.swinginwind.iknowu.service.SysUserService;

@Service
public class CertificateServiceImpl implements CertificateService {
	
	@Autowired
	private SysUserService userService;
	
	@Autowired
	private BaseFileMapper fileMapper;
	
	@Autowired
	private CertificateMapper certMapper;
	
	@Autowired
	private Web3jUtil web3jUtil;
	
	@Autowired
	private DicTypeMapper dictTypeMapper;
	
	@Autowired
	private BaseFileService baseFileService;

	@Override
	public int insert(Certificate cert) {
		cert.setId(Identities.uuid());
		cert.setCreateTime(new Date());
		cert.setUpdateTime(new Date());
		cert.setCreateUser(userService.getCurrentUser().getId());
		cert.setUpdateUser(cert.getCreateUser());
		if(cert.getFiles() != null && cert.getFiles().size() > 0) {
			for(int i = 0; i < cert.getFiles().size(); i ++) {
				BaseFile file = cert.getFiles().get(i);
				file.setSortCode(i + 1);
				file.setRecordType("CERT_FILE");
				file.setRecordId(cert.getId());
				fileMapper.updateByPrimaryKeySelective(file);
			}
		}
		int result = certMapper.insert(cert);
		if(result == 1) {
			SysUser user = userService.getCurrentUser();
			if(!StringUtils.isEmpty(user.getWalletAccount()) 
					&& !StringUtils.isEmpty(user.getWalletPwd())) {
				new Thread(new Runnable() {
				    @Override
				    public void run() {
						try {
							String hash = web3jUtil.createCertificate(cert, user.getWalletAccount(), user.getWalletPwd());
							Certificate certTemp = new Certificate();
							certTemp.setId(cert.getId());
							certTemp.setCreateTransaction(hash);
							certMapper.updateByPrimaryKeySelective(certTemp);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				    }
				}).start();
			}
		}
		return result;
	}

	@Override
	public int update(Certificate cert) {
		cert.setUpdateTime(new Date());
		cert.setUpdateUser(userService.getCurrentUser().getId());
		cert.setCreateUser(null);
		cert.setCreateTime(null);
		cert.setCreateTransaction(null);
		cert.setUpdateTransaction(null);
		fileMapper.deleteUnusedFilesWhenUpdate(cert.getId(), "CERT_FILE", cert.getFiles());
		if(cert.getFiles() != null && cert.getFiles().size() > 0) {
			for(int i = 0; i < cert.getFiles().size(); i ++) {
				BaseFile file = cert.getFiles().get(i);
				file.setSortCode(i + 1);
				file.setRecordType("CERT_FILE");
				file.setRecordId(cert.getId());
				fileMapper.updateByPrimaryKeySelective(file);
			}
		}
		int result = certMapper.updateByPrimaryKeySelective(cert);
		if(result == 1) {
			SysUser user = userService.getCurrentUser();
			if(!StringUtils.isEmpty(user.getWalletAccount()) 
					&& !StringUtils.isEmpty(user.getWalletPwd())) {
				new Thread(new Runnable() {
				    @Override
				    public void run() {
						try {
							String hash = web3jUtil.updateCertificate(cert, user.getWalletAccount(), user.getWalletPwd());
							Certificate certTemp = new Certificate();
							certTemp.setId(cert.getId());
							certTemp.setUpdateTransaction(hash);
							certMapper.updateByPrimaryKeySelective(certTemp);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				    }
				}).start();
			}
		}
		return result;
	}

	@Override
	public List<Certificate> select(CertificatePager pager) {
		List<Certificate> certList = certMapper.select(pager);
		if(certList != null && certList.size() > 0) {
			for(Certificate cert : certList) {
				cert.setFiles(fileMapper.selectByRecord(cert.getId(), "CERT_FILE"));
			}
		}
		return certList;
	}

	@Override
	public int delete(Certificate cert) {
		int result = certMapper.deleteByPrimaryKey(cert.getId());
		if(result == 1) {
			SysUser user = userService.getCurrentUser();
			if(!StringUtils.isEmpty(user.getWalletAccount()) 
					&& !StringUtils.isEmpty(user.getWalletPwd())) {
				new Thread(new Runnable() {
				    @Override
				    public void run() {
						try {
							String hash = web3jUtil.deleteCertificate(cert.getId(), user.getWalletAccount(), user.getWalletPwd());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				    }
				}).start();
			}
		}
		return result;
	}

	@Override
	public Certificate selectByPrimaryKey(String id) {
		return certMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Certificate> batchImport(HttpServletRequest request, MultipartFile file) throws Exception {
		String uploadDir = ApplicationPropsUtil.getPropsValue(ApplicationPropsUtil.ATTACHMENT_UPLOADDIR);
		if ("".equals(uploadDir)) {
			uploadDir = new File(request.getSession().getServletContext().getRealPath("/")).getParentFile()
					.getParentFile().getAbsolutePath();
		}
		String fileName = file.getOriginalFilename();
		String newFileName = FileUtil.getFilenameWithoutExtension(fileName) + "-" + Identities.uuid() + "." + FileUtil.getFileExtension(fileName);

		try {
			String filePath = uploadDir + "importCert/" + newFileName;
			FileUtil.saveFile(file, filePath);
			String unzipDirectory = filePath.substring(0, filePath.length() - 4);
			FileUtil.unZipFiles(filePath, unzipDirectory);
			File unzipFileDir = new File(unzipDirectory);
			File[] files = unzipFileDir.listFiles();
			Map<String, File> attachDirMap = new HashMap<String, File>();
			File excelFile = null;
			for(File unzipFile : files) {
				if(!unzipFile.isDirectory() && unzipFile.getName().endsWith("xls")) {
					excelFile = unzipFile;
				}
				else
					attachDirMap.put(unzipFile.getName(), unzipFile);
			}
			
			if(excelFile == null)
				throw new Exception("未找到需要导入的excel！");
			
			List<Certificate> certList = this.excelToCert(excelFile.getAbsolutePath());
			if(certList.size() == 0) {
				throw new Exception("excel中未找到需要导入的证书信息！");
			}
			for(Certificate cert : certList) {
				File attachFileDir = attachDirMap.get(cert.getId());
				if(attachFileDir != null) {
					File[] attachs = attachFileDir.listFiles();
					if(attachs != null && attachs.length > 0) {
						cert.setFiles(new ArrayList<BaseFile>());
						for(File attach : attachs) {
							BaseFile attachBaseFile = baseFileService.saveFile(attach, "img");
							cert.getFiles().add(attachBaseFile);
						}
					}
				}
				this.insert(cert);
			}
			unzipFileDir.deleteOnExit();
			return certList;
			
		} catch (IOException e) {
			throw new Exception("文件解析异常");
		}

		
	}
	
	private List<Certificate> excelToCert(String filepath) throws Exception {
		ExcelConfig config = new ExcelConfig("", null);
        config.setDataRowIndex(1);
        ExcelField field0 = new ExcelField("id", "", FieldTypeEnum.String);
        ExcelField field1 = new ExcelField("certTypeName", "", FieldTypeEnum.String);
        ExcelField field2 = new ExcelField("certNo", "", FieldTypeEnum.String);
        ExcelField field3 = new ExcelField("certName", "", FieldTypeEnum.String);
        ExcelField field4 = new ExcelField("ownerName", "", FieldTypeEnum.String);
        ExcelField field5 = new ExcelField("ownerPhone", "", FieldTypeEnum.String);
        ExcelField field6 = new ExcelField("ownerId", "", FieldTypeEnum.String);
        ExcelField field7 = new ExcelField("ownerEmail", "", FieldTypeEnum.String);
        config.addField(field0);
        config.addField(field1);
        config.addField(field2);
        config.addField(field3);
        config.addField(field4);
        config.addField(field5);
        config.addField(field6);
        config.addField(field7);

        List<Map<String, Object>> resultList = ExcelImport.importExcelCompatibility(filepath, config);
        List<DicType> types = dictTypeMapper.selectAll();
        Map<String, Integer> typeNameIdMap = new HashMap<String, Integer>(); 
        for(DicType type : types) {
        	typeNameIdMap.put(type.getName(), Integer.parseInt(type.getTid()));
        }
        List<Certificate> resultCertList = new ArrayList<Certificate>();
        for(Map<String, Object> result : resultList) {
        	if(result.get("id") != null && result.get("certNo") != null) {
        		result.put("certType", typeNameIdMap.get(result.get("certTypeName")));
        		Certificate cert = new Certificate();
        		BeanUtils.copyProperties(cert, result);
        		resultCertList.add(cert);
        	}
        }
        return resultCertList;
        
	}

}
