	$(function(){
		var id = $("#customerId").val();
		selectData(id);
		selectOrder(id);
	})
	function selectOrder(id){
		$('#orderInfo').datagrid({   
//			height:$("#showCustInfo").height() -$("#kk").height()-230 ,
			width:'auto',
		    url:'orderInfo?id='+id,  
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
		    columns:[[   
		        {field:'orderTime',title:'创建时间',width:120,align:'center'}, 
		        {
		        		field:'product',
		        		title:'产品',
		        		width:100,
		        		align:'center'
		        	}, 
		        {field:'type',title:'产品类型',width:100,align:'center'},
		        {field:'area',title:'面积',width:100,align:'center'},
		        {field:'price',title:'单价',width:100,align:'center'},
		        {field:'totalAmount',title:'总金额',width:100,align:'center'},
		        {field:'remarks',title:'备注',width:100,align:'center'},
		    ]]   
		});
	}
	function selectData(id){
		$('#trackInfo').datagrid({   
//			height:$("#showCustInfo").height() -$("#kk").height()-230 ,
			width:'auto',
		    url:'trackInfo?id='+id,  
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
		    columns:[[   
		        {field:'createTime',title:'创建时间',width:100,align:'center'}, 
		        {
		        		field:'content',
		        		title:'记录',
		        		width:200,
		        		align:'center',
		        		formatter:function(value){
		        			if(value != null){
		        				return "<span title='" + value + "'>" + value + "</span>";
		        			}
		        		}
		        	}, 
		        {field:'createUser',title:'创建人',width:100,align:'center'}
		    ]]   
		});
	}
	function showCust(id){
		createdetailwindow("录入跟踪信息","addCust/addTrack?id=" + id,500,200);
		
	}
	
	function addOrder(id){
		createdetailwindow("录入订单信息","addCust/addOrder?id=" + id,690,400);
	}
	
	function editTrack(){
		var customerId = $("#customerId").val();
		var content = $("#content").val();
		$.ajax({
			type:"post",
			url:"editTrack",
			dataType:"json",
			data:{
				customerId:customerId,
				content:content
				} ,
			success:function(result){
				var success = result.success;
				if(success){
					parent.$.messager.show({
		      			title:'提示',
		      			msg:'记录添加成功！',
		      			timeout:3000,
		      			showType:'slide'
		      		});
					getOpener().refreshTrack();
					closeDialog();
				}
			}
				
		})
	}
	
	function submitOrder(){
		var customerId = $("#cusId").val();
		var product = $("#product").val();
		var type = $("#type").val();
		var area = $("#area").val();
		var price = $("#price").val();
		var totalAmount = $("#totalAmount").val();
		var remarks = $("#remarks").val();
		$.ajax({
			type:"post",
			url:"addCustOrder",
			dataType:"json",
			data:{
				customerId:customerId,
				product:product,
				type:type,
				area:area,
				price:price,
				totalAmount:totalAmount,
				remarks:remarks
				},
			success:function(result){
				var success = result.success;
				if(success){
					parent.$.messager.show({
		      			title:'提示',
		      			msg:'记录添加成功！',
		      			timeout:3000,
		      			showType:'slide'
		      		});
					getOpener().refreshTrack();
					closeDialog();
				}
			}
				
		})
	}
	function closeDialog(){
		var api = frameElement.api;
		api.close();
	}

	function getOpener(){
		var tab = parent.$('#maintabs').tabs('getSelected');
		var index = parent.$('#maintabs').tabs('getTabIndex',tab);
		var opener = parent.$("iframe[name=lhgDialog]")[0].contentWindow;
		return opener;
	}
	
	function refreshTrack(){
		$('#trackInfo').datagrid('reload');
		$('#orderInfo').datagrid('reload');
	}
	
