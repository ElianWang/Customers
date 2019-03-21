<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/jsp/main/resource.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>工作管理系统</title>
<style type="text/css">
iframe{
	width:100%;
	height:100%;
	border:none;
}
.sysName {
	font-size:30px;
	font-family:STKaiti;
	color:#10b0b1;
    position: relative;
    top: 5px;
    left: 30px;
}
.panel-header {
	background-color: #F2F2F2;
    background: -webkit-linear-gradient(top,#ffffff 0,#F2F2F2 100%);
    background: -moz-linear-gradient(top,#ffffff 0,#F2F2F2 100%);
    background: -o-linear-gradient(top,#ffffff 0,#F2F2F2 100%);
    background: linear-gradient(to bottom,#ffffff 0,#F2F2F2 100%);
}
.layout-panel-west .panel-body {
	background:#10b0b1;
}
.tabs-panels {
	background:#fff;
}
.tabs-panels .panel-body{
	background:#fff;
}
.tabs-with-icon{
	padding-left:0;
}
.panel-tool{
	right:8px;
}
.tabs-header, .tabs-tool{
	background-color: #ecf0f7;
	/* border-color: #ecf0f7; */
	/* border-color:#ecf0f7; */
}
.tabs-header, .tabs-scroller-left, .tabs-scroller-right, .tabs-tool, .tabs, .tabs-panels, .tabs li a.tabs-inner, .tabs li.tabs-selected a.tabs-inner, .tabs-header-bottom .tabs li.tabs-selected a.tabs-inner, .tabs-header-left .tabs li.tabs-selected a.tabs-inner, .tabs-header-right .tabs li.tabs-selected a.tabs-inner{
	/* border-color: #10b0b1; */
	/* border-color:#ecf0f7; */
}
.panel-header, .panel-body{
	/* border-color: #10b0b1; */
	/* border-color:#ecf0f7; */
}
</style>
 <script type="text/javascript" >
 	$(document).ready(function(){
 		$("#accordion a").bind("click",function(){
 			var url = $(this).attr("url");
 			var title = $(this).attr("name");
 			var refresh = $(this).data("refresh");
 			addTab(title,url,refresh);
 		});
 		
 	});
 </script>
 
</head>
<body class="easyui-layout" style="overflow-y: hidden" scroll="no">
	<div region="north" class="north" border="false" title="" region="north" border="false" title="" style="">
		<div style="width:30%;height:100%;float:left">
			<span class="sysName">工作管理系统</span>
		</div>
		<div style="height:100%;width:40%;float:right;margin-top:2px;">
			<div class="userInfo">
				<label style="margin-right:20px;margin-left:4px;">${user.name}</label>
				<label style="margin-left:40px;">姓名: </label>
				<label style="margin-left:4px;">${user.account}</label>
				<label>账号: </label>
			</div>
			<div class="userName" style="width:100%;height:50%;">
				<a href="logout">[退出]</a>
				<a href="index">[返回首页]</a>
				<label><fmt:formatDate value="${date}" type="date" pattern="yyyy年MM月dd日" /></label>
			</div>
		</div>
	</div>
	<div data-options="region:'west',split:true" id="menuNav" title="菜单导航" style="width:129px;">
     	<ul id="accordion" class="accordion">
<!-- 			<li class="open"> -->
<!-- 				<div class="link"><i class="fa fa-paint-brush"></i>业务办理<i class="fa fa-chevron-down">➤</i></div> -->
<!-- 				<ul class="submenu" style="display:block"> -->
<!-- 					<li><a url="businessDeal/createBusiness" name="业务新录入" data-refresh="true">新录入</a></li> -->
<!-- 					<li><a url="businessDeal/businessList" name="业务查询" data-refresh="false">查询</a></li> -->
<!-- 				</ul> -->
<!-- 			</li> -->
			<li class="open">
				<div class="link"><i class="fa fa-paint-brush"></i>客户管理<i class="fa fa-chevron-down">➤</i></div>
				<ul class="submenu" style="display:block">
					<li><a url="addCust/selectCust" name="展示信息" data-refresh="true">查询</a></li>
				</ul>
			</li>
<!-- 			<li> -->
<!-- 				<div class="link"><i class="fa fa-code"></i>业务流程<i class="fa fa-chevron-down">➤</i></div> -->
<!-- 				<ul class="submenu"> -->
<!-- 					<li><a url="progress/createProgress" name="流程新录入" data-refresh="true">新录入</a></li> -->
<!-- 					<li><a url="progress/docList" name="流程查询" data-refresh="false">查询</a></li> -->
<!-- 				</ul> -->
<!-- 			</li> -->
<!-- 			<li> -->
<!-- 				<div class="link"><i class="fa fa-mobile"></i>档案管理<i class="fa fa-chevron-down">➤</i></div> -->
<!-- 				<ul class="submenu"> -->
<!-- 					<li><a url="document/addDoc" name="档案新录入" data-refresh="true">新录入</a></li> -->
<!-- 					<li><a url="document/docList" name="档案查询" data-refresh="false">查询</a></li> -->
<!-- 				</ul> -->
<!-- 			</li> -->
<!-- 			<li><div class="link"><i class="fa fa-globe"></i>参数管理<i class="fa fa-chevron-down">➤</i></div> -->
<!-- 				<ul class="submenu"> -->
<!-- 					<li><a url="params/paramList?type=01" name="所属单位" data-refresh="false">所属单位</a></li> -->
<!-- 					<li><a url="params/paramList?type=04" name="操作类型" data-refresh="false">操作类型</a></li> -->
<!-- 					<li><a url="params/paramList?type=03" name="大类别" data-refresh="false">大类别</a></li> -->
<!-- 					<li><a url="params/paramList?type=05" name="小类别" data-refresh="false">小类别</a></li> -->
<!-- 					<li><a url="params/docParams" name="档案参数" data-refresh="false">档案参数</a></li> -->
<!-- 				</ul> -->
<!-- 			</li> -->
<!-- 			<li><div class="link"><i class="fa fa-globe"></i>系统管理<i class="fa fa-chevron-down">➤</i></div> -->
<!-- 				<ul class="submenu"> -->
<!-- 					<li><a url="user/userList" name="系统用户" data-refresh="false">系统用户</a></li> -->
<!-- 					<li><a url="util/bakList" name="备份" data-refresh="false">备份/导入</a></li> -->
<!-- 				</ul> -->
<!-- 			</li> -->
		</ul>
     </div>
	<!-- 中间-->
	<div id="mainPanle" region="center" style="overflow: hidden;">
	    <div id="maintabs" class="easyui-tabs" fit="true" border="false">
	    </div>
	</div>
</body>
</html>