package com.swinginwind.iknowu.service;

import java.util.List;

import com.swinginwind.iknowu.model.Resource;
import com.swinginwind.iknowu.pager.ResourcePager;

public interface ResourceService {
	
	/**
	 * 创建资源
	 * @param res
	 * @return
	 */
	public int insert(Resource res);
	
	/**
	 * 搜索
	 * @param pager
	 * @return
	 */
	public List<Resource> select(ResourcePager pager);

	/**
	 * 删除资源
	 * @param res
	 * @return
	 */
	int delete(Resource res);

	Resource selectByPrimaryKey(String rid);

}
