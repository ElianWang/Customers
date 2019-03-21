$(document).ready(function(){
	var height = $("#center").height() - 120;
	$('#userList').datagrid({
		height:height,
	   	width:'auto',
	    url:'../user/getList',  
	    rownumbers:true,	//显示行数
		fitColumns:true,	//自适应列的大小，防止出现水平滚动条
		loadMsg:"正在加载数据，请稍等...",	//载入等待时信息
		nowrap:true,	//数据长度超过列宽时自动截取，默认为true，否则换行
		pagination:true,	//显示分页工具栏
		pageNumber:1,	//设置分页属性时，初始化页码，默认为1
		pageSize:20,	//设置分页属性时，初始化每页记录数，默认为10
		pageList:[10,20,30,40,50],		//设置分页属性时，初始化每页记录数列表，默认为[10,20,30,40,50]
		striped:true,	//设置斑马线
		singleSelect : true,	//只允许选择一行
		columns : [[{
			field:'account',
			align : 'left',
			title:'账号',
			width: 100,
			halign:"center",
			align:"center"
		},{
			field : 'name', 
			align : 'left',
			title : '姓名',
			width: 100,
			halign:"center",
			align:"center"
		}]]
	});
	
	$("#add_button").bind("click",function(){
		createWithoutCancle("创建用户","/HR/user/addUser",600,150);
	});
	$("#edit_button").bind("click",function(){
		var selects = $('#userList').datagrid("getSelections");
		if(selects == null || selects.length == 0){
			$.messager.show({
    			title:'提示',
    			msg:'请先选择一个用户！',
    			timeout:3000,
    			showType:'slide'
    		});
			return false;
		}else if(selects.length > 1){
			$.messager.show({
    			title:'提示',
    			msg:'只能选择一个用户！',
    			timeout:3000,
    			showType:'slide'
    		});
			return false;
		}else{
			var account = selects[0]["account"];
			createWithoutCancle("编辑用户","/HR/user/editUser?account=" + account,600,150);
		}
	});
	
	$("#password_button").bind("click",function(){
		var selects = $('#userList').datagrid("getSelections");
		if(selects == null || selects.length == 0){
			$.messager.show({
    			title:'提示',
    			msg:'请先选择一个用户！',
    			timeout:3000,
    			showType:'slide'
    		});
			return false;
		}else if(selects.length > 1){
			$.messager.show({
    			title:'提示',
    			msg:'只能选择一个用户！',
    			timeout:3000,
    			showType:'slide'
    		});
			return false;
		}else{
			var account = selects[0]["account"];
			createWithoutCancle("重置密码","/HR/user/resetPass?account=" + account,400,140);
		}
	});
})
function doSearch(){
	var t = $.serializeObject($('#searchForm'));
	$('#userList').datagrid('load', t); 
}
function reset1(){
	$("#account").val("");
	$("#name").val("");
	doSearch();
}