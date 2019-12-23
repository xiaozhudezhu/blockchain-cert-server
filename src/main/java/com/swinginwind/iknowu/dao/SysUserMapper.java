package com.swinginwind.iknowu.dao;

import org.apache.ibatis.annotations.Param;

import com.swinginwind.iknowu.model.SysUser;

public interface SysUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);
    
    SysUser checkLogin(SysUser user);
    
    /**
     * 根据用户名、邮箱、手机号等判断是否已使用过
     * @param user
     * @return
     */
    int checkExists(SysUser user);
    
    SysUser selectByWechatId(@Param("wechatId") String wechatId);
    
    SysUser selectByPhone(@Param("phone") String phone);
    
}