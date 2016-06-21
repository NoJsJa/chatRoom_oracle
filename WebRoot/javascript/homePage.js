//系统初始化方法
$(function() {
	
	//添加表情
	appendEmoji();
    $("#logoutButton").click(function () {
        logout();
    });
	//更新好友列表
    $("#friends").click(function () {
        updateFriendList();
    });
	$("#chatRoom").click(function () {
        enterChatRoom();
    });
	$("#setting").click(function () {
        updateSetting();
    });
	$("#sendButtonDiv").click(function () {
        sendSingleMessage();
    });
    $("#closeSession").click(function () {
        hiddenSession(null);
    });
    $('#video').click(function () {
        videoRequest();
    });
	getHeadImg();
	//Enter键发送
	document.onkeydown = function(event){
        var e = event || window.event || arguments.callee.caller.arguments[0];       
        if(e && e.keyCode == 13){ // enter 键
        	sendSingleMessage();
        }
	};
	
	//改变背景图片
	document.getElementById("nextButton").onclick = changeImg;
	
	/*--------为元素加入onmouseover 和 onmouseout事件--------*/
	var footerDiv = document.getElementsByName("footer");
	for(var i = 0; i < footerDiv.length; i++) {
		//闭包
		//apply和call模式
		(function(){
			var j = i;
			footerDiv[j].onmouseover = function() {
				changeColor.call(footerDiv[j],"#ffffff", "#8080ff");
			};
			footerDiv[j].onmouseout = function() {
				changeColor.call(footerDiv[j], "#8080ff","#ffffff");
			};
		})();
	}
	
	var logoutButton = document.getElementById("logoutButton");
	var closeSession = document.getElementById("closeSession");
	var sendButton = document.getElementById("sendButtonDiv");
	var inputDiv = document.getElementById("inputDiv");
	
	mouseOver(logoutButton,"#ffffff", "#008040");
	mouseOut(logoutButton,"#008040", "#ffffff");
	
	mouseOver(closeSession,"#ffffff", "#800040");
	mouseOut(closeSession,"#800040", "#ffffff");
	
	mouseOver(sendButton,"#ffffff", "#8080ff");
	mouseOut(sendButton,"#8080ff", "#ffffff");	
	
	inputDiv.onmouseover = function() {
		changeLineColor.call(inputDiv, "#ac0056");
	};
	inputDiv.onmouseout = function() {
		changeLineColor.call(inputDiv, "#8080ff");
	};
	
	//更新离线消息
	updateMessage();
	//即时会话会话
	continueAjaxRequest();
});

//发送视频聊天的请求并进入视频聊天室
function videoRequest(){
	
	var name_sendTo = $('#titleDetails').text();
    var name_sender = $('#name').text();

    $('#messageInput').val('--- 请求视频通话 --- ');
    sendSingleMessage();
    hiddenSession(name_sendTo);
   /* 结束当前会话并打开新窗口建立视频通话*/
    window.open("/chatRoom/video.jsp?sender=" + name_sender + "&sendTo=" + name_sendTo);
}

//得到背景头像
function getHeadImg(){
	
	$("#logo").css("background", "url('/chatRoom/files/users/"+ $("#name").text() +"/" + $("#name").text()+".jpg') center no-repeat")
	.css("background-size", "contain");
	
	$("#senderImgInfo").css("background", "url('/chatRoom/files/users/"+ $("#name").text() +"/" + $("#name").text()+".jpg') center no-repeat")
	.css("background-size", "contain");
}

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

//改变对象的边框颜色
var changeLineColor = function(color) {
	this.style.borderColor = color;
};


//注销账号函数
function logout() {
	
	hiddenSession();
	setTimeout("window.location.href = '/chatRoom/servlet/logoutServlet'", 1500);
}

//进入聊天室
function enterChatRoom() {
	
	window.location.href = "/chatRoom/chatRoom.jsp";
}

//进入设置界面
function updateSetting() {
	
	window.location.href = "/chatRoom/setting.jsp";
} 

var messageCount = 0;

//发送更新好友列表请求
function updateFriendList() {
	
	XMLHttp.getInstance();
	
	var url = "/chatRoom/servlet/handleSingleAction";
	var method = "POST";
	var data = "action=" + "updateFriend";
	var callBack = function(objXMLHttpRequest){
		updateFriendPage(objXMLHttpRequest);
	};
	XMLHttp.sendRequest(method, url, data, callBack);
	
}

