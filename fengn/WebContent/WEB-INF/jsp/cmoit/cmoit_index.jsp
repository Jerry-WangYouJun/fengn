<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
        <meta charset="utf-8">
        <title> 物联卡管理系统</title>
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
        <meta name="author" content="SuggeElson" />
        <meta name="description" content=""/>
        <meta name="keywords" content=""/>
        <meta name="application-name" content="sprFlat admin template" />
        <link rel='stylesheet' type='text/css' />
        <link href="${pageContext.request.contextPath}/assets/css/icons.css" rel="stylesheet" />
        <link href="${pageContext.request.contextPath}/assets/css/sprflat-theme/jquery.ui.all.css" rel="stylesheet" />
        <link href="${pageContext.request.contextPath}/assets/css/bootstrap.css" rel="stylesheet" />
        <link href="${pageContext.request.contextPath}/assets/css/plugins.css" rel="stylesheet" />
        <link href="${pageContext.request.contextPath}/assets/css/main.css" rel="stylesheet" />
        <link href="${pageContext.request.contextPath}/assets/css/custom.css" rel="stylesheet" />
        <link rel="apple-touch-icon-precomposed" sizes="144x144" href="${pageContext.request.contextPath}/assets/img/ico/apple-touch-icon-144-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="114x114" href="${pageContext.request.contextPath}/assets/img/ico/apple-touch-icon-114-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="72x72" href="${pageContext.request.contextPath}/assets/img/ico/apple-touch-icon-72-precomposed.png">
        <link rel="apple-touch-icon-precomposed" href="${pageContext.request.contextPath}/assets/img/ico/apple-touch-icon-57-precomposed.png">
        <link rel="icon" href="${pageContext.request.contextPath}/assets/img/ico/favicon.ico" type="image/png">
           <link href="${pageContext.request.contextPath}/css/bootstrap-datetimepicker.min.css" rel="stylesheet" />  
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-theme.min.css">
        <script src="${pageContext.request.contextPath}/assets/js/jquery-1.8.3.min.js"></script>
        <script type="text/JavaScript" src="${pageContext.request.contextPath}/js/jquery.form.js"></script>
        
        <meta name="msapplication-TileColor" content="#3399cc" />
        <script type="text/javascript">
        function openSub(obj){
        	var _this = $(obj).parent();
            if(_this.hasClass('notExpand')) {
              //  e.preventDefault();
                //expand ul and change class to expand
                _this.next('ul').slideDown("slow");
                _this.next('ul').addClass('show');
                _this.removeClass('notExpand').addClass('expand');
                $(".active").removeClass("active");
                _this.addClass('active');
                //if(plugin.settings.sideNav.showArrows) {
                    _this.find('.sideNav-arrow').removeClass("im-plus").removeClass("sideNav-arrow").addClass("im-minus sideNav-arrow");
               // }                   
            } else if (_this.hasClass('expand')) {
              //  e.preventDefault();
                //collapse ul and change class to notExpand
                _this.next('ul').removeClass('show');
                _this.next('ul').slideUp("slow");
                _this.removeClass('expand').addClass('notExpand');
                //if(plugin.settings.sideNav.showArrows) {
                     _this.find('.sideNav-arrow').removeClass("im-minus").removeClass("sideNav-arrow").addClass("im-plus sideNav-arrow");
                //}
            }
        }
        
 		function getTreeData(datas){
 			var htmlStr = '' ;
 			var index = 0 ;
 			for(var childNode in datas){
 				if(datas[childNode].children.length>0){
 					  htmlStr += '<li><a class="notExpand" ' 
 					  + getTabData(datas[childNode])
 					  + ' href="javascript:;">' + datas[childNode].menu
 					  + ' <i class="im-plus sideNav-arrow" onclick="openSub(this)" ></i></a>'
 					  +'<ul  class="nav sub">' +  getTreeData(datas[childNode].children)
 					  +'</ul></li>';
 					 index ++ ;
 				}else{
 					 htmlStr += '<li><a ' 
 					  + getTabData(datas[childNode])
 					  + ' href="javascript:;" >' + datas[childNode].menu
 					  + '</a></li>';
 				}
 			  }
 			return htmlStr ;
 		}
 		
 		function getTabData(node){
 			return 'onclick="addTab(\''+node.attributes.urlType +'_' + node.attributes.agentId + '\',\''
 			  +   node.attributes.priUrl + '\',\'' + node.text +'\' , this)"'
 		}
 		
 		function getMenu(type,name){
 			$.ajax( {
 				url : "${pageContext.request.contextPath}/treeindex/"+type+"/" + name,
 				type : 'post',
 				dataType : 'json',
 				success : function(data) {
 					 var htmlStr = getTreeData(data[0].children);
 					 $("#" + name).html(htmlStr);
 				},
 				error : function(transport) {
 					$.messager.alert('提示', "系统产生错误,请联系管理员!", "error");
 				}
 			});
 		}
 		
 		function updatePwd(){
 			var pwd = prompt("请输入新密码")
 			var pwd2 = prompt("请再次输入密码");
 			if(pwd != pwd2 ){
 				  alert("两次密码不一致，请重新确认")
 				  return false;
 			}
 		
 			if(pwd == '' || pwd == null){
 				 alert("密码不能为空")
 				 return false;
 			}
 			$.ajax( {
 				url : "${pageContext.request.contextPath}/cmoit/user/pwd" ,
 				type : 'post',
 				dataType : 'text',
 				data:{pwd:pwd},
 				success : function(data) {
 					alert(data)
 				},
 				error : function(transport) {
 					$.messager.alert('提示', "系统产生错误,请联系管理员!", "error");
 				}
 			});
 		}
 		$(function(){
 				//getMenu("card");
 				//getMenu("kickback");
 				getMenu('cmoit',"cmoit_card");
 				getMenu('cmoit',"cmoit_kickback");
 				getMenu('pages',"cmcc_card");
 				getMenu('pages',"cmcc_kickback");
 				getMyPackageMenu();
           // getMenu(''"all_kickback");
 			
 		});
 		
 		function upload() {
 			var path = "${pageContext.request.contextPath}/uploadExcel/uploadInit"  ;
 			document.getElementById('frameContent').src = path;
 			$('#dlg-frame').dialog('open');
 		}
 		
 		function uploadBindData(table){
 			$.ajax( {
 				url : "${pageContext.request.contextPath}/unicomUpload/"+ table +"_update" ,
 				type : 'post',
 				dataType : 'json',
 				success : function(data) {
 					alert(data.msg);
 				},
 				error : function(transport) {
 					alert("系统有误，请重试或联系管理员");
 				}
 			});
 		}
 		
        </script>
    </head>
