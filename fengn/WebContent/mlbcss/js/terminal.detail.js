var simTypes,
    cardType = shared.getUrlQuery("cardType") ? shared.getUrlQuery("cardType") : "",
    mobile = shared.getUrlQuery("mobile") ? shared.getUrlQuery("mobile") : "",
    imei = shared.getUrlQuery("imei") ? shared.getUrlQuery("imei") : "",
    browser = shared.getUrlQuery("browser"),
    apptype = shared.getUrlQuery("apptype"),
    wxchatId = shared.getUrlQuery("wechatId"), // $("#hid_WeChatId").val();
    accessname = shared.getUrlQuery("accessname"),
    mchId = shared.getUrlQuery("mchId"),
    from_app = shared.getUrlQuery("fromapp"),
    isYD = shared.getUrlQuery("isYD") == 'true' ? true : false,
    phoneReg = /^1\d{10}$/,
    activeTime,
    currentTime,
    vexpireTime,
    commState, //通信状态 0禁用 1开启
    isUsageReset,
    isLimitlessUsage, //流量套餐
    historyMonthUsageList,
    totalMonthUsage,
    firstActiveTime,
    historyOption, 
    isExpire, //是否到期   true: 未到期  false: 到期
    HistoryBill = null, 
    getNetWorkSpeed,
    getHigUsageToPeriod,
    getUsageToPeriod,
    keepUsageResetTimeType,
    newTotalMonthUsage, //本月总用量
    realNameLevel,
    iccidFull,
    simstate;


var contentPackage = ''; //1： 内容套餐
// var contentPackage = 1; //1： 内容套餐
var isLogin = "";
if (contentPackage == 1) {
    $('.normal-container').hide();
    $('.meal-container').show();
    $('body').css('background', '#f4f4f4');
    $('html').css('background', '#f4f4f4');
    $('.container').css('background', '#f4f4f4');
    $('footer').css('background', '#f4f4f4');
} else {
    $('.normal-container').show();
    $('.meal-container').hide();

}

(function() {
    // 初始化微信配置
    //wxapi.readyWXCgi("../../", []);

    //语音套餐长度控制
    var leftFlow_lg = $("#voice_left_flow").text();
    if (leftFlow_lg.length > 11) {
        $("#voice_flow_con").attr("class", "col-xs-7");
        $("#voice_time_con").attr("class", "col-xs-5");
        $(".font-30").css("font-size", "25px");
        $(".voice-package-details .col-xs-5,.voice-package-details .col-xs-7,.voice-package-details .col-xs-6").css("padding", "0 5px");
    } else if (leftFlow_lg.length > 10) {
        $("#voice_flow_con").attr("class", "col-xs-7");
        $("#voice_time_con").attr("class", "col-xs-5");
        $(".voice-package-details .col-xs-5,.voice-package-details .col-xs-7,.voice-package-details .col-xs-6").css("padding", "0 5px");
    } else if (leftFlow_lg.length > 9) {
        $("#voice_flow_con").attr("class", "col-xs-7");
        $("#voice_time_con").attr("class", "col-xs-5");
        $(".voice-package-details .col-xs-5,.voice-package-details .col-xs-7,.voice-package-details .col-xs-6").css("padding", "0 10px");
    } else {
        $("#voice_flow_con").attr("class", "col-xs-6");
        $("#voice_time_con").attr("class", "col-xs-6");
        $(".voice-package-details .col-xs-5,.voice-package-details .col-xs-7,.voice-package-details .col-xs-6").css("padding", "0 10px");
    }
    //APP调用隐藏切换按钮
    if (from_app == "h5") {
        $(".switchNo").hide();
    }
})();

var simId = $("#hid_simId").val(),
    searchURL = $("#hid_url").val(),
    userName;

$(".complaint-link").click(function() {
    var _iccid = $('#hid_iccid').val();
    window.location.href = '../complaint/index.html?wxchatId=' + wxchatId + '&iccid=' + _iccid;
});

$(".use-img").click(function() {
    var userId = $("#hid_userId").val();

    if (isLogin) {
        location.href = "../member/member_center.aspx?simId=" + simId + "&imgLogo=false";

    } else {

        location.href = "../member/register.aspx?onlyVer=true&action=mobilereal&imei=" + imei + "&mobile=" + mobile + "&userId=" + userId + "&iccid=" + iccidFull + "&simId=" + simId + "&apptype=" + apptype + "&wechatId=" + wxchatId + "&mchId=" + mchId + "&accessname=" + accessname + "&fromapp=" + from_app + "&cardType=" + cardType + "&realNameLevel=" + realNameLevel +"&simstate=" +simstate;
    }

})

$(".real-name-title").click(function(){
    $(".realName-tip").toggle(400);
    var parentWrap = $(this).parent().parent().parent('.modal-dialog');
    var showType = $(this).siblings('.realName-tip').height();

})
$(".uncertification").click(function(){
    $(".realName-tip").hide();
})



function closeLayer() {
    layer.closeAll();
}

function strDecrypt(str) {
    var output = new Array();
    var i;
    var s = str.split("\\");
    for (i = 1; i < s.length; i++) {
        output += String.fromCharCode(parseInt(s[i], 8));
    }

    return output;

}


// 内容套餐选项卡切换
$('.detail-title li').click(function() {
    var index = $(this).index();
    $(this).addClass('active').siblings('li').removeClass('active');
    $('.detail-info li').eq(index).addClass('show-active').siblings('li').removeClass('show-active');
})


// 暂时屏蔽电信、移动本月用量，因为后台没有数据
function dxModalClose(simFromType,el) {
    el.on("click", function() {
        if (HistoryBill === null) {
            $.getJSON('../../api/GetSimHistoryBill', historyOption)
                .done(function(res) {
                    if (res.error !== 0) {
                        alert("数据异常!");
                        return
                    }
                    historyMonthUsageList = res.result;
                    totalMonthUsage ? historyMonthUsageList[0].UsageMB = totalMonthUsage : '';
                    HistoryBill = historyMonthUsageList;
                    dataInteractive.historyMonthUsage(historyMonthUsageList);
                    if (simFromType === 1 && contentPackage != 1) { //暂时屏蔽电信、移动本月用量，因为后台没有数据  2是电信 1联通 0移动
                        // contentPackage  1: 内容套餐
                        $('#myModal').modal('show'); //非内容套餐
                    }
                })
                .fail(function() {
                    alert("网络异常")
                })
        } else {
            dataInteractive.historyMonthUsage(HistoryBill);
            if (simFromType === 1 && contentPackage != 1) { //暂时屏蔽电信、移动本月用量，因为后台没有数据  2是电信 1联通 0移动
                $('#myModal').modal('show');
            }
        }
    })
}


$("[role='dialog']").on('show.bs.modal', function() {
    var $this = $(this);
    var $modal_dialog = $this.find('.modal-dialog');
    $this.css('display', 'block');
    $modal_dialog.css({
        'margin-top': Math.max(0, ($(window).height() - $modal_dialog.height()-50) / 2)
    });
});
$(".hasBind").click(function() {
    $("#bindShow").show();
    $("#self_active_success").hide();
    $('#cardBindModal').modal('show');
});

// 内容套餐机卡分离提示
$(".had-bind").click(function() {
    $("#bindShow").show();
    $("#self_active_success").hide();
    $('#cardBindModal').modal('show');
});

//手淘实名点击弹出框
$(".taoModal").click(function() {
    $('#TaoRealName').modal('show');
});