//更新好友列表函数
function updateFriendPage(objXMLHttpRequest) {
	
	if(objXMLHttpRequest.responseText == "error"){
		objXMLHttpRequest.abort();
		window.location.href = "/chatRoom/servlet/logoutServlet?action=error";
		
		return;
	}
	var friend = JSON.parse(objXMLHttpRequest.responseText);
	var firstTr = document.getElementById("firstTr");
	//移除所有子节点
	while(firstTr.hasChildNodes()) {
		firstTr.removeChild(firstTr.firstChild);
	}
	
	for(var friendItem in friend){
		(function() {
			var friendItem2 = friendItem;
			var friendDiv = document.createElement("div");
			var headerImg = document.createElement("div");
			var nameText = document.createTextNode(friendItem2);
			var statusDiv = document.createElement("div");
			var deleteDiv = document.createElement("div");
			
			$(headerImg).css("height", "30px").css("width", "30px").css("float", "left")
			.css("background", "url('/chatRoom/files/users/"+ friendItem2 +"/" + friendItem2 +".jpg') center no-repeat")
			.css("background-size", "contain").css("border-radius", "50%");
			$(nameText).css("float", "left");
			friendDiv.appendChild(headerImg);
			friendDiv.appendChild(nameText);
			
			friendDiv.setAttribute("class", "firstTr firstTr_name shadow3");
			statusDiv.innerHTML = friend[friendItem2]
			statusDiv.setAttribute("class", "firstTr firstTr_status inset-shadow");
			deleteDiv.innerHTML = "删 除";
			deleteDiv.setAttribute("class", "firstTr firstTr_status firstTr_delete shadow3");
			deleteDiv.id = friendItem;
			friendDiv.onclick = function() {
				//开启会话
				startSession(friendItem2);
			};
			statusDiv.onclick = function() {
				openDeleteItems(deleteDiv);
			};
			deleteDiv.onclick = function() {
				$(deleteDiv).slideUp("fast");
				$(friendDiv).slideUp("fast");
				$(statusDiv).slideUp("fast");
				
				deleteFriend(friendItem2);
			}
			firstTr.appendChild(friendDiv);
			firstTr.appendChild(statusDiv);
			firstTr.appendChild(deleteDiv);
		})();
	}
	
	objXMLHttpRequest.abort();
}

//展开删除朋友隐藏区域
var openDeleteItems = function(compolent) {
	
	if(compolent.style.display == "block"){
		compolent.style.display = "none";
	}else {
		compolent.style.display = "block";
	}
}

//删除好友
var deleteFriend = function(friendName){

	XMLHttp.getInstance();
	var method = "POST";
	var data = "action=deleteFriend" + "&friendName=" + friendName;
	var url = "/chatRoom/servlet/handleSingleAction";
	var callBack = function(){};
	XMLHttp.sendRequest(method, url, data, callBack);

}

//隐藏会话
function hiddenSession(name) {
	
	var name_sendTo = name;
	if(name == null){
		name_sendTo = document.getElementById("titleDetails").innerText;
	}
	$("#totalDiv2").fadeOut(500);
	
	XMLHttp.getInstance();
	var url = "/chatRoom/servlet/handleSingleAction";
	var method = "POST";
	var data = "action=closeSession" + "&name_sendTo=" + name_sendTo;
	var callBack = function(){};
	XMLHttp.sendRequest(method, url, data, callBack)
	updateFriendList();

}

//显示会话
function showSession() {
	
	$("#totalDiv2").fadeIn(500);
	var messageFather = document.getElementById("message2");
	//移除之前的消息
	while(messageFather.hasChildNodes()){
		messageFather.removeChild(messageFather.firstChild);
	}
}

//开始临时会话
function startSession(name) {
	
	//开始新会话之前先结束之前的会话
	hiddenSession(name);
	//显示会话页面
	showSession();
	//视频通话按钮启用
	$('#video').attr('disabled',false);
	//更新朋友列表
	var name_sendTo = document.getElementById("titleDetails");
	name_sendTo.innerText = name;
	
	XMLHttp.getInstance();
	var url = "/chatRoom/servlet/handleSingleAction?action=" + "updateSession";
	var method = "POST";
	var data = "action=updateSession" + "&name_sendTo=" + name;
	var callBack = function(request) {
		updateSessionPage(request);
	};
	XMLHttp.sendRequest(method, url, data, callBack);
}

//更新会话页面个人信息
function updateSessionPage(request) {
	
	if(request.responseText == "error"){
		request.abort();
		window.location.href = "/chatRoom/servlet/logoutServlet?action=error";
		
		return;
	}
	
	//服务器传回来的聊天对象的资料
	var information = JSON.parse(request.responseText);
	
	var name = information.name;
	var sex = information.sex;
	var motto = information.motto;
	var activity = information.activity;
	
	var titleDetails = document.getElementById("titleDetails");
	var sexDiv = document.getElementById("sexDiv");
	var mottoDiv = document.getElementById("mottoDiv");
	var activityDiv = document.getElementById("activityDiv");

	titleDetails.innerHTML = name;
	$("#sendtoImgInfo").css("background", "url('/chatRoom/files/users/"+ name +"/" + name +".jpg') center no-repeat")
	.css("background-size", "contain");
	sexDiv.innerHTML = sex;
	mottoDiv.innerHTML = motto;
	activityDiv.innerHTML = activity;
	
	request.abort();
}

