package com.swinginwind.iknowu.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.swinginwind.core.pager.JSONResponse;
import com.swinginwind.iknowu.model.SysUser;
import com.swinginwind.iknowu.service.SysUserService;

@Controller
@RequestMapping("user")
public class UserController {

	@Autowired
	private SysUserService userService;

	@Autowired
	private BaseFileController fileController;

	@RequestMapping("getCurrentUser")
	@ResponseBody
	public JSONResponse getCurrentUser() {
		JSONResponse res = new JSONResponse();
		SysUser userCurrent = userService.getCurrentUser();
		res.put("user", userCurrent);
		return res;
	}

	@RequestMapping("updateUser")
	@ResponseBody
	public JSONResponse updateUser(@RequestBody SysUser user, HttpServletRequest request) {
		JSONResponse res = new JSONResponse();
		SysUser userCurrent = userService.getCurrentUser();
		if (userCurrent.getId() != user.getId()) {
			res.setStatusAndMsg(false, "非法操作");
		} else {
			userService.updateUser(user);
			res.put("user", userService.getCurrentUser());
			res.setMsg("修改成功");
		}
		return res;
	}

	@RequestMapping("updatePwd")
	@ResponseBody
	public JSONResponse updatePwd(@RequestBody SysUser user, HttpServletRequest request) {
		JSONResponse res = new JSONResponse();
		SysUser userCurrent = userService.getCurrentUser();
		if (userCurrent.getId() != user.getId()) {
			res.setStatusAndMsg(false, "非法操作");
		} else {
			userService.updatePwd(user);
			res.setMsg("修改成功");
		}
		return res;
	}

	@RequestMapping("getHeadImg")
	public void getHeadImg(HttpServletRequest request, HttpServletResponse response, int userId) {
		SysUser user = userService.getUserById(userId);
		if (user != null && !StringUtils.isEmpty(user.getHeadImgUrl())) {
			fileController.getFile(request, response, Integer.parseInt(user.getHeadImgUrl()), "1");
		}
		else
			response.setStatus(204);
	}

}
