$(document).ready(function(){
	var height = $("#center").height() - 120;
	$('#paramList').datagrid({
		height:height,
	   	width:'auto',
	    url:'../params/queryParamList',  
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
		queryParams:$.serializeObject($('#searchForm')),
		columns : [[{
			field:'code',
			align : 'left',
			title:'编码',
			width: 100,
			halign:"center",
			align:"center"
		},{
			field : 'name', 
			align : 'left',
			title : '名称',
			width: 100,
			halign:"center",
			align:"center"
		}]]
	});
	var paramName = "";
	var paramType = $("#paramType").val();
	if(paramType == "01"){
		paramName = "所属单位";
	}else if(paramType == "02"){
		paramName = "办理状态";
	}else if(paramType == "04"){
		paramName = "操作类型";
	}else if(paramType == "03"){
		paramName = "大类别";
	}else if(paramType == "05"){
		paramName = "小类别";
	}
	
	$("#status").combobox({
		url:'getOperateStatus',    
	    valueField:'id',    
	    textField:'name',
	    panelHeight:'auto',
	    onSelect:function(record){
	    	$("#parentCode").val(record.id);
	    }
	});
	
	$("#add_button").bind("click",function(){
		createWithoutCancle("新增" + paramName,"/HR/params/addParam?paramType=" + paramType,300,180);
	});
	$("#edit_button").bind("click",function(){
		var selects = $('#paramList').datagrid("getSelected");
		if(selects == null || selects.length == 0){
			$.messager.show({
       			title:'提示',
       			msg:'请先选择一条数据！',
       			timeout:3000,
       			showType:'slide'
       		});
			return false;
		}
		var id = selects["id"];
		createWithoutCancle("编辑" + paramName,"/HR/params/editParam?id=" + id + "&paramType=" + paramType,330,170);
	});
	$("#delete_button").bind("click",function(){
		var selects = $('#paramList').datagrid("getSelected");
		if(selects == null || selects.length == 0){
			$.messager.show({
       			title:'提示',
       			msg:'请先选择一条数据！',
       			timeout:3000,
       			showType:'slide'
       		});
			return false;
		}
		$.messager.confirm('确认对话框', '确认要删除该参数吗？', function(r){
			if (r){
				var id = selects["id"];
				var name = selects["name"];
				$.ajax({
					type:"post",
					dataType:"json",
					url:"/HR/params/deleteParam",
					data:{
						"id":id,
						"paramType":paramType,
						"name":name
					},
					success:function(data){
						var success = data.success;
						if(success == "true"){
							$.messager.show({
				       			title:'提示',
				       			msg:'删除参数成功！',
				       			timeout:3000,
				       			showType:'slide'
				       		});
							doSearch();
						}else{
							$.messager.show({
				       			title:'提示',
				       			msg:data.errorMsg,
				       			timeout:3000,
				       			showType:'slide'
				       		});
						}
					}
				});
			}
		});
	});
})
function doSearch(){
	var t = $.serializeObject($('#searchForm'));
	$('#paramList').datagrid('load', t); 
}
function reset1(){
	$("#name").val("");
	$("#status").combobox("reset");
	$("#parentCode").val("");
	doSearch();
}