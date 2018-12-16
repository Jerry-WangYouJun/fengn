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
 		
 		function getMenu(name){
 			$.ajax( {
 				url : "${pageContext.request.contextPath}/treeindex/" + name,
 				type : 'post',
 				dataType : 'json',
 				success : function(data) {
                    console.log("----------------------");
 				    console.log(data);
 					 var htmlStr = getTreeData(data[0].children);
 					 $("#" + name).html(htmlStr);
 				},
 				error : function(transport) {
 					$.messager.alert('提示', "系统产生错误,请联系管理员!", "error");
 				}
 			});
 		}
 		$(function(){
 			if("${groupId}"=="1" || "${groupId}"=="3"){
 				getMenu("card");
 				getMenu("kickback");
 				getMenu("cmcc_card");
 				getMenu("cmcc_kickback");
 			}
 			if("${groupId}"=="2" || "${groupId}"=="3"){
 				getMenu("unicom_card");
 				getMenu("unicom_kickback");
 			}
            getMenu("all_kickback");
 			
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
	                                    <li><a href="#" onclick="uploadMlbData()"><i class="im-upload2"></i> 麦联宝导入</a>
	                                    </li>
	                                     <li><a href="#" onclick="uploadBindData('unicom')"><i class="br-refresh"></i>更新联通数据</a>
                                   		 </li>
                                   		  <li><a href="#" onclick="uploadBindData('cmcc')"><i class="br-refresh"></i>更新移动数据</a>
                                   		 </li>
	                                </ul>
	                            </li>
                         </c:if>
                            <li class="dropdown">
                                <a href="#" style="padding: 12px" data-toggle="dropdown"><i class="st-settings"></i></a>
                                <ul class="dropdown-menu right" role="menu">
                                    <li><a href="${pageContext.request.contextPath}/loginOut"><i class="im-exit"></i>注销用户</a>
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
								套餐管理</a>
	                            </li>
                            </c:if>
                            <c:if test="${roleid ne '1' }"><!-- roleid eq '1'  -->
                            <li><a href="#" class="st-bag" onclick='addTab("packageMaintion","${pageContext.request.contextPath}/pages/pac_maintion.jsp","维护套餐")'>
                                维护套餐</a>
                            </li>
                            </c:if>
                        </ul>
                    </li>
                    <li><a href="#">联通物联卡 <i class="fa-file-text"></i></a>
                        <ul class="nav sub" id="unicom_card">
						</ul>
                    </li>
                    <li><a href="#">移动物联卡 <i class="fa-file-text"></i></a>
                        <ul class="nav sub" id="cmcc_card">
						</ul>
                    </li>
                    <li><a href="#">电信物联卡 <i class="fa-file-text"></i></a>
                        <ul class="nav sub" id="dx_card">
						</ul>
                    </li>
                    <li>
                        <a href="#"> 续费明细 (新)<i class="im-paragraph-justify" ></i></a>
                        <ul class="nav sub" id="all_kickback">
                        </ul>
                    </li>
                    <li>
                        <a href="#"> 续费明细 <i class="im-paragraph-justify"></i></a>
                        <ul class="nav sub">
                             <li><a href="#">联通返佣 <i class="fa-money"></i></a>
		                        <ul class="nav sub" id="unicom_kickback">
								</ul>
		                    </li>
		                    <li><a href="#">移动返佣 <i class="fa-money"></i></a>
		                        <ul class="nav sub" id="cmcc_kickback">
								</ul>
		                    </li>
		                    <li><a href="#">电信返佣 <i class="fa-money"></i></a>
		                        <ul class="nav sub" id="dx_kickback">
								</ul>
		                    </li>
                        </ul>
                    </li>
                   <c:if test="${roleid eq '1' }">
                    <li><a href="#">丰宁/永思卡信息 <i class="fa-save"></i></a>
                        <ul class="nav sub" id="card">
						</ul>
                    </li>
                    <li><a href="#">丰宁/永思返佣 <i class="fa-dollar"></i></a>
                        <ul class="nav sub" id="kickback">
						</ul>
                    </li>
                 </c:if>
                </ul>
            </div>
            <!-- End .sidebar-inner -->
        </div>
        <!-- End #sidebar -->
        <!-- Start #right-sidebar -->
        <div id="right-sidebar" class="hide-sidebar">
            <!-- Start .sidebar-inner -->
            <div class="sidebar-inner">
                <div class="sidebar-panel mt0">
                    <div class="sidebar-panel-content fullwidth pt0">
                        <div class="chat-user-list">
                            <form class="form-horizontal chat-search" role="form">
                                <div class="form-group">
                                    <input type="text" class="form-control" placeholder="Search for user...">
                                    <button type="submit"><i class="ec-search s16"></i>
                                    </button>
                                </div>
                                <!-- End .form-group  -->
                            </form>
                            <ul class="chat-ui bsAccordion">
                                <li>
                                    <a href="#">Favorites <span class="notification teal">4</span><i class="en-arrow-down5"></i></a>
                                    <ul class="in">
                                        <li>
                                            <a href="#" class="chat-name">
                                                <img class="chat-avatar" src="${pageContext.request.contextPath}/assets/img/avatars/49.jpg" alt="@chadengle">Chad Engle
                                                <span class="has-message"><i class="im-pencil"></i></span>
                                            </a>
                                            <span class="status online"><i class="en-dot"></i></span>
                                        </li>
                                        <li>
                                            <a href="#" class="chat-name">
                                                <img class="chat-avatar" src="${pageContext.request.contextPath}/assets/img/avatars/54.jpg" alt="@alagoon">Anthony Lagoon</a>
                                            <span class="status offline"><i class="en-dot"></i></span>
                                        </li>
                                        <li>
                                            <a href="#" class="chat-name">
                                                <img class="chat-avatar" src="${pageContext.request.contextPath}/assets/img/avatars/52.jpg" alt="@koridhandy">Kory Handy</a>
                                            <span class="status"><i class="en-dot"></i></span>
                                        </li>
                                        <li>
                                            <a href="#" class="chat-name">
                                                <img class="chat-avatar" src="${pageContext.request.contextPath}/assets/img/avatars/50.jpg" alt="@divya">Divia Manyan</a>
                                            <span class="status"><i class="en-dot"></i></span>
                                        </li>
                                    </ul>
                                </li>
                                <li>
                                    <a href="#">Online <span class="notification green">3</span><i class="en-arrow-down5"></i></a>
                                    <ul class="in">
                                        <li>
                                            <a href="#" class="chat-name">
                                                <img class="chat-avatar" src="${pageContext.request.contextPath}/assets/img/avatars/51.jpg" alt="@kolage">Eric Hofman</a>
                                            <span class="status online"><i class="en-dot"></i></span>
                                        </li>
                                        <li>
                                            <a href="#" class="chat-name">
                                                <img class="chat-avatar" src="${pageContext.request.contextPath}/assets/img/avatars/55.jpg" alt="@mikebeecham">Mike Beecham</a>
                                            <span class="status online"><i class="en-dot"></i></span>
                                        </li>
                                        <li>
                                            <a href="#" class="chat-name">
                                                <img class="chat-avatar" src="${pageContext.request.contextPath}/assets/img/avatars/53.jpg" alt="@derekebradley">Darek Bradly</a>
                                            <span class="status online"><i class="en-dot"></i></span>
                                        </li>
                                    </ul>
                                </li>
                                <li>
                                    <a href="#">Offline <span class="notification red">5</span><i class="en-arrow-down5"></i></a>
                                    <ul>
                                        <li>
                                            <a href="#" class="chat-name">
                                                <img class="chat-avatar" src="${pageContext.request.contextPath}/assets/img/avatars/56.jpg" alt="@laurengray">Lauren Grey</a>
                                            <span class="status offline"><i class="en-dot"></i></span>
                                        </li>
                                        <li>
                                            <a href="#" class="chat-name">
                                                <img class="chat-avatar" src="${pageContext.request.contextPath}/assets/img/avatars/49.jpg" alt="@chadengle">Chad Engle</a>
                                            <span class="status offline"><i class="en-dot"></i></span>
                                        </li>
                                        <li>
                                            <a href="#" class="chat-name">
                                                <img class="chat-avatar" src="${pageContext.request.contextPath}/assets/img/avatars/58.jpg" alt="@frankiefreesbie">Frankie Freesibie</a>
                                            <span class="status offline"><i class="en-dot"></i></span>
                                        </li>
                                        <li>
                                            <a href="#" class="chat-name">
                                                <img class="chat-avatar" src="${pageContext.request.contextPath}/assets/img/avatars/57.jpg" alt="@joannefournier">Joane Fornier</a>
                                            <span class="status offline"><i class="en-dot"></i></span>
                                        </li>
                                        <li>
                                            <a href="#" class="chat-name">
                                                <img class="chat-avatar" src="${pageContext.request.contextPath}/assets/img/avatars/59.jpg" alt="@aiiaiiaii">Alia Alien</a>
                                            <span class="status offline"><i class="en-dot"></i></span>
                                        </li>
                                    </ul>
                                </li>
                            </ul>
                        </div>
                        <div class="chat-box">
                            <h5>Chad Engle</h5>
                            <a id="close-user-chat" href="#" class="btn btn-xs btn-primary"><i class="en-arrow-left4"></i></a>
                            <ul class="chat-ui chat-messages">
                                <li class="chat-user">
                                    <p class="avatar">
                                        <img src="${pageContext.request.contextPath}/assets/img/avatars/49.jpg" alt="@chadengle">
                                    </p>
                                    <p class="chat-name">Chad Engle <span class="chat-time">15 seconds ago</span>
                                    </p>
                                    <span class="status online"><i class="en-dot"></i></span>
                                    <p class="chat-txt">Hello Sugge check out the last order.</p>
                                </li>
                                <li class="chat-me">
                                    <p class="avatar">
                                        <img src="${pageContext.request.contextPath}/assets/img/avatars/48.jpg" alt="SuggeElson">
                                    </p>
                                    <p class="chat-name">SuggeElson <span class="chat-time">10 seconds ago</span>
                                    </p>
                                    <span class="status online"><i class="en-dot"></i></span>
                                    <p class="chat-txt">Ok i will check it out.</p>
                                </li>
                                <li class="chat-user">
                                    <p class="avatar">
                                        <img src="${pageContext.request.contextPath}/assets/img/avatars/49.jpg" alt="@chadengle">
                                    </p>
                                    <p class="chat-name">Chad Engle <span class="chat-time">now</span>
                                    </p>
                                    <span class="status online"><i class="en-dot"></i></span>
                                    <p class="chat-txt">Thank you, have a nice day</p>
                                </li>
                            </ul>
                            <div class="chat-write">
                                <form action="#" class="form-horizontal" role="form">
                                    <div class="form-group">
                                        <textarea name="sendmsg" id="sendMsg" class="form-control elastic" rows="1"></textarea>
                                        <a role="button" class="btn" id="attach_photo_btn">
                                            <i class="fa-picture s20"></i> 
                                        </a>
                                        <input type="file" name="attach_photo" id="attach_photo">
                                    </div>
                                    <!-- End .form-group  -->
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- End .sidebar-inner -->
        </div>
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

      
	</script>
	
	<div class="modal fade" id="mlbModal" tabindex="-2" role="dialog"
		aria-labelledby="uploadModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:400px; ">
			<div class="modal-content">
				<div class="modal-body">
					   	<form class="form-signin" role="form" method="POST"
						 id="mlbForm"
						action="${pageContext.request.contextPath}/uploadExcel/upload.do">
						<div class="form-group">
							<label for="message-text" class="control-label" id="timetext">出库时间:</label>
							 <div class='input-group date' id='datetimepicker1'  >  
					                <input type='text' class="form-control" readonly name="createdate"  id="createdate"/>  
					                <span class="input-group-addon" >  
					                    <span class="glyphicon glyphicon-calendar"></span>  
					                </span>  
					            </div>   
						</div>
						<div class="form-group">
						   <label for="message-text" class="control-label">选择卡类型:</label>
						    <select name="apiCode" id = "apiCode" class="form-control" >
						      <option value="1">移动</option>
						      <option value="2">联通</option>
						      <option value="3">电信</option>
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
		$('#btn_api').click(function() {
			if($("#apiCode").val() == "0"){
				  alert("请选择数据接口");
				  return false ;
			}
			//if (checkData()) {
				$('#form1').ajaxSubmit({
					url : '${pageContext.request.contextPath}/uploadExcel/ajaxUpload.do',
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
	
	
	function uploadMlbData(){
		 $("#mlbModal").modal("show");
	}
	
	$(document).ready(function() {
		$('#btn_mlb').click(function() {
			if($("#createdate").val() == ''){
				 alert("请添加出库时间");
				 return false;
			}
			$('#mlbForm').ajaxSubmit({
				url : '${pageContext.request.contextPath}/unicomUpload/uploadExcelUnicom',
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
	
	$(function(){
			if($('#datetimepicker1')[0] != undefined){
				$('#datetimepicker1').datetimepicker({  
					minView: "month",
					format: 'yyyy-mm-dd',
				    todayBtn: true,//显示今日按钮
				    autoclose: true,
				    language:"zh-CN",
				    clearBtn: true 
				});
			}
		});
</script>
</html>