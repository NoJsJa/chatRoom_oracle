<%@page import="dao.ApplicationConstants"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%
String path = request.getContextPath();
request.setAttribute("path", path);
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
int current_user =  ApplicationConstants.CURRENT_LOGIN_COUNT;
request.setAttribute("current_user", current_user);
%>

<!DOCTYPE>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>启航 ROOM</title>

	<link rel="stylesheet" type="text/css" href="${path}/css/chatRoom.css">
	<script type="text/javascript" src="${path}/javascript/lib/jquery-1.11.0.min.js"></script>
	<script type="text/javascript" src="${path}/javascript/lib/jquery-migrate-1.2.1.min.js"></script>
	<script type="text/javascript" src="${path}/javascript/xmlRequestPool.js"></script>
	<script type="text/javascript" src="${path}/javascript/lib/json2.js"></script>
	<script type="text/javascript" src="${path}/javascript/lib/ajaxfileupload.js"></script>
	<script type="text/javascript" src="${path}/javascript/chat.js"></script>
	
  </head>
  
  <body>
  <!-- 背景图片 -->
    <div class="background"></div>
  	<div id="header" class="inset-shadow opacity"><label id="headerLabel">启航 ROOM</label></div>
  	<div class="total">
  	<div class="onlineDiv inset-shadow" id="onlineDiv">|在线用户|
  	</div>
  	<div id="onlineMessage">
  		<div class="blueTd shadow" id="onlineCount" >在线人数->${ current_user }</div>
  	</div>
  	<div id="border" class="inset-shadow">
  		<div class="tableBorder" id="tableBorder">
	  		<table id="showTable" class="opacity">
	  		<tr><td class="headTd" colspan="2">- - - 聊 天 信 息 - - -</td></tr>
	  		</table>
  		</div>
  		<div id="emojiDiv">
  		</div>

		<div class="tableBorder2" >
			<table id="sendTable">
		    	<tr>
		    		<td class="td3"><label id="userLabel">${name}</label></td>
		    		<td class="quickTd">
		    			<select id="quickList">
		    				<option value="0">》》》使用快捷回复《《《</option>
		    				<option value="1">对不起，我现在比较忙，待会回复你...</option>
		    				<option value="2">大家好，我的名字是 ${name}，多多指教</option>
		    				<option value="3">今天天气真不错啊！</option>
		    				<option value="4">再见，有时间再聊...</option>
		    				<option value="5">不高兴..不高兴..不高兴...</option>
		    				<option value="6">你们在说什么？</option>
		    				<option value="7">这真是太逗了！</option>
		    			</select>
		    		</td>
		    		<td class="emojiTd">
		    			<select id="emojiList">
		    				<option value="1"></option>
		    				<option value="2"> T_T ||</option>
		    				<option value="3"> (=^_^=) </option>
		    				<option value="4"> ≧◇≦</option>
		    				<option value="5"> ლ(°Д°ლ)</option>
		    				<option value="6"> (#￣▽￣#)</option>
		    				<option value="7"> ( $ _ $ )</option>
		    				<option value="8"> ⊙ω⊙</option>
		    				<option value="9"> ♀(￣▽￣)/</option>
		    				<option value="10"> ≧▂≦</option>
		    			</select>
		    		</td>
		    	</tr>
		    	<tr>
		    		<td class="inputTd" colspan="3">
		    			<input type="text" id="inputMessage" name="inputMessage">
		    			<input type="button" id="sendButton" name="sendButton" value="发送" placeholder="请在此输入聊天信息">
		    			<input type="button" id="logoutButton" name="logoutButton" value="注销" >
		    		</td>
		    	</tr>
	    	</table>
		</div>
		
		</div>
		
	<div id="fileTotal" class="fileDiv inset-shadow">
			<div id="fileTitle" title="点我啊！">文件列表</div>
			<div id="fileUploadDiv" class="opacity">
				<input type="file" id="fileChoose" name="fileChoose">
				<input type="button" id="fileUpload" value="上传">
			</div>
			<div id="file">
				
			</div>
		</div>	
	<div class="footer" id="tips">
		<marquee direction="left" behavior="slide">[点我]发送不良消息的话会被强制注销额!</marquee>
	</div>
  	</div>
  	
  	<!-- 返回主页按钮 -->
    <input type="button" id="backButton" title="返回我的空间" value=" <= ">
  	
  </body>
</html>
