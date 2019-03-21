package com.kd.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kd.entity.backup;
import com.kd.service.BackupService;
import com.kd.util.Pagination;

@Controller
@RequestMapping("/util")
public class UtilController {
	
	@Resource
	private BackupService backupService;
	
	private static String bakurl = "D:\\bak";
	static {
		ResourceBundle resource = ResourceBundle.getBundle("configSystem");
		try {
			bakurl = resource.getString("bakurl");
		} catch (Exception e) {
			System.out.println("获取备份地址失败！");
		}
		System.out.println("备份地址：" + bakurl);
	}
	
	@RequestMapping("/add")
	public String addbak(){
		return "/util/addbak";
	}
	
	@RequestMapping("/bakList")
	public String userList(){
		return "/util/backup";
	}
	
	@RequestMapping("/getlist")
	@ResponseBody
	public JSONObject getList(HttpServletRequest request){
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
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
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
		Pagination<backup> pagination = backupService.queryList(map, pageIndex, pageSize);
		JSONArray rows = new JSONArray();
		if (pagination.getItems() != null) {
			for(backup bak:pagination.getItems()){
				JSONObject obj = new JSONObject();
				obj.put("date", sdf.format(bak.getDate()));
				obj.put("url", bak.getUrl());
				obj.put("remark", bak.getRemark());
				obj.put("id", bak.getId());
				rows.add(obj);
			}
		}
		result.put("total", pagination.getRowsCount());
		result.put("rows", rows);
		return result;
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public JSONObject delete(HttpServletRequest request){
		JSONObject result = new JSONObject();
		result.put("success", "true");
		String id = request.getParameter("id");
		backup backup = backupService.getById(id);
		String url = backup.getUrl();
		try {
			backupService.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", "false");
		}
		File file = new File(url);
        if (file.isFile()) {
        	try{
        		file.delete();
        		}catch (Exception e) {
        			e.printStackTrace();
        			result.put("success", "false");
        		}
        }
		return result;
	}
	
	@RequestMapping("/newback")
	@ResponseBody
	public JSONObject backupdb(HttpServletRequest request){
		JSONObject json = new JSONObject();
		json.put("success", "true");	
		String remark = request.getParameter("remark");
		String db = "D:\\HR.accdb";
		FileInputStream input = null;
		FileOutputStream output = null;
		String fileurl = "";
		Date now = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");
		String date = df.format(now);
		if(bakurl.endsWith(File.separator)){ 
			fileurl=bakurl+date+"bak.accdb"; 
		} 
		else{ 
			fileurl=bakurl+File.separator+date+".accdb"; 
		} 
		try {
			File docurl = new File(bakurl);
			if(!docurl.exists()){
				docurl.mkdir();
			}
			File bak=new File(fileurl); 
			if(!bak.exists()){
				bak.createNewFile();
			}
			input = new FileInputStream(db);
			output = new FileOutputStream(bak);
			byte[] b = new byte[1024]; 
			int len; 
			while((len = input.read(b)) != -1){
				output.write(b, 0, len); 
			}
			output.flush(); 
		} catch (Exception e) {
			e.printStackTrace();
			json.put("success", "false");
			return json;
		}finally{
			try {
				if(output != null){
					output.close();
				}
				if(input != null){
					input.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				json.put("success", "false");
				return json;
			} 
		}
		backup bakdoc = new backup();
		bakdoc.setDate(now);
		bakdoc.setUrl(fileurl);
		bakdoc.setRemark(remark);
		try {
			backupService.saveBackup(bakdoc);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("success", "false");
			return json;
		}
		return json;
	}
	
	@RequestMapping("/import")
	@ResponseBody
	public JSONObject importdb(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		json.put("success", "true");
		String id = request.getParameter("id");
		backup backup = backupService.getById(id);
		String url = backup.getUrl();
		String db = "D:\\HR.accdb";
		FileInputStream input = null;
		FileOutputStream output = null;
//		int pageIndex = 1;
//		int pageSize = 20;
//		List<backup> ls1 = new ArrayList<backup>();
//		Map<String, Object> map = new HashMap<String, Object>();
//		Pagination<backup> pagination = backupService.getbaklist(map,
//				pageIndex, pageSize);
//		if (pagination.getItems() != null) {
//			for(backup b:pagination.getItems()){
//				b.setId(null);
//				ls1.add(b);
//			}
//		}
		try {
			File newdb = new File(db);
			File bak = new File(url);
			input = new FileInputStream(bak);
			output = new FileOutputStream(newdb);
			
//			byte[] b = new byte[1024];
//			int len;
//			while ((len = input.read(b)) != -1) {
//				output.write(b, 0, len);
//			}
//			output.flush();
			
			FileChannel in,out;
			in = input.getChannel();
			out = output.getChannel();
			out.transferFrom(in, 0, in.size());
			in.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			json.put("success", "false");
			return json;
		} finally {
			try {
				if (output != null) {
					output.close();
				}
				if (input != null) {
					input.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				json.put("success", "false");
				return json;
			}
		}
//		Pagination<backup> pagination1 = backupService.getbaklist(map,
//				pageIndex, pageSize);
//		List<backup> ls2 = pagination1.getItems();
//		try {
//			if (!ls2.isEmpty()) {
//				backupService.deleteall(ls2);
//			}
//			backup bakdoc = new backup();
//			if (!ls1.isEmpty()) {
//				for(int i=0;i<ls1.size();i++){
//					bakdoc.setDate(ls1.get(i).getDate());
//					bakdoc.setUrl(ls1.get(i).getUrl());
//					bakdoc.setRemark(ls1.get(i).getRemark());
//					try {
//						backupService.saveBackup(bakdoc);
//					} catch (Exception e) {
//						e.printStackTrace();
//						json.put("success", "false");
//						return json;
//					}
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			json.put("success", "false");
//			return json;
//		}
		return json;
	}

}
