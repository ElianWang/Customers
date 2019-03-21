$(document).ready(function(){
	//保存用户信息
	$("#saveBtn").bind("click",function(){
    	var password1 = $("#password1").val();
    	var password2 = $("#password2").val();
    	if(password1 == null || password1 == ""){
    		parent.$.messager.show({
       			title:'提示',
       			msg:'新密码不能为空！',
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
       			msg:'两次输入密码必须一致！',
       			timeout:3000,
       			showType:'slide'
       		});
       		return false;
    	}
    	$("#password").val(hex_md5(password1));
		//如果验证成功，就通过ajax方式将数据传到后台并保存
		var data = $.serializeObject($('#addForm'));
		console.info(data)
		$.ajax({
		     type: 'POST',
		     url: "/HR/user/resetPassword" ,
		     data: data ,
		     dataType: "json",
		     success: function(result){
		    	 var success = result.success;
		    	 if(success == "true"){
					parent.$.messager.show({
               			title:'提示',
               			msg:'密码重置成功！',
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