//初始化函数
function init() {
	document.getElementById("loginSubmit").onclick = loginCheck;
	document.getElementById("signUp").onclick = signUp;
	
	var loginButton = document.getElementById("loginSubmit");
	var signupButton = document.getElementById("signUp");
	
	mouseOver(loginButton);
	mouseOver(signupButton);
	mouseOut(loginButton);
	mouseOut(signupButton);
}


//注册onmouseover事件的工具函数
function mouseOver(component) {

	component.onmouseover = function() {
		changeColor.call(component, "#ffffff", "#0000a0");
	}
}

//注册onmouseout事件的工具函数
function mouseOut(component) {
	
	component.onmouseout = function() {
		changeColor.call(component, "#0000a0", "#ffffff");
	}
}

//检查登录
function loginCheck() {
	var userName = document.getElementById("name").value;
	var userPassword = document.getElementById("password").value;
	if (userName == "" || userPassword == "") {
		updatePage2("咦！", "用户名和密码都缺一不可额.");
		return;
	}
	document.forms[0].action = "/chatRoom/servlet/loginServlet";
	document.forms[0].method = "POST";
    document.forms[0].submit();
}

//改变对象的背景色和文字颜色
var changeColor = function (bgColor, color) {
	
	this.style.backgroundColor = bgColor;
	this.style.color = color;
}


//注册账户
function signUp() {
	
	var account = document.getElementById("name").value;
	var password = document.getElementById("password").value;
	if (account == "" || password == "") {
		updatePage2("咦！", "用户名和密码都缺一不可额.");
		return;
	}
	if(account.length > 8) {
		updatePage2("咦！", "用户名要小于8个字符额。");
		return
	}
	
	if (!stringCheck()) {
		updatePage2("啊呀！", "特殊字符不能作为用户名和密码额.");
		return;
	}
	
	XMLHttp.getInstance();
	
	var url = "/chatRoom/servlet/loginServlet";		//url重写
	var method = "POST";
	var data = "signUp=1" + "&name=" + account + "&password=" + password;
	var callback = function(request) {
		updatePage(request);
	};
	
	XMLHttp.sendRequest(method, url, data, callback);
}

function updatePage2(tips, labelText) {
	
	var table = document.getElementById("loginTable");
	var deleteTr = document.getElementsByName("responseTr");		//删除前一次的显示信息，得到对象的数组引用
	for(var i = 0; i < deleteTr.length; i++) {
		deleteTr[i].parentNode.removeChild(deleteTr[i]);
	}
	var responseTr = document.createElement("tr");
	responseTr.setAttribute("name", "responseTr");
	responseTr.setAttribute("class", "errorTr");
	var responseTd = document.createElement("td");
	var responseTd2 = document.createElement("td");
	var responseLabel = document.createElement("label");
	var tipLabel = document.createElement("label");
	responseLabel.setAttribute("class", "errorLabel");
	tipLabel.setAttribute("class", "errorLabel");
	
	
	tipLabel.appendChild(document.createTextNode(tips));
	responseLabel.appendChild(document.createTextNode(labelText));
	
	responseTd.appendChild(tipLabel);
	responseTd2.appendChild(responseLabel);
	responseTr.appendChild(responseTd);
	responseTr.appendChild(responseTd2);
	table.appendChild(responseTr);
}

//检测非法字符
function stringCheck() {
	 var filterString = "[#_%&'/\",;:=!^]@";
	 var nameString = document.getElementById("name").value;
	 var passwordString = document.getElementById("password").value;
	 var isValid = true;
	 for (var i = 1; i < filterString.length + 1; i++) {
		 if ( (nameString.indexOf(filterString.substring(i - 1, i))) > -1) { 
			 isValid = false;
			 break;
		 }
		 if ( (nameString.indexOf(filterString.substring(i - 1, i))) > -1) { 
			 isValid = false;
			 break;
		 }
	}
	 
	 return isValid;
}

function updatePage(request) {
	
	var responseText = request.responseText;
	var table = document.getElementById("loginTable");
	var deleteTr = document.getElementsByName("responseTr");		//删除前一次的显示信息，得到对象的数组引用
	for(var i = 0; i < deleteTr.length; i++) {
		deleteTr[i].parentNode.removeChild(deleteTr[i]);
	}
	var responseTr = document.createElement("tr");
	responseTr.setAttribute("name", "responseTr");
	responseTr.setAttribute("class", "errorTr");
	var responseTd = document.createElement("td");
	var responseTd2 = document.createElement("td");
	var responseLabel = document.createElement("label");
	var tipLabel = document.createElement("label");
	responseLabel.setAttribute("class", "errorLabel");
	tipLabel.setAttribute("class", "errorLabel");
	
	if(responseText == "okay") {
		tipLabel.appendChild(document.createTextNode("happy"));
		responseLabel.appendChild(document.createTextNode("成功注册了耶!"));
	}else {
		tipLabel.appendChild(document.createTextNode("Sorry"))
		responseLabel.appendChild(document.createTextNode("你想要的用户名不小心被注册啦.."));
	}
	responseTd.appendChild(tipLabel);
	responseTd2.appendChild(responseLabel);
	responseTr.appendChild(responseTd);
	responseTr.appendChild(responseTd2);
	table.appendChild(responseTr);
	
	request.abort();
}
















