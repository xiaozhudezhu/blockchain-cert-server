package com.swinginwind.iknowu.service.impl;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.jpay.ext.kit.ZxingKit;
import com.swinginwind.blockchain.util.Web3jUtil;
import com.swinginwind.core.session.SessionService;
import com.swinginwind.core.utils.ApplicationPropsUtil;
import com.swinginwind.core.utils.EmojiFilter;
import com.swinginwind.core.utils.MD5Util;
import com.swinginwind.iknowu.dao.BaseMasterMapper;
import com.swinginwind.iknowu.dao.SysUserMapper;
import com.swinginwind.iknowu.model.BaseFile;
import com.swinginwind.iknowu.model.SysUser;
import com.swinginwind.iknowu.service.BaseFileService;
import com.swinginwind.iknowu.service.SysUserService;

import me.chanjar.weixin.mp.bean.result.WxMpUser;
import net.sf.json.JSONObject;

@Service
public class SysUserServiceImpl implements SysUserService {

	@Autowired
	private SysUserMapper userMapper;

	@Autowired
	private BaseMasterMapper masterMapper;
	
	@Autowired
	private BaseFileService fileService;
	
	@Autowired
	private SessionService sessionService;

	@Autowired
	private Web3jUtil web3jUtil;
	
	@Override
	public SysUser getCurrentUser() {
		return (SysUser) sessionService.getSession();
	}
	
	@Override
	public boolean isAdmin() {
		return ApplicationPropsUtil.getAdminIds().contains(this.getCurrentUser().getId());
	}

	@Override
	public SysUser checkLogin(SysUser user) {
		user.setPwd(MD5Util.encrypt(user.getPwd()));
		user = userMapper.checkLogin(user);
		if (user != null) {
			user.setMasterInfo(masterMapper.selectByUserId(user.getId()));
		}
		return user;
	}

	@Override
	public SysUser wechatLogin(WxMpUser wxUser) throws Exception {
		SysUser user = userMapper.selectByWechatId(wxUser.getUnionId());
		if (user != null)
			return user;
		else {
			user = new SysUser();
			if (!StringUtils.isEmpty(wxUser.getNickname()))
				user.setNickName(EmojiFilter.filterEmoji(wxUser.getNickname()));
			user.setGender(user.getGender());
			user.setWechatId(wxUser.getUnionId());
			user.setHeadImgUrl(wxUser.getHeadImgUrl());
			this.register(user, false);
		}
		return user;
	}
	
	@Override
	public SysUser phoneLogin(SysUser user1) throws Exception {
		SysUser user = userMapper.selectByPhone(user1.getPhonecall());
		if (user != null)
			return user;
		else {
			this.register(user1, false);
			user = user1;
		}
		return user;
	}

	@Override
	public void register(SysUser user, boolean createAccount) throws Exception {
		if(userMapper.checkExists(user) > 0)
			throw new Exception("账号已存在");
		user.setPwd(MD5Util.encrypt(user.getPwd()));
		user.setStatus("1");
		user.setCreateTime(new Date());
		if (createAccount) {
			user.setWalletPwd(user.getPwd());
			String account = web3jUtil.newAccount(user.getWalletPwd());
			user.setWalletAccount(account);
			// 初始化余额
			web3jUtil.sendUFOTransaction(web3jUtil.getAdminAccount(), account, web3jUtil.getAdminPwd(),
					web3jUtil.getUserInitBalance());
		}
		userMapper.insert(user);
		this.createUserQrCode(user);
		if(!StringUtils.isEmpty(user.getQrCodeUrl())) {
			SysUser userTemp = new SysUser();
			userTemp.setId(user.getId());
			userTemp.setQrCodeUrl(user.getQrCodeUrl());
			userMapper.updateByPrimaryKeySelective(userTemp);
		}
		
	}
	
	public void createUserQrCode(SysUser user) {
		Map<String, Object> userTemp = new HashMap<String, Object>();
		userTemp.put("id", user.getId());
		userTemp.put("nickName", user.getNickName());
		userTemp.put("gender", user.getGender());
		String fileDir = fileService.getFileUploadDir() + "/userQrCode";
		if(!new File(fileDir).exists())
			new File(fileDir).mkdirs();
		String fileName = String.valueOf(user.getId());
		String path = fileDir + "/" + fileName + ".png";
		Boolean encode = ZxingKit.encode(JSONObject.fromObject(userTemp).toString(), BarcodeFormat.QR_CODE, 3,
				ErrorCorrectionLevel.H, "png", 400, 400, path
				);
		if(encode) {
			BaseFile baseFile = fileService.saveFile(new File(path), "img");
			user.setQrCodeUrl(String.valueOf(baseFile.getId()));
		}
	}
	
	
	@Override
	public int updateUser(SysUser user) {
		SysUser userTemp = new SysUser();
		userTemp.setId(user.getId());
		userTemp.setUserName(user.getUserName());
		userTemp.setEmail(user.getEmail());
		userTemp.setGender(user.getGender());
		userTemp.setHeadImgUrl(user.getHeadImgUrl());
		userTemp.setNickName(user.getNickName());
		userTemp.setPhonecall(user.getPhonecall());
		userTemp.setCity(user.getCity());
		int result = userMapper.updateByPrimaryKeySelective(userTemp);
		//更新session
		SysUser userCurrent = userMapper.selectByPrimaryKey(user.getId());
		userCurrent.setMasterInfo(masterMapper.selectByUserId(user.getId()));
		sessionService.setSession(sessionService.getSessionId(), userCurrent);
		return result;
	}

	@Override
	public int updatePwd(SysUser user) {
		SysUser userTemp = new SysUser();
		userTemp.setId(user.getId());
		userTemp.setPwd(MD5Util.encrypt(user.getPwd()));
		int result = userMapper.updateByPrimaryKeySelective(userTemp);
		//更新session
		SysUser userCurrent = this.getCurrentUser();
		userCurrent.setPwd(userTemp.getPwd());
		return result;
		
	}
	
	@Override
	public SysUser getUserById(int userId) {
		return userMapper.selectByPrimaryKey(userId);
	}

}
