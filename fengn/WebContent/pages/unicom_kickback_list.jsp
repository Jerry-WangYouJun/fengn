<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@include file="/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户管理</title>
<script type="text/javascript">
		var agentId = parent.$('#tabs').tabs('getSelected').panel('options').id;
	$(function() {
		$('#data_table').datagrid({
			url:"${basePath}/unicom/kickback_query/" + agentId  ,
			rownumbers : true,
			autoRowHeight : true,
			pagination : true,
			fitColumns: true,
			nowrap : false,
			fit: true,
			columns:[[
			          
				{field:'iccid',title:'ICCID',align:'center'},
				{field:'money',title:'充值金额',align:'center'},
				{field:'packageType',title:'套餐类型',align:'center'},
				{field:'update_date',title:'充值时间',align:'center'},				
				{field:'kickback',title:'返佣',align:'center'}
			]]
		});
		$('#data_table').datagrid('getPager').pagination({  
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
		searchSum(agentId);
	});
	 function searchSum(id){
		 var timeType = $("#timeType")[0].value;
			var dateStart = document.getElementsByName("dateStart")[0].value;
			var dateEnd = document.getElementsByName("dateEnd")[0].value;
			var pageNo = $(".pagination-num").val(); 
			var pageSize = $(".pagination-page-list").val();
			var url = "${basePath}/unicom/kickback_sum/" + id;
			$.ajax({
				url : url,
				data :{
				    	timeType : timeType,pageNo:pageNo,pageSize:pageSize ,
				    	dateStart:dateStart,dateEnd:dateEnd
					},
				dataType : 'json',
				success : function(data) {
					$("#money").text(data);
				},
			});
	 }
	function doSearch(index) {
		var timeType = $("#timeType")[0].value;
		var dateStart = document.getElementsByName("dateStart")[0].value;
		var dateEnd = document.getElementsByName("dateEnd")[0].value;
		var pageNo = $(".pagination-num").val(); 
		var pageSize = $(".pagination-page-list").val();
	    $('#data_table').datagrid('reload',{
	    	timeType : timeType,pageNo:pageNo,pageSize:pageSize ,
	    	dateStart:dateStart,dateEnd:dateEnd
		} );
	    searchSum(agentId)
	}
	function doClear() {
		$("#timeType").val("");
		$('#search-dateStart').combo('setText','');
		$('#search-dateEnd').combo('setText','');
	}
</script>

</head>
<body >
	<div id="tb" region="north" title="查询条件区" class="easyui-panel"
		iconCls="icon-search" style="padding: 3px; height: 60px; width: 80%">
		<span>查询时间:</span>
		<input id="search-dateStart" name="dateStart" class="easyui-datebox"  type="text" /> - 
		<input
			id="search-dateEnd" name="dateEnd" class="easyui-datebox"  type="text"/>
		<span>查询固定时间</span>
		<select id="timeType"  name="timeType">
			  <option value="0">--请选择--</option>
			  <option value="7">--本周--</option>
			  <option value="30">--本月--</option>
			  <option value="60">--上月--</option>
		</select>
		<a href="####"
			class="easyui-linkbutton" plain="true" iconCls="icon-search"
			onclick="doSearch()">查询</a> 
		<a href="####" class="easyui-linkbutton"
			plain="true" iconCls="icon-clear" onclick="doClear()">清除</a>
	</div>
	<div id="main_layout" data-options="region:'center',border:false,showHeader:false" style="width:80%;height:70%;">
 		<table id="data_table" class="easyui-datagrid" fit="true" ></table>
 	</div>
	<div data-options="region:'south',border:false,showHeader:false" style="width:900px;height:20%;">
 			<table >
				<thead>
					<tr>
						<th >充值总金额</th>
						<th id="money">  </th>
					</tr>
				</thead>
			</table>
 	</div>

		<div id="dlg-frame">
			<iframe width="99%" height="98%" name="frameContent"
				id="frameContent" frameborder="0"></iframe>
		</div>
	</div>

</body>
</html>