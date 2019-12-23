package com.swinginwind.iknowu.service;

import com.swinginwind.iknowu.model.SysUser;

import me.chanjar.weixin.mp.bean.result.WxMpUser;

public interface SysUserService {
	
	public SysUser checkLogin(SysUser user);
	
	public void register(SysUser user, boolean createAccount) throws Exception;

	SysUser wechatLogin(WxMpUser wxUser) throws Exception;
	
	SysUser phoneLogin(SysUser user) throws Exception;

	SysUser getCurrentUser();

	int updateUser(SysUser user);

	public int updatePwd(SysUser user);

	boolean isAdmin();

	SysUser getUserById(int userId);

}
