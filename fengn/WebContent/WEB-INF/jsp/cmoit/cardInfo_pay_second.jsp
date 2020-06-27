<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="basePath" value="${pageContext.request.contextPath}"
	scope="request"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=320, initial-scale=1, maximum-scale=1.3, user-scalable=no">
<title>
	充值续费
</title>
<link href="${basePath}/mlbcss/css/bootstrap.min.css" rel="stylesheet"/>
<link href="${basePath}/mlbcss/css/buttons.css" rel="stylesheet"/>
<link href="${basePath}/mlbcss/css/main.css?" rel="stylesheet"/>
<link href="${basePath}/mlbcss/css/renewals-new.css" rel="stylesheet"/>
<link href="${basePath}/mlbcss/css/theme.css" rel="stylesheet"/>
    <style>
        html, 
        body {
            overflow: hidden !important;
        }

        #hint tr {
            display: none;
        }

        #yearPackage .li-tabs-onlyYear {
            padding: 10px;
            color: #fe8d2e;
            background-color: #fff;
            border: 1px solid #fe8d2e;
        }

            #yearPackage .li-tabs-onlyYear.active {
                background-color: #fe8d2e;
                color: #fff;
            }

            #yearPackage .li-tabs-onlyYear:nth-of-type(1) {
                border-bottom-right-radius: 0;
                border-top-right-radius: 0;
            }

            #yearPackage .li-tabs-onlyYear:nth-of-type(2) {
                border-radius: 0;
                border-left: 0;
                border-right: 0;
            }

            #yearPackage .li-tabs-onlyYear:nth-of-type(3) {
                border-top-left-radius: 0;
                border-bottom-left-radius: 0;
       }

.yearPackageTips,
        .monthPackageTips,
        .updatePackageTips,
        .infinitePackageTips {
            margin-bottom: 10px;
        }
.tipsHtml {
            padding-top: 10px;
        }

            .tipsHtml .title {
                font-size: 16px;
            }

            .tipsHtml > p {
                padding: 2px 0;
                font-size: 14px;
            }

        .tips-list {
            padding: 10px;
            border: 1px solid #ccc;
            display: none;
            margin-bottom: 5px;
        }

        .orange {
            color: #fe8d2e;
        }
        .list-group-item .leftBox .topContent{
            font-size: 0.42rem;
        }
        .topImageOms{
            background: #fff url(${basePath}/mlbcss/images/terminal/Oms_commend@2x@2x.png) no-repeat center;
            background-size: 100% 100%;
        }
        .tab-container .list-group-item-new{
            height:2.4rem !important;
        }
    </style>
	<style>
    @charset "utf-8";
    html{color:#000;background:#fff;
        overflow-y:scroll;
        -webkit-text-size-adjust:100%;
        -ms-text-size-adjust:100%}
        html *{outline:0;-webkit-text-size-adjust:none;-webkit-tap-highlight-color:rgba(0,0,0,0)}
    html,body{font-family:Microsoft YaHei}body,div,dl,dt,dd,ul,ol,li,h1,h2,h3,h4,h5,h6,pre,code,form,fieldset,legend,input,textarea,p,blockquote,th,td,hr,button,article,aside,details,figcaption,figure,footer,header,hgroup,menu,nav,section{margin:0;padding:0}input,select,textarea{font-size:100%}table{border-collapse:collapse;border-spacing:0}fieldset,img{border:0}abbr,acronym{border:0;font-variant:normal}del{text-decoration:line-through}address,caption,cite,code,dfn,em,th,var{font-style:normal;font-weight:500}ol,ul{list-style:none}caption,th{text-align:left}h1,h2,h3,h4,h5,h6{font-size:100%;font-weight:500}q:before,q:after{content:''}sub,sup{font-size:75%;line-height:0;position:relative;vertical-align:baseline}sup{top:-.5em}sub{bottom:-.25em}a:hover{text-decoration:underline}ins,a{text-decoration:none}
    </style>
</head>
<body style="display: block;">
    <div id="divshowTip" style="position: fixed; top: 0; left: 0; z-index: 999;"><div></div></div>
    <!--移动4G卡提示-->
    <div class="tips_yd4G sdn" id="tipHeight" style="display: block;">提示:由于运营商限制，每月最多只能续费3次</div>
    <!--头部按钮-->
    <header class="container t-container title-height sdn" id="tHeight" style="display: block;">
        <ul class="t-ul font-16" id="allPackage">
            <li class="li-tabs" data-list="list_year">跨月套餐</li>

        </ul>

        <ul class="t-ul font-16" id="yearPackage" style="display: block;">
            <li class="li-tabs active" data-list="list_year">跨月套餐</li>
        </ul>

        <ul class="t-ul font-16" id="monthPackage">

        </ul>

        <ul class="t-ul font-16" id="infinitePackage">
        </ul>
    </header>
    <!--包月套餐-->
    <div id="list_month" class="tab-container container" style="padding-top: 0px; display: none; height: 75%;">
        <div class="list-group month_list"></div>
    </div>
    <!--当月叠加套餐-->
    <div id="list_monthAdd" class="tab-container container" style="padding-top: 0px; height: 75%;">
        <div class="list-group month_addlist"></div>
    </div>
    <!--包年套餐-->
    <div id="list_year" class="tab-container container" style="display: block; height: 75%;">
        <div class="list-group year_list">
       	<ul>
        <li class="list-group-item  active"  style="display: block;height: 65px">
			<div class=" originTopImage  leftBox" style="width: 60px;">
				<div class="topContent" style="font-size: 14px"> </div>
				<div class="bottomContent" style="font-size: 14px">365天</div>
			</div>
			<div class="middleBox"><div class="packageName" style="font-size: 14px">${pac.typename }</div>
			<div class="isPrice text-left" style="font-size: 14px"><span>￥${pac.renew}</span><span class="unitPriceHide">￥170.67/GB</span></div><div class="notShow"><p>当月</p><p>使用</p></div></div>
			</li>
       	</ul>
			</div>

        <h5 class="yearAddTitle h5-title" style="display: none">加油包</h5>
        <div class="list-group year_addList">
        </div>
    </div>
    <!--无限流量套餐-->
    <div id="list_infinite" class="tab-container container" style="padding-top: 0px; height: 75%;">
        <div class="list-group infinite_list"></div>
    </div>
    <!--底部提交订单-->
    <footer2 class="container sdn" id="fheight" style="display: block;">
        <table id="hint" border="0">
            <tbody>
            <tr id="dx_tip" style="display: table-row;">
                <th>
                    <p class="DxYdTip">
                        *自然月最后一天为结算日，
              <span style="color: #fe8d2e;">充值后立即生效，不可退订</span>
                    </p>
                </th>
            </tr>
        </tbody></table>

        <a href="javascript:void(0);" onclick="doWeixinPay()" class="button button-raised button-primary button-pill  tjBtn" style="font-size: 14px">提交订单</a>
    </footer2>
    
    <script type="text/javascript">
	    function doWeixinPay(){
	    		window.location.href="${basePath}/wx/userAuth?totalFee=${pac.renew}&iccid=${iccid}&pacid=${pac.id}";
	    }
	 </script>
</body>
</html>