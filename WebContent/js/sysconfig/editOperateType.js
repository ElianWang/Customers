$(document).ready(function(){
	$("#parentCode").combobox({
		url:'getOperateStatus',    
	    valueField:'id',    
	    textField:'name',
	    panelHeight:'auto',
	    disabled:true
	});
	//保存用户信息
	$("#saveBtn").bind("click",function(){
		if(jQuery("form").form('validate')){
			var name = $("#name").val();
			if(name == null || name == ""){
				parent.$.messager.show({
           			title:'提示',
           			msg:'新参数名称不能为空！',
           			timeout:3000,
           			showType:'slide'
           		});
           		return false;
			}
			var parentCode = $("#parentCode").combobox("getValue");
			if(parentCode == null || parentCode == ""){
				parent.$.messager.show({
           			title:'提示',
           			msg:'办理状态不能为空！',
           			timeout:3000,
           			showType:'slide'
           		});
           		return false;
			}
    		//如果验证成功，就通过ajax方式将数据传到后台并保存
    		var data = $.serializeObject($('#addForm'));
    		$.ajax({
    		     type: 'POST',
    		     url: "/HR/params/updateParam" ,
    		     data: data ,
    		     dataType: "json",
    		     success: function(result){
    		    	 var success = result.success;
    		    	 if(success == "true"){
						parent.$.messager.show({
	               			title:'提示',
	               			msg:'参数更新成功！',
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
    			msg:'更新失败，请将页面中的必填项输入完整！',
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