
//初始化页面函数
$(function() {

	$("#sendButton").click(function () {
		sendMessage();
	});
	$("#logoutButton").click(function(){
		logout();
	});
	document.getElementById("quickList").onchange = quickSend;
	$("#tips").click(function(){
		removeTips();
	});
	$("#backButton").click(function(){
		back();
	});
	appendEmoji();
	$("#emojiDiv").click(function(){
		appendEmoji();
	});
	$("#fileTitle").click(function(){
		fileShow();
	});
	$("#fileUpload").click(function(){
		fileUpload();
	});
	updateOnline ();		//发送更新在线人数请求
	continueRequest();		//发送消息询问空请求
	readFile();	//读取共享文件
	document.onkeydown = function(event){
        var e = event || window.event || arguments.callee.caller.arguments[0];       
        if(e && e.keyCode == 13){ // enter 键
        	sendMessage();
        }
	}
});

var messageNode = 0;
//这是聊天列表的聊天信息行数

//上传文件到服务器
function fileUpload(){
	
	if($("#fileChoose").val() == ""){
		alert("请选择文件！");
		return;
	}

	$.ajaxFileUpload(
			{
				 url: '/chatRoom/servlet/handleSingleAction?action=fileUpload',  
				 secureuri: false,
				 type: 'POST',
				 fileElementId: 'fileChoose', //file标签的id  
				
				 dataType: 'text', //返回值类型 一般设置为json
		         success: function (data, status) {  
		                //把图片替换  	
		        	 var responseJson = jQuery.parseJSON(data);  
		        	 if(responseJson.tips == "okay"){
		        		 clearFileList();
		        		 readFile();
		        	 }
		         },  
		         error: function (data, status, e) {  
		                alert("errorMessage: " + e);  
		         }  
			}
	);
}

//清除文件列表
function clearFileList(){
	
	$("#file").empty();
}

//读取文件列表
function readFile() {

	XMLHttp.getInstance();
	var method = "POST";
	var data = "action=readFile";
	var url = "/chatRoom/servlet/handleSingleAction";
	var callback = function(fileRequest) {
		updateFileList(fileRequest);
	};
	XMLHttp.sendRequest(method, url, data, callback);
}

//更新文件列表的函数
function updateFileList(fileRequest){

	var fileDetails = JSON.parse(fileRequest.responseText);
	for(file in fileDetails){
		$("<a></a>",
			{
				text:fileDetails[file].substring(fileDetails[file].lastIndexOf("/") + 1, fileDetails[file].length),
				href:fileDetails[file],
				title:fileDetails[file].substring(fileDetails[file].lastIndexOf("/") + 1, fileDetails[file].length)
			}
		)
			.appendTo($("#file"));
		$("<br>").appendTo($("#file"));
	}
}

//展开/收起文件列表
function fileShow(){
	
	//折叠展开的动画
	$("#file").toggle("fast");
	$("#fileUploadDiv").toggle("fast");
}

//返回
function back() {
	window.location.href = "/chatRoom/homePage.jsp";
}

//发送消息
function sendMessage() {

	if(isNull()) {
		return;
	}
	XMLHttp.getInstance();
	
	var inputMessage = document.getElementById("inputMessage");
	var userName = document.getElementById("userLabel").innerText;
	var emoji = document.getElementById("emojiList");		//emoji表情
	var emojiMessage = emoji.options[emoji.selectedIndex].text;
	var url = "/chatRoom/servlet/messageServlet";
	var message = inputMessage.value.trim() + emojiMessage;
	var data = "message="+ message+"&"+"name="+userName;
	var method = "POST";
	var callback = function(request){
		acceptMessage(request);
	};
	XMLHttp.sendRequest(method, url, data, callback);
	inputMessage.value = "";
}


//接受信息更新页面元素
function acceptMessage(request) {
			
			if(request.responseText == "error"){
				window.location.href = "/chatRoom/servlet/logoutServlet?action=error";
				
				return;
			}
			updateOnline();
			
			handleResponse(request);
}