<body>
        <!-- Start #header -->
        <div id="header">
            <div class="container-fluid">
                <div class="navbar">
                    <div class="navbar-header">
                        <a class="navbar-brand" href="index.html">
                            <i class="im-windows8 text-logo-element animated bounceIn"></i><span class="text-logo"> 物联卡管理系统</span><span class="text-slogan"></span> 
                        </a>
                    </div>
                    <nav class="top-nav" role="navigation">
                        <ul class="nav navbar-nav pull-left">
                            <li id="toggle-sidebar-li">
                                <a href="#" style="padding: 12px" id="toggle-sidebar"><i class="en-arrow-left2"></i>
                        		</a>
                            </li>
                        </ul>
                        <ul class="nav navbar-nav pull-right">
                         <c:if test="${roleid eq '1' }">
	                      	  <li class="dropdown">
	                                <a href="#" style="padding: 12px" data-toggle="dropdown"><i class="im-paste">&nbsp;导入/更新</i></a>
	                                <ul class="dropdown-menu right" role="menu">
	                                    <li><a href="#" onclick="uploadMlbData('cmoit')"><i class="im-upload2"></i> 平度移动导入</a>
	                                    </li>
	                                     <li><a href="#" onclick="uploadMlbData('cmcc')"><i class="im-upload2"></i> 麦联宝移动导入</a>
	                                    </li>
	                                </ul>
	                            </li>
                         </c:if>
                            <li class="dropdown">
                                <a href="#" style="padding: 12px" data-toggle="dropdown"><i class="st-settings"></i></a>
                                <ul class="dropdown-menu right" role="menu">
                                    <li><a href="${pageContext.request.contextPath}/cmoit/user/loginOut"><i class="im-exit"></i>注销用户</a>
                                    </li>
                                    <li><a href="####" onclick="updatePwd()">修改密码</a>
                                    </li>
                                </ul>
                            </li>
                            <li class="dropdown">
                                <a href="#" data-toggle="dropdown">
                                    <img class="user-avatar" src="${pageContext.request.contextPath}/assets/img/avatars/48.jpg" alt="SuggeElson">${sessionScope.user}</a>
                            </li>
                        </ul>
                    </nav>
                </div>
                <!-- Start #header-area -->
                <!-- End #header-area -->
            </div>
            <!-- Start .header-inner -->
        </div>
        <!-- End #header -->
        <!-- Start #sidebar -->
        <div id="sidebar">
            <!-- Start .sidebar-inner -->
            <div class="sidebar-inner">
                <!-- Start #sideNav -->
                <ul id="sideNav" class="nav nav-pills nav-stacked">
                    <li>
                        <a href="#"> 用户管理 <i class="im-paragraph-justify"></i></a>
                        <ul class="nav sub">
                            <li>
                            	<a href="#" class="ec-users" onclick='addTab("user","${pageContext.request.contextPath}/pages/user_list.jsp","代理商管理")'>
								代理商管理</a>
                            </li>
                             <c:if test="${roleid eq '1' }"><!-- roleid eq '1'  -->
	                            <li><a href="#" class="st-bag" onclick='addTab("pac","${pageContext.request.contextPath}/pages/pac_list.jsp","套餐管理")'>
								管理套餐</a>
	                            </li>
                            </c:if>
                            <c:if test="${roleid ne '1' }"><!-- roleid eq '1'  -->
                            <li><a href="#" class="st-bag" onclick='addTab("packageMaintion","${pageContext.request.contextPath}/pages/pac_maintion.jsp","我的套餐")'>
                              	  我的套餐</a>
                            </li>
                            </c:if>
                            <li>
                            	<a href="#" class="ec-users" onclick='addTab("packageSon","${pageContext.request.contextPath}/pages/pac_sonlist.jsp","下级套餐")'>
								维护下级套餐</a>
                            </li>
                        </ul>
                    </li>
                    <!-- <li><a href="#">联通物联卡 <i class="fa-file-text"></i></a>
                        <ul class="nav sub" id="unicom_card">
						</ul>
                    </li> -->
                    <li><a href="#">老平台物联卡 <i class="fa-file-text"></i></a>
                        <ul class="nav sub" id="cmoit_card">
						</ul>
                    </li>
                     <li><a href="#">新平台物联卡 <i class="fa-file-text"></i></a>
                        <ul class="nav sub" id="cmcc_card">
						</ul>
                    </li>
                    <!-- <li>
                        <a href="#"> 续费明细 (新)<i class="im-paragraph-justify" ></i></a>
                        <ul class="nav sub" id="all_kickback">
                        </ul>
                    </li> -->
                     <li>
                        <a href="#"> 老平台返佣<i class="im-paragraph-justify" ></i></a>
                        <ul class="nav sub" id="cmoit_kickback">
                        </ul>
                    </li>
                    <li><a href="#"> 新平台返佣 <i class="fa-money"></i></a>
		                        <ul class="nav sub" id="cmcc_kickback">
								</ul>
		                    </li>
                     <li><a href="#" class="st-bag" onclick='addTab("packageMa","${pageContext.request.contextPath}/cmoit/init.jsp","维护套餐")'>
                               		 绑定返佣微信号</a>
                     </li>
                </ul>
            </div>
            <!-- End .sidebar-inner -->
        </div>
        <!-- End #sidebar -->
        <!-- Start #right-sidebar -->
        <!-- End #right-sidebar -->
        <!-- Start #content -->
        <div id="content">
            <!-- Start .content-wrapper -->
            <div class="content-wrapper">
                <!-- End .row -->
                <div class="outlet">
                		<div class="row">	  
                					<ul class="nav nav-tabs" style="margin:0px" role="tablist" id="deviceulid">
									
									</ul>
									<!-- 面板区 -->
									<div  id ="home" class="tab-content">
									
									</div>
                		</div>
                </div>
                <!-- End .outlet -->
            </div>
            <!-- End .content-wrapper -->
            <div class="clearfix"></div>
        </div>
        <script src="${pageContext.request.contextPath}/assets/plugins/core/pace/pace.min.js"></script>
        <script>
        window.jQuery || document.write('<script src="${pageContext.request.contextPath}/assets/js/libs/jquery-2.1.1.min.js">\x3C/script>')
        </script>
        <script src="${pageContext.request.contextPath}/assets/js/jquery-ui.js"></script>
        <script>
        window.jQuery || document.write('<script src="${pageContext.request.contextPath}/assets/js/libs/jquery-ui-1.10.4.min.js">\x3C/script>')
        </script>
        <script src="${pageContext.request.contextPath}/assets/js/bootstrap/bootstrap.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/jRespond.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/plugins/core/slimscroll/jquery.slimscroll.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/plugins/core/slimscroll/jquery.slimscroll.horizontal.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/plugins/forms/autosize/jquery.autosize.js"></script>
        <script src="${pageContext.request.contextPath}/assets/plugins/core/quicksearch/jquery.quicksearch.js"></script>
        <script src="${pageContext.request.contextPath}/assets/plugins/ui/bootbox/bootbox.js"></script>
        <script src="${pageContext.request.contextPath}/assets/plugins/charts/flot/jquery.flot.js"></script>
        <script src="${pageContext.request.contextPath}/assets/plugins/charts/flot/jquery.flot.pie.js"></script>
        <script src="${pageContext.request.contextPath}/assets/plugins/charts/flot/jquery.flot.resize.js"></script>
        <script src="${pageContext.request.contextPath}/assets/plugins/charts/flot/jquery.flot.time.js"></script>
        <script src="${pageContext.request.contextPath}/assets/plugins/charts/flot/jquery.flot.growraf.js"></script>
        <script src="${pageContext.request.contextPath}/assets/plugins/charts/flot/jquery.flot.categories.js"></script>
        <script src="${pageContext.request.contextPath}/assets/plugins/charts/flot/jquery.flot.stack.js"></script>
        <script src="${pageContext.request.contextPath}/assets/plugins/charts/flot/jquery.flot.tooltip.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/plugins/charts/flot/date.js"></script>
        <script src="${pageContext.request.contextPath}/assets/plugins/charts/sparklines/jquery.sparkline.js"></script>
        <script src="${pageContext.request.contextPath}/assets/plugins/charts/pie-chart/jquery.easy-pie-chart.js"></script>
        <script src="${pageContext.request.contextPath}/assets/plugins/forms/icheck/jquery.icheck.js"></script>
        <script src="${pageContext.request.contextPath}/assets/plugins/forms/tags/jquery.tagsinput.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/plugins/forms/tinymce/tinymce.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/plugins/misc/highlight/highlight.pack.js"></script>
        <script src="${pageContext.request.contextPath}/assets/plugins/misc/countTo/jquery.countTo.js"></script>
        <script src="${pageContext.request.contextPath}/assets/plugins/ui/weather/skyicons.js"></script>
        <script src="${pageContext.request.contextPath}/assets/plugins/ui/notify/jquery.gritter.js"></script>
        <script src="${pageContext.request.contextPath}/assets/plugins/ui/calendar/fullcalendar.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/jquery.sprFlat.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/app.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/pages/dashboard.js"></script>
    
		<script src="${pageContext.request.contextPath}/js/moment-with-locales.js"></script>  
		<script src="${pageContext.request.contextPath}/js/bootstrap-datetimepicker.min.js"></script> 
		<script src="${pageContext.request.contextPath}/js/bootstrap-datetimepicker.zh-CN.js"></script> 
        
        <script>
		/**
         * 增加标签页
         */
        function addTab(  tabName ,tabUrl , title , obj) {
			$(".active").removeClass("active");
			$(obj).addClass("active");
            //option:
            //tabMainName:tab标签页所在的容器
            //tabName:当前tab的名称
            //tabTitle:当前tab的标题
            //tabUrl:当前tab所指向的URL地址
            var exists = checkTabIsExists('deviceulid', tabName);
            if(exists){
                $("#tab_a_"+tabName).click();
            } else {
                $("#deviceulid").append('<li id="tab_li_'+tabName+'"><a href="#tab_content_'+tabName+'" data-toggle="tab" id="tab_a_'+tabName+'" >'+title+'&nbsp;<span class="fa-remove" type="button" onclick="closeTab(this);"></span></a></li>');
                //固定TAB中IFRAME高度
                mainHeight = $(document.body).height() - 5;
                var content = '';
                if(content){
                    content = option.content;
                } else {
                    content = '<iframe id="myframe_'+tabName+'" src="' + tabUrl + '" width="100%"  height="780px"  frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="yes" allowtransparency="yes"></iframe>';
                }
                $("#home").append('<div id="tab_content_'+tabName+'" role="tabpanel" class="tab-pane" id="'+ tabName+'">'+content+'</div>');
                $("#tab_a_"+tabName).click();
            }
        }
         function toggleTab(obj){
      		   $("#tab_content_"+obj).click(function (e) {
      			   e.preventDefault();
      			   $("#tab_a_" + obj).tab('show');
         		});
         }
        
         
        /**
         * 关闭标签页
         * @param button
         */
        function closeTab (button) {
             
            //通过该button找到对应li标签的id
            var li_id = $(button).parent().parent().attr('id');
            var id = li_id.replace("tab_li_","");
             
            //如果关闭的是当前激活的TAB，激活他的前一个TAB
            if ($("li.active").attr('id') == li_id) {
                $("li.active").prev().find("a").click();
            }
             
            //关闭TAB
            $("#" + li_id).remove();
            $("#tab_content_" + id).remove();
        };
         
        /**
         * 判断是否存在指定的标签页
         * @param tabMainName
         * @param tabName
         * @returns {Boolean}
         */
        function checkTabIsExists(tabMainName, tabName){
            var tab = $("#"+tabMainName+" > #tab_li_"+tabName);
            //console.log(tab.length)
            return tab.length > 0;
        }

         function getMyPackageMenu(){
  	        var html = "";
  	        $.ajax({
  	            url : '${pageContext.request.contextPath}/pac/getPacAll',
  	            type : 'post',
  	            data : {},
  	            dataType : 'json',
  	            success : function(data) {
  	                if (data.success) {
  	                    var dataInfo = data.dataInfo;
  	                    html += "<option value=''>全部套餐</option>";
  	                    for(var i = 0;i<dataInfo.length;i++){
  	                        html+="<option value='"+dataInfo[i].id+"'>"+dataInfo[i].typename+"</option>";
  	                    }
  	                    $("#pacId").html(html);
  	                } else {
  	                    alert("发生异常，请联系管理员");
  	                }
  	            },
  	            error : function(transport) {
  	                alert(data.msg);
  	            }
  	        });
  	    }
	</script>
	
	<div class="modal fade" id="mlbModal" tabindex="-2" role="dialog"
		aria-labelledby="uploadModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:400px; ">
			<div class="modal-content">
				<div class="modal-body">
					   	<form class="form-signin" role="form" method="POST"
						 id="mlbForm"
						action="${pageContext.request.contextPath}/uploadExcel/upload.do">
						<input type="hidden" name="apiCode" id="apiCode">
						<div class="form-group">
							<label for="message-text" class="control-label" id="timetext">上传文件:</label>
							 <div class='input-group date' id='datetimepicker1'  >  
					                <input type='file' class="form-control" readonly name="upfile"  id="upfile"/>  
					                <span class="input-group-addon" >  
					                    <span class="glyphicon glyphicon-calendar"></span>  
					                </span>  
					            </div>   
						</div>
						<div class="form-group">
							<label for="message-text" class="control-label" >选择套餐:</label>
							<select class="form-control "  id="pacId"  name="pacId" style="display: inline">
								<option value="">全部套餐</option>
							</select>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" id="btn_mlb" class="btn btn-primary" data-dismiss="modal" onclick="">导入数据</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
	</div>
	
    </body>
    <script type="text/javascript">
	
	function uploadData() {
		$("#uploadModal").modal("show");
	}
	
	//ajax 方式上传文件操作
	$(document).ready(function() {
		$('#btn_mlb').click(function() {
			//if (checkData()) {
				$('#mlbForm').ajaxSubmit({
					url : '${pageContext.request.contextPath}/cmoit/info/ajaxUpload',
					dataType : 'text',
					success : resutlMsg,
					error : errorMsg
				});
				function resutlMsg(msg) {
					alert(msg);
					parent.$('#dlg-frame').dialog("close");
					//window.location.href = "${pageContext.request.contextPath}/uploadExcel/dataList.do?dateBegin=&dateEnd=&status=";
					$("#upfile").val("");
				}
				function errorMsg() {
					alert("导入excel出错！");
				}
			//}
		});
		
		addTab("packageMa","${pageContext.request.contextPath}/cmoit/init.jsp","当前绑定微信");
	});
	

	function uploadUnicomData() {
		$("#unicomModal").modal("show");
	}
	
	//ajax 方式上传文件操作
	$(document).ready(function() {
		$('#btn_insert').click(function() {
				$('#unicomForm').ajaxSubmit({
					url : '${pageContext.request.contextPath}/unicomUpload/uploadExcelUnicom?act=insert',
					dataType : 'text',
					success : resutlMsg,
					error : errorMsg
				});
				function resutlMsg(msg) {
					alert(msg);
					parent.$('#dlg-frame').dialog('close');
					//window.location.href = "${pageContext.request.contextPath}/uploadExcel/dataList.do?dateBegin=&dateEnd=&status=";
					$("#upfile").val("");
				}
				function errorMsg() {
					alert("导入excel出错！");
				}
		});
		$('#btn_update').click(function() {
			$('#unicomForm').ajaxSubmit({
				url : '${pageContext.request.contextPath}/unicomUpload/uploadExcelUnicom?act=update',
				dataType : 'text',
				success : resutlMsg,
				error : errorMsg
			});
			function resutlMsg(msg) {
				alert(msg);
				parent.$('#dlg-frame').dialog('close');
				$("#upfile").val("");
			}
			function errorMsg() {
				alert("导入excel出错！");
			}
	});
	});
	
	function uploadMlbData(type){
		$("#apiCode").val(type);
		 $("#mlbModal").modal("show");
	}
	
	
</script>
</html>