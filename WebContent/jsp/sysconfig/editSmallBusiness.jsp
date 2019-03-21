<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<script src=<%= request.getContextPath() + "/js/sysconfig/editSmallBusiness.js" %> type="text/javascript"></script>
</head>
<body>
	<div align="center" style="margin-left:auto;margin-right:auto;width:100%">
		<form id="addForm" action="saveVehicle">
			<input id="paramType" name="type" type="hidden" value="${paramType}" />
			<input id="id" name="id" type="hidden" value="${param1.id}" />
			<table style="border-collapse: separate;border-spacing:10px;">
				<tr>
					<td align="right">
						<label for="code">编码</label>
					</td>
					<td>
						<label>${param1.code}</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label for="status">大类别</label>
					</td>
					<td>
						<input id="parentCode" name="parentCode" class="easyui-combobox" value="${param1.parentCode}"/>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label for="name">新参数名称:</label>
					</td>
					<td>
						<input id="name" name="name" value class="easyui-textbox"/>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div align="center">
		<input id="saveBtn" class="orangeBtn" value="保存" type="button"/>
	</div>
</body>
</html>