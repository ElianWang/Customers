package com.kd.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.kd.dao.UserDao;
import com.kd.entity.User;
import com.kd.util.Pagination;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

	@Override
	public User getByAccount(String account) {
		String hql = "from User where account=:account";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("account", account);
		return super.get(hql, map);
	}

	@Override
	public Pagination<User> queryList(Map<String, Object> params,
			int pageIndex, int pageSize) {
		String hql = "from User where isAdmin='false' ";
		for(String key:params.keySet()){
			if(key.equals("account")){
				hql += " and account like:account ";
				params.put("account", "%" + params.get("account") + "%");
			}else if(key.equals("name")){
				hql += " and name like:name ";
				params.put("name", "%" + params.get("name") + "%");
			}else{
				hql += " and " + key +"=:" + key;
			}
		}
		Pagination<User> pagination = super.findPagination(hql,params,pageIndex, pageSize);
		return pagination;
	}

	@Override
	public List<User> getAllUsers() {
		String hql = "from User";
		return super.findListByMap(hql,null);
	}

	@Override
	public void resetPassword(String account, String password) {
		String hql = "update User set password=:password where account=:account";
		Query query = getSession().createQuery(hql);
		query.setParameter("password", password);
		query.setParameter("account", account);
		query.executeUpdate();
	}

}