var dataInteractive = (function() {
    return {
        header: header,
        usageDataNew: usageDataNew,
        packageListNew: packageListNew,
        historyMonthUsage: historyMonthUsage,
        hasBindModalInfo: hasBindModalInfo,
        TaoRealNameModal: TaoRealNameModal,
        simFromType: 1,
        simState: simState,
        dxSimState: dxSimState
    }

    function header(data) {
        var width = $(window).width();
        dataInteractive.simFromType = data.simFromType;
        simTypes = data.simFromType;
        //电信卡 显示本月用量
        if (simTypes == 2) {
            $('.f-flowDetail').html('<i class="icon iconfont">&#xe605;</i>本月用量');
        }
        //是否显示续费记录
        if (data.isShowWXRenewals == 0)
            $(".f-renewalRecord").hide();
        //是否允许续费
        if (data.limitType == 1){
            $(".renewalBtn").attr("href", "#").css({
                background: "#ccc",
                borderColor: "#ccc"
            }).addClass('noRenewal');

            $("#ljts").attr("disabled","disabled").css({"color":"#666","border":"1px solid #e1e1e1","background-color":"#e1e1e1"});
            $("#ljts img").attr("src","../images/terminal/hj-d_close.gif");
        }
            
        //是否播放公告
        if (data.limitInfo) {
            $("#limitInfo").text(data.limitInfo).show();
            $(".lt-part1").css("padding-top", "0px");
        }
        if (data.simFromType === 1) {
            // 联通
            $(".operator-logo").css('background-image', 'url(../images/terminal/lt_new_logo.png)')
            $("#cardNo").text(addBlank(data.iccid)); //.css("font-size", fontSize)
            if (data.imei == "") {
                $("#IMEInum").html("SIM号:<i>" + data.simNo + "</i>");
            } else {
                $("#IMEInum").html("当前IMEI:<i>" + data.imei.substring(0, 14) + "*</i>");
            }
            simState(data, 4);
            tabChange();
            // 内容套餐去除卡状态
            if (contentPackage == 1) {
                $(".cardState-normal").hide();
                $(".cardState-unused").hide();
                $(".cardState-stop").hide();
            }

            //机卡绑定
            //机卡绑定执行类型：0正常(不执行)，1告警，2停机
            if (data.bindExecuteType !== 0) {
                if (data.imei == "") { //有无IMEI号
                    //realStateByAli=3 代表阿里绑定
                    //bindingRule 机卡绑定规则：1绑定初始设备(一对一卡号绑定)，2绑定厂家设备(查库匹配)
                    if (data.realStateByAli == 3) {
                        cardBindLabel(data.commState, data.simState, data.imeiChangeLogList);
                    } else {
                        if (data.bindingRule == 1) {
                            if (data.ruleBindIMEI == null || data.ruleBindIMEI == "") {
                                $(".noBind").css("display", "inline-block");
                                $(".had-bind").css("display", "inline-block").text("机卡未绑定");
                            } else {
                                cardBindLabel(data.commState, data.simState, data.imeiChangeLogList);
                            }
                        } else {
                            //$(".noBind").css("display", "inline-block");
                            if (data.ruleBindIMEI == null || data.ruleBindIMEI == "") {
                                $(".noBind").css("display", "inline-block");
                                $(".had-bind").css("display", "inline-block").text("机卡未绑定");
                            } else {
                                cardBindLabel(data.commState, data.simState, data.imeiChangeLogList);
                            }
                        }
                    }
                } else {
                    cardBindLabel(data.commState, data.simState, data.imeiChangeLogList);
                }
            } else {
                // 机卡正常情况机卡绑定不显示
                $(".card-status").hide();
            }
        } else if (data.simFromType === 2) {
            $(".operator-logo").css('background-image', 'url(../images/terminal/dx_new_logo.png)')
            $(".btn-top").css("padding", "1px 15px");
            $("#cardNo").text(addBlank(data.iccid));
            $("#IMEInum").html("SIM号:<i>" + data.simNo + "</i>");
            simState(data, 4);
            tabChange();

        } else {
            tabChange();
            // 移动
            $(".operator-logo").css('background-image', 'url(../images/terminal/yd_new_logo.png)')
            $(".btn-top").css("padding", "1px 15px");
            $("#cardNo").text(addBlank(data.iccidFull));
            $("#IMEInum").html("SIM号:<i>" + data.simNo + "</i>");
            $(".f-flowDetail").hide();
            // $("#fqLink").attr("href", "fq_yd.html");
            simState(data, 2);
        }
        //计算lt-part1-simInfo距离上面的高度
        var h_simInfo = $(".lt-part1-simInfo").prop("offsetHeight");
        var w_window = $(window).width();
        if (w_window > 320) {
            $(".lt-part1-simInfo").css("padding-top", (54 - h_simInfo) / 2);
        } else {
            $(".lt-part1-simInfo").css("padding-top", (48 - h_simInfo) / 2);
        }

        $("#lastDays").text(data.surplusPeriod);
        w_h();
    }
    //机卡绑定标签公用方法
    function cardBindLabel(commState, simState, imeiChangeLogList) {
        $(".hasBind").css("display", "inline-block");
        $(".had-bind").css("display", "inline-block");
        //通信状态禁用
        if (commState == 0) {
            //停用
            if (simState == 4) {
                $(".hasBind").text("机卡分离").removeClass("c-green").addClass("c-red"); //机卡分离停机
                $(".had-bind").text("机卡分离").removeClass("green").addClass("red"); //机卡分离停机
                if (isExpire) {
                    $("#modea_selfActive").show();
                }
            } else { //其他状态
                $(".hasBind").text("机卡已分离").removeClass("c-green").addClass("c-blue");
                $(".had-bind").text("机卡分离").removeClass("green").addClass("red");
            }
            $(".renewalBtn").removeClass("button-primary btn-uncertification").attr("href", "javascript:void(0)").addClass('noRenewal');
            $("#ljts").attr("disabled","disabled").css({"color":"#666","border":"1px solid #e1e1e1","background-color":"#e1e1e1"});
            $("#ljts img").attr("src","../images/terminal/hj-d_close.gif");


            $(".renewalBtn").click(function() {
                $.showTip("机卡已分离，暂时不支持续费!");
            });
        } else { //通信状态正常  //ExecuteType  0正常   1告警  2停机
            if (imeiChangeLogList != null) {
                var imei_len = imeiChangeLogList.length;
                if (imei_len > 0 && imeiChangeLogList[0].executeType == 1) {
                    $(".hasBind").text("机卡分离").removeClass("c-green").addClass("c-yellow"); //机卡分离告警
                    $(".had-bind").text("机卡分离").removeClass("green").addClass("red"); //机卡分离停机
                    $(".hasBind-stop").text("已机卡分离告警，请尽快将流量卡插回原设备，否则停机！");
                    $(".lt-part1").css("padding-top", "0px")
                    $(".hasBind-stop").show();
                }
            }
        }
    }

    function simState(data, num) { //设置卡状态
        if (contentPackage == 1) {
            return;
        }
        if (data.simState === num) {

            $(".cardState-stop").css("display", "inline-block");
            $(".cardState-normal").hide();
            $(".cardState-unused").hide();

            //以下逻辑只针对联通卡
            if (dataInteractive.simFromType == '1') {
                if (!isExpire) { //到期
                    $(".cardState-stop").text('到期停机');
                } else { //未到期
                    if (commState == '1') { //开启通信

                        if (isLimitlessUsage == '0') { //无限流量套餐
                            $(".cardState-stop").text('流量用超停机');
                        } else { //无限流量套餐 变为正常
                            $(".cardState-stop").hide();
                            $(".cardState-normal").show();
                        }

                    } else { //禁用通信
                        $(".cardState-stop").text('机卡分离停机');
                    }
                }
            }
        } else {
            //已失效
            if (data.simStateSrc == "已失效") {
                $(".cardState-unused").text(data.simStateSrc);
                $(".cardState-unused").css("display", "inline-block");
                $(".cardState-stop").hide();
                $(".cardState-normal").hide();
                $(".renewalBtn").removeClass("button-primary btn-uncertification").attr("href", "javascript:void(0)").addClass('noRenewal');

                $(".renewalBtn").click(function() {
                    $.showTip("该卡已失效，不支持充值续费", 2, 2000);
                });

                $("#ljts").attr("disabled","disabled").css({"color":"#666","border":"1px solid #e1e1e1","background-color":"#e1e1e1"});
                $("#ljts img").attr("src","../images/terminal/hj-d_close.gif");
                //$("#ydTip").show();
                //$("#ydTip").text("该卡已失效，不支持充值续费！");
            } else if (data.simStateSrc == "已注销") {
                $(".cardState-unused").text(data.simStateSrc);
                $(".cardState-unused").css("display", "inline-block").text("已注销");
                $(".cardState-stop").hide();
                $(".cardState-normal").hide();
                $(".renewalBtn").removeClass("button-primary btn-uncertification").attr("href", "javascript:void(0)").addClass('noRenewal');
                $(".renewalBtn").click(function() {
                    $.showTip("该卡已注销，不支持充值续费");
                });
                $("#ljts").attr("disabled","disabled").css({"color":"#666","border":"1px solid #e1e1e1","background-color":"#e1e1e1"});
                $("#ljts img").attr("src","../images/terminal/hj-d_close.gif");
                //$("#ydTip").show();
                //$("#ydTip").text("该卡已注销，不支持充值续费！");
            } else {
                $(".cardState-normal").text(data.simStateSrc);
                $(".cardState-normal").css("display", "inline-block");
                $(".cardState-stop").hide();
                $(".cardState-unused").hide();
            }
        }
    }

    //电信卡状态
    function dxSimState(data) {
        if (data.overUsageStopService === 1 || data.simServiceState === 2) { //停机  达量断网是  或者断网状态断网
            $(".cardState-stop").css("display", "inline-block");
            $(".cardState-normal").hide();
            $(".cardState-unused").hide();
        } else if (data.overUsageStopService === 0 && data.simServiceState === 1) { //正常  达量断网否 并且断网状态正常
            $(".cardState-normal").css("display", "inline-block");
            $(".cardState-stop").hide();
            $(".cardState-unused").hide();
        }
    }

    function usageDataNew(data) {
        // 总量
        var allFlows = judgement(data.amountUsageData);
        // 剩余用量
        var lastFlows = judgement(data.surplusUsage, true);
        $(".allFlow").html(allFlows);
        $(".all-normal-meal .last-flow").html(lastFlows);
        $(".all-normal-meal .progress-wrap").attr("attr-allFlow", data.amountUsageData);
        $(".all-normal-meal .progress").attr("attr-allFlow", data.surplusUsage);

        var num = 0;

        // 用量百分比
        if (data.amountUsageData != '' && data.surplusUsage != '') {
            num = (data.surplusUsage / data.amountUsageData * 100).toFixed(0);
        }
        $(".all-normal-meal .progress").css("width", num + "%");

        //移动逻辑12待激活显示静默 联通电信 本月标识显示判断 清零套餐且不是日套餐
        if (isYD) {
            if (data.simState == 3) {
                $(".all-normal-meal .month").show();
                $(".all-normal-meal .month").html("静默");
                $(".all-normal-meal .end-time").html("");
            } else {
                if (data.isUsageReset === 1 && data.usageResetTimeType != 1) {
                    $(".all-normal-meal .month").html("本月");
                    $(".all-normal-meal .month").show();
                }

            }
        } else {
            if (data.isUsageReset === 1 && data.usageResetTimeType != 1) {
                $(".all-normal-meal .month").html("本月");
                $(".all-normal-meal .month").show();
            }

        }

        // simFromType 1联通 0 移动 2 电信
        if (data.simFromType === 1) {
            //到期时间
            // 可激活状态显示剩余天数 simstate 0 库存 1可测试 2可激活状态
            if (data.simState == 0 || data.simState == 1 || data.simState == 2) {
                $(".all-normal-meal .end-time").html('剩余：<span>' + data.surplusPeriod + '天</span>');
            } else {
                $(".all-normal-meal .end-time span").html(data.vexpireTime.split(' ')[0]);
            }
            // 联通卡打开补卡功能
            $(".supplement-card").show();

        } else if (data.simFromType === 2) {
            //到期时间
            // 可激活状态显示剩余天数 simstate 2可激活状态
            if (data.simState == 2) {
                $(".all-normal-meal .end-time").html('剩余：<span>' + data.surplusPeriod + '天</span>');
            } else {
                $(".all-normal-meal .end-time span").html(data.vexpireTime.split(' ')[0]);
            }



        } else {
            var curr = +new Date();
            var getDays = function(time) {
                if (time != '' && time) {
                    var dayNum = parseInt(((+new Date(time.replace(/-/g, '/'))) - curr) / 86400000);
                    if (dayNum >= 0) {
                        return dayNum;
                    } else {
                        return 0;
                    }
                } else {
                    return '--'
                }
            }

            //移动 到期时间
            // 可激活状态显示剩余天数 simstate 3 激活状态
            if (data.simState === 3) {
                function ydSurplus(num) {
                    var dw = ' <sub>天</sub>';
                    return (num < 30 ?
                        '<span style="color:red">' + num + '</span>' :
                        num) + dw;
                }
                $(".all-normal-meal .end-time").html('剩余：<span>' + ydSurplus(getDays(data.lastActiveTime)) + '</span>');

            } else {
                if(data.simFromType != 3){
                    $(".all-normal-meal .end-time span").html(data.vexpireTime.split(' ')[0]);
                }else{
                    $(".all-normal-meal .end-time span").html(data.vexpireTime);
                }
                

            }
            // 移动逻辑12加餐包
            if (isYD) {

                

                // 套餐详情切换
                var list = $('<div class="addPackageList"><ul></ul></div>');
                var isloaded = false;
                $('#nowPackageList').append(
                    $("#ljts")
                    .html('<img src="../images/terminal/addPackage.png" style="height: 16px;top: -1px;" class="dh" /> 加餐')
                    .css({
                        'position': 'absolute',
                        'left': '-5',
                        // 'top': '50px',
                        'padding': '5px 10px'
                    })
                    .show()
                    .on('click', function() {
                        if (isloaded) {
                            list.toggle();
                            return;
                        }
                        hanlderClick(data.simId)
                            .then(function(res) {
                                var result = res.result,
                                    packageType = '',
                                    monthLen = result.monthAddPackage.length,
                                    html = '',
                                    num = 0;
                                if (res.error !== 0 || !monthLen) {
                                    $.showTip("您暂时无法加餐！");
                                    return;
                                }
                                isloaded = true;
                                var len = monthLen > 2 ? 2 : monthLen;
                                var $ul = list.find('ul');
                                for (var i = 0; i < len; i++) {
                                    num++;
                                    packageType = result.monthAddPackage[i].packageId;
                                    html += '<li data-id="' + result.monthAddPackage[i].packageId + '">';
                                    html += '<p>' + result.monthAddPackage[i].packageName + '</p>';
                                    html += '<p>￥ ' + result.monthAddPackage[i].price + '</p>';
                                    html += '</li>';
                                }

                                if (num == 1) {
                                    window.location.href = "../WechatPay/Action/ConfirmPayInfo.aspx?simId=" + simId + "&package=" + packageType +
                                        "&appType=" + apptype + "&mchId=" + mchId + "&accessname=" + accessname;
                                    return;
                                }

                                $($ul).html(html).on('click', 'li', function() {
                                    window.location.href = "../WechatPay/Action/ConfirmPayInfo.aspx?simId=" + simId + "&package=" + $(this).data('id') +
                                        "&appType=" + apptype + "&mchId=" + mchId + "&accessname=" + accessname;
                                });

                                var $li = list.find('li');
                                if (num == 2) {
                                    $($ul).css('width', 34 * num + '%')
                                    $($li).css('width', 96 / num + '%');
                                } else if (num >= 3) {
                                    $($ul).css('width', 32 * num + '%')
                                    $($li).css('width', 96 / num + '%');
                                }
                                $li.show();
                                list
                                    .css({
                                        'position': 'relative',
                                        'top': '50px',
                                        'zIndex': '999'
                                    })
                                    .show();

                            })
                    })
                );
            }

            $('#nowPackageList').append(list);

            $(".leftFlowTxt").css('padding-right', '0px');
            $(".innerContainer .flowTips,.state").remove();

            if(data.apiCode == 14){
               $("#ljts").hide();
            }

            w_h();
        }
    }

    // 套餐详情
    function packageListNew(data) {
        //当前套餐
        $(".currentPackage").text(data.packageName);
        $(".currentPackageInfo").text(data.packageInfo);
        
        if (data.simFromType !== 1 ) {
            //非联通
            $(".swiper-container .status").hide();
            $(".diagnosis").hide();
            $(".use-amount").hide();
        }

        var getAddPackage = 0;
        if (data.isUsageReset === 1) { //清零1  不清零0
            $("#packageTitle").text("当月叠加");
            // 逻辑用户 
            // usageResetTimeType  1 日套餐
            if (data.usageResetTimeType != 1 && contentPackage == 1) {
                // 内容套餐当月叠加套餐tab按钮
                $('.this-month').show();
            }

            getAddPackage = 1;
        } else { //不清零0
            $("#packageTitle").text("已续费套餐");
            getAddPackage = 0;
        }

        $("#packageList_wrap .packageList").empty();
        var $li = null;
        // $(".noclear-package").hide();
        $("#nopackage_record").show();
        var zlist1 = [];
        var zlist2 = [];
        if (data.simFromType === 1) {
            // 联通
            $.map(data.renewalsPackageList, function(dd) {
                if (dd.isAddPackage === getAddPackage) {
                    if ($.inArray(dd.PackageType, zlist1) > -1) {
                        zlist2[$.inArray(dd.PackageType, zlist1)] += 1;
                    } else {
                        zlist1.push(dd.PackageType);
                        zlist2.push(1);
                    }
                }
            });
            var i = 0;

            // 非内容套餐
            if (contentPackage != 1) {
                $.map(zlist1, function(d) {
                    var name = zlist1[i];
                    var num = zlist2[i];
                    // $(".noclear-package").show();
                    $("#nopackage_record").hide();
                    $li = $("<li>");
                    var $span = $("<span>");
                    var $span2 = $("<span>");
                    $span.addClass("pull-right").html("[<i class='packageNum'>" + num + "</i>笔]");
                    $li.append($span);
                    $span2.text(name);
                    $li.append($span2);
                    $("#packageList_wrap .packageList").append($li);
                    ++i;
                });

            } else {
                if (zlist1.length <= 0) {
                    $(".no-history-list").show();
                } else {
                    var html = "";
                    $.map(zlist1, function(d) {
                        var name = zlist1[i];
                        var num = zlist2[i];
                        $(".no-history-list").hide();
                        html += '<div class="package-wrap"><span class="title">' + name + '</span><span class="num"><span>' + num + '</span>笔</span></div>';
                        ++i;
                    });
                    $('.add-package').append(html);
                }

            }


        } else if (data.simFromType === 0) {
            // 移动
            $.map(data.renewalsPackageList, function(dd) {
                if (dd.isAddPackage === getAddPackage) {
                    // $(".noclear-package").show();
                    $("#nopackage_record").hide();
                    $li = $("<li>");
                    if (dd.isToActiveOrder == 1) {
                        dd.PackageType = "(激活)" + dd.PackageType;
                    }
                    $li.text(dd.PackageType).append(
                        $("<span>").addClass("pull-right").text("到期日期" + dd.ExpireTime)
                    );

                    $("#packageList_wrap .packageList").append($li);
                }
            });
        } else if (data.simFromType === 2) {
            // 电信
            $.map(data.renewalsPackageList, function(dd) {
                if (dd.isAddPackage === getAddPackage) { //isAddPackage  0 非叠加包
                    if ($.inArray(dd.PackageType, zlist1) > -1) {
                        zlist2[$.inArray(dd.PackageType, zlist1)] += 1;
                    } else {
                        zlist1.push(dd.PackageType);
                        zlist2.push(1);
                    }
                }
            });
            var i = 0;
            $.map(zlist1, function(d) {
                var name = zlist1[i];
                var num = zlist2[i];
                // $(".noclear-package").show();
                $("#nopackage_record").hide();
                $li = $("<li>");
                var $span = $("<span>");
                var $span2 = $("<span>");
                $span.addClass("pull-right").html("[<i class='packageNum'>" + num + "</i>笔]");
                $li.append($span);
                $span2.text(name);
                $li.append($span2);
                $("#packageList_wrap .packageList").append($li);
                ++i;
            });

        }

        $("#renewalAmount").text("￥" + Math.round(data.renewalAmount));
        if ($('body').width() < 375) {
            $("#renewalAmount").hide();
        }

        w_h();
    }



    // 历史用量列表
    function historyMonthUsage(data) {
        if (!data) {
            $("#noAmountData").show();
            return;
        }
        $("#noAmountData").hide();
        $("#dvHistoryUsage").empty();
        var $div, $div0, $div1, $div2, $h5, $span, $percent, $max, $flow;
        var z = 0;
        var newData = data;

        // 排序
        if (newData) {
            for (var i = 0; i < newData.length - 1; i++) {
                for (var j = 1; j < newData.length - 1 - i; j++) {
                    if (newData[j].BillTime < newData[j + 1].BillTime) {
                        var temp = newData[j];
                        newData[j] = newData[j + 1];
                        newData[j + 1] = temp;
                    }
                }
            }
        }


        $.map(data, function(dd) {
            if (dd.UsageMB > z) {
                z = dd.UsageMB;
            }
        });
        $.map(newData, function(dd) {
            $max = z / 0.7;
            $flow = dd.UsageMB / $max * 100;
            if ($flow < 1 && $flow > 0) {
                $flow = 1;
            }
            $percent = $flow + "%";

            $h5 = $("<h5>");
            $span = $("<span>");
            $div = $("<div>");
            $div0 = $("<div>");
            $div1 = $("<div>");
            $div2 = $("<div>");

            $h5.text(dd.BillTime);
            $span.text(commafy(dd.UsageMB) + "MB").css("paddingLeft", "5px");
            $div2.addClass("progress-bar").attr("role", "progressbar").attr("aria-valuenow", "100").attr("aria-valuemin", "0").attr("aria-valuemax", "100").css("width", "100%");
            $div1.addClass("progress").css({
                "float": 'left',
                "width": $percent
            }).append($div2);
            $div0.css({
                overflow: "auto",
                marginBottom: "5px"
            }).append($div1).append($span)
            $div.addClass("progressList").append($h5).append($div0);

            $("#dvHistoryUsage").append($div);
        });
    }
    //机卡已绑定弹出框
    function hasBindModalInfo(realStateByAli, aliBindingIMEI, imei, bindingRule, ruleBindIMEI, time, isRuleBindIMEI) {
        //realStateByAli=3 代表阿里认证
        //aliBindingIMEI: 阿里绑定IMEI号
        //bindingRule 机卡绑定规则：1绑定初始设备(一对一卡号绑定)，2绑定厂家设备(查库匹配)
        //ruleBindIMEI: 一对一绑定的IMEI
        //当前设备IMEI号
        //变更时间
        if (time == null || time == "" || time == undefined) {
            $(".modal-changeTime").hide();
        } else {
            if (time.length == 0) {
                $(".modal-changeTime").hide();
            } else {
                $("#modal_changeTime").text(time[0].createTime);
            }
        }
        //当前设备IMEI号
        if (imei == null || imei == "" || imei == undefined) {
            $(".modal-currentIMEI").hide();
        } else {
            $("#modal_currentIMEI").text(addBlank(addStar(imei, 14).substring(0, 15)));
        }

        if (realStateByAli == 3) { //阿里绑定
            //当前设备IMEI号前14位是否和绑定IMEI号前14位相同
            if (imei.substring(0, 14) == aliBindingIMEI.substring(0, 14)) {
                $("#modal_bindIMEI").text(addBlank(addStar(aliBindingIMEI, 14).substring(0, 15)));
                $("#modal_cardBindTips").hide();
                $(".modal-currentIMEI").hide();
                $(".modal-changeTime").hide();
                $(".modal-sameimei-tips").show();
                $("#IMEInum").html("当前IMEI:<i>" + addStar(aliBindingIMEI, 14).substring(0, 15) + "</i>");
            } else {
                if (aliBindingIMEI == "") {
                    $(".modal-bindIMEI").hide();
                } else {
                    $("#modal_bindIMEI").text(addBlank(addStar(aliBindingIMEI, 14).substring(0, 15)));
                    $("#IMEInum").html("绑定IMEI:<i>" + addStar(aliBindingIMEI, 14).substring(0, 15) + "</i>");
                    if (imei) {
                        $("#IMEInum").html("当前IMEI:<i>" + addStar(imei, 14).substring(0, 15) + "</i>");
                    }
                }
                modal_cardBindTips(aliBindingIMEI);
            }
        } else {
            if (bindingRule == 1) { //机卡设置一对一
                if (ruleBindIMEI == "" || ruleBindIMEI == null || ruleBindIMEI == undefined) {
                    $(".modal-bindIMEI").hide();
                    modal_cardBindTips(ruleBindIMEI);
                } else {
                    //当前设备IMEI号前14位是否和绑定IMEI号前14位相同
                    if (imei.substring(0, 14) == ruleBindIMEI.substring(0, 14)) {
                        $("#modal_bindIMEI").text(addBlank(addStar(ruleBindIMEI, 14).substring(0, 15)));
                        $("#modal_cardBindTips").hide();
                        $(".modal-currentIMEI").hide();
                        $(".modal-changeTime").hide();
                        $(".modal-sameimei-tips").show();
                    } else {
                        $("#modal_bindIMEI").text(addBlank(addStar(ruleBindIMEI, 14).substring(0, 15)));
                        modal_cardBindTips(ruleBindIMEI);
                    }
                    $("#IMEInum").html("绑定IMEI:<i>" + addStar(ruleBindIMEI, 14).substring(0, 15) + "</i>");
                    if (imei) {
                        $("#IMEInum").html("当前IMEI:<i>" + addStar(imei, 14).substring(0, 15) + "</i>");
                    }
                }
            } else {
                if (ruleBindIMEI == "" || ruleBindIMEI == null || ruleBindIMEI == undefined) {
                    if (isRuleBindIMEI == 1) { // isRuleBindIMEI   1 合法   0 不合法
                        $("#modal_bindIMEI").text(addBlank(addStar(imei, 14).substring(0, 15)));
                    } else {
                        $(".modal-bindIMEI").hide();
                    }
                    modal_cardBindTips(imei);
                } else {
                    //当前设备IMEI号前14位是否和绑定IMEI号前14位相同
                    if (imei.substring(0, 14) == ruleBindIMEI.substring(0, 14)) {
                        $("#modal_bindIMEI").text(addBlank(addStar(imei, 14).substring(0, 15)));
                        $("#modal_cardBindTips").hide();
                        $(".modal-currentIMEI").hide();
                        $(".modal-changeTime").hide();
                        $(".modal-sameimei-tips").show();
                    } else {
                        $("#modal_bindIMEI").text(addBlank(addStar(ruleBindIMEI, 14).substring(0, 15)));
                        modal_cardBindTips(ruleBindIMEI);
                    }
                    $("#IMEInum").html("绑定IMEI:<i>" + addStar(ruleBindIMEI, 14).substring(0, 15) + "</i>");
                    if (imei) {
                        $("#IMEInum").html("当前IMEI:<i>" + addStar(imei, 14).substring(0, 15) + "</i>");
                    }
                }
            }
        }
    }
    //机卡已绑定弹出框 提示语
    function modal_cardBindTips(bindIMEI) {
        var text = ""
        if (bindIMEI == null || bindIMEI == "" || bindIMEI == undefined) {
            text = "*请尽快将流量卡插回原始设备！"
            // $("#modal_cardBindTips").hide();
        } else {
            if ($(".hasBind").text() == "机卡已绑定") {
                //text = "*请尽快将流量卡插回绑定设备！"
            } else if ($(".hasBind").text() == "机卡分离告警") {
                text = "*目前因机卡分离导致告警，请尽快将流量卡插回设备！"
            } else if ($(".hasBind").text() == "机卡分离停机") {
                if (isExpire === true) {
                    text = '*目前因机卡分离导致停机！请插回绑定设备,然后点击"激活"';
                } else {
                    text = '*目前因机卡分离导致停机！请插回绑定设备,联系客服恢复使用'
                }
            } else if ($(".hasBind").text() == "机卡已分离") {
                text = "*目前已机卡分离，请尽快将流量卡插回绑定设备！";
                $("#modal_cardBindTips").css("color", "#2283e5");
            }
        }
        $("#modal_cardBindTips").text(text);
    }
    //手淘弹出框
    function TaoRealNameModal(name, tel, id, time) {
        var reg = /^1\d{10}$/;
        $("#m_tao_name").text(addStar(name, 1));
        reg.test(tel) && $("#m_tao_tel").text(tel.substring(0, 3) + "****" + tel.slice(-4));
        $("#m_tao_id").text(id.substring(0, 4) + "******" + id.slice(-4));
        $("#m_tao_time").text(time);
    }

}());

