'use strict';

(function(){
	angular.module('phoneDirectory', ['ngResource', 'ngRoute'])
		.config(['$routeProvider', function($routeProvider) {
			$routeProvider.when('/', {
				"templateUrl": "app/home/home-page.html",
				"controller": "HomeCtrl",
				"controllerAs": "homeCtrl"
			})
			.when('/not-found', {
				"templateUrl": "error/404.html"
			})
			.otherwise({"redirectTo": '/not-found'})
		}])
})();