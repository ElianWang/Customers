package com.kd.controller;

import java.io.UnsupportedEncodingException;
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kd.entity.Doc;
import com.kd.entity.Params.paramEnum;
import com.kd.service.DocumentService;
import com.kd.util.Pagination;
import com.kd.util.ResourceUtil;

@Controller
@RequestMapping("/document")
public class DocumentController {
	private Logger logger = Logger.getLogger(DocumentController.class);
	
	@Autowired
	private DocumentService documentService;
	
	@RequestMapping("docList")
	public String docList(){
		return "document/docList";
	}
	
	@RequestMapping("/getDocList")
	@ResponseBody
	public JSONObject getDocList(HttpServletRequest request){
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
		String fullName = request.getParameter("fullName");
		String idNum = request.getParameter("idNum");
		String company = request.getParameter("company");
		String organization = request.getParameter("organization");
		if(fullName != null && !fullName.isEmpty()){
			map.put("fullName", "%" + fullName + "%");
		}
		if(idNum != null && !idNum.isEmpty()){
			map.put("idNum", idNum);
		}
		if(company != null && !company.isEmpty()){
			map.put("company", company);
		}
		if(organization != null && !organization.isEmpty()){
			map.put("organization", "%" + organization + "%");
		}
		Pagination<Doc> pagination = documentService.queryList(map, pageIndex, pageSize);
		JSONArray rows = new JSONArray();
		Map<String,Object> companies = ResourceUtil.params.get(paramEnum.docCompany.getCode());
		Map<String,Object> residenceType =  ResourceUtil.params.get(paramEnum.residenceType.getCode());
		Map<String,Object> personStatus = ResourceUtil.params.get(paramEnum.personStatus.getCode());
		Map<String,Object> rank = ResourceUtil.params.get(paramEnum.rank.getCode());
		Map<String,Object> docDeposit = ResourceUtil.params.get(paramEnum.docDeposit.getCode());
		Map<String,Object> unitColhouse = ResourceUtil.params.get(paramEnum.unitColhouse.getCode());
		Map<String,Object> partyRelation = ResourceUtil.params.get(paramEnum.partyRelation.getCode());
		for(Doc d:pagination.getItems()){
			JSONObject obj = new JSONObject();
			obj.put("id", d.getId());
			obj.put("code", d.getCode());
			obj.put("fullName", d.getFullName());
			obj.put("idNum", d.getIdNum());
			obj.put("company", companies.get(d.getCompany()));
			obj.put("residenceType", residenceType.get(d.getResidenceType()));
			obj.put("personStatus", personStatus.get(d.getPersonStatus()));
			obj.put("rank", rank.get(d.getRank()));
			obj.put("docDeposit",docDeposit.get(d.getDocDeposit()));
			obj.put("unitColhouse",unitColhouse.get(d.getUnitColhouse()));
			obj.put("partyRelation",partyRelation.get(d.getPartyRelation()));
			//obj.put("onWork", d.getOnwork());
			/*obj.put("organization", d.getOrganization());
			if(d.getIntoTime() != null){
				obj.put("intoTime", sdf.format(d.getIntoTime()));
			}
			obj.put("docPost", d.getDocPost());
			if(d.getOutTime() != null){
				obj.put("outTime", sdf.format(d.getOutTime()));
			}*/
			obj.put("remarks", d.getRemarks());
			rows.add(obj);
		}
		result.put("rows", rows);
		result.put("total", pagination.getRowsCount());
		return result;
	}
	
	@RequestMapping("/addDoc")
	public String addDoc(Model model){
		String nextCode = documentService.getNextCode();
		model.addAttribute("code",nextCode);
		return "document/addDoc";
	}
	
	@RequestMapping("/saveDoc")
	@ResponseBody
	public JSONObject saveDoc(@ModelAttribute("doc") Doc doc,HttpServletRequest request){
		JSONObject result = new JSONObject();
		result.put("success", "true");
		try {
			documentService.save(doc);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", "false");
			result.put("errorMsg", "创建档案失败，原因时：" + e.getMessage());
			logger.error("创建" + doc.getFullName() + "的档案失败", e);
		}
		return result;
	}
	
	@RequestMapping("/editDoc")
	public String editDoc(HttpServletRequest request,Model model){
		String id = request.getParameter("id");
		Doc doc = documentService.getById(id);
		if (doc != null) {
			model.addAttribute("doc",doc);
		}
		return "document/editDoc";
	}
	
	@RequestMapping("/updateDoc")
	@ResponseBody
	public JSONObject updateDoc(@ModelAttribute("doc") Doc doc,HttpServletRequest request){
		JSONObject result = new JSONObject();
		result.put("success", "true");
		try {
			documentService.update(doc);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", "false");
			result.put("errorMsg", "更新档案失败，原因时：" + e.getMessage());
			logger.error("更新" + doc.getFullName() + "的档案失败", e);
		}
		return result;
	}
	
