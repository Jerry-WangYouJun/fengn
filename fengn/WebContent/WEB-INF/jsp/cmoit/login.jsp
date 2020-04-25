<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
  <head>
      <title>物联卡管理系统</title>
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="this is my page">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <link href="${pageContext.request.contextPath}/media/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
	<link href="${pageContext.request.contextPath}/media/css/bootstrap-responsive.min.css" rel="stylesheet" type="text/css"/>
	<link href="${pageContext.request.contextPath}/media/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
	<link href="${pageContext.request.contextPath}/media/css/style-metro.css" rel="stylesheet" type="text/css"/>
	<link href="${pageContext.request.contextPath}/media/css/style.css" rel="stylesheet" type="text/css"/>
	<link href="${pageContext.request.contextPath}/media/css/style-responsive.css" rel="stylesheet" type="text/css"/>
	<link href="${pageContext.request.contextPath}/media/css/default.css" rel="stylesheet" type="text/css" id="style_color"/>
	<link href="${pageContext.request.contextPath}/media/css/uniform.default.css" rel="stylesheet" type="text/css"/>
	<link href="${pageContext.request.contextPath}/media/css/login.css" rel="stylesheet" type="text/css"/>
	<link rel="shortcut icon" href="${pageContext.request.contextPath}/media/image/favicon.ico" />
	<style>
		.boxH{ box-sizing:content-box
		}
	</style>

  </head>
  <body class="login">
	<div class="logo">
		<h2 style="color: white;">物联卡管理系统2222</h2>
	</div>

	<div class="content">
		<form class="form-vertical login-form" action="${pageContext.request.contextPath }/cmoit/user/checkUser" method="post">
			<h3 class="form-title">欢迎登陆</h3>
			<div class="alert alert-error hide">
				<button class="close" data-dismiss="alert"></button>
				<span>用户名或密码不能为空</span>
			</div>
			<div class="alert alert-wrong hide">
				<button class="close" data-dismiss="alert"></button>
				<span>用户名或密码错误</span>
			</div>
			<div class="control-group">
				<label class="control-label visible-ie8 visible-ie9">用户名</label>
				<div class="controls">
					<div class="input-icon left">
						<i class="icon-user"></i>
						<input class="m-wrap placeholder-no-fix boxH" type="text" placeholder="请输入用户名" name="userNo" value="admin"/>
					</div>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label visible-ie8 visible-ie9">密码</label>
				<div class="controls">
					<div class="input-icon left">
						<i class="icon-lock"></i>
						<input class="m-wrap placeholder-no-fix boxH" type="password" placeholder="请输入密码" name="pwd" value="1980zxcv"/>
					</div>
				</div>
			</div>
			<div class="form-actions">
				<button type="submit" class="btn green ">
				登陆 <i class="m-icon-swapright m-icon-white"></i>
				</button>            
			</div>
		</form>
	</div>

	<div class="copyright">

		2017 &copy; 物联卡管理系统

	</div>

	<script src="${pageContext.request.contextPath}/media/js/jquery-1.10.1.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/media/js/jquery-migrate-1.2.1.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/media/js/jquery-ui-1.10.1.custom.min.js" type="text/javascript"></script>      
	<script src="${pageContext.request.contextPath}/media/js/bootstrap.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/media/js/jquery.slimscroll.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/media/js/jquery.blockui.min.js" type="text/javascript"></script>  
	<script src="${pageContext.request.contextPath}/media/js/jquery.cookie.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/media/js/jquery.uniform.min.js" type="text/javascript" ></script>
	<script src="${pageContext.request.contextPath}/media/js/jquery.validate.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/media/js/app.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/media/js/login.js" type="text/javascript"></script>      
	<script>
		jQuery(document).ready(function() {     
		  App.init();
		  Login.init();
		});
		
			 $(function(){
				 var msg = "${msg}" ;
				 if(msg != ''){
					 $('.alert-wrong', $('.login-form')).show();
					 $('.alert-wrong', $('.login-form')).hide(1500);
				 }
			 });
	</script>
</body>
</html>
