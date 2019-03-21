package com.cus.service;

import java.util.Date;
import java.util.List;
import java.util.Map;


import com.kd.entity.Customers;
import com.kd.entity.Orders;
import com.kd.entity.Records;
import com.kd.util.Pagination;

public interface CustInfo {
	
	public void saveCustInfo(Customers customers);

	public Pagination<Customers> getCustInfo(Map<String, Object> map, int pageIndex, int pageSize);

	public Customers getCustById(Integer id);

	public void updateCustInfo(Customers customers);

	public void deleteCust(Integer id);

	public void addTrack(Records records);

	public Pagination<Records> selectTrack(Map<String, Object> map, int pageIndex, int pageSize);

	public boolean checkInfo(String name, String phone);

	public void addOrder(Orders orders);

	public Pagination<Orders> getOrderInfo(Map<String, Object> map, int pageIndex, int pageSize);

	public void updateTime(int int1, Date date);

	public List<Customers> queryAllList(Map<String, Object> map);
}