function tip(str) {
    $(".successBind").text(str).show();
    setTimeout(function() {
        $(".successBind").hide();
    }, 1500)
}

function bindCard(userId, simId) {
    var data = {
        "userId": userId,
        "simId": simId
    }
    $.ajax({
        type: "post",
        url: "../../api/BindUserSimInfo",
        data: JSON.stringify(data),
        dataType: "json",
        contentType: "application/json",
        success: function(res) {
            if (res.error === 0) {
                localStorage.setItem("success", "true");
                location.href = location.href;
            } else {
                localStorage.setItem("success", "false");
            }
        },
        error: function() {
            console.log("网络异常");
        }
    });
}
if (localStorage.getItem("success") == "true") {
    tip("绑定成功")
    localStorage.removeItem("success")
} else if (localStorage.getItem("success") == "false") {
    tip("你已绑定该卡")
    localStorage.removeItem("success")
}
var userName,
    idCard,
    trueName,
    realStateByAli,
    activeByRealName;

// 无限流量处理改版后
function isLimitlessNew(data) {
    $(".all-normal-meal").hide();
    $(".Infinite-meal").show();

    // 当月叠加套餐
    $('#tabs_package').hide();
    // 当前套餐详情，下次改
    $('#LimitlessInfo').show();

    // 本月已用
    var monthUsage = judgement(data.monthUsageData, true);
    if (Math.ceil(data.monthUsageData / 1024) > 22) {
        monthUsage = judgement(data.monthUsageData, true, '', 1000);
    }
    $("#surplusFlowNew").html(monthUsage);
    w_h();

}


