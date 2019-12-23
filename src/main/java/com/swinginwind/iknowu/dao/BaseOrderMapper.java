package com.swinginwind.iknowu.dao;

import java.util.List;

import com.swinginwind.iknowu.model.BaseOrder;
import com.swinginwind.iknowu.pager.BaseOrderPager;

public interface BaseOrderMapper {
    int deleteByPrimaryKey(String oid);

    int insert(BaseOrder record);

    int insertSelective(BaseOrder record);

    BaseOrder selectByPrimaryKey(String oid);

    int updateByPrimaryKeySelective(BaseOrder record);

    int updateByPrimaryKey(BaseOrder record);
    
    List<BaseOrder> select(BaseOrderPager pager);
}