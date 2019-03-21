<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/jsp/main/resource.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>录入跟踪信息</title>
<script src=<%= request.getContextPath() + "/js/customerMgt/showCust.js" %> type="text/javascript"></script>
</head>
<body class="easyui-layout">
	<input type="hidden" id="customerId" value="${id }"/>
	<div id="addTrack" data-options="region:'center'" >
		<div align="center">
			<form action="" id="trackForm">
				<table style="border-collapse: separate;border-spacing:10px;">
					<tr>
						<td><label>内容</label></td>
						<td><textarea rows="6" cols="50" id="content" name="content"></textarea></td>
					</tr>
				</table>
			</form>
		</div>
		<div align="center" >
			<input type="button" style="margin-left:30px" class="orangeBtn" id="submitBtn" value="提交" onclick="editTrack()"  />
		</div>
	</div>
</body>
</html>