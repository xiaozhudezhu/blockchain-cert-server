package com.swinginwind.certificate.dao;

import java.util.List;

import com.swinginwind.certificate.model.Certificate;
import com.swinginwind.certificate.pager.CertificatePager;

public interface CertificateMapper {
    int deleteByPrimaryKey(String id);

    int insert(Certificate record);

    int insertSelective(Certificate record);

    Certificate selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Certificate record);

    int updateByPrimaryKey(Certificate record);
    
    List<Certificate> select(CertificatePager pager);
}