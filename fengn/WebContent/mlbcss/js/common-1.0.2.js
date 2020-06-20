/** -----------------------------------
 *  公共脚本函数
 *  common.js
 *  update by cien 2012-05-21
 *  -----------------------------------
 */
function _cn(c){
    return document.getElementById(c);
}
function _cnOptVal(c, i){
    return _cn(c).options[i].value;
} 
function _cnOptTxt(c, i){
    return _cn(c).options[i].text;
}
function checkBrowser(){
    var Sys = {};
    var ua = navigator.userAgent.toLowerCase();
    var s;(s = ua.match(/msie ([\d.]+)/)) ? Sys.ie = s[1] :(s = ua.match(/firefox\/([\d.]+)/)) ? Sys.firefox = s[1] :(s = ua.match(/chrome\/([\d.]+)/)) ? Sys.chrome = s[1] :(s = ua.match(/opera.([\d.]+)/)) ? Sys.opera = s[1] :(s = ua.match(/version\/([\d.]+).*safari/)) ? Sys.safari = s[1] : 0;
    var vis="";
    if (Sys.ie) vis='IE-' + Sys.ie;
    if (Sys.firefox) vis='Firefox-' + Sys.firefox;
    if (Sys.chrome) vis='Chrome-' + Sys.chrome;
    if (Sys.opera) vis='Opera-' + Sys.opera;
    if (Sys.safari) vis='Safari-' + Sys.safari;
    return vis;
}
function getQuery(name){
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r!=null) 
        return unescape(r[2]);
    return null;
}
function getQueryFromSrc(src, name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = src.split('?').length > 1 ? src.split('?')[1].match(reg) : null;
    if (r != null)
        return unescape(r[2]);
    return null;
}
function getCharWidth(str){
    var tag1 = ",1,2,3,4,5,6,7,8,9,0,a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,-,_,";
    var tag2 = ",A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,";
    var len = 0;
    for(var i=0; i<str.length; i++){
        if(tag1.indexOf(str.substring(i,i + 1)) > -1){
            len += 6;
        }else if(tag2.indexOf(str.substring(i,i + 1)) > -1){
            len += 10;
        }else{
            len += 12;
        }
    }
    return len;
}
function getCookie(name){ 
    var cookief=false; 
    var start=0,end=0,i=0;
    var cookiestr=document.cookie; 
    while(i <=cookiestr.length){
        start=i;end=start+name.length; 
        if(cookiestr.substring(start,end)==name){
            cookief=true;
            break;
        }
        i++;
    } 
    if(cookief){ 
        start=end+1; 
        end=cookiestr.length; 
        return unescape(cookiestr.substring(start,end)).split('&')[0]; 
    } 
    return ""; 
}
function canInputNumber(obj){
    var val = obj.value;
    var str = "";
    for(var i=0; i<val.length; i++){
        if (!isNaN(val.substring(i, i + 1)) && val.substring(i, i + 1) != " ") {
            str += val.substring(i, i + 1);
        }
    }
    obj.value = str;
}
/*文本框只能输入字母和数字*/
var re = /^[A-Za-z0-9]*$/;
var canInputNumberAlpha_str = '';
function canInputNumberAlpha(obj) {
    if (re.test(obj.value) == false) {
        obj.value = canInputNumberAlpha_str;
    } else {
        canInputNumberAlpha_str = obj.value;
    }
}
function canInputDecimal(obj){
    var val = obj.value;
    var str = "";
    for(var i=0; i<val.length; i++){
        if(!isNaN(val.substring(i, i + 1))){
            str += val.substring(i, i + 1);
        }else if(val.substring(i, i + 1) == "." && str.indexOf(".") < 0){
            str += val.substring(i, i + 1);
        }
    }
    obj.value = str;
}
/*返回值：arg1乘以arg2的精确结果*/
function accMul(arg1,arg2){
    var m=0,s1=arg1.toString(),s2=arg2.toString();
    try{
        m+=s1.split(".")[1].length;
    }catch(e){}
    try{
        m+=s2.split(".")[1].length;
    }catch(e){}
    return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m);
}
/*返回值：arg1加上arg2的精确结果*/
function accAdd(arg1,arg2){
    var r1,r2,m;
    try{
        r1=arg1.toString().split(".")[1].length;
    }catch(e){
        r1=0;
    }
    try{
        r2=arg2.toString().split(".")[1].length;
    }catch(e){
        r2=0;
    }
    m=Math.pow(10,Math.max(r1,r2));
    return (accMul(arg1,m)+accMul(arg2,m))/m;
}
/*返回值：arg1减去arg2的精确结果*/
function accDed(arg1,arg2){
    var r1,r2,m;
    try{
        r1=arg1.toString().split(".")[1].length
    }catch(e){
        r1=0;
    }
    try{
        r2=arg2.toString().split(".")[1].length
    }catch(e){
        r2=0
    }
    m=Math.pow(10,Math.max(r1,r2));
    return (accMul(arg1,m)-accMul(arg2,m))/m;
}
/*返回值：arg1除以arg2的精确结果*/
function accDiv(arg1,arg2){
    var t1=0,t2=0,r1,r2;
    try{
        t1=arg1.toString().split(".")[1].length;
    }catch(e){}
    try{
        t2=arg2.toString().split(".")[1].length
    }catch(e){}
    with(Math){
        r1=Number(arg1.toString().replace(".",""));
        r2=Number(arg2.toString().replace(".",""));
        return (r1/r2)*pow(10,t2-t1);
    }
}
/*删除数组所有项*/
function delArrayAllItem(arr){
    var n = arr.length;
    for(var i=0; i<n; i++){
        arr = arr.slice(0, 0).concat(arr.slice(1, arr.length));
    }
    return arr;
}
function _cnSltTxt(c){
    var v = $('#' + c).val();
    var t = '';
    for(var i=0; i<_cn(c).length; i++){
        if(v == _cnOptVal(c, i)){
            t = _cnOptTxt(c, i);
        }
    }
    return t;
}
function getSltText(objSlt){
    var sltText = "";
    for(var i=0; i<objSlt.length; i++){
        if(objSlt.options[i].value == objSlt.value){
            sltText = objSlt.options[i].text;
            break;
        }
    }
    return sltText;
}
function getSltTextByValue(objSlt,val){
    var sltText = "";
    for(var i=0; i<objSlt.length; i++){
        if(objSlt.options[i].value == val){
            sltText = objSlt.options[i].text;
            break;
        }
    }
    return sltText;
}
function setSelectIndex(obj,val){
    for(var i=0;i<obj.length;i++){
        if(obj.options[i].value==val){
            obj.selectedIndex=i;
            break;
        }
    }
}
function getCbxValue(name){
    var obj = document.getElementsByTagName("input");
    var objValue = "";
    if(name != ""){
        for(var i=0; i<obj.length; i++){
            if(obj[i].type == "checkbox" && obj[i].name == name && obj[i].checked)
                objValue += obj[i].value + ",";
        }
    }else{
        for(var i=0; i<obj.length; i++){
            if(obj[i].type == "checkbox" && obj[i].checked)
                objValue += obj[i].value + ",";
        }
    }
    objValue = objValue.length > 0 ? objValue.substring(0, objValue.length - 1) : objValue;
    return objValue;
}
function getRdoValue(name){
    var obj = document.getElementsByTagName("input");
    var objValue = "";
    if(name != ""){
        for(var i=0; i<obj.length; i++){
            if(obj[i].type == "radio" && obj[i].name == name && obj[i].checked){
                objValue = obj[i].value;
                break;
            }
        }
    }else{
        for(var i=0; i<obj.length; i++){
            if(obj[i].type == "radio" && obj[i].checked){
                objValue = obj[i].value;
                break;
            }
        }
    }
    return objValue;
}
function setAnti(the,name){
    var obj = document.getElementsByTagName("input");
    if(name != ""){
        for(var i=0; i<obj.length; i++){
            if (obj[i].type == "checkbox" && obj[i] != the && obj[i].name == name)
                if (the.checked) {
                    obj[i].checked = true;
                }else{
                    obj[i].checked = false;
                }
        }
    }else{
        for(var i=0; i<obj.length; i++){
            if(obj[i].type == "checkbox" && obj[i] != the)
                if (the.checked) {
                    obj[i].checked = true;
                }else{
                    obj[i].checked = false;
                }
        }
    }
}
function DateDiff(sDate1,sDate2){
    var aDate, oDate1, oDate2, iDays;
    aDate = sDate1.split("-");
    oDate1 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0]);  //转换为12-18-2002格式
    aDate = sDate2.split("-");
    oDate2 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0]);
    iDays = parseInt(Math.abs(oDate1 - oDate2) / 1000 / 60 / 60 /24);  //把相差的毫秒数转换为天数
    return iDays;
}
/* 得到日期年月日等加数字后的日期 */
Date.prototype.dateAdd = function(interval, number) {
    var d = this;
    var k = { 'y': 'FullYear', 'q': 'Month', 'm': 'Month', 'w': 'Date', 'd': 'Date', 'h': 'Hours', 'n': 'Minutes', 's': 'Seconds', 'ms': 'MilliSeconds' };
    var n = { 'q': 3, 'w': 7 };
    eval('d.set' + k[interval] + '(d.get' + k[interval] + '()+' + ((n[interval] || 1) * number) + ')');
    return d;
}
/* 计算两日期相差的日期年月日等 */
Date.prototype.dateDiff = function(interval, objDate2) {
    var d = this, i = {}, t = d.getTime(), t2 = objDate2.getTime();
    i['y'] = objDate2.getFullYear() - d.getFullYear();
    i['q'] = i['y'] * 4 + Math.floor(objDate2.getMonth() / 4) - Math.floor(d.getMonth() / 4);
    i['m'] = i['y'] * 12 + objDate2.getMonth() - d.getMonth();
    i['ms'] = objDate2.getTime() - d.getTime();
    i['w'] = Math.floor((t2 + 345600000) / (604800000)) - Math.floor((t + 345600000) / (604800000));
    i['d'] = Math.floor(t2 / 86400000) - Math.floor(t / 86400000);
    i['h'] = Math.floor(t2 / 3600000) - Math.floor(t / 3600000);
    i['n'] = Math.floor(t2 / 60000) - Math.floor(t / 60000);
    i['s'] = Math.floor(t2 / 1000) - Math.floor(t / 1000);
    return i[interval];
}
//保留小数点有效位数
function numFormat(num,n){
    if(n>0){
        num = num.toString().length > 0 ? num : '0';
        if(num.toString().indexOf('.')<=0){
            num+=".0";
        }
        var str=num.toString().split('.')[0]+'.';
        var temp=num.toString().split('.')[1];
        if(temp.length>n){
            for(var i=0;i<n;i++){
                str+=temp.substring(i,i+1);
            }
        }else{
            str+=temp;
            if(temp.length<n){
                for(var i=temp.length;i<n;i++){
                    str+='0';
                }
            }
        }
        return str;
    }else{return num.toString();}
}
//获取当前日期时间
function getCurrectDateTime(){
    var clientDate=new Date();
    var yy=clientDate.getFullYear(),mh=Number(clientDate.getMonth())+1,dd=Number(clientDate.getDate());
    mh=mh<10?"0"+mh:mh;
    dd=dd<10?"0"+dd:dd;
    var hh=clientDate.getHours()<10?'0'+clientDate.getHours():clientDate.getHours(),mi=clientDate.getMinutes()<10?'0'+clientDate.getMinutes():clientDate.getMinutes(),ss=clientDate.getSeconds()<10?'0'+clientDate.getSeconds():clientDate.getSeconds();
    return yy+'-'+mh+'-'+dd+' '+hh+':'+mi+':'+ss;
}
function getFormatDatetimes(clientDate) {
    var yy = clientDate.getFullYear(), mh = Number(clientDate.getMonth()) + 1, dd = Number(clientDate.getDate());
    mh = mh < 10 ? "0" + mh : mh;
    dd = dd < 10 ? "0" + dd : dd;
    var hh = clientDate.getHours() < 10 ? '0' + clientDate.getHours() : clientDate.getHours(), mi = clientDate.getMinutes() < 10 ? '0' + clientDate.getMinutes() : clientDate.getMinutes(), ss = clientDate.getSeconds() < 10 ? '0' + clientDate.getSeconds() : clientDate.getSeconds();
    return yy + '-' + mh + '-' + dd + ' ' + hh + ':' + mi + ':' + ss;
}
function takeYear(theDate){
    var y=theDate.getYear()%100;
    y+=(y<38)?2000:1900;
    return y;
}
function leadingZero(nr){
    return Number(nr)<10?'0'+nr:nr;
}
function getDateTimeFor120(dd){
    if(dd!=null)
        return takeYear(dd)+"-"+leadingZero(dd.getMonth()+1)+"-"+leadingZero(dd.getDate())+" "+leadingZero(dd.getHours())+":"+leadingZero(dd.getMinutes())+":"+leadingZero(dd.getSeconds());
    return "";
}
/*时间比较方法，大于，时间格式：2011-12-21 18:50:12*/
function compareDate1(d1,d2){
    if(d1.length > 0 && d2.length > 0)
        return ((new Date(d1.replace(/-/g,"//"))) > (new Date(d2.replace(/-/g,"//"))));
    else
        return false;
}
/*时间比较方法，大于等于，时间格式：2011-12-21 18:50:12*/
function compareDate2(d1,d2){
    if(d1.length > 0 && d2.length > 0)
        return ((new Date(d1.replace(/-/g,"//"))) >= (new Date(d2.replace(/-/g,"//"))));
    else
        return false;
}
function ShowisLoad(isShow){
    if(isShow==1)
        $("#divWaitLoadingData").show();
    else
        $("#divWaitLoadingData").hide();
}
/*设置url跳转参数，加入、修改，参数：url本页面路径、q查询参数、key值*/
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
/*不做url参数编码处理*/
function setUrlKey1(url, q, key) {
    /*
    if(key=='undefined'){
        return url;
    }else{
        var param=getQuery(q); return param != null ? url.replace( q + '=' + param, q + '=' + key) : url = url.indexOf('?') > 0 ? url + '&' + q + '=' + key : url + '?' + q + '=' + key;
    }*/

    var newUrl = "";
    if (key == 'undefined') {
        newUrl = url;
    } else {
        var paramVal = getQueryFromSrc(url, q);
        if (paramVal != null) {
            newUrl = url.replace(q + '=' + paramVal, q + '=' + key);
        } else {
            if (url.indexOf('?') > 0) {
                newUrl = url + '&' + q + '=' + key;
            } else {
                newUrl = url + '?' + q + '=' + key;
            }
        }
    }

    return newUrl;
}
/*清除url跳转参数，参数：url本页面路径、q查询参数*/
function clearUrlKey(url,q){
    var param=escape(getQuery(q));
    if(param!=null){
        url=url.replace('?'+q+'='+param,'');
        url=url.replace('&'+q+'='+param,'');
    }
    if(url.indexOf('&')>0){
        if(url.indexOf('?')<0) url=url.split('&')[0]+'?'+url.replace(url.split('&')[0]+'&','');
    }
    return url;
}
/*不做url参数编码处理*/
function clearUrlKey1(url,q){
    var param=getQuery(q);
    if(param!=null){
        url=url.replace('?'+q+'='+param,'');
        url=url.replace('&'+q+'='+param,'');
    }
    if(url.indexOf('&')>0){
        if(url.indexOf('?')<0) url=url.split('&')[0]+'?'+url.replace(url.split('&')[0]+'&','');
    }
    return url;
}
/*限制文本框只能输入数字*/
function visibNum(obj){
    var val=obj.value,str='';
    var fx=false;
    for(var i=0;i<val.length;i++){
        if(!isNaN(val.substring(i,i+1))){
            str+=val.substring(i,i+1);
        }else{ fx=true; }
    }
    if(fx) obj.value=str;
}
function getContSize(bsize,thr){
    return bsize>thr?thr:bsize-40;
}
function tranfont(str,n){
    var a=',1,2,3,4,5,6,7,8,9,0,a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,-,_,';  //7
    var b=',A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,';  //10
    var result='',temp='',m=0;
    for(var i=0;i<str.length;i++){
        temp=','+str.substring(i,i+1)+',';
        if(a.indexOf(temp)>-1){
            m+=7;
        }else if(b.indexOf(temp)>-1){
            m+=10;
        }else{
            m+=12;
        }
        if(m<n-15){
            result+=str.substring(i,i+1);
        }else{
            result+='..';
            break;
        }
    }
    return result;
}
//功能授权
/* 参数： */
/* 1)userid：账号ID */
/* 2)purview：验证功能的权限码集合，可同时验证多个功能，格式如：1002,1003,1004 */
/* 3)dis：鉴权按钮的ID集，数量同等于purview，格式如：btn1,btn2,btn3 */
function operatePurview(userid,purview,dis){
    if(Number(userid)>0&&purview.length>0&&dis.length>0){
        $.ajax({
            type: 'GET',
            url: '../../JAjax/GetPurviewAjax.aspx',
            data: 'fun=1&userid='+userid+'&purview='+purview+'&ram='+new Date().getTime(),
            success: function(msg){
                if(msg.length>0){
                    for(var i=0;i<msg.split(',').length;i++){
                        if(msg.split(',')[i]=='0'){
                            $('#'+dis.split(',')[i]).attr('disabled','disabled');
                            $('#td_'+dis.split(',')[i]).hide();
                        }
                    }
                }
            }
        });
    }
}
//判断经纬度是否合法
function isXYVisible(x){
    var flag = false;
    if(x != null){
        if(x.toString().length > 0){
            if(Number(x) <= 180 && Number(x) > -180 && Number(x) != 0){
                flag = true;
            }
        }
    }
    return flag;
}

