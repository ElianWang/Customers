package com.kd.util;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.kd.entity.User;

public class AppTools {

	/**
	 * 获取远程客户端的IP地址
	 * @param request
	 * @return
	 */
	public static String getIp() {
		HttpServletRequest request = ContextHolderUtils.getRequest();
		String ip = request.getHeader("X-Forwarded-For");
		if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个ip值，第一个ip才是真实ip
			int index = ip.indexOf(",");
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            return ip;
        }
        return request.getRemoteAddr();
    }
	
	/**
	 * 获取当前登陆用户
	 * @return
	 */
	public static User getCurrentUser(){
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession(false);
		User currentUser = (User) session.getAttribute("currentUser");
		return currentUser;
	}
	
	public static  void deleteFile(File file) {  
        if (file.exists()) {//判断文件是否存在   
             if (file.isFile()) {//判断是否是文件   
                 file.delete();//删除文件    
             }else if(file.isDirectory()){//否则如果它是一个目录   
                 File[] files = file.listFiles();//声明目录下所有的文件 files[];  
                 for(int i = 0;i < files.length;i ++){//遍历目录下所有的文件   
                     deleteFile(files[i]);//把每个文件用这个方法进行迭代   
                 }  
                 file.delete();//删除文件夹   
             }  
        }else{  
            System.out.println("所删除的文件不存在");  
        }  
     }
	
	/**
	 * 将字符串的最后几位截取下来，并装成数字加1
	 * @param str 需要加1的字符串
	 * @param lastNum 需要截取的位数
	 * @return
	 */
	public static String getNextByLast(String str,int lastNum){
		String temp1 = str.substring(0,str.length() - lastNum);
		String temp2 = str.substring(str.length() - lastNum,str.length());
		int num = Integer.parseInt(temp2);
		num++;
		String temp3 = "";
		for(int i = 0;i < lastNum - (num + "").length();i++){
			temp3 += "0";
		}
		temp3 += num;
		return temp1 + temp3;
	}
	
}
