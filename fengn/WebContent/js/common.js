/**
 * 下拉框选中时，将选中的文本值赋给target对象
 * @param obj
 * @param target
 */
function chooseOption(obj,target){
	var optionValue = $(obj).find("option:selected").val();
	var optionText = $(obj).find("option:selected").text();
	if(optionValue == ""){
		$(target).val("");
	}else{
		$(target).val(optionText);
	}
}

/**
 * 初始化部门下拉列表
 * @param target  
 */
function initDeptSelect(target){
	initDeptSelect(target,selected);
}
function initDeptSelect(target,selected){
	var url =  getProjectUrl() + "dept/deptDics";
	$.ajax({
		url:url,
		type:'post',  
		data: {},
		dataType: 'json',
		success: function(data){
			console.info(data);
			if(data.success == true) {
				$.each(data.results,function(){
					if(this.id == selected){
       					$(target).append("<option value=" + this.id + " selected='selected'>" + this.deptname + "</option>" ); 
       				}else{
       					$(target).append("<option value=" + this.id + ">" + this.deptname + "</option>" ); 
       				}
				});
			}
		},
		error: function(transport) { 
			//$.messager.alert('提示',"系统产生错误,请联系管理员!","error");
		} 
	}); 
}
/**
 * 初始化仓库下拉框
 * @param target
 */
function initStockSelect(target){
	initStockSelect(target,"");
}
function initStockSelect(target,selected){
	var url =  getProjectUrl() + "stock/stockDics";
	$.ajax({
		url:url,
		type:'post',  
		data: {},
		dataType: 'json',
		success: function(data){
			if(data.success == true){
				$.each(data.results,function(){
					if(this.id == selected){
       					$(target).append("<option value=" + this.id + " selected='selected'>" + this.stockname + "</option>" ); 
       				}else{
       					$(target).append("<option value=" + this.id + ">" + this.stockname + "</option>" ); 
       				}
				});
			}
		},
		error: function(transport) { 
			//$.messager.alert('提示',"系统产生错误,请联系管理员!","error");
		} 
	}); 
}
/**
 * 初始化供应商下拉框
 * @param target
 */
function initSupplierSelect(target){
	initSupplierSelect(target,"");
}
function initSupplierSelect(target,selected){
	var url =  getProjectUrl() + "supplier/supplierDics";
	 $.ajax({
	    	url : url,
    		type:'post',  
    		data: {},
    		dataType: 'json',
    		success: function(data){
	       		if(data.success == true) {
	       			$.each(data.results,function(){
	       				if(this.id == selected){
	       					$(target).append("<option value=" + this.id + " selected='selected'>" + this.suppliername + "</option>" ); 
	       				}else{
	       					$(target).append("<option value=" + this.id + ">" + this.suppliername + "</option>" ); 
	       				}
	       			});
		       	}
    		},
    		error: function(transport) { 
    			//$.messager.alert('提示',"系统产生错误,请联系管理员!","error");
    		} 
    	}); 
}
/**
 * 初始化字典下拉框列表
 * @param url  查询地址
 * @param dicNo 字典类型编码
 * @param selected 选中的值
 * @param target 目标对象
 */
function initDictionarySelect(dicNo,target){
	initDictionarySelect(dicNo,target,"");
}
function initDictionarySelect(dicNo,target,selected){
	var url =  getProjectUrl() + "dic/dics/" + dicNo;
	$.ajax({
		url : url,
		type:'post',  
		data:{},
		dataType: 'json',
		success: function(data){
			if(data.success == true) {
				$.each(data.results,function(){
					if(this.diccode == selected){
						$(target).append("<option value=" + this.diccode + " selected='selected'>" + this.dicvalue + "</option>" ); 
					}else{
						$(target).append("<option value=" + this.diccode + ">" + this.dicvalue + "</option>" ); 
					}
				});
			}
		},
		error: function(transport) { 
			//$.messager.alert('提示',"系统产生错误,请联系管理员!","error");
		} 
	}); 
}
/**
 * 求和 
 * @param target  目标元素id
 * @param colName 要求和的列名
 * @returns {Number}
 */
