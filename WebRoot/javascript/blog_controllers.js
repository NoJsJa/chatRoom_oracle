'use strict';

/* Controllers */


var blogControllers = angular.module('blogControllers', []);

//依赖注入的顺序很重要
/*读取文章列表CTRL*/
blogControllers.controller('BlogCtrl', ['$scope','$http','$routeParams',
    function BlogCtrl($scope, $http, $routeParams) {

        var blogAuthor = $routeParams.author;
	    //初始化方法
        function init(){
        	/*作者*/

            $scope.author = blogAuthor;
            $scope.authorImg = "/chatRoom/files/users/" + blogAuthor+ "/"+ blogAuthor + ".jpg";
            var url = '/chatRoom/blog.do?action=readList';
            var data = "&blogAuthor=" + blogAuthor;
            $http({
                url: url + data,
                method: 'POST'
                }).success(function (data,header,config,status) {
                	/*得到博客列表的json字符串后转化成Json对象*/
                	
                	if(data == '' || data == null){
                		return alert('recieve error!');
                	}
                	var blogList = null;
                	if(data != "null"){
                		//已经是一个对象了，可能是AngularJS自动解析的
                		blogList = data;
                	}else{
                		alert('您还没有发布过任何博文.');
                	}
                	
                //交由视图处理
                $scope.blogList = blogList;
            }).error(function (data,header,config,status) {
                alert('send error!');
            });
        };     
        /*读取文章列表*/
        init();
        
        //读取用户信息
        function readAuthorData(){
        	$http({
                method:'POST',
                url:'/chatRoom/blog.do?action=readAuthorData',
                params:{
                    'author':blogAuthor
                }
            }).success(function (data,header,config,status) {
                $scope.authorName = data.authorName;
                $scope.authorSex = data.authorSex;
                $scope.authorMotto = data.authorMotto;
                $scope.authorActivity = data.authorActivity;

            }).error(function (data,header,config,status) {
                alert('error');
            });
        }
        readAuthorData();

        //删除博客
        $scope.deleteBlog = function (title,author) {
            $http({
                method:'POST',
                url:'/chatRoom/blog.do?action=deletePost',
                params:{
                    'blogAuthor':author,
                    'blogTitle':title
                }
            }).success(function (data,header,config,status) {
                init();
            }).error(function (data,header,config,status) {
                alert('error');
            });
        };
		
    }]);

/*读取文章内容CTRL*/
blogControllers.controller('BlogViewCtrl',['$scope','$routeParams','$http',
    function BlogViewCtrl($scope,$routeParams,$http) {

        /*文章的搜索信息*/
        var blogAuthor = $routeParams.author;
        var blogTitle = $routeParams.title;
        $scope.author = blogAuthor;
        $scope.authorImg = "/chatRoom/files/users/" + blogAuthor+ "/"+ blogAuthor + ".jpg";
        
        function readPost(){
            //查询的服务器URL
            var url = "/chatRoom/blog.do?action=readPost";
            var queryData = "&blogAuthor=" + blogAuthor + "&blogTitle=" + blogTitle;

            $http({
                url:url + queryData,
                method: 'POST'
            }).success(function (data,header,config,status) {
                if(data == "error"){
                    alert("服务器错误!");
                    return;
                }
                /*得到文章内容 的Json数组*/
                var blogPost = data;
                console.log('blogPost:' + blogPost.content);
                //将数据交付给视图处理
                $scope.blogPost = blogPost;
            }).error(function (data,header,config,status) {
                alert('error');
            });
        };
        //读取留言
        readPost();

        /*发表文章留言*/
        $scope.publish = function () {
            $http({
                url:'/chatRoom/blog.do?action=newBlogComment',
                method:'POST',
                params:{
                    'title':blogTitle,
                    'author':blogAuthor,
                    'content':$scope.content,
                }
            }).success(function (data,header,config,status) {
                readPost();
            }).error(function (data,header,config,status) {
                alert('error');
            });
        };
    }
]);

/*发布博客*/
blogControllers.controller('BlogBuildCtrl',['$scope','$routeParams','$http',
    function blogBuildCtrl($scope,$routeParams,$http) {
        var blogAuthor = $routeParams.author;
        $scope.author = $routeParams.author;
        $scope.authorImg = "/chatRoom/files/users/" + blogAuthor+ "/"+ blogAuthor + ".jpg";
        $scope.publish = function () {
            $http({
                method:'POST',
                url:'/chatRoom/blog.do?action=newPost',
                params:{
                    'title':$scope.title,
                    'abstract':$scope.abstract,
                    'tag':$scope.tag,
                    'content':$scope.content,
                    'author':$scope.author
                }
            }).success(function (data,header,config,status) {
                alert('success');
            }).error(function (data,header,config,status) {
                alert('err');
            });
        };

        //读取用户信息
        function readAuthorData(){
            $http({
                method:'POST',
                url:'/chatRoom/blog.do?action=readAuthorData',
                params:{
                    'author':blogAuthor
                }
            }).success(function (data,header,config,status) {
                $scope.authorName = data.authorName;
                $scope.authorSex = data.authorSex;
                $scope.authorMotto = data.authorMotto;
                $scope.authorActivity = data.authorActivity;

            }).error(function (data,header,config,status) {
                alert('error');
            });
        }
        readAuthorData();
    }
]);

