package com.kd.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kd.entity.Params;
import com.kd.service.ParamService;
import com.kd.util.Pagination;
import com.kd.util.ResourceUtil;

@Controller
@RequestMapping("/params")
public class ParamsController {
	
	private Logger logger = Logger.getLogger(ParamsController.class);
	
	@Resource
	private ParamService paramService;

	@RequestMapping("/paramList")
	public String paramList(HttpServletRequest request,Model model){
		String paramType = request.getParameter("type");
		model.addAttribute("paramType", paramType);
		if(paramType.equals("04")){
			return "sysconfig/operateTypeList";
		}else if(paramType.equals("05")){
			return "sysconfig/smallBusinessList";
		}
		return "sysconfig/paramList";
	}
	
	@RequestMapping("/queryParamList")
	@ResponseBody
	public JSONObject queryParamList(HttpServletRequest request){
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
		Pagination<Params> pagination = paramService.queryList(request, pageIndex, pageSize);
		JSONArray array = new JSONArray();
		if (pagination.getItems() != null) {
			for(Params param:pagination.getItems()){
				JSONObject obj = new JSONObject();
				obj.put("id", param.getId());
				obj.put("code", param.getCode());
				obj.put("name", param.getName());
				obj.put("parentCode", param.getParentCode());
				array.add(obj);
			}
		}
		result.put("rows", array);
		result.put("total", pagination.getRowsCount());
		return result;
	}
	
	@RequestMapping("/addParam")
	public String add(HttpServletRequest request,Model model){
		String paramType = request.getParameter("paramType");
		String parentCode = request.getParameter("parenCode");
		model.addAttribute("paramType", paramType);
		model.addAttribute("parentCode",parentCode);
		int num = 2;
		String nextCode = paramService.getNextCode(paramType, parentCode, num);
		model.addAttribute("code",nextCode);
		if(paramType.equals("04")){
			return "sysconfig/addOperateType";
		}else if(paramType.equals("05")){
			return "sysconfig/addSmallBusiness";
		}
		return "sysconfig/addParam";
	}
	
	@RequestMapping("/saveParam")
	@ResponseBody
	public JSONObject saveParam(@ModelAttribute("param") Params param,HttpServletRequest request){
		JSONObject result = new JSONObject();
		result.put("success", "true");
		try {
			paramService.saveParam(param);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", "false");
			result.put("errorMsg", "创建参数失败，原因时：" + e.getMessage());
			logger.error("创建参数" + param.getName() + "失败", e);
		}
		return result;
	}
	
	@RequestMapping("/editParam")
	public String edit(HttpServletRequest request,Model model){
		String paramType = request.getParameter("paramType");
		model.addAttribute("paramType", paramType);
		String id = request.getParameter("id");
		Params param = paramService.getByIdAndType(id, paramType);
		if (param != null) {
			model.addAttribute("param1",param);
		}
		if(paramType.equals("04")){
			return "sysconfig/editOperateType";
		}else if(paramType.equals("05")){
			return "sysconfig/editSmallBusiness";
		}
		return "sysconfig/editParam";
	}
	
	@RequestMapping("/updateParam")
	@ResponseBody
	public JSONObject updateParam(Params param,HttpServletRequest request){
		JSONObject result = new JSONObject();
		result.put("success", "true");
		Params oldParam = paramService.getById(param.getId());
		oldParam.setName(param.getName());
		try {
			paramService.updateParam(oldParam);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", "false");
			result.put("errorMsg", "更新参数失败，原因时：" + e.getMessage());
			logger.error("更新参数" + param.getName() + "失败", e);
		}
		return result;
	}
	
	@RequestMapping("/deleteParam")
	@ResponseBody
	public JSONObject deleteParam(HttpServletRequest request){
		JSONObject result = new JSONObject();
		result.put("success", "true");
		String name = request.getParameter("name");
		String id = request.getParameter("id");
		String paramType = request.getParameter("paramType");
		try {
			paramService.deleteParam(id, paramType);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", "false");
			result.put("errorMsg", "删除参数失败，原因时：" + e.getMessage());
			logger.error("删除参数" + name + "失败", e);
		}
		return result;
	}
	
