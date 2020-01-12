package com.swinginwind.certificate.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.swinginwind.certificate.model.Certificate;
import com.swinginwind.certificate.pager.CertificatePager;


public interface CertificateService {
	
	/**
	 * 创建证书
	 * @param order
	 * @return
	 */
	public int insert(Certificate order);
	
	/**
	 * 更新证书
	 * @param order
	 * @return
	 */
	public int update(Certificate order);
	
	/**
	 * 搜索
	 * @param pager
	 * @return
	 */
	public List<Certificate> select(CertificatePager pager);
	

	/**
	 * 删除
	 * @param order
	 * @return
	 */
	int delete(Certificate order);
	
	Certificate selectByPrimaryKey(String oid);
	
	List<Certificate> batchImport(HttpServletRequest request, MultipartFile file) throws Exception;
	

}
