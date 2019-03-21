<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/jsp/main/resource.jsp" %>
<script type="text/javascript" src=<%= request.getContextPath() + "/js/customerMgt/showCust.js" %>></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>显示客户信息</title>
<style>
#showCustForm label{
	width:100px;
}

.menu{
	height:28px;
	border-left:#cccccc solid 1px; 
	width:280px;
	}

.menu li{
	float:left;
	width:70px;
	text-align:center;
}

.menu li.off{
	background:#3385ff;
	color: White;
	font-weight:bold;
}
</style>
</head>
<body class="easyui-layout">
	<div id="showCustInfo" data-options="region:'center'" >
		<input id="customerId" type="hidden" value="${customer.id}"/>
		<div align="center">
			<form action="" id="showCustForm">
				<table style="border-collapse: separate;border-spacing:10px;">
					<tr>
						<td style="width:100px;">
							<label>姓名：</label>
						</td>
						<td style="width:100px;">
							<input id="name" name="name" class="easyui-textbox" value="${customer.name }" disabled="disabled" />
						</td>
						<td style="width:100px;">
							<label>电话：</label>
						</td>
						<td style="width:100px;">
							<input id="phone" name="phone" class="easyui-textbox" value="${customer.phone }" disabled="disabled"/>
						</td>
						<td style="width:100px;">
							<label>省市：</label>
						</td>
						<td style="width:100px;">
							<input id="city" name="city" class="easyui-textbox" value="${customer.city }" disabled="disabled"/>
						</td>
					</tr>
					<tr>
						<td>
							<label>住址：</label>
						</td>
						<td>
							<input id="address" name="address" class="easyui-textbox" value="${customer.address }" disabled="disabled"/>
						</td>
						<td>
							<label>公司：</label>
						</td>
						<td>
							<input id="company" name="company" class="easyui-textbox" value="${customer.company }" disabled="disabled"/>
						</td>
						<td>
							<label>创建人：</label>
						</td>
						<td>
							<input id="createUser" name="createUser" class="easyui-textbox" value="${customer.createUser}" disabled="disabled"/>
						</td>
					</tr>
					<tr>
						<td>
							<label>创建时间：</label>
						</td>
						<td>
							<input id="createTime" name="createTime" class="easyui-datebox"
							value="${customer.createTime}" disabled="disabled"/>
						</td>
						<td>
							<label>更新时间：</label>
						</td>
						<td>
							<input id="updateTime" name="updateTime" class="easyui-datebox"
							value="${customer.updateTime }" disabled="disabled"/>
						</td>
						<td>
							<label>客户类型：</label>
						</td>
						<td>
							<select id="isVip" class="easyui-combobox" name="isVip" style="width:200px;" disabled="disabled">  
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
							<textarea id="remarks" name="remarks" rows="3" cols="40" disabled="disabled" >${customer.remarks }</textarea>
						</td>
						<td>
							<input type="button" style="margin-left:30px" class="orangeBtn" id="submit_button" value="录入跟踪信息" onclick="showCust(${customer.id})"  />
						</td>
						<td >
							<input type="button" style="margin-left:30px" class="orangeBtn" id="order_button" value="录入订单信息" onclick="addOrder(${customer.id})"  />
						</td>
						
					</tr>
				</table>
			</form>
		</div>
		
		<div id="tt" class="easyui-tabs" style="width:100%x;height:250px;">  
		    <div title="跟踪信息" style="padding:2px;" id="track"  >  
		          <table id="trackInfo"></table>
		    </div>  
		    <div title="订单信息" style="overflow:auto;padding:2px;" id="order"  >  
		        <table id="orderInfo"> </table>
		    </div>  
		</div>
	</div>
</body>
</html>