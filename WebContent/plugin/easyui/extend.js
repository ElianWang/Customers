jQuery.extend({
	//将form表单中的参数序列化为hash
	serializeObject: function(form) {
		var o={};
		$.each(form.serializeArray(),function(index){
			if(o[this['name']]){
				o[this[['name']]]=o[this[['name']]]+","+this['value'];
			}else{
				o[this[['name']]]=this['value'];
			}
		});
		return o;
	},
	
	/**
	 * 功能：easyui数据表格动态分页
	 * 参数：
	 * 	div：表格所在div的唯一标示（一般是ID）
	 * 	table：表格的唯一标示（一般是ID）
	 * 	limit：动态分页的上限
	 */
	dynamicPaginate: function(div, table, limit) {
		var total = $(table).datagrid('getData').total;
		if (total <= 10 && $(table).datagrid('getPager').data('pagination') != undefined) {
			//总行数小于等于limit，隐藏分页栏
			$(div).find(".pagination").hide();
		} else if (total > 10) {
			//总行数大于limit，显示分页栏
			$(div).find(".pagination").show();
		}
	},
	
	/**
	 * 功能：将对象数组中的元素取出组成数组
	 */
	ObjectArray2AttrArray: function(ObjectArray, attr) {
		var AttrArray = [];
		$.each(ObjectArray, function(n, object) {
			AttrArray[n] = object[attr];
		})
		return AttrArray;
	},
	
	/**
	 * 功能：获取url
	 */
	getUrl: function() {
		var href = window.location.pathname;
		//将项目名称截去
		href = href.replace(href.match(/^\/[A-Za-z]+\//)[0], "");
		//如果存在逗号，将逗号以后的内容截去
		var index = href.indexOf(";");
		if (index == -1) {
			return href;
		} else {
			return href.substring(0, index);
		}
	},
	
	/**
	 * 将字符串转换为时间
	 */
	string2Time: function(timeString) {
		var s = timeString.split(" ");
		var s1 = s[0].split("-");
		var s2 = s[1].split(":");
		return d = new Date(s1[0], s1[1] - 1, s1[2], s2[0], s2[1], s2[2]);
	},
	
	/**
	 * 功能：设置select多选框选项，如果没有选项，添加选项
	 * @param: selDomId 多选框id
	 * @param: optValue 选项值
	 */
	setSelOpt: function(selDomId, optValue) {
		var option = $("#" + selDomId + " option[value=" + optValue + "]");
		if (option.val() == undefined) {
			//选项不存在，添加选项
			$("#" + selDomId).append('<option value=' + optValue + ' selected="selected"></option>');
		}
	},
	
	/**
	 * 功能：设置select多选框选项，如果已有选项，删除选项
	 * @param: selDomId 多选框id
	 * @param: optValue 选项值
	 */
	setUnSelOpt: function(selDomId, optValue) {
		var option = $("#" + selDomId + " option[value=" + optValue + "]");
		if (option.val() != undefined) {
			//选项存在，删除选项
			option.remove();
		}
	},
	
	/**
	 * 功能：添加ids对应的选项
	 * @param: selDomId 多选框id
	 * @param: optValues 选项值数组
	 */
	setSelOptAllSelected: function(selDomId, optValues) {
		$.each(optValues, function(n, v) {
			var option = $("#" + selDomId + " option[value=" + v + "]");
			if (option.val() == undefined) {
				//选项不存在，添加选项
				$("#" + selDomId).append('<option value=' + v + ' selected="selected"></option>');
			}
		});
	},
	
	/**
	 * 功能：删除ids对应的选项
	 * @param: selDomId 多选框id
	 * @param: optValues 选项值数组
	 */
	setSelOptAllUnSelected: function(selDomId, optValues) {
		$.each(optValues, function(n, v) {
			var option = $("#" + selDomId + " option[value=" + v + "]");
			if (option.val() != undefined) {
				//选项存在，删除选项
				option.remove();
			}
		});
	},
	
	/**
	 * 功能：table添加行
	 * @param: tbodyId 表格body的id
	 * @param: trId 行id
	 * @param: tdArr 列数据
	 */
	addTBodyList: function(tbodyId, trId, tdArr) {
		var tr = $("#" + tbodyId).find("#" + trId)[0];
		if (tr == undefined) {
			//行不存在，添加行
			var html = "<tr id=" + trId + ">";
			$.each(tdArr, function(n ,v) {
				html += "<td>" + v + "</td>";
			});
			html += "</tr>";
			$("#" + tbodyId).append(html);
		}
	},
	
	/**
	 * 功能：table删除行
	 * @param: tbodyId 表格body的id
	 * @param: trId 行id
	 */
	delTBodyList: function(tbodyId, trId) {
		var tr = $("#" + tbodyId).find("#" + trId)[0];
		if (tr != undefined) {
			//行存在，删除行
			$(tr).remove();
		}
	},
	
	/**
	 * 功能：table批量添加行
	 * @param: listId dataTable列表id
	 * @param: selId 多选框id
	 * @param: tbodyId 表格body的id
	 * @param: trId 行id
	 * @param: ids 序号数组
	 * @param: names 名称数组
	 */
	addAllTBodyList: function(listId, selId, tbodyId, trId, ids, names) {
		if (ids.length != names.length) {
			return;
		}
		$.each(ids, function(n, v) {
			var tr = $("#" + tbodyId).find("#" + trId + "_" + v)[0];
			if (tr == undefined) {
				//行不存在，添加行
				var html = '<tr id=' + trId + '_' + v + '>';
				html += '<td>' + ids[n] + '</td>';
				html += '<td>' + names[n] + '</td>';
				html += '<td><a href="javascript:void(0)" onclick=uncheck("' + listId + '","' + selId + '",' + ids[n] + ',' + 'this' + ')>取消</a>'
				html += '</tr>';
				$("#" + tbodyId).append(html);
			}
		});
	},
	
	/**
	 * 功能：table删除添加行
	 * @param: tbodyId 表格body的id
	 * @param: trId 行id
	 * @param: ids 序号数组
	 */
	delAllTBodyList: function(tbodyId, trId, ids) {
		$.each(ids, function(n, v) {
			var tr = $("#" + tbodyId).find("#" + trId + "_" + v)[0];
			if (tr != undefined) {
				//行存在，删除行
				$(tr).remove();
			}
		});
	},
	
	/**
	 * 功能：将数字转换为中文（例如，1:一,10:十）
	 */
	formatIntChinese: function(strInt) {
		var arBigDigit = ["Ｏ", "一", "二", "三", "四", "五", "六", "七", "八", "九"]
		var arUnitBase = ["千", "百", "十", ""]
		var arUnitAdv = ["", "万", "亿"]
		
		var iIndex = 0, iIndexAdv = 0, iDigit = 0, iCount = 0;
		var strRet = "";
		
		// 计算到千亿
		iCount = strInt.length / 4;
		if(iCount * 4 < strInt.length)
		{
			iCount ++;
		}
		if(iCount > 3)
		{
			iCount = 3;
		}
		
		for (iIndexAdv = 0; iIndexAdv < iCount; iIndexAdv ++)
		{
			var iEnd = strInt.length - iIndexAdv * 4;
			var iStart = iEnd - 4;
			var iUnitOff = 0;
			var iZeroFlag = 0;
			
			if(iStart < 0)
			{
				iStart = 0;
			}

			var strTmp = strInt.substring(iStart, iEnd);
			var strUnitAdv = arUnitAdv[iIndexAdv];
			var __ret01 = "";

			if (eval(strTmp) == 0)
			{
				continue;
			}
			
			iUnitOff = 4 - strTmp.length;
			for (iIndex = strTmp.length - 1; iIndex > -1; iIndex --)
			{
				if((iDigit = eval(strTmp.substring(iIndex, iIndex + 1))) > 0)
				{
					__ret01 = arBigDigit[iDigit] + arUnitBase[iUnitOff + iIndex] + __ret01;
					iZeroFlag = 0;
	            }
				else if (iZeroFlag == 0)
				{
				    if (iIndex != 3)
				    {
				        __ret01 = arBigDigit[0] + __ret01;
				    }
			        iZeroFlag = 1;
				}
			}
			
			strRet = __ret01 + strUnitAdv + strRet;
		}

	    return strRet;
	},
	
	formatTwoDecimal: function(num) {
		if (!isNaN(num)) {
			var dot = num.indexOf('.');
			if (dot != -1) {
				var dotCnt = num.substring(dot+1, num.length);
				if (dotCnt.length > 2) {
					var temp = Number(num);
					return temp.toFixed(2);
				} else {
					return num;
				}
			} else {
				return num;
			}
		} else {
			return 0;
		}
	}
})