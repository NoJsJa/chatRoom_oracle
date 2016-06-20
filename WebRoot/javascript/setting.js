//初始化函数
$(function(){
	$("#submitButton").click(function(){
		updateData();
	});
	$("#backButton").click(function(){
		back();
	});
	$("#imgSubmit").click(function () {
		imgUpload();
	});
	$("#addFriendButton").click(function () {
		addFriend();
	});
	$("#lastPageButton").click(function () {
		lastPage();
	});
	$("#nextPageButton").click(function () {
		nextPage();
	});
	
	var submitButton = document.getElementById("submitButton");
	var backButton = document.getElementById("backButton");
	var addButton = document.getElementById("addFriendButton");
	
	mouseOver(submitButton, "#af60ff", "#ffffff");
	mouseOver(backButton, "#ffffff", "#800040");
	mouseOver(addButton, "#af60ff", "#ffffff");
	
	mouseOut(submitButton,  "#ffffff", "#af60ff");
	mouseOut(backButton, "#5badff", "#ffffff");
	mouseOut(addButton, "#ffffff","#af60ff");
	
	scrollToButtom();
});


//跳转到页面底部
function scrollToButtom() {
	
	document.getElementById("resultField").scrollIntoView();
}

//查询起始位置
var index = 0;

//注册onmouseover事件的工具函数
function mouseOver(component, bgcolor, color) {

	component.onmouseover = function() {
		changeColor.call(component, bgcolor, color);
	}
}

//注册onmouseout事件的工具函数
function mouseOut(component, bgcolor, color) {
	
	component.onmouseout = function() {
		changeColor.call(component, bgcolor, color);
	}
}

//改变对象的背景色和文字颜色
var changeColor = function (bgColor, color) {
	
	this.style.backgroundColor = bgColor;
	this.style.color = color;
}

function personMouseOver(object) {
	object.style.backgroundColor = "#6b9aff";
	object.style.color = "#00ecec";
}

function personMouseOut(object) {
	object.style.backgroundColor = 'white';
	object.style.color = "black";
}

//图片上传
function imgUpload() {
	
	if($("#imgLoad").val() == ""){
		alert("请选择文件！");
		return;
	}
	
	$.ajaxFileUpload(
			{
				 url: '/chatRoom/servlet/handleSingleAction?action=updateImg',  
				 secureuri: false,
				 type: 'POST',
				 fileElementId: 'imgLoad', //file标签的id  
				
				 dataType: 'text', //返回值类型 一般设置为json
		         success: function (data, status) {  
		                //把图片替换  	
		        	 var responseJson = jQuery.parseJSON(data);  
		        	 if(responseJson.error != undefined){
		        		 alert("上传出错！");
		        		 return;
		        	 }
		        	$("#headImg").css("background", "url(" + responseJson.imgUrl + ") center no-repeat")
                        .css("background-size", "contain");
		         },  
		         error: function (data, status, e) {  
		                alert("errorMessage: " + e);  
		         }  
			}
	);
}


//返回
function back() {
	window.location.href = "/chatRoom/homePage.jsp";
}

//更新资料
function updateData() {
	
	var motto = document.getElementById("mottoInput").value;
	var sex = document.getElementById("sexInput").value;
	
	XMLHttp.getInstance();
	var url = "/chatRoom/servlet/handleSingleAction";
	var data = "action=updateData" + "&motto="+motto+"&sex="+sex;
	var method = "POST";
	var callback = function(request) {
		updateDataPage(request);
	};
	
	XMLHttp.sendRequest(method, url, data, callback);
	
}

//更新资料回调函数
function updateDataPage(request) {
	
	if(request.responseText == "error"){
		request.abort();
		window.location.href = "/chatRoom/servlet/logoutServlet?action=error";
		
		return;
	}
	var data = JSON.parse(request.responseText);
	var sex = data.sex;
	var motto = data.motto;
	request.abort();
	alert("更新成功！");
}

//更新下一页
function lastPage(){
	
	//起始位置
	index -= 10;
	if(index < 0){
		index = 0;
	}
	
	var url = "/chatRoom/servlet/handleSingleAction?action=queryUser&index=" + index;
	document.forms[1].action = url;
	document.forms[1].method = "POST";
	document.forms[1].submit();
}

//跳到上一页
function nextPage() {
	
	//起始位置
	index += 10;
	var url = "/chatRoom/servlet/handleSingleAction?action=queryUser&index=" + index;
	document.forms[1].action = url;
	document.forms[1].method = "POST";
	document.forms[1].submit();
}

function addFriend() {
	
	XMLHttp.getInstance();
	
	var friend = document.getElementById("addFriendInput").value;
	var method = "POST";
	var data = "action=addFriend" + "&friend=" + friend;
	var url = "/chatRoom/servlet/handleSingleAction";
	var callback = function(request) {
		addFriendResponse(request);
	};
	
	XMLHttp.sendRequest(method, url, data, callback);
}

function clickAddFrend(name) {
	
	document.getElementById("addFriendInput").value = name;
	addFriend();
}

function addFriendResponse(request) {
	
	var responseInfo = request.responseText;
	if(responseInfo == "result_noUser"){
		request.abort();
		alert("添加失败，没有这个用户");
		return;
	}else if(responseInfo == "result_exit"){
		request.abort();
		alert("添加失败，已经是好友关系");
		return;
	} else if(responseInfo == "result_addSelf") {
		request.abort();
		alert("添加失败，不能添加自己为好友！");
		return;
	}
	
	alert("添加成功！" + responseInfo);
	request.abort();
}
















