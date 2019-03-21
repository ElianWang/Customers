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
<script src=<%= request.getContextPath() + "/js/util/addbak.js" %> type="text/javascript"></script>
</head>
<body class="easyui-layout">   
	<!-- north -->
    <%-- <div data-options="region:'north',href:'<%= request.getContextPath() + "/jsp/main/north.jsp" %>'" 
    	style="height:120px;width:100%"></div>    --%>
    <!-- <div data-options="region:'west',title:'West',split:true" style="width:100px;"></div>   --> 
    
    <!-- center -->
    <div id="center" data-options="region:'center'">
		<!-- search -->
		<div>
			<form id="searchForm">
				<table style="border-collapse: separate;border-spacing:10px;">
					<tr>
						<td>
							<label clsss="span">备份说明</label>
						</td>
						<td>
							<textarea id="remark" class="inputxt" rows="5" cols="40" name="remark"></textarea>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<!-- dataGrid -->
		<div align="center">
				<input type="button" style="margin-left:30px" class="orangeBtn" id="save_button" value="备份" />
				<input type="button" style="margin-left:30px" class="orangeBtn" id="cancel_button" value="取消" />
		</div>
    </div>   
</body> 
</html>