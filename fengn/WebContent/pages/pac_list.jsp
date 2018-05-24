<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title></title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  </head>
  <body id="a2">
	<div >
			<div >
				  <div class="panel-body" id="a3" style="display:block">
				  	    <table id="infoTable"> </table>
					<div id="toolbar" class="btn-group">  
			            <button id="btn_edit" type="button" class="btn btn-default" onclick="updateData()">  
			                <span class="glyphicon glyphicon-pencil" aria-hidden="true" ></span>修改  
			            </button>  
			            <button id="btn_delete" type="button" class="btn btn-default" onclick="delDish()">  
			                <span class="glyphicon glyphicon-remove" aria-hidden="true" ></span>删除  
			            </button>  
			            <button id="btn_delete" type="button" class="btn btn-default" onclick="addInfo()">  
			            	<span class="glyphicon glyphicon-plus" aria-hidden="true" ></span>新增
			            </button>
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
    <script type="text/javascript">
    	$(function(){
    		 $('#infoTable').bootstrapTable({  
			        url : '${basePath}/pac/query', // 请求后台的URL（*）            
			        method : 'get', // 请求方式（*）  
			        toolbar : '#toolbar', // 工具按钮用哪个容器  
			        cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）  
			        sidePagination : "client", // 分页方式：client客户端分页，server服务端分页（*）  
			        pagination : true, // 是否显示分页（*）  
			        pageNumber: 1,    //如果设置了分页，首页页码  
			        pageSize: 50,                       //每页的记录行数（*）  
			        pageList: [50,300,600],        //可供选择的每页的行数（*）  
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
			            field : 'typename',   title : '套餐名称',   align: 'center', valign: 'middle'  
			        },{  
			            field : 'discrip',title : '套餐描述',  align: 'center', valign: 'middle'  
			        },{  
			            field : 'cost',   title : '成本价',  align: 'center',   valign: 'middle'  
			        },{  
			            field : 'renew', title : '续费价',    align: 'center',  valign: 'middle'  
			        }, {  
			           field : 'remark',  title : '备注',  align: 'center',   valign: 'middle' 
			        }],  
			        silent : true, // 刷新事件必须设置  
			    });  
	 });
    	
    </script>
    
</html>
