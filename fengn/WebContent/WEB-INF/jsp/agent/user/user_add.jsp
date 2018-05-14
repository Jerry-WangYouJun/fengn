<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户添加页面</title>
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
		    	url : "${basePath}/insert",
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
		if($("#userNo").val() == ""){
			$.messager.alert("提示","用户名不能为空!","error");
			return false;
		}
		if($("#agentName").val() == ""){
			$.messager.alert("提示","所属代理商不能为空!","error");
			return false;
		}
		if($("#pwd").val() == ""){
			$.messager.alert("提示","密码不能为空!","error");
			return false;
		}
		if($("#pwd2").val() == ""){
			$.messager.alert("提示","确认密码不能为空!","error");
			return false;
		}
		if(checkPwd() == "1"){
			 return false ;
		}
		return true;
	}
	
	function checkPwd(){
		 var pwd = $("#pwd").val();
		 var pwd2 = $("#pwd2").val();
		 if( pwd != "" && pwd2 != ""){
			   if(pwd != pwd2 ){
				   $.messager.alert("提示","两次输入的密码不一致!","error");
				   return '1' ;
			   }
		 }
	}
	
	function checkAgent(){
		var agentName = $("#agentName").val();
		if(agentName ==""){
			 return false ;
		}
		$.ajax({
	    	url : "${basePath}/agent/checkAgent",
       		type:'post',  
       		data: {"agentName":agentName},
       		dataType: 'json',
       		success: function(data){
	       		if(data.success == true) {
       				$("#agentId").val(data.agentid);
		       	}else{
		       		$.messager.alert('提示',"输入的代理商有误,请确认!","error");  
		       	}
       		},
       		error: function(transport) { 
       			$.messager.alert('提示',"系统产生错误,请联系管理员!","error");
        	} 
       	});
	}
	
	  $(function(){
		     if("${user.id}"  > 0){
		    	    $("#userNo").attr("disabled",true); 
		    	    $("#userName").attr("disabled",true); 
		    	    $("#agentName").attr("disabled",true); 
		     }
	  })
</script>
</head>
<body>
	<form:form id="userForm" action="${basePath }/insert" modelAttribute="user" method="post">
	     <form:hidden path="id" id = "id" />
		<table width="100%">
		  	<tr>
		  		<td>登录名：</td>
		  		<td style="padding: 20px">
		  			<form:input id="userNo" path="userNo" />
		  		</td>
		  		<td>用户名称：</td>
		  		<td style="padding: 20px">
		  			<form:input id="userName" path="userName" />
		  		</td>
		  	</tr>
		  	<tr>
		  		<td>密码：</td>
		  		<td style="padding: 20px">
		  			<form:input id="pwd" path="pwd"  />
		  		</td>
		  		<td>确认密码：</td>
		  		<td style="padding: 20px">
		  			<form:input id="pwd2" path="pwd"  />
		  		</td>
		  	</tr>
		   <tr>
		  		<td>所属代理商：</td>
		  		<td style="padding: 20px">
		  			<form:hidden id="agentId" path="agentId"  />
		  			<form:input id="agentName" path="agentName"  onchange="checkAgent();" />
		  		</td>
		  	</tr> 
		  </table>
	</form:form>
</body>
</html>