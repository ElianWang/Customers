package com.cus.controller;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cus.service.CustInfo;
import com.kd.entity.Customers;
import com.kd.entity.Doc;
import com.kd.entity.Orders;
import com.kd.entity.Records;
import com.kd.entity.Params.paramEnum;
import com.kd.util.Pagination;
import com.kd.util.ResourceUtil;
@Controller
@RequestMapping("/addCust")
public class AddCustController {
	
	SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	@Autowired
	private CustInfo cust;
	
	/**
	 * 返回增加客户界面
	 * @param model
	 * @return
	 */
	@RequestMapping("/addPage")
	public ModelAndView addPage(Model model,HttpServletRequest request) {
		return new ModelAndView("/customerMgt/addCust");
	}
	
	
	
	@RequestMapping("/addInfo")
	@ResponseBody
	public  JSONObject saveCustInfo(@ModelAttribute Customers customers,HttpServletRequest request ) {
		JSONObject result = new JSONObject();
		result.put("success", "true");
		try {
			boolean t = cust.checkInfo(customers.getName(),customers.getPhone());
			if(t) {
				customers.setCreateTime(new Date());
				customers.setUpdateTime(new Date());
				String username = (String) request.getSession().getAttribute("user");
				customers.setCreateUser(username);
				cust.saveCustInfo(customers);
			}else {
				result.put("success", "false");
				result.put("reason", "已经录入该用户");
				return result;
			}
		}catch (Exception e) {
			e.printStackTrace();
			result.put("reason", e.getMessage());
			result.put("success", "false");
		}
		return result;
	}
	
	/**
	 * 返回查询界面
	 * @return
	 */
	@RequestMapping("/selectCust")
	public ModelAndView selectCust() {
		return new ModelAndView("/customerMgt/selectCust");
	}
	
