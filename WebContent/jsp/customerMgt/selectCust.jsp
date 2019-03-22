
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/jsp/main/resource.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>客户管理</title>
<script type="text/javascript" src=<%=request.getContextPath() +"/js/customerMgt/selectCust.js" %> ></script>
</head>
<body class="easyui-layout">
	<div id="infoShow" data-options="region:'center'" style="background:#eee;">
		<div style="margin-left:50px">
			<form action="" id="showInfo">
				<table style="border-collapse: separate;border-spacing:10px;">
					<tr>
						<td>
							<label>姓名:</label>
						</td>
						<td>
							<input class="easyui-textbox" id="name" name="name"/>
						</td>
						<td>
							<label>电话:</label>
						</td>
						<td>
							<input class="easyui-textbox" id="phone" name="phone"/>
						</td>
						<td>
							<label>地址:</label>
						</td>
						<td>
							<input class="easyui-textbox" id="address" name="address"/>
						</td>
						<td>
							<label>客户类型:</label>
						</td>
						<td>
							<select id="isVip" class="easyui-combobox" name="isVip" style="width:150px;">
								 <option value="">全部</option>
   								 <option value="0" >重点合作</option>  
    								 <option value="1">一般合作</option>  
    								 <option value="2" >重点潜在</option>
    								 <option value="3" >一般潜在</option>
    								 <option value="4" >其他</option>
							</select>   
						</td>
						<td>
							<label>省市：</label>
						</td>
						<td>
							<input class="easyui-textbox" name="city" id="city" >
						</td>
					</tr>
					<tr>
						<td>
							<label>单位名称:</label>
						</td>
						<td>
							<input class="easyui-textbox" id="company" name="company"/>
						</td>
						<td>
							<label>创建人:</label>
						</td>
						<td>
							<input class="easyui-textbox" id="createUser" name="createUser"/>
						</td>
						<td>
							<label>创建时间:</label>
						</td>
						<td>
							<input class="easyui-datebox" id="createTime" name="createTimeStr"/>
						</td>
						<td>
							<label>更改时间:</label>
						</td>
						<td>
							<input class="easyui-datebox" id="startTime" name="startTime"/>
						</td>
						<td>
							<span style="align-content: center;">~</span>
						</td>
						<td>	
							<input class="easyui-datebox" id="endTime" name="endTime"/>
						</td>
						
					</tr>
					<tr >
						<td colspan="8" align="right">
							<input type="button" style="" class="greenBtn" id="save" onclick="saveCus()" value="新增"/>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" style="" class="greenBtn" id="searchList" onclick="searchL()" value="查询"/>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" style="" class="greenBtn" id="resetConditon" onclick="resetCon()" value="重置"/>
<!-- 							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -->
<!-- 							<input type="button" style="margin-left:30px" class="orangeBtn" id="excel_bu"  onclick="ExportXls()" value="导出" /> -->
						</td>
					</tr>
				</table>
			</form>
		</div>
	
		<!-- Datagrid -->
		<div>
			<table id="infoList"></table>
		</div>
	</div>
</body>
</html>