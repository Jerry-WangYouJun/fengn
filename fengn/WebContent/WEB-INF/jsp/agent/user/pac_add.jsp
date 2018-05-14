<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>套餐添加页面</title>
<style type="text/css">
	table{
		font-size:16px;
	}
	textarea{
		font-size:16px;
	}
</style>
<script type="text/javascript">
	function doServlet(){
		if(checkInfo()){
			$.ajax({
		    	url : "${basePath}/pac/insert",
	       		type:'post',  
	       		data: $("#userForm").serialize(),
	       		dataType: 'json',
	       		success: function(data){
		           	parent.$.messager.alert('提示',data.msg);
		       		if(data.success == true) {
		       			parent.doSearch();
		       			parent.$('#dlg-frame').dialog("close");
			       	}
	       		},
	       		error: function(transport) { 
	       			$.messager.alert('提示',"系统产生错误,请联系管理员!","error");
	        	} 
	       	});
		}
	}
	function checkInfo(){
		if($("#typename").val() == ""){
			$.messager.alert("提示","套餐名称不能为空!","error");
			return false;
		}
		return true;
	}
	
	
	  $(function(){
	  })
</script>
</head>
<body>
	<form:form id="userForm" action="${basePath }/insert" modelAttribute="pac" method="post">
	     <form:hidden path="id" id = "id" />
		<table width="100%">
		  	<tr>
		  		<td>套餐名：</td>
		  		<td style="padding: 20px">
		  			<form:input id="typename" path="typename" />
		  		</td>
		  		<td>套餐描述：</td>
		  		<td style="padding: 20px">
		  			<form:input id="discrip" path="discrip" />
		  		</td>
		  	</tr>
		  	<tr>
		  		<td>成本价：</td>
		  		<td style="padding: 20px">
		  			<form:input id="cost" path="cost"  />
		  		</td>
		  		<td>续费价：</td>
		  		<td style="padding: 20px">
		  			<form:input id="renew" path="renew"  />
		  		</td>
		  	</tr>
		   <tr>
		  		<td>备注：</td>
		  		<td style="padding: 20px">
		  			<form:input id="remark" path="remark"   />
		  		</td>
		  	</tr> 
		  </table>
	</form:form>
</body>
</html>