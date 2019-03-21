package com.kd.service;

import java.util.List;
import java.util.Map;

import com.kd.entity.Doc;
import com.kd.util.Pagination;

public interface DocumentService {

	/**
	 * 根据查询条件获取分页后的列表
	 * @param params 查询参数
	 * @param pageIndex 页数
	 * @param pageSize 每页大小
	 * @return
	 */
	public Pagination<Doc> queryList(Map<String, Object> params,int pageIndex,int pageSize);
	
	public void save(Doc doc);
	
	public void update(Doc doc);
	
	public void delete(String id);
	
	/**
	 * 通过id获取对应的对象
	 * @param id
	 * @return
	 */
	public Doc getById(String id);
	
	/**
	 * 获取档案的下一个编号
	 * @return
	 */
	public String getNextCode();
	
	
	public List<Doc> queryAllList(Map<String, Object> params);
}
