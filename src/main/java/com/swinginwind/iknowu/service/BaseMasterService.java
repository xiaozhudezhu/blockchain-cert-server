package com.swinginwind.iknowu.service;

import java.util.List;

import com.swinginwind.iknowu.model.BaseMaster;
import com.swinginwind.iknowu.pager.BaseMasterPager;

public interface BaseMasterService {
	
	BaseMaster selectByPrimaryKey(String tid);
	
	List<BaseMaster> select(BaseMasterPager pager);

	BaseMaster register(BaseMaster master);

	BaseMaster getRegInfo();

	BaseMaster getRegInfo(int userId);

	void audit(BaseMaster master);

	/**
	 * 查询所有导师，包括非注册导师
	 * @param pager
	 * @return
	 */
	List<BaseMaster> selectAll(BaseMasterPager pager);

}
