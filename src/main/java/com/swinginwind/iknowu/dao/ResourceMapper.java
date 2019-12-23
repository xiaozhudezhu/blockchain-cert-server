package com.swinginwind.iknowu.dao;

import java.util.List;

import com.swinginwind.iknowu.model.Resource;
import com.swinginwind.iknowu.pager.ResourcePager;

public interface ResourceMapper {
    int deleteByPrimaryKey(String rid);

    int insert(Resource record);

    int insertSelective(Resource record);

    Resource selectByPrimaryKey(String rid);

    int updateByPrimaryKeySelective(Resource record);

    int updateByPrimaryKey(Resource record);
    
    List<Resource> select(ResourcePager pager);
}