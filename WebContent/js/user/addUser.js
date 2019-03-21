$(document).ready(function(){
	
	$('#roleId').combobox({
		url:'/HR/role/getRoles',
		valueField:'id',    
	    textField:'name',
	    panelHeight:'auto'
	}); 
	
	var isRepeat = false;
	//判断账号是否重复
	$("#account").blur(function(){
		var account = $("#account").val();
    	$.ajax({
    	 	type:"post",
    	 	url:"/HR/user/checkRepeat",
    	 	data:{"account":account},
    	 	dataType:"json",
    	 	async:false,
    	 	success:function(data){
    	 		if(data.repeat != null && data.repeat == "true"){
               		isRepeat = true;
    	 		}else{
    	 			isRepeat = false;
    	 		}
    	 	}
    	});
    	if(isRepeat){
    		parent.$.messager.show({
       			title:'提示',
       			msg:'该账号已经被使用，请修改！',
       			timeout:3000,
       			showType:'slide'
       		});
    	}
	});
	
	//保存用户信息
	$("#saveBtn").bind("click",function(){
		if(jQuery("form").form('validate')){
			var account = $("#account").val();
			if(account == null || account == ""){
				parent.$.messager.show({
           			title:'提示',
           			msg:'账号不能为空！',
           			timeout:3000,
           			showType:'slide'
           		});
           		return false;
			}
        	//判断账号是否重复
        	if(isRepeat){
        		parent.$.messager.show({
           			title:'提示',
           			msg:'该账号已经被使用，请修改！',
           			timeout:3000,
           			showType:'slide'
           		});
           		return false;
        	}
        	var name = $("#name").val();
        	var password1 = $("#password1").val();
        	var password2 = $("#password2").val();
        	if(name == null || name == ""){
        		parent.$.messager.show({
           			title:'提示',
           			msg:'真实姓名不能为空！',
           			timeout:3000,
           			showType:'slide'
           		});
           		return false;
        	}
        	if(password1 == null || password1 == ""){
        		parent.$.messager.show({
           			title:'提示',
           			msg:'密码不能为空！',
           			timeout:3000,
           			showType:'slide'
           		});
           		return false;
        	}
        	if(password2 == null || password2 == ""){
        		parent.$.messager.show({
           			title:'提示',
           			msg:'确认密码不能为空！',
           			timeout:3000,
           			showType:'slide'
           		});
           		return false;
        	}
        	if(password1 != password2){
        		parent.$.messager.show({
           			title:'提示',
           			msg:'两次输入密码不一致！',
           			timeout:3000,
           			showType:'slide'
           		});
           		return false;
        	}
        	$("#password").val(hex_md5(password1));
    		//如果验证成功，就通过ajax方式将数据传到后台并保存
    		var data = $.serializeObject($('#addForm'));
    		$.ajax({
    		     type: 'POST',
    		     url: "/HR/user/saveUser" ,
    		     data: data ,
    		     dataType: "json",
    		     success: function(result){
    		    	 var success = result.success;
    		    	 if(success == "true"){
						parent.$.messager.show({
	               			title:'提示',
	               			msg:'用户创建成功！',
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
		}else{
			parent.$.messager.show({
    			title:'提示',
    			msg:'创建失败，请将页面中的必填项输入完整！',
    			timeout:3000,
    			showType:'slide'
    		});
			return false;
		}
	});
	$("#closeBtn").bind("click",function(){
		closeDialog();
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