/*给用户留言*/
blogControllers.controller('BlogCommentsCtrl',['$scope','$routeParams','$http',
    function blogCommentsCtrl($scope,$routeParams,$http) {
        var blogAuthor = $routeParams.author;
        $scope.author = $routeParams.author;
        $scope.authorImg = "/chatRoom/files/users/" + blogAuthor+ "/"+ blogAuthor + ".jpg";
        //读取所有留言
        function readComment(){
            $http({
                method:'POST',
                url:'/chatRoom/blog.do?action=readComment',
                params:{
                    'author':$scope.author
                }
            }).success(function (data,header,config,status) {
                $scope.comments = data;
            }).error(function (data,header,config,status) {
                alert('error');
            });
        };

        readComment();

        //发布留言
        $scope.publish = function(){
            $http({
                method:'POST',
                url:'/chatRoom/blog.do?action=newComment',
                params:{
                    'content':$scope.content,
                    'author':$scope.author
                }
            }).success(function () {
                readComment();
            }).error(function () {
                alert('error!');
            });
        };

        //读取用户信息
        function readAuthorData(){
            $http({
                method:'POST',
                url:'/chatRoom/blog.do?action=readAuthorData',
                params:{
                    'author':blogAuthor
                }
            }).success(function (data,header,config,status) {
                $scope.authorName = data.authorName;
                $scope.authorSex = data.authorSex;
                $scope.authorMotto = data.authorMotto;
                $scope.authorActivity = data.authorActivity;

            }).error(function (data,header,config,status) {
                alert('error');
            });
        }
        readAuthorData();

    }
]);

/*读取作者所有博文的标签*/
blogApp.controller('readTagCtrl',['$scope','$routeParams','$http','$location',
    function readTagCtrl($scope,$routeParams,$http,$location) {
        var author = $routeParams.author;
        $scope.author = author;
        $scope.authorImg = "/chatRoom/files/users/" + author+ "/"+ author + ".jpg";
        
        (function (){
            $http({
                method:'POST',
                url:'/chatRoom/blog.do?action=readAllTags',
                params:{
                    'author':author
                }
            }).success(function (data,header,config,status) {
                $scope.tags = data;
                console.log(data.toString());
            }).error(function (data,header,config,status) {
                alert('error');
            });

        })();

        //读取指定标签博文
        $scope.readTagPost = function (tag) {
            $location.path('blogList/blogTag/' + author + '/' + tag);
        };

        //读取用户信息
        function readAuthorData(){
            $http({
                method:'POST',
                url:'/chatRoom/blog.do?action=readAuthorData',
                params:{
                    'author':author
                }
            }).success(function (data,header,config,status) {
                $scope.authorName = data.authorName;
                $scope.authorSex = data.authorSex;
                $scope.authorMotto = data.authorMotto;
                $scope.authorActivity = data.authorActivity;

            }).error(function (data,header,config,status) {
                alert('error');
            });
        }
        readAuthorData();
    }
]);

//读取包含指定标签的所有博文列表
blogApp.controller('readTagPostCtrl',['$scope','$routeParams','$http',
    function readTagPostCtrl($scope,$routeParams,$http) {
        var tag = $routeParams.tag;
        var author = $routeParams.author;
        $scope.author = author;
        $scope.authorImg = "/chatRoom/files/users/" + author+ "/"+ author + ".jpg";
        
        function readTagPost(){
            $http({
                method:'POST',
                url:'/chatRoom/blog.do?action=readTagPost',
                params:{
                    'tag':tag,
                    'author':author
                }
            }).success(function (data,header,config,status) {
                $scope.blogList = data;
            }).error(function (data,header,config,status) {
                alert('error');
            });
        }
        readTagPost();

        //读取用户信息
        function readAuthorData(){
            $http({
                method:'POST',
                url:'/chatRoom/blog.do?action=readAuthorData',
                params:{
                    'author':author
                }
            }).success(function (data,header,config,status) {
                $scope.authorName = data.authorName;
                $scope.authorSex = data.authorSex;
                $scope.authorMotto = data.authorMotto;
                $scope.authorActivity = data.authorActivity;

            }).error(function (data,header,config,status) {
                alert('error');
            });
        }
        readAuthorData();
    }
]);




