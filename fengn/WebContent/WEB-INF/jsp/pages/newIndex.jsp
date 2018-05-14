<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${basePath }/assets/css/bootstrap.css" rel="stylesheet" />
     <!-- FontAwesome Styles-->
    <link href="${basePath }/assets/css/font-awesome.css" rel="stylesheet" />
     <!-- Morris Chart Styles-->
        <!-- Custom Styles-->
    <link href="${basePath }/assets/css/custom-styles.css" rel="stylesheet" />
     <!-- TABLE STYLES-->
    <link href="${basePath }/assets/js/dataTables/dataTables.bootstrap.css" rel="stylesheet" />
    <link rel="stylesheet" type="text/css" href="${basePath }/treeview/css/default.css">
	<style type="text/css">
		.htmleaf-header{margin-bottom: 15px;font-family: "Segoe UI", "Lucida Grande", Helvetica, Arial, "Microsoft YaHei", FreeSans, Arimo, "Droid Sans", "wenquanyi micro hei", "Hiragino Sans GB", "Hiragino Sans GB W3", "FontAwesome", sans-serif;}
		.htmleaf-icon{color: #fff;}
		.container {
			height:100%;
			width: 100%;
			position:fixed;
			}
	</style>
<title>青岛丰宁贸易</title>
</head>
<body class="container">
<div id="wrapper">
        <nav class="navbar navbar-default top-navbar" role="navigation">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".sidebar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="index.html">后台管理系统</a>
            </div>

            <ul class="nav navbar-top-links navbar-right">
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#" aria-expanded="false">
                        <i class="fa fa-user fa-fw"></i> <i class="fa fa-caret-down"></i>
                    </a>
                    <ul class="dropdown-menu dropdown-user">
                        <li><a href="#"><i class="fa fa-user fa-fw"></i> User Profile</a>
                        </li>
                        <li><a href="#"><i class="fa fa-gear fa-fw"></i> Settings</a>
                        </li>
                        <li class="divider"></li>
                        <li><a href="#"><i class="fa fa-sign-out fa-fw"></i> Logout</a>
                        </li>
                    </ul>
                    <!-- /.dropdown-user -->
                </li>
                <!-- /.dropdown -->
            </ul>
        </nav>
        <!--/. NAV TOP  -->
        <nav class="navbar-default navbar-side" role="navigation">
            <div class="sidebar-collapse">
            <div id="treeview1" class=""></div>

            </div>

        </nav>
        <!-- /. NAV SIDE  -->
        <div id="page-wrapper" >
            <div id="page-inner">
                 <!-- /. ROW  -->
				<div class="row">
                <div class="col-md-12">
						<ul class="nav nav-tabs" role="tablist" id="deviceulid">
							
						</ul>
						<!-- 面板区 -->
						<div class="tab-content" id ="home"  style="padding:20px 20px 22px 20px">
						
						</div>
						<!-- Advanced Tables -->
                    
                    <!--End Advanced Tables -->
                </div>
            </div>
        </div>
    </div>
            </div>
    <!-- jQuery Js -->
    <script src="${basePath }/assets/js/jquery-1.10.2.js"></script>
      <!-- Bootstrap Js -->
    <script src="${basePath }/assets/js/bootstrap.min.js"></script>
    <!-- Metis Menu Js -->
    <script src="${basePath }/assets/js/jquery.metisMenu.js"></script>
     <!-- DATA TABLE SCRIPTS -->
    <script src="${basePath }/assets/js/dataTables/jquery.dataTables.js"></script>
    <script src="${basePath }/assets/js/dataTables/dataTables.bootstrap.js"></script>
	<script src="${basePath }/treeview/js/bootstrap-treeview.js"></script>
        <script>
            $(document).ready(function () {
            			$('#test').load('logs.txt');
            });
            
              
              /**
               * 增加标签页
               */
              function addTab(  tabName ,tabUrl , title) {
                  //option:
                  //tabMainName:tab标签页所在的容器
                  //tabName:当前tab的名称
                  //tabTitle:当前tab的标题
                  //tabUrl:当前tab所指向的URL地址
                  var exists = checkTabIsExists('deviceulid', tabName);
                  if(exists){
                      $("#tab_a_"+tabName).click();
                  } else {
                      $("#deviceulid").append('<li id="tab_li_'+tabName+'"><a href="#tab_content_'+tabName+'" data-toggle="tab" id="tab_a_'+tabName+'"><button class="close closeTab" type="button" onclick="closeTab(this);">×</button>'+title+'</a></li>');
                       
                      //固定TAB中IFRAME高度
                      mainHeight = $(document.body).height() - 5;
                       
                      var content = '';
                      if(content){
                          content = option.content;
                      } else {
                          content = '<iframe id="myframe" src="' + tabUrl + '" width="100%"    frameborder="no" border="0" marginwidth="0" height="800" marginheight="0" scrolling="yes" allowtransparency="yes"></iframe>';
                      }
                      $("#home").append('<div id="tab_content_'+tabName+'" role="tabpanel" class="tab-pane" id="'+ tabName+'">'+content+'</div>');
                      $("#tab_a_"+tabName).click();
                  }
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
               
               function changeFrameHeight(){
            	    var ifm= document.getElementById("myframe"); 
            	    ifm.height=document.documentElement.clientHeight;

            	}

            	window.onresize=function(){  
            	     changeFrameHeight();  

            	} 
               
               $(function() {

            	   var treeData = [{
       				text:"用户管理",	
       				name:"user",
       				backColor: "#f3f5f6",
       				color:'#768399',
       				nodes:[{
       					text:"代理商管理",
       					backColor: "#f3f5f6",
       					color:'#768399',
       				    url:"${basePath}/pages/user_list.jsp"
       				}],
       			}];
                   $('#treeview1').treeview({
                     data: treeData
                   });
                   $('#treeview1').on('nodeSelected', function(event, data) {
                	   if(data.url){
                	  	 addTab(data.name, data.url,data.text);
                	   }
                   	}); 
              });
    </script>
    
   
</body>
</html>