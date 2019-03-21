$(document).ready(function(){
	$("#print_button").hide();
	//业务大类
	$('#bigBusiness').combobox({
		valueField:'id',
		textField:'name',
		url:"../params/getBigBusiness",
		onSelect:function(rec){
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
		},
		editable:false
	});
	//业务小类
	$('#smallBusiness').combobox({
		valueField:'id',
		textField:'name',
		editable:false
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
		var data = $.serializeObject($('#searchForm'));
		$.ajax({
			type:"post",
			url: "/HR/businessDeal/saveBusinessDeal",
    	 	dataType:"json",
    	 	data:data,
    	 	success:function(result){
    	 		var success = result.success;
		    	 if(success == "true"){
					$.messager.show({
              			title:'提示',
              			msg:'业务录入成功！',
              			timeout:3000,
              			showType:'slide'
              		});
					$("#serviceTarget,#contact,#applyMan,#operateUser,#businessContent,#remarks").attr("readonly","readonly");
					$("#company,#bigBusiness,#smallBusiness,#operateType").combobox("disable");
					$("#operateTime").datebox("disable");
					
					$("#ok_button").hide();
					$("#print_button").show();
		    	 }else{
		    		 $.messager.show({
            			title:'提示',
            			msg:result.errorMsg,
            			timeout:3000,
            			showType:'slide'
            		});
		    	 }
    	 	}
		});
	});
	
	$("#cancle_button").bind("click",function(){
		parent.location.reload();
	})
});
function printMydiv(){
    var new_iframe = document.createElement('IFRAME');
    var doc = null;
    new_iframe.setAttribute('style', 'width:0px;height:0px;position:absolute;left:-2000px;top:-2000px;');
    new_iframe.setAttribute('align', 'center');
    document.body.appendChild(new_iframe);
    doc = new_iframe.contentWindow.document;
    inputElements = document.getElementsByTagName("input");
    tablehtml="<table style=\"border-collapse: separate; border-spacing: 10px;\"><tbody>" +
	"<tr>	<td><label>业务编号：</label>	</td>	<td class=\"left-td\">code</td><td> <label>所属单位：</label>	</td>" +
	"<td>company</td></tr><tr>	<td><label>业务大类：</label>	</td>	<td class=\"left-td\">bigBusiness</td>" +
	"	<td><label>业务小类：</label>	</td>	<td>smallBusiness</td></tr><tr>	<td><label>服务对象：</label>	" +
	"</td>	<td class=\"left-td\">serviceTarget</td>	<td><label>联系方式：</label>	</td>	" +
	"<td>contact</td></tr><tr>	<td><label>申报人：</label>	</td>	<td>applyMan</td></tr><tr>	" +
	"<td><label>业务记录：</label>	</td>	<td colspan=3><textarea rows='contentRows' cols='50'>businessContent</textarea>	</td></tr>" +
	"<tr>	<td><label>备注说明：</label>	</td>	<td colspan=3><textarea rows='remarkRows' cols='50'>remarks</textarea>	</td>	</tr>" +
	"<tr>	<td><label>操作方式：</label>	</td>	<td class=\"left-td\">operateType</td>	<td>" +
	"<label>操作时间：</label>	</td>	<td>operateTime</td></tr><tr>	<td><label>办理动态：</label>	</td>	" +
	"<td class=\"left-td\">operateStatus</td>	<td><label>操作人员：</label>	</td>	<td>operateUser</td>" +
	"</tr>	</tbody></table>";
    var data = $.serializeObject($('#searchForm'));
    data.company = $("#company").combobox("getText");
    data.bigBusiness = $("#bigBusiness").combobox("getText");
    data.smallBusiness = $("#smallBusiness").combobox("getText");
    data.operateType = $("#operateType").combobox("getText");
    data.operateStatus = $("#operateStatusName").val();
    data.operateTime = $("#operateTime").datebox("getValue");
	for(i in data){
		if(tablehtml.indexOf(i) != -1){
			if(data[i] == null || data[i] == "null"){
				tablehtml = tablehtml.replace(i,"");
			}else{
				tablehtml = tablehtml.replace(i,data[i]);
			}
			if(i == "businessContent"){
				var val = data[i];
				var a = 0;
				var b = 0;
				if(val != null){
					a = val.split("\n").length;
					b = parseInt(val.length/20);
				}
				if(b > a){
					a = b;
				}
				tablehtml = tablehtml.replace("contentRows",a + 1);
			}
			if(i == "remarks"){
				var val = data[i];
				var a = 0;
				var b = 0;
				if(val != null){
					a = val.split("\n").length;
					b = parseInt(val.length/20);
				}
				if(b > a){
					a = b;
				}
				tablehtml = tablehtml.replace("remarkRows",a + 1);
			}
		}
	}
	doc.write("<html><head><style media=\"print\">@page {size: auto;margin: 0mm;}</style><style>textarea{overflow:hidden;outline:none;resize:none;border:0;font-family: unset;font-size: medium;}</style></head><body>" +
			"<div style='width:100%;height:auto;min-width:1100px;margin:0px auto;'align='center'>"+tablehtml+"</div></body></html>");
    doc.close();
    new_iframe.contentWindow.focus();
    new_iframe.contentWindow.print();
    //document.body.removeChild(iframe);
}