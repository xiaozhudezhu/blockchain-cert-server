package com.swinginwind.iknowu.dao;

import java.util.List;

import com.swinginwind.iknowu.model.DicType;

public interface DicTypeMapper {
    int deleteByPrimaryKey(String tid);

    int insert(DicType record);

    int insertSelective(DicType record);

    DicType selectByPrimaryKey(String tid);

    int updateByPrimaryKeySelective(DicType record);

    int updateByPrimaryKey(DicType record);
    
    List<DicType> selectAll();
    
    List<DicType> selectByMasterId(String masterId);
}