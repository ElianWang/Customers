<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
	.left-td{
		padding-right:100px;
	}
body{padding:10px} 
</style>
<script src=<%= request.getContextPath() + "/js/progress/editPro.js" %> type="text/javascript"></script>
</head>
<body>
	<div id="center" data-options="region:'center'">
		<div align="center">
		<form id="addForm" modelAttribute="param" commandName="param" action="">
			<input id="id" name="id" type="hidden" value="${progress.id}"/>
			<input id="code" name="code" type="hidden" value="${progress.code}"/>
			<table style="border-collapse: separate;border-spacing:10px;">
				<tr>
					<td align="right">
						<label for="code">流程编号:</label>
					</td>
					<td class="left-td">
						<label>${progress.code}</label>
					</td>
					<td align="right">
						<label style="margin-left:20px" for="unit">适用单位:</label>
					</td>
					<td>
						<input id="company" name="company" value="${progress.company}" class="easyui-textbox"/>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label for="publishOrg">发布机构:</label>
					</td>
					<td class="left-td">
						<input id="publishOrg" name="publishOrg" value="${progress.publishOrg}" class="easyui-textbox"/>
					</td>
					<td align="right">
						<label style="margin-left:20px" for="applyYear">适用年度:</label>
					</td>
					<td>
						<input id="applyYear" name="applyYear" value="${progress.applyYear}" class="easyui-textbox"/>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label for="title">流程标题:</label>
					</td>
					<td>
						<input id="title" name="title" value="${progress.title}" class="easyui-textbox" style="width:257px"/>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label for="content">流程内容:</label>
					</td>
					<td colspan="3">
						<textarea id="content" name="content" rows="7" cols="50">${progress.content}</textarea>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label for="remarks">流程备注:</label>
					</td>
					<td colspan="3">
						<textarea id="remarks" name="remarks" rows="4" cols="50">${progress.remarks}</textarea>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label for="operateUser">操作人员:</label>
					</td>
					<td class="left-td">
						<input id="operateUser" name="operateUser" value="${progress.operateUser}" class="easyui-textbox"/>					</td>
					<td align="right">
						<label style="margin-left:20px" for="operateTime">操作时间:</label>
					</td>
					<td>
						<input id="operateTime" name="operateTime" value="<fmt:formatDate value='${progress.operateTime}' pattern='yyyy-MM-dd'/>" class="easyui-datebox"/>
					</td>
				</tr>
			</table>
		</form>
		</div>
	</div>
	<div align="center">
		<input id="saveBtn" class="orangeBtn" value="保存" type="button"/>
	</div>
</body>
</html>