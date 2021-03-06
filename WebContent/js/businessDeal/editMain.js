$(document).ready(function(){
	//业务大类
	$('#bigBusiness').combobox({
		valueField:'id',
		textField:'name',
		url:"../params/getBigBusiness",
		editable:false,
		onLoadSuccess:function(){
			var id = $('#bigBusiness').combobox("getValue");
			//业务小类
			$('#smallBusiness').combobox({
				valueField:'id',
				textField:'name',
				editable:false,
				url:"/HR/params/getDetailType?id=" + id
			});
		},
		onSelect:function(rec){
			$('#smallBusiness').combobox("clear");
			var url = '/HR/params/getDetailType?id='+rec.id;    //业务大类关联业务小类
            $('#smallBusiness').combobox('reload', url);    
            //生成编号
			$.ajax({
				type:"post",
	    	 	url:'/HR/businessDeal/getBusinessId?id='+rec.id,
	    	 	dataType:"json",
	    	 	success:function(data){
	    	 		$('#code').val(data);  
	    	 	},
	    	 	error:function(er){
	    	 		$.messager.show({
	        			title:'提示',
	        			msg:'获取业务编号失败！',
	        			timeout:3000,
	        			showType:'slide'
	        		});
	    	 	}
			});
		}
	});
	
	//单位
	$('#company').combobox({
		panelHeight:'auto',  
		valueField:'id',
		textField:'name',
		url:"../params/getCompany",
		editable:false
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
		var bigBusiness = $('#bigBusiness').combobox("getValue");//业务大类
		if(bigBusiness == null || bigBusiness == ""){
			$.messager.show({
    			title:'提示',
    			msg:'请选择业务大类！',
    			timeout:3000,
    			showType:'slide'
    		});
			return false;
		}
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
		//提交表单信息
		var data = $.serializeObject($('#addForm'));
		$.ajax({
			type:"post",
			url: "/HR/businessDeal/updateMain",
    	 	dataType:"json",
    	 	data:data,
    	 	success:function(result){
    	 		var success = result.success;
		    	 if(success == "true"){
		    		 parent.$.messager.show({
              			title:'提示',
              			msg:'主任务修改成功！',
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