<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@include file="/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户管理</title>
	<script type="text/javascript">
		$(function(){
			$('#data-table').datagrid( {
				//url : '${basePath}/user_list',
				rownumbers : true,
				autoRowHeight : true, 
				singleSelect : true,
				pagination : true,
				nowrap: false,
				toolbar: [{
					text:'添加',
					iconCls: 'icon-add',
					handler: function(){addUser();}
				},'-',{
					text:'修改',
					iconCls: 'icon-edit',
					handler: function(){updateUser();}
				},'-',{
					text:'删除',
					iconCls: 'icon-remove',
					handler: function(){deleteUser();}
				}]
			});
			$('#data-table').datagrid('getPager').pagination({  
                pageSize: "${page.pageSize}",  
                pageNumber: "${page.pageNo}",  
                pageList: [3 , 10, 30, 50],  
                beforePageText: '第',//页数文本框前显示的汉字   
                afterPageText: '页    共 ${page.pageIndex} 页',  
                displayMsg: '  共 ${page.total} 条记录',  
                showRefresh:false ,
                onSelectPage:function(pageNumber, pageSize){
	            		$(this).pagination('loading');
	            		alert('pageNumber:'+pageNumber+',pageSize:'+pageSize);
	            		$(this).pagination('loaded');
	            	}
           });
			
			
			 $(".pagination-num").val("${page.pageNo}");
			
			
			 
			$('#dlg-frame').dialog( {
				title : '用户管理',
				width :  700,
				height : 320,
				top:50,
				left:100,
				closed : true,
				cache : false,
				modal : true,
				buttons : [ {
					text : '确定',
					iconCls : 'icon-ok',
					handler : function() {
						if (confirm("确定执行下一步操作？")) {
							frameContent.window.doServlet();
						}
					}
				}, {
					text : '关闭',
					iconCls : 'icon-cancel',
					handler : function() {
						$('#dlg-frame').dialog("close");
					}
				} ]
			});
			
		});
		
		function doSearch(index){
			var userNo = $("#search-userNo").val();
			var pageNo = $(".pagination-num").val(); 
			var pageSize = $(".pagination-page-list").val();
			var pageTotal = ${page.pageIndex};
			if(index == "1"){
				pageNo = 1 ;
			}else if(index == "prev" && pageNo != 1 ){
				 pageNo  -= 1 ;
			}else if(index == "next" && pageNo != pageTotal){
				pageNo  = parseInt(pageNo)+parseInt(1); 
			}else if(index == "last" ){
				pageNo  = pageTotal; 
			}
			console.info(pageSize)
			window.location.href = "${basePath}/user_query?userNo=" + userNo +
					"&pageNo=" + pageNo + "&pageSize=" + pageSize ;
		}
		function doClear(){
			$("#search-userNo").val("");
		}
		function addUser(){
			var path = "${basePath}/addInit";
			document.getElementById('frameContent').src = path;
			$('#dlg-frame').dialog('open');
		}
		
		function updateUser(){
			var id = getChecked();
			if (id > 0) {
				var path = "${basePath}/updateInit/" + id;
				document.getElementById('frameContent').src = path;
				$('#dlg-frame').dialog('open');
			}
		}
		
		function deleteUser(){
			var del= confirm("确认删除？");
			if(!del){
				return false;
			}
			var id = getChecked();
			if (id > 0) {
				var path = "${basePath}/user_delete/" + id;
				$.ajax( {
					url : path,
					type : 'post',
					data : $("#dataForm").serialize(),
					dataType : 'json',
					success : function(data) {
						if(data.success){
							$.messager.alert('提示',data.msg);
							doSearch();
						}else{
							$.messager.alert('提示',data.msg,"error");
						}
						
					},
					error : function(transport) {
						$.messager.alert('提示', "系统产生错误,请联系管理员!", "error");
					}
				});
			}
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
				$.messager.alert('提示', "请先选中一行(只允许单行操作)", 'error');
				return 0;
			} else if (checkTotal > 1) {
				$.messager.alert('提示', "只能选中一行(只允许单行操作)", 'error');
				return 0;
			}
			return id;
		}
	</script>

</head>
<body class="easyui-layout">
	<div id="tb" region="north" title="查询条件区" class="easyui-panel"  iconCls="icon-search"  style="padding:3px;height: 60px; width: 86%"  >
		<span>用户名:</span>
		<input id="search-userNo" name="userNo"/>
		<a href="####" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="doSearch()">查询</a>
		<a href="####" class="easyui-linkbutton" plain="true" iconCls="icon-clear" onclick="doClear()">清除</a>
	</div>
	<div region="center"  border="0">
		<form:form id="dataForm" action="${basePath}/user/user_delete" modelAttribute="user" method="post">
			<input type="hidden" name="_method" value="DELETE"/>
			<table class="easyui-datagrid" id="data-table"  title="数据列表" width="86%" >
				<thead>
					<tr>
						<th data-options="field:'id'"></th>
						<th data-options="field:'agentName'">代理商</th>
						<th data-options="field:'userName'">用户名称</th>
						<th data-options="field:'userNo'">登陆账号</th>
						<th data-options="field:'roleId'">角色</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${list}" var="user">
						<tr>
							<td><input type="checkbox" name="id" value="${user.id}" /></td>
							<td>${user.agentName}</td>
							<td>${user.userName}</td>
							<td>${user.userNo}</td>
							<td>${user.roleId}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			
		</form:form>
		
		<div id="dlg-frame">
			<iframe width="99%" height="98%" name="frameContent" id="frameContent"
				frameborder="0"></iframe>
		</div>
	</div>
	
	
</body>
</html>