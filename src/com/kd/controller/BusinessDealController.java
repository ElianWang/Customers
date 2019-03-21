package com.kd.controller;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
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
import com.kd.entity.Business;
import com.kd.service.BusinessDealService;
import com.kd.util.Pagination;
import com.kd.util.ResourceUtil;



@Controller
@RequestMapping("/businessDeal")
public class BusinessDealController {
	private Logger logger = Logger.getLogger(BusinessDealController.class);
	SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Autowired
	private BusinessDealService businessDealService;
	
	//创建主任务
	@RequestMapping("/createBusiness")
	public ModelAndView createBusiness(Model model){
		model.addAttribute("date",new Date());
		return new  ModelAndView("/businessDeal/createBusiness");
	}
	@RequestMapping("/saveBusinessDeal")
	@ResponseBody
	public JSONObject saveBusinessDeal(@ModelAttribute("business") Business business,HttpServletRequest request){
		JSONObject result = new JSONObject();
		result.put("success", "true");
		try {
			business.setType("01");
			business.setBeginTime(s.format(new Date()));
			business.setUpdateTime(s.format(new Date()));
			businessDealService.saveBusiness(business);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", "false");
			result.put("errorMsg", "创建业务失败，原因时：" + e.getMessage());
			logger.error("创建业务" + business.getCode() + "失败", e);
		}
		return result;
	}
	
	//创建子任务
	@RequestMapping("/addChild")
	public ModelAndView addChild(Model model,HttpServletRequest request){
		String id = request.getParameter("id");
		Business business = businessDealService.getById(id);
		model.addAttribute("business",business);
		model.addAttribute("date",new Date());
		return new  ModelAndView("/businessDeal/addChild");
	}
	@RequestMapping("/saveChild")
	@ResponseBody
	public JSONObject saveChild(@ModelAttribute("business") Business business,HttpServletRequest request){
		JSONObject result = new JSONObject();
		result.put("success", "true");
		try {
			String code = business.getCode();
			business.setUpdateTime(s.format(new Date()));
			//修改
			Business parentBusiness = businessDealService.getByCode(code);
			//判断子任务的操作时间和主任务的操作时间，如果子任务的操作时间大于等于主任务的时间就将主任务修改为子任务，子任务变更为主任务
			if(business.getOperateTime() == null){
				business.setType("02");
			}else if(parentBusiness.getOperateTime() == null){
				business.setType("01");
				parentBusiness.setType("02");
				businessDealService.updateBusiness(parentBusiness);
			}else {
				if(business.getOperateTime().compareTo(parentBusiness.getOperateTime()) >=0){
					business.setType("01");
					parentBusiness.setType("02");
					businessDealService.updateBusiness(parentBusiness);
				}else{
					business.setType("02");
				}
			}
			businessDealService.saveBusiness(business);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", "false");
			result.put("errorMsg", "创建子业务失败，原因时：" + e.getMessage());
			logger.error("创建子业务" + business.getCode() + "失败", e);
		}
		return result;
	}
	//编辑子任务
	@RequestMapping("/editChild")
	public ModelAndView editChild(Model model,HttpServletRequest request){
		String id = request.getParameter("id");
		Business business = businessDealService.getById(id);
		model.addAttribute("business",business);
		model.addAttribute("date",new Date());
		return new  ModelAndView("/businessDeal/editChild");
	}
	//提交编辑子任务
	@RequestMapping("/updateChild")
	@ResponseBody
	public JSONObject updateChild(@ModelAttribute("business") Business business,HttpServletRequest request){
		JSONObject result = new JSONObject();
		result.put("success", "true");
		try {
			String code = business.getCode();
			business.setUpdateTime(s.format(new Date()));
			//修改
			Business parentBusiness = businessDealService.getByCode(code);
			//判断子任务的操作时间和主任务的操作时间，如果子任务的操作时间大于等于主任务的时间就将主任务修改为子任务，子任务变更为主任务
			if(business.getOperateTime() == null){
				business.setType("02");
			}else if(parentBusiness.getOperateTime() == null){
				business.setType("01");
				parentBusiness.setType("02");
				businessDealService.updateBusiness(parentBusiness);
			}else {
				if(business.getOperateTime().compareTo(parentBusiness.getOperateTime()) >=0){
					business.setType("01");
					parentBusiness.setType("02");
					businessDealService.updateBusiness(parentBusiness);
				}else{
					business.setType("02");
				}
			}
			businessDealService.updateBusiness(business);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", "false");
			result.put("errorMsg", "修改子业务失败，原因是：" + e.getMessage());
			logger.error("修改子业务" + business.getCode() + "失败", e);
		}
		return result;
	}
	
