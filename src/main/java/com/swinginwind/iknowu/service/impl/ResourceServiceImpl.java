package com.swinginwind.iknowu.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swinginwind.core.utils.ApplicationPropsUtil;
import com.swinginwind.core.utils.Identities;
import com.swinginwind.iknowu.dao.ResourceMapper;
import com.swinginwind.iknowu.model.Resource;
import com.swinginwind.iknowu.pager.ResourcePager;
import com.swinginwind.iknowu.service.ResourceService;
import com.swinginwind.iknowu.service.SysUserService;

@Service
public class ResourceServiceImpl implements ResourceService {

	@Autowired
	private ResourceMapper resMapper;

	@Autowired
	private SysUserService userService;

	@Override
	public int insert(Resource res) {
		res.setCreateUser(userService.getCurrentUser().getId());
		res.setRid(Identities.uuid());
		res.setDate(new Date());
		res.setState("1");
		return resMapper.insert(res);
	}

	@Override
	public int delete(Resource res) {
		return resMapper.deleteByPrimaryKey(res.getRid());
	}

	@Override
	public List<Resource> select(ResourcePager pager) {
		pager.setNewDayLimit(Integer.parseInt(ApplicationPropsUtil.getPropsValue("config.newDayLimit")));
		pager.setPopCountLimit(Integer.parseInt(ApplicationPropsUtil.getPropsValue("config.popCountLimit")));

		List<Resource> resList = resMapper.select(pager);
		return resList;
	}

	@Override
	public Resource selectByPrimaryKey(String rid) {
		return resMapper.selectByPrimaryKey(rid);
	}

}
