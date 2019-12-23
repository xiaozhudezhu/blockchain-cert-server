package com.swinginwind.iknowu.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swinginwind.core.utils.ApplicationPropsUtil;
import com.swinginwind.core.utils.Identities;
import com.swinginwind.iknowu.dao.BaseFileMapper;
import com.swinginwind.iknowu.dao.BaseMasterMapper;
import com.swinginwind.iknowu.dao.DicTypeMapper;
import com.swinginwind.iknowu.model.BaseFile;
import com.swinginwind.iknowu.model.BaseMaster;
import com.swinginwind.iknowu.pager.BaseMasterPager;
import com.swinginwind.iknowu.service.BaseMasterService;
import com.swinginwind.iknowu.service.SysUserService;

@Service
public class BaseMasterServiceImpl implements BaseMasterService {
	
	@Autowired
	private BaseMasterMapper baseMasterMapper;
	
	@Autowired
	private SysUserService userService;
	
	@Autowired
	private BaseFileMapper fileMapper;
	
	@Autowired
	DicTypeMapper dicTypeMapper;


	@Override
	public BaseMaster selectByPrimaryKey(String tid) {
		return baseMasterMapper.selectByPrimaryKey(tid);
	}

	@Override
	public List<BaseMaster> select(BaseMasterPager pager) {
		pager.setNewDayLimit(Integer.parseInt(ApplicationPropsUtil.getPropsValue("config.newDayLimit")));
		pager.setPopCountLimit(Integer.parseInt(ApplicationPropsUtil.getPropsValue("config.popCountLimit")));
		return baseMasterMapper.select(pager);
	}
	
	@Override
	public List<BaseMaster> selectAll(BaseMasterPager pager) {
		return baseMasterMapper.selectAll(pager);
	}

	@Override
	public BaseMaster register(BaseMaster master) {
		int userId = userService.getCurrentUser().getId();
		BaseMaster bm = baseMasterMapper.selectByUserId(userId);
		if(bm == null) {
			bm = new BaseMaster();
			bm.setTid(Identities.uuid());
			bm.setMastername(master.getMastername());
			bm.setIdNumber(master.getIdNumber());
			bm.setRank(master.getRank());
			bm.setPosition(master.getPosition());
			bm.setFirm(master.getFirm());
			bm.setFields(master.getFields());
			bm.setAuditRemark(master.getAuditRemark());
			bm.setUserId(userId);
			bm.setState("1");
			bm.setAuditResult(null);
			bm.setRegTime(new Date());
			bm.setTypeList(master.getTypeList());
			bm.setRegFiles(master.getRegFiles());
			baseMasterMapper.insert(bm);
		} else {
			bm.setMastername(master.getMastername());
			bm.setIdNumber(master.getIdNumber());
			bm.setRank(master.getRank());
			bm.setPosition(master.getPosition());
			bm.setFirm(master.getFirm());
			bm.setFields(master.getFields());
			bm.setAuditRemark(master.getAuditRemark());
			bm.setUserId(userId);
			bm.setState("1");
			bm.setTypeList(master.getTypeList());
			bm.setRegFiles(master.getRegFiles());
			baseMasterMapper.updateByPrimaryKey(bm);
			fileMapper.deleteUnusedFilesWhenUpdate(bm.getTid(), "MASTER_REG", master.getRegFiles());
		}
		baseMasterMapper.updateMasterTypes(bm);
		if(bm.getRegFiles() != null && bm.getRegFiles().size() > 0) {
			for(int i = 0; i < bm.getRegFiles().size(); i ++) {
				BaseFile file = bm.getRegFiles().get(i);
				file.setSortCode(i + 1);
				file.setRecordType("MASTER_REG");
				file.setRecordId(bm.getTid());
				fileMapper.updateByPrimaryKeySelective(file);
			}
		}
		return bm;
	}
	
	@Override
	public BaseMaster getRegInfo() {
		int userId = userService.getCurrentUser().getId();
		return this.getRegInfo(userId);
	}
	
	@Override
	public BaseMaster getRegInfo(int userId) {
		BaseMaster bm = baseMasterMapper.selectByUserId(userId);
		if(bm != null) {
			bm.setTypeList(dicTypeMapper.selectByMasterId(bm.getTid()));
			bm.setRegFiles(fileMapper.selectByRecord(bm.getTid(), "MASTER_REG"));
		}
		return bm;
	}
	
	@Override
	public void audit(BaseMaster master) {
		BaseMaster bm = baseMasterMapper.selectByPrimaryKey(master.getTid());
		if(bm != null) {
			bm.setAuditResult(master.getAuditResult());
			bm.setState(master.getState());
			baseMasterMapper.updateByPrimaryKey(bm);
		}
	}

}