//通过状态值来改变 img  以及 是否为在线  text  ------------
function stateChange(state,simFromType) {
    // 内容套餐 卡详情改版后为联通套餐
    if (simFromType ==1) {
        if (state == 2) {
            $('.equipment-status').css('display', 'block');
            $('.equipment-status img').attr('src', '../images/terminal/offLine.png');
            $('.equipment-status .line-status').text('离线').css('color', '#aaa');
        } else if (state == 1) {
            $('.equipment-status').css('display', 'block');
            $('.equipment-status img').attr('src', '../images/terminal/onLine.png');
            $('.equipment-status .line-status').text('在线').css('color', '#4dcb7d');
        } else if (state == 3) {
            $('.equipment-status').css('display', 'block');
            $('.equipment-status img').attr('src', '../images/terminal/fq-icon.png');
            $('.equipment-status .line-status').text('未知').css('color', '#4dcb7d');
        } else {
            $('.equipment-status').css('display', 'none');
        }


    } else {
        if (state == 2) {
            $('.state').css('display', 'block');
            $('.state img').attr('src', '../images/terminal/offLine.png');
            $('.state span').text('离线').css('color', '#aaa');
        } else if (state == 1) {
            $('.state').css('display', 'block');
            $('.state img').attr('src', '../images/terminal/onLine.png');
            $('.state span').text('在线').css('color', '#4dcb7d');
        } else if (state == 3) {
            //$('.state').css('display', 'none');
            $('.state').css('display', 'block');
            $('.state img').attr('src', '../images/terminal/fq-icon.png');
            $('.state span').text('未知').css('color', '#4dcb7d');
        } else {
            $('.state').css('display', 'none');
        }

    }

}

function uncertification() {
    $(".renewalBtn").removeClass("button-primary btn-uncertification").attr("href", "javascript:void(0)").addClass('uncertificationTip');
}

function hanlderClick(simId) {
    return $.ajax({
        url: "../../api/RenewalsPackageList/" + simId,
        dataType: "json",
        type: "GET",
        headers: {
            "Authorization": "4H5F24ZAHU8MTFI6U66T2E8UTDHC8MI5"
        },
        error: function() {
            console.log("网络异常");
        }
    })
}