//联想目标列表
/* * * * * 
 * 参数说明：
 * 1) path：相对路径，服务端所在目录 WEBGIS/GET_Server/Object.aspx，其他页面调用要以此目录为标准
 * 2) overLay：下拉列表层控件ID
 * 3) hid：选中ID存储的位置
 * 4) txt：选中名称存储的位置
 * 5) w：宽度
 * 6) h：高度
 * 7) userId：账号ID
 * 8) src：过滤条件依据（1：设备名称，2：所属用户，3：IMEI/ID，4：SIM卡号，5：设备编号）
 * 9) key：关键字
 * * * * * 
 */
function getLikeObjectList(path,overLay,hid,txt,fun,w,h,userId,holdId,src,key){
    $.ajax({
        type: 'GET',
        url: path + 'GET_Server/Object.aspx',
        data: 'fun=2&userId=' + userId + '&holdId=' + holdId + '&src=' + src + '&key=' + escape(key) + '&ram=' + new Date().getTime(),
        success: function(msg) {
            if (msg.length > 0) {
                var list = eval('(' + msg + ')');
                var html = '', sum = list.Object.length, bb = null, trColor = "#FFFFFF", item = "", text = "", l = key.length;
                if (sum > 0) {
                    html = "<div id='overlay' style='width:" + w + "px;height:" + h + "px;'>";
                    html += "<iframe src='" + path + "noting.htm' style='position:absolute;border:0px;visibility:inherit;top:0px;left:0px;width:100%;height:100%;z-index:-1;filter=progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0);'></iframe>";
                    html += "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"liketable\">";
                    for (var i = 0; i < sum; i++) {
                        bb = list.Object[i];

                        //trColor=i%2==0?"#dfe6ea":"#FFFFFF";
                        item = bb.ItemName;
                        text = "";
                        if (l > 0) {
                            for (var j = 0; j < item.length; j++) {
                                if (item.substring(j, j + l) == key) {
                                    text += "<strong style='color:red;'>" + item.substring(j, j + l) + "</strong>"; j = j + l - 1;
                                } else { text += item.substring(j, j + 1); }
                            }
                        } else {
                            text = item;
                        }

                        html += "<tr><td onmouseover=\"this.style.background='#c9ccdc';this.style.cursor='pointer';\" onmouseout=\"this.style.background='#ffffff';\" onclick=\"dlock('" + overLay + "','" + hid + "','" + txt + "','" + fun + "'," + bb.ObjectID + ",'" + bb.ItemName + "')\"><span>" + text + "</span></td></tr>";
                    }
                    html += "</table></div>";
                    $("#" + overLay).html(html);
                    $("#" + overLay).show();
                }
            }
        }
    });
}
function QueryLikeObjectList(path, overLay, hid, txt, fun, w, h, holdId, src, key) {
    switch (Number(src)) {
        case 1:
            //检索设备名/目标名称
            $.ajax({
                type: 'GET',
                url: path + 'JAjax/CacheDataAjax.aspx',
                data: 'fun=1&holdId=' + holdId + '&key=' + escape(key) + '&r=' + new Date().getTime(),
                success: function(xx) {
                    if (xx.length > 0) {
                        var dd = eval('(' + xx + ')');
                        if (dd != null) {
                            var html = "", sum = dd.data.length, trColor = "#FFFFFF", item = "", text = "", l = key.length;
                            if (sum > 0) {
                                html = "<div id='overlay' style='width:" + w + "px;height:" + h + "px;'>";
                                html += "<iframe src='" + path + "noting.htm' style='position:absolute;border:0px;visibility:inherit;top:0px;left:0px;width:100%;height:100%;z-index:-1;filter=progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0);'></iframe>";
                                html += "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"liketable\">";
                                for (var i = 0; i < sum; i++) {
                                    item = dd.data[i].ObjectName;
                                    text = "";
                                    if (l > 0) {
                                        for (var j = 0; j < item.length; j++) {
                                            if (item.substring(j, j + l) == key) {
                                                text += "<strong style='color:red;'>" + item.substring(j, j + l) + "</strong>"; j = j + l - 1;
                                            } else { text += item.substring(j, j + 1); }
                                        }
                                    } else {
                                        text = item;
                                    }

                                    html += "<tr><td onmouseover=\"this.style.background='#c9ccdc';this.style.cursor='pointer';\" onmouseout=\"this.style.background='#ffffff';\" onclick=\"dlock('" + overLay + "','" + hid + "','" + txt + "','" + fun + "'," + dd.data[i].ObjectID + ",'" + dd.data[i].ObjectName + "')\"><span>" + text + "</span></td></tr>";
                                }
                                html += "</table></div>";
                                $("#" + overLay).html(html);
                                $("#" + overLay).show();
                            }
                        }
                    }
                }
            });
            break;
        case 2:
            //检索所属用户名
            $.ajax({
                type: 'GET',
                url: path + 'JAjax/CacheDataAjax.aspx',
                data: 'fun=5&holdId=' + holdId + '&key=' + escape(key) + '&r=' + new Date().getTime(),
                success: function(xx) {
                    if (xx.length > 0) {
                        var dd = eval('(' + xx + ')');
                        if (dd != null) {
                            var html = "", sum = dd.data.length, trColor = "#FFFFFF", item = "", text = "", l = key.length;
                            if (sum > 0) {
                                html = "<div id='overlay' style='width:" + w + "px;height:" + h + "px;'>";
                                html += "<iframe src='" + path + "noting.htm' style='position:absolute;border:0px;visibility:inherit;top:0px;left:0px;width:100%;height:100%;z-index:-1;filter=progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0);'></iframe>";
                                html += "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"liketable\">";
                                for (var i = 0; i < sum; i++) {
                                    item = dd.data[i].HoldName;
                                    text = "";
                                    if (l > 0) {
                                        for (var j = 0; j < item.length; j++) {
                                            if (item.substring(j, j + l) == key) {
                                                text += "<strong style='color:red;'>" + item.substring(j, j + l) + "</strong>"; j = j + l - 1;
                                            } else { text += item.substring(j, j + 1); }
                                        }
                                    } else {
                                        text = item;
                                    }

                                    html += "<tr><td onmouseover=\"this.style.background='#c9ccdc';this.style.cursor='pointer';\" onmouseout=\"this.style.background='#ffffff';\" onclick=\"dlock('" + overLay + "','" + hid + "','" + txt + "','" + fun + "'," + dd.data[i].HoldID + ",'" + dd.data[i].HoldName + "')\"><span>" + text + "</span></td></tr>";
                                }
                                html += "</table></div>";
                                $("#" + overLay).html(html);
                                $("#" + overLay).show();
                            }
                        }
                    }
                }
            });
            break;
        case 3:
            //检索IMEI/ID
            $.ajax({
                type: 'GET',
                url: path + 'JAjax/CacheDataAjax.aspx',
                data: 'fun=2&holdId=' + holdId + '&key=' + escape(key) + '&r=' + new Date().getTime(),
                success: function(xx) {
                    if (xx.length > 0) {
                        var dd = eval('(' + xx + ')');
                        if (dd != null) {
                            var html = "", sum = dd.data.length, trColor = "#FFFFFF", item = "", text = "", l = key.length;
                            if (sum > 0) {
                                html = "<div id='overlay' style='width:" + w + "px;height:" + h + "px;'>";
                                html += "<iframe src='" + path + "noting.htm' style='position:absolute;border:0px;visibility:inherit;top:0px;left:0px;width:100%;height:100%;z-index:-1;filter=progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0);'></iframe>";
                                html += "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"liketable\">";
                                for (var i = 0; i < sum; i++) {
                                    item = dd.data[i].IMEI;
                                    text = "";
                                    if (l > 0) {
                                        for (var j = 0; j < item.length; j++) {
                                            if (item.substring(j, j + l) == key) {
                                                text += "<strong style='color:red;'>" + item.substring(j, j + l) + "</strong>"; j = j + l - 1;
                                            } else { text += item.substring(j, j + 1); }
                                        }
                                    } else {
                                        text = item;
                                    }

                                    html += "<tr><td onmouseover=\"this.style.background='#c9ccdc';this.style.cursor='pointer';\" onmouseout=\"this.style.background='#ffffff';\" onclick=\"dlock('" + overLay + "','" + hid + "','" + txt + "','" + fun + "'," + dd.data[i].ObjectID + ",'" + dd.data[i].IMEI + "')\"><span>" + text + "</span></td></tr>";
                                }
                                html += "</table></div>";
                                $("#" + overLay).html(html);
                                $("#" + overLay).show();
                            }
                        }
                    }
                }
            });
            break;
        case 4:
            //检索SIM卡号
            $.ajax({
                type: 'GET',
                url: path + 'JAjax/CacheDataAjax.aspx',
                data: 'fun=3&holdId=' + holdId + '&key=' + escape(key) + '&r=' + new Date().getTime(),
                success: function(xx) {
                    if (xx.length > 0) {
                        var dd = eval('(' + xx + ')');
                        if (dd != null) {
                            var html = "", sum = dd.data.length, trColor = "#FFFFFF", item = "", text = "", l = key.length;
                            if (sum > 0) {
                                html = "<div id='overlay' style='width:" + w + "px;height:" + h + "px;'>";
                                html += "<iframe src='" + path + "noting.htm' style='position:absolute;border:0px;visibility:inherit;top:0px;left:0px;width:100%;height:100%;z-index:-1;filter=progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0);'></iframe>";
                                html += "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"liketable\">";
                                for (var i = 0; i < sum; i++) {
                                    item = dd.data[i].SIM;
                                    text = "";
                                    if (l > 0) {
                                        for (var j = 0; j < item.length; j++) {
                                            if (item.substring(j, j + l) == key) {
                                                text += "<strong style='color:red;'>" + item.substring(j, j + l) + "</strong>"; j = j + l - 1;
                                            } else { text += item.substring(j, j + 1); }
                                        }
                                    } else {
                                        text = item;
                                    }

                                    html += "<tr><td onmouseover=\"this.style.background='#c9ccdc';this.style.cursor='pointer';\" onmouseout=\"this.style.background='#ffffff';\" onclick=\"dlock('" + overLay + "','" + hid + "','" + txt + "','" + fun + "'," + dd.data[i].ObjectID + ",'" + dd.data[i].SIM + "')\"><span>" + text + "</span></td></tr>";
                                }
                                html += "</table></div>";
                                $("#" + overLay).html(html);
                                $("#" + overLay).show();
                            }
                        }
                    }
                }
            });
            break;
        case 5:
            //检索设备编号
            $.ajax({
                type: 'GET',
                url: path + 'JAjax/CacheDataAjax.aspx',
                data: 'fun=4&holdId=' + holdId + '&key=' + escape(key) + '&r=' + new Date().getTime(),
                success: function(xx) {
                    if (xx.length > 0) {
                        var dd = eval('(' + xx + ')');
                        if (dd != null) {
                            var html = "", sum = dd.data.length, bb = null, trColor = "#FFFFFF", item = "", text = "", l = key.length;
                            if (sum > 0) {
                                html = "<div id='overlay' style='width:" + w + "px;height:" + h + "px;'>";
                                html += "<iframe src='" + path + "noting.htm' style='position:absolute;border:0px;visibility:inherit;top:0px;left:0px;width:100%;height:100%;z-index:-1;filter=progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0);'></iframe>";
                                html += "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"liketable\">";
                                for (var i = 0; i < sum; i++) {
                                    item = dd.data[i].ObjectCode;
                                    text = "";
                                    if (l > 0) {
                                        for (var j = 0; j < item.length; j++) {
                                            if (item.substring(j, j + l) == key) {
                                                text += "<strong style='color:red;'>" + item.substring(j, j + l) + "</strong>"; j = j + l - 1;
                                            } else { text += item.substring(j, j + 1); }
                                        }
                                    } else {
                                        text = item;
                                    }

                                    html += "<tr><td onmouseover=\"this.style.background='#c9ccdc';this.style.cursor='pointer';\" onmouseout=\"this.style.background='#ffffff';\" onclick=\"dlock('" + overLay + "','" + hid + "','" + txt + "','" + fun + "'," + dd.data[i].ObjectID + ",'" + dd.data[i].ObjectCode + "')\"><span>" + text + "</span></td></tr>";
                                }
                                html += "</table></div>";
                                $("#" + overLay).html(html);
                                $("#" + overLay).show();
                            }
                        }
                    }
                }
            });
            break;
        case 6:
            //检索帐号
            $.ajax({
                type: 'GET',
                url: path + 'JAjax/CacheDataAjax.aspx',
                data: 'fun=6&holdId=' + holdId + '&key=' + escape(key) + '&r=' + new Date().getTime(),
                success: function(xx) {
                    if (xx.length > 0) {
                        var dd = eval('(' + xx + ')');
                        if (dd != null) {
                            var html = "", sum = dd.data.length, bb = null, trColor = "#FFFFFF", item = "", text = "", l = key.length;
                            if (sum > 0) {
                                html = "<div id='overlay' style='width:" + w + "px;height:" + h + "px;'>";
                                html += "<iframe src='" + path + "noting.htm' style='position:absolute;border:0px;visibility:inherit;top:0px;left:0px;width:100%;height:100%;z-index:-1;filter=progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0);'></iframe>";
                                html += "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"liketable\">";
                                for (var i = 0; i < sum; i++) {
                                    item = dd.data[i].UserName;
                                    text = "";
                                    if (l > 0) {
                                        for (var j = 0; j < item.length; j++) {
                                            if (item.substring(j, j + l) == key) {
                                                text += "<strong style='color:red;'>" + item.substring(j, j + l) + "</strong>"; j = j + l - 1;
                                            } else { text += item.substring(j, j + 1); }
                                        }
                                    } else {
                                        text = item;
                                    }

                                    html += "<tr><td onmouseover=\"this.style.background='#c9ccdc';this.style.cursor='pointer';\" onmouseout=\"this.style.background='#ffffff';\" onclick=\"dlock('" + overLay + "','" + hid + "','" + txt + "','" + fun + "'," + dd.data[i].HoldID + ",'" + dd.data[i].UserName + "')\"><span>" + text + "</span></td></tr>";
                                }
                                html += "</table></div>";
                                $("#" + overLay).html(html);
                                $("#" + overLay).show();
                            }
                        }
                    }
                }
            });
            break;
        default:
            break;
    }
}
function dlock(overLay,hid,txt,fun,id,text){
    $("#"+hid).val(id);
    $("#"+txt).val(text);
    $("#"+overLay).hide();
    setTimeout(fun,200);
}
//联想用户列表
/* * * * * 
 * 参数说明：
 * 1) path：相对路径，服务端所在目录 WEBGIS/GET_Server/Object.aspx，其他页面调用要以此目录为标准
 * 2) overLay：下拉列表层控件ID
 * 3) hid：选中ID存储的位置
 * 4) txt：选中名称存储的位置
 * 5) w：宽度
 * 6) h：高度
 * 7) holdId：用户ID
 * 8) key：关键字
 * * * * * 
 */
