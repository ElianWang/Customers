<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/jsp/main/resource.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
.easyui-textbox{
	border:1px solid lightgray;
}
body{padding:10px}
</style>
<script src=<%= request.getContextPath() + "/js/user/resetPass.js" %> type="text/javascript"></script>
<script src=<%= request.getContextPath() + "/plugin/md5/md5.js" %> type="text/javascript"></script>
</head>
<body>
	<div align="center" style="margin-left:auto;margin-right:auto;width:100%">
		<form id="addForm" action="saveVehicle">
			<input id="account" name="account" type="hidden" value="${account}" />
			<table style="border-collapse: separate;border-spacing:10px;">
				<tr>
					<td align="right">
						<label for="password1">新密码</label>
					</td>
					<td>
						<input type="password" id="password1" name="password1" class="easyui-textbox"/>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label for="password2">确认密码</label>
					</td>
					<td>
						<input type="password" id="password2" name="password2" class="easyui-textbox"/>
						<input type="hidden" id="password" name="password"/>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div align="center">
		<input id="saveBtn" class="orangeBtn" value="重置" type="button"/>
		<input id="closeBtn" class="orangeBtn" value="关闭" type="button"/>
	</div>
</body>
</html>