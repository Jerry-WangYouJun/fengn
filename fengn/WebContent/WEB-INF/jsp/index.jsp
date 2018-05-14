<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="${basePath}/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="${basePath}/css/style.css" />
<script src="${basePath}/js/jquery-3.1.1.min.js"></script>
<script src="${basePath}/js/bootstrap.min.js"></script>
<script type="text/javascript">

	    function getPrepay(){
	    	 if('${info.flag}' == '1'){
	    	  	  alert("此号码不支持充值，请换卡或联系管理员！");
	    	  	  return false;
	   		  }
		    	if('${info.ICCID }' == ''){
		    		  alert('无效的iccid，请点击【切换】按钮获得iccid');
		    		  return false ;
		    	}
		    	window.location.href='${basePath}/card/xinfu_wechat_pay?iccid=${info.ICCID}';
	    }
</script>
</head>
<body>
		<div class="container-fluid">
			<div class="row">
				<div class="user-head">
					<div class="inbox-avatar">
						<img src="${basePath}/img/logo.png" alt="">
					</div>
					<c:choose>
						<c:when test="${info.cardStatus == '01' }"><span class="label label-success">在线</span></c:when>
						<c:otherwise><span class="label label-default">离线</span></c:otherwise>
					</c:choose>
					
					<!--<span class="label label-default">离线</span>-->
					<button type="button" class="btn btn-info pull-right" onclick="window.location.href='${basePath}/card/searchInit'">切换</button>
				</div>
				<div class="container user-name">
					<h3 style="float: left;">${info.ICCID }</h3>
					<h3 style="float: right;">[${info.userStatus }]</h3>
					<p style="float: left;">剩余时间${info.restDay }天<span>(到期时间  ${info.dateEnd} )</span></p>
				</div>
			</div>
			<div class="row container">
				<section class="panel">
					<div class="twt-feed blue-bg">
						<p>剩余流量</p>
						<h1>
						${info.monthTotalStream - info.gprsUsed}
						<span>MB</span></h1>
						<div class="weather-category twt-category">
							<ul>
								<li class="active">
									<h5>总流量</h5>
									${info.monthTotalStream}MB
								</li>
								<li>
									<h5>已用流量</h5>${info.gprsUsed }MB
								</li>
							</ul>
						</div>
					</div>

				</section>
			</div>
			<div class="row container">
				<ul class="nav nav-tabs" role="tablist">
					<li role="presentation" class="col-md-6 col-xs-6 active">
						<a href="#home" role="tab" data-toggle="tab">当前套餐</a>
					</li>
					<li role="presentation" class="col-md-6 col-xs-6">
						<a href="#profile" role="tab" data-toggle="tab">已续费套餐</a>
					</li>
				</ul>

				<!-- Tab panes -->
				<div>
								<h5> ${info.packageType }</h5>
								<p> ${ p.remark}  </p>
								<hr />
				</div>
				<%-- <div class="tab-content" style="margin-bottom: 93px;">
					<div role="tabpanel" class="tab-pane active" id="home">
						<c:forEach items = "${info.packageList }" var = "p" >
						    <div>
								<h3> ${info.packageType }</h3>
								<p> ${ p.remark}  </p>
								<hr />
							</div>
						</c:forEach>
					</div>
					<div role="tabpanel" class="tab-pane" id="profile">
						<c:forEach items = "${info.packageList }" var = "p" >
						    <div>
								<h3> ${ p.packageName } (已续费) </h3>
								<p> ${ p.remark}  </p>
								<hr />
							</div>
						</c:forEach>
					</div>
				</div> --%>
			</div>
			<div class="row dingwei" >
				<p class="text-center">
					<button type="button" class="btn btn-primary btn-lg  col-xs-6" style="border: none;" onclick=" getPrepay()">充值续费</button>
				</p>
				<p class="text-center">
					<a href="${basePath}/card/search?iccid=${info.ICCID}">历史续费查询</a>
				</p>
				
			</div>
		</div>

</body>
</html>