function getLikeHoldList(path,overLay,hid,txt,fun,w,h,holdId,key){
    $.ajax({
        type: 'GET',
        url: path + 'GET_Server/Object.aspx',
        data: 'fun=3&holdId=' + holdId + '&key=' + escape(key) + '&ram=' + new Date().getTime(),
        success: function(msg) {
            if (msg.length > 0) {
                var list = eval('(' + msg + ')');
                var html = '', sum = list.Hold.length, bb = null, trColor = "#FFFFFF", item = "", text = "", l = key.length;
                if (sum > 0) {
                    html = "<div id='overlay' style='width:" + w + "px;height:" + h + "px;'>";
                    html += "<iframe src='" + path + "noting.htm' style='position:absolute;border:0px;visibility:inherit;top:0px;left:0px;width:100%;height:100%;z-index:-1;filter=progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0);'></iframe>";
                    html += "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"liketable\">";
                    for (var i = 0; i < sum; i++) {
                        bb = list.Hold[i];

                        //trColor=i%2==0?"#dfe6ea":"#FFFFFF";
                        item = bb.HoldName;
                        text = "";
                        if (l > 0) {
                            for (var j = 0; j < item.length; j++) {
                                if (item.substring(j, j + l) == key) {
                                    text += "<strong style='color:red;'>" + item.substring(j, j + l) + "</strong>"; j = j + l - 1;
                                } else { text += item.substring(j, j + 1); }
                            }
                        } else {
                            text = item;
                        }

                        html += "<tr><td onmouseover=\"this.style.background='#c9ccdc';this.style.cursor='pointer';\" onmouseout=\"this.style.background='#ffffff';\" onclick=\"dlock('" + overLay + "','" + hid + "','" + txt + "','" + fun + "'," + bb.HoldID + ",'" + bb.HoldName + "')\"><span>" + text + "</span></td></tr>";
                    }
                    html += "</table></div>";
                    $("#" + overLay).html(html);
                    $("#" + overLay).show();
                }
            }
        }
    });
}

