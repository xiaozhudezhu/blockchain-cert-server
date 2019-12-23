package com.swinginwind.iknowu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.swinginwind.core.pager.JSONResponse;
import com.swinginwind.iknowu.service.DicTypeService;

@Controller
@RequestMapping("dic")
public class DicController {
	
	@Autowired
	DicTypeService dicTypeService;
	
	@RequestMapping(value = "getTypes")
	@ResponseBody
	public JSONResponse getTypes() {
		JSONResponse res = new JSONResponse();		
		res.put("types", dicTypeService.selectAll());
		return res;
	}
	
}
