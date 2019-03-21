package com.kd.controller;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kd.entity.Params;
import com.kd.entity.Progress;
import com.kd.entity.User;
import com.kd.service.ParamService;
import com.kd.service.ProgressService;
import com.kd.service.UserService;
import com.kd.util.Pagination;
import com.kd.util.ResourceUtil;

@Controller
@RequestMapping("/progress")
public class ProgressController {
	
	private Logger logger = Logger.getLogger(UserController.class);
	
	@Resource
	private ParamService paramService;
	@Resource
	private ProgressService progressService;
	
	
	@RequestMapping("/createProgress")
	public String progress(Model model){
		String nextCode = progressService.getNextCode();
		model.addAttribute("code",nextCode);
		model.addAttribute("date",new Date());
		return "/progress/createProgress";
	}
	
	@RequestMapping("/queryOrgno")
	@ResponseBody
	public JSONArray queryOrgno(){
		JSONArray params = new JSONArray();
		List<Params> orgNo = paramService.getByType("01");//单位
		if (orgNo != null) {
			for(Params param:orgNo){
				JSONObject obj = new JSONObject();
				obj.put("code", param.getCode());
				obj.put("name",param.getName());
				params.add(obj);
			}
		}
		return params;
	}
	
	@RequestMapping("/saveProgress")
	@ResponseBody
	public JSONObject saveProgress(@ModelAttribute Progress progress,HttpServletRequest request){
		JSONObject result = new JSONObject();
		result.put("success", "true");
		try{
			progressService.save(progress);
		}catch(Exception e){
			e.printStackTrace();
			result.put("success", "false");
			result.put("errorMsg", "业务流程失败：" + e.getMessage());
			logger.error("业务流程添加失败",e);
		}
		return result;
	}
	
	
	@RequestMapping("docList")
	public String docList(){
		return "progress/queryProgress";
	}
	
	@RequestMapping("/getProList")
	@ResponseBody
	public JSONObject getProList(HttpServletRequest request){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
		String title = request.getParameter("title");
		
		if(title != null && !title.isEmpty()){
			map.put("title", "%" + title + "%");
		}
		Pagination<Progress> pagination = progressService.queryList(map, pageIndex, pageSize);
		Map<String,Object> companies = ResourceUtil.params.get("01");
		JSONArray rows = new JSONArray();
		for(Progress d:pagination.getItems()){
			JSONObject obj = new JSONObject();
			obj.put("id", d.getId());
			obj.put("code", d.getCode());
			obj.put("company",companies.get(d.getCompany()));
			obj.put("publishOrg", d.getPublishOrg());
			obj.put("applyYear", d.getApplyYear());
			obj.put("title", d.getTitle());
			obj.put("content", d.getContent());
			obj.put("remarks", d.getRemarks());
			obj.put("operateUser", d.getOperateUser());
			obj.put("operateTime", d.getOperateTime());
			if(d.getOperateTime() != null){
				obj.put("operateTime", sdf.format(d.getOperateTime()));
			}
			rows.add(obj);
		}
		result.put("rows", rows);
		result.put("total", pagination.getRowsCount());
		return result;
	}
	
	@RequestMapping("/editPro")
	public String editPro(HttpServletRequest request,Model model){
		String id = request.getParameter("id");
		Progress progress = progressService.getById(id);
		if (progress != null) {
			model.addAttribute("progress",progress);
		}
		return "progress/editPro";
	}
	
	@RequestMapping("/updatePro")
	@ResponseBody
	public JSONObject updatePro(@ModelAttribute Progress progress,HttpServletRequest request){
		JSONObject result = new JSONObject();
		result.put("success", "true");
		try {
			progressService.update(progress);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", "false");
			result.put("errorMsg", "更新流程失败，原因时：" + e.getMessage());
			logger.error("更新" + progress.getTitle() + "的流程失败", e);
		}
		return result;
	}
	
	@RequestMapping("/deletePro")
	@ResponseBody
	public JSONObject deletePro(HttpServletRequest request){
		JSONObject result = new JSONObject();
		result.put("success", "true");
		String ids = request.getParameter("ids");
		try {
			String[] array = ids.split(",");
			for(int i = 0;i < array.length;i++){
				progressService.delete(array[i]);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", "false");
			result.put("errorMsg", "删除参数失败，原因时：" + e.getMessage());
			logger.error("删除档案失败", e);
		}
		return result;
	}
	
	/**
	 * 导出excel
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping("/exportExcel")
	public String exportExcel(Progress progress,HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) {
		//Map<String, String[]> parameterMap = request.getParameterMap();
		Map<String, Object> map = new HashMap<String, Object>();
		String title = "";
		String ids = request.getParameter("ids");
		String type = request.getParameter("type");
		if(type.equals("2")){
			map.put("ids", ids);
		}
		
		try {
			if(request.getParameter("title")!=null){
				title = new String(request.getParameter("title").getBytes("iso-8859-1"),"utf-8");
			}
			
			if(title != null && !title.isEmpty()){
				map.put("title", "%" + title + "%");
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		List<Progress> pro = progressService.queryAllList(map);
		for(int i=0;i<pro.size();i++){
			Progress pros = pro.get(i);
			Map<String, Object> companyMap = ResourceUtil.params.get("01");//所属单位缓存
			String code = pros.getCompany();
			if((code!=null&&!("".equals(code)))){
				//Params params = paramService.getByCode(code);
				pros.setCompany((String)companyMap.get(code));
			}
			
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String date = simpleDateFormat.format(new Date());
		if(pro!=null&&pro.size()>0){
			modelMap.put(NormalExcelConstants.FILE_NAME,"业务流程"+date);
			modelMap.put(NormalExcelConstants.CLASS,Progress.class);
			modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("业务流程","导出信息"));
			modelMap.put(NormalExcelConstants.DATA_LIST,pro);
			
			return NormalExcelConstants.JEECG_EXCEL_VIEW;
		}else{
			return null;
		}
	}
	
}
