package com.swinginwind.iknowu.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swinginwind.core.utils.Identities;
import com.swinginwind.iknowu.dao.BaseFileMapper;
import com.swinginwind.iknowu.dao.BaseOrderMapper;
import com.swinginwind.iknowu.dao.OrderDialogMapper;
import com.swinginwind.iknowu.model.BaseFile;
import com.swinginwind.iknowu.model.BaseOrder;
import com.swinginwind.iknowu.model.OrderDialog;
import com.swinginwind.iknowu.pager.BaseOrderPager;
import com.swinginwind.iknowu.service.BaseOrderService;
import com.swinginwind.iknowu.service.SysUserService;

@Service
public class BaseOrderServiceImpl implements BaseOrderService {
	
	@Autowired
	private BaseOrderMapper orderMapper;
	
	@Autowired
	private BaseFileMapper fileMapper;
	
	@Autowired
	private OrderDialogMapper dialogMapper;
	
	@Autowired
	private SysUserService userService;

	@Override
	public int insert(BaseOrder order) {
		order.setDate(new Date());
		order.setOid(Identities.uuid());
		order.setCreateUser(userService.getCurrentUser().getId());
		if(StringUtils.isEmpty(order.getMaster()))
			order.setState("0");
		else
			order.setState("1");
		if(order.getFilesQuestion() != null && order.getFilesQuestion().size() > 0) {
			for(int i = 0; i < order.getFilesQuestion().size(); i ++) {
				BaseFile file = order.getFilesQuestion().get(i);
				file.setSortCode(i + 1);
				file.setRecordType("ORDER_Q");
				file.setRecordId(order.getOid());
				fileMapper.updateByPrimaryKeySelective(file);
			}
		}
		return orderMapper.insert(order);
	}
	
	@Override
	public int delete(BaseOrder order) {
		return orderMapper.deleteByPrimaryKey(order.getOid());
	}

	@Override
	public List<BaseOrder> select(BaseOrderPager pager) {
		List<BaseOrder> orderList = orderMapper.select(pager);
		if(orderList != null && orderList.size() > 0) {
			for(BaseOrder order : orderList) {
				order.setFilesQuestion(fileMapper.selectByRecord(order.getOid(), "ORDER_Q"));
				//改成对话模式，取消回答附件
				//order.setFilesAnswer(fileMapper.selectByRecord(order.getOid(), "ORDER_A"));
			}
			//如果是查询某一个order的详细内容，则可以查看对话内容
			if(!StringUtils.isEmpty(pager.getOid())) {
				List<OrderDialog> dialogs = dialogMapper.selectByOrderId(pager.getOid());
				if(dialogs != null && dialogs.size() > 0) {
					for(OrderDialog dialog : dialogs) {
						dialog.setFiles(fileMapper.selectByRecord(dialog.getId().toString(), "ORDER_D"));
					}
					orderList.get(0).setDialogs(dialogs);
				}
			}
		}
		return orderList;
	}
	
	@Deprecated
	@Override
	public int answer(BaseOrder order) {
		BaseOrder orderNew = new BaseOrder();
		orderNew.setOid(order.getOid());
		orderNew.setAnswerUser(userService.getCurrentUser().getId());
		orderNew.setDeldate(new Date());
		orderNew.setDelprocess(order.getDelprocess());
		orderNew.setState("2");
		fileMapper.deleteUnusedFilesWhenUpdate(order.getOid(), "ORDER_A", order.getFilesAnswer());
		if(order.getFilesAnswer() != null && order.getFilesAnswer().size() > 0) {
			for(int i = 0; i < order.getFilesAnswer().size(); i ++) {
				BaseFile file = order.getFilesAnswer().get(i);
				file.setSortCode(i + 1);
				file.setRecordType("ORDER_A");
				file.setRecordId(order.getOid());
				fileMapper.updateByPrimaryKeySelective(file);
			}
		}
		return orderMapper.updateByPrimaryKeySelective(orderNew);
	}
	
	@Override
	public int dialog(OrderDialog dialog) {
		BaseOrder order = orderMapper.selectByPrimaryKey(dialog.getOid());
		if(order.getState().equals("0") || order.getState().equals("1")) {
			BaseOrder orderNew = new BaseOrder();
			orderNew.setOid(order.getOid());
			orderNew.setAnswerUser(userService.getCurrentUser().getId());
			orderNew.setDeldate(new Date());
			orderNew.setDelprocess(order.getDelprocess());
			orderNew.setState("2");
			orderMapper.updateByPrimaryKeySelective(orderNew);
		}
		dialog.setCreateDate(new Date());
		dialog.setCreateUser(userService.getCurrentUser().getId());
		dialogMapper.insert(dialog);
		if(dialog.getFiles() != null && dialog.getFiles().size() > 0) {
			for(int i = 0; i < dialog.getFiles().size(); i ++) {
				BaseFile file = dialog.getFiles().get(i);
				file.setSortCode(i + 1);
				file.setRecordType("ORDER_D");
				file.setRecordId(dialog.getId().toString());
				fileMapper.updateByPrimaryKeySelective(file);
			}
		}
		return 1;
	}
	
	@Override
	public int delDialog(OrderDialog dialog) {
		return dialogMapper.deleteByPrimaryKey(dialog.getId());
	}
	
	@Override
	public OrderDialog selectDialogById(int id) {
		return dialogMapper.selectByPrimaryKey(id);
	}
	
	
	@Override
	public int evaluate(BaseOrder order) {
		BaseOrder orderNew = new BaseOrder();
		orderNew.setOid(order.getOid());
		orderNew.setCusContent(order.getCusContent());
		orderNew.setCusResult(order.getCusResult());
		orderNew.setState("3");
		return orderMapper.updateByPrimaryKeySelective(orderNew);
	}

	@Override
	public BaseOrder selectByPrimaryKey(String oid) {
		return orderMapper.selectByPrimaryKey(oid);
	}
	
	

}
