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
		        url : '${basePath}/cmoit/card/card_query/'+agentId, // 请求后台的URL（*）            
		        method : 'get', // 请求方式（*）  
		        toolbar : '#toolbar', // 工具按钮用哪个容器  
		        cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）  
		        sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）  
		        queryParamsType:'',
		        queryParams : function(params) {
                    params.iccidStart = $("#idstart").val();
                    params.iccidEnd = $("#idend").val();
                    params.packageId = $("#pacId").val();
                    params.simstate = $("#simstate").val();
                    params.agentName = $("#agentName").val();
                    params.simNum = $("#simNum").val();
                    params.activeStartTime = $("#activeStartTime").val();
                    params.activeEndTime = $("#activeEndTime").val();
                    return params;
		        	},
		        pagination : true, // 是否显示分页（*）  
		        pageNumber: 1,    //如果设置了分页，首页页码  
		        pageSize: 50,                       //每页的记录行数（*）  
		        pageList: [50,100,300,600,1000],        //可供选择的每页的行数（*）  
		        showRefresh : true, // 是否显示刷新按钮  
		        clickToSelect : true, // 是否启用点击选中行  
		        showToggle : false, // 是否显示详细视图和列表视图的切换按钮  
		        search:true,   //是否启用搜索框 
		        
		        columns : [ {   checkbox : true } ,
			        {field : 'id', visible: false  },
			        {field:'msisdn',title:'卡号',align:'center', valign: 'middle'},
					{field:'iccid',title:'ICCID',align:'center', valign: 'middle'},
					{field:'imsi',title:'IMSI',align:'center', valign: 'middle'},
					{field:'name',title:'所属代理商',align:'center', valign: 'middle'},
					{field:'discrip',title:'套餐类型',align:'center', valign: 'middle'},
					{field:'cardstatus',title:'卡状态',align:'center', valign: 'middle'},
					{field:'userstatus',title:'用户状态',align:'center', valign: 'middle'},
					{field:'balance',title:'当前余额',align:'center', valign: 'middle'},
					{field:'gprsused',title:'剩余流量',align:'center', valign: 'middle'},
					{field:'gprssum',title:'使用流量',align:'center', valign: 'middle'},
					{field:'callused',title:'通话剩余',align:'center', valign: 'middle'},
					{field:'callsum',title:'包月通话',align:'center', valign: 'middle'},
					{field:'msgused',title:'使用短信',align:'center', valign: 'middle'},
					{field:'msgsum',title:'包月短信',align:'center', valign: 'middle'},
					{field:'activetime',title:'激活日期',align:'center', valign: 'middle'},
					{field:'opentime',title:'开卡日期',align:'center', valign: 'middle'},
					{field:'updatetime',title:'到期日期',align:'center', valign: 'middle'}			     ],  
		        silent : true, // 刷新事件必须设置  
		    }); 
		 
		 $('#infoTable').bootstrapTable({locale:'en-US'});
	});

	function queryData(){
		$("#infoTable").bootstrapTable("refresh");
	}
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
				  	    <table id="infoTable" > </table>
					<div id="toolbar" class="btn-group"> 
						 <form class="form-inline" role="form">
							 <div style="margin-bottom: 1px;">
								 <input type='text' class="form-control" id='activeStartTime' placeholder="请输入激活日期起始时间" />
								 <span>至</span>
								 <input type='text' class="form-control" id='activeEndTime'   placeholder="请输入激活日期结束时间"/>
								 <div class="form-group">
									 <label class="sr-only" for="name">名称</label>
									 <input type="text" class="form-control" id="idstart"
											placeholder="请输入ICCID">
								 </div> -
								 <div class="form-group">
									 <label class="sr-only" for="name">名称</label>
									 <input type="text" class="form-control" id="idend"
											placeholder="请输入ICCID">
								 </div>
							</div>
						  <br>
						  <input type="text" class="form-control" id="simNum"
								 placeholder="请输入MSID号">
						  <input type="text" class="form-control" id="agentName"
								 placeholder="请输入代理商名称">
						  <select class="form-control "  id="pacId"  name="pacId" style="display: inline">
							  <option value="">全部套餐</option>
						  </select>
						  <select class="form-control "  id="simstate"  name="pacId" style="display: inline">
							  <option value="">卡状态</option>
							  <option value="0">待激活</option>
							  <option value="1">正常</option>
							  <option value="2">停机</option>
						  </select>
							<button id="btn_edit" type="button" class="btn btn-default" onclick="queryData()">
							   查询
							</button>
							<button id="btn_edit" type="reset" class="btn btn-default" >
								重置
							</button>
							 <button id="btn_edit" type="button" class="btn btn-default" onclick="moveData()">
								分配
							</button>
						</form>
			            <!-- <button id="btn_edit" type="button" class="btn btn-default" onclick="updateOrderStatus()">  
			                <span class="glyphicon glyphicon-pencil" aria-hidden="true" ></span>
			                修改订单状态
			            </button> -->
			        </div>  
				  </div>
			</div>
		</div>
		
	<div class="modal fade" id="myModal" tabindex="-2" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="height: ">
			<div class="modal-content">
				<div class="modal-body">
					    <table id="agentTable"> </table>
					    <div id="agentToolbar"  class=" form-inline" >
						<select class="form-control "  id="packageId"  name="packageId" style="display: inline">
							<option value="">全部套餐</option>
						</select>
			            <button id="btn_edit" type="button" class="btn btn-default" onclick="moveCardByAgent()">  
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
	
	<div class="modal fade" id="reamrkModal" tabindex="-2" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="height: ">
				<form action="${basePath}/treeindex/updateRemark/cmcc" method="post" id="remarkForm">
					<div class="modal-content">
						<div class="modal-body">
							    <table data-toggle="table">
								    <thead>
								        <tr>
								            <th>ICCID</th>
								            <th>标签</th>
								        </tr>
								    </thead>
								     <tbody id = "remarks">
								    </tbody>
								</table>
					        </div> 
							<div class="modal-footer">
								<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
								<button type="button" class="btn btn-default" onclick="subRemark('cmcc')">提交</button>
							</div>
						</div>
			  </form>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
	<script type="text/javascript">


        function getMyPackage(){
            var html = "";
            $.ajax({
                url : '${basePath}/pac/getPacList',
                type : 'post',
                data : {},
                dataType : 'json',
                success : function(data) {
                    if (data.success) {
                        var dataInfo = data.dataInfo;
                        for(var i = 0;i<dataInfo.length;i++){
                            html+="<option value='"+dataInfo[i].id+"'>"+dataInfo[i].typename+"</option>";
                        }
                        $("#packageId").html(html);
                    } else {
                        alert("发生异常，请联系管理员");
                    }
                },
                error : function(transport) {
                    alert(data.msg);
                }
            });
        }

        function getMyPackageMenu(){
            var html = "";
            $.ajax({
                url : '${basePath}/pac/getPacList',
                type : 'post',
                data : {},
                dataType : 'json',
                success : function(data) {
                    if (data.success) {
                        var dataInfo = data.dataInfo;
                        html += "<option value=''>全部套餐</option>";
                        for(var i = 0;i<dataInfo.length;i++){
                            html+="<option value='"+dataInfo[i].id+"'>"+dataInfo[i].typename+"</option>";
                        }
                        $("#pacId").html(html);
                    } else {
                        alert("发生异常，请联系管理员");
                    }
                },
                error : function(transport) {
                    alert(data.msg);
                }
            });
        }


	$(function(){
        getMyPackageMenu();
		var tabName = parent.$("#deviceulid > li.active").attr("id");
		 var agentId = tabName.split("_")[4];
	    $('#agentTable').bootstrapTable({  
	        url : '${basePath}/agent/user_query', // 请求后台的URL（*）            
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
	    
	    
	    $('#remarkTable').bootstrapTable({  
	        method : 'get', // 请求方式（*）  
	        toolbar : '#remarkToolbar', // 工具按钮用哪个容器  
	        cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）  
	        sidePagination : "client", // 分页方式：client客户端分页，server服务端分页（*）  
	        queryParamsType:'',
	        pagination : true, // 是否显示分页（*）  
	        pageNumber: 1,    //如果设置了分页，首页页码  
	        pageSize: 50,                       //每页的记录行数（*）  
	        pageList: [100,300,600],        //可供选择的每页的行数（*）  
	        showRefresh : true, // 是否显示刷新按钮  
	        showToggle : false, // 是否显示详细视图和列表视图的切换按钮  
	        silent : true, // 刷新事件必须设置  
	    });
        getMyPackage();
	});
	 
	
	
	function moveData() {
		var selectRow =  $("#infoTable").bootstrapTable('getSelections')[0];
		if($("#infoTable").bootstrapTable('getSelections').length  == 0){
			 alert("选择要分配的卡！");
			 return false;
		}
		$("#myModal").modal("show");
	}
	
	
	
	function moveCardByAgent(){
        var pacId = $("#packageId").val();
		var ids = "";
		for(var i in $("#infoTable").bootstrapTable('getSelections')){
			//console.info($("#infoTable").bootstrapTable('getSelections')[i]);
			 ids += $("#infoTable").bootstrapTable('getSelections')[i].id + ",";
		}
		var del = confirm("确认转移？");
		if (!del) {
			return false;
		}
		var id = $("#agentTable").bootstrapTable('getSelections')[0].id;
		if (id > 0) {
			var url = "${basePath}/cmoit/card/card_move";
			$.ajax({
				url : url,
				type : 'post',
				data : {iccids: ids , agentid : id , pacId:pacId,table:"cmoit"},
				dataType : 'json',
				success : function(data) {
					if (data.success) {
						alert(data.msg);
						$("#infoTable").bootstrapTable("refresh");
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
	
	function updateOrderStatus() {
		var del = confirm("确认修改？");
		if (!del) {
			return false;
		}
		var id = $("#infoTable").bootstrapTable('getSelections')[0].id;
		if (id > 0) {
			var url = "${basePath}/unicom/status/" + id;
			$.ajax({
				url : url,
				type : 'post',
				dataType : 'json',
				success : function(data) {
					if (data.success) {
						$("#infoTable").bootstrapTable("refresh");
						closeModel();
					} else {
						alert( data.msg);
					}

				},
				error : function(transport) {
					alert('提示', "系统产生错误,请联系管理员!", "error");
				}
			});
		}
	}

    $('#activeStartTime').datetimepicker({
        format: 'yyyy-mm-dd hh:ii',
        language:"zh-CN",
        autoclose :true ,
        todayHighlight : true,
        todayBtn : true,
        minuteStep: 30,
        minView : 0,
        initialDate:new Date()
    });

    $('#activeEndTime').datetimepicker({
        format: 'yyyy-mm-dd hh:ii',
        language:"zh-CN",
        autoclose :true ,
        todayHighlight : true,
        todayBtn : true,
        minuteStep: 30,
        minView : 0,
        initialDate:new Date()
    });
</script>
</body>
</html>