<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/jsp/main/resource.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
body{padding:10px}
</style>
<script src=<%= request.getContextPath() + "/js/document/addDoc.js" %> type="text/javascript"></script>
</head>
<body>
	<div style="font-weight:100">
		<span>档案管理</span>
		<span>></span>
		<span>新录入</span>
	</div>
	<div align="center" style="margin-left:auto;margin-right:auto;width:100%">
		<form id="addForm" modelAttribute="param" commandName="param" action="savePDoc">
			<table style="border-collapse: separate;border-spacing:10px;">
				<tr>
					<td align="right">
						<label for="code">序号:</label>
					</td>
					<td>
						<input id="code" name="code" readonly="readonly" value="${code}" class="easyui-textbox"/>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label for="fullName">姓名:</label>
					</td>
					<td>
						<input id="fullName" name="fullName" class="easyui-textbox"/>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label for="idNum">身份证号:</label>
					</td>
					<td>
						<input id="idNum" name="idNum" class="easyui-textbox"/>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label for="residenceType">户口性质:</label>
					</td>
					<td>
						<input id="residenceType" name="residenceType" class="easyui-combobox"/>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label for="personStatus">人员状态:</label>
					</td>
					<td>
						<input id="personStatus" name="personStatus" class="easyui-combobox"/>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label for="company">所属单位:</label>
					</td>
					<td>
						<input id="company" name="company" class="easyui-combobox"/>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label for="rank">职级:</label>
					</td>
					<td>
						<input id="rank" name="rank" class="easyui-combobox"/>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label for="docDeposit">档案存放情况:</label>
					</td>
					<td>
						<input id="docDeposit" name="docDeposit" class="easyui-combobox"/>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label for="unitColhouse">单位集体户:</label>
					</td>
					<td>
						<input id="unitColhouse" name="unitColhouse" class="easyui-combobox"/>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label for="partyRelation">党员关系:</label>
					</td>
					<td>
						<input id="partyRelation" name="partyRelation" class="easyui-combobox"/>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label for="remarks">备注:</label>
					</td>
					<td>
						<textarea id="remarks" name="remarks" rows="2" cols="30"></textarea>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div align="center">
		<input id="saveBtn" class="orangeBtn" value="保存" type="button"/>
		<input id="cancleBtn" class="orangeBtn" value="取消" type="button"/>
	</div>
</body>
</html>