//发送更新消息请求
function updateMessage() {
	
	XMLHttp.getInstance();
	
	var url = "/chatRoom/servlet/handleSingleAction";
	var method = "POST";
	var data = "action=updateMessage";
	var callback = function(checkMessageRequest){
		updateMessagePage(checkMessageRequest);
	};
	XMLHttp.sendRequest(method, url, data, callback);
}

//更新消息组件
function updateMessagePage(checkMessageRequest) {
	
	if(checkMessageRequest.responseText == "error"){
		checkMessageRequest.abort();
		window.location.href = "/chatRoom/servlet/logoutServlet?action=error";
		
		return;
	}
	//服务器无消息提示
	if(checkMessageRequest.responseText == "noMessage"){
		//初始化请求
		checkMessageRequest.abort();
		//继续发送请求
		updateMessage();
		return;
	}
	var messageNode = document.getElementById("message");
	var message = JSON.parse(checkMessageRequest.responseText);
	var firstTr = document.getElementById("firstTr");
	var hasMessage = false;
	
	//移除所有子节点
	while(firstTr.hasChildNodes()) {
		firstTr.removeChild(firstTr.firstChild);
	}
		
	for(var name_sender in message) {
		var messageDiv = document.createElement("div");
		var friendDiv = document.createElement("div");
		friendDiv.innerHTML = "朋友：" + name_sender;
		friendDiv.setAttribute("class", "firstTr firstTr_name shadow3");
		if(message[name_sender].substring(message[name_sender].length - 4,message[name_sender].length) == ".png"){
			var emoji = document.createElement("div");
			//emoji消息
			emoji.setAttribute("class", "emoji emojiMessage");
			var img = document.createElement("img");
			var imgUrl = "/chatRoom/img/emoji/" + message[name_sender];
			img.setAttribute("src", imgUrl);
			emoji.appendChild(img);
			messageDiv.appendChild(emoji);
			messageDiv.setAttribute("class", "firstTr firstTr_message inset-shadow");
		}else{
			messageDiv.innerHTML = " 消息：" + message[name_sender]
			messageDiv.setAttribute("class", "firstTr firstTr_message inset-shadow");
		}			
		
		friendDiv.onclick = function() {
			startSession(name_sender);
			this.parentNode.removeChild(this);
			messageDiv.onclick();
			messageNode.style.backgroundColor = "#8080ff";
		};
		messageDiv.onclick = function () {
			this.parentNode.removeChild(this);
		}

		firstTr.appendChild(friendDiv);
		firstTr.appendChild(messageDiv);
		//滚动
		scroll.call(firstTr);
		hasMessage = true;
	}
	checkMessageRequest.abort();
	messageNode.style.backgroundColor = "#800040";
	
	updateMessage();
}

//发送消息
function sendSingleMessage() {
	
	var input = document.getElementById("messageInput");
	var inputMessage = input.value;
	input.focus();
	input.value = null;
	
	if(inputMessage == "" || inputMessage == null || inputMessage == undefined) {
		return;
	}
	//消息发送对象
	var name_sendTo = document.getElementById("titleDetails").innerText;
	if(name_sendTo.trim() == "启 航 Zone"){
		return;
	}
	
	XMLHttp.getInstance();
	
	var url = "/chatRoom/servlet/singleChat";
	var data = "message="+ inputMessage + "&name_sendTo=" + name_sendTo;
	var method = "POST";
	var callback = function(singleRequest) {
		handleSessionResponse(singleRequest);
	};
	XMLHttp.sendRequest(method, url, data, callback);
}

//发送图片消息
function sendImgMessage(imgIndex) {
	
	//消息发送对象
	var name_sendTo = document.getElementById("titleDetails").innerText;
	if(name_sendTo.trim() == "启 航 Zone"){
		return;
	}
	
	XMLHttp.getInstance();
	
	var url = "/chatRoom/servlet/singleChat";
	var method = "POST";
	var data = "imgIndex="+imgIndex+"&name_sendTo="+name_sendTo;
	var callback = function(singleRequest) {
		handleSessionResponse(singleRequest);
	};
	XMLHttp.sendRequest(method, url, data, callback);
}

//继续发送请求
function continueAjaxRequest() {
	
	XMLHttp.getInstance();
	var url = "/chatRoom/servlet/singleChat";
	var method = "POST";
	var data = "action=hold";
	var callback = function(singleRequest2) {
		handleSessionResponse2(singleRequest2);
	};
	XMLHttp.sendRequest(method, url, data, callback);
}

