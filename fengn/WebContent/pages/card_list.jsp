<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@include file="/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- <script type="text/javascript" src="http://www.w3cschool.cc/try/jeasyui/datagrid-detailview.js"></script> -->
<title>用户管理</title>
<script type="text/javascript">
	$(function() {
		 var agentId = parent.$('#tabs').tabs('getSelected').panel('options').id;
		$('#card_table').datagrid({
			url:"${basePath}/treeindex/card_query/" + agentId ,
			rownumbers : true,
			autoRowHeight : true,
			pagination : true,
			fitColumns: true,
			nowrap : true,
			fit: true, 
			columns:[[
				{field : 'id',align : 'center',halign:'center',checkbox : true}, 
				{field:'ICCID',title:'ICCID',align:'center'},
				{field:'name',title:'所属代理商',align:'center'},
				{field:'cardCode',title:'卡号',align:'center'},
				{field:'gprsUsed',title:'使用流量',align:'center'},
				{field:'packageType',title:'套餐类型',align:'center'},
				{field:'openDate',title:'开卡时间',align:'center'},				
				{field:'remark',title:'备注',align:'center'}
			]],
			toolbar : [ {
				text:'全选',
				iconCls: 'icon-ok',
				handler: function(){
					$("input:checkbox").each(function(){
				    	   	 this.checked = "checked" ;
				    })
				}
			},'-',{
				text:'全取消',
				iconCls: 'icon-redo',
				handler: function(){
					$("input:checkbox").each(function(){
			    	   		  this.checked = false;
			    		})
					
				}
			},'-',{
				text : '分配',
				iconCls : 'icon-edit',
				handler : function() {
					updateCard();
				}
			},'-',{
				text : '导入数据',
				iconCls : 'icon-edit',
				handler : function() {
					upload();
				}
			}]
		});
		$('#card_table').datagrid('getPager').pagination({  
			 pageSize: 100,  
	            pageList: [ 50 ,100 , 300, 500 , 1000],  
	            showRefresh:false ,
	            onSelectPage:function(pageNumber, pageSize){
	            		doSearch();
		        	}
       });
		
		 
		$('#dlg-frame').dialog({
			title : '代理商管理',
			width : 800,
			height : 500,
			top : 50,
			left : 100,
			closed : true,
			cache : false,
			modal : true
		});
	});

	function doSearch(index) {
		var type = $("#search-type").val();
		var iccidStart = $("#search-iccidStart").val();
		var iccidEnd = $("#search-iccidEnd").val();
		var pageNo = $(".pagination-num").val(); 
		var pageSize = $(".pagination-page-list").val();
	    $('#card_table').datagrid('reload',{
	    	type : type,pageNo:pageNo,pageSize:pageSize ,
	    		iccidStart:iccidStart,iccidEnd:iccidEnd
		} );
	}
	function doClear() {
		$("#search-type").val("");
		$("#search-iccidStart").val("");
		$("#search-iccidEnd").val("");
	}
	function updateCard() {
		var agentId = parent.$('#tabs').tabs('getSelected').panel('options').id;
		var id = "";
		$("input[type=checkbox]").each(function() {
			if (this.checked) {
				id += $(this).val() + ",";
			}
		});
		if(id.indexOf("on,")>=0){
			id = id.substring(3);
		}
		var path = "${basePath}/agent/move?iccids=" + id + "&moveFlag=1&moveAgent="+agentId;
		document.getElementById('frameContent').src = path;
		$('#dlg-frame').dialog('open');
	}
	
	function upload() {
		var path = "${basePath}/uploadExcel/uploadInit"  ;
		document.getElementById('frameContent').src = path;
		$('#dlg-frame').dialog('open');
	}

	function getChecked() {
		var id;
		var checkTotal = 0;
		$("input[type=checkbox]").each(function() {
			if (this.checked) {
				id = $(this).val();
				checkTotal++;
			}
		});
		if (checkTotal == 0) {
			alert("请选中一条数据！");
			return 0;
		} else if (checkTotal > 1) {
			alert("只能选择一条数据！");
			return 0;
		}
		return id;
	}
</script>
<style type="text/css">
.datagrid-view{
	height : 2000px;
}

</style>
</head>
<body >
	<div id="tb" region="north" title="查询条件区" class="easyui-panel"
		iconCls="icon-search" style="padding: 3px; height: 60px; width: 80%">
		<%-- <span>套餐类型:</span>
		<select id="search-type" name="type" >
			 <option value="0">--选择--</option>
			 <c:forEach items="${typeList}" var="typename">
			 		<option value = "${typename}">${typename}</option>
			 </c:forEach>	
		</select> --%>
		<span>ICCID:</span>
		<input id="search-iccidStart" name="iccidStart" /> - 
		<input
			id="search-iccidEnd" name="iccidEnd" />
		<a href="####"
			class="easyui-linkbutton" plain="true" iconCls="icon-search"
			onclick="doSearch()">查询</a> <a href="####" class="easyui-linkbutton"
			plain="true" iconCls="icon-clear" onclick="doClear()">清除</a>
	</div>
 	<div id="main_layout" data-options="region:'center',border:false,showHeader:false" style="width:80%;height:70%;">
 		<table id="card_table" class="easyui-datagrid" fit="true" ></table>
 	</div>
	<div data-options="region:'south',border:false,showHeader:false" style="width:900px;height:20%;">
 		 &nbsp;
 	</div>

		<div id="dlg-frame">
			<iframe width="99%" height="98%" name="frameContent"
				id="frameContent" frameborder="0" scrolling="no"></iframe>
		</div>
	</div>


</body>
</html>