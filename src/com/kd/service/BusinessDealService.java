package com.kd.service;

import java.util.List;
import java.util.Map;

import com.kd.entity.Business;
import com.kd.util.Pagination;


public interface BusinessDealService {

	/**
	 * 根据页面选择的大类code来确定业务编号
	 * @param code
	 * @param num 
	 * @return
	 */
	public String getNextCode(String code, int num);

	public void saveBusiness(Business business);
	
	public void updateBusiness(Business business);
	
	public void deleteBusiness(String id,String type);
	
	/**
	 * 根据查询条件查询业数据列表
	 * @param params 查询参数
	 * @param pageIndex 页数
	 * @param pageSize 每页大小
	 * @return
	 */
	public Pagination<Business> getBusinessList(Map<String, Object> params,int pageIndex,int pageSize,String updated);
	
	public Business getById(String id);
	
	/**
	 * 根据code获取唯一主任务
	 * @param code
	 * @return
	 */
	public Business getByCode(String code);
	
	public List<Business> queryAllList(Map<String, Object> params,String updated);
}
