<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="basePath" value="${pageContext.request.contextPath}"
	scope="request"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>订单支付</title>
    <link href="css/main.min.css" rel="stylesheet">
    <link href="css/buttons.css" rel="stylesheet">
    <link href="css/renewals-new.css" rel="stylesheet">
    <style>
        body {
            background-color: #fbf9fe;
            color: #666;
        }
        .wx-simrenewals-container {
            background-color: #fff;
            padding: 10px;
            border-top: 1px solid #ddd;
            box-sizing: border-box;
        }
        .wx-simrenewals-container-confirm {
            padding: 10px;
            border-top: 1px solid #ddd;
            box-sizing: border-box;
        }
        .wx-simrenewals-part1 a {
            text-decoration: none;
            color: #333333;
            margin-left: 10px;
        }

        .wx-simrenewals-part1 span {
            min-width: 56px;
            display: inline-block;
        }

        .wx-simrenewals-part1 header {
            padding: 0 10px 5px 10px;
            box-sizing: border-box;
            color: #999;
        }

        .wx-simrenewals-num {
            font-size: 16px;
        }

        .wx-simrenewals-num-confirm{
            width: 90%;
            margin: 10px auto;
            text-align: center;
            font-size: 20px;
            box-sizing: border-box;
            overflow: hidden;
        }
        .wx-simrenewals-num-confirm div{
            width: 100%;
        }
        .wx-simrenewals-num-confirm .name{
            box-sizing: border-box;
            background-color: #ffffff;
            border-radius: 5px 5px 0px 0px;
            position: relative;
            border:2px solid #fb8c32 ;
            border-bottom: 0px;
        }
        .wx-simrenewals-num-confirm .name div:first-child{
            color: #000000;
            font-size: 18px;
            margin: 0px auto;
            width: 85%;
            padding: 10px 0px;
            height: 100%;
            border-bottom: 2px dashed #fb8c32 ;
        }

        .wx-simrenewals-num-confirm .name .left,.wx-simrenewals-num-confirm .name .right{
            z-index: 100;
            position: absolute;
            width: 20px;
            height: 20px;
            border:2px solid #fb8c32;
            border-radius: 50%;
            background-color: #fbf9fe;
        }
        .wx-simrenewals-num-confirm .name .left{left: -11px;bottom: -11px;}
        .wx-simrenewals-num-confirm .name .right{right: -11px;bottom: -11px;}

        .wx-simrenewals-num-confirm .number-box{
            box-sizing: border-box;
            background-color: #ffffff;
            padding: 10px 0px;
            border-radius: 0px 0px 5px 5px;
            position: relative;
            border:2px solid #fb8c32 ;
            border-top: 0px;
            color: #000000;
        }
        .wx-simrenewals-num-confirm .number-box .number{
            border-top: 0px;
            margin: 10px 0px;
        }

        .weight {font-weight: bolder;}

        .wx-simrenewals-num-confirm .number-box .tip{
            margin: 5px 0px;
            font-size: 14px;
            color: #ff0000;
        }

        .wx-simrenewals-num span {
            text-align: right;
            color: #333;
        }

        .wx-simrenewals-pk {
            font-size: 16px;
            margin-bottom: 5px;
            color: #333;
        }

        .wx-simrenewals-peroid {
            color: #666;
            font-size: 14px;
        }

        .wx-simrenewals-part2 header {
            padding: 10px;
            box-sizing: border-box;
            color: #999;
        }

        .wx-simrenewals-packages {
            overflow: auto;
        }

        .wx-simrenewals-packages-dec {
            color: #333;
            height: 20px;
        }

        .wx-simrenewals-packages-dec span {
            color: #0982df;
        }

        .xf-selected {
            background-image: url(../images/sel.png);
            background-position: right center;
            background-repeat: no-repeat;
        }

        .wx-remind-pk {
            margin: 10px 10px 0 10px;
            color: #999;
        }

        .wx-simrenewals-part3 {
            text-align: center;
        }

        .highlighted{
            color: rgb(254, 105, 8);
        }
        /*.wx-simrenewals-part3 input[type="button"] {
            width: 80%;
            padding: 10px;
            color: #fb8c32;
            border: 1px solid #fb8c32;
            box-sizing: border-box;
            border-radius: 3px;
            background-color: #fff;
            font-size: 14px;
            -webkit-appearance: none;
            font-family: "Microsoft YaHei";
        }*/
        .button-raised.button-primary.tjBtn {
            width: 80%;
            font-size: 16px;
            font-family: "Microsoft YaHei";
            border-color: #f86044;
            background: -webkit-gradient(linear, left top, left bottom, from(#fe8930), to(#f86044));
            background: linear-gradient(#fe8930, #f86044);
        }

        .button-raised.button-primary:active.tjBtn {
            background: -webkit-gradient(linear, left top, left bottom, from(#f86044), to(#fe8930));
            background: linear-gradient(#f86044, #fe8930);
            border-color: #fe8930;
            color: #da4428;
        }
        .wx-remind-pk {
            margin: 10px 10px 0 10px;
            color: #999;
        }
        /*.package_1, .package_2, .package_3{display:none;}*/
        .none-clear {
            height: 20px;
        }

        .topbor {
            border-top: #cccccc 1px solid;
        }

        .fee-label {
            font-size: 16px;
            margin-left: 10px;
        }
        /* 移动端媒体查询像素比 */
        @media only screen and (-webkit-min-device-pixel-ratio: 2), only screen and (min--moz-device-pixel-ratio: 2), only screen and (-o-min-device-pixel-ratio: 2/1), only screen and (min-device-pixel-ratio: 2) {
            .xf-selected {
                background-image: url(../images/sel@2x.png);
                background-size: 20px auto;
            }
        }
    </style>
    <script src="js/fundebug.1.2.3.min.js" apikey="8eb9c14645825f8abe6d769f9122f95d77344a9ec242558bb23343e6ad490947"></script>
    <style id="__WXWORK_INNER_SCROLLBAR_CSS">::-webkit-scrollbar { width: 12px !important; height: 12px !important; }::-webkit-scrollbar-track:vertical {  }::-webkit-scrollbar-thumb:vertical { background-color: rgba(136, 141, 152, 0.5) !important; border-radius: 10px !important; background-clip: content-box !important; border:2px solid transparent !important; } ::-webkit-scrollbar-track:horizontal {  }::-webkit-scrollbar-thumb:horizontal { background-color: rgba(136, 141, 152, 0.5) !important; border-radius: 10px !important; background-clip: content-box !important; border:2px solid transparent !important; } ::-webkit-resizer { display: none !important; }</style>
</head>
<body>
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
	<div class="wx-simrenewals-container wx-simrenewals-part1" style="border-top:0;">
    <div class="wx-simrenewals-num"><span>订单号:</span><a href="#">&nbsp;&nbsp;202006101628184101862</a></div>
</div>
<div class="wx-simrenewals-container-confirm">

    <div class="wx-simrenewals-num-confirm">
        <div class="name">
            <div>SIM卡号</div>
            <div class="left"></div>
            <div class="right"></div>
        </div>
        <div class="number-box">
            <input type="hidden" value="1440322166950">
            <p class="number weight"><span>14403</span>&nbsp;&nbsp;<span class="highlighted">2216</span>&nbsp;&nbsp;<span class="highlighted">6950</span></p>
            <p class="tip">请仔细核对SIM卡号，支付后将无法撤回</p>
        </div>
    </div>

</div>
<div class="wx-simrenewals-part1">
    <div class="wx-simrenewals-container" style="border-bottom-width: 0;">
        <div class="wx-simrenewals-pk"><span id="typeTip">续费套餐:</span><label class="fee-label highlighted weight">120M(一年)F</label></div>
        <div class="wx-simrenewals-peroid"></div>
    </div>
    <div class="wx-simrenewals-container" style="border-bottom:1px solid #ddd;">
        <div class="wx-simrenewals-num"><span>支付金额:</span><label class="fee-label highlighted weight">￥20</label></div>
    </div>
</div>
<div class="wx-remind-pk">

    <p id="renwewalsTip"></p>
    <p style="display:none;">注：每月26号为月结日</p>
</div>
<div class="none-clear"></div>
<div id="showRechar" class="wx-simrenewals-part3">

    <a href="javascript:void(0);" class="button button-raised button-primary button-pill  tjBtn" onclick="pay()">立即支付</a>
</div>
	<script type="text/javascript">
		
	</script>


</body>
</html>