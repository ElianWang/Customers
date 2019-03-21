package com.kd.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kd.dao.UserDao;
import com.kd.entity.User;
import com.kd.service.UserService;
import com.kd.util.Pagination;

@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;

	@Override
	public User getByAccount(String account) {
		List<User> list = userDao.findByProperty("account", account);
		if (list == null || list.size() == 0) {
			return null;
		}
		return list.get(0);
	}

	@Override
	public Pagination<User> queryList(Map<String, Object> params,
			int pageIndex, int pageSize) {
		Pagination<User> pagination = userDao.queryList(params, pageIndex, pageSize);
		return pagination;
	}

	@Override
	public void saveUser(User user) {
		userDao.save(user);
	}

	@Override
	public void updateUser(User user) {
		userDao.update(user);
	}

	@Override
	public List<User> getAllUsers() {
		return userDao.getAllUsers();
	}

	@Override
	public void resetPassword(String account, String password) {
		userDao.resetPassword(account, password);
	}
	
}
