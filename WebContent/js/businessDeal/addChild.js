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
	//确认按钮保存信息,提交表单
	$('#ok_button').bind('click',function(){
		//校验关键信息不为空
		var businessContent = $("#businessContent").val();
		var remarks = $("#remarks").val();
		if(businessContent != null && businessContent.length > 200){
			parent.$.messager.show({
       			title:'提示',
       			msg:'业务内容长度不能超过200个字！',
       			timeout:3000,
       			showType:'slide'
       		});
       		return false;
		}
		if(remarks != null && remarks.length > 200){
			parent.$.messager.show({
       			title:'提示',
       			msg:'备注长度不能超过200个字！',
       			timeout:3000,
       			showType:'slide'
       		});
       		return false;
		}
		//提交表单信息
		var data = $.serializeObject($('#addForm'));
		$.ajax({
			type:"post",
			url: "/HR/businessDeal/saveChild",
    	 	dataType:"json",
    	 	data:data,
    	 	success:function(result){
    	 		var success = result.success;
		    	 if(success == "true"){
					$.messager.show({
              			title:'提示',
              			msg:'子业务录入成功！',
              			timeout:3000,
              			showType:'slide'
              		});
					getOpener().doSearch();
					closeDialog();
		    	 }else{
		    		 $.messager.show({
            			title:'提示',
            			msg:result.errorMsg,
            			timeout:3000,
            			showType:'slide'
            		});
		    	 }
    	 	}
		});
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