//根据响应处理页面
function handleResponse(request) {

	//从字符串对象转换为Json对象
	var itemDetails = JSON.parse(request.responseText);			

    var name = itemDetails.name;
    var message = itemDetails.message;
    var imgUrl = itemDetails.imgUrl;

	if((message != "" && message != null) || (imgUrl != "" && imgUrl != "")) {

		var messageTb = document.getElementById("showTable");
		messageNode++;
		//移除子节点，设置最大子节点数为100
		if(messageNode > 100) {
			var i = 100;
			for(; i > 0; ) {
				messageTb.removeChild(messageTb.lastChild);
				messageNode--;
				i--;
			}
		}
		var messageTr = document.createElement("tr");
		
		var messageTd1 = document.createElement("td");
		var messageTd2 = document.createElement("td");
		
		var messageLabel1 = document.createElement("label");
		var messageDiv1 = document.createElement("div");
		messageDiv1.setAttribute("class", "nameTd");
		var headerImgDiv = document.createElement("div");
		headerImgDiv.setAttribute("class", "headerImgMessage");
		$(headerImgDiv).css("background", "url('/chatRoom/files/users/"+ name +"/" + name +".jpg') center no-repeat")
		.css("background-size", "contain");
		
		messageLabel1.appendChild(document.createTextNode(name));
		messageDiv1.appendChild(headerImgDiv);
		messageDiv1.appendChild(messageLabel1);
		messageTd1.appendChild(messageDiv1);
		
		
		//文字信息
		if (message != "" && message != null){
			
			var messageLabel2 = document.createElement("label");
			messageLabel2.appendChild(document.createTextNode(message));
			
			//消息td
			var messageDiv2 = document.createElement("div");
			messageDiv2.setAttribute("class", "nameTd messageTd");
			var messageDiv3 = document.createElement("div");
			messageDiv3.setAttribute("class", "arrow");
			messageDiv2.appendChild(messageDiv3);
			
			
			messageDiv2.appendChild(messageLabel2);
			
			messageTd2.appendChild(messageDiv2);
			
			//图片信息
		}else if (imgUrl != "" && imgUrl != "") {
			
			var imgDiv = document.createElement("div");
			imgDiv.setAttribute("class", "emoji");
			var img = document.createElement("img");
			img.setAttribute("src", imgUrl);
			imgDiv.appendChild(img);
			
			messageTd2.appendChild(imgDiv);
			
		}
		
		messageTr.appendChild(messageTd1);
		messageTr.appendChild(messageTd2);
		messageTb.appendChild(messageTr);		//增添一行
		scrollDiv();
	}
	
	request.abort();
	continueRequest();		//继续发送空请求保持连接
}

//发送空请求
function continueRequest() {
	
	XMLHttp.getInstance();
	
	var userName = document.getElementById("userLabel").innerText;
	var url = "/chatRoom/servlet/messageServlet";
	var method = "POST";
	var data = "name=" + userName;
	var callback = function(request) {
		acceptMessage(request);
	};
	
	XMLHttp.sendRequest(method, url, data, callback);
}

//div滚动条的实现函数
function scrollDiv() {
	
	var div = document.getElementById("tableBorder");
	div.scrollTop = div.scrollHeight;
}

//检查消息是否为空
function isNull() {
	
	var emoji = document.getElementById("emojiList");		//emoji表情
	var emojiMessage = emoji.options[emoji.selectedIndex].text;
	if(document.getElementById("inputMessage").value.trim() != "" || emojiMessage != "") {
		return false;
	}else {
		return true;
	}
}

//注销账号函数
function logout() {
	
	window.location.href = "/chatRoom/servlet/logoutServlet";
}


//快速发送
function quickSend() {
	
	var select = document.getElementById("quickList");
	var selectEmoji = document.getElementById("emojiList");
	var inputMessage = document.getElementById("inputMessage");
	
	var index = select.selectedIndex;		//选中的项目
	var emojiIndex = selectEmoji.selectedIndex;
	inputMessage.value = select.options[index].text;
	
	sendMessage();		//发送消息
}

//更新页面在线人数
function updateOnline () {
	
	XMLHttp.getInstance();	
	
	var url = "/chatRoom/servlet/updateOnline";
	var method = "POST";
	var data = null;
	var callback = function(request) {
		onlineChange(request);
	};

	XMLHttp.sendRequest(method, url, data, callback);
	
}

//更新页面在线人数
function onlineChange(request) {
	if(request.responseText == "error"){
		window.location.href = "/chatRoom/servlet/logoutServlet?action=error";
		
		return;
	}
	if(request.responseText != null) {
		//	去除字符串首尾的两个字符
		var response = request.responseText.substring(1, request.responseText.length - 1)
		var onlineCount = document.getElementById("onlineDiv");
		var name = response.split(",");
		var accDiv = document.getElementsByName("accountDiv");
		while(onlineCount.hasChildNodes()) {
			onlineCount.removeChild(onlineCount.firstChild);
		}
		var n = 0
		for(; n < name.length; n++) {
			var accountDiv = document.createElement("div");
			accountDiv.setAttribute("class", "onlineAccount");
			accountDiv.setAttribute("name", "accountDiv");
			accountDiv.innerHTML = name[n];
			onlineCount.appendChild(accountDiv);
		}
		document.getElementById("onlineCount").innerHTML = "在线人数->" + n;
	}
	//初始化请求
	request.abort();
}

//移除小贴士提示信息
function removeTips() {
	
	var tips = document.getElementById("tips");
	tips.parentNode.removeChild(tips);
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
                sendImg(j);
            };
			emojiDiv.appendChild(emoji);
		})();
	}
}

//移除所有emoji表情
function removeEmoji() {
	
	var emojiDiv = document.getElementById("emojiDiv");
	if (emojiDiv.hasChildNodes()) {
		while(emojiDiv.hasChildNodes()) {
			emojiDiv.removeChild(emojiDiv.firstChild);
		}
	} 
}

//发送图片消息
function sendImg(i) {
	
	XMLHttp.getInstance();
	
	var userName = document.getElementById("userLabel").innerText;
	var method = "POST";
	var url = "/chatRoom/servlet/messageServlet";
	var data = "imgIndex=" + i+"&name="+userName;
	var callback = function(imgRequest) {
		acceptMessage(imgRequest);
	};
	XMLHttp.sendRequest(method, url, data, callback);
}





