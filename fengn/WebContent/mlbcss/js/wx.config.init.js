/**
 * --------------------------------------------
 * 微信配置初始化页面，统一调用
 * 作用：加载当前页面所需要的依赖，并隐藏页面分享菜单，保护页面源代码
 * 本类会调用后台接口获取appid、签名等信息
 * 引用本类前请先引用依赖：
 * jquery 9.0以上版本类库
 * https://res.wx.qq.com/open/js/jweixin-1.0.0.js
 * --------------------------------------------
 */
(function () {
    'use strict';

    /**
     * 页面初始化配置
     * 参数：
     * path         相对根接口路径，传入api的前面部分即可
     * extendApi    扩展api列表，数组格式：[ 'scanQRCode', 'chooseWXPay', 'chooseImage' ]
     */
    function readyWXCgi(path, extendApi) {
        $.getJSON(path + "api/GetWXSignConfigInfo", function (res) {
            if (res === null || res === "" || res === undefined)
                return;

            if (res.error !== 0 || res.result === null)
                return;

            action_wx(res.result.appId, res.result.timestamp, res.result.noncestr, res.result.signature, extendApi);
        });
    }
    

    function action_wx(appid, timestamp, noncestr, signature, extendApi) {
        // 基础api列表
        var apiArr = [
            'checkJsApi',
            'hideOptionMenu',
            'hideAllNonBaseMenuItem',
            'closeWindow'
        ];
        // 加载扩展api列表
        if (extendApi !== null && extendApi !== "") {
            $.map(extendApi, function (e) {
                if (e !== null && e !== "") {
                    if (e === "showAllNonBaseMenuItem") {
                        apiArr[2] = e;
                    } else {
                        apiArr.push(e);
                    }
                }
            });
        }
        
        wx.config({
            debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
            appId: appid, // 必填，公众号的唯一标识
            timestamp: timestamp, // 必填，生成签名的时间戳
            nonceStr: noncestr, // 必填，生成签名的随机串
            signature: signature,// 必填，签名，见附录1
            jsApiList: apiArr   // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
        });
    }

    wx.ready(function () {
        // 隐藏右上角非基础菜单接口
        wx.hideAllNonBaseMenuItem();
    });

    /**
	 *! 定义全局函数对象
	 *! 调用：		$.fn(data, namespace);
	 *! data: 		[ [ 'map', map ], [ 'init', init ], [ 'setapp', setapp ] ]
	 *! namespace:  命名空间
	 */
    function fn(data, namespace) {
        var _count = data.length;
        window[namespace] = window[namespace] || {};
        for (var i = 0; i < _count; i++) {
            window[namespace][data[i][0]] = window[namespace][data[i][0]] || data[i][1];
        }
    }

    fn([
        ['readyWXCgi', readyWXCgi]
    ], 'wxcgi');
}());

var wxapi = window['wxcgi'];