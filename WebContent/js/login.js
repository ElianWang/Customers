$(document).ready(function(){
	
	var errorMsg = $("#errorMsg").val();
	if(errorMsg != null && errorMsg != ""){
		$.messager.show({
			title:'消息提示',
			msg:errorMsg,
			timeout:5000,
			showType:'slide'
		});
	}
	
//	$("#login").bind("click",function(){
//		
//	})
})