function initData(simId, refreshFlag, projectType) {
    if (!simId)
        return;
    shared.loadCfm.open()
    var userId = $("#hid_userId").val();
    $.ajax({
        method: "GET",
        url: "../../api/GetTerminalCompreForH5/" + simId + "?userId=" + userId,
        dataType: "json",
        headers: {
            Authorization: "379C68321D4C3FD90E139A2EB982189E"
        },
        success: function(rs) {
            if (!rs || rs.error !== 0 || !rs.result) {
                alert('查询失败');
                //location.href = searchURL;
                shared.loadCfm.close();
                return;

            }

            keepUsageResetTimeType = rs.result.usageResetTimeType;
            getNetWorkSpeed = rs.result.netWorkSpeed;
            getHigUsageToPeriod = rs.result.higUsageToPeriod;
            getUsageToPeriod = rs.result.usageToPeriod;
            newTotalMonthUsage = rs.result.totalMonthUsage;
            contentPackage = rs.result.controlType;
            simstate = rs.result.simState;

            if (rs.result.memberInfo.iconImage && rs.result.memberInfo.iconImage !== '' && rs.result.memberInfo.iconImage !== undefined) {
                $(".use-img").css("background-image", "url(" + rs.result.memberInfo.iconImage + ")");
            } else {
                $(".use-img").css("background-image", "url(../images/member/no_headImg.png)");
            }

            if (contentPackage == 1) {
                $('.f-znzd').hide();
                $('.f-flowDetail').css('border', 'none');
                $('.normal-container').hide();
                $('.meal-container').show();
                // $('#fqLink').hide();
                $('body').css('font-size', '15px !important');
                $('body').css('background', '#f4f4f4');
                $('html').css('background', '#f4f4f4');
                $('.container').css('background', '#f4f4f4');
                $('footer').css('background', '#f4f4f4');
            }

            var result = rs.result,
                iccid = result.iccid;
            iccidFull = result.iccidFull;
            currentTime = result.currentTime.split(' ')[0];
            vexpireTime = result.vexpireTime;
            commState = result.commState;
            isLimitlessUsage = result.isLimitlessUsage;
            isUsageReset = result.isUsageReset;
            historyMonthUsageList = result.historyMonthUsageList;
            firstActiveTime = result.firstActiveTime;
            userName = result.memberInfo.userName;
            idCard = strDecrypt(result.memberInfo.idCard);
            trueName = strDecrypt(result.memberInfo.trueName);
            realStateByAli = result.realStateByAli;
            realNameLevel = result.realNameLevel;
            activeByRealName = result.activeByRealName;
            isAuthIdentity = result.isAuthIdentity;
            var newVexpireTime = result.vexpireTime.split(' ')[0];
            isExpire = +new Date(new Date(newVexpireTime.split('-').join('/')).setDate(new Date(newVexpireTime.split('-').join('/')).getDate() + 1)) > +new Date(currentTime.split('-').join('/'));
            istExpireStop = result.isUsageReset === 1 && !isExpire && result.simState != 2;
            shared.loadCfm.close()
            console.log(new Date(new Date(newVexpireTime.split('-').join('/')).setDate(new Date(newVexpireTime.split('-').join('/')).getDate() + 1)))



            realStateByAli = result.realStateByAli;
            if(contentPackage == 1){
                dxModalClose(result.simFromType,$("#meal-history"))
            }else{
                dxModalClose(result.simFromType,$("#history"))

            }

            if (result.apiCode) {
                var apiCode = result.apiCode;
                //将apiCode存到localstorage
                localStorage.setItem('apiCode', apiCode);
            }

            // 电信暂时查不到连接记录，那下面的本月用量就先隐藏
            if (result.simFromType == 2) {
                $('.f-flowDetail').hide();
            }

            //0移动 1联通 2电信  simFromType:sim卡源类型
            if (result.simFromType == 1) {
                //realStateByAli：手淘实名，3已实名，2审核中，-1实名失败，其他未实名
                //realNameLevel：实名等级，1麦谷实名  2阿里实名  3手机实名-麦谷  4手机实名-阿里  5企业实名
                //realIndustry：实名企业简称 为空则未企业实名 不为空则企业实名
                //isAuthIdentity：true则已个人实名，false则未个人实名
                //realNameStatus：实名状态 3已实名，2待审核，其他0未实名
                if (contentPackage == 1) {
                    var appListLength = result.appList.length;
                    var listData = result.appList;
                    var html = '';
                    if (appListLength > 0) {
                        $.each(listData, function(index, item) {
                            var amountUsageData = (item.amountUsageData / 1024) >= 1024 ? (item.amountUsageData / 1024 / 1024).toFixed(2) + 'GB' : (item.amountUsageData / 1024).toFixed(2) + 'MB';
                            var flowLeftValue = (item.flowLeftValue / 1024) >= 1024 ? (item.flowLeftValue / 1024 / 1024).toFixed(2) + 'GB' : (item.flowLeftValue / 1024).toFixed(2) + 'MB';
                            var thisMonth = '';
                            var num = 0;
                            var now = new Date();
                            var date = now.toLocaleDateString();
                            var my_hours = now.getHours();
                            var my_minutes = now.getMinutes();
                            var my_seconds = now.getSeconds();
                            var nowTime = date + ' ' + my_hours + ':' + my_minutes + ':' + my_seconds;
                            var d1 = Date.parse(item.expireTime.replace(/-/g, "/"));
                            var d2 = Date.parse(nowTime.replace(/-/g, "/"));
                            var time = '';

                            if (item.expireTime !== '') {
                                if (d2 - d1) {
                                    // 未到期
                                    time = '到期：' + item.expireTime.substring(0, 11);

                                } else {
                                    time = '服务到期';
                                }
                            } else {
                                time = '剩余' + item.surplusPeriod + '天';
                            }


                            if (item.amountUsageData != '' && item.flowLeftValue != '') {
                                num = (item.flowLeftValue / item.amountUsageData * 100).toFixed(0);
                            }

                            if (result.isUsageReset === 1 && result.usageResetTimeType != 1) {
                                thisMonth = '<div class="month" style="display:block">本月</div>';
                            } else {
                                thisMonth = '';
                            }
                            html += '<div class="meal-item"><h4 class="title">' + item.appName + '</h4><span class="total-flow">(总量' + amountUsageData + ')</span><div class="progress-wrap"><span style="width:' + num + '%" class="progress"></span></div><div class="item-detail"><span class="last-flows">剩余<span>' + flowLeftValue + '</span></span><span class="end-time">' + time + '</span></div>' + thisMonth + '</div>'
                        })

                        $('.meal-use').append(html);
                    }


                }

                if (result.realStateByAli == 3) {
                    $(".certification").css("display", "inline-block");
                    $(".certification").text("手淘实名");
                    $("#TaoRealName .modal-title").text("手淘实名认证详情");
                    $(".m-realName-img img").attr("src", "../images/terminal/taoAuthen.png")
                } else {
                    if (result.realNameLevel != 5) {
                        //等级个人实名或未设置
                        if (result.isAuthIdentity) {
                            $(".certification").css("display", "inline-block");
                            $(".certification").text("个人实名");
                        } else {
                            if (result.sourceType === 1 || result.sourceType === 2) {
                                $(".uncertification").css("display", "inline-block");
                            } else {
                                $(".uncertification").css("display", "inline-block");
                            }
                            if (!result.noRealNameRenewals) { //未实名无法续费
                                uncertification();
                            }
                            if (result.realNameStatus == 2) {
                                $(".uncertification").text('实名审核中');
                            }
                        }
                    } else {
                        //等级企业实名
                        if (result.isAuthIdentity) {
                            $(".certification").css("display", "inline-block");
                            $(".certification").text("个人实名");
                        } else {
                            if (!result.realIndustry && result.sourceType !== 1 && result.sourceType !== 2) {
                                $(".uncertification") //假企业实名
                                    .css("display", "inline-block")
                                    .on('click', function() {})
                            } else {

                                $(".companyCertification").css("display", "inline-block"); //真企业实名
                            }
                            if (!result.noRealNameRenewals) { //未实名无法续费
                                uncertification();
                            }
                            if (result.realNameStatus == 2) $(".uncertification").text('实名审核中');
                        }
                    }
                }

                if (contentPackage == 1) {
                    // 套餐名称
                    $('.show-active h3.title').text(result.packageName);
                    $('.show-active .meal-descript').text(result.packageInfo);
                }

                // 实名显示
                if (result.realStateByAli == 3) {
                    $('.realname-link-tao').show().text('手淘实名 >');
                    $("#TaoRealName .modal-title").text("手淘实名认证详情");
                    $(".m-realName-img img").attr("src", "../images/terminal/taoAuthen.png")
                } else {
                    if (result.realNameLevel != 5) {
                        if (result.isAuthIdentity) {
                            $('.realname-link-tao').show().text('个人实名 >');
                        } else {
                            if (result.sourceType === 1 || result.sourceType === 2) {
                                $('.realname-link-false').show().text('企业实名,去完善信息 >');

                            } else {
                                $('.realname-link-false').show().text('企业实名,去完善信息 >');
                            }

                            if (!result.noRealNameRenewals) { //未实名无法续费
                                uncertification();
                            }
                            if (result.realNameStatus == 2) {
                                $('.realname-link-false').show().text('实名审核中 >');
                            }
                        }

                    } else {
                        if (result.isAuthIdentity) {
                            $('.realname-link-tao').show().text('个人实名 >');
                        } else {
                            if (!result.realIndustry && result.sourceType !== 1 && result.sourceType !== 2) {
                                $('.realname-link-false').show().text('企业实名,去完善信息 >'); //假企业实名

                            } else {
                                $('.realname-link-real').show().text('企业实名');; //真企业实名
                            }

                            if (!result.noRealNameRenewals) { //未实名无法续费
                                uncertification();
                            }
                            if (result.realNameStatus == 2) {
                                $(".realname-link-false").show().text('实名审核中 >');
                            }


                        }

                    }
                }





            }

            //0移动 
            if (result.simFromType == 0) {
                $('.f-znzd').hide();
                $('.f-renewalRecord').css("border-right","none");
                //realStateByAli：手淘实名，3已实名，2审核中，-1实名失败，其他未实名
                //realNameLevel：实名等级，1企业实名，2个人实名 0则未设置则归为个人实名等级
                //realIndustry：实名企业简称 为空则未企业实名 不为空则企业实名
                //isAuthIdentity：true则已个人实名，false则未个人实名
                
                if (result.realStateByAli == 3) {
                    $(".certification").css("display", "inline-block");
                    $(".certification").text("手淘实名");
                    $("#TaoRealName .modal-title").text("手淘实名认证详情");
                    $(".m-realName-img img").attr("src", "../images/terminal/taoAuthen.png")
                } else {
                    if (result.realNameLevel != 5) {
                        //等级个人实名或未设置
                        if (result.isAuthIdentity) {
                            $(".certification").css("display", "inline-block");
                            $(".certification").text("个人实名");
                        } else {
                            if (result.sourceType === 1 || result.sourceType === 2) {
                                $(".companyCertification")
                                    .css("display", "inline-block")
                                    .on('click', function() {
                                        $("#uncertification").trigger("click");
                                    })
                                $("#uncertification").trigger("click");
                            } else {
                                $(".uncertification").css("display", "inline-block");
                            }
                            if (!result.noRealNameRenewals) { //未实名无法续费
                                uncertification();
                            }
                        }
                    } else {
                        //等级企业实名
                        if (result.isAuthIdentity) {
                            $(".certification").css("display", "inline-block");
                            $(".certification").text("个人实名");
                        } else {
                            if (!result.realIndustry && result.sourceType !== 1 && result.sourceType !== 2) {
                                $(".uncertification")
                                    .css("display", "inline-block")
                                    .on('click', function() {
                                        $("#uncertification").trigger("click");
                                    })
                            } else {
                                if (result.sourceType === 1 && result.sourceType === 2) {
                                    $("#uncertification").trigger("click");
                                }
                                $(".companyCertification").css("display", "inline-block"); //企业实名
                            }
                            if (!result.noRealNameRenewals) { //未实名无法续费
                                uncertification();
                            }
                        }
                    }
                }
            }

            //0移动 1联通 2电信
            if (result.simFromType == 2) { //电信只有麦谷实名
                //realStateByAli：手淘实名，3已实名，2审核中，-1实名失败，其他未实名
                //realNameLevel：实名等级，1企业实名，2个人实名 0则未设置则归为个人实名等级
                //realIndustry：实名企业简称 为空则未企业实名 不为空则企业实名
                //isAuthIdentity：true则已个人实名，false则未个人实名
                if (result.realStateByAli == 3) { //已实名
                    $(".certification").css("display", "inline-block");
                    $(".certification").text("手淘实名");
                    $("#TaoRealName .modal-title").text("手淘实名认证详情");
                    $(".m-realName-img img").attr("src", "../images/terminal/taoAuthen.png")
                } else {
                    if (result.realNameLevel != 5) {
                        //等级个人实名或未设置
                        if (result.isAuthIdentity) {
                            $(".certification").css("display", "inline-block");
                            $(".certification").text("个人实名");
                        } else {
                            if (result.sourceType === 1 || result.sourceType === 2) {
                                $(".companyCertification")
                                    .css("display", "inline-block")
                                    .on('click', function() {
                                        $("#uncertification").trigger("click");
                                    })
                                $("#uncertification").trigger("click");
                            } else {
                                $(".uncertification").css("display", "inline-block");
                            }
                            if (!result.noRealNameRenewals) { //未实名无法续费
                                uncertification();
                            }
                        }
                    } else {
                        //等级企业实名
                        if (result.isAuthIdentity) {
                            $(".certification").css("display", "inline-block");
                            $(".certification").text("个人实名");
                        } else {
                            if (!result.realIndustry && result.sourceType !== 1 && result.sourceType !== 2) {
                                $(".uncertification")
                                    .css("display", "inline-block")
                                    .on('click', function() {
                                        $("#uncertification").trigger("click");
                                    })
                            } else {
                                if (result.sourceType === 1 && result.sourceType === 2) {
                                    $("#uncertification").trigger("click");
                                }
                                $(".companyCertification").css("display", "inline-block"); //企业实名
                            }
                            if (!result.noRealNameRenewals) { //未实名无法续费
                                uncertification();
                            }
                        }
                    }
                }
            }

            var data = {
                    iccid: iccidFull,
                    simId: result.simId,
                    amountUsageData: result.amountUsageData,
                    totalMonthUsage: result.totalMonthUsage,
                    isUsageReset: result.isUsageReset,
                    apiAccount: result.apiAccount,
                    contractType: result.contractType,
                    isLimitlessUsage: result.isLimitlessUsage,
                    resetTimeType: result.usageResetTimeType,

                },

                changeData = {
                    amountUsageData: result.amountUsageData,
                    usageToPeriod: result.usageToPeriod,
                    higUsageToPeriod: result.higUsageToPeriod,
                }

            historyOption = {
                simFromType: result.simFromType,
                simId: result.simId,
                monthUsage: result.monthUsageData,
                dayUsage: result.dayUsageData,
                simNo: result.simNo,
                doneUsage: result.doneUsage,
                apiCode: result.apiCode

            }
            dxData = {
                simId: result.simId
            }
            dataInteractive.header(result);

            // 分离计费页面分离节点

            dataInteractive.usageDataNew(result);
            dataInteractive.packageListNew(result);
            // dataInteractive.packageList(result);
            dataInteractive.hasBindModalInfo(result.realStateByAli, result.aliBindingIMEI, result.imei, result.bindingRule, result.ruleBindIMEI, result.imeiChangeLogList, result.isRuleBindIMEI);
            if (result.realNameInfo != null) {
                if (result.realNameInfo.idCard && result.realNameInfo.idCard !== "" && result.realNameInfo.idCard !== undefined) {
                    dataInteractive.TaoRealNameModal(strDecrypt(result.realNameInfo.trueName), result.realNameInfo.userName, strDecrypt(result.realNameInfo.idCard), result.realNameInfo.authTime);
                } else {
                    dataInteractive.TaoRealNameModal(strDecrypt(result.realNameInfo.trueName), result.realNameInfo.userName, result.realNameInfo.idCard, result.realNameInfo.authTime);
                }

            }
            if (result.simFromType == 1 && refreshFlag) { //联通
                setTimeout(function() {
                    updateTerminalState(data, changeData, result.isLimitlessUsage, result.netWorkSpeed);
                }, 300);
            }
            if (result.simFromType == 2 && refreshFlag) { //电信同步状态
                setTimeout(function() {
                    updateDXTerminalState(dxData);
                }, 300);
            }
            //离线、在线状态
            stateChange(result.gprsState,result.simFromType);

            userState(userId, simId, iccidFull, result, projectType);

            if (result.isLimitlessUsage) {
                // 无限流量卡调用
                isLimitlessNew(result);

                // $('#LimitlessInfo > p').eq(0).find('span').text(result.packageName);

                var a = result.netWorkSpeed;
                $('#flowType').text(a == '1' ? '高速' : (a == '2' ? '中速' : '低速')).css('color', a == '1' ? '#01c05f' : (a == '2' ? '#ffb21d' : '#fc6334'));

                $('#tipLimitless').text(a == '1' ? '看蓝光视频、玩吃鸡游戏' : (a == '2' ? '看小视频、浏览网页、刷朋友圈' : '网速较慢，建议您续提速包哦'))

                $('#flowTypeIcon').attr('src', a == '1' ? '../images/terminal/js.png' : (a == '2' ? '../images/terminal/zs.png' : '../images/terminal/ds.png'));
                $('#limitlessMonth').text(changeCloseTime(result.firstActiveTime, result.currentTime) + 1);
                $('#limitlessTime').text(result.vexpireTime.split(' ')[0]);
                // 无限流量到期日期
                // 可激活状态显示剩余天数 simstate 2可激活状态
                if (result.simState == 2) {
                    $(".end-Infinite-time").html('剩余：<span>' + result.surplusPeriod + '天</span>');
                } else {
                    $(".end-Infinite-time span").text(result.vexpireTime.split(' ')[0]);
                }



                if (!isExpire && result.simState != 2) { //到期卡
                    $('#LimitlessInfo .card-stop').html('流量卡' + '<span style="color:red;">已停机</span>' + '，请充值续费！');
                    // $('#LimitlessInfo > p').eq(2).hide();
                    $('.layer-speed > ul').addClass('marginTop15');
                    $('#limitlessBox > ul').addClass('marginTop15');
                }

                var timer = null;
                $('#ljts').on('click', function() {

                    var packageType = 1069;
                    hanlderClick(simId)
                        .then(function(res) {
                            var result = res.result,
                                packageType = '';
                            if (res.error == 0) {
                                var html = '',
                                    num = 0;
                                var monthLen = result.monthAddPackage.length
                                // var len = monthLen > 2 ? 2 : monthLen;

                                for (var i = 0; i < monthLen; i++) {
                                    if (result.monthAddPackage[i].isSpeedUpPack) {
                                        num++; {
                                            packageType = result.monthAddPackage[i].packageId;
                                            html += '<li data-id="' + result.monthAddPackage[i].packageId + '">';
                                            html += '<p class="left">' + result.monthAddPackage[i].packageName + '</p>';
                                            html += '<p class="right">￥ ' + result.monthAddPackage[i].price + '</p>';
                                            html += '</li>';
                                        }
                                    }
                                }

                                if (num == 0) {
                                    // clearInterval(timer);
                                    $.showTip("您暂时无法续费提速包！");
                                    return;
                                }

                                if (num == 1) {
                                    window.location.href = "../WechatPay/Action/SimRecommendPackagePay.aspx?simId=" + simId + "&package=" + packageType +
                                        "&appType=" + apptype + "&mchId=" + mchId + "&accessname=" + accessname;
                                    return;
                                }

                                $('.layer-speed ul').html(html);

                                layer.open({
                                    type: 1,
                                    style: 'height:50%;overflow:auto;width:80%',
                                    content: $('.speed-wrap').html()
                                });

                                $('.layer-speed ul>li').click(function() {

                                    window.location.href = "../WechatPay/Action/SimRecommendPackagePay.aspx?simId=" + simId + "&package=" + $(this).data('id') +
                                        "&appType=" + apptype + "&mchId=" + mchId + "&accessname=" + accessname;

                                })

                            }
                        })
                })
            }

        }
    });
}

