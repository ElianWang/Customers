package com.kd.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kd.dao.BusinessDealDao;
import com.kd.dao.ParamDao;
import com.kd.entity.Business;
import com.kd.service.BusinessDealService;
import com.kd.util.AppTools;
import com.kd.util.Pagination;

@Service
public class BusinessDealServiceImpl implements BusinessDealService {

	@Autowired
	private ParamDao paramDao;
	@Autowired
	private BusinessDealDao businessDealDao;
	
	@Override
	public String getNextCode(String code, int num) {
		String result="";
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String time = sdf.format(date);
		String hql ="from Business where code like '"+time+code+"%'";
		hql += " order by code desc";
		//获取当天同一大类的业务列表
		List<Business> list = paramDao.findListByMap(hql, null);  
		if(list == null || list.size() == 0){
			result=time+code+"01";
		}else{
			//业务编号最后两位加1
			String bCode = list.get(0).getCode();
			result = AppTools.getNextByLast(bCode, num);
		}
		return result;
		
	}

	@Override
	public void saveBusiness(Business business) {
		businessDealDao.save(business);
	}

	@Override
	public void updateBusiness(Business business) {
		businessDealDao.update(business);
	}

	@Override
	public void deleteBusiness(String id,String type) {
		if(type.equals("01")){
			Business business = businessDealDao.getById(id);
			String hql = "delete from Business where code='" + business.getCode() + "'";
			businessDealDao.executeHql(hql);
		}else{
			businessDealDao.deleteEntityById(Business.class, id);
		}
	}

	@Override
	public Pagination<Business> getBusinessList(Map<String, Object> params,
			int pageIndex, int pageSize,String updated) {
		Pagination<Business> pagination = null;
		if (updated == null || updated.isEmpty() || updated.equals("00")) {
			//先查出所有符合条件的任务code并去重
			String hql = "from Business where 1=1 ";
			for(String key:params.keySet()){
				if(key.equals("serviceTarget")){
					hql += " and " + key + " like:" + key;
				}else if(key.equals("startTime")){
					hql += " and operateTime >=:" + key;
				}else if(key.equals("endTime")){
					hql += " and operateTime <=:" + key;
				}else{
					hql += " and " + key + "=:" + key;
				}
			}
			hql += " order by beginTime desc";
			List<Business> all = businessDealDao.findListByMap(hql, params);
			List<String> temp = new ArrayList<String>();
			for(Business b:all){
				if(b.getCode() != null && !b.getCode().equals("null") && !temp.contains(b.getCode())){
					temp.add(b.getCode());
				}
			}
			int start = (pageIndex - 1) * pageSize;
			int end = (pageIndex * pageSize) - 1;
			//根据分页参数将任务数量进行限制
			List<String> codes = new ArrayList<String>();
			if(temp.size() >= start){
				if(temp.size() < end){
					end = temp.size() - 1;
				}
				for(int i = start;i <= end;i++){
					if(temp.get(i) != null && !temp.get(i).equals("null")){
						codes.add(temp.get(i));
					}
				}
			}
			//再根据筛选后的code查询符合条件的所有任务
			String childHql = "from Business bu where bu.code in(" + StringUtils.join(codes,",") + ") ";
			for(String key:params.keySet()){
				if(key.equals("serviceTarget")){
					childHql += " and bu." + key + " like:" + key;
				}else if(key.equals("startTime")){
					childHql += " and bu.operateTime >=:" + key;
				}else if(key.equals("endTime")){
					childHql += " and bu.operateTime <=:" + key;
				}else{
					childHql += " and bu." + key + "=:" + key;
				}
			}
			childHql += " order by bu.beginTime desc,bu.operateTime desc,bu.updateTime desc";
			List<Business> list = businessDealDao.findListByMap(childHql, params);
			pagination = new Pagination<Business>();
			pagination.setCurIndex(pageIndex);
			pagination.setPageSize(pageIndex);
			pagination.setItems(list);
			pagination.setRowsCount(temp.size());
			if(pagination.getItems() != null){
				String tempCode = null;
				int i =0;
				for(Business b:pagination.getItems()){
					if(tempCode == null){
						tempCode = b.getCode();
					}else{
						if(b.getCode().equals(tempCode)){
							b.setCode(tempCode + i);
							b.setParentCode(tempCode);
							i++;
						}else{
							tempCode = b.getCode();
						}
					}
				}
			}
		}else{
			String hql = "from Business where type='01' ";
			for(String key:params.keySet()){
				if(key.equals("serviceTarget")){
					hql += " and " + key + " like:" + key;
				}else if(key.equals("startTime")){
					hql += " and operateTime >=:" + key;
				}else if(key.equals("endTime")){
					hql += " and operateTime <=:" + key;
				}else{
					hql += " and " + key + "=:" + key;
				}
			}
			hql += " order by beginTime desc";
			pagination = businessDealDao.findPagination(hql, params, pageIndex, pageSize);
		}
		return pagination;
	}

	@Override
	public Business getById(String id) {
		return businessDealDao.getById(id);
	}

	@Override
	public Business getByCode(String code) {
		String hql = "from Business where code='" + code + "' and type='01'";
		List<Business> list = businessDealDao.findHql(hql, null);
		if (list == null || list.size() == 0) {
			return null;
		}
		return list.get(0);
	}

	@Override
	public List<Business> queryAllList(Map<String, Object> params,String updated) {
		List<Business> result = new ArrayList<Business>();
		if (updated == null || updated.isEmpty() || updated.equals("00")) {
			//先查出所有符合条件的任务code并去重
			String hql = "from Business where 1=1 ";
			for(String key:params.keySet()){
				if(key.equals("serviceTarget")){
					hql += " and " + key + " like:" + key;
				}else if(key.equals("startTime")){
					hql += " and operateTime >=:" + key;
				}else if(key.equals("endTime")){
					hql += " and operateTime <=:" + key;
				}else{
					hql += " and " + key + "=:" + key;
				}
			}
			hql += " order by code desc,operateTime desc";
			result = businessDealDao.findListByMap(hql, params);
		}else{
			String hql = "from Business where type='01' ";
			for(String key:params.keySet()){
				if(key.equals("serviceTarget")){
					hql += " and " + key + " like:" + key;
				}else if(key.equals("startTime")){
					hql += " and operateTime >=:" + key;
				}else if(key.equals("endTime")){
					hql += " and operateTime <=:" + key;
				}else{
					hql += " and " + key + "=:" + key;
				}
			}
			hql += " order by operateTime desc";
			result = businessDealDao.findListByMap(hql, params);
		}
		return result;
	}

}