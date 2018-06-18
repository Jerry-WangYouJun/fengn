<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="basePath" value="${pageContext.request.contextPath}"
	scope="request"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name="format-detection" content="telephone=no">
<title>流量卡信息</title>
<link href="${basePath }/css/bootstrap.min.css" rel="stylesheet">
<link href="${basePath }/css/iconfont.css" rel="stylesheet">
<link href="${basePath }/css/mobile.css" rel="stylesheet">
<style>

        @-webkit-keyframes scaleout {
            0% {
                color:rgba(255,0,0,1);
                background:rgba(255,255,255,1);
            }
            25%{
                color:rgba(255,0,0,1);
                background:rgba(255,0,0,0.6);
            }
            50%{
                color:rgba(255,255,255,1);
                background:rgba(255,0,0,1);
                box-shadow: 0px 0px 5px 5px rgba(255,0,0,0.5);
            }
            75%{
                color:rgba(255,0,0,1);
                background:rgba(255,0,0,0.6);
            }
            100%{
                color:rgba(255,0,0,1);
                background:rgba(255,255,255,1);
            }
        }
        @keyframes scaleout {
            0% {
                color:rgba(255,0,0,1);
                background:rgba(255,255,255,1);
            }
            25%{
                color:rgba(255,0,0,1);
                background:rgba(255,0,0,0.6);
            }
            50%{
                color:rgba(255,255,255,1);
                background:rgba(255,0,0,1);
                box-shadow: 0px 0px 5px 5px rgba(255,0,0,0.5);
            }
            75%{
                color:rgba(255,0,0,1);
                background:rgba(255,0,0,0.6);
            }
            100%{
                color:rgba(255,0,0,1);
                background:rgba(255,255,255,1);
            }
        }
        @keyframes authentication {
            0% {
                color:rgba(245,129,14,1);
                background:rgba(255,255,255,1);
            }
            25%{
                color:rgba(245,129,14,1);
                background:rgba(245,129,14,0.6);
            }
            50%{
                color:rgba(255,255,255,1);
                background:rgba(245,129,14,1);
                box-shadow: 0px 0px 5px 5px rgba(245,129,14,0.5);
            }
            75%{
                color:rgba(245,129,14,1);
                background:rgba(245,129,14,0.6);
            }
            100%{
                color:rgba(245,129,14,1);
                background:rgba(255,255,255,1);
            }
        }
        @-webkit-keyframes authentication {
            0% {
                color:rgba(245,129,14,1);
                background:rgba(255,255,255,1);
            }
            25%{
                color:rgba(245,129,14,1);
                background:rgba(245,129,14,0.6);
            }
            50%{
                color:rgba(255,255,255,1);
                background:rgba(245,129,14,1);
                box-shadow: 0px 0px 5px 5px rgba(245,129,14,0.5);
            }
            75%{
                color:rgba(245,129,14,1);
                background:rgba(245,129,14,0.6);
            }
            100%{
                color:rgba(245,129,14,1);
                background:rgba(255,255,255,1);
            }
        }

        .state img{
            width: 14px;
            position: relative;
            top: -1px;
        }

        .swiper-slide {
            height: auto !important;
        }


        .realName-tip h6{
            color:#000;
            font-size:13px;
            margin-bottom:7px;
        }
        .realName-tip p{
            font-size:12px;
        }

    </style>
