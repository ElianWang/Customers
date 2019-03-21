$(document).ready(function(){
	var height = $("#center").height() - 120;
	$('#queryList').datagrid({
		height:height,
	   	width:'auto',
	    url:'../progress/getProList',  
	    rownumbers:true,	//显示行数
		fitColumns:true,	//自适应列的大小，防止出现水平滚动条
		loadMsg:"正在加载数据，请稍等...",	//载入等待时信息
		nowrap:true,	//数据长度超过列宽时自动截取，默认为true，否则换行
		pagination:true,	//显示分页工具栏
		pageNumber:1,	//设置分页属性时，初始化页码，默认为1
		pageSize:20,	//设置分页属性时，初始化每页记录数，默认为10
		pageList:[10,20,30,40,50],		//设置分页属性时，初始化每页记录数列表，默认为[10,20,30,40,50]
		striped:true,	//设置斑马线
		singleSelect : false,	//只允许选择一行
		columns : [[{
			field:'ck',
			checkbox:true
		},{
			field:'code',
			align:'left',
			title:'流程编号',
			width:83,
			halign:"center",
			align:"center"
		},{
			field:'company', 
			align:'left',
			title:'适用单位',
			width:65,
			halign:"center",
			align:"center"
		},{
			field:'publishOrg', 
			align:'left',
			title:'发布机构',
			width:100,
			halign:"center",
			align:"center"
		},{
			field:'applyYear', 
			align:'left',
			title:'适用年度',
			width:53,
			halign:"center",
			align:"center"
		},{
			field:'title', 
			align:'left',
			title:'流程标题',
			width:285,
			formatter: function(value,row,index){
				if(value != null){
					return "<span title='" + value + "'>" + value + "</span>";
				}
			},
			halign:"center",
			align:"center"
		},{
			field:'content', 
			align:'left',
			title:'流程内容',
			width:330,
			formatter: function(value,row,index){
				if(value != null){
					return "<span title='" + value + "'>" + value + "</span>";
				}
			},
			halign:"center",
			align:"left"
		},{
			field:'remarks', 
			align:'left',
			title:'备注说明',
			width:95,
			formatter: function(value,row,index){
				if(value != null){
					return "<span title='" + value + "'>" + value + "</span>";
				}
			},
			halign:"center",
			align:"left"
		},{
			field:'operateUser', 
			align:'left',
			title:'操作人员',
			width:55,
			halign:"center",
			align:"center"
		},{
			field:'operateTime', 
			align:'left',
			title:'操作时间',
			width:70,
			halign:"center",
			align:"center"
		}]]
	});
	
	
	
	$("#edit_button").bind("click",function(){
		var selects = $('#queryList').datagrid("getSelections");
		if(selects == null || selects.length == 0){
			$.messager.show({
       			title:'提示',
       			msg:'请先选择一条数据！',
       			timeout:3000,
       			showType:'slide'
       		});
			return false;
		}
		if(selects.length > 1){
			$.messager.show({
       			title:'提示',
       			msg:'只允许选择一条数据！',
       			timeout:3000,
       			showType:'slide'
       		});
			return false;
		}
		var id = selects[0]["id"];
		createWithoutCancle("编辑流程","/HR/progress/editPro?id=" + id,600,490);
	});
	
	$("#delete_button").bind("click",function(){
		var selects = $('#queryList').datagrid("getSelections");
		if(selects == null || selects.length == 0){
			$.messager.show({
       			title:'提示',
       			msg:'请先选择一条数据！',
       			timeout:3000,
       			showType:'slide'
       		});
			return false;
		}
		$.messager.confirm('确认对话框', '确认要删除业务流程吗？', function(r){
			if (r){
				var ids = "";
				for(var i = 0;i < selects.length;i++){
					if(i < selects.length - 1){
						ids += selects[i]["id"] + ",";
					}else{
						ids += selects[i]["id"];
					}
				}
				$.ajax({
					type:"post",
					dataType:"json",
					url:"/HR/progress/deletePro",
					data:{
						"ids":ids
					},
					success:function(data){
						var success = data.success;
						if(success == "true"){
							$.messager.show({
				       			title:'提示',
				       			msg:'删除流程成功！',
				       			timeout:3000,
				       			showType:'slide'
				       		});
							doSearch2();
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
});
function doSearch2(){
	var t = $.serializeObject($('#searchForm'));
	$('#queryList').datagrid('load', t); 
}
function reset2(){
	$("#title").val("");
	doSearch2();
}
//导出
function exportExcel(){
	var queryParams = $.serializeObject($('#searchForm'));
	var params = '';
	$.each(queryParams, function(key, val){
		params+=key+'='+val+'&';
	});
	var selects = $('#queryList').datagrid('getSelections');
	var type = 01;   //是否全选标志
	if(selects.length>0){
		type = 02;
		$.messager.confirm('确认','您将导出选中的'+selects.length+'条数据',function(r){    
		    if (r){    
		    	var ids = "";
		    	for(var i = 0;i < selects.length;i++){
		    		if(i < selects.length - 1){
		    			ids += selects[i]["id"] + ",";
		    		}else{
		    			ids += selects[i]["id"];
		    		}
		    	}
		    	params+='ids='+ids+'&type='+type+'&';
		    	window.location.href = '/HR/progress/exportExcel?'+params; 
		    }    
		});
	}else{
		$.messager.confirm('确认','您将根据查询条件导出数据',function(r){    
		    if (r){    
		    	params+='&type='+type+'&';
		    	window.location.href = '/HR/progress/exportExcel?'+params;
		    }    
		}); 
	}
}