function getTotal(target,colName){
	var rows = $('#'+target).datagrid('getRows');
    var total = 0;
    for (var i = 0; i < rows.length; i++) {
    	var curNum = rows[i][colName];
    	if (isNaN(curNum)) {
    		total += 0;
		} else {
			total += parseFloat(curNum);
		}
    }
    return formatNum(total,2);
}
/**
 * 格式化数字的显示，用###，###.###的格式显示
 * @param price  要格式化的数据
 * @param n  保留的小数位数
 * @returns {String}
 */
function formatNum(num, n) {  
	if(isNaN(num)){
		num = 0;
	}
    n = n > 0 && n <= 20 ? n : 2;  
    num = parseFloat((num + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";  
    var l = num.split(".")[0].split("").reverse(), r = num.split(".")[1];  
    t = "";  
    for (i = 0; i < l.length; i++) {  
        t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");  
    }  
    return t.split("").reverse().join("") + "." + r;  
}  
/**
 * 获取项目url地址
 * @returns {String}
 */
function getProjectUrl(){
	var hostUrl = "";
	var url = window.document.location.href;
	var pathname = window.document.location.pathname;
	pathname = pathname.substring(0,pathname.indexOf("/",1)+1);
	hostUrl = url.substring(0,url.indexOf(pathname)) + pathname;
	return hostUrl;
}


/**
 * 格式化日期
 * @param value 传入日期
 * @param type 格式化样式
 * dE8 样式如2015-05-21
 * dC8 样式如2015年05月21日
 * dE12 样式如2015-05-21 09:58
 * dC12 样式如2015年05月21日09时58分
 * dE14 样式如2015-05-21 09:58:28  默认样式
 * dE14 样式如2015年05月21日09时58分28秒
 * @returns {String}
 */
function formatDate(value,type){
	var date = "";
	if(value === undefined || value == null) return "";
	if(!(value instanceof Date)){
		if(value.indexOf(".") > 0){
			value = value.substring(0,value.indexOf(".")).replace(/-/g,"/");
		}
		date = new Date(value);
	}else{
		date = value;
	}
	
	if("Invalid Date" == date){
		return "";
	}
	var year = date.getFullYear().toString();
	var month = (date.getMonth() + 1);
	var day = date.getDate().toString();
	var hour = date.getHours().toString();
	var minutes = date.getMinutes().toString();
	var seconds = date.getSeconds().toString();
	
	if (month < 10) {
		month = "0" + month;
	}
	if (day < 10) {
		day = "0" + day;
	}
	if (hour < 10) {
		hour = "0" + hour;
	}
	if (minutes < 10) {
		minutes = "0" + minutes;
	}
	if (seconds < 10) {
	    seconds = "0" + seconds;
	}
	switch(type){
		case "dE8" : return year + "-" + month + "-" + day;
		case "dC8" : return year + "年" + month + "月" + day + "日";
		case "dE12" : return year + "-" + month + "-" + day + " " + hour + ":" + minutes ;
		case "dC12" : return year + "年" + month + "月" + day + "日" + hour + "时" + minutes + "分";
		case "dE14" : return year + "-" + month + "-" + day + " " + hour + ":" + minutes + ":" + seconds;
		case "dC14" : return year + "年" + month + "月" + day + "日" + hour + "时" + minutes + "分" + seconds + "秒";
		default : return year + "-" + month + "-" + day + " " + hour + ":" + minutes + ":" + seconds;
	}
}
/**
 * 获取当前日期 格式如2015-10-27
 * @returns {String}
 */
function getCurDate(){
	return formatDate(new Date(),"dE8");
}
//比较日期大小
function compareToDate(first,second){
	  var begin=new Date(first.replace(/-/g,"/"));
    var end=new Date(second.replace(/-/g,"/"));
    if(begin > end){
  	  $.messager.alert("警告","开始日期要在截止日期之前!","error");
        return false;
    }else{
  	  return true;
    }
}