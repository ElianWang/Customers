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
	.inputArea{
		margin-right:60px;
		border:1px solid lightgray;
	}
	.span{
		margin-left:60px;
	}
	#reset{
		margin-left:30px;
	}
</style>
<script src=<%= request.getContextPath() + "/js/businessDeal/businessList.js" %> type="text/javascript"></script>
</head>
<body class="easyui-layout">   
    <div id="center" data-options="region:'center'" style="background:#eee;">
		<div style="margin-left:50px">
			<form id="searchForm" style="padding-top:5px;">
				<label style="margin-right:10px;">起/止时间</label>
				<input id="startTime" name="startTime" class="easyui-datebox"/>
				<label>~</label>
				<input id="endTime" name="endTime" class="easyui-datebox"/>
				<table style="border-collapse: separate;border-spacing:10px;">
					<tr>
						<td>
							<label clsss="span" for="bigBusiness">业务大类</label>
						</td>
						<td>
							<input id="bigBusiness" name="bigBusiness" class="easyui-combobox"/>
						</td>
						<td>
							<label clsss="span" for="smallBusiness">业务小类</label>
						</td>
						<td>
							<input id="smallBusiness" name="smallBusiness" class="easyui-combobox"/>
						</td>
						<td>
							<label clsss="span" for="company">所属单位</label>
						</td>
						<td>
							<input id="company" name="company" class="easyui-combobox"/>
						</td>
						<td>
							<input id="updated" name="updated" type="checkbox" value="01"/>
							<label for="updated">显示最新</label>
						</td>
					</tr>
					<tr>
						<td>
							<label clsss="span" for="serviceTarget">服务对象</label>
						</td>
						<td>
							<input id="serviceTarget" name="serviceTarget" class="inputArea"/>
						</td>
						<td>
							<label clsss="span" for="operateStatus">办理动态</label>
						</td>
						<td>
							<input id="operateStatus" name="operateStatus" class="easyui-combobox"/>
						</td>
						<td>
							<label clsss="span" for="operateType">操作方式</label>
						</td>
						<td>
							<input id="operateType" name="operateType" class="easyui-combobox"/>
						</td>
						<td>
							<input type="button" class="greenBtn" id="search" onclick="doSearch()" value="查询" />
							<input type="button" class="greenBtn" id="reset" onclick="reset1()" value="重置" />
						</td>
					</tr>
				</table>
			</form>
		</div>
		<!-- dataGrid -->
		<div>
			<table id="businessList"></table>
		</div>
		<!-- button -->
		<div align="center">
			<input type="button" style="margin-left:30px" class="orangeBtn" id="add_button" value="添加" />
			<input type="button" style="margin-left:30px" class="orangeBtn" id="delete_button" value="删除" />
			<input type="button" style="margin-left:30px" class="orangeBtn" id="edit_button" value="修改" />
			<input type="button" style="margin-left:30px" class="orangeBtn" id="out_button" onclick="exportExcelBus()" value="导出" />
			<input type="button" style="margin-left:30px" class="orangeBtn" id="print_button" value="打印" />
		</div>
    </div>   
</body> 
</html>