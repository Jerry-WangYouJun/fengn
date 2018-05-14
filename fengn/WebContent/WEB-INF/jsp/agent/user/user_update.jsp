<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common.jsp"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户修改页面</title>
	<link rel="stylesheet" type="text/css" href="${basePath}/js/jquery-easyui-1.4/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${basePath}/js/jquery-easyui-1.4/themes/icon.css">
	<script type="text/javascript" src="${basePath}/js/jquery-easyui-1.4/jquery.min.js"></script>
	<script type="text/javascript" src="${basePath}/js/jquery-easyui-1.4/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${basePath}/js/jquery-easyui-1.4/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${basePath}/js/common.js"></script>
	
<style type="text/css">
	table{
		font-size:12px;
	}
	textarea{
		font-size:12px;
	}
</style>
<script type="text/javascript">
	function doServlet(){
		if(checkInfo()){
			$.ajax({
		    	url : "${basePath}/user/user_update",
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
	       	
	       //	$("#userForm").submit();   //PUT方式提交
		}
	}
	
	function checkInfo(){
		if($("#userNo").val() == ""){
			$.messager.alert("提示","用户名不能为空!","error");
			return false;
		}
		return true;
	}
</script>
</head>
<body>
	<form:form id="userForm" action="${basePath }/user/user_update" modelAttribute="user" method="post">
		<input type="hidden" name="_method" value="PUT"/>
		<form:hidden path="id"/>
		<table width="100%">
		  	<tr>
		  		<td>用户名：</td>
		  		<td>
		  			<form:input id="userNo" path="userno" />
		  		</td>
		  		<td>籍贯：</td>
		  		<td>
		  			<form:select id="hometown" path="hometown" items="${requestScope.provinces }" itemLabel="dicvalue" itemValue="diccode" >
		  			</form:select>
		  		</td>
		  		<td>所在部门：</td>
		  		<td>
		  			<form:select id="deptNo" path="deptid" items="${requestScope.depts }" itemLabel="deptname" itemValue="id"></form:select>
		  		</td>
		  	</tr>
		  	<tr>
		  		<td>姓名：</td>
		  		<td>
		  			<form:input id="userName" path="username" />
		  		</td>
		  		<td>手机号：</td>
		  		<td>
		  			<form:input id="telphone" path="telphone" />
		  		</td>
		  		<td>邮箱：</td>
		  		<td>
		  			<form:input id="email" path="email" />
		  		</td>
		  	</tr>
		  	<tr>
		  		<td>性别：</td>
		  		<td>
		  			<form:radiobuttons path="sex" items="${requestScope.sexs }"  itemLabel="dicvalue" itemValue="diccode" />
		  		</td>
		  		<td>年龄：</td>
		  		<td>
		  			<form:input path="age" />
		  		</td>
		  		<td>岗位：</td>
		  		<td>
		  			<form:input path="position"/>
		  		</td>
		  	</tr>
		  	<tr>
		  		<td valign="top" colspan="">备注：</td>
				<td colspan="5">
					<form:textarea path="remark"/>
				</td>
		  	</tr>
		  </table>
	</form:form>
</body>
</html>