$("#self_active").on("click", function() {
    var self = this;
    $(this).text('执行中...').css({
        'background-color': '#ddd',
        'color': '#fff'
    }).attr("disabled", "disabled");
    $.ajax({
        type: "POST",
        url: "../../api/SelfHelpActivated",
        dataType: "JSON",
        data: JSON.stringify({
            simId: $("#hid_simId").val()
        }),
        contentType: "application/json",
        headers: {
            "Authorization": "4H5F24ZAHU8MTFI6U66T2E8UTDHC8MI5"
        },
        success: function(res) {
            if (res.error !== 0) {
                alert("激活失败");
                $(this).text('激活').removeAttr("disabled");
                return;
            }
            $(this).text('已激活');
            $("#bindShow").hide();
            $("#self_active_success").show();
        },
        error: function() {
            $(this).text('激活').css({
                'background-color': '#ddd',
                'color': '#fff'
            }).removeAttr("disabled");
            alert('网络异常');
        }
    });
})

function addBlank(src) {
    var len = src.length,
        arr,
        n,
        srcObj = src.split("");
    switch (len) {
        case 11:
            arr = [3, 7];
            break;
        case 13:
            arr = [5, 9];
            break;
        case 19:
            arr = [4, 8, 12, 16];
            break;
        default:
            arr = [4, 8, 12, 16];
    };
    n = arr.length;
    for (var i = 0, length = n; i < length; i++) {
        srcObj.splice(arr[i] + i, 0, " ");
    }
    return srcObj.join("");
}
//后面多少位用*代替
function addStar(src, j) {
    var len = src.length;
    var arr;
    var n;
    var srcObj;
    if (src !== "" && src !== "undefined" && src.length > 0) {
        srcObj = src.split("");
    }
    if (len > j) {
        for (var i = j; i < len; i++) {
            srcObj.splice(i, 1, "*");
        }
        return srcObj.join("");
    } else {
        return src;
    }
}



