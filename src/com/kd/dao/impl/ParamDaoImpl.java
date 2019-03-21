package com.kd.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.kd.dao.ParamDao;
import com.kd.entity.Params;
import com.kd.util.Pagination;

@Repository("paramDao")
public class ParamDaoImpl extends BaseDaoImpl<Params> implements ParamDao {

	@Override
	public Pagination<Params> queryList(Map<String, Object> params,int pageIndex, int pageSize) {
		String querySql = " from Params" + " where 1=1 ";
		if (!params.isEmpty()) {
			for(String key:params.keySet()){
				if (key.equals("name")) {
					querySql += " and name like:" + key + " ";
				}else{
					querySql += " and " + key + "=:" + key + " ";
				}
			}
		}
		querySql += " order by code desc";
		Pagination<Params> pagination = super.findPagination(querySql, params, pageIndex, pageSize);
		return pagination;
	}

	@Override
	public void saveParam(Params param) {
		super.save(param);
	}

	@Override
	public void updateParam(Params param) {
		super.update(param);
	}

	@Override
	public void deleteParam(String id, String paramType) {
		super.delete(id);
	}

	@Override
	public Params getByIdAndType(String id, String paramType) {
		Params param = super.getById(id);
		return param;
	}
	
	@Override
	public List<Params> getByType(String paramType) {
		List<Params> list = super.findByProperty("type", paramType);
		return list;
	}
}
