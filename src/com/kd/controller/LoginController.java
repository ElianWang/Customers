package com.kd.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kd.entity.User;
import com.kd.util.AppTools;

@Controller
@RequestMapping("/")
public class LoginController {
	
	private Logger logger = Logger.getLogger(LoginController.class);

	@RequestMapping("/login")
	public String login(Model model){
		return "login";
	}
	
	@RequestMapping("/doLogin")
	public String doLogin(Model model,HttpServletRequest request){
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		UsernamePasswordToken token = new UsernamePasswordToken(username,password); 
		Subject currentUser = SecurityUtils.getSubject(); 
		
		if(username == null || username.isEmpty()){
			model.addAttribute("errorMsg", "账号不能为空！");
			return "login";
		}
		if(password == null || password.isEmpty()){
			model.addAttribute("errorMsg", "密码不能为空！");
			return "login";
		}
		try {
			currentUser.login(token); 
		} catch (UnknownAccountException ue) {
			ue.printStackTrace();
			model.addAttribute("errorMsg", "账号不存在！");
			logger.error("登陆失败",ue);
			return "login";
		} catch (AuthenticationException ae) {
			ae.printStackTrace();
			model.addAttribute("errorMsg", "账号或密码不正确！");
			logger.error("登陆失败",ae);
			return "login";
		}
		return "redirect:/index";
	}
	
	@RequestMapping("/index")
	public String index(Model model,HttpServletRequest request){
		//保存登陆人员信息
		User currentUser = AppTools.getCurrentUser();
		request.getSession().setAttribute("user", currentUser.getName());
		model.addAttribute("user",currentUser);
		if (currentUser == null) {
			model.addAttribute("errorMsg", "当前登录人员为空！");
		}
		model.addAttribute("date",new Date());
		return "/main/index";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request){
		HttpSession session = request.getSession();
		session.removeAttribute(session.getId());
//		ClientManager.getInstance().removeClinet(session.getId());
		User user = AppTools.getCurrentUser();
		session.invalidate();
		return "redirect:login";
	}
}
