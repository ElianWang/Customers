$(document).ready(function(){
	$('#company').combobox({    
		url:'../params/getParamsByType?type=0607',   
	    valueField:'id',    
	    textField:'name',
	    panelHeight:'auto'
	});
	
	$('#residenceType').combobox({    
	    url:'../params/getParamsByType?type=0601',    
	    valueField:'id',    
	    textField:'name',
	    panelHeight:'auto'
	});
	$('#personStatus').combobox({    
	    url:'../params/getParamsByType?type=0602',    
	    valueField:'id',    
	    textField:'name',
	    panelHeight:'auto'
	});
	$('#rank').combobox({    
	    url:'../params/getParamsByType?type=0603',    
	    valueField:'id',    
	    textField:'name',
	    panelHeight:'auto'
	});
	$('#docDeposit').combobox({    
	    url:'../params/getParamsByType?type=0604',    
	    valueField:'id',    
	    textField:'name',
	    panelHeight:'auto'
	});
	$('#unitColhouse').combobox({    
	    url:'../params/getParamsByType?type=0605',    
	    valueField:'id',    
	    textField:'name',
	    panelHeight:'auto'
	});
	
	$('#partyRelation').combobox({    
	    url:'../params/getParamsByType?type=0606',    
	    valueField:'id',    
	    textField:'name',
	    panelHeight:'auto'
	});
	//保存用户信息
	$("#saveBtn").bind("click",function(){
		if(jQuery("form").form('validate')){
			var remarks = $("#remarks").val();
			if(remarks != null && remarks.length > 200){
				parent.$.messager.show({
           			title:'提示',
           			msg:'备注长度不能超过200个字！',
           			timeout:3000,
           			showType:'slide'
           		});
           		return false;
			}
			var idNum = $("#idNum").val();
			if(idNum != null && idNum.length != 18){
				parent.$.messager.show({
           			title:'提示',
           			msg:'身份证号长度必须为18位！',
           			timeout:3000,
           			showType:'slide'
           		});
           		return false;
			}
    		//如果验证成功，就通过ajax方式将数据传到后台并保存
    		var data = $.serializeObject($('#addForm'));
    		$.ajax({
    		     type: 'POST',
    		     url: "/HR/document/updateDoc" ,
    		     data: data ,
    		     dataType: "json",
    		     success: function(result){
    		    	 var success = result.success;
    		    	 if(success == "true"){
						parent.$.messager.show({
	               			title:'提示',
	               			msg:'档案更新成功！',
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