$(document).ready(function(){
	
	//保存用户信息
	$("#saveBtn").bind("click",function(){
		if(jQuery("form").form('validate')){
			var name = $("#name").val();
			if(name == null || name == ""){
				parent.$.messager.show({
           			title:'提示',
           			msg:'参数名称不能为空！',
           			timeout:3000,
           			showType:'slide'
           		});
           		return false;
			}
    		//如果验证成功，就通过ajax方式将数据传到后台并保存
    		var data = $.serializeObject($('#addForm'));
    		$.ajax({
    		     type: 'POST',
    		     url: "/HR/params/saveParam" ,
    		     data: data ,
    		     dataType: "json",
    		     success: function(result){
    		    	 var success = result.success;
    		    	 if(success == "true"){
						parent.$.messager.show({
	               			title:'提示',
	               			msg:'参数创建成功！',
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
})
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