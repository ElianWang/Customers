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
body{padding:10px}
</style>
<script src=<%= request.getContextPath() + "/js/document/editDoc.js" %> type="text/javascript"></script>
</head>
<body>
	<div align="center" style="margin-left:auto;margin-right:auto;width:100%">
		<form id="addForm" modelAttribute="param" commandName="param" action="savePDoc">
			<input id="id" name="id" type="hidden" value="${doc.id}"/>
			<input id="code" name="code" type="hidden" value="${doc.code}"/>
			<table style="border-collapse: separate;border-spacing:10px;">
				<tr>
					<td align="right">
						<label for="code">档案编号:</label>
					</td>
					<td>
						<label>${doc.code}</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label for="fullName">姓名:</label>
					</td>
					<td>
						<input id="fullName" name="fullName" value="${doc.fullName}" class="easyui-textbox"/>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label for="idNum">身份证号:</label>
					</td>
					<td>
						<input id="idNum" name="idNum" value="${doc.idNum}" class="easyui-textbox"/>
					</td>
				</tr>
				<%-- <tr>
					<td align="right">
						<label for="onwork">在岗情况:</label>
					</td>
					<td>
						<input id="onwork" name="onwork" value="${doc.onwork}" class="easyui-textbox"/>
					</td>
				</tr> --%>
				<tr>
					<td align="right">
						<label for="company">所属单位:</label>
					</td>
					<td>
						<input id="company" name="company" value="${doc.company}" class="easyui-combobox"/>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label for="residenceType">户口性质:</label>
					</td>
					<td>
						<input id="residenceType" name="residenceType" value="${doc.residenceType}" class="easyui-combobox"/>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label for="personStatus">人员状态:</label>
					</td>
					<td>
						<input id="personStatus" name="personStatus" value="${doc.personStatus}" class="easyui-combobox"/>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label for="rank">职级:</label>
					</td>
					<td>
						<input id="rank" name="rank" value="${doc.rank}" class="easyui-combobox"/>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label for="docDeposit">档案存放情况:</label>
					</td>
					<td>
						<input id="docDeposit" name="docDeposit" value="${doc.docDeposit}" class="easyui-combobox"/>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label for="unitColhouse">单位集体户:</label>
					</td>
					<td>
						<input id="unitColhouse" name="unitColhouse" value="${doc.unitColhouse}" class="easyui-combobox"/>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label for="partyRelation">党员关系:</label>
					</td>
					<td>
						<input id="partyRelation" name="partyRelation" value="${doc.partyRelation}" class="easyui-combobox"/>
					</td>
				</tr>
				<%-- <tr>
					<td align="right">
						<label for="organization">现存机构:</label>
					</td>
					<td>
						<input id="organization" name="organization" value="${doc.organization}" class="easyui-textbox"/>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label for="intoTime">转入时间:</label>
					</td>
					<td>
						<input id="intoTime" name="intoTime" value="<fmt:formatDate value='${doc.intoTime}' pattern='yyyy-MM-dd'/>" class="easyui-datebox"/>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label for="v">原档存处:</label>
					</td>
					<td>
						<input id="docPost" name="docPost" value="${doc.docPost}" class="easyui-textbox"/>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label for="outTime">转出时间:</label>
					</td>
					<td>
						<input id="outTime" name="outTime" value="<fmt:formatDate value='${doc.outTime}' pattern='yyyy-MM-dd'/>" class="easyui-datebox"/>
					</td>
				</tr> --%>
				<tr>
					<td align="right">
						<label for="remarks">备注:</label>
					</td>
					<td>
						<textarea id="remarks" name="remarks" rows="3" cols="30">${doc.remarks}</textarea>
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