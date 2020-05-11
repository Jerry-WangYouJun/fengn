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
			            <button id="btn_edit" type="button" class="btn btn-default" onclick="updateSonPacCost()">  
			                <span class="glyphicon glyphicon-pencil" aria-hidden="true" ></span>修改 下级成本价 
			            </button>  
					</div>
				  </div>
			</div>
		</div>

		
</body>
    <script type="text/javascript">

    	$(function(){
    		 $('#infoTable').bootstrapTable({
			        url : '${basePath}/pac/querySonPac', // 请求后台的URL（*）            
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
			        },{  
			            field : 'cost',title : '下级成本价',  align: 'center', valign: 'middle'  
			        }, {
                        field : 'agentName',  title : '代理名',  align: 'center',   valign: 'middle'
			        }],  
			        silent : true, // 刷新事件必须设置  
			    });  
	 });




        function updateSonPacCost(){
            var del = confirm("确认修改？");
            if (!del) {
                return false;
            }
            var pacid = $("#infoTable").bootstrapTable('getSelections')[0].id;
            var agentid = $("#infoTable").bootstrapTable('getSelections')[0].agentId;
            var childcost = prompt("请输入子代理成本价（与子代理的交易价格，只能填数字）")
            //alert(ids); return false;
            if (pacid > 0) {
                var url = "${basePath}/pac/pac_cost_update";
                $.ajax({
                    url : url,
                    type : 'post',
                    data : {packageId: pacid  ,paccost:childcost , agentId: agentid},
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

    	
    </script>
    
</html>
