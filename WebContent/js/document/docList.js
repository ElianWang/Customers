$(document).ready(function(){
	var height = $("#center").height() - 120;
	$('#docList').datagrid({
		height:height,
	   	width:'auto',
	    url:'../document/getDocList',  
	    rownumbers:true,	//显示行数
		fitColumns:true,	//自适应列的大小，防止出现水平滚动条
		loadMsg:"正在加载数据，请稍等...",	//载入等待时信息
		nowrap:true,	//数据长度超过列宽时自动截取，默认为true，否则换行
		pagination:true,	//显示分页工具栏
		pageNumber:1,	//设置分页属性时，初始化页码，默认为1
		pageSize:10,	//设置分页属性时，初始化每页记录数，默认为10
		pageList:[10,20,30,40,50],		//设置分页属性时，初始化每页记录数列表，默认为[10,20,30,40,50]
		striped:true,	//设置斑马线
		singleSelect : false,	//只允许选择一行
		columns : [[{
			field:'ck',
			checkbox:true
		},{
			field:'company', 
			align:'left',
			title:'所属单位',
			width:65,
			halign:"center",
			align:"center"
		},{
			field:'code',
			align:'left',
			title:'编号',
			width:33,
			halign:"center",
			align:"center"
		},{
			field:'fullName', 
			align:'left',
			title:'姓名',
			width:65,
			halign:"center",
			align:"center"
		},{
			field:'idNum', 
			align:'left',
			title:'身份证号',
			width:81,
			halign:"center",
			align:"center"
		},{
			field:'residenceType', 
			align:'left',
			title:'户口性质',
			width:55,
			halign:"center",
			align:"center"
		},{
			field:'personStatus', 
			align:'left',
			title:'人员状态',
			width:35,
			halign:"center",
			align:"center"
		},{
			field:'rank', 
			align:'left',
			title:'职级',
			width:30,
			halign:"center",
			align:"center"
		},{
			field:'docDeposit', 
			align:'left',
			title:'档案存放情况',
			width:140,
			halign:"center",
			align:"center"
		},{
			field:'unitColhouse', 
			align:'left',
			title:'单位集体户',
			width:70,
			halign:"center",
			align:"center"
		},{
			field:'partyRelation', 
			align:'left',
			title:'党员关系',
			width:55,
			halign:"center",
			align:"center"
		},/*,{
			field:'organization', 
			align:'left',
			title:'现存机构',
			width:248,
			halign:"center",
			align:"center"
		},{
			field:'intoTime', 
			align:'left',
			title:'转入时间',
			width:72,
			halign:"center",
			align:"center"
		},{
			field:'docPost', 
			align:'left',
			title:'原档存处',
			width:248,
			halign:"center",
			align:"center"
		},{
			field:'outTime', 
			align:'left',
			title:'转出时间',
			width:72,
			halign:"center",
			align:"center"
		},*/{
			field:'remarks', 
			align:'left',
			title:'备注',
			width:140,
			formatter: function(value,row,index){
				if(value != null){
					return "<span title='" + value + "'>" + value + "</span>";
				}
			},
			halign:"center",
			align:"left"
		}]]
	});
	
	$('#company').combobox({    
	    url:'../params/getCompany',    
	    valueField:'id',    
	    textField:'name'   
	});
	
	
	$("#edit_button").bind("click",function(){
		var selects = $('#docList').datagrid("getSelections");
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
		createWithoutCancle("编辑档案","/HR/document/editDoc?id=" + id,400,450);
	});
	$("#delete_button").bind("click",function(){
		var selects = $('#docList').datagrid("getSelections");
		if(selects == null || selects.length == 0){
			$.messager.show({
       			title:'提示',
       			msg:'请先选择一条数据！',
       			timeout:3000,
       			showType:'slide'
       		});
			return false;
		}
		$.messager.confirm('确认对话框', '确认要删除档案吗？', function(r){
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
					url:"/HR/document/deleteDoc",
					data:{
						"ids":ids
					},
					success:function(data){
						var success = data.success;
						if(success == "true"){
							$.messager.show({
				       			title:'提示',
				       			msg:'删除档案成功！',
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
});
function doSearch(){
	var t = $.serializeObject($('#searchForm'));
	$('#docList').datagrid('load', t); 
}
function reset1(){
	$("#fullName").val("");
	$("#idNum").val("");
	$('#company').combobox("reset");
	$("#organization").val("");
	doSearch();
}

//导出
function ExportXls() {
	//JeecgExcelExport1("/HR/document/exportXls?1=1", "docList");
	var queryParams = $.serializeObject($('#searchForm'));
	var params = '';
	$.each(queryParams, function(key, val){
		params+=key+'='+val+'&';
	});
	var selects = $('#docList').datagrid('getSelections');
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
		    	window.location.href = '/HR/document/exportXls?'+params;    
		    }    
		}); 
	}else{
		$.messager.confirm('确认','您将根据查询条件导出数据',function(r){    
		    if (r){    
		    	params+='&type='+type+'&';
		    	window.location.href = '/HR/document/exportXls?'+params;    
		    }    
		}); 
	}
}
