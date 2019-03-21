$(document).ready(function(){
	//所属单位
	$('#company').combobox({    
	    url:'../params/getParamsByType?type=0607',   
	    panelHeight:'auto',
	    valueField:'id',    
	    textField:'name'   
	});  
	//户口性质  
	$('#residenceType').combobox({    
	    url:'../params/getParamsByType?type=0601',    //Params.java
	    panelHeight:'auto',
	    valueField:'id',    
	    textField:'name'   
	}); 
	//人员状态
	$('#personStatus').combobox({    
	    url:'../params/getParamsByType?type=0602',    
	    panelHeight:'auto',
	    valueField:'id',    
	    textField:'name'   
	});
	//职级
	$('#rank').combobox({    
	    url:'../params/getParamsByType?type=0603',    
	    panelHeight:'auto',
	    valueField:'id',    
	    textField:'name'   
	});
	//档案存放情况
	$('#docDeposit').combobox({    
	    url:'../params/getParamsByType?type=0604',    
	    panelHeight:'auto',
	    valueField:'id',    
	    textField:'name'   
	});
	//单位集体户
	$('#unitColhouse').combobox({    
	    url:'../params/getParamsByType?type=0605',    
	    panelHeight:'auto',
	    valueField:'id',    
	    textField:'name'   
	});
	//党员关系
	$('#partyRelation').combobox({    
	    url:'../params/getParamsByType?type=0606',    
	    panelHeight:'auto',
	    valueField:'id',    
	    textField:'name'   
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
    		     url: "/HR/document/saveDoc" ,
    		     data: data ,
    		     dataType: "json",
    		     success: function(result){
    		    	 var success = result.success;
    		    	 if(success == "true"){
						parent.$.messager.show({
	               			title:'提示',
	               			msg:'档案创建成功！',
	               			timeout:3000,
	               			showType:'slide'
	               		});
						$("#addForm").find("input").each(function(){
							$(this).attr("readonly","readonly");
						});
						$("#company").combobox("disable");
						$("#intoTime").combobox("disable");
						$("#outTime").combobox("disable");
						
						$("#saveBtn").hide();
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
	
	$("#cancleBtn").bind("click",function(){
		parent.window.location = "../index";
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