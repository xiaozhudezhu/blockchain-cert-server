package com.swinginwind.iknowu.dao;

import java.util.List;

import com.swinginwind.iknowu.model.BaseMaster;
import com.swinginwind.iknowu.pager.BaseMasterPager;

public interface BaseMasterMapper {
    int deleteByPrimaryKey(String tid);

    int insert(BaseMaster record);

    int insertSelective(BaseMaster record);

    BaseMaster selectByPrimaryKey(String tid);

    int updateByPrimaryKeySelective(BaseMaster record);

    int updateByPrimaryKey(BaseMaster record);
    
    List<BaseMaster> select(BaseMasterPager pager);
    
    List<BaseMaster> selectAll(BaseMasterPager pager);
    
    BaseMaster selectByUserId(Integer userId);
    
    int updateMasterTypes(BaseMaster record);
}