//处理信息结果返回
function handleSessionResponse2(singleRequest2) {
	
	if(singleRequest2.responseText == "error"){
		singleRequest2.abort();
		window.location.href = "/chatRoom/servlet/logoutServlet?action=error";
		
		return;
	}
		updateSessionMessage(singleRequest2);
		//继续发送空请求
		continueAjaxRequest();
}

//处理信息结果返回
function handleSessionResponse(singleRequest) {
	
	if(singleRequest.responseText == "error"){
		singleRequest.abort();
		window.location.href = "/chatRoom/servlet/logoutServlet?action=error";

		return;
	}
	if(singleRequest.responseText != null && singleRequest.responseText != ""){
		updateSessionMessage(singleRequest);
	}
}

//更新页面的会话消息
function updateSessionMessage(singleRequest) {
	
	var responseText = singleRequest.responseText;
	if(responseText == "noMessage"){
		//请求超时处理
		singleRequest.abort();
		return;
	}
	
	messageCount++;
	//移除子节点,设置聊天信息缓存不超过100条
	if(messageCount > 100) {
		var i = 100;
		for(; i > 0; ) {
			messageFather.removeChild(messageFather.lastChild);
			messageCount--;
			i--;
		}
	}
	
	var json = JSON.parse(responseText);
	//消息的父节点引用	
	var messageFather = document.getElementById("message2");
	//消息的发送者
	var name_sender = json.name;
	//消息
	var message = json.message;
	//图片消息
	var imgIndex = json.imgIndex;
	//接收者状态
	var status = json.status;
	
	if(status == "offline") {
		var tipDiv = document.createElement("div");
		tipDiv.innerHTML = "小提示";
		tipDiv.setAttribute("class", "firstTr firstTr_name firstTr_tip shadow");
		var tipDiv2 = document.createElement("div");
		tipDiv2.innerHTML = "对方离线，系统已为你缓存最近一条消息.";
		tipDiv2.setAttribute("class", "firstTr firstTr_message firstTr_tip inset-shadow");
		
		//页面添加对方不在线的提示信息
		messageFather.appendChild(tipDiv);
		messageFather.appendChild(tipDiv2);
	}
	
	var senderDiv = document.createElement("div");
	var senderText = document.createTextNode(name_sender);
	$(senderText).css("float", "left");
	var senderImg = document.createElement("div");
	$(senderImg).css("height", "30px").css("float", "left").css("width", "30px")
	.css("background", "url('/chatRoom/files/users/"+ name_sender +"/" + name_sender +".jpg') center no-repeat")
	.css("background-size", "contain").css("border-radius", "50%");
	senderDiv.appendChild(senderImg);
	senderDiv.appendChild(senderText);
	senderDiv.setAttribute("class", "firstTr firstTr_name shadow");
	var messageDiv = document.createElement("div");
	if(imgIndex != null && imgIndex != undefined) {
		var emoji = document.createElement("div");
		//emoji消息
		emoji.setAttribute("class", "emoji emojiMessage");
		var img = document.createElement("img");
		var imgUrl = "/chatRoom/img/emoji/" + imgIndex + ".png";
		img.setAttribute("src", imgUrl);
		emoji.appendChild(img);
		messageDiv.appendChild(emoji);
		messageDiv.setAttribute("class", "firstTr firstTr_message inset-shadow");
	}else{
		messageDiv.innerHTML = message;
		messageDiv.setAttribute("class", "firstTr firstTr_message inset-shadow");
	}
	

	//添加消息记录
	messageFather.appendChild(senderDiv);
	messageFather.appendChild(messageDiv);
	
	singleRequest.abort();
	scroll.call(messageFather);
}


//div滚动条的实现函数
var scroll = function() {

	//设置滚动到底部
	this.scrollTop = this.scrollHeight;
};

function changeImg() {
	
	var imgIndex = Math.floor(Math.random()*10+1);
	var imgUrl = "/chatRoom/img/" + imgIndex + ".jpg";
	var background = document.getElementById("background");
	background.style.background = "url("  + "'" + imgUrl+ "'" + ")" + " no-repeat";
}


//初始化函数，为页面载入服务器的emoji表情
function appendEmoji() {
	
	var emojiDiv = document.getElementById("emojiDiv");
	if (emojiDiv.hasChildNodes()) {
		while(emojiDiv.hasChildNodes()) {
			emojiDiv.removeChild(emojiDiv.firstChild)
		}
	} 
	for(var i = 1; i <= 16; i++) {
		//javascript闭包
		(function() {
			var j = i;
			var emoji = document.createElement("div");
			emoji.setAttribute("class", "emoji");
			var img = document.createElement("img");
			var imgSrc = "/chatRoom/img/emoji/" + i + ".png";
			img.setAttribute("src", imgSrc);
			emoji.appendChild(img);
			emoji.onclick = function() {
              sendImgMessage(j);
          };
			emojiDiv.appendChild(emoji);
		})();	
	}
}












