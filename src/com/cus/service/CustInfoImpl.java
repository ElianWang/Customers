package com.cus.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.Order;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cus.dao.CustInfoDao;
import com.kd.entity.Customers;
import com.kd.entity.Doc;
import com.kd.entity.Orders;
import com.kd.entity.Records;
import com.kd.util.Pagination;

@Service
public class CustInfoImpl implements CustInfo {
	
	@Autowired
	private CustInfoDao infoDao;

	@Override
	public void saveCustInfo(Customers customers) {
		infoDao.save(customers);
	}

	@Override
	public Pagination<Customers> getCustInfo(Map<String, Object> map, int pageIndex, int pageSize) {
		String hql = "from Customers where 1=1";
		for(String key:map.keySet()){
			if (key.equals("name") || key.equals("address")||key.equals("company")|| key.equals("city")) {
				hql += " and " + key + " like:" + key;
			}else if(key.equals("startTime")){
				hql += " and updateTime >= date(:startTime)";
			}else if(key.equals("endTime")){
//				hql += " and updateTime <=Date(:endTime,'yyyy-MM-dd HH:mm:ss')";
				hql += " and updateTime < date(:endTime)+1";
			}else if(key.equals("createTime")){
				hql +=" and (datediff (createTime,:createTime)=0) ";
			}else{
				hql += " and " + key + " =:" + key;
			}
		}
		hql += " order by updateTime desc ";
		System.out.println(hql);
		Pagination<Customers> pagination = infoDao.findPagination(hql, map, pageIndex, pageSize);
		return pagination;
	}

	@Override
	public Customers getCustById(Integer id) {
		Customers id2 = infoDao.getById(id);
		return id2;
	}

	@Override
	public void updateCustInfo(Customers customers) {
		infoDao.update(customers);
	}

	@Override
	public void deleteCust(Integer id) {
		infoDao.deleteEntityById(Customers.class, id);
	}

	@Override
	public void addTrack(Records records) {
		infoDao.save(records);
	}

	@Override
	public Pagination<Records> selectTrack(Map<String, Object> map, int pageIndex, int pageSize) {
		String hql = "from Records where 1=1";
		for (String key : map.keySet()) {
			if(key.equals("customerId")) {
				hql += " and " + key + " =:" + key;
			}
		}
		hql += " order by id desc ";
		Pagination<Records> pagination2 = infoDao.findPagination(hql, map, pageIndex, pageSize);
		return pagination2;
	}

	@Override
	public boolean checkInfo(String name, String phone) {
		String hql = "from Customers where 1=1";
		Map<String, Object> map = new HashMap<>();
		map.put("name", name);
		map.put("phone", phone);
		for (String key: map.keySet()) {
			hql += " and " + key + " =:" + key;
		}
		List<Object> list = infoDao.findListByMap(hql,map);
		if(list.size() != 0) {
			return false;
		}else{
			return true;
		}
		
	}

	@Override
	public void updateTime(int int1, Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String string = sdf.format(date);
		String sql = "update Customers set updateTime = date_format('"+string+"','%Y%m%d%H%i%s') where id = "+int1;
		infoDao.updateBySqlString(sql);
	}

	@Override
	public void addOrder(Orders orders) {
		infoDao.save(orders);
	}

	@Override
	public Pagination<Orders> getOrderInfo(Map<String, Object> map, int pageIndex, int pageSize) {
		String hql ="from Orders where 1 = 1";
		for(String key : map.keySet()) {
			hql += " and " + key + " =:" + key;
		}
		
		Pagination<Orders> pagination = infoDao.findPagination(hql, map, pageIndex, pageSize);
		return pagination;
	}

	@Override
	public List<Customers> queryAllList(Map<String, Object> map) {
		
		String hql = "from Customers where 1=1 ";
		for(String key:map.keySet()){
			if (key.equals("name") || key.equals("address")||key.equals("company")|| key.equals("city")||key.equals("createTime")) {
				hql += " and " + key + " like:" + key;
			}else if(key.equals("startTime")){
				hql += " and updateTime >=:" + key;
			}else if(key.equals("endTime")){
				hql += " and updateTime <=:" + key;
			}else{
				hql += " and " + key + " =:" + key;
			}
		}
		List<Customers> docs = infoDao.findListByMap(hql, map);
		return docs;
	}


}
