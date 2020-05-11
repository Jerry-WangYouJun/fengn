<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="http://static.runoob.com/assets/jquery/2.0.3/jquery.min.js"></script>
<script type="text/javascript" src="http://static.runoob.com/assets/qrcode/qrcode.min.js"></script>
<title>Insert title here</title>
</head>
<body>
<h1>当前绑定账号：<img alt="" src="${logUser.headimg}" style="height: 50px;width: 50px">${logUser.wxName }</h1>
<h3 style="color:red">扫描下方二维码，可修改绑定返佣进账的微信！请通过以上微信号确认未出现盗绑情况！！！</h3>
<h3>扫描更换微信后，请刷新页面内容</h3>
<input id="text" type="hidden" value="http://iot.iot10.cn/wx/init?userid=${userid}" style="width:80%" /><br />
<div id="qrcode" style="width:500px; height:500px; margin-top:15px;"></div>

<script type="text/javascript">
var qrcode = new QRCode(document.getElementById("qrcode"), {
	width : 400,
	height : 400
});

function makeCode () {		
	var elText = document.getElementById("text");
	
	if (!elText.value) {
		alert("Input a text");
		elText.focus();
		return;
	}
	
	qrcode.makeCode(elText.value);
}

makeCode();

$("#text").
	on("blur", function () {
		makeCode();
	}).
	on("keydown", function (e) {
		if (e.keyCode == 13) {
			makeCode();
		}
	});
</script>
	
</body>
</html>