package com.swinginwind.certificate.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.swinginwind.certificate.model.Certificate;
import com.swinginwind.certificate.pager.CertificatePager;
import com.swinginwind.certificate.service.CertificateService;
import com.swinginwind.core.pager.JSONResponse;
import com.swinginwind.core.pager.JqgridResponse;
import com.swinginwind.iknowu.service.SysUserService;

@Controller
@RequestMapping("cert")
public class CertificateController {
	
	@Autowired
	CertificateService certificateService;
	
	@Autowired
	private SysUserService userService;
	
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public JSONResponse create(@RequestBody Certificate cert, HttpServletRequest request) {
		JSONResponse res = new JSONResponse();
		certificateService.insert(cert);
		res.setMsg("create success!");
		res.put("cert", cert);
		return res;
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public JSONResponse delete(@RequestBody Certificate cert, HttpServletRequest request) {
		JSONResponse res = new JSONResponse();
		cert = certificateService.selectByPrimaryKey(cert.getId());
		if(userService.isAdmin() || userService.getCurrentUser().getId().equals(cert.getCreateUser())) {
			certificateService.delete(cert);
			res.setMsg("delete success!");
		}
		else {
			res.setStatusAndMsg(false, "非法操作！");
		}
		return res;
	}
	
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public JSONResponse update(@RequestBody Certificate cert, HttpServletRequest request) {
		JSONResponse res = new JSONResponse();
		Certificate cert1 = certificateService.selectByPrimaryKey(cert.getId());
		if(userService.isAdmin() || userService.getCurrentUser().getId().equals(cert1.getCreateUser())) {
			certificateService.update(cert);
			res.setMsg("update success!");
		}
		else {
			res.setStatusAndMsg(false, "非法操作！");
		}
		return res;
	}
	
	@RequestMapping("search")
	@ResponseBody
	public JqgridResponse<Certificate> search(@RequestBody CertificatePager pager) {
		certificateService.select(pager);
		JqgridResponse<Certificate> res = new JqgridResponse<Certificate>(pager);
		return res;
	}
	
	

}
