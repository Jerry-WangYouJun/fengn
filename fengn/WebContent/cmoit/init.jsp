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
<input id="text" type="hidden" value="http://iot.iot10.cn/wx/init?userid=${userid}" style="width:80%" /><br />
<div id="qrcode" style="width:500px; height:500px; margin-top:15px;"></div>

<script type="text/javascript">
var qrcode = new QRCode(document.getElementById("qrcode"), {
	width : 500,
	height : 500
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