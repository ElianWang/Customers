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
<script src=<%= request.getContextPath() + "/js/progress/queryProgress.js" %> type="text/javascript"></script>
</head>
<body class="easyui-layout">   
    <div id="center" data-options="region:'center'" style="background:#eee;">
		<div style="margin-left:50px">
			<form id="searchForm">
				<table style="border-collapse: separate;border-spacing:10px;">
					<tr>
						<td>
							<label clsss="span" for="title">流程标题</label>
						</td>
						<td>
							<input id="title" name="title" class="inputArea"/>
						</td>
						
						<td>
							<input type="button" class="greenBtn" id="search1" onclick="doSearch2()" value="查询" />
							<input type="button" class="greenBtn" id="reset1" onclick="reset2()" value="重置" />
						</td>
					</tr>
				</table>
			</form>
		</div>
		<!-- dataGrid -->
		<div>
			<table id="queryList"></table>
		</div>
		<!-- button -->
		<div align="center">
			<input type="button" style="margin-left:30px" class="orangeBtn" id="delete_button" value="删除" />
			<input type="button" style="margin-left:30px" class="orangeBtn" id="edit_button" value="修改" />
			<input type="button" style="margin-left:30px" class="orangeBtn" id="out_button" onclick="exportExcel()" value="导出" />		</div>
    </div>   
</body> 
</html>