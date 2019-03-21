package com.kd.service;

import java.util.List;
import java.util.Map;

import com.kd.entity.Doc;
import com.kd.entity.Progress;
import com.kd.entity.User;
import com.kd.util.Pagination;

public interface ProgressService {
	
	public Pagination<Progress> queryList(Map<String, Object> params,int pageIndex,int pageSize);
	
	public void save(Progress progress);
	
	public void delete(String id);
	
	public void update(Progress progress);
	
	public List<Progress> queryProgess(Progress progress);
	
	public List<String> queryCompany();
	
	public String getNextCode();
	
	/**
	 * 通过id获取对应的对象
	 * @param id
	 * @return
	 */
	public Progress getById(String id);
	
	public List<Progress> queryAllList(Map<String, Object> params);
}
