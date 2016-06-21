<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%
String path = request.getContextPath();
request.setAttribute("path", path);
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>home page</title>
	
	<link rel="stylesheet" type="text/css" href="${path}/css/homePage.css">
	<link rel="stylesheet" type="text/css" href="${path}/css/homePage2.css">
	<script type="text/javascript" src="${path}/javascript/jquery-2.1.4.js"></script>
	<script type="text/javascript" src="${path}/javascript/xmlRequestPool.js"></script>
	<script type="text/javascript" src="${path}/javascript/homePage.js"></script>

  </head>
  
  <body>
  
  <!-- 背景图片 -->
  <div id="background"></div>
  
  <marquee direction="left" behavior="slide" id="homePage">我的启航空间</marquee>
  
  <!-- 联系人列表 -->
  <div  id="minDiv">
    <div id="totalDiv" class="shadow4">
    	<div id="logo" class="titleDiv"></div>
    	<div id="name" class="titleDiv inset-shadow">${sessionScope.name}</div>
    	<div id="quit" class="titleDiv"><input type="button" id="logoutButton" name="logoutButton" class="inset-shadow" value="注 销" ></div>
    	<div id="tableDiv">
    		<div id="listDiv">
    			<div id="firstTr">
    				<label class="firstTr_nomessage">---- 没 有 新 消 息 额 ----</label>
    			</div>
    		</div>
    	</div>
    	
    	<!-- 导航区域 -->
    	<div id="message" class="footerDiv shadow4" name="footer" title="我不能点额.">消息</div>
    	<div id="friends" class="footerDiv shadow4" name="footer">朋友</div>
    	<div id="setting" class="footerDiv shadow4" name="footer">设置</div>
    	<div id="chatRoom" class="footerDiv shadow4" name="footer">聊天室</div>
    </div>
    
    <!-- 会话区域 -->
    <div id="totalDiv2">
    	<div id="titleDetails" class="shadow4">启 航 ZONE</div>
    	<div id="closeSession" class="inset-shadow">退出会话</div>
    	
    	<div id="messageDetails" class="inset-shadow">
    		<div id="message2" class="inset-shadow"></div>
    		<div id="info">
    			<div id="sendtoImgInfo"></div>
    			<div id="otherInfo">
    				<div id="videoDiv"><input type="button" value="◎ 视频通话" id="video" disabled="disable"></div>
    				<div id="nameDiv">- - - 好 友 资 料 - - -</div>
    				<div id="sexDiv">【性别】</div>
    				<div id="mottoDiv">【座右铭】</div>
    				<div id="activityDiv">【活跃度】</div>
    			</div>
    			<div id="senderImgInfo"></div>
    		</div>
    	</div>
    	
    	<!-- 聊天信息输入区域 -->	
    	<div id="emojiDiv" class="shadow4"></div>
    	
    	<div id="inputDiv" class="inset-shadow">
    		<input id="messageInput" type="text" placeholder="请在这儿输入聊天消息额...">
    	</div>
    	<div id="sendButtonDiv" class="inset-shadow">发  送</div>
    	</div>
    	
    	<!-- 改变背景图片 -->
    	<input type="button" id="nextButton" title="切换主题"  value=" ↑↓ ">
  </div>
  
  </body>
</html>
