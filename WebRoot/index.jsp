<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%
String path = request.getContextPath();
request.setAttribute("path", path);
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setCharacterEncoding("utf-8");
%>

<!DOCTYPE>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>登录 启航聊天室</title>

	<link rel="stylesheet" type="text/css" href="${path}/css/index.css">
	<link rel="stylesheet" type="text/css" href="${path}/css/copyRight.css">
	<script type="text/javascript" src="${path}/javascript/lib/jquery-2.1.4.js"></script>
	<script type="text/javascript" src="${path}/javascript/xmlRequestPool.js"></script>
	<script type="text/javascript" src="${path}/javascript/index.js"></script>
  </head>
  
  <body onload="init()">
  
  <div class="webm">
  	<video class="covervid-webm" autoplay loop poster="${path}/img/rain.jpg">
		<source src="${path}/videos/rain.webm" type="video/webm">
	</video>

  </div>
  
  <!-- 欢迎标签 -->
  <marquee direction="left" behavior="slide" id="welcome">启航聊天室欢迎您！</marquee>
  
    <form >
    	<table id="loginTable">
    		<c:if test="${loginError}">
    			<tr class="errorTr">
    				<td class="td"><label>Sorry!</label></td>
    				<td><strong><label class="errorLabel">用户名或密码错误！</label></strong></td>
    			</tr>
    		</c:if>
    		<tr class="accountTr">
    			<td class="td"><strong>账户：</strong></td>
    			<td><input type="text" name="name" id="name" placeholder="请输入你的名字"></td>
    		</tr>
    		<tr class="passwordTr">
    			<td class="td"><strong>密码：</strong></td>
    			<td><input type="password" name="password" id="password" placeholder="请输入你的密码"></td>
    		</tr>
    		
    		<tr class="submitTr">
    			<td></td>
    			<td>
    				<input type="button" name="loginSubmit" id="loginSubmit" value="登录">
    				<input type="button" name="signUp" id="signUp" title="输入账户信息即刻注册！"  value="注册">
    			</td>
    		</tr>
    	</table>
    </form>
    
    <div class="copyRight">Copyright © 2016 <a href="http://www.cuit.edu.cn">www.cuit.edu.cn</a>. All rights reserved. </div>
    
  </body>
</html>
