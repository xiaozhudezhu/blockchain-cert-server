package com.swinginwind.certificate.service.impl;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swinginwind.certificate.dao.CertificateMapper;
import com.swinginwind.certificate.model.Certificate;
import com.swinginwind.certificate.pager.CertificatePager;
import com.swinginwind.certificate.service.CertificateService;
import com.swinginwind.core.utils.Identities;
import com.swinginwind.iknowu.dao.BaseFileMapper;
import com.swinginwind.iknowu.model.BaseFile;
import com.swinginwind.iknowu.service.SysUserService;

@Service
public class CertificateServiceImpl implements CertificateService {
	
	@Autowired
	private SysUserService userService;
	
	@Autowired
	private BaseFileMapper fileMapper;
	
	@Autowired
	private CertificateMapper certMapper;

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
		return certMapper.insert(cert);
	}

	@Override
	public int update(Certificate cert) {
		cert.setUpdateTime(new Date());
		cert.setUpdateUser(userService.getCurrentUser().getId());
		cert.setCreateUser(null);
		cert.setCreateTime(null);
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
		return certMapper.updateByPrimaryKeySelective(cert);
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
		return certMapper.deleteByPrimaryKey(cert.getId());
	}

	@Override
	public Certificate selectByPrimaryKey(String id) {
		return certMapper.selectByPrimaryKey(id);
	}

}
