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
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<title>Insert title here</title>
</head>
<body>
<div class="container-fluid">
				<div class="user-head">
					<div class="inbox-avatar">
						<img src="${basePath}/img/logo.png" alt="">
					</div>
					<button type="button" class="btn btn-info pull-right" onclick="window.location.href='${basePath}/card/querySingle?iccid=${info.ICCID }'">回主页</button>
				</div>
			<div class="row container">
				<!-- Tab panes -->
				<div class="tab-content" style="margin-bottom: 93px;">
					<div role="tabpanel" class="tab-pane active" id="home">
						<c:forEach items = "${info.history }" var = "h" >
						    <div>
								<h3> 套餐：${h.pname }</h3>
								<p>  详情：${h.premark } </p>
								<p>  充值时间：${h.updateDate }</p>
								<p>  充值金额：${h.money }</p>
								<hr />
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
</body>
</html>