	@ResponseBody
	@RequestMapping("/selectInfo")
	public JSONObject selectInfo(HttpServletRequest request) throws ParseException{
		JSONObject result = new JSONObject();
		String pageIndexStr = request.getParameter("page");
		String pageSizeStr = request.getParameter("rows");
		int pageIndex = 1;
		int pageSize = 20;
		if (pageIndexStr != null && !pageIndexStr.isEmpty()) {
			try {
				pageIndex = Integer.parseInt(pageIndexStr);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (pageSizeStr != null && !pageSizeStr.isEmpty()) {
			try {
				pageSize = Integer.parseInt(pageSizeStr);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		String isVip = request.getParameter("isVip");
		String address = request.getParameter("address");
		String company = request.getParameter("company");
		String city = request.getParameter("city");
		String createUser = request.getParameter("createUser");
		String createTime = request.getParameter("createTimeStr");
		if(name != null && !name.isEmpty()){
			map.put("name", "%" + name + "%");
		}
		if(address != null && !address.isEmpty()){
			map.put("address", "%"+address+"%");
		}
		if(company != null && !company.isEmpty()){
			map.put("company", "%" + company + "%");
		}
		if(city != null && !city.isEmpty()){
			map.put("city", "%" + city + "%");
		}
		if(createUser != null && !createUser.isEmpty()){
			map.put("createUser", createUser);
		}
		if(phone != null && !phone.isEmpty()){
			map.put("phone", phone);
		}
		if(isVip != null && !isVip.isEmpty()){
			map.put("isVip", isVip);
		}
		if(createTime != null && !createTime.isEmpty()){
			map.put("createTime", createTime+" 00:00:00");
		}
		if(startTime != null && !startTime.isEmpty()){
			map.put("startTime", startTime+" 00:00:00");
		}
		if(endTime != null && !endTime.isEmpty()){
			map.put("endTime", endTime+" 23:59:59");
		}
		Pagination<Customers> pagination =cust.getCustInfo(map,pageIndex, pageSize);
		JSONArray rows = new JSONArray();
		for (Customers customers2: pagination.getItems()) {
			JSONObject obj = new JSONObject();
			obj.put("id", customers2.getId());
			obj.put("name", customers2.getName());
			obj.put("phone", customers2.getPhone());
			obj.put("city", customers2.getCity());
			obj.put("createTime",s.format(customers2.getCreateTime()));
			obj.put("updateTime", s.format(customers2.getUpdateTime()));
			obj.put("createUser", customers2.getCreateUser());
			obj.put("remarks", customers2.getRemarks());
			obj.put("isVip", customers2.getIsVip());
			obj.put("company", customers2.getCompany());
			obj.put("address", customers2.getAddress());
			rows.add(obj);
		}
		result.put("rows",rows);
		result.put("total",pagination.getRowsCount());
		return result;
	}
	/**
	 * 返回编辑页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/updatePage")
	public ModelAndView updatePage(Integer id,HttpServletRequest request) {
		Customers customer = cust.getCustById(id);
		request.getSession().setAttribute("customer", customer);
		return new ModelAndView("/customerMgt/updateCust");
	}
	
	@RequestMapping("/editInfo")
	@ResponseBody
	public JSONObject editInfo(@ModelAttribute Customers customers,Integer id,HttpServletRequest request) {
		customers.setId(id);
		JSONObject result = new JSONObject();
		result.put("success", true);
		try {
			Customers old = cust.getCustById(id);
			customers.setCreateTime(old.getCreateTime());
			customers.setUpdateTime(new Date());
			customers.setCreateUser(old.getCreateUser());
			cust.updateCustInfo(customers);
		}catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
		}
		return result;
		
	}
	
	@RequestMapping("/deleteCust")
	@ResponseBody
	public JSONObject deleteCust(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		String id = request.getParameter("id");
		result.put("success", true);
		try {
			cust.deleteCust(Integer.valueOf(id));
		}catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
		}
		return result;
		
	}
	/**
	 * 返回跟踪详情界面
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("/showInfo")
	public ModelAndView showInfo(Integer id,HttpServletRequest request) {
		Customers customer = cust.getCustById(id);
		request.getSession().setAttribute("customer", customer);
		return new ModelAndView("customerMgt/showCust");
	}
	
	/**
	 * 返回跟踪该客户的记录表
	 * @param id
	 * @return
	 */
	@RequestMapping("/trackInfo")
	@ResponseBody
	public JSONObject trackInfo(Integer id,HttpServletRequest request) {
		JSONObject result = new JSONObject();
		result.put("success", true);
		String pageIndexStr = request.getParameter("page");
		String pageSizeStr = request.getParameter("rows");
		int pageIndex = 1;
		int pageSize = 10;
		if (pageIndexStr != null && !pageIndexStr.isEmpty()) {
			try {
				pageIndex = Integer.parseInt(pageIndexStr);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (pageSizeStr != null && !pageSizeStr.isEmpty()) {
			try {
				pageSize = Integer.parseInt(pageSizeStr);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("customerId", id);
		try {
			Pagination<Records> pagination= cust.selectTrack(map,pageIndex, pageSize);
			JSONArray rows = new JSONArray();
			for (Records records: pagination.getItems()) {
				JSONObject obj = new JSONObject();
				obj.put("content", records.getContent());
				obj.put("createTime", s.format(records.getCreateTime()));
				obj.put("createUser", records.getCreateUser());
				rows.add(obj);
			}
			result.put("rows",rows);
			result.put("total",pagination.getRowsCount());
		}catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
		}
		return result;
		
	}
	
	
	@RequestMapping("/addTrack")
	public ModelAndView addTrack(Integer id,HttpServletRequest request) {
		request.getSession().setAttribute("id", id);
		return new ModelAndView("customerMgt/addTrack");
	}
	
	
	@RequestMapping("/editTrack")
	@ResponseBody
	public  JSONObject editTrack(HttpServletRequest request ) {
		JSONObject result = new JSONObject();
		result.put("success", "true");
		String createUser = (String) request.getSession().getAttribute("user");
		try {
			Records records =new Records();
			records.setContent(request.getParameter("content"));
			records.setCreateTime(new Date());
			records.setCreateUser(createUser);
			String s = request.getParameter("customerId");
			int int1 = Integer.parseInt(s);
			records.setCustomerId(int1);
			cust.addTrack(records);
			cust.updateTime(int1,new Date());
		}catch (Exception e) {
			e.printStackTrace();
			result.put("success", "false");
		}
		return result;
	}
	
	@RequestMapping("/addOrder")
	public ModelAndView addOrder(Integer id,HttpServletRequest request) {
		request.getSession().setAttribute("id", id);
		return new ModelAndView("/customerMgt/addOrder");
	}
	
	@RequestMapping("/addCustOrder")
	@ResponseBody
	public JSONObject addCustOrder(HttpServletRequest request) throws ParseException {
		JSONObject object = new JSONObject();
		object.put("success", true);
		try {
			String product = request.getParameter("product");
			String type = request.getParameter("type");
			String remarks = request.getParameter("remarks");
			String area = request.getParameter("area");
			String price = request.getParameter("price");
			String totalAmount = request.getParameter("totalAmount");
			String customerId = request.getParameter("customerId");
			Orders orders = new Orders();
			orders.setProduct(product);
			orders.setType(type);
			orders.setRemarks(remarks);
			if(!"".equals(area)&& area!= null) {
				orders.setArea(Float.valueOf(area));
			}
			if(!"".equals(price)&& price!= null) {
				orders.setPrice(Float.valueOf(price));
			}
			if(!"".equals(totalAmount)&& totalAmount!= null) {
				orders.setTotalAmount(Float.valueOf(totalAmount));
			}
			if(!"".equals(customerId)&& customerId!= null) {
				orders.setCustomerId(Integer.parseInt(customerId));
			}
			orders.setOrderTime(new Date());
			cust.addOrder(orders);
			cust.updateTime(Integer.parseInt(customerId), new Date());
		}catch (Exception e) {
			System.out.println(e.getMessage());
			object.put("success",false);
		}
		return object;
	}
	
	@RequestMapping("/orderInfo")
	@ResponseBody
	public JSONObject orderInfo(Integer id,HttpServletRequest request) {
		JSONObject object = new JSONObject();
		object.put("success", true);
		String pageIndexStr = request.getParameter("page");
		String pageSizeStr = request.getParameter("rows");
		int pageIndex = 1;
		int pageSize = 10;
		if (pageIndexStr != null && !pageIndexStr.isEmpty()) {
			try {
				pageIndex = Integer.parseInt(pageIndexStr);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (pageSizeStr != null && !pageSizeStr.isEmpty()) {
			try {
				pageSize = Integer.parseInt(pageSizeStr);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Map<String, Object> map = new HashMap<>();
		map.put("customerId", id);
		try {
			Pagination<Orders> pagination = cust.getOrderInfo(map, pageIndex,pageSize);
			JSONArray array  = new JSONArray();
			for (Orders order : pagination.getItems()) {
				JSONObject object2 = new JSONObject();
				object2.put("orderTime", s.format(order.getOrderTime()));
				object2.put("product", order.getProduct());
				object2.put("type", order.getType());
				object2.put("price", order.getPrice());
				object2.put("area", order.getArea());
				object2.put("totalAmount", order.getTotalAmount());
				object2.put("remarks", order.getRemarks());
				array.add(object2);
			}
			object.put("rows",array);
			object.put("total",pagination.getRowsCount());
		}catch (Exception e) {
			System.out.println(e.getMessage());
			object.put("success", false);
		}
		return object;
	}
	
	/**
	 * 导出excel
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping("/exportXls")
	public String exportXls(Customers cus,HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) {
		//Map<String, String[]> parameterMap = request.getParameterMap();
		Map<String, Object> map = new HashMap<String, Object>();
		String name = "";
		String address ="";
		String company = "";
		String city ="" ;
		String createUser ="";
		String phone = request.getParameter("phone");
		String isVip = request.getParameter("isVip");
		String createTime = request.getParameter("createTimeStr");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		try {
			//中文参数
			if(request.getParameter("name")!=null){
				name = new String(request.getParameter("name").getBytes("iso-8859-1"),"utf-8");
			}
			if(request.getParameter("address")!=null){
				address = new String(request.getParameter("address").getBytes("iso-8859-1"),"utf-8");
			}
			if(request.getParameter("company")!=null){
				company = new String(request.getParameter("company").getBytes("iso-8859-1"),"utf-8");
			}
			if(request.getParameter("city")!=null){
				city = new String(request.getParameter("city").getBytes("iso-8859-1"),"utf-8");
			}	
			if(request.getParameter("createUser")!=null){
				createUser = new String(request.getParameter("createUser").getBytes("iso-8859-1"),"utf-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		if(name != null && !name.isEmpty()){
			map.put("name", "%" + name + "%");
		}
		if(address != null && !address.isEmpty()){
			map.put("address", "%"+address+"%");
		}
		if(company != null && !company.isEmpty()){
			map.put("company", "%" + company + "%");
		}
		if(city != null && !city.isEmpty()){
			map.put("city", "%" + city + "%");
		}
		if(createUser != null && !createUser.isEmpty()){
			map.put("createUser", createUser);
		}
		if(phone != null && !phone.isEmpty()){
			map.put("phone", phone);
		}
		if(isVip != null && !isVip.isEmpty()){
			map.put("isVip", isVip);
		}
		if(createTime != null && !createTime.isEmpty()){
			map.put("createTime", createTime+"%");
		}
		if(startTime != null && !startTime.isEmpty()){
			map.put("startTime", startTime+" 00:00:00");
		}
		if(endTime != null && !endTime.isEmpty()){
			map.put("endTime", endTime+" 23:59:59");
		}
		
		List<Customers> docs = cust.queryAllList(map);
		for(int i=0;i<docs.size();i++){
			Customers docc = docs.get(i);
			if(!"".equals(docc.getIsVip())&&docc.getIsVip()!=null) {
				if("0".equals(docc.getIsVip())) {
					docc.setIsVip("重点合作");
				}else if("1".equals(docc.getIsVip())) {
					docc.setIsVip("一般合作");
				}else if("2".equals(docc.getIsVip())) {
					docc.setIsVip("重点潜在");
				}else if("3".equals(docc.getIsVip())) {
					docc.setIsVip("一般潜在");
				}else if("4".equals(docc.getIsVip())) {
					docc.setIsVip("其他");
				}
			}
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String date = simpleDateFormat.format(new Date());
		if(docs!=null&&docs.size()>0){
			modelMap.put(NormalExcelConstants.FILE_NAME,"客户"+date);
			modelMap.put(NormalExcelConstants.CLASS,Customers.class);
			modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("客户列表","导出信息"));
			modelMap.put(NormalExcelConstants.DATA_LIST,docs);
			return NormalExcelConstants.JEECG_EXCEL_VIEW;
		}else{
			return null;
		}
	}
}