</head>
<body>
	<!--头部-->
	<header>
	<div class="container lt-part1">
		<div class="header-wrap">
			<div class="img-container">
				<img src="${basePath }/images/lt-logol.png" class="logol">
			</div>
			<div class="lt-part1-simInfo" style="padding-top: 5px;">
				ICCID:<span class="cardNo">${info.ICCID }</span>
				<div class="simInfo">
					<span class="IMEInum">设备IMEI:<i>86201003227473*</i></span>
				</div>
			</div>
		</div>
		<div class="btns-container">
			<span class="btn-top c-blue companyCertification"
				style="display: inline-block;">${info.companyLevel }</span> <span
				class="btn-top pointer c-green cardState-normal"
				style="display: inline-block;">${info.cardStatus }</span> <span
				class="btn-top pointer switchNo pull-right"><i
				class="icon iconfont"></i>切换</span>

		</div>
	</div>
	</header>
	<div class="mainBlock">
		<!--流量-->
		<div class="container">
			<div class="innerContainer">
				<div class="resetTip">
					<p class="cardTag pull-left">
						<span class="resetTag" style="display: none;">本月</span><span
							style="padding-left: 10px; color: #353535;">总量 <span
							class="allFlow">${info.monthTotalStream }</span></span>
					</p>
					<p class="flowTips pull-right">*26号月结清零</p>
					<div class="pull-right date-info" style="display: block;">
						剩余<span class="lastDays">${info.deadline }</span>
					</div>
					<div class="pull-right state" style="display: block;">
						<img src="${basePath }/images/offLine.png"> <span
							style="color: rgb(170, 170, 170);">离线</span>
					</div>
				</div>
				<div class="leftFlowTxt">
					<span class="surplusFlowContent"><sub
						style="bottom: .1em; font-size: 55%; padding-right: 10px;">剩余</sub><span>${info.gprsRest }</span></span>
				</div>
				<div class="flowState"></div>
			</div>
		</div>
		<!--套餐tab切换-->
		<div class="container">
			<div class="wrap" id="tabs_package">
				<div class="tabs">
					<a href="#" hidefocus="true" id="packageNow" class="active">当前套餐</a>
					<a href="#" hidefocus="true" id="packageTitle" class="">已续费套餐</a>
				</div>
				<div class="swiper-container">
					<div class="swiper-wrapper"
						style="width: 710px; height: 25px; transform: translate3d(0px, 0px, 0px); transition-duration: 0.5s;">
						<div class="swiper-slide swiper-slide-visible swiper-slide-active"
							style="width: 355px; height: 25px;">
							<div class="content-slide" id="packageList_wrap"
								style="display: none;">
								<ul class="packageList">
									<li><span class="pull-right">[<i class="packageNum">1</i>笔]
									</span><span>${info.packageType }</span></li>
								</ul>
							</div>
							<div class="content-slide" id="nowPackageList"
								style="overflow: auto;">
								<p>${info.packageType }</p>
								<p>${info.packageDetail }</p>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!--end tab切换-->
	</div>
	</div>
	<!--底部-->
	<footer>
	<div class="container text-center" style="position: relative">
		<a href="javascript:;" onclick="toRenewalList(this)"
			class="button button-raised button-primary button-pill btn-uncertification renewalBtn">充值续费</a>
		<div class="f-link">
			<a href="javascript:void(0);" onclick="toRenewalRecord()"
				class="f-renewalRecord"> <i class="icon iconfont"></i>历史续费<span>￥15</span>
			</a> <a href="javascript:void(0);" onclick="toMonthAmount()"
				class="f-flowDetail"> <i class="icon iconfont"></i>本月用量详情
			</a> <a href="javascript:void(0);" onclick="toIntelligentDiagnosis()"
				class="f-znzd"> <i class="icon iconfont iconZnzd"></i>智能诊断
			</a>
		</div>
	</div>
	</footer>
	<a href="javascript:;" class="f-help"><i class="icon iconfont"></i></a>

	<div class="successBind"></div>
	<script src="${basePath }/scripts/jquery.min.js"></script>
	<script src="${basePath }/scripts/bootstrap.min.js"></script>
	<script type="text/javascript">
		$(function() {
			$("#tabs_package a").click(function() {
				$(this).addClass("active").siblings().removeClass("active");
			})
			$("#packageTitle").click(function() {
				$("#nowPackageList").css("display", "none");
				$("#packageList_wrap").css("display", "block");
			})
			$("#packageNow").click(function() {
				$("#packageList_wrap").css("display", "none");
				$("#nowPackageList").css("display", "block");
			})
		})
	</script>


</body>
</html>