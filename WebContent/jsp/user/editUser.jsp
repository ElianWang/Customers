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
<script src=<%= request.getContextPath() + "/js/user/editUser.js" %> type="text/javascript"></script>
</head>
<body>
	<div align="center" style="margin-left:auto;margin-right:auto;width:100%">
		<form id="addForm" action="saveVehicle">
			<table style="border-collapse: separate;border-spacing:10px;">
				<tr>
					<td align="right">
						<label style="margin-left:50px" for="account">账号</label>
					</td>
					<td>
						<input id="account" name="account" value="${user.account}" class="easyui-textbox" readonly="readonly"/>
					</td>
					<td align="right">
						<label for="name">真实姓名</label>
					</td>
					<td>
						<input id="name" name="name" value="${user.name}" class="easyui-textbox"/>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div align="center">
		<input id="saveBtn" class="orangeBtn" value="更新" type="button"/>
		<input id="closeBtn" class="orangeBtn" value="关闭" type="button"/>
	</div>
</body>
</html>