package com.kd.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kd.dao.ProgressDao;
import com.kd.dao.impl.ProgressDaoImpl;
import com.kd.entity.Doc;
import com.kd.entity.Progress;
import com.kd.service.ProgressService;
import com.kd.util.AppTools;
import com.kd.util.Pagination;

@Service
public class ProgressServiceImpl implements ProgressService{
	
	@Autowired
	private ProgressDao progressDao;

	@Override
	public Pagination<Progress> queryList(Map<String, Object> params, int pageIndex,
			int pageSize) {
		// TODO Auto-generated method stub
		String hql = "from Progress where 1=1 ";
		for(String key:params.keySet()){
			if (key.equals("title")) {
				hql += " and " + key + " like:" + key;
			}else{
				hql += " and " + key + "=:" + key;
			}
		}
		Pagination<Progress> pagination = progressDao.findPagination(hql, params, pageIndex, pageSize);
		return pagination;
	}

	@Override
	public void save(Progress progress) {
		// TODO Auto-generated method stub
		progressDao.save(progress);
	}

	@Override
	public void delete(String id) {
		if(id != null && !id.isEmpty()){
			progressDao.deleteEntityById(Progress.class, id);
		}
		
	}

	@Override
	public void update(Progress progress) {
		progressDao.update(progress);
	}

	@Override
	public List<Progress> queryProgess(Progress progress) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> queryCompany() {
		// TODO Auto-generated method stub
		return null;
	}
	
	//拼接code
	@Override
	public String getNextCode() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String str = sdf.format(new Date());
		String hql = "from Progress where code like '" + str + "%' order by code desc";
		List<Progress> list = progressDao.findList(hql, null);
		if(list == null || list.size() == 0){
			return str + "001";
		}
		String code = list.get(0).getCode();
		return AppTools.getNextByLast(code, 3);		
	}

	@Override
	public Progress getById(String id) {
		// TODO Auto-generated method stub
		return progressDao.getById(id);
	}

	@Override
	public List<Progress> queryAllList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		String hql = "from Progress where 1=1 ";
		for(String key:params.keySet()){
			if (key.equals("title")) {
				hql += " and " + key + " like:" + key;
			}else if(key.equals("ids")){
				hql += " and id in (" + params.get(key) + ")";
				params.remove(key);
			}else{
				hql += " and " + key + "=:" + key;
			}
		}
		List<Progress> pros = progressDao.findListByMap(hql, params);
		return pros;
	}

}
