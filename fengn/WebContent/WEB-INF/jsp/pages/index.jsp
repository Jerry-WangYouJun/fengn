<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>丰宁联通信息后台管理首页</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript">
		function loginOut(){
					window.location.href = "${basePath}/loginOut" ;
		}
		$(function(){
			var treeData = [{
				text:"基本信息管理",	
				children:[{
					text:"代理商管理",
					attributes:{
						urlType:"${basePath}/jsp/agent/agent_manage.jsp"
					}
				},{
					text:"套餐管理",
					attributes:{
						urlType:"${basePath}/jsp/agent/pac_list.jsp"
					}
				}]
			}];
			$("#fixedtree").tree({
				data:treeData,
				lines:true,
				onClick:function(node ){
					if(node.attributes){
						openTab(node.text,node.attributes);
					}
				}
			});
			if("${groupId}"=="2" || "${groupId}"=="3"){
				$("#cardtree").tree({
					url:"${basePath}/treeindex/tree_unicom", 
					loadFilter: function(data){	
								 return data;	
						},
					lines:true,
					onClick:function( node){
						if(node.attributes.priUrl != ""){
							openTab("联通卡信息-"+ node.text , node.attributes);
							return false;
						}
					}
				});
				$("#kickbacktree").tree({
					url:"${basePath}/treeindex/kickback_unicom", 
					loadFilter: function(data){	
								 return data;	
						},
					lines:true,
					onClick:function( node){
						if(node.attributes.priUrl != ""){
							openTab("联通返佣-"+ node.text , node.attributes);
							return false;
						}
					}
				});
			}
			if("${groupId}"=="1" || "${groupId}"=="3"){
				$("#cardtree2").tree({
					url:"${basePath}/treeindex/tree", 
					loadFilter: function(data){	
								 return data;	
						},
					lines:true,
					onClick:function( node){
						if(node.attributes.priUrl != ""){
							openTab("移动卡信息-"+ node.text , node.attributes);
							return false;
						}
					}
				});
				$("#kickbacktree2").tree({
					url:"${basePath}/treeindex/kickback", 
					loadFilter: function(data){	
								 return data;	
						},
					lines:true,
					onClick:function( node){
						if(node.attributes.priUrl != ""){
							openTab("移动返佣-"+ node.text , node.attributes);
							return false;
						}
					}
				});
			}
			
			
			
			
			function openTab(text, attr ){
				var url = attr.urlType;
				if($("#tabs").tabs('exists',text)){
					$("#tabs").tabs('select',text);
				}else{
					if(attr.urlType == "unicom_card"){
						url = "${basePath}/jsp/unicom/card_list.jsp"
					}else if(attr.urlType == "unicom_kickback"){
						url = "${basePath}/jsp/unicom/kickback_list.jsp"
					}else if(attr.urlType == "card"){
						 url = "${basePath}/jsp/agent/card_list.jsp"
					}else if(attr.urlType == "kickback"){
						url = "${basePath}/jsp/agent/kickback_list.jsp"
					}
					var content="<iframe frameborder='0' scrolling='auto' style='width:100%;height:100%' src="+ url+"></iframe>";
					$("#tabs").tabs('add',{
						id: attr.agentId,
						title:text,
						closable:true,
						content:content
					});
				}
			}
		});
	</script>
  </head>
  
 <body class="easyui-layout">
	<div region="north" style="height:15%; background-color:#13A7D5;">
		<table width="100%">
			<tr>
				<td align="center"> 
					
				</td>
			</tr>
			<tr>
				<td align="right">
					<font color="white" size="2">当前用户： </font>
					<font color="red" size="4">${sessionScope.user }</font>
					<a href="javascript:loginOut();" style="text-decoration: none;font-size: 12px; color:black;">
						<img alt="" src="${basePath}/images/login_out.png"> 退出
					</a>
					
					 &nbsp;&nbsp;&nbsp;&nbsp;
				</td>
			</tr>
		</table>
	
	</div>
	
	<div region="west" style="width:200px;" title="导航菜单" split="true">
		<ul id="fixedtree">
		
		</ul>
		<ul id="cardtree">
		
		</ul>
		<ul id="kickbacktree">
		
		</ul>
		<ul id="cardtree2">
		
		</ul>
		<ul id="kickbacktree2">
		
		</ul>
	</div>
	
	<div region="center" class="easyui-tabs" fit="true" border="false" id="tabs" style="height: 80%">
		<div title="首页" >
			<div align="center" style="padding-top: 100px;"><font color="red" size="10">欢迎使用物联网管理系统</font></div>		
		</div>
	</div>
</body>
</html>
