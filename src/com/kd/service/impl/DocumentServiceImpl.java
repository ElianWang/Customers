package com.kd.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kd.dao.DocumentDao;
import com.kd.entity.Doc;
import com.kd.service.DocumentService;
import com.kd.util.AppTools;
import com.kd.util.Pagination;

@Service
public class DocumentServiceImpl implements DocumentService{
	
	@Autowired
	private DocumentDao documentDao;

	@Override
	public Pagination<Doc> queryList(Map<String, Object> params, int pageIndex,
			int pageSize) {
		String hql = "from Doc where 1=1 ";
		for(String key:params.keySet()){
			if (key.equals("fullName") || key.equals("organization")) {
				hql += " and " + key + " like:" + key;
			}else{
				hql += " and " + key + "=:" + key;
			}
		}
		Pagination<Doc> pagination = documentDao.findPagination(hql, params, pageIndex, pageSize);
		return pagination;
	}

	@Override
	public void save(Doc doc) {
		documentDao.save(doc);
	}

	@Override
	public void update(Doc doc) {
		documentDao.update(doc);
	}

	@Override
	public void delete(String id) {
		if(id != null && !id.isEmpty()){
			documentDao.deleteEntityById(Doc.class, id);
		}
	}

	@Override
	public Doc getById(String id) {
		return documentDao.getById(id);
	}

	@Override
	public String getNextCode() {
		String hql = "from Doc order by code desc";
		List<Doc> list = documentDao.findList(hql, null);
		if (list == null || list.size() == 0) {
			return "0001";
		}
		String code = list.get(0).getCode();
		return AppTools.getNextByLast(code, 4);
	}

	@Override
	public List<Doc> queryAllList(Map<String, Object> params) {
		String hql = "from Doc where 1=1 ";
		for(String key:params.keySet()){
			if (key.equals("fullName") || key.equals("organization")) {
				hql += " and " + key + " like:" + key;
			}else if(key.equals("ids")){
				hql += " and id in (" + params.get(key) + ")";
				params.remove(key);
			}else{
				hql += " and " + key + "=:" + key;
			}
		}
		List<Doc> docs = documentDao.findListByMap(hql, params);
		return docs;
	}

}
