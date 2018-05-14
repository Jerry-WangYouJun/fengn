<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
System.out.println("in jsapi.jsp");
%>
<!DOCTYPE html>
<html>
	<head>
		<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<link href="${basePath}/css/bootstrap.min.css" rel="stylesheet">
		<link rel="stylesheet" href="${basePath}/css/style.css" />
		<script src="${basePath}/js/jquery-3.1.1.min.js"></script>
		<script src="${basePath}/js/bootstrap.min.js"></script>
		<script type="text/javascript">  
			function pay(){
				 var timestamp = "${timeStamp}";
				var nonceStr = '${nonceStr}';
				var appid = '${appid}';
				var paySign = '${sign}';
				var packages = "${packageValue}";
				var url = "${pageContext.request.contextPath}/wx/success?orderId=${orderId}";
				WeixinJSBridge.invoke('getBrandWCPayRequest', {
			           "appId" : appid,     //公众号名称，由商户传入     
			           "timeStamp" : timestamp,  //时间戳，自1970年以来的秒数     
			           "nonceStr" : nonceStr, //随机串     
			           "package" : packages,     
			           "signType" : "MD5",  //微信签名方式：     
			           "paySign" : paySign //微信签名 
			       },
			       function(res){
			           if(res.err_msg == "get_brand_wcpay_request:ok" ) {
			        	  // alert(123);
			        	   window.location.href="${pageContext.request.contextPath}/wx/success?orderId=${orderId}";
			           }     // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。 
			           else{
			        	   $.alert("fail");
			           }
			       }
			   ); 
			}
		</script>	
	    <title>订单-支付</title>
	</head>
<body>

		<div class="container-fluid">
			<div class="container user-name" style="border: 1px solid #00C7FE; padding: 20px; -webkit-border-radius: 10px 10px 10px 10px;">
				<h3 style="text-align: center;">ICCID号</h3>
				<h3 style="text-align: center; margin-top: 20px;">${iccid }</h3>
				<p style="text-align: center; margin-top: 10px; color: #fd4a4a;">请仔细核对ICCID号，支付后将无法撤回</p>
			</div>
			<div role="tabpanel" class="tab-pane active" style="margin-top: 10px;">
				<div class="container">
					<h3>120M 12个月（每个月10M）</h3>
					<p>每月10M全国流量，流量月底清零，流量超出停机，可随时累加套餐包。</p>
					<h3 style="float: left;">支付金额：￥18</h3>
				</div>
			</div>

			<div class="row dingwei">
				<p class="text-center">
					<button style="border: none;" type="button" class="btn btn-primary btn-lg  col-xs-6" onclick="pay();">确认支付</button>
				</p>
			</div>
		</div>
</body>


</html>
