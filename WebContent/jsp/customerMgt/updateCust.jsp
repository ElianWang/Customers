<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/jsp/main/resource.jsp" %>
<script type="text/javascript" src=<%=request.getContextPath() +"/js/customerMgt/selectCust.js" %> ></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑客户信息</title>
</head>
<body class="easyui-layout">
	<div id="updateInfo" data-options="region:'center'" >
		<div align="center">
			<form action="" id="updateCust">
				<table style="border-collapse: separate;border-spacing:10px;">
					<tr>
						<td>
							<label>姓名：</label>
						</td>
						<td>
							<input id="name" name="name" class="easyui-textbox" value="${customer.name }" />
						</td>
						<td>
							<label>电话：</label>
						</td>
						<td>
							<input id="phone" name="phone" class="easyui-textbox" value="${customer.phone }"/>
						</td>
					</tr>
					<tr>
						<td>
							<label>省市：</label>
						</td>
						<td>
							<input id="city" name="city" class="easyui-textbox" value="${customer.city }"/>
						</td>
						<td>
							<label>住址：</label>
						</td>
						<td>
							<input id="address" name="address" class="easyui-textbox" value="${customer.address }"/>
						</td>
					</tr>
					<tr>
						<td>
							<label>公司：</label>
						</td>
						<td>
							<input id="company" name="company" class="easyui-textbox" value="${customer.company }"/>
						</td>
					</tr>
					<tr>
						<td>
							<label>客户类型：</label>
						</td>
						<td>
							<select id="isVip" class="easyui-combobox" name="isVip" style="width:200px;">  
   								<c:if test="${customer.isVip == 0 }">
									<option value="0" selected="selected">重点合作</option>  
    									<option value="1">一般合作</option>  
    									<option value="2">重点潜在</option>  
    									<option value="3">一般潜在</option>  
    									<option value="4">其他</option>  
								</c:if>
   								<c:if test="${customer.isVip == 1 }">
									<option value="0" >重点合作</option>  
    									<option value="1" selected="selected">一般合作</option>  
    									<option value="2">重点潜在</option>  
    									<option value="3">一般潜在</option>  
    									<option value="4">其他</option>  
								</c:if>
								<c:if test="${customer.isVip == 2 }">
									<option value="0" >重点合作</option>  
    									<option value="1">一般合作</option>  
    									<option value="2" selected="selected">重点潜在</option>  
    									<option value="3">一般潜在</option>  
    									<option value="4">其他</option>  
								</c:if>
								<c:if test="${customer.isVip == 3 }">
									<option value="0" >重点合作</option>  
    									<option value="1">一般合作</option>  
    									<option value="2">重点潜在</option>  
    									<option value="3" selected="selected">一般潜在</option>  
    									<option value="4">其他</option>  
								</c:if>
								<c:if test="${customer.isVip == 4 }">
									<option value="0" >重点合作</option>  
    									<option value="1">一般合作</option>  
    									<option value="2">重点潜在</option>  
    									<option value="3">一般潜在</option>  
    									<option value="4" selected="selected">其他</option>  
								</c:if>
							</select>   
						</td>
					</tr>
					<tr>
						<td>
							<label>备注：</label>
						</td>
						<td colspan="3" >
							<textarea id="remarks" name="remarks" rows="4" cols="58" >${customer.remarks }</textarea>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div align="center" >
			<input type="button" style="margin-left:30px" class="orangeBtn" id="submit_button" value="提交" onclick="editCust(${customer.id})"  />
		</div>
	</div>
</body>
</html>