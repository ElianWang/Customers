package com.kd.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.kd.entity.backup;
import com.kd.util.Pagination;

public interface BackupService {
	
	public void saveBackup(backup bak);

	public Pagination<backup> queryList(Map<String, Object> map, int pageIndex,
			int pageSize);

	public void delete(String id);

	public Pagination<backup> getbaklist(Map<String, Object> map,
			int pageIndex, int pageSize);

	public void deleteall(List<backup> ls);

	public void addall(List<backup> ls);
	
	public backup getById(String id);

}
