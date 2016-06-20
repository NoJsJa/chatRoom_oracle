<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=utf-8"%>
<%
String path = request.getContextPath();
request.setAttribute("path", path);
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Video Chat</title>
    <script src="${path}/javascript/jquery-2.1.4.js"></script>
    <script src="${path}/javascript/video.js"></script>
    <link rel="stylesheet" href="${path}/css/video.css">
</head>

<body class="opacity">

<video id="video"></video>

<header class="inset-shadow">
    <label>启航VIDEO</label>
</header>

<div id="onlineMessage">
    <div class="online shadow" id="online" >
        <label id="name_sender"><b>${param.sender}</b></label> 和
        <label id="name_sendTo"><b>${param.sendTo}</b></label> 正在聊天
    </div>
</div>

<div id="total" class="shadow">
    <div id="canvasDiv">
        <canvas  id="canvas2" class="inset-shadow"></canvas><br>
        <canvas  id="canvas" class="inset-shadow"></canvas><br>
    </div>
    <div id="chatDiv">
        <div id="chat" class="inset-shadow"></div>
        <div id="emojiDiv"></div>
        <div id="sendDiv">
            <input type="text" id="test" placeholder="请输入聊天信息"/>
            <button id="send" name="send">发送</button>
        </div>

    </div>
</div>

<!-- 返回主页按钮 -->
<input type="button" id="backButton" value=" <= ">

</body>
</html>

