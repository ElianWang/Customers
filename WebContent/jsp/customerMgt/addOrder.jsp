<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/jsp/main/resource.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>录入订单信息</title>
<script src=<%= request.getContextPath() + "/js/customerMgt/showCust.js" %> type="text/javascript"></script>
</head>
<body class="easyui-layout">
	<input type="hidden" id="cusId" value="${id }"/>
	<div id="addOrder" data-options="region:'center'" >
		<div align="center">
			<form action="" id="orderForm">
				<table style="border-collapse: separate;border-spacing:10px;">
					<tr>
						<td><label>产品名称</label></td>
						<td><input class="easyui-textbox" name="product" id="product"/></td>
						<td><label>产品类别</label></td>
						<td><input class="easyui-textbox" name="type" id="type"/></td>
					</tr>
					<tr>
						<td><label>面积</label></td>
						<td><input class="easyui-textbox" name="area" id="area"/></td>
						<td><label>单价</label></td>
						<td><input class="easyui-textbox" name="price" id="price"/></td>
					</tr>
					<tr>
						<td><label>总金额</label></td>
						<td><input class="easyui-textbox" name="totalAmount" id="totalAmount"/></td>
					</tr>
					<tr>
						<td><label>备注</label></td>
						<td><textarea rows="3" cols="" name="remarks" id="remarks"></textarea>  </td>
					</tr>
				</table>
			</form>
		</div>
		<div align="center" >
			<input type="button" style="margin-left:30px" class="orangeBtn" id="OrderBtn" value="提交" onclick="submitOrder()"  />
		</div>
	</div>
</body>
</html>