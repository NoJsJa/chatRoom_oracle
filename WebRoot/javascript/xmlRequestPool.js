//创建XMLHttpRequest对象的池
var XMLHttp = 
{
    //缓存XMLHttpRequest对象的数组
    XMLHttpRequestPool: [],
    //方法返回一个XMLHttpRequest对象
    getInstance: function() {
        for(var i = 0; i < this.XMLHttpRequestPool.length; i++) {
            //如果当前的请求对象空闲
            
            if(this.XMLHttpRequestPool[i].readyState == 0) {

                return this.XMLHttpRequestPool[i];
            }
        }
        
      //如果没有空闲的
        this.XMLHttpRequestPool[this.XMLHttpRequestPool.length] = this.createXMLHttpRequest();
        return this.XMLHttpRequestPool[this.XMLHttpRequestPool.length - 1];
    },

    //创建对象
    createXMLHttpRequest: function () {
        if(window.XMLHttpRequest){
            var objXMLHttp = new XMLHttpRequest();
        }else{
            //将IE中内置的所以XMLHTTP ActiveX设置成数组
            var MSXML = ['MSXML2.XMLHTTP.5.0', 'MSXML2.XMLHTTP.4.0', 'MSXML2.XMLHTTP.3.0', 'MSXML2.XMLHTTP', 'Microsoft.XMLHTTP'];
            for(var n = 0; n < MSXML.length; n++) {
                try{
                	 var objXMLHttp = new ActiveXObject(MSXML[n]);
                     break;
                }catch (e){
                	alert(e);
                }
            }
        }

        //Mozilla某些版本没有Readystate属性
        if(objXMLHttp.readyState == null) {
            //手动置为未初始化状态
            objXMLHttp = 0;
            //对于没有readystate属性的浏览器，将load动作与下面的函数关联起来
            objXMLHttp.addEventListener("load", function(){
                //等服务器的数据加载完成后，将readyState设为4
                objXMLHttp.readyState = 4;
                if(typeof objXMLHttp.onreadystatechange == "function"){
                    objXMLHttp.onreadystatechange();
                }
            },false);
        }
        return objXMLHttp;
    },

    //发送请求
    sendRequest: function(method, url, data, callback){
        var objXMLHttp = this.getInstance();
        with(objXMLHttp){
            try{
                //增加一个额外的请求参数，防止IE缓存服务器响应
                if(url.indexOf("?") > 0){
                    url += "&randnum=" + Math.random();
                }else{
                    url += "?randnum=" + Math.random();
                }
                //打开服务器连接
                open(method, url, true);
                if(method == "POST"){
                    setRequestHeader('Content-Type','application/x-www-form-urlencoded');
                    send(data);
                    /*//设置超时2.5分钟
                    setTimeout(function(){
                    	if(objXMLHttp.readyState != 4 && objXMLHttp.readyState != 0){
                    		alert("ajax响应超时");
                    		objXMLHttp.abort();
                    	}
                    }, "121000");*/
                }
                if(method == "GET"){
                    send(null);
                }

                //设置状态改变的回调函数
                onreadystatechange = function () {
                    if(objXMLHttp.readyState == 4 &&
                        (objXMLHttp.status == 200 || objXMLHttp.status == 304)){

                        //服务器响应完成
                        callback.call(null, objXMLHttp);
                        //手动置为不可使用状态，加锁
                        
                    }
                };
            }catch (e){
                alert(e);
            }
        }
    }

};


















