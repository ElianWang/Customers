package com.kd.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kd.dao.ParamDao;
import com.kd.entity.Params;
import com.kd.entity.Params.paramEnum;
import com.kd.service.ParamService;
import com.kd.util.AppTools;
import com.kd.util.Pagination;
import com.kd.util.ResourceUtil;

@Service("paramService")
public class ParamServiceImpl implements ParamService {
	
	@Autowired
	private ParamDao paramDao;

	@Override
	public Pagination<Params> queryList(HttpServletRequest request,int pageIndex, int pageSize) {
		String name = request.getParameter("name");
		String paramType = request.getParameter("paramType");
		String parentCode = request.getParameter("parentCode");
		Map<String, Object> params = new HashMap<String, Object>();
		if (name != null && !name.isEmpty()) {
			params.put("name", "%" + name + "%");
		}
		if (paramType != null && !paramType.isEmpty()) {
			params.put("type", paramType);
		}
		if (parentCode != null && !parentCode.isEmpty()) {
			params.put("parentCode", parentCode);
		}
		Pagination<Params> pagination = paramDao.queryList(params, pageIndex, pageSize);
		return pagination;
	}

	@Override
	public void saveParam(Params param) {
		paramDao.saveParam(param);
		initParams(param.getType());
	}

	@Override
	public void updateParam(Params param) {
		paramDao.updateParam(param);
		initParams(param.getType());
	}

	@Override
	public void deleteParam(String id, String paramType) {
		paramDao.deleteParam(id, paramType);
		initParams(paramType);
	}

	@Override
	public Params getByIdAndType(String id, String paramType) {
		return paramDao.getByIdAndType(id, paramType);
	}

	@Override
	public List<Params> getByType(String paramType) {
		return paramDao.getByType(paramType);
	}

	@Override
	public void initParams(String paramType) {
		List<String> types = new ArrayList<String>();
		//如果指定了参数类型，就刷新具体的参数类型，否则就刷新所有参数缓存
		if (paramType != null) {
			types.add(paramType);
		}else{
			types = paramEnum.getAllCodes();
		}
		for(String type:types){
			List<Params> list = getByType(type);
			Map<String, Object> map = new HashMap<String, Object>();
			for(Params param:list){
				map.put(param.getCode(), param.getName());
			}
			ResourceUtil.params.put(type, map);
		}
	}

	@Override
	public Params getByCode(String code) {
		List<Params> list = paramDao.findByProperty("code", code);
		if (list == null || list.size() == 0) {
			return null;
		}
		return list.get(0);
	}

	@Override
	public String getNextCode(String paramType, String parentCode,int num) {
		String hql = "from Params where type='" + paramType + "' ";
		if(parentCode != null && !parentCode.isEmpty()){
			hql += " and parentCode='" + parentCode + "'";
		}
		hql += " order by code desc";
		List<Params> list = paramDao.findList(hql, null);
		if(list == null || list.size() == 0){
			return null;
		}
		String code = list.get(0).getCode();
		return AppTools.getNextByLast(code, num);
	}

	@Override
	public Params getById(String id) {
		return paramDao.getById(id);
	}

	@Override
	public List<Params> getByPidAndType(String parentCode, String type) {
		String hql = "from Params where type=:type and parentCode=:parentCode";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		map.put("parentCode", parentCode);
		List<Params> list = paramDao.findListByMap(hql, map);
		return list;
	}

	@Override
	public Params getParentByChildCode(String childCode) {
		String hql = "from Params where code=(select  parentCode from Params where code=:childCode)";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("childCode", childCode);
		List<Params> list = paramDao.findListByMap(hql, map);
		Params p = list.get(0);
		return p;
	}

}
