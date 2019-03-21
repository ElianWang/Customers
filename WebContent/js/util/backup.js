$(document).ready(function(){
	var height = $("#center").height() - 120;
	
	$('#backupList').datagrid({
		height:height,
	   	width:'auto',
	    url:'/HR/util/getlist',  
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
			field:'remark',
			align : 'left',
			title:'备份说明',
			width: 0.2,
			halign:"center",
			align:"left"
		},{
			field : 'date', 
			align : 'left',
			title : '备份时间',
			width: 0.4,
			halign:"center",
			align:"center"
		},{
			field : 'url', 
			align : 'left',
			title : '备份路径',
			width: 0.4,
			halign:"center",
			align:"center"
		}]]
	});
	$("#backup_button").bind("click",function(){
		createWithoutCancle("新增备份",'/HR/util/add',400,140);
	});
	$("#import_button").bind("click",function(){
		var selects = $('#backupList').datagrid("getSelections");
		if(selects == null || selects.length == 0){
			$.messager.show({
    			title:'提示',
    			msg:'请先选择一条数据！',
    			timeout:3000,
    			showType:'slide'
    		});
			return false;
		}else if(selects.length > 1){
			$.messager.show({
    			title:'提示',
    			msg:'只能选择一条数据！',
    			timeout:3000,
    			showType:'slide'
    		});
			return false;
		}else{
			var id = selects[0]["id"];
		    $.ajax({
		    	type:"post",
		    	dataType:"json",
		    	url:"/HR/util/import?id=" + id,
		    	success:function(data){
					if(data.success){
						parent.$.messager.show({
		           			title:'提示',
		           			msg:'导入成功，数据稍后将会更新为所导入的数据！',
		           			timeout:3000,
		           			showType:'slide'
		           		});
						doSearch();
		           		}
						else{
							parent.$.messager.show({
			           			title:'提示',
			           			msg:'失败！',
			           			timeout:3000,
			           			showType:'slide'
			           		});
			           	}
					}
		    });
		}
	});
	
	$("#delete_button").bind("click",function(){
		var selects = $('#backupList').datagrid("getSelections");
		if(selects == null || selects.length == 0){
			$.messager.show({
    			title:'提示',
    			msg:'请先选择一条数据！',
    			timeout:3000,
    			showType:'slide'
    		});
			return false;
		}else if(selects.length > 1){
			$.messager.show({
    			title:'提示',
    			msg:'只能选择一条数据！',
    			timeout:3000,
    			showType:'slide'
    		});
			return false;
		}else{
			$.messager.confirm('确认对话框', '确认要删除该备份吗？', function(r){
				if (r){
					var id = selects[0]["id"];
				    $.ajax({
				    	type:"post",
				    	dataType:"json",
				    	url:"/HR/util/delete?id="+id,
				    	success:function(data){
							if(data.success){
								parent.$.messager.show({
				           			title:'提示',
				           			msg:'成功！',
				           			timeout:3000,
				           			showType:'slide'
				           		});
								doSearch();
			           		}else{
								parent.$.messager.show({
				           			title:'提示',
				           			msg:'失败！',
				           			timeout:3000,
				           			showType:'slide'
				           		});
				           	}
						}
				    });
				}
			});
		}
	});
})
function doSearch(){
	var t = $.serializeObject($('#searchForm'));
	$('#backupList').datagrid('load', t); 
}
function reset3(){
	$("#startTime,#endTime").datebox("reset");
	doSearch();
}