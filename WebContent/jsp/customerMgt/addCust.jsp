<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/jsp/main/resource.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>增加客户</title>
<style type="text/css">
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
<script src=<%= request.getContextPath() + "/js/customerMgt/selectCust.js" %> type="text/javascript"></script>
</head>
<body class="easyui-layout">
	<div id="addInfo" data-options="region:'center'" >
		<div align="center">
			<form action="" id="addCust">
				<table style="border-collapse: separate;border-spacing:10px;">
					<tr>
						<td>
							<label>姓名：</label>
						</td>
						<td>
							<input id="name" name="name" class="easyui-textbox"  />
						</td>
						<td>
							<label>电话：</label>
						</td>
						<td>
							<input id="phone" name="phone" class="easyui-textbox"/>
						</td>
					</tr>
					<tr>
						<td>
							<label>省市：</label>
						</td>
						<td>
							<input id="city" name="city" class="easyui-textbox" />
						</td>
						<td>
							<label>住址：</label>
						</td>
						<td>
							<input id="address" name="address" class="easyui-textbox"/>
						</td>
					</tr>
					<tr>
						<td>
							<label>公司：</label>
						</td>
						<td>
							<input id="company" name="company" class="easyui-textbox" />
						</td>
					</tr>
					<tr>
						<td>
							<label>客户类型：</label>
						</td>
						<td>
							<select id="isVip" class="easyui-combobox" name="isVip" style="width:200px;">  
   								 <option value="0" selected="selected">重点合作</option>  
    								<option value="1">一般合作</option>  
    								<option value="2">重点潜在</option>  
    								<option value="3">一般潜在</option>  
    								<option value="4">其他</option>  
							</select>   
						</td>
					</tr>
					<tr>
						<td>
							<label>备注：</label>
						</td>
						<td colspan="3" >
							<textarea id="remarks" name="remarks" rows="4" cols="58"></textarea>
						</td>
					</tr>
				</table>
			</form>
			<p class="tishi"></p>
		</div>
		<div align="center" >
			<input type="button" style="margin-left:30px" class="orangeBtn" id="ok_button" value="增加" />
		</div>
	</div>
</body>
</html>