function getLikeHoldListHover(path, overLay, hid, txt, fun, w, h, holdId, key) {
    $.ajax({
        type: 'GET',
        url: path + 'GET_Server/Object.aspx',
        data: 'fun=3&holdId=' + holdId + '&key=' + escape(key) + '&ram=' + new Date().getTime(),
        success: function(msg) {
            if (msg.length > 0) {
                var list = eval('(' + msg + ')');
                var html = '', sum = list.Hold.length, bb = null, trColor = "#FFFFFF", item = "", text = "", l = key.length;
                if (sum > 0) {
                    html = "<div id='overlay' style='width:" + w + "px;height:" + h + "px;'>";
                    html += "<iframe src='" + path + "noting.htm' class='ifrbak'></iframe>";
                    html += "<div id=\"keyLikeLayout\">";
                    for (var i = 0; i < sum; i++) {
                        bb = list.Hold[i];

                        //trColor=i%2==0?"#dfe6ea":"#FFFFFF";
                        item = bb.HoldName;
                        text = "";
                        if (l > 0) {
                            for (var j = 0; j < item.length; j++) {
                                if (item.substring(j, j + l) == key) {
                                    text += "<strong style='color:red;'>" + item.substring(j, j + l) + "</strong>"; j = j + l - 1;
                                } else { text += item.substring(j, j + 1); }
                            }
                        } else {
                            text = item;
                        }

                        html += "<ul>";
                        html += "<li onclick=\"dlock('" + overLay + "','" + hid + "','" + txt + "','" + fun + "'," + bb.HoldID + ",'" + bb.HoldName + "')\"><span>" + text + "</span></li>";
                        html += "</ul>";
                    }
                    html += "</div>";
                    html += "</div>";
                    $("#" + overLay).html(html);
                    $("#" + overLay).show();
                }
            }
        }
    });
}
var _hover_index = 0, _hover_flag = false;
/*
document.onkeydown = function() {
    if (_hover_flag) {
        if (event.keyCode == 38) {
            // 按了向上箭头
            if (_hover_index > 0)
                _hover_index--;
        } else if (event.keyCode == 40) {
            // 按了向下箭头
            _hover_index++;
            if (!$("#keyLikeLayout ul:eq(" + _hover_index + ")")[0]) {
                _hover_index--;
            }
        }

        if (event.keyCode == 38 || event.keyCode == 40) {
            if ($("#keyLikeLayout ul li:eq(" + _hover_index + ")")[0]) {
                $("#keyLikeLayout ul li:eq(" + _hover_index + ")").css("background-color", "#c9ccdc");
            }
        }
    }
}
*/
/*
function movermap(obb) {
    obb.style.background = "#c9ccdc";
    obb.style.cursor = "pointer";
}

function moutmap() {
    obb.style.background = "#ffffff";
}
*/

