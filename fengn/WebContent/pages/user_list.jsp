<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@include file="/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html id="a1">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-theme.min.css">
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script> 
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-table.min.css" />  
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap-table.js"></script>
<title>用户管理</title>
	

</head>
<body id="a2">
	<div >
			<div >
				  <div class="panel-body" id="a3" style="display:block">
				  	    <table id="infoTable"> </table>
					<div id="toolbar" class="btn-group">  
			            <button id="btn_edit" type="button" class="btn btn-default" onclick="updateDish()">  
			                <span class="glyphicon glyphicon-pencil" aria-hidden="true" ></span>修改  
			            </button>  
			            <button id="btn_delete" type="button" class="btn btn-default" onclick="delDish()">  
			                <span class="glyphicon glyphicon-remove" aria-hidden="true" ></span>删除  
			            </button>  
			        </div>  
				  </div>
			</div>
		</div>
	
</body>
<script type="text/javascript">
		$(function(){
			    $('#infoTable').bootstrapTable({  
			        url : '${basePath}/agent/user_query', // 请求后台的URL（*）            
			        method : 'get', // 请求方式（*）  
			        toolbar : '#toolbar', // 工具按钮用哪个容器  
			        cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）  
			        sidePagination : "client", // 分页方式：client客户端分页，server服务端分页（*）  
			        pagination : true, // 是否显示分页（*）  
			        pageNumber: 1,    //如果设置了分页，首页页码  
			        pageSize: 4,                       //每页的记录行数（*）  
			        pageList: [10,30,60],        //可供选择的每页的行数（*）  
			        showRefresh : true, // 是否显示刷新按钮  
			        clickToSelect : true, // 是否启用点击选中行  
			        showToggle : false, // 是否显示详细视图和列表视图的切换按钮  
			        search:true,   //是否启用搜索框 
			        
			        columns : [ {  
			            checkbox : true 
			        },{  
			            field : 'id', visible: false 
			        },{  
			            field : 'agentId',  visible: false 
			        },{  
			            field : 'agentName',   title : '代理商',   align: 'center', valign: 'middle'  
			        },{  
			            field : 'agentCode',title : '代理商代码',  align: 'center', valign: 'middle'  
			        },{  
			            field : 'userNo',   title : '登录账号',  align: 'center',   valign: 'middle'  
			        },{  
			            field : 'type', title : '套餐类型',    align: 'center',  valign: 'middle'  
			        }, {  
			           field : 'cost',  title : '成本价',  align: 'center',   valign: 'middle' 
			        }, {  
				           field : 'renew',  title : '续费价', align: 'center',   valign: 'middle' 
				        }],  
			        silent : true, // 刷新事件必须设置  
			    });  
		});
		
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
</html>