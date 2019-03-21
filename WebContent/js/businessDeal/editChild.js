$(document).ready(function(){
	$("#print_button").hide();
	//业务大类
	$('#bigBusiness').combobox({
		valueField:'id',
		textField:'name',
		url:"../params/getBigBusiness",
		editable:false,
		disabled:true,
		onLoadSuccess:function(){
			var id = $('#bigBusiness').combobox("getValue");
			//业务小类
			$('#smallBusiness').combobox({
				valueField:'id',
				textField:'name',
				editable:false,
				disabled:true,
				url:"/HR/params/getDetailType?id=" + id
			});
		}
	});
	
	//单位
	$('#company').combobox({
		panelHeight:'auto',  
		valueField:'id',
		textField:'name',
		url:"../params/getCompany",
		editable:false,
		disabled:true
	});
	//操作方式
	$('#operateType').combobox({
		valueField:'id',
		textField:'name',
		url:"../params/getOperateType",
		onSelect:function(rec){  //操作方式关联办理状态
			$.ajax({
				type:"post",
	    	 	url:'/HR/params/getDealStatue?id='+rec.id,
	    	 	dataType:"json",
	    	 	success:function(data){
	    	 		if(data.operateStatus != null && data.operateStatus != ""){
	    	 			$("#operateStatus").val(data.code);  //办理动态
	    	 			$("#operateStatusName").val(data.operateStatus);
	    	 		}else{
	    	 			$.messager.show({
		        			title:'提示',
		        			msg:'获取对应的办理状态失败！',
		        			timeout:3000,
		        			showType:'slide'
		        		});
	    	 		}
	    	 	},
	    	 	error:function(er){
	    	 		$.messager.show({
	        			title:'提示',
	        			msg:'关联办理状态失败！',
	        			timeout:3000,
	        			showType:'slide'
	        		});
	    	 	}
			});
		},
		editable:false
	});
	//显示办理动态
	var operateStatus = $("#operateStatus").val();
	if(operateStatus == "0201"){
		$("#operateStatusName").val("办理中");
	}else if(operateStatus == "0202"){
		$("#operateStatusName").val("终止");
	}else if(operateStatus == "0203"){
		$("#operateStatusName").val("已完成");
	}else{
		$("#operateStatusName").val(operateStatus);
	}
	//确认按钮保存信息,提交表单
	$('#ok_button').bind('click',function(){
		//校验关键信息不为空
		var businessContent = $("#businessContent").val();
		var remarks = $("#remarks").val();
		if(businessContent != null && businessContent.length > 300){
			parent.$.messager.show({
       			title:'提示',
       			msg:'业务内容长度不能超过200个字！',
       			timeout:3000,
       			showType:'slide'
       		});
       		return false;
		}
		if(remarks != null && remarks.length > 300){
			parent.$.messager.show({
       			title:'提示',
       			msg:'备注长度不能超过200个字！',
       			timeout:3000,
       			showType:'slide'
       		});
       		return false;
		}
		submitForm();
		//判断子任务时间操作时间，不能早于主任务操作时间
//		var cTime= $('#operateTime').val();
//		var cId = $('#id').val(); 
//		$.ajax({
//			type:"post",
//			url: "/HR/businessDeal/compareTime",
//    	 	dataType:"json",
//    	 	data:{"cTime":cTime,"cId":cId},
//    	 	success:function(result){
//    	 		var success = result.success;
//		    	 if(success == "false"){
//					parent.$.messager.show({
//              			title:'提示',
//              			msg:'子业务操作时间不能早于主任务操作时间！',
//              			timeout:3000,
//              			showType:'slide'
//              		});
//					return false;
//		    	 }else if (success == "true"){
//		    		 submitForm();
//		    	 }else{
//		    		 $.messager.show({
//            			title:'提示',
//            			msg:result.errorMsg,
//            			timeout:3000,
//            			showType:'slide'
//            		});
//		    		 return false;
//		    	 }
//    	 	}
//		});
		
	});
});

function closeDialog(){
	var api = frameElement.api;
	api.close();
}
function getOpener(){
	var tab = parent.$('#maintabs').tabs('getSelected');
	var index = parent.$('#maintabs').tabs('getTabIndex',tab);
	var opener = parent.$("iframe[name!=lhgDialog]")[index].contentWindow;
	return opener;
}

//提交表单信息
function submitForm(){
	var data = $.serializeObject($('#editForm'));
	$.ajax({
		type:"post",
		url: "/HR/businessDeal/updateChild",
	 	dataType:"json",
	 	data:data,
	 	success:function(result){
	 		var success = result.success;
	    	 if(success == "true"){
	    		 parent.$.messager.show({
          			title:'提示',
          			msg:'子业务修改成功！',
          			timeout:3000,
          			showType:'slide'
          		});
				getOpener().doSearch();
				closeDialog();
	    	 }else{
	    		 parent.$.messager.show({
        			title:'提示',
        			msg:result.errorMsg,
        			timeout:3000,
        			showType:'slide'
        		});
	    	 }
	 	}
	});
}