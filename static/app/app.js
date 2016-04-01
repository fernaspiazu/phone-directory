'use strict';

(function(){
	angular.module('phoneDirectory', ['ngResource', 'ngRoute'])
		.config(['$routeProvider', function($routeProvider) {
			$routeProvider.when('/', {
				"templateUrl": "app/home/home-page.tmpl.html",
				"controller": "HomeCtrl",
				"controllerAs": "homeCtrl",
				"resolve": {
					"firstPageInTable": ['PhoneDirectoryAPI', function(PhoneDirectoryAPI) {
						return PhoneDirectoryAPI.find({"page": 0, "size": 10}).$promise
					}],
					"isEmptyContent": ['PhoneDirectoryAPI', function(PhoneDirectoryAPI) {
						return PhoneDirectoryAPI.isEmpty().$promise
					}]
				}
			})
			.when('/new', {
				"templateUrl": "app/form/edit-form.tmpl.html",
				"controller": "FormCtrl",
				"controllerAs": "formCtrl"
			})
			.when('/not-found', {
				"templateUrl": "app/error/404.html"
			})
			.otherwise({"redirectTo": '/not-found'})
		}]);
})();