function userState(userId, simId, iccid, data, projectType) {
    var verUrl = from_app === 'h5' ? "../Terminal/AuthRealName.aspx" : "../member/Verified.aspx",
        verData = from_app === 'h5' ?
        "?num=" + iccid + "&num_type=iccid&imei=" + imei :
        "?userId=" + userId + "&simId=" + simId + "&iccid=" + iccid + "&fromapp=" + from_app + "&onlyVer=true" +"&simstate=" +simstate;
    $.ajax({
        url: "../../api/CheckMemberRealBind?userId=" + userId + "&simId=" + simId,
        type: "GET",
        dataType: "json",
        success: function(res) {
            var result = res.result,
                iccid = $.trim($("#hid_iccid").val()),
                noJD = projectType.toUpperCase() !== 'JD',
                addAnimation = function(noJD) {
                    noJD && $("#uncertification").addClass('authentication-animation');
                }
            if (res.error == 0) {
                if (data.activePgStatus == 1) { //0不送  
                    var activePgStatusFlag = true;
                } else {
                    var activePgStatusFlag = false;
                }

                isLogin = res.result.isLogin;

                if (cardType) {
                    //实名验证
                    if (realStateByAli !== 3) { //没有阿里实名
                        if (activePgStatusFlag) {
                            $(".modal-body div.modal-body-text").html("当前流量卡未个人实名,请完成实名认证,实名完成后将赠送套餐:" + "<b>" + data.activePgName + "</b>");
                        } else {
                            $(".modal-body div.modal-body-text").html("当前流量卡未个人实名,请完成实名认证");
                        }
                        $(".btn-right").text("实名认证").parent().removeClass("col-xs-6").addClass("col-xs-12");
                        $(".btn-left").remove();
                        if (!$("#uncertification").is(":hidden") && activeByRealName === 1)
                            $("#uncertification").trigger("click");
                        else
                            addAnimation();

                        if ((mobile && phoneReg.test(mobile) && (realNameLevel == 3 || realNameLevel == 4)) || (realNameLevel != 3 && realNameLevel != 4)) { //手机等级实名  和  已加入手机号
                            $(".btn-right").on("click", function() {
                                location.href = "simcard_realbind.aspx?imei=" + imei + "&mobile=" + mobile + "&simNo=" + iccid + "&apptype=" + apptype + "&wechatId=" + wxchatId + "&mchId=" + mchId + "&accessname=" + accessname + "&fromapp=" + from_app;
                            })
                        } else {
                            //跳转会员注册
                            $(".btn-right").on("click", function() {
                                location.href = "../member/register.aspx?onlyVer=true&action=mobilereal&imei=" + imei + "&mobile=" + mobile + "&userId=" + userId + "&iccid=" + iccid + "&simId=" + simId + "&apptype=" + apptype + "&wechatId=" + wxchatId + "&mchId=" + mchId + "&accessname=" + accessname + "&fromapp=" + from_app + "&cardType=" + cardType + "&realNameLevel=" + realNameLevel +"&simstate=" +simstate;
                            })
                        }

                    }
                } else {

                    if (result.user) {
                        //实名验证
                        if (result.realState == 3) {
                            //是否与此卡绑定--
                            //1
                            if (result.binds == false) {
                                $(".btn-right").attr("data-dismiss", "modal").text("确定").parent().attr("class", "col-xs-12 text-center");
                                $(".btn-left").remove();
                                if (activePgStatusFlag) {
                                    $(".modal-body div.modal-body-text").html("是否用会员账号(" + userName + ")" + trueName + "实名认证该流量卡?实名后将赠送套餐:" + "<b>" + data.activePgName + "</b>");
                                } else {
                                    $(".modal-body div.modal-body-text").html("是否用会员账号(" + userName + ")" + trueName + "实名认证该流量卡?");
                                }
                                $(".btn-right").on("click", function() {
                                    if (apptype == 118 || from_app == "h5") {
                                        location.href = "../Terminal/AuthRealName.aspx?num=" + iccid + "&num_type=iccid&imei=" + imei;
                                        return;
                                    }
                                    bindCard(userId, simId);
                                })
                                if (!$("#uncertification").is(":hidden") && activeByRealName === 1)
                                    $("#uncertification").trigger("click");
                                else
                                    addAnimation();
                            }
                            //实名审核中
                        } else if (result.realState == 2) {
                            if (result.binds == false) {
                                //3
                                $(".btn-left").remove();
                                $(".btn-right").attr("data-dismiss", "modal").text("确定").parent().removeClass("col-xs-6").addClass("col-xs-12");
                                if (activePgStatusFlag) {
                                    $(".modal-body div.modal-body-text").html("是否用审核中的会员(" + userName + ")认证该流量卡,认证通过后自动完成该流量卡的实名认证,并赠送套餐:" + "<b>" + data.activePgName + "</b>");
                                } else {
                                    $(".modal-body div.modal-body-text").html("是否用审核中的会员(" + userName + ")认证该流量卡,认证通过后自动完成该流量卡的实名认证");
                                }
                                $(".btn-right").on("click", function() {
                                    bindCard(userId, simId);
                                })
                                if (!$("#uncertification").is(":hidden") && activeByRealName === 1)
                                    $("#uncertification").trigger("click");
                                else
                                    addAnimation();
                            } else {
                                //2
                                $(".modal-body div.modal-body-text").text("您已提交实名信息，审核期间不影响您合法合规使用");
                                $(".btn-right").attr("data-dismiss", "modal").text("知道了").parent().removeClass("col-xs-6").addClass("col-xs-12");
                                $(".btn-left").hide();
                            }
                        }
                        //非实名验证
                        else {
                            //4
                            if (activePgStatusFlag) {
                                $(".modal-body div.modal-body-text").html("当前会员账号(" + userName + ")未实名,请完成实名认证,实名后将赠送套餐:" + "<b>" + data.activePgName + "</b>");
                            } else {
                                $(".modal-body div.modal-body-text").html("当前会员账号(" + userName + ")未实名,请完成实名认证");
                            }
                            $(".btn-right").text("实名认证").parent().attr("class", "col-xs-12 text-center");
                            $(".btn-right").on("click", function() {
                                location.href = verUrl + verData;
                            })
                            $(".btn-left").remove();
                            if (!$("#uncertification").is(":hidden") && activeByRealName === 1)
                                $("#uncertification").trigger("click");
                            else
                                addAnimation();
                        }
                    } else {
                        //5
                        if (activePgStatusFlag) {
                            $(".modal-body div.modal-body-text").html("尊敬的企业实名用户,请您尽快提交实名认证,完善个人资料,实名后将赠送套餐:" + "<b>" + data.activePgName + "</b>");
                        } else {
                            $(".modal-body div.modal-body-text").html("尊敬的企业实名用户,请您尽快提交实名认证,完善个人资料,否则我们将依法停止通信服务");
                        }
                        $(".btn-right").text("实名注册").parent().attr("class", "col-xs-12 text-center");
                        $(".btn-left").remove().parent().css("marginTop", "15px").attr("class", "col-xs-12 text-center");

                        if (apptype == 118 || from_app == "h5") {
                            $(".btn-right").on("click", function() {
                                if (userId !== '0') {
                                    location.href = "../Terminal/AuthRealName.aspx?num=" + iccid + "&num_type=iccid&imei=" + imei;
                                } else {
                                    location.href = "../member/register.aspx?userId=" + userId + "&simId=" + simId + "&iccid=" + iccid + "&mobile=" + mobile + "&onlyVer=true&fromapp=" + from_app + "&realNameLevel=" + realNameLevel+"&mchId="+mchId +"&simstate=" +simstate;
                                }
                            })
                        } else {
                            $(".btn-right").on("click", function() {
                                if (mobile && phoneReg.test(mobile)) {
                                    //location.href = "../member/Verified.aspx?userId=" + userId + "&simId=" + simId + "&iccid=" + iccid + "&onlyVer=true&fromapp=" + from_app;
                                    location.href = "../member/Verified.aspx?userId=" + userId + "&simId=" + simId + "&iccid=" + iccid + "&onlyVer=true&fromapp=" + from_app + "&mchId=" + mchId +"&simstate=" +simstate;;

                                } else {
                                    location.href = "../member/register.aspx?userId=" + userId + "&simId=" + simId + "&iccid=" + iccid + "&onlyVer=true&fromapp=" + from_app + "&realNameLevel=" + realNameLevel+"&mchId="+mchId +"&simstate=" +simstate;
                                }
                            })
                        }
                        if (result.realState !== 2 && result.realState !== 3 && !$("#uncertification").is(":hidden") && activeByRealName === 1)
                            $("#uncertification").trigger("click");
                        else
                            addAnimation();
                    }
                }
            }
            w_h();
        },
        error: function() {
            console.log("失败")
        }
    })
}

function updateDXTerminalState(dxData) {
    $.ajax({
        type: "POST",
        dataType: "json",
        url: "../../api/QueryTerminalApiDataForDX",
        data: JSON.stringify(dxData),
        contentType: "application/json",
        success: function(c) {
            if (c === null || c === undefined || c === "") {
                console.log("同步出错");
                return;
            }
            if (c.error === 0) {
                var res = c.result;
                console.log("同步成功");
                if (dataInteractive.simFromType == 2) { //电信
                    dataInteractive.dxSimState(res); //simState 4已停用   2可激活
                }
            } else {
                console.log("同步失败 " + c.reason);
            }
        }
    });
}

function updateTerminalState(data, changeData, isLimitlessUsage) {
    $.ajax({
        type: "POST",
        dataType: "json",
        url: "../../api/UpdateTerminalUsageByICCID",
        data: JSON.stringify(data),
        contentType: "application/json",
        success: function(c) {
            if (c === null || c === undefined || c === "") {
                console.log("同步出错");
                return;
            }
            if (c.error === 0) {
                var res = c.result;
                console.log("同步成功");
                totalMonthUsage = res.totalMonthUsage;
                var usedText = c.result.simState !== 2 ? "已使用" + (changeCloseTime(firstActiveTime, currentTime) + 1) + "个月(含本月)" : "未使用";

                // $("#surplusFlow").html(isLimitlessUsage ? judgement(res.doneUsage, true, '', 1000) : judgement(res.surplusUsage, true));
                if (isLimitlessUsage) {
                    // 本月已用
                    $("#surplusFlowNew").html(judgement(res.doneUsage, true, '', 1000))
                } else {
                    // 剩余
                    $(".last-flow").html(judgement(res.surplusUsage, true));
                    // 用量百分比
                    var num = 0;
                    if (data.amountUsageData != '' && res.surplusUsage != '') {
                        num = (res.surplusUsage / data.amountUsageData * 100).toFixed(0);
                    }
                    $(".all-normal-meal .progress").css("width", num + "%");

                }

                $(".allFlow").text(judgement(data.amountUsageData))


                if (dataInteractive.simFromType == 1) { //联通
                    dataInteractive.simState(res, 4)
                } else if (dataInteractive.simFromType == 0) { //移动
                    dataInteractive.simState(res, 2)
                }
                stateChange(res.gprsState,dataInteractive.simFromType);

                // 无限流量套餐isLimitlessUsage==1是无限套餐
                if (data.isLimitlessUsage) {
                    res.usageResetTimeType = keepUsageResetTimeType;
                    isLimitlessNew(res);

                    // 高速、中速、低速剩余流量

                    var handleMonthUsage = parseFloat(judgement(res.monthUsageData, false, '', 1000));
                    var handleDayUsage = parseFloat(judgement(res.dayUsageData, false, '', 1000));

                    if (getNetWorkSpeed == '1') {
                        var num;
                        if (keepUsageResetTimeType == 1) {

                            if (Math.ceil(res.dayUsageData / 1024) > 22) {
                                num = getHigUsageToPeriod - handleDayUsage * 1024; //若为高速，高速流量剩余=高速周期流量-处理过的已用量24.57G；
                                num = num < 0 ? 0 : num;

                            } else {
                                num = getHigUsageToPeriod - res.dayUsageData < 0 ? 0 : getHigUsageToPeriod - res.dayUsageData;
                            }

                        } else {

                            if (Math.ceil(res.monthUsageData / 1024) > 22) {
                                num = getHigUsageToPeriod - handleMonthUsage * 1024; //若为高速，高速流量剩余=高速周期流量-处理过的已用量24.57G；
                                num = num < 0 ? 0 : num;

                            } else {
                                num = getHigUsageToPeriod - res.monthUsageData < 0 ? 0 : getHigUsageToPeriod - res.monthUsageData;
                            }
                        }

                        $('#flowSurplus').html('(高速' + (num >= 500 ? '流量充足' : '剩余:<span style="color:#fb7738;font-size:14px;"> ' + judgement(num, '')) + '</span>)'); //高速流量 - 本月已用
                    } else if (getNetWorkSpeed == '2') {
                        var num = res.amountUsageData - res.monthUsageData < 0 ? 0 : res.amountUsageData - res.monthUsageData;

                        if (Math.ceil(res.monthUsageData / 1024) > 22) {
                            num = getUsageToPeriod - handleMonthUsage * 1024; //若为中速，中速流量剩余周期用量-处理过的已用量24.57G
                            num = num < 0 ? 0 : num;
                        }

                        if (keepUsageResetTimeType != 1) {
                            $('#flowSurplus').html('(中速剩余：<span style="color:#fb7738;font-size:14px;">' + (judgement(num, "")) + '</span>)'); //卡周期流量 - 本月已用
                        }

                    } else {
                        // $('#LimitlessInfo > p').eq(2).hide();
                        $('.layer-speed > ul').addClass('marginTop15');
                        $('#limitlessBox > ul').addClass('marginTop15');

                    }
                    w_h();

                }



            } else {
                console.log("同步失败 " + c.reason);
            }
        }
    });
}