	//编辑主任务
	@RequestMapping("/editMain")
	public String editMain(HttpServletRequest request,Model model){
		String id = request.getParameter("id");
		Business business = businessDealService.getById(id);
		model.addAttribute("business",business);
		String operateStatusName = (String) ResourceUtil.params.get("02").get(business.getOperateStatus());
		model.addAttribute("operateStatusName",operateStatusName);
		return "businessDeal/editMain";
	}
	@RequestMapping("/updateMain")
	@ResponseBody
	public JSONObject updateMain(@ModelAttribute("business") Business business,HttpServletRequest request){
		JSONObject result = new JSONObject();
		result.put("success", "true");
		try {
			business.setUpdateTime(s.format(new Date()));
			businessDealService.updateBusiness(business);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", "false");
			result.put("errorMsg", "更新业务失败，原因时：" + e.getMessage());
			logger.error("更新业务" + business.getCode() + "失败", e);
		}
		return result;
	}
	
	@RequestMapping("/getBusinessId")
	@ResponseBody
	public String getBusinessId(HttpServletRequest request){
		String code = request.getParameter("id");
		int num =2; //某一大类业务相关的业务的编号位数
		String nextCode = businessDealService.getNextCode(code,num) ;
		return nextCode;
	}
	
	@RequestMapping("/businessList")
	public String businessList(){
		return "businessDeal/businessList";
	}
	