	@RequestMapping("/getParamCombo")
	@ResponseBody
	public JSONArray getParamCombo(HttpServletRequest request){
		JSONArray data = new JSONArray();
		String paramType = request.getParameter("paramType");
		if (paramType == null || paramType.isEmpty()) {
			return data;
		}
		Map<String, Object> map = ResourceUtil.params.get(paramType);
		for(String key:map.keySet()){
			JSONObject obj = new JSONObject();
			obj.put("id", key);
			String name = "";
			
			obj.put("name",name);
			data.add(obj);
		}
		return data;
	}
	
	@RequestMapping("/getStatusByOperateType")
	@ResponseBody
	public String getStatusByOperateType(HttpServletRequest request){
		String typeCode = request.getParameter("code");
		Params param = paramService.getByCode(typeCode);
		Params status = paramService.getByCode(param.getParentCode());
		if (status == null) {
			return "";
		}
		return status.getName();
	}
	
	@RequestMapping("/getOperateStatus")
	@ResponseBody
	public JSONArray getOperateStatus(){
		JSONArray array = new JSONArray();
		List<Params> params = paramService.getByType("02");
		for(Params p:params){
			JSONObject obj = new JSONObject();
			obj.put("id", p.getCode());
			obj.put("name", p.getName());
			array.add(obj);
		}
		return array;
	}

	//根据code找到业务小类
	@RequestMapping("/getDetailType")
	@ResponseBody
	public JSONArray getDetailType(HttpServletRequest request){
		JSONArray array = new JSONArray();
		String bigTypeCode = request.getParameter("id");
		List<Params> list = paramService.getByPidAndType(bigTypeCode, "05");
		if(list!=null){
			for(Params l:list){
				JSONObject o = new JSONObject();
				o.put("id", l.getCode());
				o.put("name", l.getName());
				array.add(o);
			}
		}
		return array;
	}
	//根据操作方式找到对应的办理状态
	@RequestMapping("/getDealStatue")
	@ResponseBody
	public JSONObject getDealStatue(HttpServletRequest request){
		JSONObject data = new JSONObject();
		String optCode = request.getParameter("id");
		Params p = paramService.getParentByChildCode(optCode);
		if (p != null) {
			data.put("code",p.getCode());
			data.put("operateStatus", p.getName());
		}
		return data;
	}

	//获取大类别下拉框数据
	@RequestMapping("/getBigBusiness")
	@ResponseBody
	public JSONArray getBigBusiness(){
		JSONArray array = new JSONArray();
		List<Params> params = paramService.getByType("03");
		for(Params p:params){
			JSONObject obj = new JSONObject();
			obj.put("id", p.getCode());
			obj.put("name", p.getName());
			array.add(obj);
		}
		return array;
	}
	//获取所属单位下拉框数据
	@RequestMapping("/getCompany")
	@ResponseBody
	public JSONArray getCompany(){
		JSONArray array = new JSONArray();
		List<Params> params = paramService.getByType("01");
		for(Params p:params){
			JSONObject obj = new JSONObject();
			obj.put("id", p.getCode());
			obj.put("name", p.getName());
			array.add(obj);
		}
		return array;
	}
	
	//获取操作类型下拉框数据
	@RequestMapping("/getOperateType")
	@ResponseBody
	public JSONArray getOperateType(){
		JSONArray array = new JSONArray();
		List<Params> params = paramService.getByType("04");
		for(Params p:params){
			JSONObject obj = new JSONObject();
			obj.put("id", p.getCode());
			obj.put("name", p.getName());
			array.add(obj);
		}
		return array;
	}
	
	@RequestMapping("/getParamsByType")
	@ResponseBody
	public JSONArray getParamsByType(HttpServletRequest request){
		JSONArray array = new JSONArray();
		String type = request.getParameter("type");
		List<Params> params = paramService.getByType(type);
		for(Params p:params){
			JSONObject obj = new JSONObject();
			obj.put("id", p.getCode());
			obj.put("name", p.getName());
			array.add(obj);
		}
		return array;
	}
	
	@RequestMapping("/docParams")
	public String getDocParams(HttpServletRequest request) {
		return "sysconfig/docParams";
	}
	
}