	@RequestMapping("/deleteDoc")
	@ResponseBody
	public JSONObject deleteParam(HttpServletRequest request){
		JSONObject result = new JSONObject();
		result.put("success", "true");
		String ids = request.getParameter("ids");
		try {
			String[] array = ids.split(",");
			for(int i = 0;i < array.length;i++){
				documentService.delete(array[i]);
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
	@RequestMapping("/exportXls")
	public String exportXls(Doc doc,HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) {
		//Map<String, String[]> parameterMap = request.getParameterMap();
		Map<String, Object> map = new HashMap<String, Object>();
		String fullName = "";
		String idNum = "";
		String organization = "";
		String ids = request.getParameter("ids");
		String type = request.getParameter("type");
		String company = request.getParameter("company");
		if(type.equals("2")){
			map.put("ids", ids);
		}
		
		try {
			//中文参数
			if(request.getParameter("fullName")!=null){
				fullName = new String(request.getParameter("fullName").getBytes("iso-8859-1"),"utf-8");
			}
			if(request.getParameter("idNum")!=null){
				idNum = new String(request.getParameter("idNum").getBytes("iso-8859-1"),"utf-8");
			}
			if(request.getParameter("organization")!=null){
				organization = new String(request.getParameter("organization").getBytes("iso-8859-1"),"utf-8");
			}	
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		if(fullName != null && !fullName.isEmpty()){
			map.put("fullName", "%" + fullName + "%");
		}
		if(idNum != null && !idNum.isEmpty()){
			map.put("idNum", idNum);
		}
		if(organization != null && !organization.isEmpty()){
			map.put("organization", "%" + organization + "%");
		}
		if(company != null && !company.isEmpty()){
			map.put("company", "%" + company + "%");
		}
		
		List<Doc> docs = documentService.queryAllList(map);
		for(int i=0;i<docs.size();i++){
			Doc docc = docs.get(i);
			Map<String, Object> companyMap = ResourceUtil.params.get("0607");//所属单位缓存
			Map<String,Object> residenceType =  ResourceUtil.params.get(paramEnum.residenceType.getCode());
			Map<String,Object> personStatus = ResourceUtil.params.get(paramEnum.personStatus.getCode());
			Map<String,Object> rank = ResourceUtil.params.get(paramEnum.rank.getCode());
			Map<String,Object> docDeposit = ResourceUtil.params.get(paramEnum.docDeposit.getCode());
			Map<String,Object> unitColhouse = ResourceUtil.params.get(paramEnum.unitColhouse.getCode());
			Map<String,Object> partyRelation = ResourceUtil.params.get(paramEnum.partyRelation.getCode());
			String code = docc.getCompany();
			String residenceTypecode = docc.getResidenceType();
			String personStatuscode = docc.getPersonStatus();
			String rankCode = docc.getRank();
			String docDepositcode = docc.getDocDeposit();
			String unitColhousecode = docc.getUnitColhouse();
			String partyRelationcode = docc.getPartyRelation();
			if((code!=null&&!("".equals(code)))){
				docc.setCompany((String)companyMap.get(code));
			}
			if((residenceTypecode!=null&&!("".equals(residenceTypecode)))){
				docc.setResidenceType((String)residenceType.get(residenceTypecode));
			}
			if((personStatuscode!=null&&!("".equals(personStatuscode)))){
				docc.setPersonStatus((String)personStatus.get(personStatuscode));
			}
			if((rankCode!=null&&!("".equals(rankCode)))){
				docc.setRank((String)rank.get(rankCode));
			}
			if((docDepositcode!=null&&!("".equals(docDepositcode)))){
				docc.setDocDeposit((String)docDeposit.get(docDepositcode));
			}
			if((unitColhousecode!=null&&!("".equals(unitColhousecode)))){
				docc.setUnitColhouse((String)unitColhouse.get(unitColhousecode));
			}
			if((partyRelationcode!=null&&!("".equals(partyRelationcode)))){
				docc.setPartyRelation((String)partyRelation.get(partyRelationcode));
			}
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String date = simpleDateFormat.format(new Date());
		if(docs!=null&&docs.size()>0){
			modelMap.put(NormalExcelConstants.FILE_NAME,"档案"+date);
			modelMap.put(NormalExcelConstants.CLASS,Doc.class);
			modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("档案列表","导出信息"));
			modelMap.put(NormalExcelConstants.DATA_LIST,docs);
			
			return NormalExcelConstants.JEECG_EXCEL_VIEW;
		}else{
			return null;
		}
	}
	
}
