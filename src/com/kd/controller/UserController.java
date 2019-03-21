package com.kd.controller;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kd.entity.User;
import com.kd.service.UserService;
import com.kd.util.Pagination;

@Controller
@RequestMapping("/user")
public class UserController {

	private Logger logger = Logger.getLogger(UserController.class);
	@Resource
	private UserService userService;
	
	@RequestMapping("/userList")
	public String userList(){
		return "/user/userList";
	}
	
	@RequestMapping("/getList")
	@ResponseBody
	public JSONObject getList(HttpServletRequest request){
		JSONObject result = new JSONObject();
		String pageIndexStr = request.getParameter("page");
		String pageSizeStr = request.getParameter("rows");
		int pageIndex = 1;
		int pageSize = 20;
		if (pageIndexStr != null && !pageIndexStr.isEmpty()) {
			pageIndex = Integer.parseInt(pageIndexStr);
		}
		if (pageSizeStr != null && !pageSizeStr.isEmpty()) {
			pageSize = Integer.parseInt(pageSizeStr);;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		String account = request.getParameter("account");
		String name = request.getParameter("name");
		if (account != null && !account.isEmpty()) {
			params.put("account", account);
		}
		if (name != null && !name.isEmpty()) {
			params.put("name", name);
		}
		
		Pagination<User> pagination = userService.queryList(params, pageIndex, pageSize);
		JSONArray array = new JSONArray();
		if (pagination.getItems() != null) {
			for(User user:pagination.getItems()){
				JSONObject obj = new JSONObject();
				obj.put("account", user.getAccount());
				obj.put("name", user.getName());
				array.add(obj);
			}
		}
		result.put("total", pagination.getRowsCount());
		result.put("rows", array);
		return result;
	}
	
	@RequestMapping("addUser")
	public String addUser(Model model){
		return "/user/addUser";
	}
	
	@RequestMapping("/saveUser")
	@ResponseBody
	public JSONObject saveUser(User user,HttpServletRequest request){
		JSONObject result = new JSONObject();
		result.put("success", "true");
		try {
			userService.saveUser(user);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", "false");
			result.put("errorMsg", "新建用户失败：" + e.getMessage());
			logger.error("创建用户失败",e);
		}
		
		return result;
	}
	
	@RequestMapping("editUser")
	public String editUser(HttpServletRequest request,Model model){
		String account = request.getParameter("account");
		if (account != null && !account.isEmpty()) {
			User user = userService.getByAccount(account);
			model.addAttribute("user",user);
		}
		return "/user/editUser";
	}
	
	@RequestMapping("/updateUser")
	@ResponseBody
	public JSONObject updateUser(HttpServletRequest request){
		JSONObject result = new JSONObject();
		result.put("success", "true");
		String account = request.getParameter("account");
		String name = request.getParameter("name");
		if (account == null || account.isEmpty()) {
			result.put("success", "false");
			result.put("errorMsg", "账号为空，编辑用户失败！");
		}
		User user = userService.getByAccount(account);
		if (user == null) {
			result.put("success", "false");
			result.put("errorMsg", "用户为空，编辑用户失败！");
		}
		user.setName(name);
		try {
			userService.updateUser(user);
		} catch (Exception e) {
			result.put("success", "false");
			result.put("errorMsg", "编辑用户失败：" + e.getMessage());
			logger.error("更新用户失败",e);
		}
		return result;
	}
	
	@RequestMapping("/checkRepeat")
	@ResponseBody
	public JSONObject checkRepeat(HttpServletRequest request){
		JSONObject result = new JSONObject();
		result.put("repeat", "false");
		String account = request.getParameter("account");
		if (account == null || account.isEmpty()) {
			return result;
		}
		User user = userService.getByAccount(account);
		if (user != null) {
			result.put("repeat", "true");
		}
		return result;
	}
	
	@RequestMapping("/getUserCombo")
	@ResponseBody
	public JSONArray getUserCombo(){
		JSONArray users = new JSONArray();
		List<User> list = userService.getAllUsers();
		if (list != null) {
			for(User user:list){
				JSONObject obj = new JSONObject();
				obj.put("id", user.getId());
				obj.put("name",user.getAccount());
				users.add(obj);
			}
		}
		return users;
	}
	
	@RequestMapping("/resetPass")
	public String resetPass(HttpServletRequest request,Model model){
		String account = request.getParameter("account");
		model.addAttribute("account", account);
		return "/user/resetPass";
	}
	
	@RequestMapping("/resetPassword")
	@ResponseBody
	public JSONObject resetPassword(HttpServletRequest request){
		JSONObject result = new JSONObject();
		result.put("success", "true");
		
		String account = request.getParameter("account");
		String newPassword = request.getParameter("password");
		try {
			User user = userService.getByAccount(account);
			user.setPassword(newPassword);
			userService.updateUser(user);
		} catch (Exception e) {
			logger.error("更新用户失败",e);
		}
		return result;
	}
}
