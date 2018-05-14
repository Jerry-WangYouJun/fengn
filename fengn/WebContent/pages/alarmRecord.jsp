<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link href="css/bootstrap.css" rel='stylesheet' type='text/css' />
<link href="css/style.css" rel='stylesheet' type='text/css' />
<link rel="icon" href="favicon.ico" type="image/x-icon" >
<link href="css/font-awesome.css" rel="stylesheet"> 
<script src="js/jquery-1.11.1.min.js"></script>
<script src="js/modernizr.custom.js"></script>
<script src="js/metisMenu.min.js"></script>
<script src="js/custom.js"></script>
<link href="css/custom.css" rel="stylesheet">
</head>
<body >
		<div >
			<div class="main-page">
				<!--buttons-->
				<div class="grids-section">
					<h2 class="hdg"><a href="index.jsp">回首页</a></h2>
				<div class="bottom-table">
				<div class="bs-docs-example">
					<table data-toggle="table" 
					  class="table table-hover">
						<thead>
							<tr>
							  <th data-field="type">报警点</th>
							  <th data-field="type">报警类型</th>
							  <th data-field="alarmdata">报警值</th>
							  <th data-field="recordDate">报警时间</th>
							</tr>
						</thead>
						<c:forEach items="${list }" var="alarm">
							   <tr>
							  <td data-field="recordDate">${alarm.rtuName }</td>
							  <td data-field="type">${alarm.type }</td>
							  <td data-field="alarmdata">${alarm.data }</td>
							  <td data-field="recordDate">${alarm.recordDate }</td>
							</tr>
						</c:forEach>
							
					</table>
					
				</div>
				</div>
				</div>
				</div>
	</div>
				<script src="js/classie.js"></script>
				<script type="text/javascript" src="js/bootstrap.min.js"></script>
				<script src="js/jquery.nicescroll.js"></script>
				<script src="js/scripts.js"></script>
</body>
</html>