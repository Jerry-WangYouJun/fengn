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
		 var tabName = parent.$("#deviceulid > li.active").attr("id");
		 var agentId = tabName.split("_")[4];
		 $('#infoTable').bootstrapTable({  
		        url : '${basePath}/cmoit/card/cmoit_kickback_query/'+agentId, // 请求后台的URL（*）            
		        method : 'get', // 请求方式（*）  
		        toolbar : '#toolbar', // 工具按钮用哪个容器  
		        cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）  
		        sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）  
		        queryParamsType:'',
		        pagination : true, // 是否显示分页（*）  
		        pageNumber: 1,    //如果设置了分页，首页页码  
		        pageSize: 50,                       //每页的记录行数（*）  
		        pageList: [50,100,300,600,1000],        //可供选择的每页的行数（*）  
		        showRefresh : true, // 是否显示刷新按钮  
		        clickToSelect : true, // 是否启用点击选中行  
		        showToggle : false, // 是否显示详细视图和列表视图的切换按钮  
		        search:true,   //是否启用搜索框 
		        
		        columns : [ {   checkbox : true } ,
		                    {field:'iccid',title:'ICCID',align:'center', valign: 'middle'},
		    				{field:'money',title:'充值金额',align:'center', valign: 'middle'},
		    				{field:'packageType',title:'套餐类型',align:'center', valign: 'middle'},
		    				{field:'update_date',title:'充值时间',align:'center', valign: 'middle'},				
		    				{field:'kickback',title:'返佣',align:'center', valign: 'middle'}
			     ],  
		        silent : true, // 刷新事件必须设置  
		    }); 
	});

</script>
<style type="text/css">
  .panel-body {
    padding: 0px !important; 
}

</style>
</head>
<body >
	<div >
			<div >
				  <div class="panel-body" id="a3" style="display:block">
				  	    <table id="infoTable"> </table>
					<div id="toolbar" class="btn-group">  
			        </div>  
				  </div>
			</div>
		</div>
		
	<div class="modal fade" id="myModal" tabindex="-2" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="height: ">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">支出管理</h4>
				</div>
				<div class="modal-body">
					<form id="dataForm">
					 <input  class="form-control" name="id" type="hidden"></input>
					 <input  class="form-control" name="user" type="hidden"></input>
					 <input  class="form-control" name="actordate" type="hidden"></input>
					 <div class="form-group" >
					            <label class="control-label">发生时间：</label>  
					            <!--指定 date标记-->  
					            <div class='input-group date' id='datetimepicker1'  >  
					                <input type='text' class="form-control" readonly name="actordate" />  
					                <span class="input-group-addon" >  
					                    <span class="glyphicon glyphicon-calendar"></span>  
					                </span>  
					            </div>   
				        </div> 
						<div class="form-group">
							<label for="recipient-name" class="control-label">支出费用:</label> <input
								type="text" class="form-control" name="money">
						</div>
						<div class="form-group">
							<label for="message-text" class="control-label">支出类型:</label> 
								<select name="modeid" class="form-control">
										 <c:forEach items="${modelList}" var="mode">
										 	<c:if test="${ mode.parenttype eq '支出'}">
										 		<option value="${mode.id}">${mode.typename}</option>
										 	</c:if>
										 		
										 </c:forEach>
								</select>
						</div>
						<div class="form-group">
							<label for="message-text" class="control-label">支出人:</label> <input
								class="form-control" name="actor"></input>
						</div>
						<div class="form-group">
							<label for="message-text" class="control-label">所在账户:</label> 
								<select name="account" class="form-control">
										 <c:forEach items="${accountList}" var="account">
										 		<option value="${account.id}">${account.aname}</option>
										 </c:forEach>
								</select>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" onclick="subInfo()">提交更改</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
	</div>
</body>
</html>