//联想客户信息列表
/* * * * * 
 * 参数说明：
 * 1) path：相对路径，服务端所在目录 CRM/GET_Server/CustomerList.aspx，其他页面调用要以此目录为标准
 * 2) overLay：下拉列表层控件ID
 * 3) hid：选中ID存储的位置
 * 4) txt：选中名称存储的位置
 * 5) w：宽度
 * 6) h：高度
 * 7) holdId：用户ID
 * 8) key：关键字
 * * * * * 
 */
function getLikeCustomerList(path,overLay,hid,txt,fun,w,h,holdId,key){
    $.ajax({
        type: 'GET',
        url: path+'GET_Server/CustomerList.aspx',
        data: 'fun=1&holdId='+holdId+'&key='+escape(key)+'&ram='+new Date().getTime(),
        success: function(msg){
            if(msg.length>0){
                var list=eval('('+msg+')');
                var html='',sum=list.CustomerInfo.length,bb=null,trColor="#FFFFFF",item="",text="",l=key.length;
                if(sum>0){
                    html="<div id='overlay' style='width:"+w+"px;height:"+h+"px;'>";
                    html+="<iframe src='../../WEBGIS/noting.htm' style='position:absolute;border:0px;visibility:inherit;top:0px;left:0px;width:100%;height:100%;z-index:-999;filter=progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0);'></iframe>";
                    html+="<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"liketable\">";
                    for(var i=0;i<sum;i++){
                        bb=list.CustomerInfo[i];
                        
                        //trColor=i%2==0?"#dfe6ea":"#FFFFFF";
                        item=bb.CustomerName;
                        text="";
                        if(l>0){
                            for(var j=0;j<item.length;j++){
                                if(item.substring(j,j+l)==key){
                                    text+="<strong style='color:red;'>"+item.substring(j,j+l)+"</strong>"; j=j+l-1;
                                }else{ text+=item.substring(j,j+1); }
                            }
                        }else{
                            text=item;
                        }
                        
                        html+="<tr><td onmouseover=\"this.style.background='#c9ccdc';this.style.cursor='pointer';\" onmouseout=\"this.style.background='#ffffff';\" onclick=\"dlock('"+overLay+"','"+hid+"','"+txt+"','"+fun+"',"+bb.CustomerID+",'"+bb.CustomerName+"')\"><span>"+text+"</span></td></tr>";
                    }
                    html+="</table></div>";
                    $("#"+overLay).html(html);
                    $("#"+overLay).show();
                }
            }
        }
    });
}
function over1(id){
    $("#"+id).css("background-color","#cde2f0");
}
function out1(id,bgColor){
    $("#"+id).css("background-color",bgColor);
}
//检测字符串是否合法，非法则返回有效的空值
function visibledString(str){
    if(str != 'undefined' && str != null)
        return str;
    return '';
}
//获取控件坐标Top
function getAbsoluteTop(obj){
    var s_el = 0, el = obj;
    while(el){
        s_el = s_el + el.offsetTop;
        el = el.offsetParent;
    };
    return s_el;
}
//获取控件坐标Left
function getAbsoluteLeft(obj){
    var s_el = 0, el = obj;
    while(el){
        s_el = s_el + el.offsetLeft;
        el = el.offsetParent;
    };
    return s_el;
}
/* 
* ok!: MSIE 6, 7, 8, Firefox 3.6, chrome 4, Safari 4, Opera 10 
* o 旋转图片ID; 
* p 选择旋转方向，固定值为'left'或'right'; 
*/ 
function rotateimg(o,p){ 
    var img = document.getElementById(o); 
    if(!img || !p) return false; 
    var n = img.getAttribute('step'); 
    if(n== null) n=0; 
    if(p=='right'){ 
        (n==3)? n=0:n++; 
    }else if(p=='left'){ 
        (n==0)? n=3:n--; 
    } 
    img.setAttribute('step',n); 
    //MSIE 
    if(document.all) { 
        img.style.filter = 'progid:DXImageTransform.Microsoft.BasicImage(rotation='+ n +')'; 
        //HACK FOR MSIE 8 
        switch(n){ 
            case 0: 
                img.parentNode.style.height = img.height; 
                break; 
            case 1: 
                img.parentNode.style.height = img.width; 
                break; 
            case 2: 
                img.parentNode.style.height = img.height; 
                break; 
            case 3: 
                img.parentNode.style.height = img.width; 
                break; 
        } 
    //DOM 
    }else{ 
        var c = document.getElementById('canvas_'+o); 
        if(c== null){ 
            img.style.visibility = 'hidden'; 
            img.style.position = 'absolute'; 
            c = document.createElement('canvas'); 
            c.setAttribute("id",'canvas_'+o); 
            img.parentNode.appendChild(c); 
        } 
        var canvasContext = c.getContext('2d'); 
        switch(n) { 
            default :
            case 0 : 
                c.setAttribute('width', img.width); 
                c.setAttribute('height', img.height); 
                canvasContext.rotate(0 * Math.PI / 180); 
                canvasContext.drawImage(img, 0, 0); 
                break; 
            case 1 : 
                c.setAttribute('width', img.height); 
                c.setAttribute('height', img.width); 
                canvasContext.rotate(90 * Math.PI / 180); 
                canvasContext.drawImage(img, 0, -img.height); 
                break; 
            case 2 : 
                c.setAttribute('width', img.width); 
                c.setAttribute('height', img.height); 
                canvasContext.rotate(180 * Math.PI / 180); 
                canvasContext.drawImage(img, -img.width, -img.height); 
                break; 
            case 3 : 
                c.setAttribute('width', img.height); 
                c.setAttribute('height', img.width); 
                canvasContext.rotate(270 * Math.PI / 180); 
                canvasContext.drawImage(img, -img.width, 0); 
                break; 
        }
    }
}
//验证图片格式是否合法
function visible_imgformat(imgsrc){
    var staff_format = ",.jpg,.JPG,.png,.PNG,.gif,.GIF,.bmp,.BMP,";
    var img_staff = imgsrc.substring(imgsrc.length - 4, imgsrc.length);
    if(staff_format.indexOf(img_staff) > 0){
        return true;
    }else{
        return false;
    }
}
//获取元素绝对位置的横坐标
function getElementLeft(element){
　　var actualLeft = element.offsetLeft;
　　var current = element.offsetParent;
　　while (current !== null){
　　　　actualLeft += current.offsetLeft;
　　　　current = current.offsetParent;
　　}
　　return actualLeft;
}
//获取元素绝对位置的纵坐标
function getElementTop(element){
　　var actualTop = element.offsetTop;
　　var current = element.offsetParent;
　　while (current !== null){
　　　　actualTop += current.offsetTop;
　　　　current = current.offsetParent;
　　}
　　return actualTop;
}
function commafy(num) {

    if ((num + "").replace(/(^\s*)|(\s*$)/g, '') == "") {

        return "";

    }

    if (isNaN(num)) {

        return "";

    }

    num = num + "";

    if (/^.*\..*$/.test(num)) {

        var pointIndex = num.lastIndexOf(".");

        var intPart = num.substring(0, pointIndex);

        var pointPart = num.substring(pointIndex + 1, num.length);

        intPart = intPart + "";

        var re = /(-?\d+)(\d{3})/

        while (re.test(intPart)) {

            intPart = intPart.replace(re, "$1,$2")

        }

        num = intPart + "." + pointPart;

    } else {

        num = num + "";

        var re = /(-?\d+)(\d{3})/

        while (re.test(num)) {

            num = num.replace(re, "$1,$2")

        }

    }

    return num;

}
//保留3位小数--如果为整数则补0--四舍五入
function toDecimal3(x) {
    var f = parseFloat(x);
    if (isNaN(f)) {
        return false;
    }
    var f = Math.round(x * 1000) / 1000;
    var s = f.toString();
    var rs = s.indexOf('.');
    if (rs < 0) {
        rs = s.length;
        s += '.';
    }
    while (s.length <= rs + 3) {
        s += '0';
    }
    return s;
}
//保留2位小数--如果为整数则补0--四舍五入
function toDecimal2(x) {
    var f = parseFloat(x);
    if (isNaN(f)) {
        return false;
    }
    var f = Math.round(x * 100) / 100;
    var s = f.toString();
    var rs = s.indexOf('.');
    if (rs < 0) {
        rs = s.length;
        s += '.';
    }
    while (s.length <= rs + 2) {
        s += '0';
    }
    return s;
}
//保留1位小数--如果为整数则补0--四舍五入
function toDecimal1(x) {
    var f = parseFloat(x);
    if (isNaN(f)) {
        return false;
    }
    var f = Math.round(x * 10) / 10;
    var s = f.toString();
    var rs = s.indexOf('.');
    if (rs < 0) {
        rs = s.length;
        s += '.';
    }
    while (s.length <= rs + 1) {
        s += '0';
    }
    return s;
}

// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
// 例子： 
// (new Date()).Format("yyyy-MM-dd HH:mm:ss.S") ==> 2006-07-02 08:09:04.423 
// (new Date()).Format("yyyy-M-d H:m:s.S")      ==> 2006-7-2 8:9:4.18 
Date.prototype.Format = function (fmt) { //author: meizz 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "H+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

//js数组去重
Array.prototype.Unique = function () {
    var n = {}, r = []; //n为hash表，r为临时数组
    for (var i = 0; i < this.length; i++) //遍历当前数组
    {
        if (!n[this[i]]) //如果hash表中没有当前项
        {
            n[this[i]] = true; //存入hash表
            r.push(this[i]); //把当前数组的当前项push到临时数组里面
        }
    }
    return r;
}
//功能：将浮点数四舍五入，取小数点后3位，如果为整数还是整数，最多保留3位小数
function saveDecimal3(x) {
    var f = parseFloat(x);
    if (isNaN(f)) {
        return;
    }
    f = Math.round(x * 1000) / 1000;
    return f;
}
//功能：将浮点数四舍五入，取小数点后2位，如果为整数还是整数，最多保留2位小数
function saveDecimal2(x) {
    var f = parseFloat(x);
    if (isNaN(f)) {
        return;
    }
    f = Math.round(x * 100) / 100;
    return f;
}
//功能：将浮点数四舍五入，取小数点后1位，如果为整数还是整数，最多保留1位小数
function saveDecimal1(x) {
    var f = parseFloat(x);
    if (isNaN(f)) {
        return;
    }
    f = Math.round(x * 10) / 10;
    return f;
}