	@RequestMapping("/getBusinessList")
	@ResponseBody
	public JSONObject getBusinessList(HttpServletRequest request){
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
		String bigBusiness = request.getParameter("bigBusiness");
		String smallBusiness = request.getParameter("smallBusiness");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String company = request.getParameter("company");
		String serviceTarget = request.getParameter("serviceTarget");
		String operateType = request.getParameter("operateType");
		String operateStatus = request.getParameter("operateStatus");
		
		String updated = request.getParameter("updated");
		
		if(bigBusiness != null && !bigBusiness.isEmpty()){
			map.put("bigBusiness", bigBusiness);
		}
		if(smallBusiness != null && !smallBusiness.isEmpty()){
			map.put("smallBusiness", smallBusiness);
		}
		if(serviceTarget != null && !serviceTarget.isEmpty()){
			map.put("serviceTarget", "%" + serviceTarget + "%");
		}
		if(operateType != null && !operateType.isEmpty()){
			map.put("operateType", operateType);
		}
		if(operateStatus != null && !operateStatus.isEmpty()){
			map.put("operateStatus", operateStatus);
		}
		if(company != null && !company.isEmpty()){
			map.put("company", company);
		}
		if(startTime != null && !startTime.isEmpty()){
			try {
				map.put("startTime", sdf.parse(startTime));
			} catch (Exception e) {
			}
		}
		if(endTime != null && !endTime.isEmpty()){
			try {
				map.put("endTime", sdf.parse(endTime));
			} catch (Exception e) {
			}
		}
		Pagination<Business> pagination = businessDealService.getBusinessList(map, pageIndex, pageSize,updated);
		Map<String, Object> companyMap = ResourceUtil.params.get("01");//所属单位缓存
		Map<String, Object> operateStatusMap = ResourceUtil.params.get("02");//办理状态缓存
		Map<String, Object> operateTypeMap = ResourceUtil.params.get("04");//操作类型缓存
		Map<String, Object> bigBusinessMap = ResourceUtil.params.get("03");//业务大类缓存
		Map<String, Object> smallBusinessMap = ResourceUtil.params.get("05");//业务小类缓存
		JSONArray rows = new JSONArray();
		if (updated == null || updated.isEmpty() || updated.equals("00")) {
			for(Business b:pagination.getItems()){
				JSONObject obj = new JSONObject();
				obj.put("id", b.getId());
				obj.put("code", b.getCode());
				//此处的主任务在数据库中不一定是主任务，所有传到前台时以有没有父节点来判断，并添加type来标记
				if(b.getParentCode() == null || b.getParentCode().isEmpty()){
					obj.put("company", companyMap.get(b.getCompany()));
					obj.put("bigBusiness", bigBusinessMap.get(b.getBigBusiness()));
					obj.put("smallBusiness", smallBusinessMap.get(b.getSmallBusiness()));
					obj.put("serviceTarget", b.getServiceTarget());
					obj.put("contact", b.getContact());
					obj.put("state", "closed");
					obj.put("type", "01");
				}else{
					obj.put("type", "02");
					obj.put("_parentId", b.getParentCode());
				}
				obj.put("applyMan", b.getApplyMan());
				obj.put("operateStatus", operateStatusMap.get(b.getOperateStatus()));
				if(b.getOperateTime() != null){
					obj.put("operateTime", sdf.format(b.getOperateTime()));
				}
				obj.put("operateType", operateTypeMap.get(b.getOperateType()));
				obj.put("operateUser", b.getOperateUser());
				obj.put("businessContent", b.getBusinessContent());
				obj.put("remarks", b.getRemarks());
				rows.add(obj);
			}
		}else{
			for(Business b:pagination.getItems()){
				JSONObject obj = new JSONObject();
				obj.put("id", b.getId());
				obj.put("type", "01");
				obj.put("code", b.getCode());
				obj.put("company", companyMap.get(b.getCompany()));
				obj.put("bigBusiness", bigBusinessMap.get(b.getBigBusiness()));
				obj.put("smallBusiness", smallBusinessMap.get(b.getSmallBusiness()));
				obj.put("serviceTarget", b.getServiceTarget());
				obj.put("contact", b.getContact());
				obj.put("state", "closed");
				obj.put("applyMan", b.getApplyMan());
				obj.put("operateStatus", operateStatusMap.get(b.getOperateStatus()));
				if(b.getOperateTime() != null){
					obj.put("operateTime", sdf.format(b.getOperateTime()));
				}
				obj.put("operateType", operateTypeMap.get(b.getOperateType()));
				obj.put("operateUser", b.getOperateUser());
				obj.put("businessContent", b.getBusinessContent());
				obj.put("remarks", b.getRemarks());
				if(b.getParentCode() != null){
					obj.put("_parentId", b.getParentCode());
				}
				rows.add(obj);
			}
		}
		result.put("rows",rows);
		result.put("total",pagination.getRowsCount());
		return result;
	}
	