function dOrM(num) {
    if (isUsageReset === 1) {
        if (num === 0) {
            return 0;
        }
        if (vexpireTime === '') {
            if (num % 365 === 0) {
                return num / 365 * 12;
            } else {
                return Math.round(num / 30);
            }
        } else {
            return changeCloseTime(currentTime, vexpireTime);
        }
    }
    return num;
}

function judgement(num, sub, tofixed, fb) {
    var sub = sub || false,
        tofixed = tofixed === 0 ? 0 : tofixed ? tofixed : 2,
        subFStr = sub !== false ? "<sub>" : "",
        subBstr = sub !== false ? "</sub>" : "",
        denominator = fb > 0 ? fb : 1024,
        flowNum,
        flowUnit;
    if (num < 1000) {
        flowNum = num;
        flowUnit = "MB"
    } else {
        flowNum = num / denominator;
        flowUnit = "GB"
        if (flowNum > 1000) {
            flowNum = flowNum / denominator;
            flowUnit = "TB"
        }
    }
    return commafy(flowNum.toFixed(tofixed)) + subFStr + flowUnit + subBstr;
}

//nTime:首次激活 eTime:当前日期
function changeCloseTime(nTime, eTime) {
    var nTime = nTime.split(' ');
    nTime = nTime[0].split('-');
    var eTime = eTime.split(' '),
        eTime = eTime[0].split('-');
    addM = eTime[0] - nTime[0],
        theM = parseInt(eTime[1]) + 12 * addM;
    nTime[2] > 26 ? theM-- : "";
    eTime[2] > 26 ? theM++ : "";
    return theM - nTime[1];
}

//套餐tab切换
function tabChange() {
    var tabsSwiper = new Swiper('.swiper-container', {
        speed: 500,
        onSlideChangeStart: function() {
            $(".tabs .active").removeClass('active');
            $(".tabs a").eq(tabsSwiper.activeIndex).addClass('active');
        }
    });

    $(".tabs a").on('touchstart mousedown', function(e) {
        e.preventDefault()
        $(".tabs .active").removeClass('active');
        $(this).addClass('active');
        tabsSwiper.swipeTo($(this).index());
    });

    $(".tabs a").click(function(e) {
        e.preventDefault();
    });
}
//统计点击次数
function statisticsClickCount() {
    return true;
}

//跳转至历史续费记录和本月用量详情
function toRenewalRecord() {
    if (statisticsClickCount()) {
        var iccid = $.trim($("#hid_iccid").val()),
            d = new Date(),
            year = d.getFullYear();
        window.location.href = "../Terminal/renewalRecord.aspx?iccid=" + iccid + "&year=" + year;
    }
}

function toMonthAmount() {
    if (statisticsClickCount()) {
        var iccid = $.trim($("#hid_iccid").val());
        window.location.href = "../Terminal/MonthAmount.aspx?iccid=" + iccid + "&simTypes=" + simTypes;
    }
}
//点击状态标签 跳转至智能诊断页面
function toIntelligentDiagnosis() {
    if (statisticsClickCount()) {
        var iccid = $.trim($("#hid_iccid").val());
        window.location.href = "../IntelligentDiagnosis/IntelligentDiagnosis.aspx?iccid=" + iccid + "&fromapp=" + from_app;
    }
}
//带URL参数跳转到help页面
function toHelp() {
    if (statisticsClickCount()) {
        //var url = location.href;
        var iccid = $.trim($("#hid_iccid").val());
        window.location.href = "../Terminal/fq.html?iccid=" + iccid;
    }
}
// //点击图片进入弹出层
// function toSolve() {
//     if ($("#qrcode canvas").length == 0) {
//         var iccid = $.trim($("#hid_iccid").val());
//         text = window.location.href.split('?')[0].replace('simcard_lt_new.aspx', 'searchsims.aspx');
//         url = text + '?iccid=' + iccid + '&fromapp=h5';
//         $("#qrcode").qrcode(url);
//     }
//     if ($(".solve-mb:visible").length == 1) {
//         $(".solve-mb").fadeOut();
//     } else {
//         $(".solve-mb").fadeIn();
//     }

//     $('.solve-mb').unbind('click').on('click', function() {
//         $(this).fadeOut();
//     })
// }
// 
$(".solve-btn").click(function(){
    $(".solve-btn").hide();
    layer.open({
        shift:6,
        shadeClose:false,
        style: 'height:60%;width:100%;',
        content: $(".solve-layer-wrap").html()
    });
})


function layerLinkFun(type){
    var iccid = $.trim($("#hid_iccid").val());
    $(".solve-btn").show();
    layer.closeAll()
    if (statisticsClickCount()) {
        if(type=="diagnosisBtn"){
            window.location.href = "../IntelligentDiagnosis/IntelligentDiagnosis.aspx?iccid=" + iccid + "&fromapp=" + from_app;
        }else if(type=="faq"){
            window.location.href = "../Terminal/fq.html?iccid=" + iccid;

        }else if(type=="supplementCardBtn"){
            window.location.href = "../WechatPay/Action/RemakeCardPayOrder.aspx?iccid="+iccid;
            
        }else if(type=="useAmountBtn"){
            window.location.href = "../Terminal/MonthAmount.aspx?iccid=" + iccid + "&simTypes=" + simTypes;
            
        }else if(type=="closerBtn"){
            layer.closeAll()
        }
       
    }

}


//高度
function w_h() {
    var h = $(window).height();
    var h_header = $("header").prop("offsetHeight");
    var h_footer = $("footer").prop("offsetHeight");
    var h_tabs = $(".tabs").prop("offsetHeight");
    var limit_tabs = $(".limit-tabs").prop("offsetHeight");
    var InfiniteMeal = $("#history").prop("offsetHeight");
    var h_main = h - h_header - h_footer - 7;
    $("#tabs_package").outerHeight(h_main);
    $("#nowPackageList").outerHeight(h_main - h_tabs);
    $("#limitNowPackageList").outerHeight(h_main - limit_tabs-InfiniteMeal-22);
    $("#packageList_wrap").outerHeight(h_main - h_tabs);
    if (contentPackage == 1) {
        var tabs = $(".detail-title").prop("offsetHeight");
        var mealItem = $("#meal-history").prop("offsetHeight");
        $(".detail-info").outerHeight(h_main - tabs - mealItem);

    }
}
//窗体大小自动调整
window.onresize = function() {
    w_h();
}
//下拉刷新
var slide = function(option) {
    var defaults = {
        container: '',
        next: function() {}
    }
    var start,
        end,
        length,
        isLock = false, //是否锁定整个操作
        isCanDo = false, //是否移动滑块
        isTouchPad = (/hp-tablet/gi).test(navigator.appVersion),
        hasTouch = 'ontouchstart' in window && !isTouchPad;
    var obj = document.querySelector(option.container);
    var loading = obj.firstElementChild;
    var offset = loading.clientHeight;
    var objparent = obj.parentElement;
    /*操作方法*/
    var fn = {
        //移动容器
        translate: function(diff) {
            obj.style.webkitTransform = 'translate3d(0,' + diff + 'px,0)';
            obj.style.transform = 'translate3d(0,' + diff + 'px,0)';
        },
        //设置效果时间
        setTransition: function(time) {
            obj.style.webkitTransition = 'all ' + time + 's';
            obj.style.transition = 'all ' + time + 's';
        },
        //返回到初始位置
        back: function() {
            fn.translate(0 - offset);
            //标识操作完成
            isLock = false;
        },
        addEvent: function(element, event_name, event_fn) {
            if (element.addEventListener) {
                element.addEventListener(event_name, event_fn, false);
            } else if (element.attachEvent) {
                element.attachEvent('on' + event_name, event_fn);
            } else {
                element['on' + event_name] = event_fn;
            }
        }
    };

    fn.translate(0 - offset);
    fn.addEvent(obj, 'touchstart', start);
    fn.addEvent(obj, 'touchmove', move);
    fn.addEvent(obj, 'touchend', end);
    fn.addEvent(obj, 'mousedown', start)
    fn.addEvent(obj, 'mousemove', move)
    fn.addEvent(obj, 'mouseup', end)

    function getY(even) {
        return hasTouch ?
            even.touches ? even.touches[0].pageY : 0 :
            even.pageY || 0;
    }
    //滑动开始
    function start(e) {
        var even = typeof event == "undefined" ? e : event;
        end = getY(even)
        if (even.srcElement == "swiper-container" || $(".swiper-container").find(even.srcElement).length > 0) {
            if ($("#packageList_wrap").scrollTop() <= 0) {
                //标识操作进行中
                isLock = true;
                isCanDo = true;
                //保存当前鼠标Y坐标
                start = getY(even)
                //消除滑块动画时间
                fn.setTransition(0);
                loading.innerHTML = '下拉刷新数据';
            }
        } else if (objparent.scrollTop <= 0 && !isLock) {
            //标识操作进行中
            isLock = true;
            isCanDo = true;
            //保存当前鼠标Y坐标
            start = getY(even)
            //消除滑块动画时间
            fn.setTransition(0);
            loading.innerHTML = '下拉刷新数据';
        }
        return false;
    }

    //滑动中
    function move(e) {
        if (objparent.scrollTop <= 0 && isCanDo) {
            var even = typeof event == "undefined" ? e : event;
            //保存当前鼠标Y坐标
            end = getY(even)
            if (start < end) {
                even.preventDefault();
                //消除滑块动画时间
                fn.setTransition(0);
                //移动滑块
                if ((end - start - offset) / 2 <= 150) {
                    length = (end - start - offset) / 2;
                    fn.translate(length);
                } else {
                    length += 0.3;
                    fn.translate(length);
                }
            }
        }
    }
    //滑动结束
    function end(e) {
        if (isCanDo) {
            isCanDo = false;
            //判断滑动距离是否大于等于指定值
            if (end - start >= offset) {
                //设置滑块回弹时间
                fn.setTransition(1);
                //保留提示部分
                fn.translate(0);
                //执行回调函数
                loading.innerHTML = '正在刷新数据';
                if (typeof option.next == "function") {
                    option.next.call(fn, e);
                }
            } else {
                //返回初始状态
                fn.back();
            }
        }
    }
}