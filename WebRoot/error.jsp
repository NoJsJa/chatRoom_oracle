<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%
String path = request.getContextPath();
request.setAttribute("path", path);
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>哎呀...出错了！</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="${path}/css/error.css">
	<script type="text/javascript" src="${path}/javascript/error.js"></script>

  </head>
  
  <body>
  
  <div id="background"></div>
  
    <table>
    	<tr class="tip">
    		<td><label>糟了...你的账户已经注销！</label><td>
    		<td></td>
    	</tr>
    	<tr class="back">
    		<td><a href="${path}/index.jsp" class="backA">返回</a></td>
    		<td></td><td></td>
    	</tr>
    </table>
  </body>
</html>
