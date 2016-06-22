<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%
String path = request.getContextPath();
request.setAttribute("path", path);
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>资料 & 添加</title>

	<link rel="stylesheet" type="text/css" href="${path}/css/setting.css">
	<link rel="stylesheet" type="text/css" href="${path}/css/setting2.css">
	
	<script type="text/javascript" src="${path}/javascript/jquery-1.11.0.min.js"></script>
	<script type="text/javascript" src="${path}/javascript/jquery-migrate-1.2.1.min.js"></script>
	<script type="text/javascript" src="${path}/javascript/xmlRequestPool.js"></script>
	<script type="text/javascript" src="${path}/javascript/setting.js"></script>
	<script type="text/javascript" src="${path}/javascript/ajaxfileupload.js"></script>

  </head>
  
  <body onload="init()">
  
  <!-- 背景填充 -->
  <div id="background"></div>
  
  <input type="button" id="backButton" value="<=">
  
  <header class="inset-shadow">
    <label>启航 SETTING</label>
</header>
  
  <!-- 修改资料 -->
  <fieldset id="selfInfoField" class="field shadow">
  	<legend style="color: #800040">修改资料</legend>
  	<div id="div">
    <div id="totalDiv">
    
  
	    <div id="imgDiv"> 
	    	<div id="imgSelect"><input type="file" id="imgLoad" name="imgLoad"></div>
	    	<div id="imgSubmit">(推荐比例 1 x 1)上传</div>
	    </div>
	    <div id="headImg"></div>
    	
    	<div id="motto">座右铭</div>
    	<div id="mottoInputDiv"><input id="mottoInput" placeholder="---座右铭---"></div>
    	
    	<div id="sex">性别</div>
    	<div id="sexInputDiv">
    		<select name="sex" id="sexInput">
    			<option value="男生">我是调皮的男生额...</option>
    			<option value="女生">我是可爱的女生额...</option>
    		</select>
    	</div>
    	
    </div>
    <div id="submit">
    	<div id="submitDiv"><input type="button" id="submitButton" value="提交"></div>
    </div>
  </div>  
  
  <!-- 添加好友 -->
  <div id="addFriend">
  	<div id="addFriendDiv"><input type="text" id="addFriendInput" placeholder="请输入要添加的好友名字"></div>
  	<div id="addFriendSubmit"><input type="button" id="addFriendButton" value="添 加"></div>
  </div>
  
  </fieldset>
  
  
 <!--  功能区2 -->
  
  <!-- 查询用户 -->
  <div id="queryUser">
  
  <fieldset id="conditionField" class="field shadow2">
  	<legend style="color: #0080c0">查询条件</legend>
  	<form action="/chatRoom/servlet/handleSingleAction?action=queryUser" method="POST">
  	<div id="queryCondition">
  		<div id="nameCondition">姓名：
  			<input type="text" id="nameConditionInput" name="nameConditionInput">
  		</div>
  		<div id="sexCondition">性别：
  			<select id="sexSelect" name="sexSelect">
  				<option value="none" selected="selected">不限</option>
  				<option value="男生">男生</option>
  				<option value="女生">女生</option>
  			</select>
  		</div>
  		<div id="activityCondition">星级：
  			<select id="activitySelect" name="activitySelect">
  				<option value="none" selected="selected">不限</option>
  				<option value="50">二星级</option>
  				<option value="100">三星级</option>
  				<option value="200">四星级</option>
  				<option value="500">五星级</option>
  			</select>
  			
  		</div>
  		<input type="submit" value="查询" id="querySubmit">
  		<input type="reset" value="重置" id="resetSubmit">
  	</div>
  	</form>
  </fieldset>
  	
  	<fieldset id="resultField" class="field shadow2">
  		<legend style="color: green">查询结果</legend>
  		<div id="tableDiv">
  		<table id="headTable">
  			<tr id="headTr">
  				<td>姓名</td>
  				<td>性别</td>
  				<td>座右铭</td>
  				<td>登录次数</td>
  				<td>操作</td>
  			</tr>
  			
  			<c:forEach items="${requestScope.personList}" var= "person">
  				<tr  onmouseover= "javascript:personMouseOver(this)" 
  				onmouseout= "javascript:personMouseOut(this)" class="contentTr">
  					<td>${person.name}</td>
  					<td>${person.sex}</td>
  					<td>${person.motto}</td>
  					<td>${person.activity}</td>
  					<td style="color: green; cursor:pointer" onclick="javascript:clickAddFrend('${person.name}')">+好友</td>
  				</tr>
  			</c:forEach>
  			
  		</table>
  		
  		<div id="pageControl">
  			<input type="button" value="上一页" id="lastPageButton">
  			<input type="button" value="下一页" id="nextPageButton">
  		</div>
  		
  	</div>
  	</fieldset>
  	
  </div>
  
  </body>
</html>






























