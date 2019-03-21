package com.kd.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kd.dao.BackupDao;
import com.kd.entity.Doc;
import com.kd.entity.backup;
import com.kd.service.BackupService;
import com.kd.util.Pagination;

@Service("backupService")
public class BackupServiceImpl implements BackupService{
	
	@Autowired
	private BackupDao backupDao;
	
	public void saveBackup(backup bak){
		backupDao.save(bak);		
	}

	@Override
	public Pagination<backup> queryList(Map<String, Object> params, int pageIndex,
			int pageSize) {
		String hql = "from backup where 1=1 ";
		for(String key:params.keySet()){
	         if(key.equals("startTime")){
				hql += " and date >=:" + key;
			}else if(key.equals("endTime")){
				hql += " and date <=:" + key;
			}else{
				hql += " and " + key + "=:" + key;
			}
		}
		backupDao.clearSession();
		Pagination<backup> pagination = backupDao.findPagination(hql, params, pageIndex, pageSize);
		return pagination;
	}

	@Override
	public void delete(String id) {
		if(id != null && !id.isEmpty()){
			backupDao.deleteEntityById(backup.class, id);
		}
	}

	@Override
	public Pagination<backup> getbaklist(Map<String, Object> map,
			int pageIndex, int pageSize) {
		backupDao.clearSession();
		String hql = "from backup where 1=1 ";
		Pagination<backup> pagination = backupDao.findPagination(hql, map, pageIndex, pageSize);
		return pagination;
	}

	@Override
	public void deleteall(List<backup> ls) {
		backupDao.delete(ls);
		
	}

	@Override
	public void addall(List<backup> ls) {
		backupDao.save(ls);
		
	}

	@Override
	public backup getById(String id) {
		return backupDao.getById(id);
	}

}
