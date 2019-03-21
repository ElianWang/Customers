<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/jsp/main/resource.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登陆界面</title>
<link rel="stylesheet" href=<%= request.getContextPath() + "/css/home/login.css" %> type="text/css">
<script src=<%=request.getContextPath() +"/js/login.js" %> type="text/javascript"></script>
</head>
<body>
	<div id="errorMsg">
		<div class="login_form" align='center' valign='center'>
			<div class="sysName">
				<div style="font-size:21px;color:#ff9933;margin-top:9px">工作管理系统</div>
			</div>
			<div class="inputArea">
				<form id="loginForm" action="doLogin" method="post">
					<table style="border-collapse: separate;border-spacing:25px;">
						<tr>
							<td>
								<label for="username">用户名:</label>
							</td>
							<td>
								<input id="username" name="username" value="${userName}"/>
							</td>
						</tr>
						<tr>
							<td>
								<label for="password">密&nbsp码:</label>
							</td>
							<td>	
								<input id="password" name="password" type="password" value="${password}"/>
							</td>
						</tr>			
					</table>
					<div align="center" style="margin-left:auto;margin-right:auto;margin-top:-10px">
						<input id="login" type="submit" value="登录" class="orangeBtn" style="margin-right:20px;"/>
						<input id="reset" type="reset" value="重置" class="orangeBtn"/>
					</div>
				</form>
			</div>
			<span id="errorMsg" style="color:red;margin-top:10px" align="center">${errorMsg}</span>
		</div>
	</div>
</body>
</html>