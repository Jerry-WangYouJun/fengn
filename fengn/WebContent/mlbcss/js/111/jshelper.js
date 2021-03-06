﻿(function () {
  'use strict';

  function getUrlQuery(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null)
      return decodeURI(r[2]);
    return null;
  }

  //弹窗显示指令成功或者失败的状态
  function comStatus(promptName) {
    $(promptName).slideDown(200);
    setTimeout(function () { 
      $(promptName).slideUp(200);
    }, 3000);
  }

  //获取中英文字符长度，截取指定长度字符显示
  function cutStr(str, len) {
    var str_length = 0;

    if (str == null || str == "" || str.length == 0 || str == undefined) return "";
    if (str == '未填写品牌') return str;

    var str_cut = new String();
    for (var i = 0; i < str.length; i++) {
      var a = str.charAt(i);
      str_length++;
      if (escape(a).length > 4) {
        //中文字符的长度经编码之后大于4  
        str_length++;
      }
      str_cut = str_cut.concat(a);
      if (str_length >= len) {
        str_cut = str_cut.concat("...");
        return str_cut;
      }
    }
    //如果给定字符串小于指定长度，则返回源字符串；  
    if (str_length < len) {
      return str;
    }
  }

	/**
	 *! 确认框封装
	 *! 调用例子：
	 *! 打开：shared.cfm.open('确定要提交吗？');
	 *! 关闭：shared.cfm.close();
	 */
  var cfm = (function () {
    var $cfm = null, $content = null;

    return {
      open: open,
      close: close
    }


    function open(text) {
      if ($cfm === null)
        init_cfm_ele();
      $cfm = $(".m-confirm-bg");
      $content = $(".m-confirm-container");

      $('.m-confirm-msg', $content).text(text);
      $cfm.show();
      $content.show();
    }

    function close() {
      if ($cfm !== null)
        $cfm.hide();
      if ($content !== null)
        $content.hide();
    }

  }());

  /*loading加载方法*/

  var loading = (function () {
    var $loading = null, $content = null;

    return {
      open: open,
    }

    function init_loading_ele() {
      var html = [];
      html.push('<div class="m-confirm-bg"></div>');
      html.push('<div class="loading">');
      html.push('<div class="loading-img"><img src="../cgi-bin/images/loading.gif" /></div><div class="loading-text">正在查询</div>');
      html.push('</div>');
      $('body').append(html.join(''));
    }


    function open(text) {
      if ($loading === null)
        init_loading_ele();
      $loading = $(".m-confirm-bg");
      $content = $(".loading");

      $('loading-text', $content).text(text);
      $loading.show();
      $content.show();

    }

  }());


  /*获取照片和视频方法*/

  var photoCapture = (function () {
    var $photoCapture = null, $content = null;

    return {
      open: open,
    }

    function init_photoCapture_ele() {
      var html = [];
      html.push('<div class="m-confirm-bg"></div>');
      html.push('<div class="photoCapture">');
      html.push('<div class="photoCapture-text"></div>');
      html.push('</div>');
      $('body').append(html.join(''));
    }


    function open(text) {
      if ($photoCapture === null)
        init_photoCapture_ele();
      $photoCapture = $(".m-confirm-bg");
      $content = $(".photoCapture");

      $('.photoCapture-text', $content).text(text);
      $photoCapture.show();
      $content.show();

    }

  }());








	/**
	 *! 信息框封装，用于显示详细地址为主
	 *! 调用例子：
	 *! 打开：shared.msg.open('河北省衡水市桃城区...');
	 *! 关闭：shared.msg.close();
	 */
  var msg = (function () {
    var $msg = null, $content = null;

    return {
      open: open,
      close: close
    }

    function init_msg_ele() {
      var html = [];
      html.push('<div class="m-confirm-bg"></div>');
      html.push('<div class="m-confirm-container">');
      html.push('<div class="m-confirm-msg"></div>');
      html.push('<div class="m-confirm-btn">');
      html.push('<div class="m-row">');
      html.push('<div class="m-col-1"></div>');
      html.push('<div class="m-col-10">');
      html.push('<button type="button" class="m-btn m-btn-info m-btn-block" onclick="shared.msg.close()">关闭</button>');
      html.push('</div>');
      html.push('<div class="m-col-1"></div>');
      html.push('</div>');
      html.push('</div>');
      html.push('</div>');
      $('body').append(html.join(''));
    }

    function open(text) {
      if ($msg === null)
        init_msg_ele();
      $msg = $(".m-confirm-bg");
      $content = $(".m-confirm-container");

      $('.m-confirm-msg', $content).text(text);
      $msg.show();
      $content.show();
    }

    function close() {
      if ($msg !== null)
        $msg.hide();
      if ($content !== null)
        $content.hide();
    }

  }());

  //手机提示框
  function getPrompt(text, time) {
    $('#promptBox').remove();
    if ($('#promptBox').length == 0) {
      $('body').append('<div id="promptBox" style="display: none;">' + text + '</div>');
      $('#promptBox').css({
        position: 'fixed',
        top: '40%',
        transform: 'translate(-50%)',
        'width': '50%',
        'text-align': 'center',
        padding: '20px 20px',
        left: '50%',
        'background-color': 'rgba(0,0,0,.5)',
        color: '#fff',
        'border-radius': '8px',
        'z-index': 9999
      });
    } else {
      $('#promptBox').text(text);
    }
    $('#promptBox').fadeIn(600);
    setTimeout(function () {
      $('#promptBox').fadeOut(600);
    }, time ? time : 3000)
  }

  //打开等待提示窗口
  var loadCfm = (function () {
    var $cfm = null, $content = null;

    return {
      open: open,
      close: close
    }

    function init_cfm_ele() {
      var html = [];
      $('.m-confirm-bg').length == 0 ? html.push('<div class="m-confirm-bg"></div>') : '';
      html.push('<div class="m-confirm-loading">');
      html.push('<div style="float: left;margin-top: 11px;margin-left: 10px;"><img src="/Html/images/terminal/loading.gif" /> </div>');
      html.push('<div class="m-loading-msg">正在处理中...</div>');
      html.push('</div>');
      $('body').append(html.join(''));
    }

    function open() {
      if ($cfm === null)
        init_cfm_ele();
      $cfm = $(".m-confirm-bg");
      $content = $(".m-confirm-loading");

      //$('.m-loading-msg', $content).text(text);
      $cfm.show();
      $content.show();
    }

    function close() {
      if ($cfm !== null)
        $cfm.hide();
      if ($content !== null)
        $content.hide();
    }

  }());

  var timehelper = (function () {

    return {
      getTimeSpaceHHMM: getTimeSpaceHHMM,
      getTimeFormatMDHM: getTimeFormatMDHM
    };

    /**
     *! 获取一段时间的时长
     *# 参数格式(start_time/end_time)：2015-05-01 07:06:06
     *# 注意：end_time > start_time
     *! 返回格式：HH:mm
     */
    function getTimeSpaceHHMM(start_time, end_time) {
      var st = start_time.split(' ');
      var et = end_time.split(' ');
      var ar_ds = st[0].split('-');
      var ar_ts = st[1].split(':');
      var ar_de = et[0].split('-');
      var ar_te = et[1].split(':');
      var ds = new Date(ar_ds[0], ar_ds[1] - 1, ar_ds[2], ar_ts[0], ar_ts[1]);
      var de = new Date(ar_de[0], ar_de[1] - 1, ar_de[2], ar_te[0], ar_te[1]);

      var miss = de.getTime() - ds.getTime();
      var hhmm = [];
      var hh = (miss / 3600000).toFixed(0);
      var mm = ((miss % 3600000) / 60000).toFixed(0);
      hhmm.push(hh < 10 ? '0' + hh : hh);
      hhmm.push(mm < 10 ? '0' + mm : mm);

      return hhmm.join(':');
    }

    /**
     *! 返回简略的日期时间格式
     *# 参数格式(date_time)：2015-05-01 07:06:06
     *! 返回格式：MM-dd HH:mm
     *! 如：05-01 07:06
     */
    function getTimeFormatMDHM(date_time) {
      var t = date_time.split(' ');
      var md = t[0].split('-');
      var hm = t[1].split(':');
      return md[1] + '-' + md[2] + ' ' + hm[0] + ':' + hm[1];
    }

  }());

  function getMyLocation(callBackCurPosition) {
    var options = {
      enableHighAccuracy: true,
      maximumAge: 3000
    }
    if (navigator.geolocation) {
      //浏览器支持geolocation
      navigator.geolocation.getCurrentPosition(callBackCurPosition, callBackonError, options);
    } else {
      //浏览器不支持geolocation
    }
  }

  function callBackonError(error) {
    switch (error.code) {
      case 1:
        alert("位置服务被拒绝");
        break;
      case 2:
        alert("暂时获取不到位置信息");
        break;
      case 3:
        alert("获取信息超时");
        break;
      case 4:
        alert("未知错误");
        break;
    }
  }

  // 保留小数点后几位
  function disposeNumber(data, subcount) {
    if (data == null || data == "") {
      return 0;
    } else if (data.toString().indexOf(".") == -1) {
      return data;
    } else {
      return round(data, subcount);
    }
  }

  function round(v, e) {
    var t = 1;
    for (; e > 0; t *= 10, e--);
    for (; e < 0; t /= 10, e++);
    return Math.round(v * t) / t;
  }

  function setSelIndex(obj, val) {
    for (var i = 0; i < obj.length; i++) {
      if (obj.options[i].value === val) {
        obj.selectedIndex = i;
        break;
      }
    }
  }

  function getOptionTextByVal(objSlt, val) {
    var sltText = "";
    for (var i = 0; i < objSlt.length; i++) {
      if (objSlt.options[i].value === val) {
        sltText = objSlt.options[i].text;
        break;
      }
    }
    return sltText;
  }

  // 解析地址接口
  function analyze_insert_address(lon, lat, path, element) {
    var url = path + 'api/GeoCoding';
    $.get(url, { lon: lon, lat: lat }).
      done(function (data) {
        if (data === null || data === '' || data === 'undefined')
          return;
        if (data.error !== 0)
          return;
        $(element).text(data.result);
      })
      .fail(function () {
        console.log('error');
      });
  }

  //判断经纬度是否合法
  function isCoordVaild(x) {
    var flag = false;
    if (x !== null) {
      if (!isNaN(x)) {
        if (x <= 180 && x > -180 && x !== 0) {
          flag = true;
        }
      }
    }
    return flag;
  }

  /*设置url跳转参数，加入、修改，参数：url本页面路径、q查询参数=objectId、key值*/
  function setUrlKey(url, q, key) {
    /*
      if(key=='undefined'){
          return url;
      }else{
          var param=getQuery(q); return param != null ? url.replace( q + '=' + escape(param), q + '=' + encodeURIComponent(key)) : url = url.indexOf('?') > 0 ? url + '&' + q + '=' + encodeURIComponent(key) : url + '?' + q + '=' + encodeURIComponent(key);
      }*/

    var newUrl = "";
    if (key == 'undefined') {
      newUrl = url;
    } else {
      var paramVal = getQueryFromSrc(url, q);
      if (paramVal != null) {
        newUrl = url.replace(q + '=' + escape(paramVal), q + '=' + encodeURIComponent(key));
      } else {
        if (url.indexOf('?') > 0) {
          newUrl = url + '&' + q + '=' + encodeURIComponent(key);
        } else {
          newUrl = url + '?' + q + '=' + encodeURIComponent(key);
        }
      }
    }

    return newUrl;
  }
  function getQueryFromSrc(src, name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = src.split('?').length > 1 ? src.split('?')[1].match(reg) : null;
    if (r != null)
      return unescape(r[2]);
    return null;
  }

  function ajax(url, data, type, sucCallBack, errCallBack, comCallBack) {
    var ajaxObj = {
      url: url,
      type: type,
      dataType: "JSON",
      contentType: "application/json"
    }
    if (!!data && data != "") {
      ajaxObj["data"] = JSON.stringify(data)
    }
    sucCallBack && (ajaxObj.success = sucCallBack)
    comCallBack && (ajaxObj.complete = comCallBack)
    errCallBack ? ajaxObj.error = errCallBack : function () { alert("网络异常") };
    return $.ajax(ajaxObj);
  }
  //字符串中添加空格功能
  //如 addBlank("xxxxxxxxxxxxxxxxxx",[3,3,4,4,4]) => "xxx xxx xxxx xxxx xxxx"
  function addBlank(str, arr) {
    var str = str,
      strArr = str.split(""),
      arr = arr,
      arrCount = 0,
      i,
      len = arr.length;
    for (i = 0; i < len; i++) {
      arrCount += arr[i];
      strArr.splice(arrCount + i, 0, " ")
    }
    return strArr.join("")
  }

  function Trim(str, is_global) {
    var result;
    result = str.replace(/(^\s+)|(\s+$)/g, "");
    if (is_global.toLowerCase() == "g") {
      result = result.replace(/\s/g, "");
    }
    return result;
  }

  var TimeObjectUtil;
  /**
   * @title 时间工具类
   * @note 本类一律违规验证返回false
   * @author {boonyachengdu@gmail.com}
   * @date 2013-07-01
   * @formatter "2013-07-01 00:00:00" , "2013-07-01"
   */
  TimeObjectUtil = {
    /**
       * 获取当前时间毫秒数
       */
    getCurrentMsTime: function () {
      var myDate = new Date();
      return myDate.getTime();
    },
    /**
       * 毫秒转时间格式
       */
    longMsTimeConvertToDateTime: function (time) {
      var myDate = new Date(time);
      return this.formatterDateTime(myDate);
    },
    /**
       * 时间格式转毫秒
       */
    dateToLongMsTime: function (date) {
      var myDate = new Date(date);
      return myDate.getTime();
    },
    /**
       * 距离目标时间多少天
       */
    nowDateToEndDate: function (startTime, endTime) {
      var s = new Date(Date.parse((startTime + '').replace(/-/g, "/"))),
        e = new Date(Date.parse((endTime + '').replace(/-/g, "/")));
      var daysHs = e.getTime() - s.getTime();

      return Math.floor(daysHs / (1000 * 60 * 60 * 24));
    },
    /**
       * 增加天数（不含时间）
       */
    addDay: function (dayNumber, date) {
      date = date ? date : new Date();
      var ms = dayNumber * (1000 * 60 * 60 * 24)
      var newDate = new Date(date.getTime() + ms);
      return newDate;
    },
    /**
       * 格式化日期（不含时间）
       */
    formatterDate: function (date) {
      var datetime = date.getFullYear()
        + "-"// "年"
        + ((date.getMonth() + 1) >= 10 ? (date.getMonth() + 1) : "0"
          + (date.getMonth() + 1))
        + "-"// "月"
        + (date.getDate() < 10 ? "0" + date.getDate() : date
          .getDate());
      return datetime;
    },
    /**
       * 格式化日期（含时间"00:00:00"）
       */
    formatterDate2: function (date) {
      var datetime = date.getFullYear()
        + "-"// "年"
        + ((date.getMonth() + 1) >= 10 ? (date.getMonth() + 1) : "0"
          + (date.getMonth() + 1))
        + "-"// "月"
        + (date.getDate() < 10 ? "0" + date.getDate() : date
          .getDate()) + " " + "00:00:00";
      return datetime;
    },
    /**
       * 格式化去日期（含时间）
       */
    formatterDateTime: function (date) {
      var datetime = date.getFullYear()
        + "-"// "年"
        + ((date.getMonth() + 1) >= 10 ? (date.getMonth() + 1) : "0"
          + (date.getMonth() + 1))
        + "-"// "月"
        + (date.getDate() < 10 ? "0" + date.getDate() : date
          .getDate())
        + " "
        + (date.getHours() < 10 ? "0" + date.getHours() : date
          .getHours())
        + ":"
        + (date.getMinutes() < 10 ? "0" + date.getMinutes() : date
          .getMinutes())
        + ":"
        + (date.getSeconds() < 10 ? "0" + date.getSeconds() : date
          .getSeconds());
      return datetime;
    },
    /**
       * 时间比较{结束时间大于开始时间}
       */
    compareDateEndTimeGTStartTime: function (startTime, endTime) {
      return ((new Date(endTime.replace(/-/g, "/"))) > (new Date(
        startTime.replace(/-/g, "/"))));
    },
    /**
       * 验证开始时间合理性{开始时间不能小于当前时间{X}个月}
       */
    compareRightStartTime: function (month, startTime) {
      var now = formatterDayAndTime(new Date());
      var sms = new Date(startTime.replace(/-/g, "/"));
      var ems = new Date(now.replace(/-/g, "/"));
      var tDayms = month * 30 * 24 * 60 * 60 * 1000;
      var dvalue = ems - sms;
      if (dvalue > tDayms) {
        return false;
      }
      return true;
    },
    /**
       * 验证开始时间合理性{结束时间不能小于当前时间{X}个月}
       */
    compareRightEndTime: function (month, endTime) {
      var now = formatterDayAndTime(new Date());
      var sms = new Date(now.replace(/-/g, "/"));
      var ems = new Date(endTime.replace(/-/g, "/"));
      var tDayms = month * 30 * 24 * 60 * 60 * 1000;
      var dvalue = sms - ems;
      if (dvalue > tDayms) {
        return false;
      }
      return true;
    },
    /**
       * 验证开始时间合理性{结束时间与开始时间的间隔不能大于{X}个月}
       */
    compareEndTimeGTStartTime: function (month, startTime, endTime) {
      var sms = new Date(startTime.replace(/-/g, "/"));
      var ems = new Date(endTime.replace(/-/g, "/"));
      var tDayms = month * 30 * 24 * 60 * 60 * 1000;
      var dvalue = ems - sms;
      if (dvalue > tDayms) {
        return false;
      }
      return true;
    },
    /**
       * 获取最近几天[开始时间和结束时间值,时间往前推算]
       */
    getRecentDaysDateTime: function (day) {
      var daymsTime = day * 24 * 60 * 60 * 1000;
      var yesterDatsmsTime = this.getCurrentMsTime() - daymsTime;
      var startTime = this.longMsTimeConvertToDateTime(yesterDatsmsTime);
      var pastDate = this.formatterDate2(new Date(startTime));
      var nowDate = this.formatterDate2(new Date());
      var obj = {
        startTime: pastDate,
        endTime: nowDate
      };
      return obj;
    },
    /**
       * 获取今天[开始时间和结束时间值]
       */
    getTodayDateTime: function () {
      var daymsTime = 24 * 60 * 60 * 1000;
      var tomorrowDatsmsTime = this.getCurrentMsTime() + daymsTime;
      var currentTime = this.longMsTimeConvertToDateTime(this.getCurrentMsTime());
      var termorrowTime = this.longMsTimeConvertToDateTime(tomorrowDatsmsTime);
      var nowDate = this.formatterDate2(new Date(currentTime));
      var tomorrowDate = this.formatterDate2(new Date(termorrowTime));
      var obj = {
        startTime: nowDate,
        endTime: tomorrowDate
      };
      return obj;
    },
    /**
       * 获取明天[开始时间和结束时间值]
       */
    getTomorrowDateTime: function () {
      var daymsTime = 24 * 60 * 60 * 1000;
      var tomorrowDatsmsTime = this.getCurrentMsTime() + daymsTime;
      var termorrowTime = this.longMsTimeConvertToDateTime(tomorrowDatsmsTime);
      var theDayAfterTomorrowDatsmsTime = this.getCurrentMsTime() + (2 * daymsTime);
      var theDayAfterTomorrowTime = this.longMsTimeConvertToDateTime(theDayAfterTomorrowDatsmsTime);
      var pastDate = this.formatterDate2(new Date(termorrowTime));
      var nowDate = this.formatterDate2(new Date(theDayAfterTomorrowTime));
      var obj = {
        startTime: pastDate,
        endTime: nowDate
      };
      return obj;
    }
  };

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

  var phoneReg =  /^1\d{10}$/;
    // 八进制转化
  function strDecrypt(str) {
    var output = new Array();
    var i;
    var s = str.split("\\");
    for (i = 1; i < s.length; i++) {
        output += String.fromCharCode(parseInt(s[i], 8));
    }

    return output;

  }

  



  fn([
    ['fn', fn],
    ['cfm', cfm],
    ['getUrlQuery', getUrlQuery],
    ['getQueryFromSrc', getQueryFromSrc],
    ['cfm', cfm],
    ['loading', loading],
    ['photoCapture', photoCapture],
    ['msg', msg],
    ['loadCfm', loadCfm],
    ['getMyLocation', getMyLocation],
    ['disposeNumber', disposeNumber],
    ['setSelIndex', setSelIndex],
    ['getOptionTextByVal', getOptionTextByVal],
    ['timehelper', timehelper],
    ['analyze_insert_address', analyze_insert_address],
    ['isCoordVaild', isCoordVaild],
    ['cutStr', cutStr],
    ['comStatus', comStatus],
    ['setUrlKey', setUrlKey],
    ['ajax', ajax],
    ['Trim', Trim],
    ['addBlank', addBlank],
    ['getPrompt', getPrompt],
    ['TimeObjectUtil', TimeObjectUtil],
    ['phoneReg',phoneReg],
    ['strDecrypt',strDecrypt]
  ], 'jshelper');

}());

var shared = window['jshelper'];