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
<style type="text/css">
  .panel-body {
    padding: 0px !important; 
}
</style>
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
						<button id="btn_edit" type="button" class="btn btn-default" onclick="moveData()">
							<span class="glyphicon glyphicon-forward" aria-hidden="true" ></span>分配
						</button>
					</div>
				  </div>
			</div>
		</div>

	<div class="modal fade" id="myModal2" tabindex="-2" role="dialog"
		 aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="height: ">
			<div class="modal-content">
				<div class="modal-body">
					<table id="refTable"> </table>
					<div id="agentToolbar" class="btn-group">
						<button id="btn_edit" type="button" class="btn btn-default" onclick="movePacByAgent()">
							<span class="glyphicon glyphicon-pencil" aria-hidden="true" ></span>分配
						</button>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
	</div>
		
	<div class="modal fade" id="myModal" tabindex="-2" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="height: ">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">套餐管理</h4>
				</div>
				<div class="modal-body">
					<form id="dataForm">
					 <input  class="form-control" name="id" type="hidden"></input>
						<div class="form-group">
							<label for="recipient-name" class="control-label">套餐名:</label> <input
								type="text" class="form-control" name="typename">
						</div>
						<div class="form-group">
							<label for="message-text" class="control-label">套餐描述:</label> 
								<input type="text" class="form-control" name="discrip">
						</div>
						<div class="form-group">
							<label for="message-text" class="control-label">备注:</label> <input
								class="form-control" name="remark"></input>
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
            var tabName = parent.$("#deviceulid > li.active").attr("id");
            var agentId = tabName.split("_")[4];
    		 $('#infoTable').bootstrapTable({
			        url : '${basePath}/pac/query', // 请求后台的URL（*）            
			        method : 'get', // 请求方式（*）  
			        toolbar : '#toolbar', // 工具按钮用哪个容器  
			        cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）  
			        sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）  
			        queryParamsType:'',
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
			        }, {
                        field : 'remark',  title : '备注',  align: 'center',   valign: 'middle'
			        }],  
			        silent : true, // 刷新事件必须设置  
			    });  
	 });


        function subInfo(){
            subInfoAll("pac");
        }

        function delDish(){
            deleteDataAll("pac");
        }

        function moveData() {
            var selectRow =  $("#infoTable").bootstrapTable('getSelections')[0];
            console.info($("#infoTable").bootstrapTable('getSelections').length);
            if($("#infoTable").bootstrapTable('getSelections').length  == 0){
                alert("选择要分配的套餐！");
                return false;
            }
            $("#myModal2").modal("show");
        }


        function movePacByAgent(){
            var ids = "";
            for(var i in $("#infoTable").bootstrapTable('getSelections')){
                //console.info($("#infoTable").bootstrapTable('getSelections')[i]);
                ids += ","+$("#infoTable").bootstrapTable('getSelections')[i].id ;
            }
            ids=ids.substring(1);
            var parentAgentId = $("#infoTable").bootstrapTable('getSelections')[0].agentId;
            var del = confirm("确认分配？");
            if (!del) {
                return false;
            }
            var id = $("#refTable").bootstrapTable('getSelections')[0].id;
            if(id == parentAgentId){
                alert("不能选择自己！");
                return false;
			}
            var childcost = prompt("请输入子代理成本价（与子代理的交易价格，只能填数字）")
            //alert(ids); return false;
            if (id > 0) {
                var url = "${basePath}/pac/pac_move";
                $.ajax({
                    url : url,
                    type : 'post',
                    data : {pacids: ids , agentid : id ,parentAgentId:parentAgentId ,childcost:childcost},
                    dataType : 'json',
                    success : function(data) {
                        if (data.success) {
                            alert(data.msg);
                            $("#refTable").bootstrapTable("refresh");
                            closeModel();
                        } else {
                            alert(data.msg);
                        }
                    },
                    error : function(transport) {
                        alert(data.msg);
                    }
                });

            }
        }


        $(function(){
            var tabName = parent.$("#deviceulid > li.active").attr("id");
            var agentId = tabName.split("_")[4];
            $('#refTable').bootstrapTable({
                url : '${basePath}/agent/son_query', // 请求后台的URL（*）
                method : 'get', // 请求方式（*）
                toolbar : '#agentToolbar', // 工具按钮用哪个容器
                cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
                queryParamsType:'',
                pagination : true, // 是否显示分页（*）
                pageNumber: 1,    //如果设置了分页，首页页码
                pageSize: 50,                       //每页的记录行数（*）
                pageList: [100,300,600],        //可供选择的每页的行数（*）
                singleSelect    : true,
                showRefresh : true, // 是否显示刷新按钮
                clickToSelect : true, // 是否启用点击选中行
                showToggle : false, // 是否显示详细视图和列表视图的切换按钮
                search:true,   //是否启用搜索框

                columns : [ {
                    checkbox : true
                },{
                    field : 'id', visible: false
                },{
                    field : 'name',   title : '代理商',   align: 'center', valign: 'middle'
                },{
                    field : 'code',title : '代理商代码',  align: 'center', valign: 'middle'
                },{
                    field : 'userNo',   title : '登录账号',  align: 'center',   valign: 'middle'
                },{
                    field : 'groupId',   title : '代理商类型',  align: 'center',   valign: 'middle'  ,
                    formatter : function(value, row, index) {
                        if (value == '1') {
                            return "移动";
                        } else if (value == '2') {
                            return "联通";
                        } else if(value =='3' ){
                            return  "移动,联通";
                        }
                    }}],
                silent : true, // 刷新事件必须设置
            });
        });
    	
    </script>
    
</html>