	@RequestMapping("/deleteBusiness")
	@ResponseBody
	public JSONObject deleteBusiness(HttpServletRequest request){
		JSONObject result = new JSONObject();
		result.put("success", "true");
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		try {
			businessDealService.deleteBusiness(id,type);
		} catch (Exception e) {
			result.put("success", "false");
			result.put("errorMsg", e.getMessage());
		}
		return result;
	}
	@RequestMapping("/compareTime")
	@ResponseBody
	public JSONObject compareTime(HttpServletRequest request){
		JSONObject result = new JSONObject();
		result.put("success", "true");
		String cId = request.getParameter("cId");
		String cTime = request.getParameter("cTime");
		Business business = businessDealService.getById(cId);  //
		String pCode = business.getParentCode();
		Business parent = businessDealService.getByCode(pCode);
		Date pDate = parent.getOperateTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date cDate = new Date();
		try {
			sdf.setLenient(false);
			cDate = sdf.parse(cTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int r = pDate.compareTo(cDate);
		if(r>0){
			result.put("success", "false");
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
	public String exportExcel(Business business,HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, Object> map = new HashMap<String, Object>();
		String bigBusiness = request.getParameter("bigBusiness");
		String smallBusiness = request.getParameter("smallBusiness");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String company = request.getParameter("company");
		String serviceTarget = "";
		try {
			serviceTarget = new String(request.getParameter("serviceTarget").getBytes("iso-8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String operateType = request.getParameter("operateType");
		String operateStatus = request.getParameter("operateStatus");
		
		String updated = request.getParameter("updated");
		
		if(bigBusiness != null && !bigBusiness.isEmpty()){
			map.put("bigBusiness", bigBusiness);
		}
		if(smallBusiness != null && !smallBusiness.isEmpty()){
			map.put("smallBusiness", smallBusiness);
		}
		if(serviceTarget != null && !serviceTarget.isEmpty()){
			map.put("serviceTarget", "%" + serviceTarget + "%");
		}
		if(operateType != null && !operateType.isEmpty()){
			map.put("operateType", operateType);
		}
		if(operateStatus != null && !operateStatus.isEmpty()){
			map.put("operateStatus", operateStatus);
		}
		if(company != null && !company.isEmpty()){
			map.put("company", company);
		}
		if(startTime != null && !startTime.isEmpty()){
			try {
				map.put("startTime", sdf.parse(startTime));
			} catch (Exception e) {
			}
		}
		if(endTime != null && !endTime.isEmpty()){
			try {
				map.put("endTime", sdf.parse(endTime));
			} catch (Exception e) {
			}
		}
		
		List<Business> bus = businessDealService.queryAllList(map,updated);
		for(int i=0;i<bus.size();i++){
			Business buss = bus.get(i);
			Map<String, Object> companyMap = ResourceUtil.params.get("01");//所属单位缓存
			Map<String, Object> operateStatusMap = ResourceUtil.params.get("02");//办理状态缓存
			Map<String, Object> operateTypeMap = ResourceUtil.params.get("04");//操作类型缓存
			Map<String, Object> bigBusinessMap = ResourceUtil.params.get("03");//业务大类缓存
			Map<String, Object> smallBusinessMap = ResourceUtil.params.get("05");//业务小类缓存
			String code = buss.getCompany();
			String bigNo = buss.getBigBusiness();
			String smallNo = buss.getSmallBusiness();
			String czlx = buss.getOperateType();
			String blzt = buss.getOperateStatus();
			if((code!=null&&!("".equals(code)))){
				buss.setCompany((String) companyMap.get(code));
			}
			if(bigNo!=null&&!("".equals(bigNo))){
				buss.setBigBusiness((String)bigBusinessMap.get(bigNo));
			}
			if(smallNo!=null&&!("".equals(smallNo))){
				buss.setSmallBusiness((String)smallBusinessMap.get(smallNo));
			}
			buss.setOperateStatus((String) operateStatusMap.get(blzt));
			buss.setOperateType((String)operateTypeMap.get(czlx));
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String date = simpleDateFormat.format(new Date());
		if(bus!=null&&bus.size()>0){
			modelMap.put(NormalExcelConstants.FILE_NAME,"业务办理"+date);
			modelMap.put(NormalExcelConstants.CLASS,Business.class);
			modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("业务办理","导出信息"));
			modelMap.put(NormalExcelConstants.DATA_LIST,bus);
			
			return NormalExcelConstants.JEECG_EXCEL_VIEW;
		}else{
			return null;
		}
	}
	

	@RequestMapping("/getBusiness")
	@ResponseBody
	public Business getBusiness(HttpServletRequest request){
		String id = request.getParameter("id");
		Business business = businessDealService.getById(id);
		Map<String, Object> companyMap = ResourceUtil.params.get("01");//所属单位缓存
		Map<String, Object> operateStatusMap = ResourceUtil.params.get("02");//办理状态缓存
		Map<String, Object> operateTypeMap = ResourceUtil.params.get("04");//操作类型缓存
		Map<String, Object> bigBusinessMap = ResourceUtil.params.get("03");//业务大类缓存
		Map<String, Object> smallBusinessMap = ResourceUtil.params.get("05");//业务小类缓存
		if (business.getType().equals("02")) {
			business.setCode(business.getParentCode());
		}
		business.setCompany((String) companyMap.get(business.getCompany()));
		business.setOperateStatus((String) operateStatusMap.get(business.getOperateStatus()));
		business.setOperateType((String) operateTypeMap.get(business.getOperateType()));
		business.setBigBusiness((String) bigBusinessMap.get(business.getBigBusiness()));
		business.setSmallBusiness((String) smallBusinessMap.get(business.getSmallBusiness()));
		return business;
	}
	

}
