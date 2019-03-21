<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="/jsp/main/resource.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script src=<%= request.getContextPath() + "/js/progress/createProgress.js" %> type="text/javascript"></script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>业务流程</title>
</head>
<style>
	body{
		font-size:12px;
	}
	.left-td{
		padding-right:100px;
	}
	.combo-panel{
		border:1px solid grey;
	}
</style>
<body>
	<div align="center" style="margin-right:auto;margin-right:auto;width:100%;">
		<input id="roles" type="hidden" value="${roles}" />
		
		<form id="addForm_pro" modelAttribute="progress" commandName="progress" action="saveProgress">
			<table style="border-collapse: separate;border-spacing:10px;">
				<tr>
					<td align="right">
						<label for="progressId">流程编号:</label>
					</td>
					<td>
						<input id=code name="code" value="${code}" readonly="readonly" class="easyui-textbox"/>
					</td>
					<td align="right">
						<label style="margin-left:50px" for="unit">适用单位:</label>
					</td>
					<td>
						<input id="company" name="company" class="easyui-combobox"/>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label for="fbjg">发布机构:</label>
					</td>
					<td>
						<input id="publishOrg" name="publishOrg" class="easyui-textbox"/>
					</td>
					<td align="right">
						<label style="margin-left:50px" for="synd">适用年度:</label>
					</td>
					<td>
						<input id="applyYear" name="applyYear" class="easyui-textbox"/>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label for="lcbt">流程标题:</label>
					</td>
					<td colspan="3">
						<input id="title" name="title" type="easyui-textbox" style="width:457px"/>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label for="lcnr">流程内容(手填):</label>
					</td>
					<td colspan="3">
						<textarea id="content" name="content" rows="7" cols="67" ></textarea>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label for="lcsm">备注说明(手填):</label>
					</td>
					<td colspan="3">
						<textarea id="remarks" name="remarks" rows="4" cols="67"></textarea>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label for="czry">操作人员:</label>
					</td>
					<td>
						<input id="operateUser" name="operateUser" class="easyui-textbox"/>
					</td>
					<td align="right">
						<label style="margin-left:50px" for="czsj">操作时间:</label>
					</td>
					<td>
						<input id="operateTime" name="operateTime" class="easyui-datebox"
							value="<fmt:formatDate value='${date}' pattern='yyyy-MM-dd'/>"/>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div align="center">
		<input id="saveProgress" class="orangeBtn" value="确认" type="button"/>
		<input id="closeProgress" class="orangeBtn" value="取消" type="button"/>
	</div>
</body>
</html>