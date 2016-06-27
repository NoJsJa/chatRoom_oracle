'use strict';

/* App Module */

var blogApp = angular.module('blogApp', [
    'ngRoute',     
    'blogControllers',
    'ng.ueditor',
    'ngSanitize'
]);

blogApp.config(['$routeProvider', '$locationProvider',
    function($routeProvider, $locationProvider) {
        $routeProvider.
        when('/blogList/:author', {
            templateUrl: '/chatRoom/partials/blogMain.html',
            controller: 'BlogCtrl'
        }).when('/blogPost/:author/:title',{
            templateUrl:'/chatRoom/partials/blogPost.html',
            controller: 'BlogViewCtrl'
        }).when('/blogBuild/:author',{
            templateUrl:'/chatRoom/partials/blogBuild.html',
            controller:'BlogBuildCtrl'
        }).when('/blogComments/:author',{
            templateUrl:'/chatRoom/partials/blogComments.html',
            controller:'BlogCommentsCtrl'
        }).when('/blogTag/:author',{
        	templateUrl:'/chatRoom/partials/blogTag.html',
            controller:'readTagCtrl'
        }).when('/blogList/blogTag/:author/:tag',{
            templateUrl:'/chatRoom/partials/blogMain.html',
            controller:'readTagPostCtrl'
        });

        $locationProvider.html5Mode(false).hashPrefix('!');
    }]);

blogApp.filter('trustHtml', function ($sce) {
    return function (input) {
        return $sce.trustAsHtml(input);
    }
});


