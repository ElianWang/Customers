$(document).ready(function(){
	//获取填写的客户信息，提交表单
	$('#ok_button').bind('click',function(){
		var name = $("#name").val();
		var phone = $("#phone").val();
		var city = $("#city").val();
		var address = $("#address").val();
		var company = $("#company").val();
		var createUser = $("#createUser").val();
		var createTime = $("#createTime").datebox("disable");
		var updateTime = $("#updateTime").datebox("disable");
		var isVip = $("#isVip").combobox('getValue');
		var remarks = $("#remarks").val();
//		var nameRest=/^[\u4E00-\u9FA5]+$/;
//		var myreg=/^[1][3,4,5,7,8][0-9]{9}$/;
//		if(name=="" || name==undefined){
//            $(".tishi").show().html("姓名不能为空");
//            return;
//        }
//        if(!nameRest.test(name)){
//            $(".tishi").show().html("姓名格式不正确，必须为汉字");
//            return;
//        }
//
//        if(phone==""){
//            $(".tishi").show().html("手机号码不能为空");
//            return;
//        }
//        if(!myreg.test(phone)){
//            $(".tishi").show().html("手机号码格式不正确");
//            return;
//        }
		//提交表单信息
		var data = $.serializeObject($('#addCust'));
		$.ajax({
			type:"post",
			url:"/customer/addCust/addInfo",
			dataType:"json",
			data:data,
			success:function(result){
				var success = result.success;
				if(success){
					parent.$.messager.show({
              			title:'提示',
              			msg:'客户录入成功！',
              			timeout:3000,
              			showType:'slide'
              		});
					getOpener().searchL();
//					searchL();
					closeDialog();
				}else{
					var reason  = result.reason;
					parent.$.messager.show({
              			title:'提示',
              			msg:reason,
              			timeout:3000,
              			showType:'slide'
              		});
				}
			}
		})
		
		
	})
	var height = $("#infoShow").height() - 150;
	$('#infoList').datagrid({   
		height:height,
		width:'auto',
	    url:'selectInfo',  
	    rownumbers:true,	//显示行数
		fitColumns:true,	//自适应列的大小，防止出现水平滚动条
		loadMsg:"正在加载数据，请稍等...",	//载入等待时信息
		nowrap:true,	//数据长度超过列宽时自动截取，默认为true，否则换行
		pagination:true,	//显示分页工具栏
		pageNumber:1,	//设置分页属性时，初始化页码，默认为1
		pageSize:10,	//设置分页属性时，初始化每页记录数，默认为10
		pageList:[10,20,30,40,50],		//设置分页属性时，初始化每页记录数列表，默认为[10,20,30,40,50]
		striped:true,	//设置斑马线
		singleSelect:true,	//只允许选择一行
		queryParams:{},
	    columns:[[   
	    		{field:'isVip',title:'是否重点',name:'isVip',width:100,align:'center',
	        	formatter: function(value,row,index){
					if (value == 0){
						return "重点合作";
					} else if(value == 1) {
						return "一般合作";
					}else if(value == 2) {
						return "重点潜在";
					}else if(value == 3) {
						return "一般潜在";
					}else if(value == 4) {
						return "其他";
					}
				}},   
	        {field:'name',title:'姓名',width:100,align:'center'}, 
	        {field:'phone',title:'电话',width:100,align:'center'}, 
	        {field:'city',title:'省市',width:100,align:'center'}, 
	        {field:'address',title:'地址',width:100,align:'center'}, 
	        {field:'company',title:'单位',width:100,align:'center'}, 
	        {field:'createTime',title:'创建时间',width:130,align:'center'}, 
	        {field:'updateTime',title:'最新更改时间',width:130,align:'center'}, 
	        {field:'createUser',title:'创建人',width:100,align:'center'},
	        {field:'remarks',title:'备注',width:100,align:'center'},
	        {field:'operation',title:'操作',width:300,align:'center',
	        	 formatter: function(value,row,index){
	        		 	var updBtn = '<a href="javascript:void(0)" onclick="updateInfo(\'' + row.id + '\',1)" > 修改</a>';
	        	        var delBtn = '<a href="javascript:void(0)" onclick="deleteInfo(\'' + row.id + '\',2)" > 删除</a>';
	        	        var trackBtn = '<a href="javascript:void(0)" onclick="trackInfo(\'' + row.id + '\',3)" > 查看详情</a>';
	        	        return updBtn + "    " + delBtn + "    " + trackBtn;
				}}
	    ]]   
	}); 
});

function saveCus(){
	createdetailwindow("新增客户","addCust/addPage",690,400,"updateCust");
}

function searchL(){
	var queryParams = $.serializeObject($('#showInfo'));
	$('#infoList').datagrid('load',queryParams);
}

function resetCon(){
	$("#name,#phone,#address,#company,#createUser").val("");
	$("#isVip").combobox("reset");
	$("#createTime").datebox("reset");
	$("#startTime,#endTime").datebox("reset");
	searchL();
}

function updateInfo(id,x){
	createdetailwindow("更新客户信息","addCust/updatePage?id=" + id,690,400,"updateCust");
}

function deleteInfo(id,x){
	 $.messager.confirm('Confirm','确定删除该行数据？',function(r){
		 if(r){
			 $.ajax({
					type:"post",
					dataType:"json",
					url:"deleteCust",
					data:{
						"id":id
					},
					success:function(data){
						var success = data.success;
						if(success){
							parent.$.messager.show({
				      			title:'提示',
				      			msg:'客户删除成功！',
				      			timeout:3000,
				      			showType:'slide'
				      		});
							searchL();
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
	 })
}

function editCust(id){
	var data = $.serializeObject($('#updateCust'));
	$.ajax({
	type:"post",
	url:"/customer/addCust/editInfo?id="+id,
	dataType:"json",
	data:data,
	success:function(result){
		var success = result.success;
		if(success){
			parent.$.messager.show({
      			title:'提示',
      			msg:'客户修改成功！',
      			timeout:3000,
      			showType:'slide'
      		});
			getOpener().searchL();
			closeDialog();
		}else{
			$.messager.show({
	  			title:'提示',
	  			msg:'客户修改失败！',
	  			timeout:3000,
	  			showType:'slide'
	  		});
		}
	}
})
}


function getOpener(){
	var tab = parent.$('#maintabs').tabs('getSelected');
	var index = parent.$('#maintabs').tabs('getTabIndex',tab);
	var opener = parent.$("iframe[name!=updateCust]")[index].contentWindow;
	return opener;
}

function closeDialog(){
	var api = frameElement.api;
	api.close();
}

function trackInfo(id,x){
	createWithoutCancle("显示详情","addCust/showInfo?id=" + id,780,500);
}


//导出
function ExportXls() {
	var queryParams = $.serializeObject($('#showInfo'));
	var params = '';
	$.each(queryParams, function(key, val){
		params+=key+'='+val+'&';
	});
	$.messager.confirm('确认','您将根据查询条件导出数据',function(r){    
	    if (r){    
	    	window.location.href = 'exportXls?'+params;    
	    }    
	}); 
}