$(document).ready(function(){
	
	$("#save_button").bind("click",function(){
		var data = $.serializeObject($('#searchForm'));
		var remarks = $("#remark").val();
		if(remarks != null && remarks.length > 200){
			parent.$.messager.show({
       			title:'提示',
       			msg:'说明长度不能超过200个字！',
       			timeout:3000,
       			showType:'slide'
       		});
       		return false;
		}
		$.ajax({
		     type: 'POST',
		     url: "/HR/util/newback",
		     data: data ,
		     dataType: "json",
		     success: function(result){
		    	 var success = result.success;
		    	 if(success == "true"){
					parent.$.messager.show({
               			title:'提示',
               			msg:'备份成功！',
               			timeout:3000,
               			showType:'slide'
               		});
					getOpener().doSearch();
					closeDialog();
		    	 }else{
		    		 parent.$.messager.show({
             			title:'提示',
             			msg:'备份失败',
             			timeout:3000,
             			showType:'slide'
             		});
		    	 }
		     }
		});
	});
		
	$("#cancel_button").bind("click",function(){
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