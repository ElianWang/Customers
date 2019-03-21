function openSecondMenu(id){
	$(".topMemu").find("div").each(function(){
		$(this).css("display","none");
	});
	$("#" + id).css("display","inline");
}
function addTab(title, href,refresh){  
    var tt = $('#maintabs');  
    if (tt.tabs('exists', title)){//如果tab已经存在,则选中并刷新该tab     
        tt.tabs('select', title);  
//        refreshTab({tabTitle:title,url:href});  
        if(refresh == "true" || refresh == true){
        	var content="";
            if (href){  
                content = '<iframe scrolling="no" frameborder="0"  src="'+href+'" style="width:100%;height:100%;"></iframe>';  
            } else {  
                content = '未实现';  
            }  
            var tab = $('#maintabs').tabs('getSelected');  
            $('#maintabs').tabs('update', {
            	tab: tab,
            	options: {
            		title:title,  
                    closable:true,  
                    content:content,  
                    iconCls:'icon-default'  
            	}
            });
        }
    } else {  
        var content="";
        if (href){  
            content = '<iframe scrolling="no" frameborder="0"  src="'+href+'" style="width:100%;height:100%;"></iframe>';  
        } else {  
            content = '未实现';  
        }  
        tt.tabs('add',{  
            title:title,  
            closable:true,  
            content:content,  
            iconCls:'icon-default'  
        });  
    }  
}  
/**     
 * 刷新tab 
 * @cfg  
 *example: {tabTitle:'tabTitle',url:'refreshUrl'} 
 *如果tabTitle为空，则默认刷新当前选中的tab 
 *如果url为空，则默认以原来的url进行reload 
 */  
function refreshTab(cfg){  
    var refresh_tab = cfg.tabTitle?$('#mytabs').tabs('getTab',cfg.tabTitle):$('#mytabs').tabs('getSelected');  
    if(refresh_tab && refresh_tab.find('iframe').length > 0){  
    var _refresh_ifram = refresh_tab.find('iframe')[0];  
    var refresh_url = cfg.url?cfg.url:_refresh_ifram.src;  
    //_refresh_ifram.src = refresh_url;  
    _refresh_ifram.contentWindow.location.href=refresh_url;  
    }  
}  
     
     /*window.onload=function()
        {
            var pages=[{pageName:"task",title:"任务下发",icon:"icon-task"},{pageName:"track",title:"任务跟踪",icon:"icon-track"},{pageName:"report",title:"数据分析",icon:"icon-report"}];
            for (var i = 0; i < pages.length; i++) {
                var href= PROJECT_URL + "/drilling/"+pages[i].pageName;
                addTab(pages[i].title,href,pages[i].icon);
            }
        };*/