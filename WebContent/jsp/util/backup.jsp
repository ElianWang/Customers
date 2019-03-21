<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<script src=<%= request.getContextPath() + "/js/util/backup.js" %> type="text/javascript"></script>
</head>
<body class="easyui-layout">   
	<!-- north -->
    <%-- <div data-options="region:'north',href:'<%= request.getContextPath() + "/jsp/main/north.jsp" %>'" 
    	style="height:120px;width:100%"></div>    --%>
    <!-- <div data-options="region:'west',title:'West',split:true" style="width:100px;"></div>   --> 
    
    <!-- center -->
    <div id="center" data-options="region:'center'" style="background:#eee;">
		<!-- search -->
		<div>
			<form id="searchForm">
				<table style="border-collapse: separate;border-spacing:10px;">
					<tr>
						<td>
							<label clsss="span">开始时间</label>
						</td>
						<td>
							<input id="startTime" name="startTime" class="easyui-datebox"/>
						</td>
						<td>
							<label clsss="span">结束时间</label>
						</td>
						<td>
							<input id="endTime" name="endTime" class="easyui-datebox"/>
						</td>
						<td>
							<input type="button" class="greenBtn" id="search" onclick="doSearch()" value="查询" />
							<input type="button" class="greenBtn" id="reset" onclick="reset3()" value="重置" />
						</td>
					</tr>
				</table>
			</form>
		</div>
		<!-- dataGrid -->
		<div>
			<table id="backupList"></table>
		</div>
		<div align="center">
				<input type="button" style="margin-left:30px" class="orangeBtn" id="backup_button" value="新建备份" />
				<input type="button" style="margin-left:30px" class="orangeBtn" id="import_button" value="导入备份" />
				<input type="button" style="margin-left:30px" class="orangeBtn" id="delete_button" value="删除备份" />
		</div>
    </div>   
</body> 
</html>