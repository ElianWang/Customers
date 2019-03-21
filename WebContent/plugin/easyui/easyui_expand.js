jQuery(document).ready(function(){
	/*---------------------------扩展验证条件---------------------------------------------------*/
	//扩展checkbox的验证
	$.extend($.fn.validatebox.defaults.rules,{
		checkbox:{
			validator: function(value, param){
				var frm = param[0], groupname = param[1], checkNum = 0;
				$('input[name="' + groupname + '"]', document[frm]).each(function(){
					if (this.checked) checkNum++;
				});
				return checkNum > 0
			},
			message: "至少选择一项"
		}
	});
	
	//扩展radio的验证
	$.extend($.fn.validatebox.defaults.rules,{
		radio:{
			validator: function(value, param){
				var frm = param[0], groupname = param[1], isCheck = false;
				$('input[name="' + groupname + '"]', document[frm]).each(function(){
					if (this.checked) {isCheck = true; return false};
				});
				return isCheck
			},
			message: "请选择一项"
		}
	});
	
	//扩展combobox的验证，检查值域为id，模仿autocomplete功能
	$.extend($.fn.validatebox.defaults.rules,{
		comboboxAc:{
			validator: function(value, param){
				var selectId = param[1], isAc = false;
				$("#"+selectId).each(function(){
					/*if (parseInt($(this).combobox('getValue')) >= 0 && $(this).combobox('getValue') != value) {isAc = true; return false};*/
					/* 新版本eaysui的combobox方法getValue取值时，如果未匹配，取不到值 */
					if ($(this).combobox('getValue') != undefined) {isAc = true; return false};
				});
				return isAc
			},
			message: "请选择一项"
		}
	});
	
	//扩展validation的验证，检查值为“-”或大于等于0的整数，如果是大于等于0的整数，启用param中的元素并设置为必填
	$.extend($.fn.validatebox.defaults.rules,{
		inputChkVal:{
			validator: function(value, param){
				var isLegal = false, rgExp = /(^\d*$)/, ids = param[0];
				if(rgExp.test(value)){
					for (i in ids){
						$(ids[i]).removeAttr("disabled");
						$(ids[i]).validatebox({required:true});
					};
					isLegal = true;
				}else if(value == '-'){
					for (i in ids){
						$(ids[i]).val("");
						$(ids[i]).validatebox({required:false});
						$(ids[i]).attr("disabled","disabled");
					};
					isLegal = true;
				}else{
					for (i in ids){
						$(ids[i]).val("");
						$(ids[i]).validatebox({required:false});
						$(ids[i]).attr("disabled","disabled");
					};
				};
				return isLegal
			},
			message: "必须填写'-'或者大于等于0的整数"
		}
	});	
	
	//扩展datebox的验证，比较两个日期的大小
	$.extend($.fn.validatebox.defaults.rules,{
		compareDate:{
			validator: function(value, param){
				var isLegal = false, compId = param[0], compType = param[1];
				switch(compType){
					case "<=":
						if(value <= $(compId).datebox('getValue')){
							isLegal = true;
						};
					case ">=":
						if(value >= $(compId).datebox('getValue')){
							isLegal = true;
						};	
				};
				return isLegal
			},
			message: "日期输入范围有误"
		}
	});	
	
	//扩展输入框验证，比较两个日期的大小
	$.extend($.fn.validatebox.defaults.rules,{
		compareDateInput:{
			validator: function(value, param){
				var isLegal = false, compId = param[0], compType = param[1];
				if($(compId).get(0).value == "") {
					return true;
				}
				switch(compType){
					case '<=':
						if(value <= $(compId).get(0).value){
							isLegal = true;
						};
					case '>=':
						if(value >= $(compId).get(0).value){
							isLegal = true;
						};
				};
				return isLegal
			},
			message: "日期输入范围有误"
		}
	});	
	
	//验证两个输入框的内容是否一致
	$.extend($.fn.validatebox.defaults.rules,{
		equalTo:{
			validator:function(value,param){
				var paw = param[0];
				var isEqual = false;
				if(value == $(paw).get(0).value){
					isEqual = true;
				}else{
					isEqual = false;
				}
				return isEqual;
			},
			message:"两次输入的密码不一致"
		}
	});
	
	//字段长度最大值验证
	$.extend($.fn.validatebox.defaults.rules,{
		maxLength:{
			validator:function(value,param){
				return value.length <= param[0];
			},
			message:"必须{0}字符以内"
		}
	});
	
	/*------------------------------------------------------------------------------------------------*/
	
	

});