package com.kd.dao.impl;

import org.springframework.stereotype.Repository;

import com.kd.dao.BackupDao;
import com.kd.entity.backup;

@Repository("backupDao")
public class BackupDaoImpl extends BaseDaoImpl<backup> implements BackupDao{

}
