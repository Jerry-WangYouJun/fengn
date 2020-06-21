<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<title>移动流量卡信息</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
    <meta name="format-detection" content="telephone=no" />
    <title>流量卡信息</title>
    <link href="${basePath}/mlbcss/css/bootstrap.min.css" rel="stylesheet" />
    <link href="${basePath}/mlbcss/css/main.css" rel="stylesheet" />
    <link href="${basePath}/mlbcss/css/iconfont.css" rel="stylesheet" />
    <link href="${basePath}/mlbcss/css/buttons.css" rel="stylesheet" />
    <link href="${basePath}/mlbcss/css/simcard_It_new.css" rel="stylesheet" />
    <link href="${basePath}/mlbcss/css/simcardDetail.css" rel="stylesheet" />
    <link href="${basePath}/mlbcss/css/theme.css" rel="stylesheet" />
    <script src="${basePath}/mlbcss/js/base.js"></script>
</head>
 <body style="font-size: 16px; display: block;" class="modal-open">
    <div id="divshowTip"><div></div></div>
    <div id="refreshDiv" class="scroller" style="transform: translate3d(0px, -40px, 0px);">
      <div class="loadings">
        <!-- 下拉刷新数据 -->
      </div>
      <!--头部-->
      <div id="limitInfo" class="hasBind-stop">
        <div class="marquee">*已机卡分离停机,请尽快插回原设备，并联系客服恢复使用！</div>
      </div>
      <!--机卡已绑定--停机-->
      <header>
        <div class="container lt-part1">
          <div class="bg_wrap">
            <div class="operator-logo" style="background-image: url(images/yd_new_logo.png)"></div>

            
                  <div class="switchNo switch-bg"><!-- 切换按钮 --> </div>
                
            <div class="header-wrap">
              <div class="use-img" style="background-image:url(images/no_headImg.png)">
                <!-- 头像 -->
              </div>
              <div class="lt-part1-simInfo" style="padding-top: 2px;">
                <span id="cardNo">${cmoitInfo.iccid}</span>
                <div class="simInfo">
                  <span id="IMEInum">SIM号:<i>${cmoitInfo.msisdn}</i></span> 
                </div>
              </div>
            </div>
            <div class="btns-container">
              
              <span class="btn-top pointer c-yellow uncertification" id="uncertification" data-target="#tipBox" data-toggle="modal" style="display: inline-block; padding: 1px 15px;">企业实名</span>
              <span class="btn-top pointer c-yellow infoNeedPerfected" id="infoNeedPerfected" data-target="#infoNeedBox" data-toggle="modal" style="padding: 1px 15px;">信息待完善</span>
              <span class="btn-top c-blue companyCertification" style="padding: 1px 15px;">企业实名</span>
              <span class="btn-top c-green certification taoModal" style="padding: 1px 15px;">个人实名</span>
              <span class="btn-top pointer c-green hasBind" style="padding: 1px 15px;">机卡已绑定</span>
              <span class="btn-top c-grey noBind" style="padding: 1px 15px;">机卡未绑定</span>
              <span class="btn-top pointer c-green cardState-normal" onclick="toIntelligentDiagnosis()" style="padding: 1px 15px; display: inline-block;">已激活</span>
              <span class="btn-top c-red cardState-stop" onclick="toIntelligentDiagnosis()" style="padding: 1px 15px; display: none;">停机</span>
              <span class="btn-top c-grey cardState-unused" style="padding: 1px 15px; display: none;">已失效</span>
            </div>
        </div>
        </div>
      </header>
      
      <!-- old 开始-->
      <div class="mainBlock normal-container">
        <!--流量-->
        <div id="history">
          <!-- 非无限套餐 -->
          <div class="mormal-meal-wrap all-normal-meal">
            <div class="meal-wrap meal-use meal-wrap-item">
              <div class="meal-item">
                <h4 class="title">总量
                  <span class="allFlow">${cmoitInfo.gprssum}MB</span>
                </h4>
                <div class="progress-wrap" attr-allflow="120">
                  <span class="progress" attr-flow="8" attr-allflow="120" style="width: 100%;"></span>
                </div>
                <div class="item-detail">
                  使用
                  <span class="last-flow"><fmt:formatNumber value="${cmoitInfo.gprsused / 1024}" pattern="###,###.##"/><sub>MB</sub></span>
                  <span class="end-time">剩余：<span>-- <sub>天</sub></span></span>
                </div>
                <div class="month" style="display: block;">${info.userStatus }</div>
              </div>
            </div>
          </div>
          <!-- 无限套餐 -->
          <div class="mormal-meal-wrap Infinite-meal">
            <div class="meal-wrap">
              <div class="meal-item">
                <p class="Infinite-use">
                  <sub class="Infinite-use">本月已用</sub>
                  <span id="surplusFlowNew"></span>
                </p>
                
                <p class="end-Infinite-time">到期日期：<span></span></p>
              </div>
              
            </div>
          </div>
        </div>

        <!--套餐tab切换-->
        <div class="container">
          <div class="wrap" id="tabs_package" style="height: 411px;">
            <div class="tabs">
              <a href="#" hidefocus="true" class="active">当前套餐</a>
              <a href="#" hidefocus="true" id="packageTitle">已续费套餐</a>
            </div>
            <div class="swiper-container">
              <div class="swiper-wrapper" style="width: 740px; height: 1164px;">
                <div class="swiper-slide swiper-slide-visible swiper-slide-active" style="width: 370px; height: 1164px;">
                  <div class="content-slide" id="nowPackageList" style="overflow: auto; position: relative; height: 371px;">
                    <p class="currentPackage">${cmoitInfo.discrip} ${unicomCard.packagename}</p>
                    <p class="currentPackageInfo normal-currentPackageInfo"></p>
                    <div class="status equipment-status" style="display: none;">
                      <span class="title">设备状态：</span>
                      <span class="line-status"></span>
                      <img class="line-img" src="images/onLine.png">
                    </div>
                    <div class="status realname-status" style="display: none;">
                      <span class="title">实名状态：</span>
                      <span class="realname-link realname-link-tao taoModal"></span>
                      <span class="realname-link companyCertification realname-link-real"></span><!--  真 -->
                      <span class="realname-link uncertification realname-link-false" id="uncertification" data-target="#tipBox" data-toggle="modal" style="display: inline-block;"></span><!--  假 -->
                    </div>
                    <div class="status card-status" style="display: none;">
                      <span class="title">机卡信息：</span>
                      <span class="card-separate-link green had-bind">机卡已绑定 &gt; </span>
                      <span class="card-separate-link no-bind">机卡未绑定 &gt; </span>
                    </div>
                  <div class="addPackageList"><ul></ul></div></div>
                </div>
                <div class="swiper-slide" style="width: 370px; height: 1164px;">
                  <div class="content-slide" id="packageList_wrap" style="height: 371px;">
                    <ul class="packageList"></ul>
                    <p class="text-center" id="nopackage_record" style="">无套餐记录！</p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!--底部-->
    <footer>
      <div class="container text-center" style="position: relative">
        <a href="javascript:;" onclick="toRenewalList(this)" class="button button-raised button-primary button-pill btn-uncertification renewalBtn">充值续费</a>
        <div class="f-link">
          <a href="javascript:void(0);" onclick="toRenewalRecord()" class="f-renewalRecord" style="border-right: none;">
            <i class="icon iconfont icon-lishirenwu"></i>历史续费
            <span id="renewalAmount">￥0</span>
          </a>
          <a href="javascript:void(0);" onclick="toHelp()" class="f-fq">
            <i class="icon iconfont iconFq"></i>
          </a>
        </div>
      </div>
    </footer>
   <script type="text/javascript">
		function  toRenewalList(obj){
		    	if('${cmoitInfo.iccid }' == ''){
		    		  alert('无效的iccid，请点击【切换】按钮获得iccid');
		    		  return false ;
		    	}
			    window.location.href='${basePath}/cmoit/info/pay_second?iccid=${cmoitInfo.iccid}&apitype=${apitype}';
		}
	</script>
    <script src="${basePath}/mlbcss/js/jquery.min.js"></script>
    <script src="${basePath}/mlbcss/js/bootstrap.min.js"></script>
    <script src="${basePath}/mlbcss/js/jshelper.js"></script>
    <script src="${basePath}/mlbcss/js/jshelper.min.js"></script>
    <script src="${basePath}/mlbcss/js/common-1.0.2.js"></script>
    <script src="${basePath}/mlbcss/js/toptip.js"></script>
    <script src="${basePath}/mlbcss/js/jweixin-1.0.0.js"></script>
    <script src="${basePath}/mlbcss/js/jquery.qrcode.min.js"></script>
    <script src="${basePath}/mlbcss/js/wx.config.init.js"></script>
    <script src="${basePath}/mlbcss/js/idangerous.swiper.min.js"></script>
    <script src="${basePath}/mlbcss/js/terminal.detail.js"></script> 
    <script src="${basePath}/mlbcss/js/layer_mobile/layer-mobile.js"></script>
    <script src="${basePath}/mlbcss/js/theme.js"></script>

  </body>
</html>