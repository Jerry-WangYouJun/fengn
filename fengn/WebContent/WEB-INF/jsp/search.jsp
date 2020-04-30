<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>查询ICCID卡号</title>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<link href="${basePath}/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="${basePath}/css/style.css" />
<script src="${basePath}/js/jquery-3.1.1.min.js"></script>
<script src="${basePath}/js/bootstrap.min.js"></script>
<script type="text/javascript">
	wx.config({  
	    debug: false,  
	    appId: '${ret.appId}',  
	    timestamp:'${ret.timestamp}',  
	    nonceStr:'${ret.nonceStr}',  
	    signature:'${ret.signature}',  
	    jsApiList : [ 'scanQRCode' ]  
	});//end_config  
	
	wx.error(function(res) {  
	    alert("出错了：" + res.errMsg);  
	});  
	

    //扫描二维码  
    function scan() {  
        wx.scanQRCode({  
            needResult : 0, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，  
            scanType : [ "qrCode", "barCode" ], // 可以指定扫二维码还是一维码，默认二者都有  
            success : function(res) {  
                var result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果  
                document.getElementById("wm_id").value = result;//将扫描的结果赋予到jsp对应值上  
                alert("扫描成功::扫描码=" + result);  
            }  
        });  
    };//end_document_scanQRCode  
      
</script> 
</head>
<body>
		<div class="container-fluid">
			<div class="row">
				<!-- Tab panes -->
				<form  action = "/cmoit/info/c" method="post">
					<div class="col-lg-12">
						<div class="input-group">
							<input name="iccid"  type="text" class="form-control input"   required>
							<a class="clear" onclick="cleardiv()"></a>
							<span class="input-group-btn">
								<button class="btn btn-default"  onclick="scan()" type="button"><span class="glyphicon glyphicon-camera"></span></button>
							</span>
						</div>
						<!-- /input-group -->
						<button type="submit" class="btn btn-primary btn-lg btn-block" style="border: none;" onclick="window.location.href=''" >查询续费</button>
						<div class="img-box">
							<img src="${basePath}/img/img.png"  onclick="scan()" />
							<h5>点击此处，对着卡的条形码扫一扫</h5>
						</div>
					</div>
				</form>
				<!-- /.col-lg-6 -->
			</div>
			<!-- /.row -->
		</div>

		<script >
			function cleardiv(){
				
				document.getElementById("password").value="";
			}
		</script>
	</body>
</html>