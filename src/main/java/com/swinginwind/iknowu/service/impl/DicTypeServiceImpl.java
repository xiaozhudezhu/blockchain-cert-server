package com.swinginwind.iknowu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swinginwind.iknowu.dao.DicTypeMapper;
import com.swinginwind.iknowu.model.DicType;
import com.swinginwind.iknowu.service.DicTypeService;

@Service
public class DicTypeServiceImpl implements DicTypeService {
	
	@Autowired
	DicTypeMapper dicTypeMapper;

	@Override
	public List<DicType> selectAll() {
		return dicTypeMapper.selectAll();
	}

}
