<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/jsp/main/resource.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>业务</title>
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

<style media=print type="text/css"> 
	.noprint{visibility:hidden} 
</style>

<script src=<%= request.getContextPath() + "/js/businessDeal/createBusiness.js" %> type="text/javascript"></script>
</head>
<body class="easyui-layout">   
	
    <div id="center" data-options="region:'center'">
		<!-- search -->
		<div align="center">
			<form id="searchForm" action="">
				<table style="border-collapse: separate;border-spacing:10px;">
					<tr>
						<td >
							<label>业务编号：</label>
						</td>
						<td class="left-td">
							<input id="code" name="code" class="easyui-textbox" readonly="readonly"/>
						</td>
						<td> 
							<label>所属单位：</label>
						</td>
						<td>
							<input id="company" name="company" class="easyui-combobox"/>
						</td>
					</tr>
					<tr>
						<td>
							<label>业务大类：</label>
						</td>
						<td class="left-td">
							<input id="bigBusiness" name="bigBusiness" class="easyui-combobox"/>
						</td>
						<td>
							<label>业务小类：</label>
						</td>
						<td>
							<input id="smallBusiness" name="smallBusiness" class="easyui-combobox" />
						</td>
					</tr>
					<tr>
						<td>
							<label>服务对象：</label>
						</td>
						<td class="left-td">
							<input id="serviceTarget" name="serviceTarget" type="text" />
						</td>
						<td>
							<label>联系方式：</label>
						</td>
						<td>
							<input id="contact" name="contact" type="text" />
						</td>
					</tr>
					<tr>
						<td>
							<label>申报人：</label>
						</td>
						<td>
							<input id="applyMan" name="applyMan" type="text" />
						</td>
					</tr>
					<tr >
						<td >
							<label>业务记录：</label>
						</td>
						<td colspan="3" >
							<textarea id="businessContent" name="businessContent" rows="4" cols="58"></textarea>
						</td>
					</tr>
					<tr >
						<td >
							<label>备注说明：</label>
						</td>
						<td colspan="3" >
							<textarea id="remarks" name="remarks" rows="4" cols="58"></textarea>
						</td>
						
					</tr>
					<tr>
						<td>
							<label>操作方式：</label>
						</td>
						<td class="left-td">
							<input id="operateType" name="operateType" class="easyui-combobox"/>
						</td>
						<td>
							<label>操作时间：</label>
						</td>
						<td>
							<input id="operateTime" name="operateTime" class="easyui-datebox"
							value="<fmt:formatDate value='${date}' pattern='yyyy-MM-dd'/>"/>
						</td>
					</tr>
					<tr>
						<td>
							<label>办理动态：</label>
						</td>
						<td class="left-td"> 
							<input id="operateStatusName" class="easyui-textbox" readonly="readonly"/>
							<input id="operateStatus" name="operateStatus" type="hidden" />
						</td>
						<td>
							<label>操作人员：</label>
						</td>
						<td>
							<input id="operateUser" name="operateUser" type="text" />
						</td>
					</tr>
					
				</table>
			</form>
		</div>
		<div align="center" class="noprint">
			<input type="button" style="margin-left:30px" class="orangeBtn" id="ok_button" value="确认" />
			<input type="button" style="margin-left:30px" class="orangeBtn" id="print_button" onclick="printMydiv();" value="打印" />
			<input type="button" style="margin-left:30px" class="orangeBtn" id="cancle_button" value="取消" />
		</div>
    </div>
</body> 
</html>