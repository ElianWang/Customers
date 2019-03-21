$(document).ready(function(){
	
	queryOrgno();
	//保存用户信息
	$("#saveProgress").bind("click",function(){
    		//如果验证成功，就通过ajax方式将数据传到后台并保存
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
    		//如果验证成功，就通过ajax方式将数据传到后台并保存
    		var data = $.serializeObject($('#addForm_pro'));
    		$.ajax({
    		     type: 'POST',
    		     url: "/HR/progress/saveProgress",
    		     data: data ,
    		     dataType: "json",
    		     success: function(result){
    		    	 var success = result.success;
    		    	 if(success == "true"){
						parent.$.messager.show({
	               			title:'提示',
	               			msg:'流程创建成功！',
	               			timeout:3000,
	               			showType:'slide'
	               		});
						$("#addForm_pro").find("input").each(function(){
							$(this).attr("readonly","readonly");
						});
						$("#company").combobox("disable");
						
						$("#saveProgress").hide();
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
	
	$("#closeProgress").bind("click",function(){
			parent.window.location = "../index";
	});
});

//查询orgNo
function queryOrgno(){
	$('#company').combobox({
		url:'queryOrgno',
		valueField:'code',
		textField:'name'
	});
}


 