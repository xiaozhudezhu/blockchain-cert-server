package com.swinginwind.iknowu.dao;

import java.util.List;

import com.swinginwind.iknowu.model.OrderDialog;

public interface OrderDialogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderDialog record);

    int insertSelective(OrderDialog record);

    OrderDialog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderDialog record);

    int updateByPrimaryKey(OrderDialog record);
    
    List<OrderDialog> selectByOrderId(String oid);
}