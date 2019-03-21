$(document).ready(function(){
	var height = $("#center").height() - 150;
	$('#businessList').treegrid({
		height:height,
		width:'auto',
	    url:'getBusinessList',  
	    rownumbers:true,	//显示行数
		fitColumns:true,	//自适应列的大小，防止出现水平滚动条
		loadMsg:"正在加载数据，请稍等...",	//载入等待时信息
		nowrap:true,	//数据长度超过列宽时自动截取，默认为true，否则换行
		pagination:true,	//显示分页工具栏
		pageNumber:1,	//设置分页属性时，初始化页码，默认为1
		pageSize:10,	//设置分页属性时，初始化每页记录数，默认为10
		pageList:[10,20,30,40,50],		//设置分页属性时，初始化每页记录数列表，默认为[10,20,30,40,50]
		striped:true,	//设置斑马线
		singleSelect:true,	//只允许选择一行
		collapsible: true,
		idField:"code",
		treeField:"code",
		columns : [[{
			field:'code',
			align:'left',
			title:'业务编号',
			width:138,
			formatter: function(value,row,index){
				if(row["type"] == "02"){
					return "";
				}
				return value;
			},
			halign:"center",
			align:"center"
		},{
			field:'company', 
			align:'left',
			title:'所属单位',
			width:65,
			formatter: function(value,row,index){
				if(value != null){
					return "<span title='" + value + "'>" + value + "</span>";
				}
			},
			halign:"center",
			align:"center"
		},{
			field:'bigBusiness', 
			align:'left',
			title:'业务大类',
			width:65,
			halign:"center",
			align:"center"
		},{
			field:'smallBusiness', 
			align:'left',
			title:'业务小类',
			width:65,
			halign:"center",
			align:"center"
		},{
			field:'serviceTarget', 
			align:'left',
			title:'服务对象',
			width:55,
			halign:"center",
			align:"center"
		},{
			field:'contact', 
			align:'left',
			title:'联系方式',
			width:83,
			halign:"center",
			align:"center"
		},{
			field:'applyMan', 
			align:'left',
			title:'申报人',
			width:55,
			halign:"center",
			align:"center"
		},{
			field:'operateStatus', 
			align:'left',
			title:'办理动态',
			width:55,
			halign:"center",
			align:"center"
		},{
			field:'operateTime', 
			align:'left',
			title:'操作时间',
			width:72,
			halign:"center",
			align:"center"
		},{
			field:'operateType', 
			align:'left',
			title:'操作类型',
			width:65,
			halign:"center",
			align:"center"
		},{
			field:'operateUser', 
			align:'left',
			title:'操作人',
			width:43,
			formatter: function(value,row,index){
				if(value != null){
					return "<span title='" + value + "'>" + value + "</span>";
				}
			},
			halign:"center",
			align:"center"
		},{
			field:'businessContent', 
			align:'left',
			title:'记录内容',
			width:270,
			formatter: function(value,row,index){
				if(value != null){
					return "<span title='" + value + "'>" + value + "</span>";
				}
			},
			halign:"center",
			align:"left"
		},{
			field:'remarks', 
			align:'left',
			title:'备注',
			width:125,
			formatter: function(value,row,index){
				if(value != null){
					return "<span title='" + value + "'>" + value + "</span>";
				}
			},
			halign:"center",
			align:"left"
		}]]
	});
	
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
		editable:false
	});
	//办理状态
	$('#operateStatus').combobox({
		valueField:'id',
		textField:'name',
		url:"../params/getOperateStatus",
		editable:false,
		panelHeight:"auto"
	});
	
	$("#add_button").bind("click",function(){
		var selects = $('#businessList').treegrid("getSelected");
		if(selects == null || selects.length == 0){
			$.messager.show({
       			title:'提示',
       			msg:'请先选择一条数据！',
       			timeout:3000,
       			showType:'slide'
       		});
			return false;
		}
		var id = selects["id"];
		var type = selects["type"];
		if(type == "02"){
			$.messager.show({
       			title:'提示',
       			msg:'请选择一条主任务！',
       			timeout:3000,
       			showType:'slide'
       		});
			return false;
		}
		createWithoutCancle("添加子业务","/HR/businessDeal/addChild?id=" + id,690,400);
	});
	
	$("#edit_button").bind("click",function(){
		var selects = $('#businessList').treegrid("getSelected");
		if(selects == null || selects.length == 0){
			$.messager.show({
       			title:'提示',
       			msg:'请先选择一条数据！',
       			timeout:3000,
       			showType:'slide'
       		});
			return false;
		}
		var id = selects["id"];
		var type = selects["type"];
		if(type == "01"){
			//主任务要判断其是否有子任务，有则不允许修改
			var child = selects.children;
			if(child == null || child.length == 0){
				createWithoutCancle("编辑主任务","/HR/businessDeal/editMain?id=" + id,690,400);
			}else{
				$.messager.show({
	       			title:'提示',
	       			msg:'该主任务已经包含子任务，不允许被修改！',
	       			timeout:3000,
	       			showType:'slide'
	       		});
			}
		}else{
			createWithoutCancle("编辑子业务","/HR/businessDeal/editChild?id=" + id,690,400);
		}
	});
	$("#delete_button").bind("click",function(){
		var selects = $('#businessList').treegrid("getSelected");
		if(selects == null || selects.length == 0){
			$.messager.show({
       			title:'提示',
       			msg:'请先选择一条数据！',
       			timeout:3000,
       			showType:'slide'
       		});
			return false;
		}
		$.messager.confirm('确认对话框', '确认要删除业务吗？', function(r){
			if (r){
				var id = selects["id"];
				var type = selects["type"];
				$.ajax({
					type:"post",
					dataType:"json",
					url:"/HR/businessDeal/deleteBusiness",
					data:{
						"id":id,
						"type":type
					},
					success:function(data){
						var success = data.success;
						if(success == "true"){
							$.messager.show({
				       			title:'提示',
				       			msg:'删除业务成功！',
				       			timeout:3000,
				       			showType:'slide'
				       		});
							doSearch();
						}else{
							$.messager.show({
				       			title:'提示',
				       			msg:data.errorMsg,
				       			timeout:3000,
				       			showType:'slide'
				       		});
						}
					}
				});
			}
		});
	});
	
	$("#print_button").bind("click",function(){
		var selects = $('#businessList').treegrid("getSelected");
		if(selects == null || selects.length == 0){
			$.messager.show({
       			title:'提示',
       			msg:'请先选择一条数据！',
       			timeout:3000,
       			showType:'slide'
       		});
			return false;
		}
		var id = selects["id"];
		$.ajax({
			type:"post",
			dataType:"json",
			url:"/HR/businessDeal/getBusiness",
			data:{
				"id":id
			},
			success:function(data){
				printMydiv(data);
			}
		});
	});
});
function doSearch(){
	var t = $.serializeObject($('#searchForm'));
	$('#businessList').treegrid('load', t); 
}
function reset1(){
	$("#serviceTarget").val("");
	$('#company,#bigBusiness,#smallBusiness,#operateType,#operateStatus').combobox("reset");
	$("#startTime,#endTime").datebox("reset");
	$("#updated").attr("checked",false);
	doSearch();
}
function printMydiv(data){
	Date.prototype.pattern=function(fmt) {         
	    var o = {         
	    "M+" : this.getMonth()+1, //月份         
	    "d+" : this.getDate(), //日         
	    "h+" : this.getHours()%12 == 0 ? 12 : this.getHours()%12, //小时         
	    "H+" : this.getHours(), //小时         
	    "m+" : this.getMinutes(), //分         
	    "s+" : this.getSeconds(), //秒         
	    "q+" : Math.floor((this.getMonth()+3)/3), //季度         
	    "S" : this.getMilliseconds() //毫秒         
	    };         
	    var week = {         
	    "0" : "/u65e5",         
	    "1" : "/u4e00",         
	    "2" : "/u4e8c",         
	    "3" : "/u4e09",         
	    "4" : "/u56db",         
	    "5" : "/u4e94",         
	    "6" : "/u516d"        
	    };         
	    if(/(y+)/.test(fmt)){         
	        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));         
	    }         
	    if(/(E+)/.test(fmt)){         
	        fmt=fmt.replace(RegExp.$1, ((RegExp.$1.length>1) ? (RegExp.$1.length>2 ? "/u661f/u671f" : "/u5468") : "")+week[this.getDay()+""]);         
	    }         
	    for(var k in o){         
	        if(new RegExp("("+ k +")").test(fmt)){         
	            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));         
	        }         
	    }         
	    return fmt;         
	};     
    var new_iframe = document.createElement('IFRAME');
    var doc = null;
    new_iframe.setAttribute('style', 'width:0px;height:0px;position:absolute;left:-2000px;top:-2000px;');
    new_iframe.setAttribute('align', 'center');
    document.body.appendChild(new_iframe);
    doc = new_iframe.contentWindow.document;
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
	for(i in data){
		if(tablehtml.indexOf(i) != -1){
			if(data[i] == null || data[i] == "null"){
				tablehtml = tablehtml.replace(i,"");
			}else{
				if(i == "operateTime"){
					var operateTime = new Date(data["operateTime"]).pattern("yyyy-MM-dd");
					tablehtml = tablehtml.replace(i,operateTime);
				}else{
					tablehtml = tablehtml.replace(i,data[i]);
				}
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
//导出
function exportExcelBus(){
	var queryParams = $.serializeObject($('#searchForm'));
	var params = '';
	$.each(queryParams, function(key, val){
		params+=key+'='+val+'&';
	}); 
	window.location.href = '/HR/businessDeal/exportExcel?'+params;
}