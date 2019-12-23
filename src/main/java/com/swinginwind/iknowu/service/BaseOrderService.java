package com.swinginwind.iknowu.service;

import java.util.List;

import com.swinginwind.iknowu.model.BaseOrder;
import com.swinginwind.iknowu.model.OrderDialog;
import com.swinginwind.iknowu.pager.BaseOrderPager;

public interface BaseOrderService {
	
	/**
	 * 创建订单
	 * @param order
	 * @return
	 */
	public int insert(BaseOrder order);
	
	/**
	 * 搜索
	 * @param pager
	 * @return
	 */
	public List<BaseOrder> select(BaseOrderPager pager);
	
	/**
	 * 回答
	 * @param order
	 * @return
	 */
	@Deprecated
	public int answer(BaseOrder order);

	/**
	 * 客户评价
	 * @param order
	 * @return
	 */
	public int evaluate(BaseOrder order);

	/**
	 * 对话
	 * @param dialog
	 * @return
	 */
	int dialog(OrderDialog dialog);

	/**
	 * 删除
	 * @param order
	 * @return
	 */
	int delete(BaseOrder order);
	
	BaseOrder selectByPrimaryKey(String oid);

	/**
	 * 删除对话
	 * @param dialog
	 * @return
	 */
	int delDialog(OrderDialog dialog);

	/**
	 * 按ID查询对话
	 * @param id
	 * @return
	 */
	OrderDialog selectDialogById(int id);

	

}
